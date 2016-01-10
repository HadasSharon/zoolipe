package com.admin.controller;

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
import com.admin.domain.MainSubCatagory;
import com.admin.domain.ProductCatalog;
import com.admin.service.AdminService;

@Controller
@RequestMapping("/" + MainSubCategoryControllerAdmin.SECTION_KEY)
public class MainSubCategoryControllerAdmin extends AdminAbstractController {

	protected static final String SECTION_KEY = "mainSubCategory";
	@Resource(name = "blAdminService")
	private AdminService adminService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getSearchWindow(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("customView",
				"views/mainSubCategory/searchMainSubCatagory");
		model.addAttribute("mainSubCategoryList",
				adminService.fetchMainSubCatagoryDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addMainSubCatagoryWindow(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("customView",
				"views/mainSubCategory/addMainSubCatagory");
		model.addAttribute("mainCategoryList", adminService.fetchMainCatagoryDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/close", method = RequestMethod.GET)
	public String closeAddWindow(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("customView",
				"views/mainSubCategory/searchMainSubCatagory");
		model.addAttribute("mainSubCategoryList",
				adminService.fetchMainSubCatagoryDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveMainSubCatagoryDataPOST(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@ModelAttribute("mainSubCategory") MainSubCatagory mainSubCategoryConfigAdmin)
			throws Exception {
		adminService.saveMainSubCatagory(mainSubCategoryConfigAdmin);
		model.addAttribute("customView",
				"views/mainSubCategory/searchMainSubCatagory");
		model.addAttribute("mainSubCategoryList",
				adminService.fetchMainSubCatagoryDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";

	}

	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String saveMainSubCatagoryDataGET(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@ModelAttribute("mainSubCategory") MainSubCatagory mainSubCategoryConfigAdmin)
			throws Exception {
		adminService.saveMainSubCatagory(mainSubCategoryConfigAdmin);
		model.addAttribute("customView",
				"views/mainSubCategory/searchMainSubCatagory");
		model.addAttribute("mainSubCategoryList",
				adminService.fetchMainSubCatagoryDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";

	}

	@RequestMapping(value = "/getMainSubCatagoryForEdit", method = RequestMethod.GET)
	public String getMainSubCatagoryForEdit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam("id") Long id) throws Exception {
		model.addAttribute("customView",
				"views/mainSubCategory/editMainSubCatagory");
		List<MainSubCatagory> mainSubCategoryConfigAdmins = adminService
				.findMainSubCatagoryBySupplierNameForEdit("", id);
		MainSubCatagory mainSubCategoryConfigAdmin = mainSubCategoryConfigAdmins
				.get(0);
		model.addAttribute("mainCategoryList", adminService.fetchMainCatagoryDetails());
		model.addAttribute("mainSubCategory", mainSubCategoryConfigAdmin);
		model.addAttribute("id", id);
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateMainSubCatagoryData(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@ModelAttribute("mainSubCategory") MainSubCatagory mainSubCategoryConfigAdmin)
			throws Exception {
		adminService.updateMainSubCatagory(mainSubCategoryConfigAdmin);
		model.addAttribute("customView",
				"views/mainSubCategory/searchMainSubCatagory");
		model.addAttribute("mainSubCategoryList",
				adminService.fetchMainSubCatagoryDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/searchByName", method = RequestMethod.POST)
	public String getSearchByName(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("customView",
				"views/mainSubCategory/searchMainSubCatagory");
		String mainSubCategoryName = request
				.getParameter("mainSubCategoryName");
		model.addAttribute("mainSubCategoryList", adminService
				.findMainSubCatagoryByMainSubCatagoryName(mainSubCategoryName));
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/deleteMainSubCatagory", method = RequestMethod.GET)
	public String deleteMainSubCatagory(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam("id") Long id) throws Exception {
		System.out.println("inside delete" + id);
		adminService.deleteMainSubCatagory(id);
		model.addAttribute("customView",
				"views/mainSubCategory/searchMainSubCatagory");
		model.addAttribute("mainSubCategoryList",
				adminService.fetchMainSubCatagoryDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}
}