package com.mod.loan.service.impl;

import com.mod.loan.common.mapper.BaseServiceImpl;
import com.mod.loan.mapper.OrderRiskInfoMapper;
import com.mod.loan.model.OrderRiskInfo;
import com.mod.loan.service.OrderRiskInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("orderRiskService")
public class OrderRiskInfoServiceImpl extends BaseServiceImpl<OrderRiskInfo, Long> implements OrderRiskInfoService {

    @Autowired
    OrderRiskInfoMapper orderRiskInfoMapper;

    @Override
    public OrderRiskInfo getLastOneByOrderId(@Param("orderId") Long orderId) {
        return orderRiskInfoMapper.getLastOneByOrderId(orderId);
    }
}
