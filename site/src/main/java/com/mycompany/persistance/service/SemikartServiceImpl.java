package com.mycompany.persistance.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.Util.DatabaseConstants;
import com.mycompany.persistance.dao.MouserApiDao;
import com.mycompany.persistance.dao.SemikartDao;
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
import com.mycompany.sample.payment.service.gateway.NullPaymentGatewayRollbackServiceImpl;

@Service
public class SemikartServiceImpl implements SemikartService {
	protected static final Log LOG = LogFactory
			.getLog(SemikartServiceImpl.class);

	@Autowired
	protected SemikartDao semikartDao;

	@Override
	public List<ProductCatalog> fetchProductCatalogDetails() {
		// TODO Auto-generated method stub
		return semikartDao.fetchProductCatalogDetails();
	}

	@Override
	public List<MainCatagory> getMainCategoryForProductListDropdown() {
		// TODO Auto-generated method stub
		return semikartDao.getMainCategoryForProductListDropdown();
	}

	@Override
	public List<ProductCatalog> getNewProductListDropdown() {
		// TODO Auto-generated method stub
		return semikartDao.getNewProductListDropdown();
	}

	public List<ProductCatalog> productSearch(ProductCatalog catalogImpl) {
		return semikartDao.productSearch(catalogImpl);
	}

	@Override
	public List<ProductCatalog> getHotDealProductList() {
		// TODO Auto-generated method stub
		return semikartDao.getHotDealProductList();
	}

	@Override
	public List<ProductCatalog> getNewestProductList() {
		// TODO Auto-generated method stub
		return semikartDao.getNewestProductList();
	}

	@Override
	public List<ProductCatalog> getPopularProductList() {
		// TODO Auto-generated method stub
		return semikartDao.getPopularProductList();
	}

	@Override
	public List<Category> productSearchCategoryList(
			ProductCatalog catalogImpl) {
		// TODO Auto-generated method stub
		return semikartDao.productSearchCategoryList(catalogImpl);
	}

	@Override
	public List<MainCatagory> getMainCategoryForApplicationProductListDropdown() {
		// TODO Auto-generated method stub
		return semikartDao.getMainCategoryForApplicationProductListDropdown();
	}

	@Override
	public List<CustomerAddressVO> getCustomerAddressDetails(long custId) {
		// TODO Auto-generated method stub
		return semikartDao.getCustomerAddressDetails(custId);
	}

	public boolean deleteCustomerAddress(Long custAddressId) {
		return semikartDao.deleteCustomerAddress(custAddressId);
	}

	@Override
	public PaymentGatewayDetailsVO getPaymentGatewayHash(
			PaymentGatewayDetailsVO paymentGatewayDetailsVO) {
		// TODO Auto-generated method stub
		String hash = "";

		String udf2 = "";

		System.out.println("************************************* " + udf2
				+ " ************************************");
		try {
			String txnid = semikartDao.getTransactionId();
			String password = DatabaseConstants.HDFC_PAYMENT_GATEWAY_TESTING_KEY
					+ "|"
					+ txnid
					+ "|"
					+ paymentGatewayDetailsVO.getAmount()
					+ "|"
					+ paymentGatewayDetailsVO.getProductinfo()
					+ "|"
					+ paymentGatewayDetailsVO.getFirstName()
					+ "|"
					+ paymentGatewayDetailsVO.getEmail()
					+ "|||||||||||"
					+ DatabaseConstants.HDFC_PAYMENT_GATEWAY_TESTING_SALT;

			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("SHA-512");
			md.update(password.getBytes());
			byte byteData[] = md.digest();

			// convert the byte to hex format method 1

			StringBuffer sb = new StringBuffer();

			for (int i1 = 0; i1 < byteData.length; i1++) {

				sb.append(Integer.toString((byteData[i1] & 0xff) + 0x100, 16)
						.substring(1));

			}

			hash = sb.toString();
			paymentGatewayDetailsVO.setTxnid(txnid);
			paymentGatewayDetailsVO.setHash(hash);
			System.out.println("Hex format : " + hash);
		} catch (Exception exception) {
			LOG.error(exception);
		}

		return paymentGatewayDetailsVO;
	}

	@Override
	public String saveOrderInfo(SemikartOrder semikartOrder) {
		// TODO Auto-generated method stub
		return semikartDao.saveOrderInfo(semikartOrder);
	}

	@Override
	public long saveOrderItemInfo(SemikartOrderItem semikartOrderItem) {
		// TODO Auto-generated method stub
		return semikartDao.saveOrderItemInfo(semikartOrderItem);
	}

	@Override
	public long saveOrderPaymentInfo(SemikartOrderPayment semikartOrderPayment) {
		// TODO Auto-generated method stub
		return semikartDao.saveOrderPaymentInfo(semikartOrderPayment);
	}

	public java.util.Date getCurrentDate() {
		return semikartDao.getCurrentDate();
	}

	public long saveWhishList(CartVo cartVo) {
		return semikartDao.saveWhishList(cartVo);
	}

	@Override
	public List<CartVo> getWishListDeatils(long custId) {
		// TODO Auto-generated method stub
		return semikartDao.getWishListDeatils(custId);
	}
}
