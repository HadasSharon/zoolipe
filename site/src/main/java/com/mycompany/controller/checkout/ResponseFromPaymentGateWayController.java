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


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

@Controller("PaymentGatewayWebResponseService")
@RequestMapping("/responseFromPaymentgateWay")
public class ResponseFromPaymentGateWayController {

	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String receiveResponseFromBank(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(true);
	
		String FirstName = request.getParameter("firstname");
		String UniqueReferenceID = request.getParameter("mihpayid"); 
		String ModeofTransaction = request.getParameter("mode");
		String StatusofPayment = request.getParameter("status");
		String TransactionID = request.getParameter("txnid");
		String Amount = request.getParameter("amount");
		//String ProductInfo = request.getParameter("productinfo");
		String BankCode = request.getParameter("bankcode");
		String BankReferenceNumber = request.getParameter("bank_ref_num");
		String UnMappedStatus = request.getParameter("unmappedstatus");
		String EmailId = request.getParameter("email");
		System.out.println("receivingResponse"+	BankReferenceNumber );

		return "checkout/redirectToPaymentGateway";
	}

}
