package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.SchoolMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.School;
import com.lettucetech.me2.service.SchoolService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolServiceImpl implements SchoolService {
    @Autowired
    private SchoolMapper schoolMapper;

    private static final Logger logger = LoggerFactory.getLogger(SchoolServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.schoolMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public School selectByPrimaryKey(Integer id) {
        return this.schoolMapper.selectByPrimaryKey(id);
    }

    public List<School> selectByParams(Criteria example) {
        return this.schoolMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.schoolMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(School record) {
        return this.schoolMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(School record) {
        return this.schoolMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.schoolMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(School record, Criteria example) {
        return this.schoolMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(School record, Criteria example) {
        return this.schoolMapper.updateByParams(record, example.getCondition());
    }

    public int insert(School record) {
        return this.schoolMapper.insert(record);
    }

    public int insertSelective(School record) {
        return this.schoolMapper.insertSelective(record);
    }
}