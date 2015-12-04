package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtMetoo;
import java.util.List;

public interface TXtMetooService {
    int countByParams(Criteria example);

    TXtMetoo selectByPrimaryKey(Integer id);

    List<TXtMetoo> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TXtMetoo record);

    int updateByPrimaryKey(TXtMetoo record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(TXtMetoo record, Criteria example);

    int updateByParams(TXtMetoo record, Criteria example);

    int insert(TXtMetoo record);

    int insertSelective(TXtMetoo record);
}