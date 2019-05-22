package com.mod.loan.model;

import javax.persistence.*;

/**
 * 续期订单表
 *
 * @author kibear
 * @since 1.8
 */
@Table(name = "tb_order_defer")
public class OrderDefer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_phone")
    private String userPhone;
    @Column(name = "overdue_day")
    private Integer overdueDay;
    @Column(name = "overdue_fee")
    private Double overdueFee;
    @Column(name = "reduce_fee")
    private Double reduceFee;
    @Column(name = "defer_day")
    private Integer deferDay;
    @Column(name = "daily_defer_fee")
    private Double dailyDeferFee;
    @Column(name = "defer_fee")
    private Double deferFee;
    @Column(name = "defer_times")
    private Integer deferTimes;
    @Column(name = "defer_total_fee")
    private Double deferTotalFee;
    @Column(name = "pay_type")
    private String payType;
    @Column(name = "pay_no")
    private String payNo;
    @Column(name = "pay_status")
    private Integer payStatus;
    @Column(name = "pay_time")
    private String payTime;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "repay_date")
    private String repayDate;
    @Column(name = "defer_repay_date")
    private String deferRepayDate;
    @Column(name = "remark")
    private String remark;

    private String merchant;
    private Long uid;

    public OrderDefer() {
    }

    public OrderDefer(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getOverdueDay() {
        return overdueDay;
    }

    public void setOverdueDay(Integer overdueDay) {
        this.overdueDay = overdueDay;
    }

    public Double getOverdueFee() {
        return overdueFee;
    }

    public void setOverdueFee(Double overdueFee) {
        this.overdueFee = overdueFee;
    }

    public Double getReduceFee() {
        return reduceFee;
    }

    public void setReduceFee(Double reduceFee) {
        this.reduceFee = reduceFee;
    }

    public Integer getDeferDay() {
        return deferDay;
    }

    public void setDeferDay(Integer deferDay) {
        this.deferDay = deferDay;
    }

    public Double getDailyDeferFee() {
        return dailyDeferFee;
    }

    public void setDailyDeferFee(Double dailyDeferFee) {
        this.dailyDeferFee = dailyDeferFee;
    }

    public Double getDeferFee() {
        return deferFee;
    }

    public void setDeferFee(Double deferFee) {
        this.deferFee = deferFee;
    }

    public Integer getDeferTimes() {
        return deferTimes;
    }

    public void setDeferTimes(Integer deferTimes) {
        this.deferTimes = deferTimes;
    }

    public Double getDeferTotalFee() {
        return deferTotalFee;
    }

    public void setDeferTotalFee(Double deferTotalFee) {
        this.deferTotalFee = deferTotalFee;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public String getDeferRepayDate() {
        return deferRepayDate;
    }

    public void setDeferRepayDate(String deferRepayDate) {
        this.deferRepayDate = deferRepayDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
