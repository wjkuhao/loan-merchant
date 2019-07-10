package com.mod.loan.mapper;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.MerchantRate;

import java.util.List;
import java.util.Map;

public interface MerchantRateMapper extends MyBaseMapper<MerchantRate> {
    int merchantRateCount(Map<String, Object> param);

    List<Map<String, Object>> findMerchantRateList(Map<String, Object> param);
}