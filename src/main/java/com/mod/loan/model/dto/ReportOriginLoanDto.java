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
     *  渠道名称
     */
    private String originName;

    /**
     * uv人数
     */
//    private String uvCount;

    /**
     * 注册人数
     */
    private String regCount;

    /**
     * 注册的登录人数
     */
//    private String loginCount;

    /**
     * 实名认证数
     */
    private String realNameCount;

    /**
     *  个人信息认证数
     */
    private String personalInfoCertiCount;

    /**
     * 运营商认证数
     */
    private String yysCount;

    /**
     * 银行卡绑定数
     */
    private String bankCount;

    /**
     * 申请订单数
     */
    private String orderCount;

    /**
     * 风控通过数
     */
    private String passRiskCount;

    /**
     * 下款数
     */
    private String loanSuccessCount;

    /**
     * 实名认证率
     */
    private String realNameCertiRate;

    /**
     * 个人信息认证率
     */
    private String personalInfoCertiRate;

    /**
     * 运营商认证率
     */
    private String yysCertiRate;

    /**
     * 银行卡绑定率
     */
    private String bankBoundRate;

    /**
     * 申请转化率
     */
    private String regApplyTransRate;

    /**
     * 下款率
     */
    private String loanRate;

    /**
     * 审核通过率
     */
    private String auditPassRate;

    /**
     * 注册通过率
     */
//    private String regPassRate;

}
