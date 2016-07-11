package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.PictureMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Picture;
import com.lettucetech.me2.service.PictureService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    private PictureMapper pictureMapper;

    private static final Logger logger = LoggerFactory.getLogger(PictureServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.pictureMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Picture selectByPrimaryKey(Integer pid) {
        return this.pictureMapper.selectByPrimaryKey(pid);
    }

    public List<Picture> selectByParams(Criteria example) {
        return this.pictureMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer pid) {
        return this.pictureMapper.deleteByPrimaryKey(pid);
    }

    public int updateByPrimaryKeySelective(Picture record) {
        return this.pictureMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Picture record) {
        return this.pictureMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.pictureMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Picture record, Criteria example) {
        return this.pictureMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Picture record, Criteria example) {
        return this.pictureMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Picture record) {
        return this.pictureMapper.insert(record);
    }

    public int insertSelective(Picture record) {
        return this.pictureMapper.insertSelective(record);
    }
    

	public List<Picture> selectByParams4Rand(Criteria example) {
		 return this.pictureMapper.selectByParams4Rand(example);
	}

	public List<Picture> selectByParamsTagSearch(Criteria example) {
		return this.pictureMapper.selectByParamsTagSearch(example);
	}

	public List<Picture> selectByParamsCustomerIdSearch(Criteria example) {
		return this.pictureMapper.selectByParamsCustomerIdSearch(example);
	}
}