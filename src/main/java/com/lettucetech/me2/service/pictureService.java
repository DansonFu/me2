package com.lettucetech.me2.service;

import java.util.List;

import com.lettucetech.me2.pojo.Picture;

/**
 * 涓氬姟灞�
 *
 * @author Danson
 * @time 2017骞�6鏈�29鏃ヤ笂鍗�11:02:31
 *
 */
public interface pictureService {

    /**
     * 淇濆瓨鍥剧墖鏂规硶
     * 
     * @author Danson
     * @time 2017骞�6鏈�29鏃ヤ笂鍗�11:11:18
     * @param picture
     */
    public void insertPic(Picture picture);

    /**
     * 鏍规嵁id淇敼鍥剧墖
     * 
     * @author Danson
     * @time 2017骞�6鏈�29鏃ヤ笂鍗�11:08:56
     */
    public void updateById(Picture picture);

    /**
     * 鏍规嵁id鍒犻櫎鍥剧墖
     * 
     * @author Danson
     * @time 2017骞�6鏈�29鏃ヤ笂鍗�11:09:42
     */
    public void deleteById(int id);

    /**
     * 鏌ヨ鎵�鏈夌殑鍥剧墖
     * 
     * @author Danson
     * @time 2017骞�6鏈�29鏃ヤ笂鍗�11:11:37
     * @return
     */
    public List<Picture> findAll();

    /**
     * 鏍规嵁id鏌ヨ涓�寮犲浘鐗�
     * 
     * @author Danson
     * @time 2017骞�6鏈�29鏃ヤ笂鍗�11:11:42
     * @param id
     * @return
     */
    public Picture findById(int id);

}
