package com.mod.loan.service.impl;

import com.mod.loan.common.model.Page;
import com.mod.loan.mapper.MerchantFeeStatisticsMapper;
import com.mod.loan.mapper.OrderRiskInfoMapper;
import com.mod.loan.mapper.SmsRecordMapper;
import com.mod.loan.mapper.ThirdCallHistoryMapper;
import com.mod.loan.service.MerchantFeeStatisticsService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author actor
 * @date 2019/7/3 20:43
 */
@Service
public class MerchantFeeStatisticsServiceImpl implements MerchantFeeStatisticsService {
    @Resource
    private MerchantFeeStatisticsMapper merchantFeeStatisticsMapper;
    @Resource
    private SmsRecordMapper smsRecordMapper;
    @Resource
    private ThirdCallHistoryMapper thirdCallHistoryMapper;
    @Resource
    private OrderRiskInfoMapper orderRiskInfoMapper;

    @Override
    public List<Map<String, Object>> findFeeStatisticsList(String merchant, String startTime, String endTime, Page page) {
        Map<String, Object> params = new HashMap<>();
        params.put("merchant", merchant);
        if (StringUtils.isNotEmpty(startTime)) {
            params.put("startTime", DateUtil.parseYYYYMMDDDate(startTime));
        }
        if (StringUtils.isNotEmpty(endTime)) {
            params.put("endTime", DateUtil.parseYYYYMMDDDate(endTime));
        }
        params.put("startIndex", page.getStartIndex());
        params.put("pageSize", page.getPageSize());
        page.setTotalCount(merchantFeeStatisticsMapper.findFeeStatisticsCount(params));
        List<Map<String, Object>> merchantFeeStatisticsList = merchantFeeStatisticsMapper.findFeeStatisticsList(params);
        //第一页
        if (page.getStartIndex() == 0) {
            Map<String, Object> sumMerchantFeeStatistics = merchantFeeStatisticsMapper.findSumFeeStatistics(params);
            merchantFeeStatisticsList.add(0, sumMerchantFeeStatistics);
        }
        return merchantFeeStatisticsList;
    }

    @Override
    public Map<String, Integer> merchantNowStatisticsCount(String merchant,Date now) {
        Map<String,Integer> result=new HashMap<>();
        Map smsMap=new HashMap();
        smsMap.put("merchant",merchant);
        smsMap.put("now",now);
        smsMap.put("channel",1);
        result.put("sms1Count",smsRecordMapper.selectCountNow(smsMap));
        smsMap.put("channel",2);
        result.put("sms2Count",smsRecordMapper.selectCountNow(smsMap));
        Map youdunMap=new HashMap();
        youdunMap.put("merchant",merchant);
        youdunMap.put("now",now);
        youdunMap.put("code",1);
        result.put("youdunCount",thirdCallHistoryMapper.selectCountNow(youdunMap));
        youdunMap.put("code",2);
        result.put("operatorCount",thirdCallHistoryMapper.selectCountNow(youdunMap));
        Map riskMap=new HashMap();
        riskMap.put("merchant",merchant);
        riskMap.put("now",now);
        result.put("riskCount",orderRiskInfoMapper.selectCountNow(riskMap));
        riskMap.put("riskStatus",2);
        result.put("refusedCount",orderRiskInfoMapper.selectCountNow(riskMap));

        return result;
    }

}
