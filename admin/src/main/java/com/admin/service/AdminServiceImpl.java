package com.admin.service;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.admin.dao.AdminDao;
import com.admin.domain.AdminImpl;
import com.admin.domain.Category;
import com.admin.domain.MainCatagory;
import com.admin.domain.MainSubCatagory;
import com.admin.domain.ManufacturerConfig;
import com.admin.domain.PriceTag;
import com.admin.domain.ProductCatalog;
import com.admin.domain.SupplierConfig;
import com.admin.domain.SupplierCatalog;

@Service("blAdminService")
public class AdminServiceImpl implements AdminService {

	@Resource(name = "blAdminDao")
	protected AdminDao adminDao;

	@Override
	public long savePassportType(AdminImpl adminImpl) {
		// TODO Auto-generated method stub
		return adminDao.saveAccount(adminImpl);
	}

	@Override
	public List<ProductCatalog> searchAllProduct(String productName) {
		// TODO Auto-generated method stub
		return adminDao.searchAllProduct(productName);
	}

	@Override
	public long saveProductCataLog(ProductCatalog productCatalogImpl) {
		// TODO Auto-generated method stub
		return adminDao.saveProductCataLog(productCatalogImpl);
	}

	@Override
	public long saveSupplierCataLog(
			SupplierCatalog supplierCatalogueImplAdmin) {
		// TODO Auto-generated method stub
		return adminDao.saveSupplierCataLog(supplierCatalogueImplAdmin);
	}

	// Supplier config starts here
	@Override
	public List<SupplierConfig> fetchSupplierDetails() {
		// TODO Auto-generated method stub
		return adminDao.fetchSupplierDetails();
	}

	@Override
	public long saveSupplier(SupplierConfig supplier) {
		// TODO Auto-generated method stub
		return adminDao.saveSupplier(supplier);
	}

	@Override
	public void updateSupplier(SupplierConfig supplier) {
		// TODO Auto-generated method stub
		adminDao.updateSupplier(supplier);
	}

	@Override
	public void deleteSupplier(long id) {
		// TODO Auto-generated method stub
		adminDao.deleteSupplier(id);
	}

	@Override
	public List<SupplierConfig> findSupplierBySupplierName(String supplier) {
		// TODO Auto-generated method stub
		return adminDao.findSupplierBySupplierName(supplier);
	}

	@Override
	public List<SupplierConfig> findSupplierBySupplierNameForEdit(
			String supplier, long id) {
		// TODO Auto-generated method stub
		return adminDao.findSupplierBySupplierNameForEdit(supplier, id);
	}
	@Override
	public void updateSupplierLogo(Long supplierId, File pathData) {
		// TODO Auto-generated method stub
		 adminDao.updateSupplierLogo(supplierId, pathData);
	}
	// Supplier config ends here
	// Manufacturer starts here
	@Override
	public List<ManufacturerConfig> fetchManufacturerDetails() {
		// TODO Auto-generated method stub
		return adminDao.fetchManufacturerDetails();
	}

	@Override
	public long saveManufacturer(ManufacturerConfig manufacturerConfigAdmin) {
		// TODO Auto-generated method stub
		return adminDao.saveManufacturer(manufacturerConfigAdmin);
	}

	@Override
	public void updateManufacturer(
			ManufacturerConfig manufacturerConfigAdmin) {
		// TODO Auto-generated method stub
		System.out.println("inside update service");
		adminDao.updateManufacturer(manufacturerConfigAdmin);
	}

	@Override
	public void deleteManufacturer(long id) {
		// TODO Auto-generated method stub
		adminDao.deleteManufacturer(id);
	}

	@Override
	public List<ManufacturerConfig> findManufacturerByManufacturerName(
			String manufacturer) {
		// TODO Auto-generated method stub
		return adminDao.findManufacturerByManufacturerName(manufacturer);
	}

	@Override
	public List<ManufacturerConfig> findManufacturerBySupplierNameForEdit(
			String manufacturer, long id) {
		// TODO Auto-generated method stub
		return adminDao.findManufacturerBySupplierNameForEdit(manufacturer, id);
	}

	// Manufacturer ends here

	// mainCategory starts here
	@Override
	public List<MainCatagory> fetchMainCatagoryDetails() {
		// TODO Auto-generated method stub
		return adminDao.fetchMainCatagoryDetails();
	}

	@Override
	public long saveMainCatagory(MainCatagory mainCatagoryAdmin) {
		// TODO Auto-generated method stub
		return adminDao.saveMainCatagory(mainCatagoryAdmin);
	}

	@Override
	public void updateMainCatagory(MainCatagory mainCatagoryAdmin) {
		// TODO Auto-generated method stub
		adminDao.updateMainCatagory(mainCatagoryAdmin);
	}

	@Override
	public void deleteMainCatagory(long id) {
		// TODO Auto-generated method stub
		adminDao.deleteMainCatagory(id);
	}

	@Override
	public List<MainCatagory> findMainCatagoryByMainCatagoryName(
			String mainCatagory) {
		// TODO Auto-generated method stub
		return adminDao.findMainCatagoryByMainCatagoryName(mainCatagory);
	}

	@Override
	public List<MainCatagory> findMainCatagoryBySupplierNameForEdit(
			String mainCatagory, long id) {
		// TODO Auto-generated method stub
		return adminDao.findMainCatagoryBySupplierNameForEdit(mainCatagory, id);
	}

	// Main Catagoabry ends here

	// Main subCatagory starts here
	@Override
	public List<MainSubCatagory> fetchMainSubCatagoryDetails() {
		// TODO Auto-generated method stub
		return adminDao.fetchMainSubCatagoryDetails();
	}

	@Override
	public long saveMainSubCatagory(MainSubCatagory mainSubCatagoryAdmin) {
		// TODO Auto-generated method stub
		return adminDao.saveMainSubCatagory(mainSubCatagoryAdmin);
	}

	@Override
	public void updateMainSubCatagory(MainSubCatagory mainSubCatagoryAdmin) {
		// TODO Auto-generated method stub
		adminDao.updateMainSubCatagory(mainSubCatagoryAdmin);
	}

	@Override
	public void deleteMainSubCatagory(long id) {
		// TODO Auto-generated method stub
		adminDao.deleteMainSubCatagory(id);
	}

	@Override
	public List<MainSubCatagory> findMainSubCatagoryByMainSubCatagoryName(
			String mainSubCatagory) {
		// TODO Auto-generated method stub
		return adminDao
				.findMainSubCatagoryByMainSubCatagoryName(mainSubCatagory);
	}

	@Override
	public List<MainSubCatagory> findMainSubCatagoryBySupplierNameForEdit(
			String mainSubCatagory, long id) {
		// TODO Auto-generated method stub
		return adminDao.findMainSubCatagoryBySupplierNameForEdit(
				mainSubCatagory, id);
	}

	// Main subCatagory ends here

	// Catagory starts here
	@Override
	public List<Category> fetchCategoryDetails() {
		// TODO Auto-generated method stub
		return adminDao.fetchCategoryDetails();
	}

	@Override
	public long saveCategory(Category categoryAdmin) {
		// TODO Auto-generated method stub
		return adminDao.saveCategory(categoryAdmin);
	}

	@Override
	public void updateCategory(Category categoryAdmin) {
		// TODO Auto-generated method stub
		adminDao.updateCategory(categoryAdmin);
	}

	@Override
	public void deleteCategory(long id) {
		// TODO Auto-generated method stub
		adminDao.deleteCategory(id);
	}

	@Override
	public List<Category> findCategoryByCategoryName(String catagory) {
		// TODO Auto-generated method stub
		return adminDao.findCategoryByCategoryName(catagory);
	}

	@Override
	public List<Category> findCategoryBySupplierNameForEdit(
			String catagory, long id) {
		// TODO Auto-generated method stub
		return adminDao.findCategoryBySupplierNameForEdit(catagory, id);
	}

	// Category ends here

	@Override
	public long saveAccount(AdminImpl adminImpl) {
		// TODO Auto-generated method stub
		return adminDao.saveAccount(adminImpl);
	}

	// Productcatalog starts here
	@Override
	public List<ProductCatalog> fetchProductCatalogDetails() {
		// TODO Auto-generated method stub
		return adminDao.fetchProductCatalogDetails();
	}

	@Override
	public long saveProductCatalog(ProductCatalog categoryAdmin) {
		// TODO Auto-generated method stub
		return adminDao.saveProductCatalog(categoryAdmin);
	}

	@Override
	public void updateProductCatalog(ProductCatalog categoryAdmin) {
		// TODO Auto-generated method stub
		adminDao.updateProductCatalog(categoryAdmin);
	}

	@Override
	public void deleteProductCatalog(long id) {
		// TODO Auto-generated method stub
		adminDao.deleteProductCatalog(id);
	}

	@Override
	public List<ProductCatalog> findProductCatalogByProductCatalogName(
			String catagory) {
		// TODO Auto-generated method stub
		return adminDao.findProductCatalogByProductCatalogName(catagory);
	}

	@Override
	public List<ProductCatalog> findProductCatalogBySupplierNameForEdit(
			String catagory, long id) {
		// TODO Auto-generated method stub
		return adminDao.findProductCatalogBySupplierNameForEdit(catagory, id);
	}
	// Product catalog ends here

	@Override
	public List<SupplierCatalog> getSuplierCatalogOnProductId(
			long productCatalogId) {
		// TODO Auto-generated method stub
		return adminDao.getSuplierCatalogOnProductId(productCatalogId);
	}

	@Override
	public List<PriceTag> getPriceSlabDetails(long productCatalogId,
			long supplierId) {
		// TODO Auto-generated method stub
		return adminDao.getPriceSlabDetails(productCatalogId,supplierId);
	}


	
}
