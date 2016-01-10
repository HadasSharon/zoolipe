package com.admin.dao;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;

import org.broadleafcommerce.openadmin.server.service.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.Util.DatabaseConstants;
import com.admin.domain.AdminImpl;
import com.admin.domain.Category;
import com.admin.domain.MainCatagory;
import com.admin.domain.MainSubCatagory;
import com.admin.domain.ManufacturerConfig;
import com.admin.domain.PriceTag;
import com.admin.domain.ProductCatalog;
import com.admin.domain.SupplierConfig;
import com.admin.domain.SupplierCatalog;

@Repository("blAdminDao")
public class AdminDaoImpl implements AdminDao {
	@PersistenceContext(unitName = "blPU")
	protected EntityManager entityManager;

	@Autowired
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// JNDI TESTING

	@Override
	public long saveAccount(AdminImpl adminImpl) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO ACCOUNT "
				+ "(ACCOUNT_ID, ACCOUNT_NBR, NAME) VALUES (?, ?, ?)";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, 1111111);
			ps.setString(2, "JITU");
			ps.setString(3, "JITU");
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return 0;
	}

	//
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public List<ProductCatalog> searchAllProduct(String productName) {

		try {

			Query query = entityManager.createQuery("FROM ProductCatalog ");
			return query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public long saveProductCataLog(ProductCatalog productCatalogImpl) {
		// TODO Auto-generated method stub
		try {

			entityManager.persist(productCatalogImpl);
			entityManager.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productCatalogImpl.getProductCatalogId();
	}

	@Override
	public long saveSupplierCataLog(SupplierCatalog productCatalogImpl) {
		// TODO Auto-generated method stub
		return 0;
	}

	// SupplierAdmin config
	// SupplierAdmin
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public List<SupplierConfig> fetchSupplierDetails() {
		try {
			TypedQuery<SupplierConfig> query = entityManager.createQuery(
					"SELECT c FROM SupplierConfig c", SupplierConfig.class);
			return query.getResultList();
		} catch (PersistenceException persistenceException) {

		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public long saveSupplier(SupplierConfig supplier) {
		try {
			entityManager.persist(supplier);
			entityManager.flush();
		} catch (PersistenceException persistenceException) {
			persistenceException.printStackTrace();
		}
		return supplier.getSupplierId();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public void updateSupplier(SupplierConfig supplier) {
		try {

			entityManager.merge(supplier);
			entityManager.flush();
		} catch (PersistenceException persistenceException) {
			persistenceException.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public void deleteSupplier(long id) {
		try {
			SupplierConfig supplier = entityManager.find(SupplierConfig.class,
					id);
			System.out.println("supplier" + supplier.getSupplierId());
			entityManager.remove(supplier);
			entityManager.flush();
		} catch (PersistenceException persistenceException) {
			persistenceException.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public List<SupplierConfig> findSupplierBySupplierName(String supplier) {
		try {
			TypedQuery<SupplierConfig> query = entityManager
					.createQuery(
							"SELECT c FROM SupplierConfig c WHERE lower(c.supplierName) LIKE :supplierName",
							SupplierConfig.class);
			return query.setParameter("supplierName",
					"%" + supplier.toLowerCase() + "%").getResultList();
		} catch (PersistenceException persistenceException) {

		}

		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public List<SupplierConfig> findSupplierBySupplierNameForEdit(
			String supplier, long id) {
		try {
			TypedQuery<SupplierConfig> query = entityManager.createQuery(
					"SELECT c FROM SupplierConfig c WHERE   c.id=:id",
					SupplierConfig.class);
			return query.setParameter("id", id).getResultList();
		} catch (PersistenceException persistenceException) {

		}

		return null;
	}

	// //////////Supplier COnfig ends here
	// /Manufacturer Starts here
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public List<ManufacturerConfig> fetchManufacturerDetails() {
		try {
			TypedQuery<ManufacturerConfig> query = entityManager
					.createQuery(
							"SELECT c FROM ManufacturerConfig c order by c.manuFacturerName",
							ManufacturerConfig.class);
			return query.getResultList();
		} catch (PersistenceException persistenceException) {

		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public long saveManufacturer(ManufacturerConfig manufacturerConfigAdmin) {
		try {
			entityManager.persist(manufacturerConfigAdmin);
			entityManager.flush();
		} catch (PersistenceException persistenceException) {
			persistenceException.printStackTrace();
		}
		return manufacturerConfigAdmin.getManufacturerId();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public void updateManufacturer(ManufacturerConfig manufacturerConfigAdmin) {
		try {
			System.out.println("inside update manufacturer Dao");
			entityManager.merge(manufacturerConfigAdmin);
			entityManager.flush();
		} catch (PersistenceException persistenceException) {
			persistenceException.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public void deleteManufacturer(long id) {
		try {
			ManufacturerConfig manufacturerConfigAdmin = entityManager.find(
					ManufacturerConfig.class, id);
			entityManager.remove(manufacturerConfigAdmin);
			entityManager.flush();
		} catch (PersistenceException persistenceException) {
			persistenceException.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public List<ManufacturerConfig> findManufacturerByManufacturerName(
			String manufacturer) {
		try {
			TypedQuery<ManufacturerConfig> query = entityManager
					.createQuery(
							"SELECT c FROM ManufacturerConfig c WHERE lower(c.manuFacturerName) LIKE :manuFacturerName",
							ManufacturerConfig.class);
			return query.setParameter("manuFacturerName",
					"%" + manufacturer.toLowerCase() + "%").getResultList();
		} catch (PersistenceException persistenceException) {

		}

		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public List<ManufacturerConfig> findManufacturerBySupplierNameForEdit(
			String supplier, long id) {
		try {
			TypedQuery<ManufacturerConfig> query = entityManager.createQuery(
					"SELECT c FROM ManufacturerConfig c WHERE   c.id=:id",
					ManufacturerConfig.class);
			return query.setParameter("id", id).getResultList();
		} catch (PersistenceException persistenceException) {

		}

		return null;
	}

	// Manufacturer ends here
	// MainCatagory Starts here
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public List<MainCatagory> fetchMainCatagoryDetails() {
		try {
			TypedQuery<MainCatagory> query = entityManager.createQuery(
					"SELECT c FROM MainCatagory c order by c.maincatagoryName",
					MainCatagory.class);
			return query.getResultList();
		} catch (PersistenceException persistenceException) {

		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public long saveMainCatagory(MainCatagory mainCatagoryAdmin) {
		try {
			entityManager.persist(mainCatagoryAdmin);
			entityManager.flush();
		} catch (PersistenceException persistenceException) {
			persistenceException.printStackTrace();
		}
		return mainCatagoryAdmin.getMainCatagoryId();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public void updateMainCatagory(MainCatagory mainCatagoryAdmin) {
		try {

			entityManager.merge(mainCatagoryAdmin);
			entityManager.flush();
		} catch (PersistenceException persistenceException) {
			persistenceException.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public void deleteMainCatagory(long id) {
		try {
			MainCatagory mainCatagoryAdmin = entityManager.find(
					MainCatagory.class, id);
			entityManager.remove(mainCatagoryAdmin);
			entityManager.flush();
		} catch (PersistenceException persistenceException) {
			persistenceException.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public List<MainCatagory> findMainCatagoryByMainCatagoryName(
			String mainCatagory) {
		try {
			TypedQuery<MainCatagory> query = entityManager
					.createQuery(
							"SELECT c FROM MainCatagory c WHERE lower(c.maincatagoryName) LIKE :maincatagoryName",
							MainCatagory.class);
			return query.setParameter("maincatagoryName",
					"%" + mainCatagory.toLowerCase() + "%").getResultList();
		} catch (PersistenceException persistenceException) {

		}

		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public List<MainCatagory> findMainCatagoryBySupplierNameForEdit(
			String mainCatagory, long id) {
		try {
			TypedQuery<MainCatagory> query = entityManager.createQuery(
					"SELECT c FROM MainCatagory c WHERE   c.id=:id",
					MainCatagory.class);
			return query.setParameter("id", id).getResultList();
		} catch (PersistenceException persistenceException) {

		}

		return null;
	}

	// MainCatagory Ends here
	// Main Sub catagory Starts here
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public List<MainSubCatagory> fetchMainSubCatagoryDetails() {
		String sql = DatabaseConstants.SEARCH_MAIN_SUB_CATEGORY_DETAILS;
		Connection conn = null;
		List<MainSubCatagory> mainSubCatagoriesList = new ArrayList<MainSubCatagory>();
		MainSubCatagory mainSubCatagory = null;
		try {
			conn = dataSource.getConnection();
			Statement statement1 = conn.createStatement();
			ResultSet resultSet1 = statement1.executeQuery(sql);
			while (resultSet1.next()) {
				mainSubCatagory = new MainSubCatagory();
				mainSubCatagory.setMainSubCatagoryName(resultSet1
						.getString(DatabaseConstants.MAIN_SUB_CATEGORY_NAME));
				mainSubCatagory.setMainSubCatagoryId(resultSet1
						.getLong(DatabaseConstants.MAIN_SUB_CATEGORY_ID));
				mainSubCatagory.setMainCatagoryId(resultSet1
						.getLong(DatabaseConstants.MAIN_CATAGORY_ID));
				mainSubCatagory.setMainCategoryName(resultSet1
						.getString(DatabaseConstants.MAIN_CATAGORY_NAME));
				mainSubCatagoriesList.add(mainSubCatagory);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return mainSubCatagoriesList;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public long saveMainSubCatagory(MainSubCatagory mainSubCatagoryAdmin) {
		try {
			entityManager.persist(mainSubCatagoryAdmin);
			entityManager.flush();
		} catch (PersistenceException persistenceException) {
			persistenceException.printStackTrace();
		}
		return mainSubCatagoryAdmin.getMainSubCatagoryId();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public void updateMainSubCatagory(MainSubCatagory mainSubCatagoryAdmin) {
		try {

			entityManager.merge(mainSubCatagoryAdmin);
			entityManager.flush();
		} catch (PersistenceException persistenceException) {
			persistenceException.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public void deleteMainSubCatagory(long id) {
		try {
			MainSubCatagory mainSubCatagoryAdmin = entityManager.find(
					MainSubCatagory.class, id);
			entityManager.remove(mainSubCatagoryAdmin);
			entityManager.flush();
		} catch (PersistenceException persistenceException) {
			persistenceException.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public List<MainSubCatagory> findMainSubCatagoryByMainSubCatagoryName(
			String mainSubCatagory) {
		try {
			TypedQuery<MainSubCatagory> query = entityManager
					.createQuery(
							"SELECT c FROM MainSubCatagory c WHERE lower(c.mainSubCatagoryName) LIKE :mainSubCatagoryName",
							MainSubCatagory.class);
			return query.setParameter("mainSubCatagoryName",
					"%" + mainSubCatagory.toLowerCase() + "%").getResultList();
		} catch (PersistenceException persistenceException) {

		}

		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public List<MainSubCatagory> findMainSubCatagoryBySupplierNameForEdit(
			String mainSubCatagory, long id) {
		try {
			TypedQuery<MainSubCatagory> query = entityManager.createQuery(
					"SELECT c FROM MainSubCatagory c WHERE   c.id=:id",
					MainSubCatagory.class);
			return query.setParameter("id", id).getResultList();
		} catch (PersistenceException persistenceException) {

		}

		return null;
	}

	// Main subcatagory ends here
	// Category starts here
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public List<Category> fetchCategoryDetails() {
		String sql = DatabaseConstants.SEARCH_MAIN_CATEGORY_DETAILS;
		Connection conn = null;
		List<Category> categoriesList = new ArrayList<Category>();
		Category category = null;
		try {
			conn = dataSource.getConnection();
			Statement statement1 = conn.createStatement();
			ResultSet resultSet1 = statement1.executeQuery(sql);
			while (resultSet1.next()) {
				category = new Category();
				category.setMainSubCategoryName(resultSet1
						.getString(DatabaseConstants.MAIN_SUB_CATEGORY_NAME));
				category.setMainSubcatagoryId(resultSet1
						.getLong(DatabaseConstants.MAIN_SUB_CATEGORY_ID));
				category.setMainCategoryId(resultSet1
						.getLong(DatabaseConstants.MAIN_CATAGORY_ID));
				category.setMainCategoryName(resultSet1
						.getString(DatabaseConstants.MAIN_CATAGORY_NAME));
				category.setCatagoryId(resultSet1
						.getLong(DatabaseConstants.CATAGORY_ID));
				category.setCatagoryName(resultSet1
						.getString(DatabaseConstants.CATAGORY_NAME));
				category.setVat(resultSet1.getFloat(DatabaseConstants.VAT));
				category.setHfi(resultSet1.getFloat(DatabaseConstants.HFI));
				category.setDuty(resultSet1.getFloat(DatabaseConstants.DUTY));
				category.setCurrencyMarkup(resultSet1
						.getFloat(DatabaseConstants.CURRENCY_MARKUP));

				categoriesList.add(category);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return categoriesList;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public long saveCategory(Category catagoryAdmin) {
		try {
			entityManager.persist(catagoryAdmin);
			entityManager.flush();
		} catch (PersistenceException persistenceException) {
			persistenceException.printStackTrace();
		}
		return catagoryAdmin.getCatagoryId();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public void updateCategory(Category catagoryAdmin) {
		try {

			entityManager.merge(catagoryAdmin);
			entityManager.flush();
		} catch (PersistenceException persistenceException) {
			persistenceException.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public void deleteCategory(long id) {
		try {
			Category catagoryAdmin = entityManager.find(Category.class, id);
			entityManager.remove(catagoryAdmin);
			entityManager.flush();
		} catch (PersistenceException persistenceException) {
			persistenceException.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public List<Category> findCategoryByCategoryName(String catagory) {
		try {
			TypedQuery<Category> query = entityManager
					.createQuery(
							"SELECT c FROM Category c WHERE lower(c.catagoryName) LIKE :catagoryName",
							Category.class);
			return query.setParameter("catagoryName",
					"%" + catagory.toLowerCase() + "%").getResultList();
		} catch (PersistenceException persistenceException) {

		}

		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public List<Category> findCategoryBySupplierNameForEdit(String catagory,
			long id) {
		try {
			TypedQuery<Category> query = entityManager
					.createQuery("SELECT c FROM Category c WHERE   c.id=:id",
							Category.class);
			return query.setParameter("id", id).getResultList();
		} catch (PersistenceException persistenceException) {

		}

		return null;
	}

	// Category ends here
	// Pricecatalog starts here
	@Override
	public List<ProductCatalog> fetchProductCatalogDetails() {
		String sql = DatabaseConstants.SEARCH_PRODUCT;
		Connection conn = null;
		List<ProductCatalog> productCatalogImplAdmins = new ArrayList<ProductCatalog>();
		ProductCatalog productCatalogImplAdmin = null;
		try {
			conn = dataSource.getConnection();
			Statement statement1 = conn.createStatement();
			ResultSet resultSet1 = statement1.executeQuery(sql);
			while (resultSet1.next()) {
				productCatalogImplAdmin = new ProductCatalog();
				productCatalogImplAdmin.setCategoryName(resultSet1
						.getString(DatabaseConstants.CATAGORY_NAME));
				productCatalogImplAdmin.setManufacturerName(resultSet1
						.getString(DatabaseConstants.MANUFACTURER_NAME));
				productCatalogImplAdmin.setManufacturerPart(resultSet1
						.getString(DatabaseConstants.MANUFACTURER_PART));
				productCatalogImplAdmin.setSemikartPart(resultSet1
						.getString(DatabaseConstants.SEMIKART_PART));
				productCatalogImplAdmin.setIsFeaturedProduct(resultSet1
						.getString(DatabaseConstants.IS_FEAUTURED_PRODUCT));
				productCatalogImplAdmin.setIsNewProduct(resultSet1
						.getString(DatabaseConstants.IS_NEW_PRODUCT));
				productCatalogImplAdmin.setDataSheet(resultSet1
						.getString(DatabaseConstants.DATA_SHEET_URL));
				productCatalogImplAdmin.setProductStatus(resultSet1
						.getString(DatabaseConstants.PRODUCT_STATUS));
				productCatalogImplAdmin.setDescription(resultSet1
						.getString(DatabaseConstants.DESCRIPTION));
				productCatalogImplAdmin.setImageUrl(resultSet1
						.getString(DatabaseConstants.IMAGE_URL));
				productCatalogImplAdmin.setProductCatalogId(resultSet1
						.getLong(DatabaseConstants.PRODUCT_CATALOG_ID));
				productCatalogImplAdmins.add(productCatalogImplAdmin);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return productCatalogImplAdmins;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public long saveProductCatalog(ProductCatalog manufacturerConfigAdmin) {
		try {
			entityManager.persist(manufacturerConfigAdmin);
			entityManager.flush();
		} catch (PersistenceException persistenceException) {
			persistenceException.printStackTrace();
		}
		return manufacturerConfigAdmin.getProductCatalogId();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public void updateProductCatalog(ProductCatalog manufacturerConfigAdmin) {
		try {

			entityManager.merge(manufacturerConfigAdmin);
			entityManager.flush();
		} catch (PersistenceException persistenceException) {
			persistenceException.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public void deleteProductCatalog(long id) {
		try {
			ProductCatalog manufacturerConfigAdmin = entityManager.find(
					ProductCatalog.class, id);
			entityManager.remove(manufacturerConfigAdmin);
			entityManager.flush();
		} catch (PersistenceException persistenceException) {
			persistenceException.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public List<ProductCatalog> findProductCatalogByProductCatalogName(
			String manufacturerPart) {
		try {
			TypedQuery<ProductCatalog> query = entityManager
					.createQuery(
							"SELECT c FROM ProductCatalog c WHERE lower(c.manufacturerPart) LIKE :manufacturerPart",
							ProductCatalog.class);
			return query.setParameter("manufacturerPart",
					"%" + manufacturerPart.toLowerCase() + "%").getResultList();
		} catch (PersistenceException persistenceException) {

		}

		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public List<ProductCatalog> findProductCatalogBySupplierNameForEdit(
			String supplier, long id) {
		try {
			TypedQuery<ProductCatalog> query = entityManager.createQuery(
					"SELECT c FROM ProductCatalog c WHERE   c.id=:id",
					ProductCatalog.class);
			return query.setParameter("id", id).getResultList();
		} catch (PersistenceException persistenceException) {

		}

		return null;
	}

	// price catalog ends here

	@Override
	public void updateSupplierLogo(Long supplierId, File pathData) {
		// TODO Auto-generated method stub
		FileInputStream fis;
		PreparedStatement ps;
		Connection connection = null;

		try {
			connection = dataSource.getConnection();

			fis = new FileInputStream(pathData);

			ps = connection
					.prepareStatement(DatabaseConstants.UPDATE_SUPPLIER_LOGO);
			ps.setBinaryStream(1, fis, (int) pathData.length());
			ps.setLong(2, supplierId);
			ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	// ////////////////
	@Override
	public List<SupplierCatalog> getSuplierCatalogOnProductId(
			long productCatalogId) {
		// TODO Auto-generated method stub
		String sql = DatabaseConstants.GET_SUPPLIER_DETAILS;

		List<SupplierCatalog> supplierCatalogList = new ArrayList<SupplierCatalog>();
		Connection conn = null;
		SupplierCatalog supplierCatalog = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, productCatalogId);
			ResultSet resultSet1 = pstmt.executeQuery();
			while (resultSet1.next()) {
				supplierCatalog = new SupplierCatalog();
				supplierCatalog.setSupplierName(resultSet1
						.getString(DatabaseConstants.SUPPLIER_NAME));
				supplierCatalog.setLeadTime(resultSet1
						.getInt(DatabaseConstants.LEAD_TIME));
				supplierCatalog.setStock(resultSet1
						.getInt(DatabaseConstants.STOCK));
				supplierCatalog.setMinu(resultSet1
						.getInt(DatabaseConstants.MINI));
				supplierCatalog.setMult(resultSet1
						.getInt(DatabaseConstants.MULT));
				supplierCatalog.setSupplierId(resultSet1
						.getLong(DatabaseConstants.SUPPLIER_ID));

				List<PriceTag> priceTagImpls = getPriceSlabDetails(
						productCatalogId, supplierCatalog.getSupplierId());
				System.out.println("PriceTag size" + priceTagImpls.size());
				supplierCatalog.setPriceTagImplLists(priceTagImpls);
				supplierCatalogList.add(supplierCatalog);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return supplierCatalogList;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public List<PriceTag> getPriceSlabDetails(long productCatalogId,
			long supplierId) {
		String sql = DatabaseConstants.GET_PRICE_DETAILS;

		List<PriceTag> priceTagsList = new ArrayList<PriceTag>();
		System.out.println("productCatalogId" + productCatalogId + "supplierId"
				+ supplierId);
		PriceTag priceTag = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, productCatalogId);
			pstmt.setLong(2, supplierId);
			ResultSet resultSet1 = pstmt.executeQuery();
			while (resultSet1.next()) {
				System.out.println("Inside pricetag");
				priceTag = new PriceTag();
				priceTag.setPriceTagId(resultSet1
						.getLong(DatabaseConstants.PRICE_TAG_ID));
				priceTag.setProductCatalogId(resultSet1
						.getLong(DatabaseConstants.PRODUCT_CATALOG_ID));
				priceTag.setSupplierId(resultSet1
						.getInt(DatabaseConstants.SUPPLIER_ID));
				priceTag.setQuentity(resultSet1
						.getInt(DatabaseConstants.QUENTITY));
				priceTag.setSalePrice(resultSet1
						.getFloat(DatabaseConstants.SALE_PRICE));
				priceTag.setTax(resultSet1.getString(DatabaseConstants.TAX));
				priceTag.setSku(resultSet1.getString(DatabaseConstants.SKU));
				priceTag.setRetailPrice(resultSet1
						.getFloat(DatabaseConstants.RETAIL_PRICE));

				priceTagsList.add(priceTag);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		System.out.println(priceTagsList.size());
		return priceTagsList;
	}
}
