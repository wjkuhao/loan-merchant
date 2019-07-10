package com.mod.loan.controller.system;

import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.Page;
import com.mod.loan.common.model.RequestThread;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.config.Constant;
import com.mod.loan.model.Merchant;
import com.mod.loan.model.MerchantConfigRange;
import com.mod.loan.model.MerchantDeferConfig;
import com.mod.loan.model.MerchantRate;
import com.mod.loan.service.MerchantConfigRangeService;
import com.mod.loan.service.MerchantDeferConfigService;
import com.mod.loan.service.MerchantRateService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author actor
 * @date 2019/7/5 18:26
 */
@RequestMapping("config")
@RestController
public class ConfigController {

    @Autowired
    private MerchantDeferConfigService merchantDeferConfigService;
    @Autowired
    private MerchantRateService merchantRateService;
    @Autowired
    private MerchantConfigRangeService merchantConfigRangeService;

    @RequestMapping(value = "config_page")
    public ModelAndView config_page(ModelAndView view) {
        view.setViewName("system/config_page");
        return view;
    }

    @RequestMapping(value = "config_rate_edit")
    public ModelAndView config_rate_edit(ModelAndView view, Long id, String merchant) {
        view.addObject("merchant", merchant);
        if (id == null) {
            view.setViewName("system/config_rate_add");
        } else {
            view.addObject("id", id);
            view.setViewName("system/config_rate_edit");
        }
        return view;
    }

    @RequestMapping(value = "config_defer_edit")
    public ModelAndView config_defer_edit(ModelAndView view, String merchant) {
        view.addObject("merchant", merchant);
        view.setViewName("system/config_defer_edit");
        return view;
    }

    @RequestMapping(value = "config_rate_detail_ajax", method = {RequestMethod.POST})
    public ResultMessage config_rate_detail_ajax(Long id) {
        return new ResultMessage(ResponseEnum.M2000, merchantRateService.selectByPrimaryKey(id));
    }

    /**
     * 商户费率查看
     *
     * @author actor
     * @date 2019/7/5 19:26
     */
    @RequestMapping(value = "/config_rate_list_ajax", method = {RequestMethod.POST})
    public ResultMessage config_rate_list_ajax(MerchantRate merchantRate, Page page) {
        merchantRate.setMerchant(RequestThread.get().getMerchant());
        return new ResultMessage(ResponseEnum.M2000, merchantRateService.findMerchantRateList(merchantRate, page), page);
    }

    /**
     * 借款参数配置
     *
     * @author actor
     * @date 2019/7/6 12:40
     */
    @RequestMapping(value = "config_rate_edit_ajax", method = {RequestMethod.POST})
    public ResultMessage config_rate_edit_ajax(MerchantRate merchantRate) {
        merchantRate.setMerchant(RequestThread.get().getMerchant());
        merchantRate.setProductName(RequestThread.get().getMerchant());

        if (merchantRate.getProductDay() == null || merchantRate.getProductDay() < 0) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "请重新输入产品期限");
        }
        if (merchantRate.getProductMoney() == null
                || new BigDecimal(Constant.MERCHANT_MAX_PRODUCT_MONEY).compareTo(merchantRate.getProductMoney()) < 0) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "请重新输入借款金额");
        }
        if (merchantRate.getProductLevel() == null) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "优先级不能为空");
        }
        if (merchantRate.getTotalRate() == null) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "请重新输入综合费率");
        }
        if (merchantRate.getOverdueRate() == null) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "请重新输入逾期费率");
        }
        if (merchantRate.getBorrowType() == null
                || (merchantRate.getBorrowType() > 4 && merchantRate.getBorrowType() != 99)) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "请重新输入借款次数");
        }

        MerchantConfigRange merchantConfigRange = merchantConfigRangeService.selectByPrimaryKey(RequestThread.get().getMerchant());
        if (merchantConfigRange != null) {
            if (merchantConfigRange.getProductDayMax() != null && merchantRate.getProductDay() > merchantConfigRange.getProductDayMax()) {
                return new ResultMessage(ResponseEnum.M4000.getCode(), "产品期限太大");
            }
            if (merchantConfigRange.getProductDayMin() != null && merchantRate.getProductDay() < merchantConfigRange.getProductDayMin()) {
                return new ResultMessage(ResponseEnum.M4000.getCode(), "产品期限太小");
            }
            if (merchantConfigRange.getProductMoneyMax() != null && merchantRate.getProductMoney().compareTo(new BigDecimal(merchantConfigRange.getProductMoneyMax())) > 0) {
                return new ResultMessage(ResponseEnum.M4000.getCode(), "借款金额太大");
            }
            if (merchantConfigRange.getProductMoneyMin() != null && merchantRate.getProductMoney().compareTo(new BigDecimal(merchantConfigRange.getProductMoneyMin())) < 0) {
                return new ResultMessage(ResponseEnum.M4000.getCode(), "借款金额太小");
            }
            if (merchantConfigRange.getTotalRateMax() != null && merchantRate.getTotalRate().compareTo(new BigDecimal(merchantConfigRange.getTotalRateMax())) > 0) {
                return new ResultMessage(ResponseEnum.M4000.getCode(), "综合费率太大");
            }
            if (merchantConfigRange.getTotalRateMin() != null && merchantRate.getTotalRate().compareTo(new BigDecimal(merchantConfigRange.getTotalRateMin())) > 0) {
                return new ResultMessage(ResponseEnum.M4000.getCode(), "综合费率太小");
            }
        }

        if (merchantRate.getId() == null) {
            merchantRate.setCreateTime(new Date());
            merchantRateService.insertSelective(merchantRate);
        } else if (!merchantRateService.selectByPrimaryKey(merchantRate.getId()).getMerchant().equals(merchantRate.getMerchant())) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "请重新修改");
        } else {
            merchantRate.setUpdateTime(new Date());
            merchantRateService.updateByPrimaryKeySelective(merchantRate);
        }
        return new ResultMessage(ResponseEnum.M2000);
    }

    /**
     * 商户展期查看
     *
     * @author actor
     * @date 2019/7/5 19:26
     */
    @RequestMapping(value = "/config_defer_ajax", method = {RequestMethod.POST})
    public ResultMessage config_defer_ajax(Page page) {
        String merchant = RequestThread.get().getMerchant();
        MerchantDeferConfig merchantDeferConfig = new MerchantDeferConfig();
        merchantDeferConfig.setMerchant(merchant);
        return new ResultMessage(ResponseEnum.M2000, merchantDeferConfigService.findMerchantDeferList(merchantDeferConfig, page), page);
    }


    /**
     * 续期配置保存
     */
    @RequestMapping(value = "config_defer_edit_ajax")
    public ResultMessage edit_config_defer_ajax(MerchantDeferConfig deferConfig) {
        String merchant = RequestThread.get().getMerchant();
        deferConfig.setMerchant(merchant);
        if (deferConfig.getDailyDeferFee() == null || deferConfig.getDailyDeferFee() == 0) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "日续期费不能为空");
        }
        if (deferConfig.getDailyDeferRate() == null || deferConfig.getDailyDeferRate() == 0) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "日续期费率不能为空");
        }
        MerchantConfigRange merchantConfigRange = merchantConfigRangeService.selectByPrimaryKey(RequestThread.get().getMerchant());
        if (merchantConfigRange != null) {
            if (merchantConfigRange.getDailyDeferFeeMax() != null && deferConfig.getDailyDeferFee() > merchantConfigRange.getDailyDeferFeeMax().doubleValue()) {
                return new ResultMessage(ResponseEnum.M4000.getCode(), "日续期费太大");
            }
            if (merchantConfigRange.getDailyDeferFeeMin() != null && deferConfig.getDailyDeferFee() < merchantConfigRange.getDailyDeferFeeMin().doubleValue()) {
                return new ResultMessage(ResponseEnum.M4000.getCode(), "日续期费太小");
            }
            if (merchantConfigRange.getDailyDeferRateMax() != null && deferConfig.getDailyDeferRate() > merchantConfigRange.getDailyDeferRateMax().doubleValue()) {
                return new ResultMessage(ResponseEnum.M4000.getCode(), "日续期费太大");
            }
            if (merchantConfigRange.getDailyDeferRateMin() != null && deferConfig.getDailyDeferRate() < merchantConfigRange.getDailyDeferRateMin().doubleValue()) {
                return new ResultMessage(ResponseEnum.M4000.getCode(), "日续期费太小");
            }
            if (merchantConfigRange.getDeferDayMax() != null && deferConfig.getDeferDay() != null && deferConfig.getDeferDay() > merchantConfigRange.getDeferDayMax().doubleValue()) {
                return new ResultMessage(ResponseEnum.M4000.getCode(), "续期天数太大");
            }
            if (merchantConfigRange.getDeferDayMin() != null && deferConfig.getDeferDay() != null && deferConfig.getDeferDay() < merchantConfigRange.getDeferDayMin().doubleValue()) {
                return new ResultMessage(ResponseEnum.M4000.getCode(), "续期天数太小");
            }
            if (merchantConfigRange.getDailyOtherFeeMax() != null && deferConfig.getDailyOtherFee() != null && deferConfig.getDailyOtherFee() > merchantConfigRange.getDailyOtherFeeMax().doubleValue()) {
                return new ResultMessage(ResponseEnum.M4000.getCode(), "续期日额外费太大");
            }
            if (merchantConfigRange.getDailyOtherFeeMin() != null && deferConfig.getDailyOtherFee() != null && deferConfig.getDailyOtherFee() < merchantConfigRange.getDailyOtherFeeMin().doubleValue()) {
                return new ResultMessage(ResponseEnum.M4000.getCode(), "续期日额外费太大");
            }
        }
        if (deferConfig.getId() != null) {
            merchantDeferConfigService.updateByPrimaryKey(deferConfig);
        } else {
            MerchantDeferConfig merchantDeferConfig = new MerchantDeferConfig();
            merchantDeferConfig.setMerchant(merchant);
            List<MerchantDeferConfig> merchantDeferConfigs = merchantDeferConfigService.select(merchantDeferConfig);
            if (CollectionUtils.isEmpty(merchantDeferConfigs)) {
                merchantDeferConfigService.insert(deferConfig);
            } else {
                deferConfig.setId(merchantDeferConfigs.get(0).getId());
                merchantDeferConfigService.updateByPrimaryKey(deferConfig);
            }
        }
        return new ResultMessage(ResponseEnum.M2000);
    }

    /**
     * 查询续期配置详情
     */
    @RequestMapping(value = "config_defer_detail_ajax", method = {RequestMethod.POST})
    public ResultMessage config_defer_detail_ajax() {
        MerchantDeferConfig merchantDeferConfig = new MerchantDeferConfig();
        merchantDeferConfig.setMerchant(RequestThread.get().getMerchant());
        return new ResultMessage(ResponseEnum.M2000, merchantDeferConfigService.selectOne(merchantDeferConfig));
    }

}
