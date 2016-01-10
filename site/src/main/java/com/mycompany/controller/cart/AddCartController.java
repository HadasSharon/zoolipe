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

import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.inventory.service.InventoryUnavailableException;
import org.broadleafcommerce.core.order.service.call.AddToCartItem;
import org.broadleafcommerce.core.order.service.exception.AddToCartException;
import org.broadleafcommerce.core.order.service.exception.ProductOptionValidationException;
import org.broadleafcommerce.core.order.service.exception.RemoveFromCartException;
import org.broadleafcommerce.core.order.service.exception.RequiredAttributeNotProvidedException;
import org.broadleafcommerce.core.order.service.exception.UpdateCartException;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.broadleafcommerce.core.web.controller.account.CustomerAddressForm;
import org.broadleafcommerce.core.web.controller.cart.BroadleafCartController;
import org.broadleafcommerce.core.web.order.CartState;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerImpl;
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
import com.mycompany.persistance.domain.MainCatagory;
import com.mycompany.persistance.domain.ProductCatalog;
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
@RequestMapping("/addToCart")
public class AddCartController {

	@Value("${solr.index.use.sku}")
	protected boolean useSku;
	@Autowired
	private SemikartService semikartService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String addCart(HttpServletRequest request,
			HttpServletResponse response, Model model,
			RedirectAttributes redirectAttributes) throws IOException,
			PricingException, AddToCartException {

		String wishListSelected = request.getParameter("wishListSelected");
		System.out.println("wishListSelected" + wishListSelected);

		String manufacturerPart = request.getParameter("manufacturerPart");
		
		
		String price = request.getParameter("price1");
		String qty = request.getParameter("qty1");
		String supplierName = request.getParameter("desc1");
		String vat = request.getParameter("vat1");
		String supplierId = request.getParameter("supplierId1");
		String productCatalogId=request.getParameter("productCatalogId");
		String customerId=request.getParameter("customerId");
		
		
		
		HashMap<String, CartVo> map = new HashMap<String, CartVo>();
		String resultPage = "catalog/product";
		ProductCatalog catalogImpl = new ProductCatalog();
		catalogImpl.setCategoryName(manufacturerPart);
		catalogImpl.setSemikartPart(manufacturerPart);
		catalogImpl.setManufacturerPart(manufacturerPart);
		catalogImpl.setManufacturerName(manufacturerPart);
		List<ProductCatalog> productCatalogImpls = semikartService
				.productSearch(catalogImpl);
		model.addAttribute("productDetail", productCatalogImpls.get(0));
		model.addAttribute("productCategoryList",
				semikartService.productSearchCategoryList(catalogImpl));
		CartVo cartObj = new CartVo();
		cartObj.setManuFacturerPart(manufacturerPart);
		cartObj.setSupplierName(supplierName);
		cartObj.setQuantity(Integer.parseInt(qty));
		cartObj.setVat(Float.parseFloat(vat));
		cartObj.setPrice(Float.parseFloat(price));
		cartObj.setCustomerId(Long.parseLong(customerId));
		cartObj.setProductCatalogId(Long.parseLong(productCatalogId));
		cartObj.setSupplierId(Long.parseLong(supplierId));
		
		float totaPrice = cartObj.getPrice() * cartObj.getQuantity();
		cartObj.setTotalPrice(totaPrice);
		HttpSession session = request.getSession();
		cartObj.getVat();

		model.addAttribute("productDetail", productCatalogImpls.get(0));
		if (wishListSelected.equals("false")) {
			List<CartVo> addCartList = (ArrayList<CartVo>) session
					.getAttribute("ProductsInCart");
			if (addCartList == null) {
				addCartList = new ArrayList<CartVo>();
			}
			addCartList.add(cartObj);
			float grandTotal = 0f;
			for (int i = 0; i < addCartList.size(); i++) {
				map.put(addCartList.get(i).getManuFacturerPart(),
						addCartList.get(i));
				grandTotal += addCartList.get(i).getTotalPrice();
			}
			addCartList = new ArrayList<CartVo>(map.values());
			request.getSession().setAttribute("ProductsInCart", addCartList);
			request.getSession().setAttribute("NoOfProductsInCart",
					addCartList.size());
			request.getSession().setAttribute("grandTotal", grandTotal);
		} else {
			cartObj.setStatus("ADDED TO WISHLIST");
			semikartService.saveWhishList(cartObj);
		}

		return resultPage;
	}

}
