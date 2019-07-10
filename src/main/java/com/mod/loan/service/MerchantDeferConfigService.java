package com.mod.loan.service;

import com.mod.loan.common.mapper.BaseService;
import com.mod.loan.common.model.Page;
import com.mod.loan.model.MerchantDeferConfig;

import java.util.List;
import java.util.Map;

public interface MerchantDeferConfigService extends BaseService<MerchantDeferConfig, Integer> {
    List<Map<String, Object>> findMerchantDeferList(MerchantDeferConfig merchantDeferConfig, Page page);
}
