package com.mod.loan.service;

import com.mod.loan.common.mapper.BaseService;
import com.mod.loan.common.model.Page;
import com.mod.loan.model.MerchantRate;

import java.util.List;
import java.util.Map;

public interface MerchantRateService extends BaseService<MerchantRate, Long> {

	List<Map<String, Object>> findMerchantRateList(MerchantRate merchantRate, Page page);

}
