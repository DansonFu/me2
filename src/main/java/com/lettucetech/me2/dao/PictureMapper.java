package com.lettucetech.me2.dao;

import java.util.List;

import com.lettucetech.me2.pojo.Picture;

public interface PictureMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Picture record);

    int insertSelective(Picture record);

    Picture selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Picture record);

    int updateByPrimaryKey(Picture record);

    List<Picture> findAll();
}