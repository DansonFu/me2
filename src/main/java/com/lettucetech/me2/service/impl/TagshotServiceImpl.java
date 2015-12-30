package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.TagshotMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Tagshot;
import com.lettucetech.me2.service.TagshotService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagshotServiceImpl implements TagshotService {
    @Autowired
    private TagshotMapper tagshotMapper;

    private static final Logger logger = LoggerFactory.getLogger(TagshotServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.tagshotMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Tagshot selectByPrimaryKey(Integer id) {
        return this.tagshotMapper.selectByPrimaryKey(id);
    }

    public List<Tagshot> selectByParams(Criteria example) {
        return this.tagshotMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.tagshotMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Tagshot record) {
        return this.tagshotMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Tagshot record) {
        return this.tagshotMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.tagshotMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Tagshot record, Criteria example) {
        return this.tagshotMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Tagshot record, Criteria example) {
        return this.tagshotMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Tagshot record) {
        return this.tagshotMapper.insert(record);
    }

    public int insertSelective(Tagshot record) {
        return this.tagshotMapper.insertSelective(record);
    }

	public List<Tagshot> selectByParams4Matching(Criteria example) {
		return this.tagshotMapper.selectByParams4Matching(example);
	}
}