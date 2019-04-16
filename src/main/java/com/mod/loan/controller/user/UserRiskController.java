package com.mod.loan.controller.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.RequestThread;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.config.Constant;
import com.mod.loan.model.OrderRiskInfo;
import com.mod.loan.service.OrderRiskInfoService;
import com.mod.loan.service.OrderService;
import com.mod.loan.util.OkHttpReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "user")
public class UserRiskController {

    private static final Logger logger = LoggerFactory.getLogger(UserRiskController.class);

    private final RabbitTemplate rabbitTemplate;
    private final OkHttpReader okHttpReader;
    private final OrderRiskInfoService orderRiskInfoService;
    private final OrderService orderService;

    @Autowired
    public UserRiskController(RabbitTemplate rabbitTemplate, OkHttpReader okHttpReader, OrderRiskInfoService orderRiskInfoService, OrderService orderService) {
        this.rabbitTemplate = rabbitTemplate;
        this.okHttpReader = okHttpReader;
        this.orderRiskInfoService = orderRiskInfoService;
        this.orderService = orderService;
    }

    @RequestMapping(value = "risk_again")
    public ResultMessage risk_again(Long orderId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderId", orderId);
        jsonObject.put("merchant", RequestThread.get().getMerchant());
        rabbitTemplate.convertAndSend("queue_risk_order_notify", jsonObject);
        return new ResultMessage(ResponseEnum.M2000);
    }

    @GetMapping(value = "risk_result")
    public ResultMessage risk_result(Long orderId) {
        OrderRiskInfo orderRiskInfo = orderRiskInfoService.getLastOneByOrderId(orderId);
        if (orderRiskInfo==null){
            return new ResultMessage(ResponseEnum.M4000);
        }
        return new ResultMessage(ResponseEnum.M2000, orderRiskInfo.getRiskResult());
    }

    @GetMapping(value = "risk_tianji_score")
    public ResultMessage risk_tianji_score(Long orderId) {
        OrderRiskInfo orderRiskInfo = orderRiskInfoService.getLastOneByOrderId(orderId);
        if (orderRiskInfo==null){
            return new ResultMessage(ResponseEnum.M4000);
        }
        String tianjiScore = pullRiskData(orderRiskInfo.getRiskId(),"tianji_score");
        return new ResultMessage(ResponseEnum.M2000, tianjiScore);
    }

    @GetMapping(value = "risk_paipai_score")
    public ResultMessage risk_paipai_score(Long orderId) {
        OrderRiskInfo orderRiskInfo = orderRiskInfoService.getLastOneByOrderId(orderId);
        if (orderRiskInfo==null){
            return new ResultMessage(ResponseEnum.M4000);
        }
        String paipaiScore = pullRiskData(orderRiskInfo.getRiskId(),"paipai_score");
        return new ResultMessage(ResponseEnum.M2000, paipaiScore);
    }

    @GetMapping(value = "risk_antifraud_score")
    public ResultMessage risk_antifraud_score(Long orderId) {
        OrderRiskInfo orderRiskInfo = orderRiskInfoService.getLastOneByOrderId(orderId);
        if (orderRiskInfo==null){
            return new ResultMessage(ResponseEnum.M4000);
        }
        String antifraudScore = pullRiskData(orderRiskInfo.getRiskId(),"antifraud_score");
        return new ResultMessage(ResponseEnum.M2000, antifraudScore);
    }

    @RequestMapping(value = "user_risk_score")
    public ModelAndView order_risk_score(ModelAndView view, Long id) {
        view.addObject("id", id);
        view.setViewName("user/user_risk_score");
        return view;
    }

    @RequestMapping(value = "user_risk_score_ajax")
    public ResultMessage user_risk_score_ajax(Long id) { //uid
        Long orderId = orderService.selectLastOneByUid(id).getId();
        logger.info("user_risk_score_ajax uid={},orderId={}",id, orderId);
        OrderRiskInfo orderRiskInfo = orderRiskInfoService.getLastOneByOrderId(orderId);
        if (orderRiskInfo==null){
            return new ResultMessage(ResponseEnum.M4000);
        }

        String allScoreData = pullRiskData(orderRiskInfo.getRiskId(),"all_score");
        if(allScoreData!=null){
            return new ResultMessage(ResponseEnum.M2000, allScoreData);
        }
        return new ResultMessage(ResponseEnum.M4000);
    }

    private String pullRiskData(String riskId, String dataType){
        //riskId为 R2-reportId 格式
        if (riskId == null) {
            return "风控ID不存在";
        }
        String[] riskIds = riskId.split("-");
        if (riskIds.length<2){
            return null;
        }
        String reportId = riskIds[1];

        String getParamPattern = "/api/risk_result/%s?reportId=%s&token=%s";
        String getParam = String.format(getParamPattern, dataType, reportId, Constant.mx_risk_token);

        String riskUrl = Constant.mx_risk_url.concat(getParam);


        String result = okHttpReader.get(riskUrl,null, null);
        JSONObject object = JSON.parseObject(result);

        String returnMsg;
        if (Integer.parseInt(object.getString("status"))==200) {
            returnMsg = object.getString("data");
            if (returnMsg != null) {
                return returnMsg;
            }
        }

        return "无相关信息";
    }
}
