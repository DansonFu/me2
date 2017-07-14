package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Picture1;

import java.util.List;

public interface Picture1Service {
    int countByParams(Criteria example);

    Picture1 selectByPrimaryKey(Integer pid);

    List<Picture1> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer pid);

    int updateByPrimaryKeySelective(Picture1 record);

    int updateByPrimaryKey(Picture1 record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Picture1 record, Criteria example);

    int updateByParams(Picture1 record, Criteria example);

    int insert(Picture1 record);

    int insertSelective(Picture1 record);
 

	List<Picture1> selectByParams4Rand(Criteria example);

	List<Picture1> selectByParamsTagSearch(Criteria example);
	
	List<Picture1> selectByParamsCustomerIdSearch(Criteria example);

}