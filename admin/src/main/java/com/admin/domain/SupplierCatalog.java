package com.admin.domain;

import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "supplier_catalog", uniqueConstraints = { @UniqueConstraint(columnNames = "SUPPLIER_CATALOG_ID") })
public class SupplierCatalog {
	@Id
	@GeneratedValue
	@Column(name = "SUPPLIER_CATALOG_ID")
	private long supplierCatalogueId;
	
	@Column(name = "STOCK")
	private int stock;
	@Column(name = "MINI")
	private int minu;
	@Column(name = "MULT")
	private int mult;
	@Column(name = "LEAD_TIME")
	private int leadTime;
	@Column(name = "PRODUCT_CATALOG_ID")
	private long productCatalogId;
	@Column(name = "SUPPLIER_ID")
	private long supplierId;
	@Column(name = "PRODUCT_STATUS")
	private String productStatus;
	
	@Column(name = "SKU")
	private String sku;
	@Column(name = "ON_ORDER")
	private int onOrder;
	@Column(name = "PACAKAGE")
	private String pacakage;
	
	@Transient
	private String supplierName;
	@Transient
	List<PriceTag> priceTagImplLists;
	
	//@ManyToOne(cascade=CascadeType.ALL)
	/*
	 * @OneToOne
	 * 
	 * @JoinColumn(name = "product_catalog_id") private ProductCatalogImplAdmin
	 * productCatalogImplAdmin;
	 */

	public List<PriceTag> getPriceTagImplLists() {
		return priceTagImplLists;
	}

	public void setPriceTagImplLists(List<PriceTag> priceTagImplLists) {
		this.priceTagImplLists = priceTagImplLists;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public long getSupplierCatalogueId() {
		return supplierCatalogueId;
	}

	public void setSupplierCatalogueId(long supplierCatalogueId) {
		this.supplierCatalogueId = supplierCatalogueId;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getMinu() {
		return minu;
	}

	public void setMinu(int minu) {
		this.minu = minu;
	}

	public int getMult() {
		return mult;
	}

	public void setMult(int mult) {
		this.mult = mult;
	}

	public int getLeadTime() {
		return leadTime;
	}

	public void setLeadTime(int leadTime) {
		this.leadTime = leadTime;
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

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}



	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public int getOnOrder() {
		return onOrder;
	}

	public void setOnOrder(int onOrder) {
		this.onOrder = onOrder;
	}

	public String getPacakage() {
		return pacakage;
	}

	public void setPacakage(String pacakage) {
		this.pacakage = pacakage;
	}


	
}
