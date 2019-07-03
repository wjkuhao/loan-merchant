package com.mod.loan.controller.statistics;

import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.Page;
import com.mod.loan.common.model.RequestThread;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.model.MerchantOrigin;
import com.mod.loan.model.Order;
import com.mod.loan.service.*;
import com.mod.loan.util.ExcelUtil;
import com.mod.loan.util.TimeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "statistics")
public class StatisticsController {

    public static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private ReportOrderLoanService reportOrderLoanService;
    @Autowired
    private ReportOrderRepayService reportOrderRepayService;
    @Autowired
    private ReportPartnerEffectService reportPartnerEffectService;
    @Autowired
    private ReportRegisterOrderService reportRegisterOrderService;
    @Autowired
    private ReportConnectionRateService reportConnectionRateService;
    @Autowired
    private ReportPartnerEffectDeductionService reportPartnerEffectDeductionService;
    @Autowired
    private ReportRegisterOrderDeductionService reportRegisterOrderDeductionService;




    @RequestMapping(value = "loan_report_list")
    public ModelAndView loan_report_list(ModelAndView view) {
        view.setViewName("statistics/loan_report_list");
        return view;
    }

    @RequestMapping(value = "connection_rate_report_list")
    public ModelAndView connection_rate_report_list(ModelAndView view) {
        view.setViewName("statistics/connection_rate_report_list");
        return view;
    }

    @RequestMapping(value = "report_register_order_list")
    public ModelAndView report_register_order_list(ModelAndView view) {
        view.setViewName("statistics/report_register_order_list");
        return view;
    }

    @RequestMapping(value = "report_register_order_list_ajax")
    public ResultMessage report_register_order_list_ajax(String startTime, String endTime, Page page) {
        Map<String, Object> param = new HashMap<>();
        param.put("merchant",RequestThread.get().getMerchant());
        param.put("startTime", StringUtils.isNotEmpty(startTime) ? startTime : null);
        param.put("endTime", StringUtils.isNotEmpty(endTime) ? endTime : null);
        return new ResultMessage(ResponseEnum.M2000,reportRegisterOrderService.findReportRegisterOrderList(param,page),page);
    }

    @RequestMapping(value = "report_register_order_deduction_list_ajax")
    public ResultMessage report_register_order_deducton_list_ajax(String startTime, String endTime, Page page) {
        Map<String, Object> param = new HashMap<>();
        param.put("merchant",RequestThread.get().getMerchant());
        param.put("startTime", StringUtils.isNotEmpty(startTime) ? startTime : null);
        param.put("endTime", StringUtils.isNotEmpty(endTime) ? endTime : null);
        return new ResultMessage(ResponseEnum.M2000,reportRegisterOrderDeductionService.findReportRegisterOrderDeductionList(param,page),page);
    }

    @RequestMapping(value = "loan_report_list_ajax", method = {RequestMethod.POST})
    public ResultMessage loan_report_list_ajax(String startTime, String endTime, Page page) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("merchant", RequestThread.get().getMerchant());
        param.put("startTime", StringUtils.isNotEmpty(startTime) ? startTime : null);
        param.put("endTime", StringUtils.isNotEmpty(endTime) ? endTime : null);
        return new ResultMessage(ResponseEnum.M2000, reportOrderLoanService.findReportOrderLoanList(param, page), page);
    }

    @RequestMapping(value = "repay_report_list")
    public ModelAndView repay_report_list(ModelAndView view) {
        view.setViewName("statistics/repay_report_list");
        return view;
    }

    @RequestMapping(value = "repay_report_list_ajax", method = {RequestMethod.POST})
    public ResultMessage repay_report_list_ajax(String startTime, String endTime, Page page) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("merchant", RequestThread.get().getMerchant());
        param.put("startTime", StringUtils.isNotEmpty(startTime) ? startTime : null);
        param.put("endTime", StringUtils.isNotEmpty(endTime) ? endTime : null);
        return new ResultMessage(ResponseEnum.M2000, reportOrderRepayService.findReportOrderRepayList(param, page), page);
    }

    @RequestMapping(value = "partner_report_list")
    public ModelAndView partner_report_list(ModelAndView view) {
        view.setViewName("statistics/partner_report_list");
        return view;
    }

    @RequestMapping(value = "partner_report_deduction_list")
    public ModelAndView partner_report_deduction_list(ModelAndView view) {
        view.setViewName("statistics/partner_report_deduction_list");
        return view;
    }

    @RequestMapping(value = "partner_report_list_ajax", method = {RequestMethod.POST})
    public ResultMessage partner_report_list_ajax(String userOrigin, String startTime, String endTime, Page page) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("merchant", RequestThread.get().getMerchant());
        param.put("userOrigin", StringUtils.isNotEmpty(userOrigin) ? userOrigin : null);
        param.put("startTime", StringUtils.isNotEmpty(startTime) ? startTime : null);
        param.put("endTime", StringUtils.isNotEmpty(endTime) ? endTime : null);
        return new ResultMessage(ResponseEnum.M2000, reportPartnerEffectService.findReportPartnerEffectList(param, page), page);
    }

    //扣量查询
    @RequestMapping(value = "partner_report_deduction_list_ajax", method = {RequestMethod.POST})
    public ResultMessage partner_report_list_ajax_deduction(String userOrigin, String startTime, String endTime, Page page) {
        Map<String, Object> param = new HashMap<>();
        param.put("merchant", RequestThread.get().getMerchant());
        param.put("userOrigin", StringUtils.isNotEmpty(userOrigin) ? userOrigin : null);
        param.put("startTime", StringUtils.isNotEmpty(startTime) ? startTime : null);
        param.put("endTime", StringUtils.isNotEmpty(endTime) ? endTime : null);
        param.put("managerId", RequestThread.get().getUid());
        return new ResultMessage(ResponseEnum.M2000, reportPartnerEffectDeductionService.findReportPartnerEffectDeductionList(param, page), page);
    }

    @RequestMapping(value = "day")
    public ModelAndView main(ModelAndView view) {
        view.setViewName("statistics/day");
        return view;
    }

	@RequestMapping(value = "main_data_ajax", method = { RequestMethod.POST })
	public ResultMessage main_data_ajax(String searchTime) {
		if (StringUtils.isEmpty(searchTime)) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "请选择统计日期。");
		}
		return new ResultMessage(ResponseEnum.M2000, orderService.mainData(RequestThread.get().getMerchant(), searchTime));
	}

    @RequestMapping(value = "export_report")
    public void export_report(String reportName, String startTime, String endTime, HttpServletResponse response, String userPhone, String userOrigin, Integer status) {
        try {
            String[] title = null;
            String sheetName = null;
            String[] columns = null;
            List<Map<String, Object>> list = null;
            String downloadFileName = TimeUtils.parseTime(new Date(), TimeUtils.dateformat4);
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("merchant", RequestThread.get().getMerchant());
            param.put("startTime", StringUtils.isNotEmpty(startTime) ? startTime : null);
            param.put("endTime", StringUtils.isNotEmpty(endTime) ? endTime : null);
            param.put("status", status);
            param.put("userPhone", StringUtils.isNotEmpty(userPhone) ? userPhone : null);
            param.put("userOrigin", StringUtils.isNotEmpty(userOrigin) ? userOrigin : null);

            switch (reportName) {
                case "order_loan":
                    downloadFileName += "-借款报表";
                    // 定义excel第一行的信息
                    title = new String[]{"放款日期", "放款笔数(笔)", "放款金额(元)", "首借人数(人)", "首借金额(元)", "次新人数(人)", "次新金额(元)", "续借人数(人)", "续借金额(元)", "综合费用(元)"};
                    sheetName = "借款报表";
                    // 设置插入值的名称
                    columns = new String[]{"day_key", "arrive_cnt", "arrive_amount", "first_cnt", "first_amount", "second_cnt", "second_amount", "old_cnt", "old_amount", "total_fee"};
                    // 获取信息
                    list = reportOrderLoanService.exportReport(param);
                    break;
                case "order_repay":
                    downloadFileName += "-还款报表";
                    // 定义excel第一行的信息
                    title = new String[]{"应还日期", "应还订单", "提前还款", "正常还款", "逾期已还", "逾期中", "坏账", "应还金额", "实还金额", "放款金额/成本", "逾期费(元)", "减免金额(元)", "待还金额", "首逾率", "逾期率", "回收率1天", "回收率3天", "回收率7天", "回收率15天"};
                    sheetName = "还款报表";
                    // 设置插入值的名称
                    columns = new String[]{"day_key", "should_repay_cnt", "early_repay_cnt", "normal_repay_cnt", "overdue_repay_cnt", "overdue_cnt", "bad_cnt", "repay_amount", "real_repay_amount", "pay_amount", "overdue_fee", "reduce_money", "overdue_repay_amount", "first_overdue_rate", "overdue_rate", "overdue1_repay_cnt1", "overdue3_repay_cnt1", "overdue7_repay_cnt1", "overdue15_repay_cnt1"};
                    // 获取信息
                    list = reportOrderRepayService.exportReport(param);
                    break;
                case "order_repay_detail":
                    downloadFileName += "-还款统计(详情)";
                    // 定义excel第一行的信息
                    title = new String[]{"应还日期", "应还订单数", "实还订单数", "展期订单数占比", "全额还款订单数占比", "未还订单数", "还款率", "新客还款率", "老客还款率", "复借订单数","复借占比"};
                    sheetName = "还款统计(详情)报表";
                    // 设置插入值的名称
                    columns = new String[]{"date", "shouldRepayOrderCount", "realRepayOrderCount", "extendOrderRate", "fullRepayOrderRate", "notRepayCount", "repayRate", "newUserRepayRate", "oldUserRepayRate", "againBorrowOrderCount", "againBorrowRate"};
                    // 获取信息
                    list = reportOrderRepayService.exportReport(param);
                    break;
                case "partner_effect":
                    downloadFileName += "-渠道注册-放款统计";
                    // 定义excel第一行的信息
                    title = new String[]{"注册日期",  "注册人数",  "实名认证数","个人信息认证数", "运营商认证数", "银行卡绑定数", "申请订单数","风控通过数","下款数", "实名认证率","个人信息认证率","运营商认证率","银行卡绑定率","申请转化率","下款率","审核通过率"};
                    sheetName = "渠道注册-放款统计";
                    // 设置插入值的名称
                    columns = new String[]{"dayKey", "regCnt",  "realNameCnt","personalInfoCertiCnt", "yysCnt", "bankCnt", "orderCnt","passRiskCnt","loanSuccessCnt","realNameCertiRate","personalInfoCertiRate","yysCertiRate","bankBoundRate","regApplyTransRate","loanRate","auditPassRate"};
                    // 获取信息
                    list = reportPartnerEffectService.exportReport(param);
                    break;
                case "old_user_repay_rate":
                    downloadFileName+="-老客回款率(包括展期)统计";
                    // 定义excel第一行的信息
                    title = new String[]{"打款日期", "自然到期日", "借款金额", "老客数", "实时老客首逾率", "老客在逾率", "老客还本率", "老客在展率", "老客提前还款", "老客正常还款", "老客逾期还款", "老客逾期中", "老客提前展期", "老客正常展期", "老客逾期展期", "老客展期逾期中", "老总展期次数"};
                    sheetName = "老客回款率(包括展期)";
                    // 设置插入值的名称
                    columns = new String[]{"give_time", "nature_give_time", "borrow_money", "old_user_count", "time_old_over_rate", "old_over_now_rate", "old_principal_pay_rate", "old_defer_rate", "old_ahead_repay", "old_normal_repay", "old_over_repay", "old_over", "old_ahead_defer", "old_nature_defer", "old_over_defer", "old_defer_over", "old_total_defer_time"};
                    // 获取信息
                    list = reportOrderRepayService.exportUserRepayRate(param, "oldUserRepayRate");
                    break;
                case "new_user_repay_rate":
                    downloadFileName+="-新客回款率(包括展期)统计";
                    // 定义excel第一行的信息
                    title = new String[]{"打款日期", "自然到期日", "商户名称", "打款数", "新客数", "次新数", "老客数", "实时新客首逾率", "新客在逾率", "新客还本率", "新客在展率", "新客提前还款", "新客正常还款", "新客逾期还款", "新客逾期中", "新客提前展期", "新客正常展期", "新客逾期展期", "新客展期逾期中", "新总展期次数"};
                    sheetName = "新客回款率(包括展期)";
                    // 设置插入值的名称
                    columns = new String[]{"give_time", "nature_give_time", "merchant_name", "pay_count", "new_user_count", "two_user_count", "old_user_count", "time_new_over_rate", "new_over_now_rate", "new_principal_pay_rate", "new_defer_rate", "new_ahead_repay", "new_normal_repay", "new_over_repay", "new_over", "new_ahead_defer", "new_nature_defer", "new_over_defer", "new_defer_over", "new_total_defer_time"};
                    // 获取信息
                    list = reportOrderRepayService.exportUserRepayRate(param, "newUserRepayRate");
                    break;
                case "total_user_repay_rate":
                    downloadFileName+="-总客户回款率(包括展期)统计";
                    // 定义excel第一行的信息
                    title = new String[]{"打款日期", "自然到期日", "商户名称", "总打款", "总首逾率", "总在逾率", "总还本率", "总在展率", "新客数", "次新数", "新客首逾率", "新客在逾率", "新客还本率", "新客在展率", "老客数", "老客首逾率", "老客在逾率", "老客还本率", "老客在展率"};
                    sheetName = "总客户回款率(包括展期)";
                    // 设置插入值的名称
                    columns = new String[]{"give_time", "nature_give_time", "merchant_name", "pay_count", "time_total_over_rate", "total_over_now_rate", "total_principal_pay_rate", "total_defer_rate", "new_user_count", "two_user_count", "new_over_rate", "new_time_over_rate", "new_principal_repay_rate", "new_defer_rate", "old_user_count", "old_over_rate", "old_time_over_rate", "old_principal_repay_rate", "old_defer_rate"};
                    // 获取信息
                    list = reportOrderRepayService.exportUserRepayRate(param, "totalUserRepayRate");
                    break;

                case "connection_rate":
                    downloadFileName+="-接通率统计";
                    // 定义excel第一行的信息
                    title = new String[]{"序号","催收人姓名", "入催订单数", "接通数", "未接通数", "入催日期", "接通率"};
                    sheetName = "接通率统计";
                    // 设置插入值的名称
                    columns = new String[]{"id", "recycledName", "recycleCnt", "connectCnt", "noconnectCnt", "recycleDate", "connectRate"};
                    // 获取信息
                    list = reportConnectionRateService.exportConnectionrRateReportList(param);
                    break;
                default:
                    logger.info("无法导出不存在的报表:" + reportName);
                    return;
            }
            HSSFWorkbook workbook = new HSSFWorkbook();
            ExcelUtil.createSheet(workbook, sheetName, title, ExcelUtil.mapToArray(list, columns));
            ExcelUtil.excelExp(response, downloadFileName, workbook);
        } catch (Exception e) {
            logger.error(reportName + "报告导出异常。", e);
        }
    }


    @RequestMapping(value = "export_report_deduction")
    public void export_report_deduction(String reportName, String startTime, String endTime, HttpServletResponse response, String userPhone, String userOrigin, Integer status) {
        try {
            String[] title;
            String sheetName = null;
            String[] columns = null;
            List<Map<String, Object>> list;
            String downloadFileName = TimeUtils.parseTime(new Date(), TimeUtils.dateformat4);
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("merchant", RequestThread.get().getMerchant());
            param.put("startTime", StringUtils.isNotEmpty(startTime) ? startTime : null);
            param.put("endTime", StringUtils.isNotEmpty(endTime) ? endTime : null);
            param.put("status", status);
            param.put("userPhone", StringUtils.isNotEmpty(userPhone) ? userPhone : null);
            param.put("userOrigin", StringUtils.isNotEmpty(userOrigin) ? userOrigin : null);
            param.put("managerId", RequestThread.get().getUid());

            downloadFileName += "-渠道注册-放款统计（商户）";
            // 定义excel第一行的信息
            title = new String[]{"注册日期", "注册渠道", "注册人数(人)", "注册的登录数量(人)", "实名人数(人)", "提单人数(人)", "首借人数(人)", "首借金额(元)"};
            sheetName = "渠道统计";
            // 设置插入值的名称
            columns = new String[]{"day_key", "user_origin", "reg_cnt", "login_cnt", "real_name_cnt", "submit_order_cnt", "first_submit_cnt", "first_submit_amount"};
            // 获取信息
            list = reportPartnerEffectDeductionService.exportReport(param);


            HSSFWorkbook workbook = new HSSFWorkbook();
            ExcelUtil.createSheet(workbook, sheetName, title, ExcelUtil.mapToArray(list, columns));
            ExcelUtil.excelExp(response, downloadFileName, workbook);
        } catch (Exception e) {
            logger.error(reportName + "报告导出异常。", e);
        }
    }

    /**
     *老客回款率首页
     * */
    @RequestMapping(value = "old_user_repay_rate_list")
    public ModelAndView old_user_repay_rate_list(ModelAndView view) {
        //老客回款率
        view.setViewName("statistics/old_user_repay_rate_list");
        return view;
    }

    /**
     *新客回款率首页
     * */
    @RequestMapping(value = "new_user_repay_rate_list")
    public ModelAndView new_user_repay_rate_list(ModelAndView view) {
        //新客回款率
        view.setViewName("statistics/new_user_repay_rate_list");
        return view;
    }

    /**
     *总回款率首页
     * */
    @RequestMapping(value = "total_user_repay_rate_list")
    public ModelAndView total_user_repay_rate_list(ModelAndView view) {
        //总体回款率
        view.setViewName("statistics/total_user_repay_rate_list");
        return view;
    }

    /**
     * 用户回款率(包括展期)查询
     * */
    @RequestMapping(value = "user_repay_rate_ajax", method = {RequestMethod.POST})
    public ResultMessage user_repay_rate_ajax(String type, String userSource, String userOrigin, String startTime, String endTime, Page page) {
        Map<String, Object> param = new HashMap();
        param.put("merchant", RequestThread.get().getMerchant());
        param.put("userSource", userSource);
        param.put("userOrigin", userOrigin);
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        if("oldUserRepayRate".equals(type)){
            //老客回款率
            return new ResultMessage(ResponseEnum.M2000, reportOrderRepayService.oldUserRepayRate(param, page), page);
        }else if("newUserRepayRate".equals(type)){
            //新客回款率
            return new ResultMessage(ResponseEnum.M2000, reportOrderRepayService.newUserRepayRate(param, page), page);
        }else if("totalUserRepayRate".equals(type)){
            //总体回款率
            return new ResultMessage(ResponseEnum.M2000, reportOrderRepayService.totalUserRepayRate(param, page), page);
        }
        return new ResultMessage(ResponseEnum.M4000.getCode(), "请选择正确的回款率报表类型");
    }

    @RequestMapping(value = "connection_rate_report_list_ajax", method = {RequestMethod.POST})
    public ResultMessage connection_rate_report_list_ajax( String startTime, String endTime, Page page) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("merchant", RequestThread.get().getMerchant());
        param.put("startTime", StringUtils.isNotEmpty(startTime) ? startTime : null);
        param.put("endTime", StringUtils.isNotEmpty(endTime) ? endTime : null);
        return new ResultMessage(ResponseEnum.M2000, reportConnectionRateService.findConnectionrRateReportList(param, page), page);
    }

    /**
     * 渠道注册-放款统计(详情页面)
     */
    @RequestMapping(value = "partner_report_list_detail")
    public ModelAndView partner_report_list_detail(ModelAndView view, String date) {
        view.addObject("date", date);
        view.setViewName("statistics/partner_report_list_detail");
        return view;
    }

    /**
     * 渠道注册-放款统计(查看详情)
     * @param originName
     * @param date
     * @param page
     * @return
     */
    @RequestMapping(value = "partner_report_list_detail_ajax", method = {RequestMethod.POST})
    public ResultMessage partner_report_list_detail_ajax(String originName, String date, Page page) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("merchant", RequestThread.get().getMerchant());
        param.put("originName", StringUtils.isNotEmpty(originName) ? originName : null);
        param.put("date", StringUtils.isNotEmpty(date) ? date : null);
        return new ResultMessage(ResponseEnum.M2000, reportPartnerEffectService.findReportPartnerEffectDetailList(param, page), page);
    }

    /**
     * 渠道注册-放款统计商户(详情页面)
     */
    @RequestMapping(value = "partner_report_deduction_list_detail")
    public ModelAndView partner_report_deduction_list_detail(ModelAndView view, String date) {
        view.addObject("date", date);
        view.setViewName("statistics/partner_report_deduction_list_detail");
        return view;
    }

    /**
     * 渠道注册-放款统计商户(查看详情)
     * @param originNo
     * @param date
     * @param page
     * @return
     */
    @RequestMapping(value = "partner_report_deduction_list_detail_ajax", method = {RequestMethod.POST})
    public ResultMessage partner_report_deduction_list_detail_ajax(String originNo, String date, Page page) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("merchant", RequestThread.get().getMerchant());
        param.put("managerId", RequestThread.get().getUid());
        return new ResultMessage(ResponseEnum.M2000, reportPartnerEffectService.findReportPartnerEffectList(param, page), page);
    }

    @RequestMapping(value = "repay_report_list_detail")
    public ModelAndView repay_report_list_detail(ModelAndView view) {
        view.setViewName("statistics/repay_report_list_detail");
        return view;
    }

    @RequestMapping(value = "repay_report_list_detail_ajax", method = {RequestMethod.POST})
    public ResultMessage repay_report_list_detail_ajax(String startTime, String endTime, Page page) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("merchant", RequestThread.get().getMerchant());
        param.put("startTime", StringUtils.isNotEmpty(startTime) ? startTime : null);
        param.put("endTime", StringUtils.isNotEmpty(endTime) ? endTime : null);
        return new ResultMessage(ResponseEnum.M2000, reportOrderRepayService.findReportOrderRepayListDetail(param, page), page);
    }

    /**
     * 数据看板 系统总数据统计
     * @param view
     * @return
     */
    @RequestMapping(value = "data_view")
    public ModelAndView data_view(ModelAndView view) {
        view.setViewName("statistics/data_view");
        return view;
    }

    @RequestMapping(value = "data_view_ajax", method = { RequestMethod.POST })
    public ResultMessage data_view_ajax() {
        return new ResultMessage(ResponseEnum.M2000, orderService.dataView(RequestThread.get().getMerchant()));
    }

}
