package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtRoleMenu;
import com.lettucetech.me2.pojo.TXtRoleUser;

import java.util.List;

public interface TXtRoleMenuService {
    int countByParams(Criteria example);

    TXtRoleMenu selectByPrimaryKey(Integer roleMenuId);

    List<TXtRoleMenu> selectByParams(Criteria example);
    
    List<TXtRoleMenu> advancedSearching(Criteria example);
    
    int insertSelective(TXtRoleMenu record);
    
    int deleteByPrimaryKey(Integer roleMenuId);
    
    int updateByPrimaryKeySelective(TXtRoleMenu record);
    
    int deleteByParams(Criteria example);

	boolean savePerSelected(String selectedIds, String selectedId);
	
	List<TXtRoleMenu> selectMId(List<TXtRoleUser> roleUserList);
}