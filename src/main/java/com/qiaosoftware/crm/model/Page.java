package com.qiaosoftware.crm.model;

import java.util.List;

public class Page<T> {
	
	private List<T> list;
	
	private int pageNo;
	
	private int pageSize;
	
	public static final int PAGE_SIZE = 4;
	
	private int totalRecordNo;
	
	private int totalPageNo;

	public Page(String pageNoStr, int pageSize, int totalRecordNo) {
		
		this.totalRecordNo = totalRecordNo;
		
		this.pageSize = pageSize;
		
		//计算总页数
		this.totalPageNo = this.totalRecordNo / pageSize + (this.totalRecordNo % pageSize == 0?0:1);
		
		this.pageNo = 1;
		
		try {
			this.pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		
		if (this.pageNo > this.totalPageNo) {
			this.pageNo = this.totalPageNo;
		}
		
		if (this.pageNo < 1) {
			this.pageNo = 1;
		}
		
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getTotalRecordNo() {
		return totalRecordNo;
	}

	public int getTotalPageNo() {
		return totalPageNo;
	}
	
	public boolean isHasPrev(){
		return pageNo > 1;
	}
	
	public int getPrev(){
		return pageNo - 1;
	}
	
	public boolean isHasNext(){
		return pageNo < totalPageNo;
	}
	
	public int getNext(){
		return pageNo + 1;
	}
	
}
