package com.admin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name="main_category")
public class MainCatagory {
	@Id
	@GeneratedValue
	@Column(name = "MAIN_CATAGORY_ID", unique = true, nullable = false)
	private long mainCatagoryId;
	@Column(name = "MAIN_CATAGORY_NAME")
	private String maincatagoryName;
	@Column(name = "IMAGE_URL")
	private String imageUrl;
	@Column(name = "INCLUDE_IN_SUBMENU")
	private String includeInSubmenu;
	@Column(name = "MAIN_MENU")
	private String mainMenu;
	@Transient
	private MultipartFile file;
	public long getMainCatagoryId() {
		return mainCatagoryId;
	}
	public void setMainCatagoryId(long mainCatagoryId) {
		this.mainCatagoryId = mainCatagoryId;
	}
	public String getMaincatagoryName() {
		return maincatagoryName;
	}
	public void setMaincatagoryName(String maincatagoryName) {
		this.maincatagoryName = maincatagoryName;
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
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
