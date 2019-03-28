package com.mod.loan.service.impl;

import com.mod.loan.common.mapper.BaseServiceImpl;
import com.mod.loan.model.OrderRiskInfo;
import com.mod.loan.service.OrderRiskInfoService;
import org.springframework.stereotype.Service;

@Service("orderRiskService")
public class OrderRiskInfoServiceImpl extends BaseServiceImpl<OrderRiskInfo, Long> implements OrderRiskInfoService {

    @Override
    public OrderRiskInfo getByOrderId(Long orderId) {
        OrderRiskInfo orderRiskInfo = new OrderRiskInfo();
        orderRiskInfo.setOrderId(orderId);
        return selectOne(orderRiskInfo);
    }
}
