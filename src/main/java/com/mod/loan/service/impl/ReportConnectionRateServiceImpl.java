package com.mod.loan.service.impl;


import com.mod.loan.common.mapper.BaseServiceImpl;
import com.mod.loan.common.model.Page;
import com.mod.loan.mapper.OrderRecycleRecordMapper;
import com.mod.loan.mapper.ReportConnectionRateMapper;
import com.mod.loan.model.OrderRecycleRecord;
import com.mod.loan.model.dto.ReportConnectionRate;
import com.mod.loan.service.ReportConnectionRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ReportConnectionRateServiceImpl extends BaseServiceImpl<ReportConnectionRate, Long> implements ReportConnectionRateService {

    @Autowired
    private  ReportConnectionRateMapper reportConnectionRateMapper;


    @Override
    public List<Map<String, Object>> findConnectionrRateReportList(Map<String, Object> param, Page page) {
        param.put("startIndex", page.getStartIndex());
        param.put("pageSize", page.getPageSize());
        page.setTotalCount(reportConnectionRateMapper.findConnectionrRateReportCount(param));
        return reportConnectionRateMapper.findConnectionrRateReportList(param);
    }

    @Override
    public List<Map<String, Object>> exportConnectionrRateReportList(Map<String, Object> param) {
        //获取日期和催收人信息
        List<Map<String, Object>> reportConnectionRateList = reportConnectionRateMapper.exportRecycleDateList(param);
        Integer i=1;
        for (Map<String, Object> map : reportConnectionRateList) {
            Long connectCnt = (Long) map.get("connectCnt");
            Long noconnectCnt = (Long) map.get("noconnectCnt");
            Long totalConnectCnt= connectCnt+noconnectCnt;
            double connectRate = 0.00;
            if (totalConnectCnt!=0) {
                connectRate = (double) connectCnt / totalConnectCnt;
                BigDecimal b  = new BigDecimal(connectRate);
                connectRate= b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            map.put("connectRate",connectRate);
            map.put("id",i++);
        }
        return reportConnectionRateList;
    }
}
