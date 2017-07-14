package com.lettucetech.me2.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lettucetech.me2.dao.TXtRoleUserMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtRoleUser;
import com.lettucetech.me2.service.TXtRoleUserService;

@Service
public class TXtRoleUserServiceImpl implements TXtRoleUserService {
    @Autowired
    private TXtRoleUserMapper tXtRoleUserMapper;

    private static final Logger logger = LoggerFactory.getLogger(TXtRoleUserServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.tXtRoleUserMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public TXtRoleUser selectByPrimaryKey(Integer roleUserId) {
        return this.tXtRoleUserMapper.selectByPrimaryKey(roleUserId);
    }

    public List<TXtRoleUser> selectByParams(Criteria example) {
        return this.tXtRoleUserMapper.selectByParams(example);
    }

    public List<TXtRoleUser> advancedSearching(Criteria example) {
        return this.tXtRoleUserMapper.advancedSearching(example);
    }

    public int insertSelective(TXtRoleUser record) {
        return this.tXtRoleUserMapper.insertSelective(record);
    }

    public int deleteByPrimaryKey(Integer roleUserId) {
        return this.tXtRoleUserMapper.deleteByPrimaryKey(roleUserId);
    }

    public int updateByPrimaryKeySelective(TXtRoleUser record) {
        return this.tXtRoleUserMapper.updateByPrimaryKeySelective(record);
    }

    public boolean saveRoleSelected(String userIds, String roleId) {
        if (StringUtils.isNotEmpty(userIds)) {
            try {
                String[] ids = userIds.split(",");
                Criteria criteria = new Criteria();
                criteria.put("roleId", roleId);
                this.tXtRoleUserMapper.deleteByParams(criteria);
                for (int i = 0; i < ids.length; i++) {
                    TXtRoleUser roleUser = new TXtRoleUser();
                    roleUser.setCreateTime(new Date());
                    roleUser.setUserId(Integer.parseInt(ids[i]));
                    roleUser.setRoleId(Integer.parseInt(roleId));
                    this.tXtRoleUserMapper.insertSelective(roleUser);
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }
}