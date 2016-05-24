package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Picture;

import java.util.List;

public interface PictureService {
    int countByParams(Criteria example);

    Picture selectByPrimaryKey(Integer pid);

    List<Picture> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer pid);

    int updateByPrimaryKeySelective(Picture record);

    int updateByPrimaryKey(Picture record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Picture record, Criteria example);

    int updateByParams(Picture record, Criteria example);

    int insert(Picture record);

    int insertSelective(Picture record);

	List<Picture> selectByParams4Rand(Criteria example);

	List<Picture> selectByParamsTagSearch(Criteria example);

}