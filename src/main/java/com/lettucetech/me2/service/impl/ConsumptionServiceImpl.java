package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.ConsumptionMapper;
import com.lettucetech.me2.pojo.Consumption;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.service.ConsumptionService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumptionServiceImpl implements ConsumptionService {
    @Autowired
    private ConsumptionMapper consumptionMapper;

    private static final Logger logger = LoggerFactory.getLogger(ConsumptionServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.consumptionMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Consumption selectByPrimaryKey(Integer consumptionId) {
        return this.consumptionMapper.selectByPrimaryKey(consumptionId);
    }

    public List<Consumption> selectByParams(Criteria example) {
        return this.consumptionMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer consumptionId) {
        return this.consumptionMapper.deleteByPrimaryKey(consumptionId);
    }

    public int updateByPrimaryKeySelective(Consumption record) {
        return this.consumptionMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Consumption record) {
        return this.consumptionMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.consumptionMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Consumption record, Criteria example) {
        return this.consumptionMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Consumption record, Criteria example) {
        return this.consumptionMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Consumption record) {
        return this.consumptionMapper.insert(record);
    }

    public int insertSelective(Consumption record) {
        return this.consumptionMapper.insertSelective(record);
    }
}