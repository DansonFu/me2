package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.MytaskMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Mytask;
import com.lettucetech.me2.service.MytaskService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MytaskServiceImpl implements MytaskService {
    @Autowired
    private MytaskMapper mytaskMapper;

    private static final Logger logger = LoggerFactory.getLogger(MytaskServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.mytaskMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Mytask selectByPrimaryKey(Integer id) {
        return this.mytaskMapper.selectByPrimaryKey(id);
    }

    public List<Mytask> selectByParams(Criteria example) {
        return this.mytaskMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.mytaskMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Mytask record) {
        return this.mytaskMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Mytask record) {
        return this.mytaskMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.mytaskMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Mytask record, Criteria example) {
        return this.mytaskMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Mytask record, Criteria example) {
        return this.mytaskMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Mytask record) {
        return this.mytaskMapper.insert(record);
    }

    public int insertSelective(Mytask record) {
        return this.mytaskMapper.insertSelective(record);
    }
}