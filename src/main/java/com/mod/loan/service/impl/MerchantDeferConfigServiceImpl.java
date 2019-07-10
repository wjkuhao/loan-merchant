package com.mod.loan.service.impl;

import com.mod.loan.common.mapper.BaseServiceImpl;
import com.mod.loan.common.model.Page;
import com.mod.loan.mapper.MerchantDeferConfigMapper;
import com.mod.loan.model.MerchantDeferConfig;
import com.mod.loan.service.MerchantDeferConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("merchantDeferConfigService")
public class MerchantDeferConfigServiceImpl extends BaseServiceImpl<MerchantDeferConfig, Integer> implements MerchantDeferConfigService {
   @Resource
   private MerchantDeferConfigMapper merchantDeferConfigMapper;
    @Override
    public List<Map<String, Object>> findMerchantDeferList(MerchantDeferConfig merchantDeferConfig, Page page) {
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("merchant", merchantDeferConfig.getMerchant());

        param.put("startIndex", page.getStartIndex());
        param.put("pageSize", page.getPageSize());
        page.setTotalCount(merchantDeferConfigMapper.merchantDeferCount(param));
        return merchantDeferConfigMapper.findMerchantDeferList(param);
    }
}
