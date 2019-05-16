package com.mod.loan.service.impl;

import com.mod.loan.common.enums.OrderEnum;
import com.mod.loan.common.mapper.BaseServiceImpl;
import com.mod.loan.mapper.OrderDeferMapper;
import com.mod.loan.model.Order;
import com.mod.loan.model.OrderDefer;
import com.mod.loan.service.OrderDeferService;
import com.mod.loan.service.OrderService;
import com.mod.loan.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("orderDeferService")
public class OrderDeferServiceImpl extends BaseServiceImpl<OrderDefer, Integer> implements OrderDeferService {

    private final OrderDeferMapper orderDeferMapper;
    private final OrderService orderService;

    @Autowired
    public OrderDeferServiceImpl(OrderDeferMapper orderDeferMapper, //
                                 OrderService orderService) {
        this.orderDeferMapper = orderDeferMapper;
        this.orderService = orderService;
    }

    @Override
    public OrderDefer findLastValidByOrderId(Long orderId) {
        return orderDeferMapper.findLastValidByOrderId(orderId);
    }

    @Override
    public void modifyOrderDeferByPayCallback(OrderDefer orderDefer) {
        // 修改订单的还款日期
        Order modifiedOrder = orderService.selectByPrimaryKey(orderDefer.getOrderId());
        modifiedOrder.setRepayTime(TimeUtil.parseDate(orderDefer.getDeferRepayDate()));
        modifiedOrder.setOverdueFee(new BigDecimal(0));
        modifiedOrder.setOverdueDay(0);
        modifiedOrder.setShouldRepay(modifiedOrder.getBorrowMoney());

        Integer status = modifiedOrder.getStatus();
        if (status.equals(OrderEnum.REPAYING.getCode()) || status.equals(OrderEnum.DEFER_OVERDUE.getCode())) {
            modifiedOrder.setStatus(OrderEnum.DEFER.getCode());
        } else if (status.equals(OrderEnum.OVERDUE.getCode())) {
            modifiedOrder.setStatus(OrderEnum.OVERDUE_DEFER.getCode());
        }
        orderService.updateByPrimaryKeySelective(modifiedOrder);
        // 修改续期单 支付时间和支付状态
        orderDefer.setPayTime(TimeUtil.nowTime());
        orderDeferMapper.updateByPrimaryKeySelective(orderDefer);
    }

    @Override
    public OrderDefer selectByPayNo(String payNo) {
        return orderDeferMapper.selectByPayNo(payNo);
    }

}
