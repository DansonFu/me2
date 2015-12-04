package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtRoleUser;

import java.util.List;

public interface TXtRoleUserService {
    int countByParams(Criteria example);

    TXtRoleUser selectByPrimaryKey(Integer roleUserId);

    List<TXtRoleUser> selectByParams(Criteria example);
    
    List<TXtRoleUser> advancedSearching(Criteria example);
    
    int insertSelective(TXtRoleUser record);
    
    int deleteByPrimaryKey(Integer roleUserId);
    
    int updateByPrimaryKeySelective(TXtRoleUser record);

	boolean saveRoleSelected(String selectedIds, String selectedId);
}