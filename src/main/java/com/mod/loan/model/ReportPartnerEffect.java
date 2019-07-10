package com.mod.loan.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "report_partner_effect")
@Data
public class ReportPartnerEffect {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 注册日期
     */
    @Column(name = "day_key")
    private Date dayKey;

    /**
     * 注册渠道，来源
     */
    @Column(name = "user_origin")
    private String userOrigin;

    /**
     * 注册人数
     */
    @Column(name = "reg_cnt")
    private Integer regCnt;

    /**
     * 实名人数
     */
    @Column(name = "real_name_cnt")
    private Integer realNameCnt;

    /**
     * 提单人数
     */
    @Column(name = "submit_order_cnt")
    private Integer submitOrderCnt;

    /**
     * 首借人数
     */
    @Column(name = "first_submit_cnt")
    private Integer firstSubmitCnt;

    /**
     * 首借金额
     */
    @Column(name = "first_submit_amount")
    private BigDecimal firstSubmitAmount;

    /**
     * 插入时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 商户
     */
    @Column(name = "merchant")
    private String merchant;

    /**
     * 注册的登录数量
     */
    @Column(name = "login_cnt")
    private Integer loginCnt;

    /**
     * 个人信息认证数
     */
    @Column(name = "personal_info_certi_cnt")
    private Integer personalInfoCertiCnt;

    /**
     * 运营商认证数
     */
    @Column(name = "yys_cnt")
    private Integer yysCnt;

    /**
     * 银行卡绑定数
     */
    @Column(name = "bank_cnt")
    private Integer bankCnt;

    /**
     * 申请订单数
     */
    @Column(name = "order_cnt")
    private Integer orderCnt;

    /**
     * 风控通过数
     */
    @Column(name = "pass_risk_cnt")
    private Integer passRiskCnt;

    /**
     * 下款数
     */
    @Column(name = "loan_success_cnt")
    private Integer loanSuccessCnt;

    /**
     * 实名认证率
     */
    @Column(name = "real_name_certi_rate")
    private String realNameCertiRate;

    /**
     * 个人信息认证率
     */
    @Column(name = "personal_info_certi_rate")
    private String personalInfoCertiRate;

    /**
     * 运营商认证率
     */
    @Column(name = "yys_certi_rate")
    private String yysCertiRate;

    /**
     * 银行卡绑定率
     */
    @Column(name = "bank_bound_rate")
    private String bankBoundRate;

    /**
     * 申请转化率
     */
    @Column(name = "reg_apply_trans_rate")
    private String regApplyTransRate;

    /**
     * 下款率
     */
    @Column(name = "loan_rate")
    private String loanRate;

    /**
     * 审核通过率
     */
    @Column(name = "audit_pass_rate")
    private String auditPassRate;

    /**
     * 设置商户
     *
     * @param merchant 商户
     */
    public void setMerchant(String merchant) {
        this.merchant = merchant == null ? null : merchant.trim();
    }
}