package com.lettucetech.me2.dao;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtDepartment;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface TXtDepartmentMapper {
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
    int deleteByPrimaryKey(Integer depId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(TXtDepartment record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(TXtDepartment record);

    /**
     * 根据条件查询记录集
     */
    List<TXtDepartment> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    TXtDepartment selectByPrimaryKey(Integer depId);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") TXtDepartment record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") TXtDepartment record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(TXtDepartment record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(TXtDepartment record);
    
    List<TXtDepartment> advancedSearching(Criteria example);
    
    int advancedSearchingCount(Criteria example);
}