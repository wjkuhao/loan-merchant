package com.mod.loan.service;

import java.util.List;
import java.util.Map;

import com.mod.loan.common.mapper.BaseService;
import com.mod.loan.common.model.Page;
import com.mod.loan.model.ReportOrderRepay;

public interface ReportOrderRepayService extends BaseService<ReportOrderRepay, Long> {

	List<Map<String, Object>> findReportOrderRepayList(Map<String, Object> param, Page page);

	List<Map<String, Object>> exportReport(Map<String, Object> param);

	List<Map<String, Object>> oldUserRepayRate(Map<String, Object> param, Page page);

	List<Map<String, Object>> newUserRepayRate(Map<String, Object> param, Page page);

	List<Map<String, Object>> oldUserRepayRateDetail(Map<String, Object> param, Page page);

	List<Map<String, Object>> newUserRepayRateDetail(Map<String, Object> param, Page page);

	List<Map<String, Object>> totalUserRepayRate(Map<String, Object> param, Page page);

	List<Map<String, Object>> exportUserRepayRate(Map<String, Object> param,String type);

	List<Map<String, Object>> findReportOrderRepayListDetail(Map<String, Object> param, Page page);
}
