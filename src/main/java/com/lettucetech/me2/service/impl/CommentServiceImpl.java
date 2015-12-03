package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.CommentMapper;
import com.lettucetech.me2.pojo.Comment;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.service.CommentService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.commentMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Comment selectByPrimaryKey(Integer commentId) {
        return this.commentMapper.selectByPrimaryKey(commentId);
    }

    public List<Comment> selectByParams(Criteria example) {
        return this.commentMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer commentId) {
        return this.commentMapper.deleteByPrimaryKey(commentId);
    }

    public int updateByPrimaryKeySelective(Comment record) {
        return this.commentMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Comment record) {
        return this.commentMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.commentMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Comment record, Criteria example) {
        return this.commentMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Comment record, Criteria example) {
        return this.commentMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Comment record) {
        return this.commentMapper.insert(record);
    }

    public int insertSelective(Comment record) {
        return this.commentMapper.insertSelective(record);
    }
}