package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.ServerMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Server;
import com.lettucetech.me2.service.ServerService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerServiceImpl implements ServerService {
    @Autowired
    private ServerMapper serverMapper;

    private static final Logger logger = LoggerFactory.getLogger(ServerServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.serverMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Server selectByPrimaryKey(Integer serverId) {
        return this.serverMapper.selectByPrimaryKey(serverId);
    }

    public List<Server> selectByParams(Criteria example) {
        return this.serverMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer serverId) {
        return this.serverMapper.deleteByPrimaryKey(serverId);
    }

    public int updateByPrimaryKeySelective(Server record) {
        return this.serverMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Server record) {
        return this.serverMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.serverMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Server record, Criteria example) {
        return this.serverMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Server record, Criteria example) {
        return this.serverMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Server record) {
        return this.serverMapper.insert(record);
    }

    public int insertSelective(Server record) {
        return this.serverMapper.insertSelective(record);
    }
}