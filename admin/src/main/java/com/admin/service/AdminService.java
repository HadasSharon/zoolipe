package com.admin.service;

import java.io.File;
import java.util.List;

import com.admin.domain.AdminImpl;
import com.admin.domain.Category;
import com.admin.domain.MainCatagory;
import com.admin.domain.MainSubCatagory;
import com.admin.domain.ManufacturerConfig;
import com.admin.domain.PriceTag;
import com.admin.domain.ProductCatalog;
import com.admin.domain.SupplierConfig;
import com.admin.domain.SupplierCatalog;


public interface AdminService {
		public long savePassportType(AdminImpl accountImpl);
		public long saveAccount(AdminImpl adminImpl);

		public List<ProductCatalog> searchAllProduct(
				String productName);
		public long saveProductCataLog(ProductCatalog productCatalogImpl);

		public long saveSupplierCataLog(SupplierCatalog productCatalogImpl);



		//Methods for supplier config
		public List<SupplierConfig> fetchSupplierDetails();

		public long saveSupplier(SupplierConfig supplier);

		public void updateSupplier(SupplierConfig supplier);

		public void deleteSupplier(long id);

		public List<SupplierConfig> findSupplierBySupplierName(String supplier);

		public List<SupplierConfig> findSupplierBySupplierNameForEdit(
				String supplier, long id);
		public void updateSupplierLogo(Long manufacturerId, File pathData) ;
		//Supplier config ends here
		// Manufacturer starts here
		public List<ManufacturerConfig> fetchManufacturerDetails();

		public long saveManufacturer(ManufacturerConfig manufacturerConfigAdmin);

		public void updateManufacturer(
				ManufacturerConfig manufacturerConfigAdmin);

		public void deleteManufacturer(long id);

		public List<ManufacturerConfig> findManufacturerByManufacturerName(
				String manufacturer);

		public List<ManufacturerConfig> findManufacturerBySupplierNameForEdit(
				String manufacturer, long id);
	
		// Manufacturer ends here
		// Main catagory starts here
		public List<MainCatagory> fetchMainCatagoryDetails();

		public long saveMainCatagory(MainCatagory mainCatagoryAdmin);

		public void updateMainCatagory(MainCatagory mainCatagoryAdmin);

		public void deleteMainCatagory(long id);

		public List<MainCatagory> findMainCatagoryByMainCatagoryName(
				String mainCatagory);

		public List<MainCatagory> findMainCatagoryBySupplierNameForEdit(
				String mainCatagory, long id);

		// Main catagory ends here
		// Main sub catagory starts here
		public List<MainSubCatagory> fetchMainSubCatagoryDetails();

		public long saveMainSubCatagory(MainSubCatagory mainSubCatagoryAdmin);

		public void updateMainSubCatagory(MainSubCatagory mainSubCatagoryAdmin);

		public void deleteMainSubCatagory(long id);

		public List<MainSubCatagory> findMainSubCatagoryByMainSubCatagoryName(
				String mainSubCatagory);

		public List<MainSubCatagory> findMainSubCatagoryBySupplierNameForEdit(
				String mainSubCatagory, long id);

		// Main sub catagory ends here
		// catagory starts here
		public List<Category> fetchCategoryDetails();

		public long saveCategory(Category categoryAdmin);

		public void updateCategory(Category categoryAdmin);

		public void deleteCategory(long id);

		public List<Category> findCategoryByCategoryName(String catagory);

		public List<Category> findCategoryBySupplierNameForEdit(
				String catagory, long id);
		// Category ends here
		// ProductCatalog starts here
		public List<ProductCatalog> fetchProductCatalogDetails();

		public long saveProductCatalog(ProductCatalog categoryAdmin);

		public void updateProductCatalog(ProductCatalog categoryAdmin);

		public void deleteProductCatalog(long id);

		public List<ProductCatalog> findProductCatalogByProductCatalogName(
				String catagory);

		public List<ProductCatalog> findProductCatalogBySupplierNameForEdit(
				String catagory, long id);
		// product catalog ends here
		public List<SupplierCatalog> getSuplierCatalogOnProductId(
				long productCatalogId);
		public List<PriceTag> getPriceSlabDetails(long productCatalogId,
				long supplierId);

}
