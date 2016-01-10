package com.mycompany.persistance.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="main_sub_category")
public class MainSubCatagory {
	@Id
	@GeneratedValue
	@Column(name = "MAIN_SUB_CATEGORY_ID", unique = true, nullable = false)
	private long mainSubCatagoryId;
	@Column(name = "MAIN_CATAGORY_ID", unique = true)
	private long mainCatagoryId;
	@Column(name = "MAIN_SUB_CATEGORY_NAME")
	private String mainSubCatagoryName;
	@Column(name = "INCLUDE_IN_SUBMENU")
	private String includeInSubmenu;
	@Column(name = "IMAGE_URL")
	private String imageUrl;
	@Column(name = "MAIN_MENU")
	private String mainMenu;
	public long getMainSubCatagoryId() {
		return mainSubCatagoryId;
	}
	public void setMainSubCatagoryId(long mainSubCatagoryId) {
		this.mainSubCatagoryId = mainSubCatagoryId;
	}
	public long getMainCatagoryId() {
		return mainCatagoryId;
	}
	public void setMainCatagoryId(long mainCatagoryId) {
		this.mainCatagoryId = mainCatagoryId;
	}
	public String getMainSubCatagoryName() {
		return mainSubCatagoryName;
	}
	public void setMainSubCatagoryName(String mainSubCatagoryName) {
		this.mainSubCatagoryName = mainSubCatagoryName;
	}
	public String getIncludeInSubmenu() {
		return includeInSubmenu;
	}
	public void setIncludeInSubmenu(String includeInSubmenu) {
		this.includeInSubmenu = includeInSubmenu;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getMainMenu() {
		return mainMenu;
	}
	public void setMainMenu(String mainMenu) {
		this.mainMenu = mainMenu;
	}
}
