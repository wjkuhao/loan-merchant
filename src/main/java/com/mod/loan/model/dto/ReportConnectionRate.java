package com.mod.loan.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReportConnectionRate {
    private Integer id;

    private String  recycledName;//催收人姓名

    private Integer recycleCnt;  //入催单数

    private Integer connectCnt; // 接通数

    private Integer noconnectCnt; //未接通数

    private Date recycleDate; // 入催时间

    private Double connectRate; //接通率
}
