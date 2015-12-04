package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.TXtRoleMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtRole;
import com.lettucetech.me2.service.TXtRoleService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TXtRoleServiceImpl implements TXtRoleService {
	@Autowired
	private TXtRoleMapper tXtRoleMapper;

	private static final Logger logger = LoggerFactory.getLogger(TXtRoleServiceImpl.class);

	public int countByParams(Criteria example) {
		int count = this.tXtRoleMapper.countByParams(example);
		logger.debug("count: {}", count);
		return count;
	}

	public TXtRole selectByPrimaryKey(Integer roleId) {
		return this.tXtRoleMapper.selectByPrimaryKey(roleId);
	}

	public List<TXtRole> selectByParams(Criteria example) {
		return this.tXtRoleMapper.selectByParams(example);
	}

	@Override
	public List<TXtRole> advancedSearching(Criteria example) {
		return this.tXtRoleMapper.advancedSearching(example);
	}

	@Override
	public int insertSelective(TXtRole record) {
		return this.tXtRoleMapper.insertSelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer roleId) {
		return this.tXtRoleMapper.deleteByPrimaryKey(roleId);
	}

	@Override
	public int updateByPrimaryKeySelective(TXtRole record) {
		return this.tXtRoleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<TXtRole> selectAll() {
		return this.tXtRoleMapper.selectAll();
	}

	@Override
	public int advancedSearchingCount(Criteria example) {
		// TODO Auto-generated method stub
		return this.tXtRoleMapper.advancedSearchingCount(example);
	}
}