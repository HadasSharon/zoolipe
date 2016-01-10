package com.admin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="subcategory")
public class SubcategoryImpl  {
	@Id @GeneratedValue
	@Column(name="id")
	private long subCategoryId;
	@Column(name="category")
	private String category;
	@Column(name="mainsubcat")
	
	private String mainSubCategory;
	@Column(name="subcat")
	private String subCategory;
	@Column(name="currency_markup")
	private int currencyMarkup;
	@Column(name="duty")
	private int duty;
	@Column(name="hfi")
	private int hfi;
	@Column(name="vat")
	private float vat;
	public long getSubCategoryId() {
		return subCategoryId;
	}
	public void setSubCategoryId(long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getMainSubCategory() {
		return mainSubCategory;
	}
	public void setMainSubCategory(String mainSubCategory) {
		this.mainSubCategory = mainSubCategory;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public int getCurrencyMarkup() {
		return currencyMarkup;
	}
	public void setCurrencyMarkup(int currencyMarkup) {
		this.currencyMarkup = currencyMarkup;
	}
	public int getDuty() {
		return duty;
	}
	public void setDuty(int duty) {
		this.duty = duty;
	}
	public int getHfi() {
		return hfi;
	}
	public void setHfi(int hfi) {
		this.hfi = hfi;
	}
	public float getVat() {
		return vat;
	}
	public void setVat(float vat) {
		this.vat = vat;
	}

}
