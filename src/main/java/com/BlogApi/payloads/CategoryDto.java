package com.BlogApi.payloads;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryDto {

    private int categoryid;
	
	@NotBlank
	@Size(min=4,message="length is short")
	private String categorytitle;
	
	@NotBlank
	@Size(min=10,message="length is short")
	private String categorydescription;


	public CategoryDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CategoryDto(int categoryid, String categorytitle, String categorydescription) {
		super();
		this.categoryid = categoryid;
		this.categorytitle = categorytitle;
		this.categorydescription = categorydescription;
	}


	public int getCategoryid() {
		return categoryid;
	}


	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}


	public String getCategorytitle() {
		return categorytitle;
	}


	public void setCategorytitle(String categorytitle) {
		this.categorytitle = categorytitle;
	}


	public String getCategorydescription() {
		return categorydescription;
	}


	public void setCategorydescription(String categorydescription) {
		this.categorydescription = categorydescription;
	}
	
	

}
