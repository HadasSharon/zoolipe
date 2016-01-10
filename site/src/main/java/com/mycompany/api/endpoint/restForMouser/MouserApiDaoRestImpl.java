package com.mycompany.api.endpoint.restForMouser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.Util.DatabaseConstants;
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

@Repository("mouserApiDao")
public class MouserApiDaoRestImpl  {
	@PersistenceContext(unitName = "blPU")
	protected EntityManager entityManager;
	
	private DatabaseForRest dataSource=new DatabaseForRest();

	

	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, value = "blTransactionManagerSecureInfo")
	public long saveManufacturer(ManufacturerConfig manufacturersImpl) {
		// TODO Auto-generated method stub
		try {
			entityManager.persist(manufacturersImpl);
			entityManager.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return manufacturersImpl.getManufacturerId();
	}

	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, value = "blTransactionManagerSecureInfo")
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

	// To get the vat deatils for price cal in mouser API
	@SuppressWarnings("unchecked")
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public List<SubcategoryImpl> findVatDetailsOnSubCategoryName(
			String subCategoryName) {

		try {

			Query query = entityManager
					.createQuery("FROM SubcategoryImpl WHERE lower(subCategory) = '"
							+ subCategoryName.toLowerCase().trim() + "'");
			return query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public long saveSupplierCataLog(SupplierCatalog supplierCatalogueImpl) {
		// TODO Auto-generated method stub
		try {

			entityManager.persist(supplierCatalogueImpl);
			entityManager.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return supplierCatalogueImpl.getSupplierCatalogueId();
	}

	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public long updateProductCataLog(ProductCatalog productCatalogImpl) {
		// TODO Auto-generated method stub
		try {
			entityManager.merge(productCatalogImpl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productCatalogImpl.getProductCatalogId();
	}

	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public long updateSupplierCataLog(SupplierCatalog supplierCatalogueImpl) {
		// TODO Auto-generated method stub
		try {
			entityManager.merge(supplierCatalogueImpl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return supplierCatalogueImpl.getSupplierCatalogueId();
	}

	
	public long getManufacturerId(String manufacturerName) {
		// TODO Auto-generated method stub
		String sql = DatabaseConstants.GET_MANUFACTURER_ID;
		Connection conn = null;
		long manufacturerId = 0;
		manufacturerName = "'%" + manufacturerName.toLowerCase() + "%'";
		System.out.println("manufacturerName" + manufacturerName);
		sql += " where lower(MANUFACTURER_NAME) like " + manufacturerName;
		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			ResultSet resultSet1 = pstmt.executeQuery(sql);
			if (resultSet1.next()) {
				manufacturerId = resultSet1.getLong(1);
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
		return manufacturerId;
	}

	// TODO Auto-generated method stub
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public long saveMaincategory(MainCatagory mainCatagory) {
		// TODO Auto-generated method stub
		try {
			entityManager.persist(mainCatagory);
			entityManager.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mainCatagory.getMainCatagoryId();
	}

	
	public long getMainCategoryId(String mainCategoryName) {
		// TODO Auto-generated method stub
		String sql = DatabaseConstants.GET_MAIN_CATEGORY_ID;
		Connection conn = null;
		long mainCategoryId = 0;

		mainCategoryName = "'%" + mainCategoryName.toLowerCase() + "%'";
		sql += " where lower(MAIN_CATAGORY_NAME) like " + mainCategoryName;
		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			ResultSet resultSet1 = pstmt.executeQuery(sql);
			if (resultSet1.next()) {
				mainCategoryId = resultSet1.getLong(1);
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
		return mainCategoryId;
	}

	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public long saveMainSubcategory(MainSubCatagory mainCatagory) {
		// TODO Auto-generated method stub
		try {
			entityManager.persist(mainCatagory);
			entityManager.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mainCatagory.getMainSubCatagoryId();
	}

	
	public long getMainSubCategoryId(String mainSubCategory) {
		// TODO Auto-generated method stub
		String sql = DatabaseConstants.GET_MAIN_SUB_CATEGORY_ID;
		Connection conn = null;
		long mainubCategoryId = 0;
		mainSubCategory = "'%" + mainSubCategory.toLowerCase() + "%'";
		sql += " where lower(MAIN_SUB_CATEGORY_NAME) like " + mainSubCategory;
		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			ResultSet resultSet1 = pstmt.executeQuery(sql);
			if (resultSet1.next()) {
				mainubCategoryId = resultSet1.getLong(1);
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
		return mainubCategoryId;
	}

	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public long saveCategory(Category category) {
		// TODO Auto-generated method stub
		try {
			entityManager.persist(category);
			entityManager.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return category.getCatagoryId();
	}

	
	public long getCategoryId(String categoryName) {
		// TODO Auto-generated method stub
		String sql = DatabaseConstants.GET_CATEGORY_ID;
		Connection conn = null;
		long categoryId = 0;
		categoryName = "'%" + categoryName.toLowerCase() + "%'";
		sql += " where lower(CATAGORY_NAME) like " + categoryName;
		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			ResultSet resultSet1 = pstmt.executeQuery(sql);
			if (resultSet1.next()) {
				categoryId = resultSet1.getLong(1);
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
		return categoryId;
	}

	
	public long getProductCatalogId(String manufacturerPart) {
		// TODO Auto-generated method stub
		String sql = DatabaseConstants.GET_PRODUCT_CATALOG_ID;
		Connection conn = null;
		long productCataLogId = 0;
		manufacturerPart = "'%" + manufacturerPart.toLowerCase() + "%'";
		sql += " where lower(MANUFACTURER_PART) like " + manufacturerPart;
		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			ResultSet resultSet1 = pstmt.executeQuery(sql);
			if (resultSet1.next()) {
				productCataLogId = resultSet1.getLong(1);
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
		return productCataLogId;
	}

	
	public long getSupplierId(String supplierName) {
		// TODO Auto-generated method stub
		String sql = DatabaseConstants.GET_SUPPLIER_ID;
		Connection conn = null;
		long supplierId = 0;
		supplierName = "'%" + supplierName.toLowerCase() + "%'";
		sql += " where lower(supplier_name) like " + supplierName;
		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			ResultSet resultSet1 = pstmt.executeQuery(sql);
			if (resultSet1.next()) {
				supplierId = resultSet1.getLong(1);
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
		return supplierId;
	}

	
	public long getSupplierCatalogId(long supplierId, long productCatalogId) {
		// TODO Auto-generated method stub
		String sql = DatabaseConstants.GET_SUPPLIER_CATALOG_ID;
		Connection conn = null;
		long supplierCatalogId = 0;
		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, productCatalogId);
			pstmt.setLong(2, supplierId);
			ResultSet resultSet1 = pstmt.executeQuery();
			if (resultSet1.next()) {
				supplierCatalogId = resultSet1.getLong(1);
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
		return supplierCatalogId;
	}

	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public long insertSupplierCatalog(SupplierCatalog supplierCatalog) {
		// TODO Auto-generated method stub
		try {
			entityManager.persist(supplierCatalog);
			entityManager.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return supplierCatalog.getSupplierCatalogueId();
	}

	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public long updateSupplierCatalog(SupplierCatalog supplierCatalog) {
		// TODO Auto-generated method stub
		try {
			entityManager.merge(supplierCatalog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return supplierCatalog.getSupplierCatalogueId();
	}

	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public long insertSupplier(SupplierConfig supplierConfig) {
		// TODO Auto-generated method stub
		try {
			entityManager.persist(supplierConfig);
			entityManager.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return supplierConfig.getSupplierId();
	}

	
	public long getSupplierPriceConfigId(long supplierId, long categoryId) {
		// TODO Auto-generated method stub
		String sql = DatabaseConstants.GET_SUPPLIER_PRICE_CONFIG_ID;
		Connection conn = null;
		long supplierPriceConfigId = 0;

		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, supplierId);
			pstmt.setLong(2, categoryId);
			ResultSet resultSet1 = pstmt.executeQuery();
			if (resultSet1.next()) {
				supplierPriceConfigId = resultSet1.getLong(1);
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
		return supplierPriceConfigId;
	}

	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public long insertSupplierPriceConfig(
			SupplierPriceConfig supplierPriceConfig) {
		// TODO Auto-generated method stub
		try {
			entityManager.persist(supplierPriceConfig);
			entityManager.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return supplierPriceConfig.getSupplierPriceConfigId();
	}

	
	public long getPriceTagId(long supplierId, long productCatalogId,
			int quentity) {
		// TODO Auto-generated method stub
		String sql = DatabaseConstants.GET_PRICE_TAG_ID;
		Connection conn = null;
		long supplierPriceConfigId = 0;

		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, productCatalogId);
			pstmt.setLong(2, supplierId);
			pstmt.setLong(3, quentity);
			ResultSet resultSet1 = pstmt.executeQuery();
			if (resultSet1.next()) {
				supplierPriceConfigId = resultSet1.getLong(1);
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
		return supplierPriceConfigId;
	}

	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public long insertPriceTag(PriceTag priceTagImpl) {
		// TODO Auto-generated method stub
		try {
			entityManager.persist(priceTagImpl);
			entityManager.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return priceTagImpl.getPriceTagId();
	}

	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public long updatePriceTag(PriceTag priceTagImpl) {
		// TODO Auto-generated method stub
		try {
			entityManager.merge(priceTagImpl);
			entityManager.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return priceTagImpl.getPriceTagId();
	}

	
	public SupplierPriceConfig getSupplierPriceConfigData(
			long supplierPriceConfigId) {
		// TODO Auto-generated method stub
		String sql = DatabaseConstants.GET_SUPPLIER_PRICE_CONFIG;
		Connection conn = null;
		SupplierPriceConfig supplierPriceConfig = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, supplierPriceConfigId);
			ResultSet resultSet1 = pstmt.executeQuery();

			if (resultSet1.next()) {
				supplierPriceConfig = new SupplierPriceConfig();
				supplierPriceConfig.setCurrencyMarkup(resultSet1
						.getFloat(DatabaseConstants.CURRENCY_MARKUP));
				supplierPriceConfig.setHfi(resultSet1
						.getFloat(DatabaseConstants.HFI));
				supplierPriceConfig.setDuty(resultSet1
						.getFloat(DatabaseConstants.DUTY));
				supplierPriceConfig.setVat(resultSet1
						.getFloat(DatabaseConstants.VAT));
				supplierPriceConfig.setCategoryId(resultSet1
						.getLong(DatabaseConstants.CATEGORY_ID));
				supplierPriceConfig.setSupplierId(resultSet1
						.getLong(DatabaseConstants.SUPPLIER_ID));
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
		return supplierPriceConfig;
	}

	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public long updateSupplierPriceConfig(SupplierPriceConfig priceTagImpl) {
		// TODO Auto-generated method stub
		try {
			entityManager.merge(priceTagImpl);
			entityManager.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return priceTagImpl.getSupplierPriceConfigId();
	}

	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "blTransactionManagerSecureInfo")
	public List<SupplierPriceConfig> getSupplierPriceConfigDetails(
			long supplierId, long catgeoryId) {
		try {
			TypedQuery<SupplierPriceConfig> query = entityManager
					.createQuery(
							"SELECT c FROM SupplierPriceConfig c WHERE   c.categoryId=:catgeoryId AND  c.supplierId=:supplierId",
							SupplierPriceConfig.class);
			return query.setParameter("catgeoryId", catgeoryId)
					.setParameter("supplierId", supplierId).getResultList();
		} catch (PersistenceException persistenceException) {

		}

		return null;
	}
}
