package com.admin.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "product_catalog", uniqueConstraints = {
		@UniqueConstraint(columnNames = "PRODUCT_CATALOG_ID"),
		@UniqueConstraint(columnNames = "MANUFACTURER_PART") })
public class ProductCatalog {

	@Id
	@GeneratedValue
	@Column(name = "PRODUCT_CATALOG_ID", unique = true, nullable = false)
	private long productCatalogId;
	@Column(name = "IMAGE_URL")
	private String imageUrl;
	@Column(name = "SEMIKART_PART")
	private String semikartPart;
	@Column(name = "MANUFACTURER_PART", nullable = false)
	private String manufacturerPart;
	@Column(name = "DATA_SHEET_URL")
	private String dataSheet;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "ROHAS")
	private String rohs;
	@Column(name = "CATAGORY_ID", nullable = false)
	private long categoryId;
	@Column(name = "MANUFACTURER_ID", nullable = false)
	private long manufacturerId;
	@Column(name = "PRODUCT_STATUS")
	private String productStatus;
	@Column(name = "IS_NEW_PRODUCT")
	private String isNewProduct;
	@Column(name = "IS_FEAUTURED_PRODUCT")
	private String isFeaturedProduct;
	
	@Column(name = "HOT_DEAL")
	private String hotDeal;
	@Column(name = "POPULAR")
	private String popular;
	@Column(name = "LENGTH")
	private float length;
	@Column(name = "WIDTH")
	private float width;
	@Column(name = "HEIGHT")
	private float height;
	@Column(name = "weight")
	private float weight;
	@Column(name = "FULL_FILLMENT_TYPE")
	private String fullFillmentType;
	@Column(name = "STATUS")
	private String status;
	@Column(name = "TYPE")
	private String type;
	@Column(name = "BRAND")
	private String brand;
	@Column(name = "FEATURES")
	private String features;
	@Transient
	private MultipartFile imageFile;
	@Transient
	private MultipartFile pdfFile;
	
	public String getFullFillmentType() {
		return fullFillmentType;
	}

	public void setFullFillmentType(String fullFillmentType) {
		this.fullFillmentType = fullFillmentType;
	}


	@Column(name = "API_NAME")
	private String apiName;
	@Column(name = "PRODUCT_NAME")
	private String productName;
	@Column(name = "DIMENSION_UNITS")
	private String dimensionUnits;
	@Column(name = "WEIGHT_UNITS")
	private String weightUnits;     
	
	@Transient
	private SupplierCatalog  supplierCatalog;
	
	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getHotDeal() {
		return hotDeal;
	}

	public void setHotDeal(String hotDeal) {
		this.hotDeal = hotDeal;
	}

	public String getPopular() {
		return popular;
	}

	public void setPopular(String popular) {
		this.popular = popular;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	
	@Transient
	private String categoryName;
	@Transient
	private String manufacturerName;
	
	//@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Transient
	private Collection<SupplierCatalog> supplierCatalogList;

	/*
	 * @OneToOne(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	 * 
	 * @PrimaryKeyJoinColumn private SupplierCatalog supplierCatalog;
	 */
	@Transient
	private String priceBreaks;
	@Transient
	private SupplierCatalog supplierCatalogueImpl;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSemikartPart() {
		return semikartPart;
	}

	public void setSemikartPart(String semikartPart) {
		this.semikartPart = semikartPart;
	}

	public String getDataSheet() {
		return dataSheet;
	}

	public void setDataSheet(String dataSheet) {
		this.dataSheet = dataSheet;
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

	public String getManufacturerPart() {
		return manufacturerPart;
	}

	public void setManufacturerPart(String manufacturerPart) {
		this.manufacturerPart = manufacturerPart;
	}

	public SupplierCatalog getSupplierCatalogueImpl() {
		return supplierCatalogueImpl;
	}

	public void setSupplierCatalogueImpl(
			SupplierCatalog supplierCatalogueImpl) {
		this.supplierCatalogueImpl = supplierCatalogueImpl;
	}

	public String getPriceBreaks() {
		return priceBreaks;
	}

	public void setPriceBreaks(String priceBreaks) {
		this.priceBreaks = priceBreaks;
	}

	public long getProductCatalogId() {
		return productCatalogId;
	}

	public void setProductCatalogId(long productCatalogId) {
		this.productCatalogId = productCatalogId;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(long manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public String getIsNewProduct() {
		return isNewProduct;
	}

	public void setIsNewProduct(String isNewProduct) {
		this.isNewProduct = isNewProduct;
	}

	public String getIsFeaturedProduct() {
		return isFeaturedProduct;
	}

	public void setIsFeaturedProduct(String isFeaturedProduct) {
		this.isFeaturedProduct = isFeaturedProduct;
	}

	public Collection<SupplierCatalog> getSupplierCatalogList() {
		return supplierCatalogList;
	}

	public void setSupplierCatalogList(
			Collection<SupplierCatalog> supplierCatalogList) {
		this.supplierCatalogList = supplierCatalogList;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public SupplierCatalog getSupplierCatalog() {
		return supplierCatalog;
	}

	public void setSupplierCatalog(SupplierCatalog supplierCatalog) {
		this.supplierCatalog = supplierCatalog;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

	public MultipartFile getPdfFile() {
		return pdfFile;
	}

	public void setPdfFile(MultipartFile pdfFile) {
		this.pdfFile = pdfFile;
	}

	public String getDimensionUnits() {
		return dimensionUnits;
	}

	public void setDimensionUnits(String dimensionUnits) {
		this.dimensionUnits = dimensionUnits;
	}

	public String getWeightUnits() {
		return weightUnits;
	}

	public void setWeightUnits(String weightUnits) {
		this.weightUnits = weightUnits;
	}

	
}
