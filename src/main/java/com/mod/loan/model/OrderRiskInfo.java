package com.mod.loan.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 美象风控结果
 */
@Data
@Table(name = "tb_order_risk_info")
@NoArgsConstructor
public class OrderRiskInfo {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "risk_id")
    private String riskId;

    @Column(name = "risk_status")
    private Integer riskStatus;

    @Column(name = "risk_result")
    private String riskResult;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "user_cert_no")
    private String userCertNo;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "update_time")
    private String updateTime;

}
