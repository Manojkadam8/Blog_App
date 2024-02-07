package com.BlogApi.payloads;

import java.util.List;

public class PostResponse {

	private List<PostDto> content;
	
	private int pagenumber;
	
	private int pagesize;
	
	private long totalelement;
	
	private int totalpages;
	
	private boolean lastpage;

	public PostResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PostResponse(List<PostDto> content, int pagenumber, int pagesize, long totalelement, int totalpages,
			boolean lastpage) {
		super();
		this.content = content;
		this.pagenumber = pagenumber;
		this.pagesize = pagesize;
		this.totalelement = totalelement;
		this.totalpages = totalpages;
		this.lastpage = lastpage;
	}

	public List<PostDto> getContent() {
		return content;
	}

	public void setContent(List<PostDto> content) {
		this.content = content;
	}

	public int getPagenumber() {
		return pagenumber;
	}

	public void setPagenumber(int pagenumber) {
		this.pagenumber = pagenumber;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPageSize(int pagesize) {
		this.pagesize = pagesize;
	}

	public long getTotalElement() {
		return totalelement;
	}

	public void setTotalElement(long totalelement) {
		this.totalelement = totalelement;
	}

	public int getTotalpages() {
		return totalpages;
	}

	public void setTotalpages(int totalpages) {
		this.totalpages = totalpages;
	}

	public boolean isLastpage() {
		return lastpage;
	}

	public void setLastpage(boolean lastpage) {
		this.lastpage = lastpage;
	}
	
	
	
}
