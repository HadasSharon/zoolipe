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
import com.admin.domain.Category;
import com.admin.domain.ProductCatalog;
import com.admin.service.AdminService;
import com.admin.service.MouserApiService;

@Controller
@RequestMapping("/" + CategoryManagementController.SECTION_KEY)
public class CategoryManagementController extends AdminAbstractController {

	protected static final String SECTION_KEY = "catalogManagement";
	@Resource(name = "blAdminService")
	private AdminService adminService;

	@Autowired
	MouserApiService mouserService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getSearchWindow(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("customView", "views/catalogManagement/generateCatalog");
		model.addAttribute("keywordList", mouserService.getKeywordList());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String generateCatalogFromMouser(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		String[] keyWords=request.getParameterValues("keyWords");
		mouserService.processRequest(keyWords,request);
		model.addAttribute("customView", "views/catalogManagement/generateCatalog");
		model.addAttribute("keywordList", mouserService.getKeywordList());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}


	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addCategoryWindow(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("customView", "views/category/addCategory");
		model.addAttribute("mainSubCategoryList",
				adminService.fetchMainSubCatagoryDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/close", method = RequestMethod.GET)
	public String closeAddWindow(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("customView", "views/category/searchCategory");
		model.addAttribute("categoryList", adminService.fetchCategoryDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveCategoryDataPOST(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute("category") Category categoryConfigAdmin)
			throws Exception {
		adminService.saveCategory(categoryConfigAdmin);
	model.addAttribute("customView", "views/category/searchCategory");
		model.addAttribute("categoryList", adminService.fetchCategoryDetails());
	setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";

	}

	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String saveCategoryDataGET(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute("category") Category categoryConfigAdmin)
			throws Exception {
		adminService.saveCategory(categoryConfigAdmin);
	model.addAttribute("customView", "views/category/searchCategory");
		model.addAttribute("categoryList", adminService.fetchCategoryDetails());
		setModelAttributes(model, SECTION_KEY);
	return "modules/emptyContainer";

	}

	@RequestMapping(value = "/getCategoryForEdit", method = RequestMethod.GET)
	public String getCategoryForEdit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam("id") Long id) throws Exception {
		model.addAttribute("customView", "views/category/editCategory");
		List<Category> categoryConfigAdmins = adminService
				.findCategoryBySupplierNameForEdit("", id);
		model.addAttribute("mainSubCategoryList",
				adminService.fetchMainSubCatagoryDetails());
		Category categoryConfigAdmin = categoryConfigAdmins.get(0);
		model.addAttribute("category", categoryConfigAdmin);
		model.addAttribute("id", id);
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateCategoryData(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute("category") Category categoryConfigAdmin)
			throws Exception {
			adminService.updateCategory(categoryConfigAdmin);
		model.addAttribute("customView", "views/category/searchCategory");
		model.addAttribute("categoryList", adminService.fetchCategoryDetails());
		setModelAttributes(model, SECTION_KEY);
	return "modules/emptyContainer";
	}


}