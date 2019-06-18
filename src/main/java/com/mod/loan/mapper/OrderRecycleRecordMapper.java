package com.mod.loan.mapper;

import java.util.List;
import java.util.Map;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.OrderRecycleRecord;
import com.mod.loan.model.dto.ReportConnectionRate;
import com.mod.loan.service.form.OrderQuery;
import org.apache.ibatis.annotations.Param;

public interface OrderRecycleRecordMapper extends MyBaseMapper<OrderRecycleRecord> {

	List<Map<String, Object>> findOverdueList(OrderQuery query);

	List<Map<String, Object>> findAuditOverdueList(Map<String,Object> param);

	List<Map<String, Object>> findRecycleRepayList(Map<String,Object> param);

	int countOverdueList(OrderQuery query);

	List<Map<String, Object>> findRecycleList(Map<String, Object> param);

	List<Map<String, Object>> findAllRecycleList(OrderQuery query);

	int countAuditList(OrderQuery query);

	/**
	 * 催单excel导出计数
	 */
	int selectOverdueUserMessageCount(OrderQuery query);

	/**
	 * 查询S0分组列表
	 * */
	List<Map<String, Object>> findS0List(OrderQuery query);

	/**
	 * 查询S0分组列表大小
	 * */
	int countS0List(OrderQuery query);

	/**
	 * 查询坏账分组列表
	 * */
	List<Map<String, Object>> findBadList(OrderQuery query);

	/**
	 * 查询坏账分组列表大小
	 * */
	int countBadList(OrderQuery query);
}