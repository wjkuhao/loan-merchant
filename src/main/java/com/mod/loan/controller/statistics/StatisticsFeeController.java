package com.mod.loan.controller.statistics;

import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.Page;
import com.mod.loan.common.model.RequestThread;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.service.MerchantFeeStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.PublicKey;
import java.util.Date;

/**
 * @author actor
 * @date 2019/7/4 11:17
 */
@RestController
@RequestMapping("statistics")
public class StatisticsFeeController {
    public static final Logger logger = LoggerFactory.getLogger(StatisticsFeeController.class);

    @Autowired
    private MerchantFeeStatisticsService merchantFeeStatisticsService;

    @RequestMapping(value = "fee_statistics_list")
    public ModelAndView merchant_fee_statistics_list(ModelAndView view) {
        view.setViewName("statistics/fee_statistics_list");
        return view;
    }

    // 当前时间数据条数
    @RequestMapping(value = "/merchant_now_statistics_count_ajax", method = {RequestMethod.POST})
    public ResultMessage merchant_now_statistics_count_ajax() {
        String merchant = RequestThread.get().getMerchant();
        Date now = new Date();
        return new ResultMessage(ResponseEnum.M2000, merchantFeeStatisticsService.merchantNowStatisticsCount(merchant,now));
    }

    // 查找全部商户费用配置
    @RequestMapping(value = "/fee_statistics_list_ajax", method = {RequestMethod.POST})
    public ResultMessage fee_statistics_list_ajax(String startTime, String endTime, Page page) {
        String merchant = RequestThread.get().getMerchant();
        return new ResultMessage(ResponseEnum.M2000, merchantFeeStatisticsService.findFeeStatisticsList(merchant, startTime, endTime, page), page);
    }

}
