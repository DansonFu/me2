package com.lettucetech.me2.dao;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Paster;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface PasterMapper {
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
    int deleteByPrimaryKey(Integer pasterId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Paster record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Paster record);

    /**
     * 根据条件查询记录集
     */
    List<Paster> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    Paster selectByPrimaryKey(Integer pasterId);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") Paster record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") Paster record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Paster record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Paster record);

	List<Paster> selectByParams4Business(Criteria example);
}