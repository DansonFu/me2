package com.lettucetech.me2.web.form;

import java.util.List;

import com.lettucetech.me2.pojo.Customer;
import com.lettucetech.me2.pojo.Picture1;

public class MessageForm {
	private Picture1 picture;

	private List<Customer> proposers;

	public Picture1 getPicture() {
		return picture;
	}

	public void setPicture(Picture1 picture) {
		this.picture = picture;
	}

	public List<Customer> getProposers() {
		return proposers;
	}

	public void setProposers(List<Customer> proposers) {
		this.proposers = proposers;
	}
	
}
