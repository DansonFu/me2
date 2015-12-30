package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Tagshot;
import java.util.List;

public interface TagshotService {
    int countByParams(Criteria example);

    Tagshot selectByPrimaryKey(Integer id);

    List<Tagshot> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tagshot record);

    int updateByPrimaryKey(Tagshot record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Tagshot record, Criteria example);

    int updateByParams(Tagshot record, Criteria example);

    int insert(Tagshot record);

    int insertSelective(Tagshot record);
}