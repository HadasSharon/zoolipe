package com.admin.dao;

import java.util.List;




import com.admin.domain.Category;
import com.admin.domain.Keyword;
import com.admin.domain.MainCatagory;
import com.admin.domain.MainSubCatagory;
import com.admin.domain.ManufacturerConfig;
import com.admin.domain.PriceTag;
import com.admin.domain.ProductCatalog;
import com.admin.domain.SubcategoryImpl;
import com.admin.domain.SupplierCatalog;
import com.admin.domain.SupplierConfig;
import com.admin.domain.SupplierPriceConfig;



public interface MouserApiDao {

	public long saveManufacturer(ManufacturerConfig manufacturerConfig);

	public long getManufacturerId(String manufacturerName);

	public long saveMaincategory(MainCatagory mainCatagory);

	public long getMainCategoryId(String manufacturerName);

	public long saveMainSubcategory(MainSubCatagory mainCatagory);

	public long getMainSubCategoryId(String manufacturerName);

	public long saveCategory(Category category);

	public long getCategoryId(String manufacturerName);
	
	
	public long getSupplierId(String supplierName);
	public long insertSupplier(SupplierConfig supplierConfig);
	public long getSupplierCatalogId(long supplierId,long productCatalogId);
	public long insertSupplierCatalog(SupplierCatalog supplierCatalog);
	public long updateSupplierCatalog(SupplierCatalog supplierCatalog);
	public List<SupplierPriceConfig> getSupplierPriceConfigDetails(long supplierId, long catgeoryId);
	public long getSupplierPriceConfigId(long supplierId,long categoryId);
	public long insertSupplierPriceConfig(SupplierPriceConfig  supplierPriceConfig);
	public SupplierPriceConfig getSupplierPriceConfigData(long  supplierPriceConfigId);
	public long updateSupplierPriceConfig(SupplierPriceConfig  supplierPriceConfig);
	
	 
	public long getPriceTagId(long supplierId,long productCatalogId,int quentity);
	public long insertPriceTag(PriceTag priceTagImpl);
	public long updatePriceTag(PriceTag priceTagImpl);
	
	
	

	public long saveProductCataLog(ProductCatalog productCatalogImpl);
	public long getProductCatalogId(String manufacturerPart);
	public long updateProductCataLog(ProductCatalog productCatalogImpl);
	

	public long saveSupplierCataLog(SupplierCatalog productCatalogImpl);
	public long updateSupplierCataLog(SupplierCatalog productCatalogImpl);

	public List<SubcategoryImpl> findVatDetailsOnSubCategoryName(
			String subCategoryName);

	public List<Keyword> getKeywordList() ;
	public double getCurrency();
	public void updateCurrency(String currencyValue);
	
	
	public Category getCategoryDetailsBasedOn(
			long categoryId);
	
	public void updateMainSubCategory(long mainCategoryId,long mainSubCtagoryId);
	public void updateCategory(long mainSubCtagoryId,long  categoryId);
}
