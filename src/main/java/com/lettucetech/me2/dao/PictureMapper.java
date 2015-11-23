package com.lettucetech.me2.dao;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Picture;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface PictureMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByParams(Criteria example);

    /**
     * 根据条件删除记录
     */
    int deleteByParams(Criteria example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer pid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Picture record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Picture record);

    /**
     * 根据条件查询记录集
     */
    List<Picture> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    Picture selectByPrimaryKey(Integer pid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") Picture record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") Picture record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Picture record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Picture record);
}