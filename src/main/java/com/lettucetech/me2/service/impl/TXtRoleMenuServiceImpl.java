package com.lettucetech.me2.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lettucetech.me2.dao.TXtRoleMenuMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtRoleMenu;
import com.lettucetech.me2.pojo.TXtRoleUser;
import com.lettucetech.me2.service.TXtRoleMenuService;

@Service
public class TXtRoleMenuServiceImpl implements TXtRoleMenuService {
    @Autowired
    private TXtRoleMenuMapper tXtRoleMenuMapper;

    private static final Logger logger = LoggerFactory.getLogger(TXtRoleMenuServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.tXtRoleMenuMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public TXtRoleMenu selectByPrimaryKey(Integer roleMenuId) {
        return this.tXtRoleMenuMapper.selectByPrimaryKey(roleMenuId);
    }

    public List<TXtRoleMenu> selectByParams(Criteria example) {
        return this.tXtRoleMenuMapper.selectByParams(example);
    }

    public int insertSelective(TXtRoleMenu record) {
        return this.tXtRoleMenuMapper.insertSelective(record);
    }

    public int deleteByPrimaryKey(Integer roleMenuId) {
        return this.tXtRoleMenuMapper.deleteByPrimaryKey(roleMenuId);
    }

    public int updateByPrimaryKeySelective(TXtRoleMenu record) {
        return this.tXtRoleMenuMapper.updateByPrimaryKeySelective(record);
    }

    public List<TXtRoleMenu> advancedSearching(Criteria example) {
        return this.tXtRoleMenuMapper.advancedSearching(example);
    }

    public int deleteByParams(Criteria example) {
        return this.tXtRoleMenuMapper.deleteByParams(example);
    }

    public boolean savePerSelected(String menuIds, String roleId) {
        if (StringUtils.isNotEmpty(menuIds)) {
            try {
                String[] ids = menuIds.split(",");
                Criteria criteria = new Criteria();
                criteria.put("roleId", roleId);
                this.tXtRoleMenuMapper.deleteByParams(criteria);
                for (int i = 0; i < ids.length; i++) {
                    TXtRoleMenu roleMenu = new TXtRoleMenu();
                    roleMenu.setmId(Integer.parseInt(ids[i]));
                    roleMenu.setRoleId(Integer.parseInt(roleId));
                    roleMenu.setCreateTime(new Date());
                    this.tXtRoleMenuMapper.insertSelective(roleMenu);
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public List<TXtRoleMenu> selectMId(List<TXtRoleUser> roleUserList) {
        return this.tXtRoleMenuMapper.selectMId(roleUserList);
    }
}