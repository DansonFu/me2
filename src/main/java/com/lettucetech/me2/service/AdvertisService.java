package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Advertis;
import com.lettucetech.me2.pojo.Criteria;
import java.util.List;

public interface AdvertisService {
    int countByParams(Criteria example);

    Advertis selectByPrimaryKey(Integer id);

    List<Advertis> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Advertis record);

    int updateByPrimaryKey(Advertis record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Advertis record, Criteria example);

    int updateByParams(Advertis record, Criteria example);

    int insert(Advertis record);

    int insertSelective(Advertis record);
}