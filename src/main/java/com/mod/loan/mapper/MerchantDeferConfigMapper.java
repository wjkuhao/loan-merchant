package com.mod.loan.mapper;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.MerchantDeferConfig;

import java.util.List;
import java.util.Map;

public interface MerchantDeferConfigMapper extends MyBaseMapper<MerchantDeferConfig> {
    int merchantDeferCount(Map<String, Object> param);

    List<Map<String, Object>> findMerchantDeferList(Map<String, Object> param);
}
