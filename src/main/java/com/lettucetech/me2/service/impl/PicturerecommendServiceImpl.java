package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.PicturerecommendMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Picturerecommend;
import com.lettucetech.me2.service.PicturerecommendService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PicturerecommendServiceImpl implements PicturerecommendService {
    @Autowired
    private PicturerecommendMapper picturerecommendMapper;

    private static final Logger logger = LoggerFactory.getLogger(PicturerecommendServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.picturerecommendMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Picturerecommend selectByPrimaryKey(Integer id) {
        return this.picturerecommendMapper.selectByPrimaryKey(id);
    }

    public List<Picturerecommend> selectByParams(Criteria example) {
        return this.picturerecommendMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.picturerecommendMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Picturerecommend record) {
        return this.picturerecommendMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Picturerecommend record) {
        return this.picturerecommendMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.picturerecommendMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Picturerecommend record, Criteria example) {
        return this.picturerecommendMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Picturerecommend record, Criteria example) {
        return this.picturerecommendMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Picturerecommend record) {
        return this.picturerecommendMapper.insert(record);
    }

    public int insertSelective(Picturerecommend record) {
        return this.picturerecommendMapper.insertSelective(record);
    }

	public List<Picturerecommend> selectByParams4Hot(Criteria example) {
		return this.picturerecommendMapper.selectByParams4Hot(example);
	}
}