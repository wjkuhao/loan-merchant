package com.mod.loan.mapper;


import com.mod.loan.model.MerchantFeeStatistics;

import java.util.List;
import java.util.Map;

public interface MerchantFeeStatisticsMapper {
    int deleteByPrimaryKey(MerchantFeeStatistics key);

    int insert(MerchantFeeStatistics record);

    int insertSelective(MerchantFeeStatistics record);

    MerchantFeeStatistics selectByPrimaryKey(MerchantFeeStatistics key);

    int updateByPrimaryKeySelective(MerchantFeeStatistics record);

    int updateByPrimaryKey(MerchantFeeStatistics record);

    int findFeeStatisticsCount(Map<String, Object> params);

    List<Map<String, Object>> findFeeStatisticsList(Map<String, Object> params);

    Map<String, Object> findSumFeeStatistics(Map<String, Object> params);

    int findFeeStatisticsDetailCount(Map<String, Object> params);

}