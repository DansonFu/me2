package com.lettucetech.me2.web.form;

import java.util.List;

import com.lettucetech.me2.pojo.Customer;
import com.lettucetech.me2.pojo.Picture;

public class MessageForm {
	private Picture picture;

	private List<Customer> proposers;

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	public List<Customer> getProposers() {
		return proposers;
	}

	public void setProposers(List<Customer> proposers) {
		this.proposers = proposers;
	}
	
}
