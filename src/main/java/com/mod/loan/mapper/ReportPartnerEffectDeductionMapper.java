package com.mod.loan.mapper;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.ReportPartnerEffectDeduction;

import java.util.List;
import java.util.Map;

public interface ReportPartnerEffectDeductionMapper extends MyBaseMapper<ReportPartnerEffectDeduction> {

	List<Map<String, Object>> findReportPartnerEffectDeductionList(Map<String, Object> param);

	int reportPartnerEffectDeductionCount(Map<String, Object> param);

	List<Map<String, Object>> exportReport(Map<String, Object> param);

	int reportPartnerEffectDeductionDetailCount(Map<String, Object> param);

	List<Map<String, Object>> findReportPartnerEffectDeductionDetailList(Map<String, Object> param);
}