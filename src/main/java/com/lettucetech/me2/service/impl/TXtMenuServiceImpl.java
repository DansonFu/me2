package com.lettucetech.me2.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lettucetech.me2.dao.TXtMenuMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtMenu;
import com.lettucetech.me2.pojo.TXtRoleMenu;
import com.lettucetech.me2.service.TXtMenuService;

@Service
public class TXtMenuServiceImpl implements TXtMenuService {
    @Autowired
    private TXtMenuMapper tXtMenuMapper;

    private static final Logger logger = LoggerFactory.getLogger(TXtMenuServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.tXtMenuMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public TXtMenu selectByPrimaryKey(Integer mId) {
        return this.tXtMenuMapper.selectByPrimaryKey(mId);
    }

    public List<TXtMenu> selectByParams(Criteria example) {
        return this.tXtMenuMapper.selectByParams(example);
    }

    public int insert(TXtMenu menu) {
        return this.tXtMenuMapper.insert(menu);
    }

    public int updateByPrimaryKeySelective(TXtMenu menu) {
        return this.tXtMenuMapper.updateByPrimaryKeySelective(menu);
    }

    public int deleteByPrimaryKey(Integer mId) {
        return this.tXtMenuMapper.deleteByPrimaryKey(mId);
    }

    public List<TXtMenu> selectMenus(List<TXtRoleMenu> roleMenuList) {
        return this.tXtMenuMapper.selectMenus(roleMenuList);
    }
}