package com.mod.loan.mapper;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.ReportRecycleRepayStat;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ReportRecycleRepayStatMapper extends MyBaseMapper<ReportRecycleRepayStat> {

	List<ReportRecycleRepayStat> findAllEnable();

	List<Map<String, Object>> findRecycleRepayStatList(Map<String, Object> param);

	void decreaseNotReturnCnt(@Param("recycledId") Long recycledId, @Param("recycleDate") String recycleDate);
}