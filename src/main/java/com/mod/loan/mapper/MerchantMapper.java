package com.mod.loan.mapper;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.Merchant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MerchantMapper extends MyBaseMapper<Merchant> {

    List<Merchant> selectMerchantAliasByStatus(@Param("status") int status);

}