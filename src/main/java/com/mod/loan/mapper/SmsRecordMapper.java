package com.mod.loan.mapper;


import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.SmsRecord;

import java.util.Map;

public interface SmsRecordMapper extends MyBaseMapper<SmsRecord> {

    int selectCountNow(Map params);
}