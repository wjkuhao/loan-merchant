package com.mod.loan.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * tb_merchant_fee_statistics
 *
 * @author
 */
public class MerchantFeeStatistics implements Serializable {
    /**
     * 日期yyyy-MM-dd
     */
    private String day;

    /**
     * 商户别名
     */
    private String merchant;

    /**
     * 短信1创蓝单价
     */
    private BigDecimal sms1Price;

    /**
     * 短信1创蓝条数
     */
    private Integer sms1Count;

    /**
     * 短信1创蓝总费用
     */
    private BigDecimal sms1Fee;

    /**
     * 飞鸽单价
     */
    private BigDecimal sms2Price;

    /**
     * 飞鸽条数
     */
    private Integer sms2Count;

    /**
     * 飞鸽总费用
     */
    private BigDecimal sms2Fee;

    /**
     * 有盾单价
     */
    private Integer youdunPrice;

    /**
     * 有盾次数
     */
    private Integer youdunCount;

    /**
     * 有盾总费用
     */
    private BigDecimal youdunFee;

    /**
     * 运营商单价
     */
    private BigDecimal operatorPrice;

    /**
     * 运营商个数
     */
    private Integer operatorCount;

    /**
     * 运营商费用
     */
    private BigDecimal operatorFee;

    /**
     * 风控单价
     */
    private BigDecimal riskPrice;

    /**
     * 风控个数
     */
    private Integer riskCount;

    /**
     * 风控费用
     */
    private BigDecimal riskFee;

    /**
     * 风控拒绝量
     */
    private Integer refusedCount;

    private static final long serialVersionUID = 1L;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public BigDecimal getSms1Price() {
        return sms1Price;
    }

    public void setSms1Price(BigDecimal sms1Price) {
        this.sms1Price = sms1Price;
    }

    public Integer getSms1Count() {
        return sms1Count;
    }

    public void setSms1Count(Integer sms1Count) {
        this.sms1Count = sms1Count;
    }

    public BigDecimal getSms1Fee() {
        return sms1Fee;
    }

    public void setSms1Fee(BigDecimal sms1Fee) {
        this.sms1Fee = sms1Fee;
    }

    public BigDecimal getSms2Price() {
        return sms2Price;
    }

    public void setSms2Price(BigDecimal sms2Price) {
        this.sms2Price = sms2Price;
    }

    public Integer getSms2Count() {
        return sms2Count;
    }

    public void setSms2Count(Integer sms2Count) {
        this.sms2Count = sms2Count;
    }

    public BigDecimal getSms2Fee() {
        return sms2Fee;
    }

    public void setSms2Fee(BigDecimal sms2Fee) {
        this.sms2Fee = sms2Fee;
    }

    public Integer getYoudunPrice() {
        return youdunPrice;
    }

    public void setYoudunPrice(Integer youdunPrice) {
        this.youdunPrice = youdunPrice;
    }

    public Integer getYoudunCount() {
        return youdunCount;
    }

    public void setYoudunCount(Integer youdunCount) {
        this.youdunCount = youdunCount;
    }

    public BigDecimal getYoudunFee() {
        return youdunFee;
    }

    public void setYoudunFee(BigDecimal youdunFee) {
        this.youdunFee = youdunFee;
    }

    public BigDecimal getOperatorPrice() {
        return operatorPrice;
    }

    public void setOperatorPrice(BigDecimal operatorPrice) {
        this.operatorPrice = operatorPrice;
    }

    public Integer getOperatorCount() {
        return operatorCount;
    }

    public void setOperatorCount(Integer operatorCount) {
        this.operatorCount = operatorCount;
    }

    public BigDecimal getOperatorFee() {
        return operatorFee;
    }

    public void setOperatorFee(BigDecimal operatorFee) {
        this.operatorFee = operatorFee;
    }

    public BigDecimal getRiskPrice() {
        return riskPrice;
    }

    public void setRiskPrice(BigDecimal riskPrice) {
        this.riskPrice = riskPrice;
    }

    public Integer getRiskCount() {
        return riskCount;
    }

    public void setRiskCount(Integer riskCount) {
        this.riskCount = riskCount;
    }

    public BigDecimal getRiskFee() {
        return riskFee;
    }

    public void setRiskFee(BigDecimal riskFee) {
        this.riskFee = riskFee;
    }

    public Integer getRefusedCount() {
        return refusedCount;
    }

    public void setRefusedCount(Integer refusedCount) {
        this.refusedCount = refusedCount;
    }
}