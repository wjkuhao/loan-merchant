package com.mod.loan.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mod.loan.common.mapper.BaseServiceImpl;
import com.mod.loan.common.model.Page;
import com.mod.loan.mapper.ReportOrderRepayMapper;
import com.mod.loan.model.ReportOrderRepay;
import com.mod.loan.service.ReportOrderRepayService;

@Service
public class ReportOrderRepayServiceImpl extends BaseServiceImpl<ReportOrderRepay, Long> implements ReportOrderRepayService {

    @Autowired
    private ReportOrderRepayMapper reportOrderRepayMapper;

    @Override
    public List<Map<String, Object>> findReportOrderRepayList(Map<String, Object> param, Page page) {
        param.put("startIndex", page.getStartIndex());
        param.put("pageSize", page.getPageSize());
        // TODO... changcf
        page.setTotalCount(reportOrderRepayMapper.reportOrderRepayCount(param));
        return reportOrderRepayMapper.findReportOrderRepayList(param);
    }

    @Override
    public List<Map<String, Object>> exportReport(Map<String, Object> param) {
        List<Map<String, Object>> list = reportOrderRepayMapper.exportReport(param);
        return list;
    }

    @Override
    public List<Map<String, Object>> oldUserRepayRate(Map<String, Object> param, Page page){
        param.put("startIndex", page.getStartIndex());
        param.put("pageSize", page.getPageSize());
        List<Map<String, Object>> list = reportOrderRepayMapper.oldUserRepayRate(param);
        page.setTotalCount(list.size());
        return getSubList(list, page.getStartIndex(), page.getPageSize());
    }

    @Override
    public List<Map<String, Object>> newUserRepayRate(Map<String, Object> param, Page page){
        param.put("startIndex", page.getStartIndex());
        param.put("pageSize", page.getPageSize());
        List<Map<String, Object>> list = reportOrderRepayMapper.newUserRepayRate(param);
        page.setTotalCount(list.size());
        return getSubList(list, page.getStartIndex(), page.getPageSize());
    }

    @Override
    public List<Map<String, Object>> totalUserRepayRate(Map<String, Object> param, Page page){
        param.put("startIndex", page.getStartIndex());
        param.put("pageSize", page.getPageSize());
        List<Map<String, Object>> list = reportOrderRepayMapper.totalUserRepayRate(param);
        page.setTotalCount(list.size());
        return getSubList(list, page.getStartIndex(), page.getPageSize());
    }

    @Override
    public List<Map<String, Object>> exportUserRepayRate(Map<String, Object> param,String type){
        List<Map<String, Object>> list = null;
        if("oldUserRepayRate".equals(type)){
            list = reportOrderRepayMapper.oldUserRepayRate(param);
        }else if("newUserRepayRate".equals(type)){
            list = reportOrderRepayMapper.newUserRepayRate(param);
        } else {
            list = reportOrderRepayMapper.totalUserRepayRate(param);
        }
        if(list == null || list.size() == 0){
            return new ArrayList<>();
        }
       return list;
    }

    @Override
    public List<Map<String, Object>> findReportOrderRepayListDetail(Map<String, Object> param, Page page) {
        param.put("startIndex", page.getStartIndex());
        param.put("pageSize", page.getPageSize());
        // TODO... changcf
        page.setTotalCount(reportOrderRepayMapper.reportOrderRepayDetailCount(param));
        return reportOrderRepayMapper.findReportOrderRepayListDetail(param);
    }

    /**
     * 获取子集合
     * */
    private List<Map<String, Object>> getSubList(List<Map<String, Object>> list, int startIndex,int pageSize){
        if(list == null || list.size() == 0){
            return new ArrayList<>();
        }
        if(startIndex >= list.size()){
            startIndex = list.size();
        }
        int endIndex = startIndex + pageSize;
        if(endIndex >= list.size()){
            endIndex = list.size();
        }
        List<Map<String, Object>> subList = list.subList(startIndex, endIndex);
        return subList;
    }
}