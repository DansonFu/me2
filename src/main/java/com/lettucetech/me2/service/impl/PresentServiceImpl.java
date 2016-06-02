package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.PresentMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Present;
import com.lettucetech.me2.service.PresentService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PresentServiceImpl implements PresentService {
    @Autowired
    private PresentMapper presentMapper;

    private static final Logger logger = LoggerFactory.getLogger(PresentServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.presentMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Present selectByPrimaryKey(Integer id) {
        return this.presentMapper.selectByPrimaryKey(id);
    }

    public List<Present> selectByParams(Criteria example) {
        return this.presentMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.presentMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Present record) {
        return this.presentMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Present record) {
        return this.presentMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.presentMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Present record, Criteria example) {
        return this.presentMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Present record, Criteria example) {
        return this.presentMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Present record) {
        return this.presentMapper.insert(record);
    }

    public int insertSelective(Present record) {
        return this.presentMapper.insertSelective(record);
    }
}