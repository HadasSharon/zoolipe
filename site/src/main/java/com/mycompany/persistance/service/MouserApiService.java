package com.mycompany.persistance.service;

import java.util.ArrayList;
import java.util.List;

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

public interface MouserApiService {
	
	
	
	//Calling API
	public ArrayList<ProductCatalog> processRequest(String keyword,
			String checkBox[]);
	
	//Saving Data
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
	public long getSupplierCatalogId(long supplierId,long productCatalogId);
	public List<SupplierPriceConfig> getSupplierPriceConfigDetails(long supplierId, long catgeoryId);
	public long insertSupplierCatalog(SupplierCatalog supplierCatalog);
	public long updateSupplierCatalog(SupplierCatalog supplierCatalog);
	
	
	
	

	public long saveProductCataLog(ProductCatalog productCatalogImpl);
	public long getProductCatalogId(String manufacturerPart);
	public long updateProductCataLog(ProductCatalog productCatalogImpl);
	

	public long saveSupplierCataLog(SupplierCatalog productCatalogImpl);


	public List<SubcategoryImpl> findVatDetailsOnSubCategoryName(
			String subCategoryName);


	public ArrayList<PriceTag> calculatePrice(
			List<SupplierPriceConfig> subcategoryImplList, String priceBreaks,
			double currencyINR, String manuFacturerPartNumber,
			long productCatalogId, long supplierId);
	

}
