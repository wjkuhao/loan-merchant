package com.mod.loan.controller.recycle;

import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.Page;
import com.mod.loan.common.model.RequestThread;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.mapper.RecycleOrderExportMapper;
import com.mod.loan.model.*;
import com.mod.loan.service.*;
import com.mod.loan.service.form.OrderQuery;
import com.mod.loan.util.ArrayUtil;
import com.mod.loan.util.TimeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "recycle")
public class RecycleController {
    private static Logger logger = LoggerFactory.getLogger(RecycleController.class);

    @Autowired
    private RecycleService recycleService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private UserService userService;

    @Autowired
    private RecycleOrderExportMapper recycleOrderExportMapper;

    @Autowired
    private RecycleUserService recycleUserService;

    @Autowired
    private ReportRecycleRepayStatService reportRecycleRepayStatService;

    /**
     * 催收分单
     *
     * @param view
     * @return
     */
    @RequestMapping(value = "recycle_list")
    public ModelAndView recycle_list(ModelAndView view) {
        view.setViewName("recycle/recycle_list");
        return view;
    }

    /**
     * 催收分配表格数据查询
     */
    @RequestMapping(value = "recycle_list_ajax")
    public ResultMessage recycle_list_ajax(String userPhone, String repayTimeDown, String repayTimeUp, Integer overdueDayDown, Integer overdueDayUp, Long followUserId, Integer orderStatus, Integer userType, String groupId, Page page) {
        OrderQuery query = new OrderQuery();
        query.setMerchant(RequestThread.get().getMerchant());
        if (StringUtils.isNotEmpty(groupId)) {
            query.setGroupId(Long.parseLong(groupId));
        } else {
            query.setOverdueDayDown(overdueDayDown);
            query.setOverdueDayUp(overdueDayUp);
        }
        query.setFollowUserId(followUserId);
        query.setUserType(userType);
        //非逾期的状态查询,逾期时间都设置为空
        if(orderStatus != 33 && orderStatus != 34 && orderStatus != 37 && orderStatus != 38){
            query.setOverdueDayUp(null);
            query.setOverdueDayDown(null);
        }
        query.setOrderStatus(orderStatus);
        if (!StringUtils.isBlank(userPhone)) {
            query.setUserPhone(userPhone);
        }
        if (!StringUtils.isBlank(repayTimeDown)) {
            query.setRepayTimeDown(repayTimeDown);
        }
        if (!StringUtils.isBlank(repayTimeUp)) {
            query.setRepayTimeUp(repayTimeUp);
        }
        return new ResultMessage(ResponseEnum.M2000, recycleService.findOverdueList(query, page), page);
    }

    /**
     * 催收组长分配
     *
     * @param view
     * @return
     */
    @RequestMapping(value = "recycle_fenpei_list")
    public ModelAndView recycle_fenpei_list(ModelAndView view) {
        RecycleUser user = recycleUserService.selectByPrimaryKey(RequestThread.get().getUid());
        if (user != null && user.getGroupId() != null) {
            view.addObject("myGroupId", user.getGroupId());
        } else {
            view.addObject("myGroupId", "");
        }
        view.setViewName("recycle/recycle_fenpei_list");
        return view;
    }

    /**
     * 催收组长分配
     *
     * @param userPhone
     * @param repayTimeDown
     * @param repayTimeUp
     * @param overdueDayDown
     * @param overdueDayUp
     * @param followUserId
     * @param orderStatus
     * @param userType
     * @param page
     * @return
     */
    @RequestMapping(value = "recycle_fenpei_list_ajax")
    public ResultMessage recycle_fenpei_list_ajax(String userPhone, String repayTimeDown, String repayTimeUp, Integer overdueDayDown, Integer overdueDayUp, Long followUserId, Integer orderStatus, Integer userType, String groupId, Page page) {
        OrderQuery query = new OrderQuery();
        query.setMerchant(RequestThread.get().getMerchant());
        if (StringUtils.isNotEmpty(groupId)) {
            query.setGroupId(Long.parseLong(groupId));
        } else {
            query.setOverdueDayDown(overdueDayDown);
            query.setOverdueDayUp(overdueDayUp);
        }
        //非逾期的状态查询,逾期时间都设置为空
        if(orderStatus != 33 && orderStatus != 34 && orderStatus != 37 && orderStatus != 38){
            query.setOverdueDayUp(null);
            query.setOverdueDayDown(null);
        }
        query.setOrderStatus(orderStatus);
        query.setUserType(userType);
        if (followUserId != null) {
            query.setFollowUserId(followUserId);
        } else {
            query.setFollowUserId(RequestThread.get().getUid());
        }

        if (!StringUtils.isBlank(userPhone)) {
            query.setUserPhone(userPhone);
        }
        if (!StringUtils.isBlank(repayTimeDown)) {
            query.setRepayTimeDown(repayTimeDown);
        }
        if (!StringUtils.isBlank(repayTimeUp)) {
            query.setRepayTimeUp(repayTimeUp);
        }
        return new ResultMessage(ResponseEnum.M2000, recycleService.findOverdueList(query, page), page);
    }

    /**
     * 催收记录
     *
     * @param view
     * @param orderId
     * @return
     */
    @RequestMapping(value = "recycle_record_list")
    public ModelAndView recycle_record_list(ModelAndView view, Long orderId) {
        view.setViewName("recycle/recycle_record_list");
        view.addObject("orderId", orderId);
        return view;
    }

    @RequestMapping(value = "recycle_record_list_ajax")
    public ResultMessage recycle_record_list_ajax(Long orderId, Long uid) {
        if (orderId == null && uid == null) {
            return new ResultMessage(ResponseEnum.M4000);
        }
        Map<String, Object> param = new HashMap<>();
        param.put("merchant", RequestThread.get().getMerchant());
        param.put("uid", uid);
        param.put("orderId", orderId);
        return new ResultMessage(ResponseEnum.M2000, recycleService.findRecycleList(param));
    }

    /**
     * 记录催收记录
     *
     * @param view
     * @param orderId
     * @return
     */
    @RequestMapping(value = "recycle_record_add")
    public ModelAndView recycle_record_add(ModelAndView view, Long orderId) {
        view.setViewName("recycle/recycle_record_add");
        view.addObject("orderId", orderId);
        return view;
    }

    @RequestMapping(value = "recycle_record_add_ajax")
    public ResultMessage recycle_record_add_ajax(Long orderId, String remark, Integer type) {
        Order order = orderService.selectByPrimaryKey(orderId);
        if (!order.getMerchant().equals(RequestThread.get().getMerchant())) {
            return new ResultMessage(ResponseEnum.M4000);
        }
        OrderRecycleRecord orderRecycle = new OrderRecycleRecord();
        orderRecycle.setCreateTime(new Date());
        orderRecycle.setFollowUserId(RequestThread.get().getUid());
        orderRecycle.setMerchant(RequestThread.get().getMerchant());
        orderRecycle.setOrderId(orderId);
        orderRecycle.setRemark(remark);
        orderRecycle.setType(type);
        orderRecycle.setUid(order.getUid());
        recycleService.insertSelective(orderRecycle);
        order.setRecycleType(type);
        orderService.updateByPrimaryKeySelective(order);
        return new ResultMessage(ResponseEnum.M2000);
    }

    /**
     * 我的催单
     *
     * @param view
     * @return
     */
    @RequestMapping(value = "my_recycle_list")
    public ModelAndView my_recycle_list(ModelAndView view) {
        view.setViewName("recycle/my_recycle_list");
        return view;
    }

    @RequestMapping(value = "my_recycle_list_ajax")
    public ResultMessage my_recycle_list_ajax(String userPhone, String repayTimeDown, String repayTimeUp, Integer overdueDayDown, Integer overdueDayUp, Integer orderStatus, Page page) {
        OrderQuery query = new OrderQuery();
        query.setMerchant(RequestThread.get().getMerchant());
        query.setOverdueDayDown(overdueDayDown);
        query.setOverdueDayUp(overdueDayUp);
        query.setFollowUserId(RequestThread.get().getUid());
        query.setOrderStatus(orderStatus);
        //非逾期的状态查询,逾期时间都设置为空
        if(orderStatus != 33 && orderStatus != 34 && orderStatus != 37 && orderStatus != 38){
            query.setOverdueDayUp(null);
            query.setOverdueDayDown(null);
        }
        if (!StringUtils.isBlank(userPhone)) {
            query.setUserPhone(userPhone);
        }
        if (!StringUtils.isBlank(repayTimeDown)) {
            query.setRepayTimeDown(repayTimeDown);
        }
        if (!StringUtils.isBlank(repayTimeUp)) {
            query.setRepayTimeUp(repayTimeUp);
        }
        return new ResultMessage(ResponseEnum.M2000, recycleService.findOverdueList(query, page), page);
    }

    @RequestMapping(value = "recycle_fenpei")
    public ResultMessage recycle_fenpei(Long followUserId, String ids) {
        Long[] longArray = ArrayUtil.toLongArray(ids, ",");
        if (longArray.length == 0) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "请选择订单");
        }
        if (followUserId == null) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "分单失败");
        }
        Manager manager = managerService.selectByPrimaryKey(followUserId);
        if (manager == null) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "分单失败");
        }
        if (!manager.getMerchant().equals(RequestThread.get().getMerchant())) {
            return new ResultMessage(ResponseEnum.M4000);
        }
        orderService.updateOrderFollowUser(followUserId, manager.getMerchant(), longArray);

        //更新入催还款报表 催收人的总数
        ReportRecycleRepayStat reportRecycleRepayStat = reportRecycleRepayStatService.updateUserRecycleCnt(followUserId, longArray.length);
        if (reportRecycleRepayStat==null){
            return new ResultMessage(ResponseEnum.M4000.getCode(), "统计数据不存在,请联系技术人员");
        }
        return new ResultMessage(ResponseEnum.M2000);
    }

    @RequestMapping(value = "all_recycle_list")
    public ModelAndView all_recycle_list(ModelAndView view) {
        view.setViewName("recycle/all_recycle_list");
        return view;
    }

    @RequestMapping(value = "all_recycle_list_ajax")
    public ResultMessage all_recycle_list_ajax(String startTime, String endTime, String userPhone, Page page) {
        OrderQuery query = new OrderQuery();
        query.setUserPhone(userPhone);
        query.setMerchant(RequestThread.get().getMerchant());
        query.setCreateTimeUp(StringUtils.isNotEmpty(startTime) ? startTime : null);
        query.setCreateTimeDown(StringUtils.isNotEmpty(endTime) ? endTime : null);
        return new ResultMessage(ResponseEnum.M2000, recycleService.findAllRecycleList(query, page), page);
    }

    @RequestMapping(value = "recycle_blacklist_edit")
    public ModelAndView recycle_blacklist_edit(ModelAndView view, Long uid) {
        User user = userService.selectByPrimaryKey(uid);
        if (user == null || !user.getMerchant().equals(RequestThread.get().getMerchant())) {
            view.addObject("message", new ResultMessage(ResponseEnum.M4004));
            view.setViewName("common/error");
        } else {
            view.addObject("user", user);
            view.setViewName("recycle/recycle_blacklist_edit");
        }
        return view;
    }

    @RequestMapping(value = "recycle_quality_list")
    public ModelAndView recycle_quality_list(ModelAndView view) {
        view.setViewName("recycle/recycle_quality_list");
        return view;
    }

    /**
     * 催单excel导出
     */
    @RequestMapping(value = "export_overdue_user_message")
    public ResultMessage export_overdue_user_message(HttpServletResponse response, String userPhone,
                                                     String repayTimeDown, String repayTimeUp, Integer overdueDayDown, Integer overdueDayUp, Long followUserId,
                                                     Integer orderStatus, Integer userType) {
        OrderQuery query = new OrderQuery();
        query.setMerchant(RequestThread.get().getMerchant());
        query.setOverdueDayDown(overdueDayDown);
        query.setOverdueDayUp(overdueDayUp);
        query.setFollowUserId(followUserId);
        query.setUserType(userType);

        query.setOrderStatus(orderStatus);
        if (!StringUtils.isBlank(userPhone)) {
            query.setUserPhone(userPhone);
        }
        if (!StringUtils.isBlank(repayTimeDown)) {
            query.setRepayTimeDown(repayTimeDown);
        }
        if (!StringUtils.isBlank(repayTimeUp)) {
            query.setRepayTimeUp(repayTimeUp);
        }
        int count = recycleService.selectOverdueUserMessageCount(query);
        if (count == 0) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "导出记录条数为零，请重新选择导出条件。");
        } else if (count > 2000) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "导出记录超过两千条，请重新选择导出条件。");
        }
        // 新增导出记录，发送消息
        recycleService.insertExportRecordsAndSendMessage(query);
        return new ResultMessage(ResponseEnum.M2000.getCode(), "任务创建成功。");
    }

    @RequestMapping(value = "recycle_download_list")
    public ModelAndView recycle_download_list(ModelAndView view) {
        view.setViewName("recycle/recycle_download_list");
        return view;
    }

    @RequestMapping(value = "recycle_download_list_ajax", method = RequestMethod.POST)
    public ResultMessage recycle_download_list_ajax() {
        Map<String, Object> param = new HashMap<>();
        param.put("merchant", RequestThread.get().getMerchant());
        return new ResultMessage(ResponseEnum.M2000, recycleService.findDownloadList(param));
    }

    @RequestMapping(value = "download_ajax")
    public void download_ajax(Long id, HttpServletResponse response) {
        RecycleOrderExport recycleOrderExport = recycleOrderExportMapper.selectByPrimaryKey(id);
        String linkurl = recycleOrderExport.getUrl();
        try {
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + new String("催收订单excel".getBytes("utf-8"), "ISO8859-1") + ".xls");
            URL url = new URL(linkurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            connection.disconnect();
            InputStream is = connection.getInputStream();
            OutputStream os = response.getOutputStream();
            int byteCount = 0;
            byte[] bytes = new byte[1024];
            while ((byteCount = is.read(bytes)) != -1) {
                os.write(bytes, 0, byteCount);
            }
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "audit_overdue_list")
    public ModelAndView audit_overdue_list(ModelAndView view) {
        view.setViewName("recycle/audit_overdue_list");
        return view;
    }

    @RequestMapping(value = "audit_overdue_list_ajax")
    public ResultMessage audit_overdue_list_ajax(Order order, String startTime, String endTime) {
        int timeDiff = TimeUtils.getTimeDiff(startTime, endTime);
        if (timeDiff > 38 || timeDiff < 0) {
            return null;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("merchant", RequestThread.get().getMerchant());
        param.put("startTime", StringUtils.isBlank(startTime) ? TimeUtils.getNowString() : startTime);
        param.put("endTime", StringUtils.isBlank(endTime) ? TimeUtils.getNowString() : endTime);
        param.put("userType", order.getUserType() != null ? order.getUserType() : null);
        return new ResultMessage(ResponseEnum.M2000, recycleService.findAuditOverdueList(param));
    }

    @RequestMapping(value = "recycle_repay_list")
    public ModelAndView recycle_repay_list(ModelAndView view) {
        view.setViewName("recycle/recycle_repay_list");
        return view;
    }

    @RequestMapping(value = "recycle_repay_list_ajax")
    public ResultMessage recycle_repay_list_ajax(Order order, String startTime, String endTime) {
        int timeDiff = TimeUtils.getTimeDiff(startTime, endTime);
        if (timeDiff > 38 || timeDiff < 0) {
            return null;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("merchant", RequestThread.get().getMerchant());
        param.put("userType", order.getUserType() != null ? order.getUserType() : null);
        param.put("startTime", StringUtils.isBlank(startTime) ? TimeUtils.getNowString() : startTime);
        param.put("endTime", StringUtils.isBlank(endTime) ? TimeUtils.getNowString() : endTime);
        return new ResultMessage(ResponseEnum.M2000, recycleService.findRecycleRepayList(param));
    }

    @RequestMapping(value = "recycle_repay_stat_list")
    public ModelAndView recycle_repay_stat_list(ModelAndView view) {
        view.setViewName("recycle/recycle_repay_stat_list");
        return view;
    }

    @RequestMapping(value = "recycle_repay_stat_list_ajax")
    public ResultMessage recycle_repay_stat_list_ajax(String startTime, String endTime) {
        int timeDiff = TimeUtils.getTimeDiff(startTime, endTime);
        if (timeDiff > 38 || timeDiff < 0) {
            return null;
        }

        Map<String, Object> param = new HashMap<>();
        param.put("merchant", RequestThread.get().getMerchant());
        param.put("startTime", StringUtils.isBlank(startTime) ? TimeUtils.getNowString() : startTime);
        param.put("endTime", StringUtils.isBlank(endTime) ? TimeUtils.getNowString() : endTime);
        return new ResultMessage(ResponseEnum.M2000, reportRecycleRepayStatService.findRecycleRepayStatList(param));
    }

    /**
     * 催收S0组菜单
     * */
    @RequestMapping(value = "recycle_fenpei_s0_list")
    public ModelAndView recycle_fenpei_s0_list(ModelAndView view) {
        view.setViewName("recycle/recycle_fenpei_s0_list");
        return view;
    }

    /**
     * 催收分配S0组查询
     * */
    @RequestMapping(value = "recycle_fenpei_s0_ajax")
    public ResultMessage recycle_fenpei_s0_ajax(String userPhone, Long followUserId, Integer orderStatus, Integer userType, Page page) {
        OrderQuery query = new OrderQuery();
        query.setMerchant(RequestThread.get().getMerchant());
        query.setUserType(userType);
        query.setOrderStatus(orderStatus);
        if(followUserId != null){
            query.setFollowUserId(followUserId);
        }
        if (!StringUtils.isBlank(userPhone)) {
            query.setUserPhone(userPhone);
        }
        //应还款时间为今天
        logger.error("repayTimeUp:{}", DateFormat.getDateInstance(DateFormat.DEFAULT).format(new Date()));
        logger.error("recycle_fenpei_s0_ajax:{}", query.toString());
        return new ResultMessage(ResponseEnum.M2000, recycleService.findS0List(query, page), page);
    }

    /**
     * 催收分配坏账账单查询
     * */
    @RequestMapping(value = "recycle_fenpei_bad_list")
    public ModelAndView recycle_fenpei_bad_list(ModelAndView view) {
        view.setViewName("recycle/recycle_fenpei_bad_list");
        return view;
    }

    /**
     * 催收分配坏账查询
     * */
    @RequestMapping(value = "recycle_fenpei_bad_ajax")
    public ResultMessage recycle_fenpei_bad_ajax(String userPhone, Long followUserId, Integer overdueDayDown, Integer overdueDayUp, Integer orderStatus, Integer userType, Page page) {
        OrderQuery query = new OrderQuery();
        query.setMerchant(RequestThread.get().getMerchant());
        query.setUserType(userType);
        query.setOrderStatus(orderStatus);
        if(followUserId != null){
            query.setFollowUserId(followUserId);
        }
        if (!StringUtils.isBlank(userPhone)) {
            query.setUserPhone(userPhone);
        }
        if(overdueDayUp != null && overdueDayUp > 0){
            query.setOverdueDayUp(overdueDayUp);
        }
        if(overdueDayDown != null && overdueDayDown > 0){
            query.setOverdueDayDown(overdueDayDown);
        }
        return new ResultMessage(ResponseEnum.M2000, recycleService.findBadList(query, page), page);
    }

}
