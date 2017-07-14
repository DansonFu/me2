package com.lettucetech.me2.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lettucetech.me2.dao.TXtUserMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtUser;
import com.lettucetech.me2.service.TXtUserService;

@Service
public class TXtUserServiceImpl implements TXtUserService {
    @Autowired
    private TXtUserMapper tXtUserMapper;

    private static final Logger logger = LoggerFactory.getLogger(TXtUserServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.tXtUserMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public TXtUser selectByPrimaryKey(Integer userId) {
        return this.tXtUserMapper.selectByPrimaryKey(userId);
    }

    public List<TXtUser> selectByParams(Criteria example) {
        return this.tXtUserMapper.selectByParams(example);
    }

    public List<TXtUser> advancedSearching(Criteria example) {
        return this.tXtUserMapper.advancedSearching(example);
    }

    public int insertSelective(TXtUser record) {
        // TODO Auto-generated method stub
        return this.tXtUserMapper.insertSelective(record);
    }

    public int deleteByPrimaryKey(Integer userId) {
        // TODO Auto-generated method stub
        return this.tXtUserMapper.deleteByPrimaryKey(userId);
    }

    public int updateByPrimaryKeySelective(TXtUser record) {
        // TODO Auto-generated method stub
        return this.tXtUserMapper.updateByPrimaryKeySelective(record);
    }

    public List<TXtUser> selectByRoleName(String roleName) {
        return this.tXtUserMapper.selectByRoleName(roleName);
    }

    public int advancedSearchingCount(Criteria example) {
        // TODO Auto-generated method stub
        return this.tXtUserMapper.advancedSearchingCount(example);
    }

}