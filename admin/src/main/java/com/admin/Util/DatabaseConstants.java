package com.admin.Util;

public class DatabaseConstants {
	public static final String CATAGORY_NAME = "CATAGORY_NAME";
	public static final String MANUFACTURER_PART = "MANUFACTURER_PART";
	public static final String SEMIKART_PART = "SEMIKART_PART";
	public static final String DATA_SHEET_URL = "DATA_SHEET_URL";
	public static final String DESCRIPTION = "DESCRIPTION";
	public static final String IS_NEW_PRODUCT = "IS_NEW_PRODUCT";
	public static final String IS_FEAUTURED_PRODUCT = "IS_FEAUTURED_PRODUCT";
	public static final String PRODUCT_STATUS = "PRODUCT_STATUS";
	public static final String MANUFACTURER_NAME = "MANUFACTURER_NAME";
	public static final String IMAGE_URL = "IMAGE_URL";
	public static final String PRODUCT_CATALOG_ID = "PRODUCT_CATALOG_ID";
	public static final String MANUFACTURER_LOGO = "MANUFACTURER_LOGO";

	public static final String PRICE_TAG_ID="PRICE_TAG_ID";
	public static final String QUENTITY="QUENTITY";
	public static final String SALE_PRICE="SALE_PRICE";
	public static final String TAX="TAX";
	public static final String SKU="SKU";
	public static final String RETAIL_PRICE="RETAIL_PRICE";
	
	public static final String CURRENCY_MARKUP = "CURRENCY_MARKUP";
	public static final String SUPPLIER_ID = "SUPPLIER_ID";
	public static final String VAT = "VAT";
	public static final String DUTY = "DUTY";
	public static final String HFI = "HFI";
	public static final String CATEGORY_ID = "CATEGORY_ID";
	public static final String CATAGORY_ID = "CATAGORY_ID";
	
	public static final String SUPPLIER_PRICE_CONFIG_NO = "SUPPLIER_PRICE_CONFIG_NO";
	
	public static final String MAIN_CATAGORY_NAME = "MAIN_CATAGORY_NAME";
	public static final String MAIN_SUB_CATEGORY_ID = "MAIN_SUB_CATEGORY_ID";
	public static final String MAIN_CATAGORY_ID = "MAIN_CATAGORY_ID";
	public static final String MAIN_SUB_CATEGORY_NAME = "MAIN_SUB_CATEGORY_NAME";
	
	public static final String STOCK = "STOCK";
	public static final String MINI = "MINI";
	public static final String MULT = "MULT";
	public static final String LEAD_TIME = "LEAD_TIME";
	public static final String SUPPLIER_NAME = "SUPPLIER_NAME";


	public static final String SEARCH_PRODUCT = "SELECT PC.PRODUCT_CATALOG_ID, PC.MANUFACTURER_PART,PC.SEMIKART_PART,PC.DATA_SHEET_URL,PC.IMAGE_URL,PC.DESCRIPTION,PC.IS_NEW_PRODUCT,PC.IS_FEAUTURED_PRODUCT,PC.PRODUCT_STATUS,"
			+ " MAF.MANUFACTURER_NAME,CAT.CATAGORY_NAME FROM PRODUCT_CATALOG PC INNER JOIN MANUFACTURER MAF ON MAF.MANUFACTURER_ID=PC.MANUFACTURER_ID "
			+ " INNER JOIN CATEGORY CAT ON CAT.CATAGORY_ID=PC.CATAGORY_ID";
	
	public static final String SEARCH_MAIN_SUB_CATEGORY_DETAILS ="SELECT MC.MAIN_CATAGORY_NAME,MSC.MAIN_SUB_CATEGORY_ID,MSC.MAIN_CATAGORY_ID,MSC.MAIN_SUB_CATEGORY_NAME FROM main_sub_category MSC LEFT JOIN main_category MC ON MC.MAIN_CATAGORY_ID=MSC.MAIN_CATAGORY_ID ORDER BY MSC.MAIN_SUB_CATEGORY_NAME ";
	
	public static final String SEARCH_MAIN_CATEGORY_DETAILS ="SELECT CA.VAT,CA.DUTY,CA.HFI,CA.CURRENCY_MARKUP,MC.MAIN_CATAGORY_NAME,MSC.MAIN_SUB_CATEGORY_ID,MSC.MAIN_CATAGORY_ID,MSC.MAIN_SUB_CATEGORY_NAME,CA.CATAGORY_NAME,CA.CATAGORY_ID FROM category CA LEFT JOIN  main_sub_category MSC ON MSC.MAIN_SUB_CATEGORY_ID=CA.MAIN_SUB_CATEGORY_ID  LEFT JOIN main_category MC ON MC.MAIN_CATAGORY_ID=MSC.MAIN_CATAGORY_ID ORDER BY CA.CATAGORY_NAME ";

	public static final String UPDATE_MANUFACTURER_LOGO = "UPDATE MANUFACTURER SET MANUFACTURER_LOGO=?,IMAGE_NAME=? WHERE MANUFACTURER_ID=?";
	public static final String UPDATE_SUPPLIER_LOGO = "UPDATE SUPPLIER SET SUPPLIER_LOGO=? WHERE supplier_id=?";
	public static final String SELECT_MANUFACTURER_LOGO = "SELECT MANUFACTURER_LOGO FROM manufacturer WHERE MANUFACTURER_ID=?";
	public static final String MENUFACTURER_IMAGE_PATH = "marketplaceImg/manuFacturerLogo/";
	public static final String PRODUCT_IMAGE_PATH = "marketplaceImg/productImages/";
	public static final String PRODUCT_DATASHEET_PATH = "marketplaceDataSheet/";
	public static final String MAIN_CATEGORY_IMAGE_PATH = "marketplaceImg/mainCategory/";
	public static final String GET_SUPPLIER_DETAILS = " SELECT SC.SUPPLIER_CATALOG_ID,SC.SUPPLIER_ID,SC.STOCK,SC.MINI,SC.MULT,SC.LEAD_TIME,SUP.SUPPLIER_NAME,SUP.SUPPLIER_ID FROM SUPPLIER_CATALOG SC  INNER JOIN SUPPLIER SUP ON SUP.SUPPLIER_ID=SC.SUPPLIER_ID INNER JOIN product_catalog PC on pc.PRODUCT_CATALOG_ID=SC.PRODUCT_CATALOG_ID  WHERE SC.PRODUCT_CATALOG_ID=?";
	public static final String GET_PRICE_DETAILS=" SELECT PT.PRICE_TAG_ID,PT.PRODUCT_CATALOG_ID,PT.SUPPLIER_ID,PT.QUENTITY,PT.SALE_PRICE,PT.TAX,PT.SKU,PT.RETAIL_PRICE FROM PRICE_TAG PT WHERE PT.PRODUCT_CATALOG_ID=? AND PT.SUPPLIER_ID=? ";

	// ///////////////MOUSER API
	public static final String GET_MANUFACTURER_ID = "SELECT MANUFACTURER_ID FROM manufacturer";
	public static final String GET_MAIN_CATEGORY_ID = "SELECT MAIN_CATAGORY_ID FROM main_category ";
	public static final String GET_MAIN_SUB_CATEGORY_ID = "SELECT MAIN_SUB_CATEGORY_ID FROM main_sub_category ";
	public static final String GET_CATEGORY_ID = "SELECT CATAGORY_ID FROM category ";
	public static final String GET_PRODUCT_CATALOG_ID = "SELECT PRODUCT_CATALOG_ID FROM product_catalog ";
	public static final String GET_SUPPLIER_ID = "SELECT supplier_id FROM supplier ";
	public static final String GET_SUPPLIER_CATALOG_ID = "SELECT SUPPLIER_CATALOG_ID FROM supplier_catalog where PRODUCT_CATALOG_ID=? AND SUPPLIER_ID=?";

	public static final String GET_SUPPLIER_PRICE_CONFIG_ID = "SELECT SUPPLIER_PRICE_CONFIG_NO FROM supplier_price_config  where SUPPLIER_ID=? AND CATEGORY_ID=?";
	public static final String GET_PRICE_TAG_ID = "SELECT PRICE_TAG_ID FROM price_tag  where PRODUCT_CATALOG_ID=? AND SUPPLIER_ID=? AND QUENTITY=?";
	public static final String GET_SUPPLIER_PRICE_CONFIG = "SELECT * FROM supplier_price_config  where SUPPLIER_PRICE_CONFIG_NO=?";
	public static final String UPDATE_CURRENCY = "UPDATE CURRENCY SET VALUE=?";
	public static final String SELECT_CURRENCY = "SELECT VALUE FROM CURRENCY";
	public static final String SELECT_CATEGORY_BASED_ON_NAME = "SELECT MAIN_SUB_CATEGORY_ID,CATAGORY_NAME,IMAGE_URL,VAT,DUTY,HFI,CURRENCY_MARKUP FROM category WHERE CATAGORY_ID=?";
	
	public static final String UPDATE_MAIN_SUB_CATEGORY = "UPDATE main_sub_category SET MAIN_CATAGORY_ID=? WHERE MAIN_SUB_CATEGORY_ID=?"; 
	public static final String UPDATE_CATEGORY ="UPDATE category SET MAIN_SUB_CATEGORY_ID=? WHERE CATAGORY_ID=?";
	
	
	// /////////////////////////END MOUSERE
}
