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

package com.mycompany.controller.wishList;

import java.util.List;

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.broadleafcommerce.core.web.controller.account.BroadleafRegisterController;
import org.broadleafcommerce.profile.web.core.form.RegisterCustomerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.persistance.domain.CartVo;
import com.mycompany.persistance.service.SemikartService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The controller responsible for registering a customer
 */
@Controller
@RequestMapping("/displayWishList")
public class DispalyWishListController extends BroadleafRegisterController {
	@Autowired
	SemikartService semikartService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getWIshListDetails(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam("customerId") Long customerId) {

		List<CartVo> wishListVOS = semikartService
				.getWishListDeatils(customerId);
		model.addAttribute("wishListVOS", wishListVOS);
		return "account/manageWishlist";

	}

	@RequestMapping(method = RequestMethod.POST)
	public String processRegister(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@ModelAttribute("registrationForm") RegisterCustomerForm registerCustomerForm,
			BindingResult errors) throws ServiceException, PricingException {
		System.out.print("inside register");

		return super.processRegister(registerCustomerForm, errors, request,
				response, model);
	}

}
