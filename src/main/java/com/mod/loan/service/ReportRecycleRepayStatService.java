package com.mod.loan.service;

import com.mod.loan.common.mapper.BaseService;
import com.mod.loan.model.ReportRecycleRepayStat;

import java.util.List;
import java.util.Map;

public interface ReportRecycleRepayStatService extends BaseService<ReportRecycleRepayStat, Long> {

    ReportRecycleRepayStat updateUserRecycleCnt(Long recycleUserId, int addCnt);

    List<Map<String, Object>> findRecycleRepayStatList(Map<String, Object> param);

    void decreaseNotReturnCnt(Long recycleUserId, String recycleDate);
}
