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
@RequestMapping("/removeFromCart")
public class RemoveCartController  {

	@Value("${solr.index.use.sku}")
	protected boolean useSku;
	@Autowired
	private SemikartService semikartService;

	
	@RequestMapping(value="")
	public String remove(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam("manufacturerPart")String manufacturerPart)
			throws IOException, PricingException, RemoveFromCartException {

	
		HashMap<String, CartVo> map = new HashMap<String, CartVo>();
		HttpSession session = request.getSession();
		List<CartVo> addCartList = (ArrayList<CartVo>) session
				.getAttribute("ProductsInCart");
		for (int i = 0; i < addCartList.size(); i++) {
			map.put(addCartList.get(i).getManuFacturerPart(),
					addCartList.get(i));
		}
		map.remove(manufacturerPart);
		addCartList = new ArrayList<CartVo>(map.values());
		request.getSession().setAttribute("ProductsInCart", addCartList);
		request.getSession().setAttribute("NoOfProductsInCart",
				addCartList.size());
		/*String returnPath = super.remove(request, response, model,
				addToCartItem);*/
		/*if (isAjaxRequest(request)) {
			returnPath += " :: ajax";
		}*/
		
		return "cart/cart";
	}

	
}
