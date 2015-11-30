package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Paster;
import java.util.List;

public interface PasterService {
    int countByParams(Criteria example);

    Paster selectByPrimaryKey(Integer pasterId);

    List<Paster> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer pasterId);

    int updateByPrimaryKeySelective(Paster record);

    int updateByPrimaryKey(Paster record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Paster record, Criteria example);

    int updateByParams(Paster record, Criteria example);

    int insert(Paster record);

    int insertSelective(Paster record);

	List<Paster> selectByParams4Business(Criteria example);
}