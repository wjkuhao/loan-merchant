package com.mod.loan.model;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * tb_merchant_config_range
 * @author 
 */
@Table(name = "tb_merchant_config_range")
public class MerchantConfigRange implements Serializable {
    /**
     * 商户别名
     */
    @Id
    private String merchantAlias;

    /**
     * 借款期限最小
     */
    private Integer productDayMin;

    /**
     * 借款期限最大
     */
    private Integer productDayMax;

    /**
     * 借款金额最小
     */
    private Integer productMoneyMin;

    /**
     * 借款金额最大
     */
    private Integer productMoneyMax;

    /**
     * 综合费率最小%
     */
    private Integer totalRateMin;

    /**
     * 综合费率最大%
     */
    private Integer totalRateMax;

    /**
     * 逾期费率最小
     */
    private Integer overdueRateMin;

    /**
     * 逾期费率最大
     */
    private Integer overdueRateMax;

    /**
     * 日续期费最少
     */
    private Integer dailyDeferFeeMin;

    /**
     * 日续期费最大
     */
    private Integer dailyDeferFeeMax;

    /**
     * 日续期费率最小
     */
    private Integer dailyDeferRateMin;

    /**
     * 日续期费最大
     */
    private Integer dailyDeferRateMax;

    /**
     * 续期日额外费
     */
    private Integer dailyOtherFeeMin;

    /**
     * 续期日额外费最大
     */
    private Integer dailyOtherFeeMax;

    /**
     * 续期天数最小
     */
    private Integer deferDayMin;

    /**
     * 续期天数最大
     */
    private Integer deferDayMax;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public String getMerchantAlias() {
        return merchantAlias;
    }

    public void setMerchantAlias(String merchantAlias) {
        this.merchantAlias = merchantAlias;
    }

    public Integer getProductDayMin() {
        return productDayMin;
    }

    public void setProductDayMin(Integer productDayMin) {
        this.productDayMin = productDayMin;
    }

    public Integer getProductDayMax() {
        return productDayMax;
    }

    public void setProductDayMax(Integer productDayMax) {
        this.productDayMax = productDayMax;
    }

    public Integer getProductMoneyMin() {
        return productMoneyMin;
    }

    public void setProductMoneyMin(Integer productMoneyMin) {
        this.productMoneyMin = productMoneyMin;
    }

    public Integer getProductMoneyMax() {
        return productMoneyMax;
    }

    public void setProductMoneyMax(Integer productMoneyMax) {
        this.productMoneyMax = productMoneyMax;
    }

    public Integer getTotalRateMin() {
        return totalRateMin;
    }

    public void setTotalRateMin(Integer totalRateMin) {
        this.totalRateMin = totalRateMin;
    }

    public Integer getTotalRateMax() {
        return totalRateMax;
    }

    public void setTotalRateMax(Integer totalRateMax) {
        this.totalRateMax = totalRateMax;
    }

    public Integer getOverdueRateMin() {
        return overdueRateMin;
    }

    public void setOverdueRateMin(Integer overdueRateMin) {
        this.overdueRateMin = overdueRateMin;
    }

    public Integer getOverdueRateMax() {
        return overdueRateMax;
    }

    public void setOverdueRateMax(Integer overdueRateMax) {
        this.overdueRateMax = overdueRateMax;
    }

    public Integer getDailyDeferFeeMin() {
        return dailyDeferFeeMin;
    }

    public void setDailyDeferFeeMin(Integer dailyDeferFeeMin) {
        this.dailyDeferFeeMin = dailyDeferFeeMin;
    }

    public Integer getDailyDeferFeeMax() {
        return dailyDeferFeeMax;
    }

    public void setDailyDeferFeeMax(Integer dailyDeferFeeMax) {
        this.dailyDeferFeeMax = dailyDeferFeeMax;
    }

    public Integer getDailyDeferRateMin() {
        return dailyDeferRateMin;
    }

    public void setDailyDeferRateMin(Integer dailyDeferRateMin) {
        this.dailyDeferRateMin = dailyDeferRateMin;
    }

    public Integer getDailyDeferRateMax() {
        return dailyDeferRateMax;
    }

    public void setDailyDeferRateMax(Integer dailyDeferRateMax) {
        this.dailyDeferRateMax = dailyDeferRateMax;
    }

    public Integer getDailyOtherFeeMin() {
        return dailyOtherFeeMin;
    }

    public void setDailyOtherFeeMin(Integer dailyOtherFeeMin) {
        this.dailyOtherFeeMin = dailyOtherFeeMin;
    }

    public Integer getDailyOtherFeeMax() {
        return dailyOtherFeeMax;
    }

    public void setDailyOtherFeeMax(Integer dailyOtherFeeMax) {
        this.dailyOtherFeeMax = dailyOtherFeeMax;
    }

    public Integer getDeferDayMin() {
        return deferDayMin;
    }

    public void setDeferDayMin(Integer deferDayMin) {
        this.deferDayMin = deferDayMin;
    }

    public Integer getDeferDayMax() {
        return deferDayMax;
    }

    public void setDeferDayMax(Integer deferDayMax) {
        this.deferDayMax = deferDayMax;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}