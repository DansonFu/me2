package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.GradeMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Grade;
import com.lettucetech.me2.service.GradeService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradeServiceImpl implements GradeService {
    @Autowired
    private GradeMapper gradeMapper;

    private static final Logger logger = LoggerFactory.getLogger(GradeServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.gradeMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Grade selectByPrimaryKey(Integer id) {
        return this.gradeMapper.selectByPrimaryKey(id);
    }

    public List<Grade> selectByParams(Criteria example) {
        return this.gradeMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.gradeMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Grade record) {
        return this.gradeMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Grade record) {
        return this.gradeMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.gradeMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Grade record, Criteria example) {
        return this.gradeMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Grade record, Criteria example) {
        return this.gradeMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Grade record) {
        return this.gradeMapper.insert(record);
    }

    public int insertSelective(Grade record) {
        return this.gradeMapper.insertSelective(record);
    }
}