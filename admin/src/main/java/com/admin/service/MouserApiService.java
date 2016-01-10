package com.admin.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

public interface MouserApiService {

	// Calling API
	public ArrayList<ProductCatalog> processRequest(String[] keyword,HttpServletRequest request);

	// Saving Data
	public long saveManufacturer(ManufacturerConfig manufacturersImpl);

	public long getManufacturerId(String manufacturerName);

	public long saveMaincategory(MainCatagory mainCatagory);

	public long getMainCategoryId(String manufacturerName);

	public long saveMainSubcategory(MainSubCatagory mainCatagory);

	public long getMainSubCategoryId(String manufacturerName);

	public long saveCategory(Category category);

	public long getCategoryId(String manufacturerName);

	//
	public long getSupplierId(String supplierName);

	public long insertSupplier(SupplierConfig supplierConfig);

	public long getSupplierCatalogId(long supplierId, long productCatalogId);

	public List<SupplierPriceConfig> getSupplierPriceConfigDetails(
			long supplierId, long catgeoryId);

	public long insertSupplierCatalog(SupplierCatalog supplierCatalog);

	public long updateSupplierCatalog(SupplierCatalog supplierCatalog);

	public long saveProductCataLog(ProductCatalog productCatalogImpl);

	public long getProductCatalogId(String manufacturerPart);

	public long updateProductCataLog(ProductCatalog productCatalogImpl);

	public long saveSupplierCataLog(SupplierCatalog productCatalogImpl);

	public List<SubcategoryImpl> findVatDetailsOnSubCategoryName(
			String subCategoryName);

	public ArrayList<PriceTag> calculatePrice(
			Category category, String priceBreaks,
			double currencyINR, String manuFacturerPartNumber,
			long productCatalogId, long supplierId,String sku);

	public List<Keyword> getKeywordList();
}
