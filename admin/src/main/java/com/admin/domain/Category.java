package com.admin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name="category")
public class Category {
	@Id
	@GeneratedValue
	@Column(name = "CATAGORY_ID", unique = true, nullable = false)
	private long catagoryId;
	@Column(name = "CATAGORY_NAME")
	private String catagoryName;
	@Column(name = "MAIN_SUB_CATEGORY_ID", unique = true, nullable = false)
	private long mainSubcatagoryId;
	@Column(name = "IMAGE_URL")
	private String imageUrl;
	@Column(name = "INCLUDE_IN_SUBMENU")
	private String includeInSubmenu;
	@Column(name = "MAIN_MENU")
	private String mainMenu;
	@Column(name="VAT")
	private float vat;
	@Column(name="DUTY")
	private float duty;
	@Column(name="HFI")
	private float hfi;
	@Column(name="CURRENCY_MARKUP")
	private float currencyMarkup;
	
	
	@Transient
	private long mainCategoryId;
	@Transient
	private String mainCategoryName;
	@Transient
	private String mainSubCategoryName;
	
	public long getCatagoryId() {
		return catagoryId;
	}
	public void setCatagoryId(long catagoryId) {
		this.catagoryId = catagoryId;
	}
	public String getCatagoryName() {
		return catagoryName;
	}
	public void setCatagoryName(String catagoryName) {
		this.catagoryName = catagoryName;
	}
	public long getMainSubcatagoryId() {
		return mainSubcatagoryId;
	}
	public void setMainSubcatagoryId(long mainSubcatagoryId) {
		this.mainSubcatagoryId = mainSubcatagoryId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getIncludeInSubmenu() {
		return includeInSubmenu;
	}
	public void setIncludeInSubmenu(String includeInSubmenu) {
		this.includeInSubmenu = includeInSubmenu;
	}
	public String getMainMenu() {
		return mainMenu;
	}
	public void setMainMenu(String mainMenu) {
		this.mainMenu = mainMenu;
	}
	public float getVat() {
		return vat;
	}
	public void setVat(float vat) {
		this.vat = vat;
	}
	public float getDuty() {
		return duty;
	}
	public void setDuty(float duty) {
		this.duty = duty;
	}
	public float getHfi() {
		return hfi;
	}
	public void setHfi(float hfi) {
		this.hfi = hfi;
	}
	public float getCurrencyMarkup() {
		return currencyMarkup;
	}
	public void setCurrencyMarkup(float currencyMarkup) {
		this.currencyMarkup = currencyMarkup;
	}
	public long getMainCategoryId() {
		return mainCategoryId;
	}
	public void setMainCategoryId(long mainCategoryId) {
		this.mainCategoryId = mainCategoryId;
	}
	public String getMainCategoryName() {
		return mainCategoryName;
	}
	public void setMainCategoryName(String mainCategoryName) {
		this.mainCategoryName = mainCategoryName;
	}
	public String getMainSubCategoryName() {
		return mainSubCategoryName;
	}
	public void setMainSubCategoryName(String mainSubCategoryName) {
		this.mainSubCategoryName = mainSubCategoryName;
	}
	
}
