package com.mod.loan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mod.loan.config.Constant;
import com.mod.loan.service.DataCenterService;
import com.mod.loan.util.OkHttpReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class DataCenterServiceImpl implements DataCenterService {
	private static Logger logger = LoggerFactory.getLogger(DataCenterServiceImpl.class);

	private final OkHttpReader okHttpReader;

	@Autowired
	public DataCenterServiceImpl(OkHttpReader okHttpReader) {
		this.okHttpReader = okHttpReader;
	}

	@Async
	public void delMultiLoanOrder(String merchant, Long orderId){
		try {
			JSONObject reqJson = new JSONObject();
			reqJson.put("merchant", merchant);
			reqJson.put("orderId", orderId);

			String result = okHttpReader.postJson(Constant.MULTI_LOAN_DEL_URL, reqJson.toJSONString(), null);

			JSONObject respObject = JSONObject.parseObject(result);
			String status = respObject.getString("status");
			if (!"200".equals(status)){
				logger.error("delMultiLoanOrder err={}", respObject.getString("msg"));
			}
		}catch (Exception e){
			logger.error("delMultiLoanOrder Exception merchant={} orderId={}, err={}", merchant, orderId, e);
		}
	}
}
