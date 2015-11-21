package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Device;
import java.util.List;

public interface DeviceService {
    int countByParams(Criteria example);

    Device selectByPrimaryKey(Integer deviceId);

    List<Device> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer deviceId);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Device record, Criteria example);

    int updateByParams(Device record, Criteria example);

    int insert(Device record);

    int insertSelective(Device record);
}