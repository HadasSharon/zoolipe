package com.mycompany.persistance.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

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
	@Column(name = "API_NAME")
	private String apiName;
	@Column(name = "PRODUCT_NAME")
	private String productName;
	
	
	@Transient
	private String mainSubCategoryName;
	@Transient
	private String manuFacturerLogo;
	
	public String getMainSubCategoryName() {
		return mainSubCategoryName;
	}

	public void setMainSubCategoryName(String mainSubCategoryName) {
		this.mainSubCategoryName = mainSubCategoryName;
	}

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

	

	public String getFullFillmentType() {
		return fullFillmentType;
	}

	public void setFullFillmentType(String fullFillmentType) {
		this.fullFillmentType = fullFillmentType;
	}



	@Transient
	private String menufacturer;
	@Transient
	private String category;
	@Transient
	private String tags;
	@Transient
	private String categoryName;
	@Transient
	private String manufacturerName;
	@Transient
	private String tax;
	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
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

	@Transient
	private String priceBreaks;
	@Transient
	private SupplierCatalog supplierCatalog;
	public SupplierCatalog getSupplierCatalog() {
		return supplierCatalog;
	}

	public void setSupplierCatalog(SupplierCatalog supplierCatalog) {
		this.supplierCatalog = supplierCatalog;
	}

	@Transient
	PriceTag priceTagImpl;
	@Transient
	ArrayList<PriceTag> priceTagImplList;
	@Transient
	List<PriceTag> priceTagImplLists;
	
	@Transient
	List<SupplierCatalog> supplierCatalogList;

	public List<SupplierCatalog> getSupplierCatalogList() {
		return supplierCatalogList;
	}

	public void setSupplierCatalogList(List<SupplierCatalog> supplierCatalogList) {
		this.supplierCatalogList = supplierCatalogList;
	}

	public List<PriceTag> getPriceTagImplLists() {
		return priceTagImplLists;
	}

	public void setPriceTagImplLists(List<PriceTag> priceTagImplLists) {
		this.priceTagImplLists = priceTagImplLists;
	}

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

	public String getMenufacturer() {
		return menufacturer;
	}

	public void setMenufacturer(String menufacturer) {
		this.menufacturer = menufacturer;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getManufacturerPart() {
		return manufacturerPart;
	}

	public void setManufacturerPart(String manufacturerPart) {
		this.manufacturerPart = manufacturerPart;
	}

	

	public PriceTag getPriceTagImpl() {
		return priceTagImpl;
	}

	public void setPriceTagImpl(PriceTag priceTagImpl) {
		this.priceTagImpl = priceTagImpl;
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

	public ArrayList<PriceTag> getPriceTagImplList() {
		return priceTagImplList;
	}

	public void setPriceTagImplList(ArrayList<PriceTag> priceTagImplList) {
		this.priceTagImplList = priceTagImplList;
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

	public String getManuFacturerLogo() {
		return manuFacturerLogo;
	}

	public void setManuFacturerLogo(String manuFacturerLogo) {
		this.manuFacturerLogo = manuFacturerLogo;
	}



}
