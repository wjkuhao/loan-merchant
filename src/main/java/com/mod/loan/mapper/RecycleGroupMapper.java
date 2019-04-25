package com.mod.loan.mapper;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.RecycleGroup;

import java.util.List;
import java.util.Map;

public interface RecycleGroupMapper extends MyBaseMapper<RecycleGroup> {

    List<Map<String, Object>> findRecycleGroupList(Map<String, Object> param);

    int recycleGroupCount(Map<String, Object> param);

}