package com.lettucetech.me2.dao;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Picturerecommend;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface PicturerecommendMapper {
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
    int insert(Picturerecommend record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Picturerecommend record);

    /**
     * 根据条件查询记录集
     */
    List<Picturerecommend> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    Picturerecommend selectByPrimaryKey(Integer id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") Picturerecommend record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") Picturerecommend record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Picturerecommend record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Picturerecommend record);

	List<Picturerecommend> selectByParams4Hot(Criteria example);
}