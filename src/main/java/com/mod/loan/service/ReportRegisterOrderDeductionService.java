package com.mod.loan.service;

import com.mod.loan.common.mapper.BaseService;
import com.mod.loan.common.model.Page;
import com.mod.loan.model.ReportRegisterOrderDeduction;

import java.util.List;
import java.util.Map;

/**
 * Created by chenanle on 2018/8/28.
 */
public interface ReportRegisterOrderDeductionService extends BaseService<ReportRegisterOrderDeduction, Long> {

    List<Map<String, Object>> findReportRegisterOrderDeductionList(Map<String, Object> param, Page page);
}
