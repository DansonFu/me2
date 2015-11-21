package com.lettucetech.me2.dao;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Server;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface ServerMapper {
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
    int deleteByPrimaryKey(Integer serverId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Server record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Server record);

    /**
     * 根据条件查询记录集
     */
    List<Server> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    Server selectByPrimaryKey(Integer serverId);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") Server record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") Server record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Server record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Server record);
}