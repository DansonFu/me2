package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtMenu;
import com.lettucetech.me2.pojo.TXtRoleMenu;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TXtMenuService {
    int countByParams(Criteria example);

    TXtMenu selectByPrimaryKey(Integer mId);

    List<TXtMenu> selectByParams(Criteria example);

	int insert(TXtMenu menu);

	int updateByPrimaryKeySelective(TXtMenu menu);

	int deleteByPrimaryKey(Integer mId);
	List<TXtMenu> selectMenus(@Param("roleMenuList")List<TXtRoleMenu> roleMenuList);
}