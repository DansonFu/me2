package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.Picture1Mapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Picture1;
import com.lettucetech.me2.service.Picture1Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Picture1ServiceImpl implements Picture1Service {
    @Autowired
    private Picture1Mapper pictureMapper;

    private static final Logger logger = LoggerFactory.getLogger(Picture1ServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.pictureMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Picture1 selectByPrimaryKey(Integer pid) {
        return this.pictureMapper.selectByPrimaryKey(pid);
    }

    public List<Picture1> selectByParams(Criteria example) {
        return this.pictureMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer pid) {
        return this.pictureMapper.deleteByPrimaryKey(pid);
    }

    public int updateByPrimaryKeySelective(Picture1 record) {
        return this.pictureMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Picture1 record) {
        return this.pictureMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.pictureMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Picture1 record, Criteria example) {
        return this.pictureMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Picture1 record, Criteria example) {
        return this.pictureMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Picture1 record) {
        return this.pictureMapper.insert(record);
    }

    public int insertSelective(Picture1 record) {
        return this.pictureMapper.insertSelective(record);
    }
    

	public List<Picture1> selectByParams4Rand(Criteria example) {
		 return this.pictureMapper.selectByParams4Rand(example);
	}

	public List<Picture1> selectByParamsTagSearch(Criteria example) {
		return this.pictureMapper.selectByParamsTagSearch(example);
	}

	public List<Picture1> selectByParamsCustomerIdSearch(Criteria example) {
		return this.pictureMapper.selectByParamsCustomerIdSearch(example);
	}
}