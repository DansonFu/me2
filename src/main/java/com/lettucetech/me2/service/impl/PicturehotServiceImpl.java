package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.PicturehotMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Picturehot;
import com.lettucetech.me2.service.PicturehotService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PicturehotServiceImpl implements PicturehotService {
    @Autowired
    private PicturehotMapper picturehotMapper;

    private static final Logger logger = LoggerFactory.getLogger(PicturehotServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.picturehotMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Picturehot selectByPrimaryKey(Integer id) {
        return this.picturehotMapper.selectByPrimaryKey(id);
    }

    public List<Picturehot> selectByParams(Criteria example) {
        return this.picturehotMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.picturehotMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Picturehot record) {
        return this.picturehotMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Picturehot record) {
        return this.picturehotMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.picturehotMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Picturehot record, Criteria example) {
        return this.picturehotMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Picturehot record, Criteria example) {
        return this.picturehotMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Picturehot record) {
        return this.picturehotMapper.insert(record);
    }

    public int insertSelective(Picturehot record) {
        return this.picturehotMapper.insertSelective(record);
    }

	public List<Picturehot> selectByParams4Rand(Criteria example) {
		 return this.picturehotMapper.selectByParams4Rand(example);
	}
}