package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.TXtDepartmentMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtDepartment;
import com.lettucetech.me2.service.TXtDepartmentService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TXtDepartmentServiceImpl implements TXtDepartmentService {
    @Autowired
    private TXtDepartmentMapper tXtDepartmentMapper;

    private static final Logger logger = LoggerFactory.getLogger(TXtDepartmentServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.tXtDepartmentMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public TXtDepartment selectByPrimaryKey(Integer depId) {
        return this.tXtDepartmentMapper.selectByPrimaryKey(depId);
    }

    public List<TXtDepartment> selectByParams(Criteria example) {
    	return this.tXtDepartmentMapper.selectByParams(example);
    }

	public void updateByPrimaryKeySelective(TXtDepartment department) {
		this.tXtDepartmentMapper.updateByPrimaryKeySelective(department);
	}

	public void insert(TXtDepartment department) {
		this.tXtDepartmentMapper.insert(department);
	}

	public void deleteByPrimaryKey(int depId) {
		this.tXtDepartmentMapper.deleteByPrimaryKey(depId);
	}

	@Override
	public List<TXtDepartment> advancedSearching(Criteria example) {
		// TODO Auto-generated method stub
		return this.tXtDepartmentMapper.advancedSearching(example);
	}

	@Override
	public int advancedSearchingCount(Criteria example) {
		// TODO Auto-generated method stub
		return this.tXtDepartmentMapper.advancedSearchingCount(example);
	}
}