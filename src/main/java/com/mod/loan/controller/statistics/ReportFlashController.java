package com.mod.loan.controller.statistics;

import com.mod.loan.model.Merchant;
import com.mod.loan.service.MerchantService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "report_flash")
public class ReportFlashController {
    public static final Logger logger = LoggerFactory.getLogger(ReportFlashController.class);

    @Value("${server.itf.url}")
    String server_itf_url;

    private final
    MerchantService merchantService;

    @Autowired
    public ReportFlashController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @RequestMapping(value = "report_order_loan_flash")
    public void report_order_loan_flash() {
        try {
            Connection.Response execute = Jsoup.connect(server_itf_url + "report/report_order_loan?").ignoreContentType(true).ignoreHttpErrors(true).execute();
            logger.info("放款统计调用结果={}", execute.body());
        } catch (Exception e) {
            logger.error("放款统计异常", e);
        }
    }

    @RequestMapping(value = "report_order_repay_flash")
    public void report_order_repay_flash() {
        try {
            Connection.Response execute = Jsoup.connect(server_itf_url + "report/report_order_repay?").ignoreContentType(true).ignoreHttpErrors(true).timeout(5000).execute();
            logger.info("还款统计调用结果={}", execute.body());
        } catch (Exception e) {
            logger.error("还款统计异常", e);
        }
    }

    @RequestMapping(value = "report_register_order_flash")
    public void report_register_order_flash() {
        try {
            Connection.Response execute = Jsoup.connect(server_itf_url + "report/report_register_order?").ignoreContentType(true).ignoreHttpErrors(true).timeout(5000).execute();
            logger.info("注册提单统计调用结果={}", execute.body());
        } catch (Exception e) {
            logger.error("注册提单统计异常", e);
        }
    }

    @RequestMapping(value = "partner_report_deduction_list_flash")
    public void partner_report_deduction_list_flash() {
        List<Merchant> merchants = merchantService.selectMerchantAliasByStatus(1);
        for (Merchant merchant : merchants) {
            try {
                Connection.Response execute = Jsoup.connect(server_itf_url + "report/report_partner_effect_deduction?merchant=" + merchant.getMerchantAlias()).ignoreContentType(true).ignoreHttpErrors(true).timeout(5000).execute();
                logger.info("扣量渠道统计刷新调用结果={}", execute.body());
            } catch (Exception e) {
                logger.error("扣量渠道统计刷新异常，商户={}, error={}", merchant.getMerchantAlias(), e);
            }
        }
    }

    @RequestMapping(value = "partner_report_list_flash")
    public void partner_report_list_flash() {
        List<Merchant> merchants = merchantService.selectMerchantAliasByStatus(1);
        for (Merchant merchant : merchants) {
            try {
                Connection.Response execute = Jsoup.connect(server_itf_url + "report/report_partner_effect?merchant=" + merchant.getMerchantAlias()).ignoreContentType(true).ignoreHttpErrors(true).timeout(5000).execute();
                logger.info("渠道统计刷新调用结果={}", execute.body());
            } catch (Exception e) {
                logger.error("渠道统计刷新异常，商户={}, error={}", merchant.getMerchantAlias(), e);
            }
        }
    }


}
