package com.mod.loan.model.dto;

import lombok.Data;

/**
 * 数据看板
 * add by changcf
 */
@Data
public class ReportDataViewDto {

    /**
     * 总打款总额（元）
     */
    private String sumLoanMoney;

    /**
     * 总还款金额（元）
     */
    private String sumRepayMoney;

    /**
     * 总待收金额（元）
     */
    private String sumToCollectMoney;

    /**
     * 未逾期待收金额（元）
     */
    private String sumNotOverDueToCollectMoney;

    /**
     * 当日待收金额（元）
     */
    private String todayToCollectMoney;

    /**
     * 3日内逾期待收金额（元）
     */
    private String overDueToCollectMoney3Days;

    /**
     * 7日内逾期待收金额（元）
     */
    private String overDueToCollectMoney7Days;

    /**
     * 15日内逾期待收金额（元）
     */
    private String overDueToCollectMoney15Days;

    /**
     * 15日以上逾期待收金额（元）
     */
    private String overDueToCollectMoneyOver15Days;
}
