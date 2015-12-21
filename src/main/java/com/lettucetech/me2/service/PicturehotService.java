package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Picturehot;
import java.util.List;

public interface PicturehotService {
    int countByParams(Criteria example);

    Picturehot selectByPrimaryKey(Integer id);

    List<Picturehot> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Picturehot record);

    int updateByPrimaryKey(Picturehot record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Picturehot record, Criteria example);

    int updateByParams(Picturehot record, Criteria example);

    int insert(Picturehot record);

    int insertSelective(Picturehot record);

	List<Picturehot> selectByParams4Rand(Criteria example);
}