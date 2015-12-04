package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtPost;
import java.util.List;

public interface TXtPostService {
    int countByParams(Criteria example);

    TXtPost selectByPrimaryKey(Integer postId);

    List<TXtPost> selectByParams(Criteria example);
}