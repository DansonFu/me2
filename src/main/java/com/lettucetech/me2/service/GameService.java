package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Game;
import java.util.List;

public interface GameService {
    int countByParams(Criteria example);

    Game selectByPrimaryKey(Integer gameId);

    List<Game> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer gameId);

    int updateByPrimaryKeySelective(Game record);

    int updateByPrimaryKey(Game record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Game record, Criteria example);

    int updateByParams(Game record, Criteria example);

    int insert(Game record);

    int insertSelective(Game record);
}