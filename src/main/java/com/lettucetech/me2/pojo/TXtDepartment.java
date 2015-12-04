package com.lettucetech.me2.pojo;

import java.io.Serializable;

public class TXtDepartment implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer depId;

    private String depName;

    private TXtUser depManager;

    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public TXtUser getDepManager() {
        return depManager;
    }

    public void setDepManager(TXtUser depManager) {
        this.depManager = depManager;
    }
}