package com.mod.loan.model.dto;

import lombok.Data;

/**
 * 还款统计页面列数据
 * add by changcf
 */
@Data
public class ReportRepayDto {

    /**
     * 应还日期
     */
    private String date;

    /**
     * 应还金额
     */
    private String shouldRepayMoney;

    /**
     * 应还订单数
     */
    private String shouldRepayOrderCount;

    /**
     * 实还金额
     */
    private String realRepayMoney;

    /**
     * 实还订单数
     */
    private String realRepayOrderCount;

    /**
     * 未还金额
     */
    private String notRepayMoney;

    /**
     * 未还订单数
     */
    private String notRepayOrderCount;

    /**
     * 还款率
     */
    private String repayRate;

}
