package com.mod.loan.controller.order;

import com.mod.loan.common.enums.OrderRepayStatusEnum;
import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.RequestThread;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.model.MerchantDeferConfig;
import com.mod.loan.model.Order;
import com.mod.loan.model.OrderDefer;
import com.mod.loan.model.User;
import com.mod.loan.service.MerchantDeferConfigService;
import com.mod.loan.service.OrderDeferService;
import com.mod.loan.service.OrderService;
import com.mod.loan.service.UserService;
import com.mod.loan.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/order")
public class OrderDeferController {

    private final OrderService orderService;
    private final OrderDeferService orderDeferService;
    private final MerchantDeferConfigService merchantDeferConfigService;
    private final UserService userService;

    @Autowired
    public OrderDeferController(OrderService orderService, //
                                OrderDeferService orderDeferService, //
                                MerchantDeferConfigService merchantDeferConfigService, //
                                UserService userService) {
        this.orderService = orderService;
        this.orderDeferService = orderDeferService;
        this.merchantDeferConfigService = merchantDeferConfigService;
        this.userService = userService;
    }

    @RequestMapping("/order_defer_for_offline_list")
    public ModelAndView order_defer_for_offline_list(ModelAndView view) {
        view.setViewName("order/order_defer_for_offline_list");
        return view;
    }

    @RequestMapping("/order_defer_for_offline")
    public ModelAndView order_defer_for_offline(ModelAndView view, Long id) {
        view.addObject("id", id);
        view.setViewName("order/order_defer_for_offline");
        return view;
    }

    @RequestMapping("/order_defer_for_offline_compute")
    public ResultMessage compute(@RequestParam(name = "id") Long orderId,
                                 @RequestParam(defaultValue = "7") Integer deferDay) {
        //
        String merchant = RequestThread.get().getMerchant();
        MerchantDeferConfig condition = new MerchantDeferConfig();
        condition.setMerchant(merchant);
        MerchantDeferConfig merchantDeferConfig = merchantDeferConfigService.selectOne(condition);
        // 检查商户是否支持续期
        if (null == merchantDeferConfig || merchantDeferConfig.getStatus() < 1) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "商户不支持续期");
        }
        // 续期天数: 如果配置了有效的续期天数 使用配置的续期天数; 否则使用默认的续期天数
        if (null != merchantDeferConfig.getDeferDay() && merchantDeferConfig.getDeferDay() > 0) {
            deferDay = merchantDeferConfig.getDeferDay();
        }
        // 计算当前第几次续期
        int deferTimes = orderDeferService.selectCount(new OrderDefer(orderId)) + 1;// 当前续期个数加1
        if (null != merchantDeferConfig.getMaxDeferTimes() //
                && merchantDeferConfig.getMaxDeferTimes() > 0 //
                && deferTimes > merchantDeferConfig.getMaxDeferTimes()) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "续期次数达到最大限制");
        }
        // 计算续期费和还款时间
        Double dailyDeferFee = merchantDeferConfig.getDailyDeferFee();
        Double dailyDeferRate = merchantDeferConfig.getDailyDeferRate();
        Order order = orderService.selectByPrimaryKey(orderId);
        if (null != dailyDeferRate && dailyDeferRate > 0D) {
            // 如果设置了续期费率
            dailyDeferFee = order.getBorrowMoney().doubleValue() * dailyDeferRate / 100D;
        }
        double deferFee = dailyDeferFee * deferDay;
        // 计算逾期费
        Integer overdueDay = null == order.getOverdueDay() ? 0 : order.getOverdueDay();// 逾期天数
        Double overdueFee = null == order.getOverdueFee() ? 0.0D : order.getOverdueFee().doubleValue();// 逾期费
        // 计算还款时间
        String deferRepayDate = TimeUtil.datePlusDays(order.getRepayTime(), deferDay + overdueDay);
        // 计算总续期费
        Double deferTotalFee = deferFee + overdueFee;
        // 如果是null 或者 为支付成功 则可以继续生成下一笔展期订单
        OrderDefer orderDeferOld = orderDeferService.findLastValidByOrderId(orderId);
        if (orderDeferOld == null || orderDeferOld.getPayStatus().equals(OrderRepayStatusEnum.REPAY_SUCCESS.getCode())) {
            OrderDefer orderDefer = new OrderDefer();
            orderDefer.setOrderId(orderId);
            orderDefer.setDeferTimes(deferTimes);
            orderDefer.setDeferDay(deferDay);
            orderDefer.setDailyDeferFee(dailyDeferFee);
            orderDefer.setDeferFee(deferFee);
            orderDefer.setRepayDate(TimeUtil.dateFormat(order.getRepayTime()));
            orderDefer.setDeferRepayDate(deferRepayDate);
            orderDefer.setOverdueDay(overdueDay);
            orderDefer.setOverdueFee(overdueFee);
            orderDefer.setDeferTotalFee(deferTotalFee);

            User user = userService.selectByPrimaryKey(order.getUid());
            orderDefer.setUserName(user.getUserName());
            orderDefer.setUserPhone(user.getUserPhone());
            orderDefer.setCreateTime(TimeUtil.nowTime());
            orderDefer.setUid(user.getId());
            orderDefer.setMerchant(order.getMerchant());
            orderDefer.setPayStatus(OrderRepayStatusEnum.INIT.getCode());
            orderDeferService.insertSelective(orderDefer);
            return new ResultMessage(ResponseEnum.M2000.getCode(), orderDefer);
        } else {
            // 更新续期单子: 可能是用户生成了续期单 一直未支付;后面要重新按照新的费率进行计算
            orderDeferOld.setDeferDay(deferDay);
            orderDeferOld.setDailyDeferFee(dailyDeferFee);
            orderDeferOld.setDeferFee(deferFee);
            orderDeferOld.setRepayDate(TimeUtil.dateFormat(order.getRepayTime()));
            orderDeferOld.setDeferRepayDate(deferRepayDate);
            orderDeferOld.setOverdueDay(overdueDay);
            orderDeferOld.setOverdueFee(overdueFee);
            orderDeferOld.setDeferTotalFee(deferTotalFee);
            orderDeferService.updateByPrimaryKeySelective(orderDeferOld);
        }

        return new ResultMessage(ResponseEnum.M2000.getCode(), orderDeferOld);
    }

    @RequestMapping("/order_defer_for_offline_repay")
    public ResultMessage offlineRepayCallback(Long orderId, // 订单号
                                              Double repayMoney, // 线下还款金额
                                              Double reduceMoney, // 减免金额
                                              String repayCert, // 支付凭证
                                              String remark) {
        OrderDefer orderDefer = orderDeferService.findLastValidByOrderId(orderId);
        if (null == orderDefer) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "展期单不存在");
        }
        // 如果有减免金额 加上
        if (null != reduceMoney && reduceMoney >= 0.0D) {
            repayMoney += reduceMoney;
        }
        // 线下支付总额要等于续期总额
        if (!repayMoney.equals(orderDefer.getDeferTotalFee())) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "线下展期金额不对");
        }
        orderDefer.setReduceFee(reduceMoney);// 减免金额
        orderDefer.setRemark(remark);
        orderDefer.setPayNo(repayCert);
        orderDefer.setPayType("线下");
        orderDefer.setPayStatus(OrderRepayStatusEnum.REPAY_SUCCESS.getCode());
        orderDeferService.modifyOrderDeferByPayCallback(orderDefer);
        return new ResultMessage(ResponseEnum.M2000);
    }

}
