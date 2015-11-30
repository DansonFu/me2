package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.PasterMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Paster;
import com.lettucetech.me2.service.PasterService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasterServiceImpl implements PasterService {
    @Autowired
    private PasterMapper pasterMapper;

    private static final Logger logger = LoggerFactory.getLogger(PasterServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.pasterMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Paster selectByPrimaryKey(Integer pasterId) {
        return this.pasterMapper.selectByPrimaryKey(pasterId);
    }

    public List<Paster> selectByParams(Criteria example) {
        return this.pasterMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer pasterId) {
        return this.pasterMapper.deleteByPrimaryKey(pasterId);
    }

    public int updateByPrimaryKeySelective(Paster record) {
        return this.pasterMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Paster record) {
        return this.pasterMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.pasterMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Paster record, Criteria example) {
        return this.pasterMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Paster record, Criteria example) {
        return this.pasterMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Paster record) {
        return this.pasterMapper.insert(record);
    }

    public int insertSelective(Paster record) {
        return this.pasterMapper.insertSelective(record);
    }

	public List<Paster> selectByParams4Business(Criteria example) {
		return this.pasterMapper.selectByParams4Business(example);
	}
}