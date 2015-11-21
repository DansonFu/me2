package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Server;
import java.util.List;

public interface ServerService {
    int countByParams(Criteria example);

    Server selectByPrimaryKey(Integer serverId);

    List<Server> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer serverId);

    int updateByPrimaryKeySelective(Server record);

    int updateByPrimaryKey(Server record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Server record, Criteria example);

    int updateByParams(Server record, Criteria example);

    int insert(Server record);

    int insertSelective(Server record);
}