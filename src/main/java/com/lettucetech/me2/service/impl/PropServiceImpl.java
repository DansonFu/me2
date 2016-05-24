package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.PropMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Prop;
import com.lettucetech.me2.service.PropService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropServiceImpl implements PropService {
    @Autowired
    private PropMapper propMapper;

    private static final Logger logger = LoggerFactory.getLogger(PropServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.propMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Prop selectByPrimaryKey(Integer id) {
        return this.propMapper.selectByPrimaryKey(id);
    }

    public List<Prop> selectByParams(Criteria example) {
        return this.propMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.propMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Prop record) {
        return this.propMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Prop record) {
        return this.propMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.propMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Prop record, Criteria example) {
        return this.propMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Prop record, Criteria example) {
        return this.propMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Prop record) {
        return this.propMapper.insert(record);
    }

    public int insertSelective(Prop record) {
        return this.propMapper.insertSelective(record);
    }
}