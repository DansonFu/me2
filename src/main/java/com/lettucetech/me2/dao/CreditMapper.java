package com.lettucetech.me2.dao;

import com.lettucetech.me2.pojo.Credit;
import com.lettucetech.me2.pojo.Criteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface CreditMapper {
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
    int deleteByPrimaryKey(Integer creditId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Credit record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Credit record);

    /**
     * 根据条件查询记录集
     */
    List<Credit> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    Credit selectByPrimaryKey(Integer creditId);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") Credit record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") Credit record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Credit record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Credit record);
}