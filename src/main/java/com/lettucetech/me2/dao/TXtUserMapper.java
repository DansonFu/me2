package com.lettucetech.me2.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtUser;

public interface TXtUserMapper {
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
    int deleteByPrimaryKey(Integer userId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(TXtUser record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(TXtUser record);

    /**
     * 根据条件查询记录集
     */
    List<TXtUser> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    TXtUser selectByPrimaryKey(Integer userId);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") TXtUser record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") TXtUser record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(TXtUser record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(TXtUser record);
    
    /**
     * 高级查询
     */
    List<TXtUser> advancedSearching(Criteria example);
    
    int advancedSearchingCount(Criteria example);
    
    
    List<TXtUser> selectByRoleName(String name);
}