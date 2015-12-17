package com.lettucetech.me2.dao;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Gameface;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface GamefaceMapper {
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
    int insert(Gameface record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Gameface record);

    /**
     * 根据条件查询记录集
     */
    List<Gameface> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    Gameface selectByPrimaryKey(Integer id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") Gameface record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") Gameface record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Gameface record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Gameface record);
}