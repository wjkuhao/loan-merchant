package com.mod.loan.service.impl;

import com.mod.loan.common.mapper.BaseServiceImpl;
import com.mod.loan.common.model.Page;
import com.mod.loan.mapper.ReportRegisterOrderDeductionMapper;
import com.mod.loan.model.ReportRegisterOrderDeduction;
import com.mod.loan.service.ReportRegisterOrderDeductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by chenanle on 2018/8/28.
 */
@Service
public class ReportRegisterOrderDeductionServiceImpl extends BaseServiceImpl<ReportRegisterOrderDeduction,Long> implements ReportRegisterOrderDeductionService {

    @Autowired
    private ReportRegisterOrderDeductionMapper reportRegisterOrderDeductionMapper;

    @Override
    public List<Map<String, Object>> findReportRegisterOrderDeductionList(Map<String, Object> param, Page page) {
        param.put("startIndex",page.getStartIndex());
        param.put("pageSize",page.getPageSize());
        page.setTotalCount(reportRegisterOrderDeductionMapper.countReportRegisterOrderDeduction(param));
        return reportRegisterOrderDeductionMapper.findReportRegisterOrderDeductionList(param);
    }
}
