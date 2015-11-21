package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.DeviceMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Device;
import com.lettucetech.me2.service.DeviceService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;

    private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.deviceMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Device selectByPrimaryKey(Integer deviceId) {
        return this.deviceMapper.selectByPrimaryKey(deviceId);
    }

    public List<Device> selectByParams(Criteria example) {
        return this.deviceMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer deviceId) {
        return this.deviceMapper.deleteByPrimaryKey(deviceId);
    }

    public int updateByPrimaryKeySelective(Device record) {
        return this.deviceMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Device record) {
        return this.deviceMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.deviceMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Device record, Criteria example) {
        return this.deviceMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Device record, Criteria example) {
        return this.deviceMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Device record) {
        return this.deviceMapper.insert(record);
    }

    public int insertSelective(Device record) {
        return this.deviceMapper.insertSelective(record);
    }
}