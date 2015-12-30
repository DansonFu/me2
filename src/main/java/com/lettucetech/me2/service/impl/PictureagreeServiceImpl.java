package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.PictureagreeMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Pictureagree;
import com.lettucetech.me2.service.PictureagreeService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureagreeServiceImpl implements PictureagreeService {
    @Autowired
    private PictureagreeMapper pictureagreeMapper;

    private static final Logger logger = LoggerFactory.getLogger(PictureagreeServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.pictureagreeMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Pictureagree selectByPrimaryKey(Integer id) {
        return this.pictureagreeMapper.selectByPrimaryKey(id);
    }

    public List<Pictureagree> selectByParams(Criteria example) {
        return this.pictureagreeMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.pictureagreeMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Pictureagree record) {
        return this.pictureagreeMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Pictureagree record) {
        return this.pictureagreeMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.pictureagreeMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Pictureagree record, Criteria example) {
        return this.pictureagreeMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Pictureagree record, Criteria example) {
        return this.pictureagreeMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Pictureagree record) {
        return this.pictureagreeMapper.insert(record);
    }

    public int insertSelective(Pictureagree record) {
        return this.pictureagreeMapper.insertSelective(record);
    }
}