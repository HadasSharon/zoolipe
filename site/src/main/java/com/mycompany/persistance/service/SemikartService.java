package com.mycompany.persistance.service;

import java.util.ArrayList;
import java.util.List;

import com.admin.domain.AdminImpl;
import com.mycompany.persistance.domain.CartVo;
import com.mycompany.persistance.domain.Category;
import com.mycompany.persistance.domain.CustomerAddressVO;
import com.mycompany.persistance.domain.MainCatagory;
import com.mycompany.persistance.domain.MainSubCatagory;
import com.mycompany.persistance.domain.ManufacturerConfig;
import com.mycompany.persistance.domain.PaymentGatewayDetailsVO;
import com.mycompany.persistance.domain.PriceTag;
import com.mycompany.persistance.domain.ProductCatalog;
import com.mycompany.persistance.domain.SemikartOrder;
import com.mycompany.persistance.domain.SemikartOrderItem;
import com.mycompany.persistance.domain.SemikartOrderPayment;
import com.mycompany.persistance.domain.SubcategoryImpl;
import com.mycompany.persistance.domain.SupplierCatalog;


public interface SemikartService {
	
	
	//To display dynamic sliders
		public List<ProductCatalog> fetchProductCatalogDetails();
		public List<MainCatagory> getMainCategoryForProductListDropdown();
		public List<ProductCatalog> getNewProductListDropdown();
		public List<ProductCatalog> productSearch(ProductCatalog catalogImpl);
		public List<Category> productSearchCategoryList(ProductCatalog catalogImpl);
		public List<ProductCatalog> getHotDealProductList();
		public List<ProductCatalog> getNewestProductList( );
		public List<ProductCatalog> getPopularProductList( );
		public  List<MainCatagory>  getMainCategoryForApplicationProductListDropdown();
		public  List<CustomerAddressVO>  getCustomerAddressDetails(long custId);
		public  boolean  deleteCustomerAddress(Long custAddressId);
		public  PaymentGatewayDetailsVO  getPaymentGatewayHash(PaymentGatewayDetailsVO paymentGatewayDetailsVO);
		public String saveOrderInfo(SemikartOrder semikartOrder);
		public long saveOrderItemInfo(SemikartOrderItem semikartOrderItem);
		public long saveOrderPaymentInfo(SemikartOrderPayment semikartOrderPayment);
		public java.util.Date getCurrentDate();
		public long saveWhishList(CartVo cartVo);
		public  List<CartVo>  getWishListDeatils(long custId);
}
