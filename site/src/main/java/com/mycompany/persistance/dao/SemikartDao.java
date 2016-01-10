package com.mycompany.persistance.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

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

public interface SemikartDao {
	// To display dynamic sliders
		public List<ProductCatalog> fetchProductCatalogDetails();
		//
		public List<MainCatagory> getMainCategoryForProductListDropdown();
		public List<ProductCatalog> getNewProductListDropdown();
		public  List<MainCatagory>  getMainCategoryForApplicationProductListDropdown();
		public List<ProductCatalog> productSearch(ProductCatalog catalogImpl);
		public List<Category> productSearchCategoryList(ProductCatalog catalogImpl);
		public List<PriceTag> getPriceTagsByProductCatalogId( long id,long supplierId);
		public List<ProductCatalog> getHotDealProductList( );
		public List<ProductCatalog> getNewestProductList( );
		public List<ProductCatalog> getPopularProductList( );
		public List<SupplierCatalog> productSearchSupplierDetails(long productCatalogId,Connection conn) ;
		public  List<CustomerAddressVO>  getCustomerAddressDetails(long custId);
		public  boolean  deleteCustomerAddress(long custAddressId);
		public  String  getTransactionId();
		
		public String saveOrderInfo(SemikartOrder semikartOrder);
		public long saveOrderItemInfo(SemikartOrderItem semikartOrderItem);
		public long saveOrderPaymentInfo(SemikartOrderPayment semikartOrderPayment);
		public Date getCurrentDate();
		public long saveWhishList(CartVo cartVo);
		
		
		public  List<CartVo>  getWishListDeatils(long custId);
}
