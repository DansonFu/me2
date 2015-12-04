package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtRole;

import java.util.List;

public interface TXtRoleService {
    int countByParams(Criteria example);

    TXtRole selectByPrimaryKey(Integer roleId);

    List<TXtRole> selectByParams(Criteria example);
    
    List<TXtRole> advancedSearching(Criteria example);
    
    int insertSelective(TXtRole record);
    
    int deleteByPrimaryKey(Integer roleId);
    
    int updateByPrimaryKeySelective(TXtRole record);
    
    List<TXtRole> selectAll();
    
    int advancedSearchingCount(Criteria example);
}