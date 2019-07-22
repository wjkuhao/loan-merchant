package com.mod.loan.mapper;

import java.util.List;
import java.util.Map;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.ReportOrderRepay;

public interface ReportOrderRepayMapper extends MyBaseMapper<ReportOrderRepay> {

	List<Map<String, Object>> findReportOrderRepayList(Map<String, Object> param);

	int reportOrderRepayCount(Map<String, Object> param);

	List<Map<String, Object>> exportReport(Map<String, Object> param);

	List<Map<String, Object>> oldUserRepayRate(Map<String, Object> param);

	List<Map<String, Object>> newUserRepayRate(Map<String, Object> param);

	List<Map<String, Object>> totalUserRepayRate(Map<String, Object> param);

	List<Map<String, Object>> oldUserRepayRateDetail(Map<String, Object> param);

	List<Map<String, Object>> newUserRepayRateDetail(Map<String, Object> param);

    int reportOrderRepayDetailCount(Map<String, Object> param);

	List<Map<String, Object>> findReportOrderRepayListDetail(Map<String, Object> param);
}