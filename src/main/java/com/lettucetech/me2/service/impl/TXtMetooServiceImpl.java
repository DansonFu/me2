package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.TXtMetooMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtMetoo;
import com.lettucetech.me2.service.TXtMetooService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TXtMetooServiceImpl implements TXtMetooService {
    @Autowired
    private TXtMetooMapper tXtMetooMapper;

    private static final Logger logger = LoggerFactory.getLogger(TXtMetooServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.tXtMetooMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public TXtMetoo selectByPrimaryKey(Integer id) {
        return this.tXtMetooMapper.selectByPrimaryKey(id);
    }

    public List<TXtMetoo> selectByParams(Criteria example) {
        return this.tXtMetooMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.tXtMetooMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(TXtMetoo record) {
        return this.tXtMetooMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(TXtMetoo record) {
        return this.tXtMetooMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.tXtMetooMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(TXtMetoo record, Criteria example) {
        return this.tXtMetooMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(TXtMetoo record, Criteria example) {
        return this.tXtMetooMapper.updateByParams(record, example.getCondition());
    }

    public int insert(TXtMetoo record) {
        return this.tXtMetooMapper.insert(record);
    }

    public int insertSelective(TXtMetoo record) {
        return this.tXtMetooMapper.insertSelective(record);
    }

	public List<TXtMetoo> selectByParams4MetooPicture(Criteria example) {
		return this.tXtMetooMapper.selectByParams4MetooPicture(example);
	}
}