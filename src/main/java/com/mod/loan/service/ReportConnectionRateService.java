package com.mod.loan.service;

import com.mod.loan.common.mapper.BaseService;
import com.mod.loan.common.model.Page;
import com.mod.loan.model.Order;
import com.mod.loan.model.OrderRecycleRecord;
import com.mod.loan.model.dto.ReportConnectionRate;

import java.util.List;
import java.util.Map;

public interface ReportConnectionRateService extends BaseService<ReportConnectionRate, Long> {

    List<Map<String, Object>> findConnectionrRateReportList(Map<String, Object> param, Page page);

    List<Map<String, Object>> exportConnectionrRateReportList(Map<String, Object> param);
}
