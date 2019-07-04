package com.mod.loan.service.impl;

import com.mod.loan.common.mapper.BaseServiceImpl;
import com.mod.loan.common.model.Page;
import com.mod.loan.mapper.ReportPartnerEffectDeductionMapper;
import com.mod.loan.model.ReportPartnerEffectDeduction;
import com.mod.loan.model.dto.ReportOriginLoanDto;
import com.mod.loan.service.ReportPartnerEffectDeductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportPartnerEffectDeductionServiceImpl extends BaseServiceImpl<ReportPartnerEffectDeduction, Long> implements ReportPartnerEffectDeductionService {

	@Autowired
	private ReportPartnerEffectDeductionMapper reportPartnerEffectDeductionMapper;

	@Override
	public List<Map<String, Object>> findReportPartnerEffectDeductionList(Map<String, Object> param, Page page) {
		param.put("startIndex", page.getStartIndex());
		param.put("pageSize", page.getPageSize());
		page.setTotalCount(reportPartnerEffectDeductionMapper.reportPartnerEffectDeductionCount(param));
		return reportPartnerEffectDeductionMapper.findReportPartnerEffectDeductionList(param);
	}

	@Override
	public List<Map<String, Object>> exportReport(Map<String, Object> param) {
		return reportPartnerEffectDeductionMapper.exportReport(param);
	}

	@Override
	public List<Map<String, Object>> findReportPartnerEffectDeductionDetailList(Map<String, Object> param, Page page) {
		param.put("startIndex", page.getStartIndex());
		param.put("pageSize", page.getPageSize());
		page.setTotalCount(reportPartnerEffectDeductionMapper.reportPartnerEffectDeductionDetailCount(param));
		return reportPartnerEffectDeductionMapper.findReportPartnerEffectDeductionDetailList(param);
	}
}