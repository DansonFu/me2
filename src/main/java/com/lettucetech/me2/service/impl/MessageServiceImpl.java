package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.MessageMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Message;
import com.lettucetech.me2.service.MessageService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.messageMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Message selectByPrimaryKey(Integer id) {
        return this.messageMapper.selectByPrimaryKey(id);
    }

    public List<Message> selectByParams(Criteria example) {
        return this.messageMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.messageMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Message record) {
        return this.messageMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Message record) {
        return this.messageMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.messageMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Message record, Criteria example) {
        return this.messageMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Message record, Criteria example) {
        return this.messageMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Message record) {
        return this.messageMapper.insert(record);
    }

    public int insertSelective(Message record) {
        return this.messageMapper.insertSelective(record);
    }

	public List<Message> selectByParams4Classify(Criteria example) {
		return this.messageMapper.selectByParams4Classify(example);
	}
}