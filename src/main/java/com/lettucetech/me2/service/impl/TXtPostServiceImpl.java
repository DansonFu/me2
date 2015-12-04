package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.TXtPostMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtPost;
import com.lettucetech.me2.service.TXtPostService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TXtPostServiceImpl implements TXtPostService {
    @Autowired
    private TXtPostMapper tXtPostMapper;

    private static final Logger logger = LoggerFactory.getLogger(TXtPostServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.tXtPostMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public TXtPost selectByPrimaryKey(Integer postId) {
        return this.tXtPostMapper.selectByPrimaryKey(postId);
    }

    public List<TXtPost> selectByParams(Criteria example) {
        return this.tXtPostMapper.selectByParams(example);
    }
}