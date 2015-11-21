package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.MemberMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Member;
import com.lettucetech.me2.pojo.MemberKey;
import com.lettucetech.me2.service.MemberService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.memberMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Member selectByPrimaryKey(MemberKey key) {
        return this.memberMapper.selectByPrimaryKey(key);
    }

    public List<Member> selectByParams(Criteria example) {
        return this.memberMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(MemberKey key) {
        return this.memberMapper.deleteByPrimaryKey(key);
    }

    public int updateByPrimaryKeySelective(Member record) {
        return this.memberMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Member record) {
        return this.memberMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.memberMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Member record, Criteria example) {
        return this.memberMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Member record, Criteria example) {
        return this.memberMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Member record) {
        return this.memberMapper.insert(record);
    }

    public int insertSelective(Member record) {
        return this.memberMapper.insertSelective(record);
    }
}