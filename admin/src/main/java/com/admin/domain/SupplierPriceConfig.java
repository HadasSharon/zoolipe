package com.admin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="supplier_price_config")
public class SupplierPriceConfig {
	@Id
	@GeneratedValue
	@Column(name="SUPPLIER_PRICE_CONFIG_NO")
	private long supplierPriceConfigId;
	@Column(name="SUPPLIER_ID")
	private long supplierId;
	@Column(name="CATEGORY_ID")
	private long categoryId;
	@Column(name="VAT")
	private float vat;
	@Column(name="DUTY")
	private float duty;
	@Column(name="HFI")
	private float hfi;
	@Column(name="CURRENCY_MARKUP")
	private float currencyMarkup;
	public long getSupplierPriceConfigId() {
		return supplierPriceConfigId;
	}
	public void setSupplierPriceConfigId(long supplierPriceConfigId) {
		this.supplierPriceConfigId = supplierPriceConfigId;
	}
	
	
	public long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
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
	
}
