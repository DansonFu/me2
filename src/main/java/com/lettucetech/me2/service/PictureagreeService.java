package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Pictureagree;
import java.util.List;

public interface PictureagreeService {
    int countByParams(Criteria example);

    Pictureagree selectByPrimaryKey(Integer id);

    List<Pictureagree> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Pictureagree record);

    int updateByPrimaryKey(Pictureagree record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Pictureagree record, Criteria example);

    int updateByParams(Pictureagree record, Criteria example);

    int insert(Pictureagree record);

    int insertSelective(Pictureagree record);
}