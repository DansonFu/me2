package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtDepartment;
import java.util.List;

public interface TXtDepartmentService {
    int countByParams(Criteria example);

    TXtDepartment selectByPrimaryKey(Integer depId);

    List<TXtDepartment> selectByParams(Criteria example);

	void updateByPrimaryKeySelective(TXtDepartment department);

	void insert(TXtDepartment department);

	void deleteByPrimaryKey(int parseInt);
	
	List<TXtDepartment> advancedSearching(Criteria example);
    
    int advancedSearchingCount(Criteria example);
}