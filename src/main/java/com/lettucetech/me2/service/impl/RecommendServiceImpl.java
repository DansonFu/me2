package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.RecommendMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Recommend;
import com.lettucetech.me2.pojo.Tagshot;
import com.lettucetech.me2.service.RecommendService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendServiceImpl implements RecommendService {
    @Autowired
    private RecommendMapper recommendMapper;

    private static final Logger logger = LoggerFactory.getLogger(RecommendServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.recommendMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Recommend selectByPrimaryKey(Integer id) {
        return this.recommendMapper.selectByPrimaryKey(id);
    }

    public List<Recommend> selectByParams(Criteria example) {
        return this.recommendMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.recommendMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Recommend record) {
        return this.recommendMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Recommend record) {
        return this.recommendMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.recommendMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Recommend record, Criteria example) {
        return this.recommendMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Recommend record, Criteria example) {
        return this.recommendMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Recommend record) {
        return this.recommendMapper.insert(record);
    }

    public int insertSelective(Recommend record) {
        return this.recommendMapper.insertSelective(record);
    }
    public List<Recommend> selectByParams4Matching(Criteria example) {
		return this.recommendMapper.selectByParams4Matching(example);
	}
}