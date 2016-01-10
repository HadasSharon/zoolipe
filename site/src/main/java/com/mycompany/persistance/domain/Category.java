package com.mycompany.persistance.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
	
}
