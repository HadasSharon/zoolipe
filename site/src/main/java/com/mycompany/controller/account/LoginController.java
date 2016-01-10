/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mycompany.controller.account;

import java.util.List;

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.core.web.controller.account.BroadleafLoginController;
import org.broadleafcommerce.core.web.controller.account.ResetPasswordForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.persistance.domain.MainCatagory;
import com.mycompany.persistance.domain.ProductCatalog;
import com.mycompany.persistance.service.SemikartService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The controller responsible for all actions involving logging a customer in
 */
@Controller
public class LoginController extends BroadleafLoginController {
	@Autowired(required = true)
	private SemikartService semikartService;

	@RequestMapping("/")
	public String homePage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		System.out.println("Inside home page controller");
		/*
		 * model.addAttribute("dynamicSliderList",
		 * semikartService.fetchProductCatalogDetails());
		 */
		List<MainCatagory> productMenuDropDownList = semikartService
				.getMainCategoryForProductListDropdown();

	
		request.getSession().setAttribute("ProductMenuDropDown",
				productMenuDropDownList);
		request.getSession().setAttribute("NewProductsListDropDown",
				semikartService.getNewProductListDropdown());
		request.getSession().setAttribute("ProductMenuDropDown",
				productMenuDropDownList);
		request.getSession().setAttribute(
				"applicationProductList",
				semikartService
						.getMainCategoryForApplicationProductListDropdown());

		model.addAttribute("hotProductList",
				semikartService.getHotDealProductList());
		model.addAttribute("popularProductList",
				semikartService.getPopularProductList());
		model.addAttribute("newestProductList",
				semikartService.getNewestProductList());
		

		return "layout/home";
	}

	@RequestMapping("/semikart.html")
	public String semikartHomePage(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		System.out.println("Inside home semikart homepage");
		return "layout/home";
	}

	@RequestMapping("/login")
	public String login(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// String resultPage = super.login(request, response, model);
		String resultPage = "authentication/login";
		System.out.println("resultPage" + resultPage);
		return resultPage;
	}

	@RequestMapping(value = "/login/forgotPassword", method = RequestMethod.GET)
	public String forgotPassword(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return super.forgotPassword(request, response, model);
	}

	@RequestMapping(value = "/login/forgotPassword", method = RequestMethod.POST)
	public String processForgotPassword(
			@RequestParam("emailAddress") String emailAddress,
			HttpServletRequest request, Model model) {
		return super.processForgotPassword(emailAddress, request, model);
	}

	@RequestMapping(value = "/login/resetPassword", method = RequestMethod.GET)
	public String resetPassword(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return super.resetPassword(request, response, model);
	}

	@RequestMapping(value = "/login/resetPassword", method = RequestMethod.POST)
	public String processResetPassword(
			@ModelAttribute("resetPasswordForm") ResetPasswordForm resetPasswordForm,
			HttpServletRequest request, HttpServletResponse response,
			Model model, BindingResult errors) throws ServiceException {
		return super.processResetPassword(resetPasswordForm, request, response,
				model, errors);
	}

	@Override
	public String getResetPasswordUrl(HttpServletRequest request) {
		String url = request.getScheme() + "://" + request.getServerName()
				+ getResetPasswordPort(request, request.getScheme() + "/");

		if (request.getContextPath() != null
				&& !"".equals(request.getContextPath())) {
			url = url + request.getContextPath() + "/login/resetPassword";
		} else {
			url = url + "/login/resetPassword";
		}
		return url;
	}
}
