package com.lettucetech.me2.web.form;

import java.util.List;

public class DataTablePaginationForm {
	private String sEcho;
	private int iTotalRecords;
	private int iTotalDisplayRecords;
	private List aaData;
	
	
	public String getsEcho() {
		return sEcho;
	}
	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public int getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public List getAaData() {
		return aaData;
	}
	public void setAaData(List aaData) {
		this.aaData = aaData;
	}


}
