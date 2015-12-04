package com.lettucetech.me2.dao;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtRole;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TXtRoleMapper {
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
    int deleteByPrimaryKey(Integer roleId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(TXtRole record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(TXtRole record);

    /**
     * 根据条件查询记录集
     */
    List<TXtRole> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    TXtRole selectByPrimaryKey(Integer roleId);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") TXtRole record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") TXtRole record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(TXtRole record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(TXtRole record);
    
    /**
     * 高级查询
     */
    List<TXtRole> advancedSearching(Criteria example);
    
    int advancedSearchingCount(Criteria example);
    
    /**
     * 查询所有
     */
    List<TXtRole> selectAll();
}