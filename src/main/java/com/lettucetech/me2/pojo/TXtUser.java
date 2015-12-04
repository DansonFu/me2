package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class TXtUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer userId;

    /**
     * 账号
     */
    private String account;

    private String password;

    private String name;

//    private Integer depId;
    private TXtDepartment department;

    /**
     * ְ
     */
    private Integer postId;

    private String phone;

    private String sex;

    private String idcard;

//    private String leader;
    private TXtUser leader;

    /**
     * ¼
     */
    private Date createTime;

    private String status;

    private Date updateTime;

    /**
     * ɾ
     */
    private Integer deleteState;

    private String qq;

    private String email;

    private String address;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return 账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account 
	 *            账号
     */
    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Integer getDepId() {
//        return depId;
//    }
//
//    public void setDepId(Integer depId) {
//        this.depId = depId;
//    }

    /**
     * @return ְ
     */
    public Integer getPostId() {
        return postId;
    }

    /**
     * @param postId 
	 *            ְ
     */
    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

//    public String getLeader() {
//        return leader;
//    }
//
//    public void setLeader(String leader) {
//        this.leader = leader;
//    }

    /**
     * @return ¼
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime 
	 *            ¼
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return ɾ
     */
    public Integer getDeleteState() {
        return deleteState;
    }

    /**
     * @param deleteState 
	 *            ɾ
     */
    public void setDeleteState(Integer deleteState) {
        this.deleteState = deleteState;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

	public TXtDepartment getDepartment() {
		return department;
	}

	public void setDepartment(TXtDepartment department) {
		this.department = department;
	}

	public TXtUser getLeader() {
		return leader;
	}

	public void setLeader(TXtUser leader) {
		this.leader = leader;
	}
    
}