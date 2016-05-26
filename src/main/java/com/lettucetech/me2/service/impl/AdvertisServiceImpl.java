package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.AdvertisMapper;
import com.lettucetech.me2.pojo.Advertis;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.service.AdvertisService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvertisServiceImpl implements AdvertisService {
    @Autowired
    private AdvertisMapper advertisMapper;

    private static final Logger logger = LoggerFactory.getLogger(AdvertisServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.advertisMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Advertis selectByPrimaryKey(Integer id) {
        return this.advertisMapper.selectByPrimaryKey(id);
    }

    public List<Advertis> selectByParams(Criteria example) {
        return this.advertisMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.advertisMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Advertis record) {
        return this.advertisMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Advertis record) {
        return this.advertisMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.advertisMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Advertis record, Criteria example) {
        return this.advertisMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Advertis record, Criteria example) {
        return this.advertisMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Advertis record) {
        return this.advertisMapper.insert(record);
    }

    public int insertSelective(Advertis record) {
        return this.advertisMapper.insertSelective(record);
    }
}