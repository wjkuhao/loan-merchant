package com.mod.loan.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.RequestThread;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.config.Constant;
import com.mod.loan.model.OrderRiskInfo;
import com.mod.loan.service.OrderRiskInfoService;
import com.mod.loan.util.OkHttpReader;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "order")
public class OrderRiskController {

    private final RabbitTemplate rabbitTemplate;
    private final OkHttpReader okHttpReader;
    private final OrderRiskInfoService orderRiskInfoService;

    @Autowired
    public OrderRiskController(RabbitTemplate rabbitTemplate, OkHttpReader okHttpReader, OrderRiskInfoService orderRiskInfoService) {
        this.rabbitTemplate = rabbitTemplate;
        this.okHttpReader = okHttpReader;
        this.orderRiskInfoService = orderRiskInfoService;
    }

    @GetMapping(value = "risk_again")
    public ResultMessage risk_again(Long orderId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderId", orderId);
        jsonObject.put("merchant", RequestThread.get().getMerchant());
        rabbitTemplate.convertAndSend("queue_risk_order_notify", jsonObject);
        return new ResultMessage(ResponseEnum.M2000);
    }

    @GetMapping(value = "get_risk_result")
    public ResultMessage risk_result(Long orderId) {
        OrderRiskInfo orderRiskInfo = orderRiskInfoService.getByOrderId(orderId);
        if (orderRiskInfo==null){
            return new ResultMessage(ResponseEnum.M4000);
        }
        return new ResultMessage(ResponseEnum.M2000, orderRiskInfo.getRiskResult());
    }

    @GetMapping(value = "get_risk_tianji_score")
    public ResultMessage risk_tianji_score(Long orderId) {
        OrderRiskInfo orderRiskInfo = orderRiskInfoService.getByOrderId(orderId);
        if (orderRiskInfo==null){
            return new ResultMessage(ResponseEnum.M4000);
        }
        String tianjiScore = pullRiskData(orderRiskInfo.getRiskId(),"tianji_score");
        return new ResultMessage(ResponseEnum.M2000, tianjiScore);
    }

    @GetMapping(value = "get_risk_paipai_score")
    public ResultMessage risk_paipai_score(Long orderId) {
        OrderRiskInfo orderRiskInfo = orderRiskInfoService.getByOrderId(orderId);
        if (orderRiskInfo==null){
            return new ResultMessage(ResponseEnum.M4000);
        }
        String paipaiScore = pullRiskData(orderRiskInfo.getRiskId(),"paipai_score");
        return new ResultMessage(ResponseEnum.M2000, paipaiScore);
    }

    @GetMapping(value = "get_risk_antifraud_score")
    public ResultMessage risk_antifraud_score(Long orderId) {
        OrderRiskInfo orderRiskInfo = orderRiskInfoService.getByOrderId(orderId);
        if (orderRiskInfo==null){
            return new ResultMessage(ResponseEnum.M4000);
        }
        String antifraudScore = pullRiskData(orderRiskInfo.getRiskId(),"antifraud_score");
        return new ResultMessage(ResponseEnum.M2000, antifraudScore);
    }

    private String pullRiskData(String riskId, String dataType){
        //riskId为 R2-reportId 格式
        String[] riskIds = riskId.split("-");
        if (riskIds.length<2){
            return null;
        }
        String reportId = riskIds[1];

        String getParamPattern = "/api/risk_result/%s?reportId=%s&token=%s";
        String getParam = String.format(getParamPattern, dataType, reportId, Constant.mx_risk_token);

        String riskTianjiUrl = Constant.mx_risk_url.concat(getParam);
        return okHttpReader.get(riskTianjiUrl,null, null);
    }
}
