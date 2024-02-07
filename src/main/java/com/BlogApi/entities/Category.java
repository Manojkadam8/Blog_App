package com.BlogApi.entities;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int categoryid;
	
	@Column(name="title",length=100,nullable=false)
	private String categorytitle;
	
	@Column(name="description")
	private String categorydescription;
	
	
	@OneToMany(mappedBy="category",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<Post> posts=new ArrayList();

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Category(int categoryid, String categorytitle, String categorydescription, List<Post> posts) {
		super();
		this.categoryid = categoryid;
		this.categorytitle = categorytitle;
		this.categorydescription = categorydescription;
		this.posts = posts;
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

//	@Override
//	public String toString() {
//		return "Category [categoryid=" + categoryid + ", categorytitle=" + categorytitle + ", categorydescription="
//				+ categorydescription + ", posts=" + posts + "]";
//	}



	
	
}
