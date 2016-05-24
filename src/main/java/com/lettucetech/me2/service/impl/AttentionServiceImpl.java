package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.AttentionMapper;
import com.lettucetech.me2.pojo.Attention;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.service.AttentionService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttentionServiceImpl implements AttentionService {
    @Autowired
    private AttentionMapper attentionMapper;

    private static final Logger logger = LoggerFactory.getLogger(AttentionServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.attentionMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Attention selectByPrimaryKey(Integer id) {
        return this.attentionMapper.selectByPrimaryKey(id);
    }

    public List<Attention> selectByParams(Criteria example) {
        return this.attentionMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.attentionMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Attention record) {
        return this.attentionMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Attention record) {
        return this.attentionMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.attentionMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Attention record, Criteria example) {
        return this.attentionMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Attention record, Criteria example) {
        return this.attentionMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Attention record) {
        return this.attentionMapper.insert(record);
    }

    public int insertSelective(Attention record) {
        return this.attentionMapper.insertSelective(record);
    }
}