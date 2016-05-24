package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.TaskMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Task;
import com.lettucetech.me2.service.TaskService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskMapper taskMapper;

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.taskMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Task selectByPrimaryKey(Integer id) {
        return this.taskMapper.selectByPrimaryKey(id);
    }

    public List<Task> selectByParams(Criteria example) {
        return this.taskMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.taskMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Task record) {
        return this.taskMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Task record) {
        return this.taskMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.taskMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Task record, Criteria example) {
        return this.taskMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Task record, Criteria example) {
        return this.taskMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Task record) {
        return this.taskMapper.insert(record);
    }

    public int insertSelective(Task record) {
        return this.taskMapper.insertSelective(record);
    }
}