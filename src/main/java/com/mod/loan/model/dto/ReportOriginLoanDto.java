package com.mod.loan.model.dto;

import lombok.Data;

/**
 * 渠道注册-放款统计页面列数据
 * add by changcf
 */
@Data
public class ReportOriginLoanDto {

    /**
     * 日期
     */
    private String date;

    /**
     * uv人数
     */
    private String uvCount;

    /**
     * 注册人数
     */
    private String regCount;

    /**
     * 注册的登录人数
     */
    private String loginCount;

    /**
     * 实名认证人数
     */
    private String realNameCount;

    /**
     * 运营商认证人数
     */
    private String yysCount;

    /**
     * 银行卡绑定人数
     */
    private String bankCount;

    /**
     * 订单申请人数
     */
    private String orderCount;

    /**
     * 通过风控人数
     */
    private String passRiskCount;

    /**
     * 放款成功人数
     */
    private String loanSuccessCount;

    /**
     * 注册→申请转化率
     */
    private String regApplyTransRate;

    /**
     * 审核通过率
     */
    private String auditPassRate;

    /**
     * 注册通过率
     */
    private String regPassRate;

}
