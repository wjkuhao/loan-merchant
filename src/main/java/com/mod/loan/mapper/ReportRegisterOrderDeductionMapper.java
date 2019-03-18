package com.mod.loan.mapper;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.ReportRegisterOrderDeduction;

import java.util.List;
import java.util.Map;

public interface ReportRegisterOrderDeductionMapper extends MyBaseMapper<ReportRegisterOrderDeduction> {

    int countReportRegisterOrderDeduction(Map<String, Object> param);

    List<Map<String, Object>> findReportRegisterOrderDeductionList(Map<String, Object> param);
}