package com.lettucetech.me2.web.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lettucetech.me2.pojo.Picture;
import com.lettucetech.me2.service.pictureService;

@Controller
@Scope(value = "prototype")
@RequestMapping("/picture")
public class pictureController {

    @Resource
    private pictureService pictureService;

    @RequestMapping(value = "/main", method = { RequestMethod.GET })
    public ModelAndView main(HttpServletRequest request, HttpSession session, HttpServletResponse response) {

        ModelAndView mav = new ModelAndView("/main");
        return mav;
    }

    @RequestMapping(value = "/add", method = { RequestMethod.GET })
    public ModelAndView add() {
        ModelAndView mav = new ModelAndView("/add");
        return mav;
    }

    @RequestMapping(value = "/edit", method = { RequestMethod.GET })
    public ModelAndView edit() {
        ModelAndView mav = new ModelAndView("/edit");
        return mav;
    }

    @RequestMapping(value = "/addPic", method = { RequestMethod.POST })
    public void addPic(HttpServletRequest request) {
        String name = request.getParameter("name");
        String pic = request.getParameter("pic");
        Picture picture = new Picture();
        picture.setQiniukey(pic);
        picture.setCreatetime(new Date());
        picture.setUpdatetime(new Date());
        pictureService.insertPic(picture);
    }

    @RequestMapping(value = "/editPic", method = { RequestMethod.POST })
    public void editPic(HttpServletRequest request) {
        String name = request.getParameter("name");
        String pic = request.getParameter("pic");
        Picture picture = new Picture();
        picture.setQiniukey(pic);
        picture.setCreatetime(new Date());
        picture.setUpdatetime(new Date());
        pictureService.insertPic(picture);
    }

    @RequestMapping(value = "/delete", method = { RequestMethod.GET })
    public void delete(int id) {
        pictureService.deleteById(id);
    }

    @RequestMapping(value = "/deletes", method = { RequestMethod.GET })
    public void deletes(int[] ids) {
        for (int i : ids) {
            pictureService.deleteById(i);
        }
    }
}
