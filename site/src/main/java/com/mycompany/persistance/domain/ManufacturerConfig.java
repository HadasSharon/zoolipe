package com.mycompany.persistance.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "manufacturer")
public class ManufacturerConfig {
	@Id
	@GeneratedValue
	@Column(name = "MANUFACTURER_ID")
	public long manufacturerId;
	@Column(name = "MANUFACTURER_NAME")
	public String manuFacturerName;
	@Column(name = "IMAGE_URL")
	public String imageUrl;
	@Column(name = "MID")
	public int mid;
	public long getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(long manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public String getManuFacturerName() {
		return manuFacturerName;
	}

	public void setManuFacturerName(String manuFacturerName) {
		this.manuFacturerName = manuFacturerName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	
}
