package com.mycompany.persistance.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "semikart_wish_list")
public class CartVo {

	@Id
	@GeneratedValue
	@Column(name = "semikart_wish_list_id", unique = true, nullable = false)
	private long semikartWishListId;

	@Column(name = "manufacturer_part")
	private String manuFacturerPart;
	@Column(name = "product_catalog_id")
	private long productCatalogId;

	@Column(name = "supplier_id")
	private long supplierId;
	@Column(name = "price")
	public float price;
	@Column(name = "total_PRICE")
	public float totalPrice;
	@Column(name = "vat")
	public float vat;
	@Column(name = "quantity")
	public int quantity;
	@Column(name = "customer_Id")
	public long customerId;
	@Column(name = "status")
	public String  status;
	
	@Transient
	public String manuFacturerName;
	@Transient
	public String rohs;
	@Transient
	public String description;
	@Transient
	public String supplierName;
	@Transient
	public String semikartPart;
	@Transient
	public int stock;

	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getManuFacturerPart() {
		return manuFacturerPart;
	}

	public void setManuFacturerPart(String manuFacturerPart) {
		this.manuFacturerPart = manuFacturerPart;
	}

	public String getManuFacturerName() {
		return manuFacturerName;
	}

	public void setManuFacturerName(String manuFacturerName) {
		this.manuFacturerName = manuFacturerName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public float getVat() {
		return vat;
	}

	public void setVat(float vat) {
		this.vat = vat;
	}

	public long getSemikartWishListId() {
		return semikartWishListId;
	}

	public void setSemikartWishListId(long semikartWishListId) {
		this.semikartWishListId = semikartWishListId;
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

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSemikartPart() {
		return semikartPart;
	}

	public void setSemikartPart(String semikartPart) {
		this.semikartPart = semikartPart;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRohs() {
		return rohs;
	}

	public void setRohs(String rohs) {
		this.rohs = rohs;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	

}
