package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.PrivilegeMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Privilege;
import com.lettucetech.me2.service.PrivilegeService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {
    @Autowired
    private PrivilegeMapper privilegeMapper;

    private static final Logger logger = LoggerFactory.getLogger(PrivilegeServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.privilegeMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Privilege selectByPrimaryKey(Integer id) {
        return this.privilegeMapper.selectByPrimaryKey(id);
    }

    public List<Privilege> selectByParams(Criteria example) {
        return this.privilegeMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.privilegeMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Privilege record) {
        return this.privilegeMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Privilege record) {
        return this.privilegeMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.privilegeMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Privilege record, Criteria example) {
        return this.privilegeMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Privilege record, Criteria example) {
        return this.privilegeMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Privilege record) {
        return this.privilegeMapper.insert(record);
    }

    public int insertSelective(Privilege record) {
        return this.privilegeMapper.insertSelective(record);
    }
}