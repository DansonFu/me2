package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Comment;
import com.lettucetech.me2.pojo.Criteria;
import java.util.List;

public interface CommentService {
    int countByParams(Criteria example);

    Comment selectByPrimaryKey(Integer commentId);

    List<Comment> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer commentId);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Comment record, Criteria example);

    int updateByParams(Comment record, Criteria example);

    int insert(Comment record);

    int insertSelective(Comment record);

	List<Comment> selectByParams4Business(Criteria example);
}