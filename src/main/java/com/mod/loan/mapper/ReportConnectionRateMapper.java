package com.mod.loan.mapper;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.Order;
import com.mod.loan.model.dto.ReportConnectionRate;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ReportConnectionRateMapper extends MyBaseMapper<ReportConnectionRate> {


	int findConnectionrRateReportCount(Map<String, Object> param);

	List<Map<String, Object>> findConnectionrRateReportList(Map<String, Object> param);

	List<Map<String, Object>> exportRecycleDateList(Map<String, Object> param);
}