package com.mod.loan.service.impl;

import com.mod.loan.common.mapper.BaseServiceImpl;
import com.mod.loan.mapper.ReportRecycleRepayStatMapper;
import com.mod.loan.model.ReportRecycleRepayStat;
import com.mod.loan.service.ReportRecycleRepayStatService;
import com.mod.loan.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ReportRecycleRepayStatServiceImpl extends BaseServiceImpl<ReportRecycleRepayStat, Long> implements ReportRecycleRepayStatService {
	private static Logger logger = LoggerFactory.getLogger(ReportRecycleRepayStatServiceImpl.class);

	@Autowired
	ReportRecycleRepayStatMapper reportRecycleRepayStatMapper;

	@Override
	public ReportRecycleRepayStat updateUserRecycleCnt(Long recycleUserId, int addCnt) {
        ReportRecycleRepayStat reportQry = new ReportRecycleRepayStat();
        reportQry.setRecycleDate(TimeUtil.nowDate());
        reportQry.setRecycledId(recycleUserId);
        ReportRecycleRepayStat reportRecycleRepayStat = selectOne(reportQry);
        if (null==reportRecycleRepayStat) {
            return null;
        }

		reportRecycleRepayStat.setRecycleCnt(reportRecycleRepayStat.getRecycleCnt() + addCnt);
        reportRecycleRepayStat.setNotReturnCnt(reportRecycleRepayStat.getNotReturnCnt() + addCnt);
        reportRecycleRepayStat.setUpdateTime(new Date());
        updateByPrimaryKey(reportRecycleRepayStat);
        return reportRecycleRepayStat;
	}

    @Override
    public List<Map<String, Object>> findRecycleRepayStatList(Map<String,Object> param) {
        return reportRecycleRepayStatMapper.findRecycleRepayStatList(param);

    }

    @Override
    public void decreaseNotReturnCnt(Long recycleUserId, String recycleDate) {
	    reportRecycleRepayStatMapper.decreaseNotReturnCnt(recycleUserId,recycleDate);
    }

}
