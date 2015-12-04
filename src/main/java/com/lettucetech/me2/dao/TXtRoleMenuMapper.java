package com.lettucetech.me2.dao;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtRoleMenu;
import com.lettucetech.me2.pojo.TXtRoleUser;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TXtRoleMenuMapper {
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
    int deleteByPrimaryKey(Integer roleMenuId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(TXtRoleMenu record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(TXtRoleMenu record);

    /**
     * 根据条件查询记录集
     */
    List<TXtRoleMenu> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    TXtRoleMenu selectByPrimaryKey(Integer roleMenuId);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") TXtRoleMenu record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") TXtRoleMenu record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(TXtRoleMenu record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(TXtRoleMenu record);
    
    /**
     * 高级查询
     */
    List<TXtRoleMenu> advancedSearching(Criteria example);
    
    List<TXtRoleMenu> selectMId(@Param("roleUserList")List<TXtRoleUser> roleUserList);
}