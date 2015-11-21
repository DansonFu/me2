package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Shop;
import java.util.List;

public interface ShopService {
    int countByParams(Criteria example);

    Shop selectByPrimaryKey(Integer shopId);

    List<Shop> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer shopId);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Shop record, Criteria example);

    int updateByParams(Shop record, Criteria example);

    int insert(Shop record);

    int insertSelective(Shop record);
}