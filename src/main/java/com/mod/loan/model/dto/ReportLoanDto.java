package com.mod.loan.model.dto;

import lombok.Data;

/**
 * 放款统计页面列数据
 * add by changcf
 */
@Data
public class ReportLoanDto {
    /**
     * 放款时间
     */
    private String date;

    /**
     * 放款订单数
     */
    private String loanOrderCount;

    /**
     * 放款金额
     */
    private String loanMoney;

    /**
     * 展期订单数
     */
    private String extendOrderCount;

    /**
     * 新客放款订单数
     */
    private String newUserLoanOrderCount;

    /**
     * 新客借款金额
     */
    private String newUserBorrowMoney;

    /**
     * 新客订单占比
     */
    private String newUserOrderRate;

    /**
     * 老客放款订单数
     */
    private String oldUserLoanOrderCount;

    /**
     * 老客借款金额
     */
    private String oldUserBorrowMoney;

    /**
     * 老客订单占比
     */
    private String oldUserOrderRate;

    /**
     * 综合费用
     */
    private String omnibusFee;

}
