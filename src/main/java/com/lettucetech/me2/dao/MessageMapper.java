package com.lettucetech.me2.dao;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Message;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface MessageMapper {
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
    int insert(Message record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Message record);

    /**
     * 根据条件查询记录集
     */
    List<Message> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    Message selectByPrimaryKey(Integer id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") Message record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") Message record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Message record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Message record);

	List<Message> selectByParams4Classify(Criteria example);
}