package com.mod.loan.service;

import com.mod.loan.common.model.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MerchantFeeStatisticsService {

    List<Map<String, Object>> findFeeStatisticsList(String merchant, String startTime, String endTime, Page page);

    Map<String, Integer> merchantNowStatisticsCount(String merchant,Date now);
}
