package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Post;
import java.util.List;

public interface PostService {
    int countByParams(Criteria example);

    Post selectByPrimaryKey(Integer postId);

    List<Post> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer postId);

    int updateByPrimaryKeySelective(Post record);

    int updateByPrimaryKey(Post record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Post record, Criteria example);

    int updateByParams(Post record, Criteria example);

    int insert(Post record);

    int insertSelective(Post record);
}