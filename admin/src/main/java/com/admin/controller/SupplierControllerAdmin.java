package com.admin.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.broadleafcommerce.openadmin.dto.ClassMetadata;
import org.broadleafcommerce.openadmin.dto.ClassTree;
import org.broadleafcommerce.openadmin.dto.Entity;
import org.broadleafcommerce.openadmin.dto.SectionCrumb;
import org.broadleafcommerce.openadmin.web.controller.AdminAbstractController;
import org.broadleafcommerce.openadmin.web.form.entity.EntityForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.admin.domain.AdminImpl;
import com.admin.domain.ProductCatalog;

import com.admin.domain.SupplierConfig;
import com.admin.service.AdminService;
import com.google.common.io.Files;

@Controller
@RequestMapping("/" + SupplierControllerAdmin.SECTION_KEY)
public class SupplierControllerAdmin extends AdminAbstractController {

	protected static final String SECTION_KEY = "supplier";
	@Resource(name = "blAdminService")
	private AdminService adminService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getSearchWindow(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("customView", "views/supplier/searchSupplier");
		model.addAttribute("supplierList", adminService.fetchSupplierDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addSupplierWindow(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("customView", "views/supplier/addSupplier");
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/close", method = RequestMethod.GET)
	public String closeAddWindow(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("customView", "views/supplier/searchSupplier");
		model.addAttribute("supplierList", adminService.fetchSupplierDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveSupplierDataPOST(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute("supplier") SupplierConfig supplierConfigAdmin)
			throws Exception {
		adminService.saveSupplier(supplierConfigAdmin);
		model.addAttribute("customView", "views/supplier/searchSupplier");
		model.addAttribute("supplierList", adminService.fetchSupplierDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";

	}

	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String saveSupplierDataGET(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute("supplier") SupplierConfig supplierConfigAdmin)
			throws Exception {
		adminService.saveSupplier(supplierConfigAdmin);
		model.addAttribute("customView", "views/supplier/searchSupplier");
		model.addAttribute("supplierList", adminService.fetchSupplierDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";

	}

	@RequestMapping(value = "/getSupplierForEdit", method = RequestMethod.GET)
	public String getSupplierForEdit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam("id") Long id) throws Exception {
		model.addAttribute("customView", "views/supplier/editSupplier");
		List<SupplierConfig> supplierConfigAdmins = adminService
				.findSupplierBySupplierNameForEdit("", id);
		SupplierConfig supplierConfigAdmin = supplierConfigAdmins.get(0);
		model.addAttribute("supplier", supplierConfigAdmin);
		model.addAttribute("id", id);
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateSupplierData(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute("supplier") SupplierConfig supplierConfigAdmin)
			throws Exception {
		adminService.updateSupplier(supplierConfigAdmin);
		model.addAttribute("customView", "views/supplier/searchSupplier");
		model.addAttribute("supplierList", adminService.fetchSupplierDetails());

		if (!supplierConfigAdmin.getFile().isEmpty()) {
			try {
				byte[] bytes = supplierConfigAdmin.getFile().getBytes();
				String fileName = supplierConfigAdmin.getFile()
						.getOriginalFilename();
				System.out.println(" file Name " + fileName);
				String ext = Files.getFileExtension(fileName);
				System.out.println(ext);
				// Creating the directory to store file
				String pathData = request.getRealPath("");
				System.out.println(" request.getRealPath 12 " + pathData);
				File dir = new File(pathData + File.separator + "supplierImg");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator
						+ supplierConfigAdmin.getSupplierName() + "." + ext);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				System.out.print("updateManufacturerLogoAction ");
				adminService.updateSupplierLogo(
						supplierConfigAdmin.getSupplierId(), serverFile);

				return "modules/emptyContainer";
			} catch (Exception e) {

				System.out.print("Inside catch");
				e.printStackTrace();
			}
		}

		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/searchByName", method = RequestMethod.POST)
	public String getSearchByName(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("customView", "views/supplier/searchSupplier");
		String supplierName = request.getParameter("supplierName");
		model.addAttribute("supplierList",
				adminService.findSupplierBySupplierName(supplierName));
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/deleteSupplier", method = RequestMethod.GET)
	public String deleteSupplier(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam("id") Long id) throws Exception {
		System.out.println("inside delete" + id);
		adminService.deleteSupplier(id);
		model.addAttribute("customView", "views/supplier/searchSupplier");
		model.addAttribute("supplierList", adminService.fetchSupplierDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}
}