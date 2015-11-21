package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.PostMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Post;
import com.lettucetech.me2.service.PostService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostMapper postMapper;

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.postMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Post selectByPrimaryKey(Integer postId) {
        return this.postMapper.selectByPrimaryKey(postId);
    }

    public List<Post> selectByParams(Criteria example) {
        return this.postMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer postId) {
        return this.postMapper.deleteByPrimaryKey(postId);
    }

    public int updateByPrimaryKeySelective(Post record) {
        return this.postMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Post record) {
        return this.postMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.postMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Post record, Criteria example) {
        return this.postMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Post record, Criteria example) {
        return this.postMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Post record) {
        return this.postMapper.insert(record);
    }

    public int insertSelective(Post record) {
        return this.postMapper.insertSelective(record);
    }
}