package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Task;
import java.util.List;

public interface TaskService {
    int countByParams(Criteria example);

    Task selectByPrimaryKey(Integer id);

    List<Task> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Task record, Criteria example);

    int updateByParams(Task record, Criteria example);

    int insert(Task record);

    int insertSelective(Task record);
}