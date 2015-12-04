package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtUser;

import java.util.List;

public interface TXtUserService {
    int countByParams(Criteria example);

    TXtUser selectByPrimaryKey(Integer userId);

    List<TXtUser> selectByParams(Criteria example);
    
    List<TXtUser> advancedSearching(Criteria example);
    
    int insertSelective(TXtUser record);
    
    int deleteByPrimaryKey(Integer userId);
    
    int updateByPrimaryKeySelective(TXtUser record);

    List<TXtUser> selectByRoleName(String roleName);
    
    int advancedSearchingCount(Criteria example);
}