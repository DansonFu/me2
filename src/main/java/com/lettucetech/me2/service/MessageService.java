package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Message;
import java.util.List;

public interface MessageService {
    int countByParams(Criteria example);

    Message selectByPrimaryKey(Integer id);

    List<Message> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Message record, Criteria example);

    int updateByParams(Message record, Criteria example);

    int insert(Message record);

    int insertSelective(Message record);
}