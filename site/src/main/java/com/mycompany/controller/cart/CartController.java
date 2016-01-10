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

package com.mycompany.controller.cart;

import org.apache.commons.lang.StringUtils;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.inventory.service.InventoryUnavailableException;
import org.broadleafcommerce.core.order.service.call.AddToCartItem;
import org.broadleafcommerce.core.order.service.exception.AddToCartException;
import org.broadleafcommerce.core.order.service.exception.ProductOptionValidationException;
import org.broadleafcommerce.core.order.service.exception.RemoveFromCartException;
import org.broadleafcommerce.core.order.service.exception.RequiredAttributeNotProvidedException;
import org.broadleafcommerce.core.order.service.exception.UpdateCartException;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.broadleafcommerce.core.web.controller.cart.BroadleafCartController;
import org.broadleafcommerce.core.web.order.CartState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycompany.persistance.domain.CartVo;
import com.mycompany.persistance.domain.CustomerAddressVO;
import com.mycompany.persistance.domain.MainCatagory;
import com.mycompany.persistance.service.SemikartService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController extends BroadleafCartController {

	@Value("${solr.index.use.sku}")
	protected boolean useSku;
	@Autowired
	private SemikartService semikartService;


	@RequestMapping("")
	public String cart(HttpServletRequest request,
			HttpServletResponse response, Model model,@RequestParam("customerId")Long customerId) throws PricingException {

		/*HttpSession session = request.getSession();
		// String returnPath = super.cart(request, response, model);
		long custId = 0;
		if (StringUtils.isNotEmpty(customerId)) {
			custId = Long.parseLong(customerId);
		}*/
		List<CustomerAddressVO> customerAddressVOs = semikartService
				.getCustomerAddressDetails(customerId);
		model.addAttribute("customerAddressVOs", customerAddressVOs);
		System.out.println("custId" + customerId);
		return "cart/cart";
	}

	/*
	 * The Heat Clinic does not show the cart when a product is added. Instead,
	 * when the product is added via an AJAX POST that requests JSON, we only
	 * neeAd to return a few attributes to update the state of the page. The most
	 * efficient way to do this is to call the regular add controller method,
	 * but instead return a map that contains the necessary attributes. By using
	 * the @ResposeBody tag, Spring will automatically use Jackson to convert
	 * the returned object into JSON for easy processing via JavaScript.
	 */
	@RequestMapping(value = "/add", produces = "application/json")
	public @ResponseBody Map<String, Object> addJson(
			HttpServletRequest request, HttpServletResponse response,
			Model model,
			@ModelAttribute("productCatalog") AddToCartItem addToCartItem)
			throws IOException, PricingException, AddToCartException {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {

			String manufacturerPart = request.getParameter("manufacturerPart");
			System.out.print("manufacturerPart" + manufacturerPart);
			String resultPage = super.add(request, response, model,
					addToCartItem);
			System.out.print("inside Cart " + resultPage + "manufacturerPart"
					+ manufacturerPart);

			if (addToCartItem.getItemAttributes() == null
					|| addToCartItem.getItemAttributes().size() == 0) {
				responseMap.put("productId", addToCartItem.getProductId());
			}
			responseMap.put("productName",
					catalogService
							.findProductById(addToCartItem.getProductId())
							.getName());
			responseMap.put("quantityAdded", addToCartItem.getQuantity());
			responseMap.put("cartItemCount",
					String.valueOf(CartState.getCart().getItemCount()));
			if (addToCartItem.getItemAttributes() == null
					|| addToCartItem.getItemAttributes().size() == 0) {
				responseMap.put("productId", addToCartItem.getProductId());
			}
			if (useSku) {
				responseMap.put("skuId", addToCartItem.getSkuId());
			}
		} catch (AddToCartException e) {
			if (e.getCause() instanceof RequiredAttributeNotProvidedException) {
				responseMap.put("error", "allOptionsRequired");
			} else if (e.getCause() instanceof ProductOptionValidationException) {
				ProductOptionValidationException exception = (ProductOptionValidationException) e
						.getCause();
				responseMap.put("error", "productOptionValidationError");
				responseMap.put("errorCode", exception.getErrorCode());
				responseMap.put("errorMessage", exception.getMessage());
				// blMessages.getMessage(exception.get, lfocale))
			} else if (e.getCause() instanceof InventoryUnavailableException) {
				responseMap.put("error", "inventoryUnavailable");
			} else {
				throw e;
			}
		}

		return responseMap;
	}

	/*
	 * The Heat Clinic does not support adding products with required product
	 * options from a category browse page when JavaScript is disabled. When
	 * this occurs, we will redirect the user to the full product details page
	 * for the given product so that the required options may be chosen.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/add", produces = { "text/html", "*/*" })
	public String add(HttpServletRequest request, HttpServletResponse response,
			Model model, RedirectAttributes redirectAttributes,
			@ModelAttribute("addToCartItem") AddToCartItem addToCartItem)
			throws IOException, PricingException, AddToCartException {

		try {

			 return "redirect:" + "catalog/product";
		} catch (Exception e) {
			e.printStackTrace();
			/*
			 * Product product = catalogService.findProductById(addToCartItem
			 * .getProductId());
			 */

		}
		return "/catalog/product";
	}

	@RequestMapping("/updateQuantity")
	public String updateQuantity(HttpServletRequest request,
			HttpServletResponse response, Model model,
			RedirectAttributes redirectAttributes,
			@ModelAttribute("addToCartItem") AddToCartItem addToCartItem)
			throws IOException, PricingException, UpdateCartException,
			RemoveFromCartException {
		try {
			String returnPath = super.updateQuantity(request, response, model,
					addToCartItem);
			if (isAjaxRequest(request)) {
				returnPath += " :: ajax";
			}
			return returnPath;
		} catch (UpdateCartException e) {
			if (e.getCause() instanceof InventoryUnavailableException) {
				// Since there was an exception, the order gets detached from
				// the Hibernate session. This re-attaches it
				CartState.setCart(orderService.findOrderById(CartState
						.getCart().getId()));
				if (isAjaxRequest(request)) {
					model.addAttribute("errorMessage",
							"Not enough inventory to fulfill your requested amount of "
									+ addToCartItem.getQuantity());
					return getCartView() + " :: ajax";
				} else {
					redirectAttributes.addAttribute("errorMessage",
							"Not enough inventory to fulfill your requested amount of "
									+ addToCartItem.getQuantity());
					return getCartPageRedirect();
				}
			} else {
				throw e;
			}
		}
	}

	/*
	 * @RequestMapping("/remove") public String remove(HttpServletRequest
	 * request, HttpServletResponse response, Model model,
	 * 
	 * @RequestParam("manufacturerPart")String manufacturerPart) throws
	 * IOException, PricingException, RemoveFromCartException {
	 * 
	 * 
	 * HashMap<String, CartVo> map = new HashMap<String, CartVo>(); HttpSession
	 * session = request.getSession(); List<CartVo> addCartList =
	 * (ArrayList<CartVo>) session .getAttribute("ProductsInCart"); for (int i =
	 * 0; i < addCartList.size(); i++) {
	 * map.put(addCartList.get(i).getManuFacturerPart(), addCartList.get(i)); }
	 * map.remove(manufacturerPart); addCartList = new
	 * ArrayList<CartVo>(map.values());
	 * request.getSession().setAttribute("ProductsInCart", addCartList); String
	 * returnPath = super.remove(request, response, model, addToCartItem); if
	 * (isAjaxRequest(request)) { returnPath += " :: ajax"; }
	 * 
	 * return "cart/cart"; }
	 */

	@Override
	@RequestMapping("/empty")
	public String empty(HttpServletRequest request,
			HttpServletResponse response, Model model) throws PricingException {
		// return super.empty(request, response, model);
		return "ajaxredirect:/";
	}

	@Override
	@RequestMapping("/promo")
	public String addPromo(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam("promoCode") String customerOffer)
			throws IOException, PricingException {
		String returnPath = super.addPromo(request, response, model,
				customerOffer);
		if (isAjaxRequest(request)) {
			returnPath += " :: ajax";
		}
		return returnPath;
	}

	@Override
	@RequestMapping("/promo/remove")
	public String removePromo(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam("offerCodeId") Long offerCodeId) throws IOException,
			PricingException {
		String returnPath = super.removePromo(request, response, model,
				offerCodeId);
		if (isAjaxRequest(request)) {
			returnPath += " :: ajax";
		}
		return returnPath;
	}

}
