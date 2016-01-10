package com.mycompany.persistance.dao;

import java.util.List;

import com.admin.domain.AdminImpl;
import com.mycompany.persistance.domain.Category;
import com.mycompany.persistance.domain.MainCatagory;
import com.mycompany.persistance.domain.MainSubCatagory;
import com.mycompany.persistance.domain.ManufacturerConfig;
import com.mycompany.persistance.domain.PriceTag;
import com.mycompany.persistance.domain.ProductCatalog;
import com.mycompany.persistance.domain.SubcategoryImpl;
import com.mycompany.persistance.domain.SupplierCatalog;
import com.mycompany.persistance.domain.SupplierConfig;
import com.mycompany.persistance.domain.SupplierPriceConfig;


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

	
}
