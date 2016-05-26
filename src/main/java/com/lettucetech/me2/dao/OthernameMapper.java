package com.lettucetech.me2.dao;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Othername;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface OthernameMapper {
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
    int deleteByPrimaryKey(Integer id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Othername record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Othername record);

    /**
     * 根据条件查询记录集
     */
    List<Othername> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    Othername selectByPrimaryKey(Integer id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") Othername record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") Othername record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Othername record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Othername record);
}