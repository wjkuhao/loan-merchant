package com.mod.loan.model.dto;

import lombok.Data;

/**
 * 还款统计详情页面列数据
 * add by changcf
 */
@Data
public class ReportRepayDetailDto {
    /**
     * 应还日期
     */
    private String date;

    /**
     * 应还订单数
     */
    private String shouldRepayOrderCount;

    /**
     * 实还订单数
     */
    private String realRepayOrderCount;

    /**
     * 展期订单数占比
     */
    private String extendOrderRate;

    /**
     * 全额还款订单数占比
     */
    private String fullRepayOrderRate;

    /**
     * 未还订单数
     */
    private String notRepayCount;

    /**
     * 还款率
     */
    private String repayRate;

    /**
     * 新客还款率
     */
    private String newUserRepayRate;

    /**
     * 老客还款率
     */
    private String oldUserRepayRate;

    /**
     * 复借订单数
     */
    private String againBorrowOrderCount;

    /**
     * 复借占比
     */
    private String againBorrowRate;
}
