package com.mod.loan.service;

import com.mod.loan.common.mapper.BaseService;
import com.mod.loan.common.model.Page;
import com.mod.loan.model.ReportPartnerEffectDeduction;

import java.util.List;
import java.util.Map;

public interface ReportPartnerEffectDeductionService extends BaseService<ReportPartnerEffectDeduction, Long> {

	List<Map<String, Object>> findReportPartnerEffectDeductionList(Map<String, Object> param, Page page);

	List<Map<String, Object>> exportReport(Map<String, Object> param);
}
