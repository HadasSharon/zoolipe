package com.mycompany.persistance.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.broadleafcommerce.openadmin.server.service.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.Util.DatabaseConstants;
import com.mycompany.persistance.domain.CartVo;
import com.mycompany.persistance.domain.Category;
import com.mycompany.persistance.domain.CustomerAddressVO;
import com.mycompany.persistance.domain.MainCatagory;
import com.mycompany.persistance.domain.ManufacturerConfig;
import com.mycompany.persistance.domain.PriceTag;
import com.mycompany.persistance.domain.ProductCatalog;
import com.mycompany.persistance.domain.SemikartOrder;
import com.mycompany.persistance.domain.SemikartOrderItem;
import com.mycompany.persistance.domain.SemikartOrderPayment;
import com.mycompany.persistance.domain.SupplierCatalog;
import com.mycompany.persistance.domain.SupplierPriceConfig;
import com.mycompany.persistance.domain.TransactionIdGenerator;

@Repository("semikartDao")
public class SemikartDaoImpl implements SemikartDao {
	@PersistenceContext(unitName = "blPU")
	protected EntityManager entityManager;
	@Autowired
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// To display dynamic sliders in login page
	public List<ProductCatalog> fetchProductCatalogDetails() {
		String sql = DatabaseConstants.SEARCH_FEAUTURED_PRODUCT;
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
	public List<MainCatagory> getMainCategoryForProductListDropdown() {
		// TODO Auto-generated method stub
		String sql = DatabaseConstants.GET_DROP_DOWN_FOR_PRODUCT_LIST;
		Connection conn = null;
		MainCatagory supplierPriceConfig = null;
		List<MainCatagory> mainCatagoryList = new ArrayList<MainCatagory>();
		System.out.println(sql);
		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "Products");
			ResultSet resultSet1 = pstmt.executeQuery();

			while (resultSet1.next()) {
				supplierPriceConfig = new MainCatagory();
				supplierPriceConfig.setMainCatagoryId(resultSet1
						.getLong(DatabaseConstants.MAIN_CATAGORY_ID));
				supplierPriceConfig.setMaincatagoryName(resultSet1
						.getString(DatabaseConstants.MAIN_CATAGORY_NAME));
				supplierPriceConfig.setImageUrl(resultSet1
						.getString(DatabaseConstants.IMAGE_URL));
				mainCatagoryList.add(supplierPriceConfig);
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
		return mainCatagoryList;
	}

	@Override
	public List<ProductCatalog> getNewProductListDropdown() {
		// TODO Auto-generated method stub
		String sql = DatabaseConstants.GET_DROP_DOWN_FOR_NEW_PRODUCT_LIST;
		Connection conn = null;
		List<ProductCatalog> productCatalogImplAdmins = new ArrayList<ProductCatalog>();
		ProductCatalog productCatalogImplAdmin = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			ResultSet resultSet1 = pstmt.executeQuery();
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
	public List<ProductCatalog> productSearch(ProductCatalog catalogImpl) {
		String sql = DatabaseConstants.PRODUCT_SEARCH_FOR_VIEW;
		Connection conn = null;
		List<ProductCatalog> productCatalogImplAdmins = new ArrayList<ProductCatalog>();
		ProductCatalog productCatalogImplAdmin = null;

		String mainCategoryName = catalogImpl.getCategoryName();
		String manufacturerPart = catalogImpl.getManufacturerPart();
		String semikartPart = catalogImpl.getSemikartPart();
		String categoryName = catalogImpl.getCategoryName();
		String manufacturerName = catalogImpl.getManufacturerName();
		// All names are same while search
		String supplierName = catalogImpl.getManufacturerName();
		if (StringUtils.isNotBlank(mainCategoryName)) {
			sql += " WHERE LOWER(MC.MAIN_CATAGORY_NAME) LIKE '%"
					+ mainCategoryName.toLowerCase() + "%'";
		}
		if (StringUtils.isNotBlank(manufacturerPart)) {
			sql += " OR PC.MANUFACTURER_PART='" + manufacturerPart + "'";
		}
		if (StringUtils.isNotBlank(semikartPart)) {
			sql += " OR PC.SEMIKART_PART= '" + semikartPart + "'";
		}
		if (StringUtils.isNotBlank(categoryName)) {
			sql += " OR LOWER(CA.CATAGORY_NAME) LIKE '%"
					+ categoryName.toLowerCase() + "%'";
		}
		if (StringUtils.isNotBlank(manufacturerName)) {
			sql += " OR LOWER(MAN.MANUFACTURER_NAME) LIKE '%"
					+ manufacturerName.toLowerCase() + "%'";
		}

		System.out.println("Query" + sql);
		try {
			conn = dataSource.getConnection();
			Statement statement1 = conn.createStatement();
			ResultSet resultSet1 = statement1.executeQuery(sql);
			while (resultSet1.next()) {
				productCatalogImplAdmin = new ProductCatalog();

				productCatalogImplAdmin.setProductCatalogId(resultSet1
						.getLong(DatabaseConstants.PRODUCT_CATALOG_ID));
				productCatalogImplAdmin.setCategoryName(resultSet1
						.getString(DatabaseConstants.CATAGORY_NAME));
				productCatalogImplAdmin.setManufacturerName(resultSet1
						.getString(DatabaseConstants.MANUFACTURER_NAME));
				productCatalogImplAdmin.setManufacturerPart(resultSet1
						.getString(DatabaseConstants.MANUFACTURER_PART));
				productCatalogImplAdmin.setSemikartPart(resultSet1
						.getString(DatabaseConstants.SEMIKART_PART));
				productCatalogImplAdmin.setDataSheet(resultSet1
						.getString(DatabaseConstants.DATA_SHEET_URL));
				productCatalogImplAdmin.setDescription(resultSet1
						.getString(DatabaseConstants.DESCRIPTION));
				productCatalogImplAdmin.setImageUrl(resultSet1
						.getString(DatabaseConstants.IMAGE_URL));
				productCatalogImplAdmin.setProductCatalogId(resultSet1
						.getLong(DatabaseConstants.PRODUCT_CATALOG_ID));
				productCatalogImplAdmin.setManuFacturerLogo(resultSet1
						.getString(DatabaseConstants.MANUFACTURER_LOGO));
				List<SupplierCatalog> supplierCatalogsList = productSearchSupplierDetails(
						productCatalogImplAdmin.getProductCatalogId(), conn);

				productCatalogImplAdmin
						.setTax("Inclusive of Duty + 14.50% VAT as Applicable");
				productCatalogImplAdmin
						.setSupplierCatalogList(supplierCatalogsList);
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
	public List<SupplierCatalog> productSearchSupplierDetails(
			long productCatalogId, Connection conn) {
		// TODO Auto-generated method stub
		String sql = DatabaseConstants.PRODUCT_SEARCH_SUPPLIER_DETAILS;

		List<SupplierCatalog> supplierCatalogList = new ArrayList<SupplierCatalog>();

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
				supplierCatalog.setVat(resultSet1
						.getLong(DatabaseConstants.VAT));
				List<PriceTag> priceTagImpls = getPriceTagsByProductCatalogId(
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
	public List<PriceTag> getPriceTagsByProductCatalogId(long productCatalogId,
			long supplierId) {
		String sql = DatabaseConstants.PRODUCT_SEARCH_PRICE_DETAILS;

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

	@Override
	public List<ProductCatalog> getHotDealProductList() {
		// TODO Auto-generated method stub
		String sql = DatabaseConstants.GET_HOT_DEAL_PRODUCT_LIST;
		Connection conn = null;
		List<ProductCatalog> productCatalogImplAdmins = new ArrayList<ProductCatalog>();
		ProductCatalog productCatalogImplAdmin = null;
		SupplierCatalog supplierCatalog = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			ResultSet resultSet1 = pstmt.executeQuery();
			while (resultSet1.next()) {
				supplierCatalog = new SupplierCatalog();
				productCatalogImplAdmin = new ProductCatalog();
				productCatalogImplAdmin.setProductCatalogId(resultSet1
						.getLong(DatabaseConstants.PRODUCT_CATALOG_ID));
				productCatalogImplAdmin.setCategoryName(resultSet1
						.getString(DatabaseConstants.CATAGORY_NAME));
				productCatalogImplAdmin.setManufacturerName(resultSet1
						.getString(DatabaseConstants.MANUFACTURER_NAME));
				productCatalogImplAdmin.setManufacturerPart(resultSet1
						.getString(DatabaseConstants.MANUFACTURER_PART));
				productCatalogImplAdmin.setSemikartPart(resultSet1
						.getString(DatabaseConstants.SEMIKART_PART));
				productCatalogImplAdmin.setDataSheet(resultSet1
						.getString(DatabaseConstants.DATA_SHEET_URL));
				productCatalogImplAdmin.setDescription(resultSet1
						.getString(DatabaseConstants.DESCRIPTION));
				productCatalogImplAdmin.setImageUrl(resultSet1
						.getString(DatabaseConstants.IMAGE_URL));
				productCatalogImplAdmin.setProductCatalogId(resultSet1
						.getLong(DatabaseConstants.PRODUCT_CATALOG_ID));
				productCatalogImplAdmin.setMainSubCategoryName(resultSet1
						.getString(DatabaseConstants.MAIN_SUB_CATEGORY_NAME));
				supplierCatalog.setLeadTime(resultSet1
						.getInt(DatabaseConstants.LEAD_TIME));
				supplierCatalog.setStock(resultSet1
						.getInt(DatabaseConstants.STOCK));
				supplierCatalog.setMinu(resultSet1
						.getInt(DatabaseConstants.MINI));
				supplierCatalog.setMult(resultSet1
						.getInt(DatabaseConstants.MULT));
				productCatalogImplAdmin.setSupplierCatalog(supplierCatalog);
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
	public List<ProductCatalog> getNewestProductList() {
		// TODO Auto-generated method stub
		String sql = DatabaseConstants.GET_NEWEST_PRODUCT_LIST;
		Connection conn = null;
		List<ProductCatalog> productCatalogImplAdmins = new ArrayList<ProductCatalog>();
		ProductCatalog productCatalogImplAdmin = null;
		SupplierCatalog supplierCatalog = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			ResultSet resultSet1 = pstmt.executeQuery();
			while (resultSet1.next()) {
				supplierCatalog = new SupplierCatalog();
				productCatalogImplAdmin = new ProductCatalog();
				productCatalogImplAdmin.setProductCatalogId(resultSet1
						.getLong(DatabaseConstants.PRODUCT_CATALOG_ID));
				productCatalogImplAdmin.setCategoryName(resultSet1
						.getString(DatabaseConstants.CATAGORY_NAME));
				productCatalogImplAdmin.setManufacturerName(resultSet1
						.getString(DatabaseConstants.MANUFACTURER_NAME));
				productCatalogImplAdmin.setManufacturerPart(resultSet1
						.getString(DatabaseConstants.MANUFACTURER_PART));
				productCatalogImplAdmin.setSemikartPart(resultSet1
						.getString(DatabaseConstants.SEMIKART_PART));
				productCatalogImplAdmin.setDataSheet(resultSet1
						.getString(DatabaseConstants.DATA_SHEET_URL));
				productCatalogImplAdmin.setDescription(resultSet1
						.getString(DatabaseConstants.DESCRIPTION));
				productCatalogImplAdmin.setImageUrl(resultSet1
						.getString(DatabaseConstants.IMAGE_URL));
				productCatalogImplAdmin.setProductCatalogId(resultSet1
						.getLong(DatabaseConstants.PRODUCT_CATALOG_ID));
				productCatalogImplAdmin.setMainSubCategoryName(resultSet1
						.getString(DatabaseConstants.MAIN_SUB_CATEGORY_NAME));
				supplierCatalog.setLeadTime(resultSet1
						.getInt(DatabaseConstants.LEAD_TIME));
				supplierCatalog.setStock(resultSet1
						.getInt(DatabaseConstants.STOCK));
				supplierCatalog.setMinu(resultSet1
						.getInt(DatabaseConstants.MINI));
				supplierCatalog.setMult(resultSet1
						.getInt(DatabaseConstants.MULT));
				productCatalogImplAdmin.setSupplierCatalog(supplierCatalog);
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
	public List<ProductCatalog> getPopularProductList() {
		// TODO Auto-generated method stub
		String sql = DatabaseConstants.GET_POPULAR_PRODUCT_LIST;
		Connection conn = null;
		List<ProductCatalog> productCatalogImplAdmins = new ArrayList<ProductCatalog>();
		ProductCatalog productCatalogImplAdmin = null;
		SupplierCatalog supplierCatalog = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			ResultSet resultSet1 = pstmt.executeQuery();
			while (resultSet1.next()) {
				supplierCatalog = new SupplierCatalog();
				productCatalogImplAdmin = new ProductCatalog();
				productCatalogImplAdmin.setProductCatalogId(resultSet1
						.getLong(DatabaseConstants.PRODUCT_CATALOG_ID));
				productCatalogImplAdmin.setCategoryName(resultSet1
						.getString(DatabaseConstants.CATAGORY_NAME));
				productCatalogImplAdmin.setManufacturerName(resultSet1
						.getString(DatabaseConstants.MANUFACTURER_NAME));
				productCatalogImplAdmin.setManufacturerPart(resultSet1
						.getString(DatabaseConstants.MANUFACTURER_PART));
				productCatalogImplAdmin.setSemikartPart(resultSet1
						.getString(DatabaseConstants.SEMIKART_PART));
				productCatalogImplAdmin.setDataSheet(resultSet1
						.getString(DatabaseConstants.DATA_SHEET_URL));
				productCatalogImplAdmin.setDescription(resultSet1
						.getString(DatabaseConstants.DESCRIPTION));
				productCatalogImplAdmin.setImageUrl(resultSet1
						.getString(DatabaseConstants.IMAGE_URL));
				productCatalogImplAdmin.setProductCatalogId(resultSet1
						.getLong(DatabaseConstants.PRODUCT_CATALOG_ID));
				productCatalogImplAdmin.setMainSubCategoryName(resultSet1
						.getString(DatabaseConstants.MAIN_SUB_CATEGORY_NAME));
				supplierCatalog.setLeadTime(resultSet1
						.getInt(DatabaseConstants.LEAD_TIME));
				supplierCatalog.setStock(resultSet1
						.getInt(DatabaseConstants.STOCK));
				supplierCatalog.setMinu(resultSet1
						.getInt(DatabaseConstants.MINI));
				supplierCatalog.setMult(resultSet1
						.getInt(DatabaseConstants.MULT));
				productCatalogImplAdmin.setSupplierCatalog(supplierCatalog);
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
	public List<Category> productSearchCategoryList(ProductCatalog catalogImpl) {
		List<Category> categoriesList = new ArrayList<Category>();
		Category category = null;
		String sql = DatabaseConstants.PRODUCT_SEARCH_CATEGORY_LIST;
		String mainCategoryName = catalogImpl.getCategoryName();
		if (StringUtils.isNotBlank(mainCategoryName)) {
			sql += " WHERE LOWER(MC.MAIN_CATAGORY_NAME) LIKE '%"
					+ mainCategoryName.toLowerCase()
					+ "%' Order by CA.CATAGORY_NAME";
		}

		Connection conn = null;

		System.out.println("Query" + sql);
		try {
			conn = dataSource.getConnection();
			Statement statement1 = conn.createStatement();
			ResultSet resultSet1 = statement1.executeQuery(sql);
			while (resultSet1.next()) {
				category = new Category();
				category.setCatagoryName(resultSet1
						.getString(DatabaseConstants.CATAGORY_NAME));
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
	public List<MainCatagory> getMainCategoryForApplicationProductListDropdown() {
		// TODO Auto-generated method stub
		String sql = DatabaseConstants.GET_DROP_DOWN_FOR_PRODUCT_LIST;
		Connection conn = null;
		MainCatagory supplierPriceConfig = null;
		List<MainCatagory> mainCatagoryList = new ArrayList<MainCatagory>();
		System.out.println(sql);
		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "Application");
			ResultSet resultSet1 = pstmt.executeQuery();

			while (resultSet1.next()) {
				supplierPriceConfig = new MainCatagory();
				supplierPriceConfig.setMainCatagoryId(resultSet1
						.getLong(DatabaseConstants.MAIN_CATAGORY_ID));
				supplierPriceConfig.setMaincatagoryName(resultSet1
						.getString(DatabaseConstants.MAIN_CATAGORY_NAME));
				supplierPriceConfig.setImageUrl(resultSet1
						.getString(DatabaseConstants.IMAGE_URL));
				mainCatagoryList.add(supplierPriceConfig);
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
		return mainCatagoryList;
	}

	@Override
	public List<CustomerAddressVO> getCustomerAddressDetails(long custId) {
		// TODO Auto-generated method stub
		String sql = DatabaseConstants.GET_CUSTOMER_ADDRESS_DETAILS;
		Connection conn = null;
		CustomerAddressVO customerAddressVO = null;
		List<CustomerAddressVO> customerAddressVOs = new ArrayList<CustomerAddressVO>();
		System.out.println(sql);
		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, custId);
			ResultSet resultSet1 = pstmt.executeQuery();

			while (resultSet1.next()) {
				customerAddressVO = new CustomerAddressVO();
				customerAddressVO.setCustomerAddressId(resultSet1
						.getLong(DatabaseConstants.CUSTOMER_ADDRESS_ID));
				customerAddressVO.setAddressName(resultSet1
						.getString(DatabaseConstants.ADDRESS_NAME));
				customerAddressVO.setAddressId(resultSet1
						.getLong(DatabaseConstants.ADDRESS_ID));
				customerAddressVO.setAddressLane1(resultSet1
						.getString(DatabaseConstants.ADDRESS_LINE1));
				customerAddressVO.setAddressLane2(resultSet1
						.getString(DatabaseConstants.ADDRESS_LINE2));
				customerAddressVO.setAddressLane3(resultSet1
						.getString(DatabaseConstants.ADDRESS_LINE3));
				customerAddressVO.setIsDefault(resultSet1
						.getInt(DatabaseConstants.IS_DEFAULT));
				customerAddressVO.setCity(resultSet1
						.getString(DatabaseConstants.CITY));
				customerAddressVO.setCompanyName(resultSet1
						.getString(DatabaseConstants.COMPANY_NAME));
				customerAddressVO.setCountry(resultSet1
						.getString(DatabaseConstants.COUNTY));
				customerAddressVO.setFirstName(resultSet1
						.getString(DatabaseConstants.FIRST_NAME));
				customerAddressVO.setFullName(resultSet1
						.getString(DatabaseConstants.FULL_NAME));
				customerAddressVO.setPostalCode(resultSet1
						.getString(DatabaseConstants.POSTAL_CODE));
				customerAddressVO.setPhone(resultSet1
						.getString(DatabaseConstants.PHONE_NUMBER));
				customerAddressVO.setShippingCost(resultSet1
						.getFloat(DatabaseConstants.SHIPPING_COST));
				customerAddressVOs.add(customerAddressVO);
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
		return customerAddressVOs;
	}

	@Override
	public boolean deleteCustomerAddress(long custAddressId) {
		// TODO Auto-generated method stub
		String sql = DatabaseConstants.DELETE_CUSTOMER_ADDRESS;
		Connection conn = null;
		System.out.println(sql);
		boolean isSuccuss = true;
		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, custAddressId);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			isSuccuss = false;
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return isSuccuss;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, value = "blTransactionManagerSecureInfo")
	public String getTransactionId() {
		// TODO Auto-generated method stub
		String txanId = "";
		try {

			TransactionIdGenerator transactionIdGenerator = new TransactionIdGenerator();
			entityManager.persist(transactionIdGenerator);
			entityManager.flush();
			java.util.Date date = new java.util.Date();
			int year = date.getYear();
			int month = date.getMonth();
			int day = date.getDay();
			txanId = "SMKRT" + String.valueOf(year) + String.valueOf(month)
					+ String.valueOf(day) + String.valueOf(month)
					+ String.valueOf(transactionIdGenerator.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txanId;
	}

	// TODO Auto-generated method stub
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, value = "blTransactionManagerSecureInfo")
	public String saveOrderInfo(SemikartOrder semikartOrder) {
		// TODO Auto-generated method stub
		String orderIdNo = null;
		try {
			entityManager.persist(semikartOrder);
			entityManager.flush();
			orderIdNo = String.valueOf(semikartOrder.getOrderId()) + "/"
					+ semikartOrder.getOrderNumber();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderIdNo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, value = "blTransactionManagerSecureInfo")
	public long saveOrderItemInfo(SemikartOrderItem semikartOrderItem) {
		// TODO Auto-generated method stub
		System.out.println("saveOrderItemInfo"
				+ semikartOrderItem.getManufacturerPart());
		try {
			entityManager.persist(semikartOrderItem);
			entityManager.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return semikartOrderItem.getOrderItemId();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, value = "blTransactionManagerSecureInfo")
	public long saveOrderPaymentInfo(SemikartOrderPayment semikartOrderPayment) {
		// TODO Auto-generated method stub
		try {
			entityManager.persist(semikartOrderPayment);
			entityManager.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return semikartOrderPayment.getPaymentTransactionId();
	}

	@Override
	public java.util.Date getCurrentDate() {
		// TODO Auto-generated method stub
		String sql = DatabaseConstants.GET_CURRENT_DATE;
		Connection conn = null;
		System.out.println(sql);
		String currentDate = null;
		java.util.Date date = null;

		DateFormat df = new SimpleDateFormat("dd-mm-yyyy");

		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet resultSet1 = pstmt.executeQuery();
			while (resultSet1.next()) {
				currentDate = resultSet1.getString(1);

				try {
					java.util.Date startDate = df.parse(currentDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

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
		return date;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, value = "blTransactionManagerSecureInfo")
	public long saveWhishList(CartVo cartVo) {
		// TODO Auto-generated method stub
		try {
			entityManager.persist(cartVo);
			entityManager.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cartVo.getSemikartWishListId();
	}

	@Override
	public List<CartVo> getWishListDeatils(long custId) {
		// TODO Auto-generated method stub
		String sql = DatabaseConstants.GET_WISHLIST_DETAILS;
		Connection conn = null;
		CartVo cartVo = null;
		List<CartVo> cartVos = new ArrayList<CartVo>();
		System.out.println(sql);
		try {
			conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, custId);
			ResultSet resultSet1 = pstmt.executeQuery();

			while (resultSet1.next()) {
				cartVo = new CartVo();
				cartVo.setSupplierName(resultSet1
						.getString(DatabaseConstants.SUPPLIER_NAME));
				cartVo.setManuFacturerPart(resultSet1
						.getString(DatabaseConstants.MANUFACTURER_PART));
				cartVo.setSemikartPart(resultSet1
						.getString(DatabaseConstants.SEMIKART_PART));
				cartVo.setDescription(resultSet1
						.getString(DatabaseConstants.DESCRIPTION));
				cartVo.setRohs(resultSet1.getString(DatabaseConstants.ROHAS));
				cartVo.setPrice(resultSet1.getFloat(DatabaseConstants.PRICE));
				cartVo.setQuantity(resultSet1
						.getInt(DatabaseConstants.QUANTITY));
				cartVo.setTotalPrice(resultSet1
						.getFloat(DatabaseConstants.TOTAL_PRICE));
				cartVo.setStock(resultSet1.getInt(DatabaseConstants.STOCK));
				cartVo.setVat(resultSet1.getFloat(DatabaseConstants.VAT));
				cartVo.setProductCatalogId(resultSet1
						.getLong(DatabaseConstants.PRODUCT_CATALOG_ID));
				cartVo.setSupplierId(resultSet1
						.getLong(DatabaseConstants.SUPPLIER_ID));
				cartVo.setSemikartWishListId(resultSet1
						.getLong(DatabaseConstants.SEMICART_WISH_LIST_ID));

				cartVos.add(cartVo);
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
		return cartVos;
	}
}
