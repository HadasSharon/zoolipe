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

package com.mycompany.controller.catalog;

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.service.CatalogServiceImpl;
import org.broadleafcommerce.core.order.service.call.AddToCartItem;
import org.broadleafcommerce.core.order.service.exception.AddToCartException;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.broadleafcommerce.core.web.controller.catalog.BroadleafSearchController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.admin.service.MyEmailWebService;
import com.mycompany.persistance.domain.CartVo;
import com.mycompany.persistance.domain.MainCatagory;
import com.mycompany.persistance.domain.ProductCatalog;
import com.mycompany.persistance.service.MouserApiService;
import com.mycompany.persistance.service.SemikartService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/search")
@SessionAttributes(value = { "productDetail", "productCatalogImplList",
		"ProductMenu" })
public class SearchController extends BroadleafSearchController {

	@Autowired
	private MouserApiService mouserApi;
	@Autowired
	private SemikartService semikartService;

	/*
	 * @Autowired private MyEmailWebService myEmailWebService;
	 */

	@RequestMapping("")
	public String search(Model model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "searchKey") String q)
			throws ServletException, IOException, ServiceException {
		String resultPage = "catalog/search";
		String keyword = q;

		String[] checkBox = { "RoHS" };
		/*
		 * Date d = new Date(); d.setYear(2015); d.setMonth(12); d.setDate(16);
		 */
		/*
		 * myEmailWebService.sendOrderConfirmation(d, "1",
		 * "desaijitendra28@gmail.com");
		 */
		//mouserApi.processRequest(keyword, checkBox);

		/*
		 * String BASE_URI
		 * ="http://localhost:8080/restApi/myresource/add/Analog"; Client client
		 * = Client.create(); WebResource webResource =
		 * client.resource(BASE_URI);
		 * 
		 * ClientResponse response1 =
		 * webResource.accept("application/json").get(ClientResponse.class);
		 */

		ProductCatalog catalogImpl = new ProductCatalog();
		// To search products baced on manufacturere part semikart part and
		// category name
		catalogImpl.setCategoryName(q);
		catalogImpl.setSemikartPart(q);
		catalogImpl.setManufacturerPart(q);
		catalogImpl.setManufacturerName(q);
		List<ProductCatalog> productCatalogImpls = semikartService
				.productSearch(catalogImpl);
		if (productCatalogImpls.size() == 1) {
			model.addAttribute("productDetail", productCatalogImpls.get(0));
			resultPage = "catalog/product";
		}
		model.addAttribute("productCatalogImplList", productCatalogImpls);
		
		
		model.addAttribute("searchKey", q);
		model.addAttribute("productCategoryList",
				semikartService.productSearchCategoryList(catalogImpl));
		
		return resultPage;
	}

	
}