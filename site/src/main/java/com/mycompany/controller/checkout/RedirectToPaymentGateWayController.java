/*
 * Copyright 2008-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mycompany.controller.checkout;

import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.inventory.service.InventoryUnavailableException;
import org.broadleafcommerce.core.order.service.call.AddToCartItem;
import org.broadleafcommerce.core.order.service.exception.AddToCartException;
import org.broadleafcommerce.core.order.service.exception.ProductOptionValidationException;
import org.broadleafcommerce.core.order.service.exception.RemoveFromCartException;
import org.broadleafcommerce.core.order.service.exception.RequiredAttributeNotProvidedException;
import org.broadleafcommerce.core.order.service.exception.UpdateCartException;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.broadleafcommerce.core.web.controller.account.BroadleafManageCustomerAddressesController;
import org.broadleafcommerce.core.web.controller.account.BroadleafManageWishlistController;
import org.broadleafcommerce.core.web.controller.cart.BroadleafCartController;
import org.broadleafcommerce.core.web.order.CartState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.admin.Util.DatabaseConstants;
import com.mycompany.persistance.domain.CartVo;
import com.mycompany.persistance.domain.CustomerAddressVO;
import com.mycompany.persistance.domain.MainCatagory;
import com.mycompany.persistance.domain.PaymentGatewayDetailsVO;
import com.mycompany.persistance.domain.SemikartOrder;
import com.mycompany.persistance.domain.SemikartOrderItem;
import com.mycompany.persistance.domain.SemikartOrderPayment;
import com.mycompany.persistance.service.SemikartService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/redirtToPaymentgateWay")
public class RedirectToPaymentGateWayController extends
		BroadleafManageCustomerAddressesController {

	@Value("${solr.index.use.sku}")
	protected boolean useSku;
	@Autowired
	private SemikartService semikartService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String removeCustomerAddress(
			HttpServletRequest request,
			Model model,
			@ModelAttribute("PaymentGatewayDetailsVO") PaymentGatewayDetailsVO paymentGatewayDetailsVO,
			@ModelAttribute("Order") SemikartOrder order) {

		String[] manufacturerPart = request
				.getParameterValues("manuFacturerPart");
		String[] quantity = request.getParameterValues("quantityOrdered");

		String[] vat = request.getParameterValues("vat");
		String[] itemPrice = request.getParameterValues("price");
		String[] totalPrice = request.getParameterValues("totalPrice");
		String[] supplierId = request.getParameterValues("supplierId");

		java.util.Date currentDate = semikartService.getCurrentDate();
		
		order.setOrderDate(currentDate);
		order.setDateUpdated(currentDate);
		
		paymentGatewayDetailsVO.setAmount(order.getOrderTotal());

		paymentGatewayDetailsVO.setCurl("http://semikart.com/Cancelled.jsp");
		paymentGatewayDetailsVO.setProductinfo("Semikart Product");
		paymentGatewayDetailsVO.setFurl("http://semikart.com/Failure.jsp");
		paymentGatewayDetailsVO
				.setKey(DatabaseConstants.HDFC_PAYMENT_GATEWAY_TESTING_KEY);
		paymentGatewayDetailsVO.setSurl("http://192.168.0.100:8080/responseFromPaymentgateWay");

		PaymentGatewayDetailsVO finalGatewayDetailsVO = semikartService
				.getPaymentGatewayHash(paymentGatewayDetailsVO);
		model.addAttribute("finalGatewayDetailsVO", finalGatewayDetailsVO);

		order.setEmailAddress(finalGatewayDetailsVO.getEmail());
		order.setOrderStatus(DatabaseConstants.ORDER_STATUS);
		String orderIdNo = semikartService.saveOrderInfo(order);

		String[] orderIdNoArray = orderIdNo.split("/");

		SemikartOrderItem semikartOrderItem = null;
		for (int i = 0; i < manufacturerPart.length; i++) {
			semikartOrderItem = new SemikartOrderItem();
			semikartOrderItem.setOrderId(Long.parseLong(orderIdNoArray[0]));
			semikartOrderItem.setManufacturerPart(manufacturerPart[i]);
			semikartOrderItem.setPrice(Float.parseFloat(itemPrice[i]));
			semikartOrderItem.setQuantityOrdered(Integer.parseInt(quantity[i]));
			semikartOrderItem.setVat(Float.parseFloat(vat[i]));
			semikartOrderItem.setTotalPrice(Float.parseFloat(totalPrice[i]));
			semikartOrderItem.setSupplierId(supplierId[i]);
			semikartService.saveOrderItemInfo(semikartOrderItem);
		}

		SemikartOrderPayment semikartOrderPayment = new SemikartOrderPayment();
		semikartOrderPayment
				.setPaymentTransactionNumber(paymentGatewayDetailsVO.getTxnid());
		semikartOrderPayment
				.setPaymentStatus(DatabaseConstants.TRANSACTION_STATUS_PENDING);
		semikartOrderPayment.setTransactionDate(currentDate);
		semikartOrderPayment.setOrderId(Long.parseLong(orderIdNoArray[0]));
		semikartOrderPayment.setTransactionAmount(order.getOrderTotal());
		semikartOrderPayment.setCustomerId(order.getCustomerId());
		semikartService.saveOrderPaymentInfo(semikartOrderPayment);

		return "checkout/redirectToPaymentGateway";
	}

}
