package com.lettucetech.me2.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lettucetech.me2.dao.PictureMapper;
import com.lettucetech.me2.pojo.Picture;
import com.lettucetech.me2.service.pictureService;

@Service("pictureService")
public class pictureServiceImpl implements pictureService {

    @Resource
    private PictureMapper pictureMapper;

    public void insertPic(Picture picture) {
        // TODO Auto-generated method stub
        pictureMapper.insert(picture);
    }

    public void updateById(Picture picture) {
        // TODO Auto-generated method stub
        pictureMapper.updateByPrimaryKey(picture);
    }

    public void deleteById(int id) {
        // TODO Auto-generated method stub
        pictureMapper.deleteByPrimaryKey(id);
    }

    public List<Picture> findAll() {
        // TODO Auto-generated method stub
        return pictureMapper.findAll();
    }

    public Picture findById(int id) {

        return pictureMapper.selectByPrimaryKey(id);
    }

}
