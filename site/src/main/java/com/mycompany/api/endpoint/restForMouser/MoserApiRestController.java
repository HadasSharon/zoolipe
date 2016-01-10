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

package com.mycompany.api.endpoint.restForMouser;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.persistance.service.MouserApiService;
import com.mycompany.persistance.service.MouserApiServiceImpl;

/**
 * This is a reference REST API endpoint for cart. This can be modified, used as
 * is, or removed. The purpose is to provide an out of the box RESTful cart
 * service implementation, but also to allow the implementor to have fine
 * control over the actual API, URIs, and general JAX-RS annotations.
 * 
 * @author Kelly Tisdell
 *
 */
@RestController
@Path("/myresource")
@Consumes("text/plain")
public class MoserApiRestController {

	@GET
	@Path("/add/{searchKey}")
	
	public void invokeMoser(@PathParam("searchKey") String searchKey) {
		//String[] checkBox = { "RoHS" };
		//MouserApiServiceRestImpl mouserApiServiceImpl = new MouserApiServiceRestImpl();
		//mouserApiServiceImpl.processRequest(searchKey, checkBox);
		System.out.print("inside mouser rest controller");

	}

}
