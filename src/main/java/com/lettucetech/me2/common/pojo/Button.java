package com.lettucetech.me2.common.pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Button {
	 private String name;//所有一级菜单、二级菜单都共有一个相同的属性，那就是name

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }
}
