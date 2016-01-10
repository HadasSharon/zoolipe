package com.mycompany.persistance.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="price_tag")
public class PriceTag {

	@Id @GeneratedValue
	@Column(name="PRICE_TAG_ID")
	private long priceTagId;
	
	@Column(name="PRODUCT_CATALOG_ID")
	private long productCatalogId;
	@Column(name="SUPPLIER_ID")
	private long supplierId;
	@Column(name="QUENTITY")
	private int quentity;
	@Column(name="SALE_PRICE")
	private float salePrice;
	@Column(name="RETAIL_PRICE")
	private float retailPrice;
	@Column(name="tax")
	private String tax;
	@Column(name = "SKU")
	private String sku;
	public long getPriceTagId() {
		return priceTagId;
	}

	public void setPriceTagId(long priceTagId) {
		this.priceTagId = priceTagId;
	}

	

	

	public long getProductCatalogId() {
		return productCatalogId;
	}

	public void setProductCatalogId(long productCatalogId) {
		this.productCatalogId = productCatalogId;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public int getQuentity() {
		return quentity;
	}

	public void setQuentity(int quentity) {
		this.quentity = quentity;
	}


	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public float getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(float salePrice) {
		this.salePrice = salePrice;
	}

	public float getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(float retailPrice) {
		this.retailPrice = retailPrice;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
}
