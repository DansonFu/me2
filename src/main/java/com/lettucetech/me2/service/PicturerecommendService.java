package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Picturerecommend;
import java.util.List;

public interface PicturerecommendService {
    int countByParams(Criteria example);

    Picturerecommend selectByPrimaryKey(Integer id);

    List<Picturerecommend> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Picturerecommend record);

    int updateByPrimaryKey(Picturerecommend record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Picturerecommend record, Criteria example);

    int updateByParams(Picturerecommend record, Criteria example);

    int insert(Picturerecommend record);

    int insertSelective(Picturerecommend record);

	List<Picturerecommend> selectByParams4Hot(Criteria example);
}