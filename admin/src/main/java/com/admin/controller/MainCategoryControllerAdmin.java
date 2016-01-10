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

import com.admin.CoreConstants;
import com.admin.Util.DatabaseConstants;
import com.admin.domain.AdminImpl;
import com.admin.domain.MainCatagory;
import com.admin.domain.ProductCatalog;
import com.admin.service.AdminService;
import com.google.common.io.Files;

@Controller
@RequestMapping("/" + MainCategoryControllerAdmin.SECTION_KEY)
public class MainCategoryControllerAdmin extends AdminAbstractController {

	protected static final String SECTION_KEY = "mainCategory";
	@Resource(name = "blAdminService")
	private AdminService adminService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getSearchWindow(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("customView",
				"views/mainCategory/searchMainCatagory");
		model.addAttribute("mainCategoryList",
				adminService.fetchMainCatagoryDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addMainCatagoryWindow(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("customView", "views/mainCategory/addMainCatagory");

		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/close", method = RequestMethod.GET)
	public String closeAddWindow(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("customView",
				"views/mainCategory/searchMainCatagory");
		model.addAttribute("mainCategoryList",
				adminService.fetchMainCatagoryDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveMainCatagoryDataPOST(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute("mainCategory") MainCatagory mainCategoryConfigAdmin)
			throws Exception {

		model.addAttribute("customView",
				"views/mainCategory/searchMainCatagory");
		model.addAttribute("mainCategoryList",
				adminService.fetchMainCatagoryDetails());
		setModelAttributes(model, SECTION_KEY);
		long mainCategoryId = adminService
				.saveMainCatagory(mainCategoryConfigAdmin);
		mainCategoryConfigAdmin.setMainCatagoryId(mainCategoryId);
		if (!mainCategoryConfigAdmin.getFile().isEmpty()) {
			try {
				byte[] bytes = mainCategoryConfigAdmin.getFile().getBytes();
				String fileName = mainCategoryConfigAdmin.getFile()
						.getOriginalFilename();
				System.out.println(" file Name " + fileName);
				String ext = Files.getFileExtension(fileName);
				System.out.println(ext); //
				// Creating the directory to store file
				String pathData = request.getRealPath("");
				System.out.println(" request.getRealPath 12 " + pathData);
				File dir = new File(pathData + File.separator
						+ DatabaseConstants.MAIN_CATEGORY_IMAGE_PATH);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				String imageName = mainCategoryConfigAdmin
						.getMaincatagoryName().trim()
						+ String.valueOf(mainCategoryConfigAdmin
								.getMainCatagoryId()) + "." + ext;
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + imageName);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				String imageUrl = CoreConstants.IMAGE_URL
						+ DatabaseConstants.MAIN_CATEGORY_IMAGE_PATH
						+ imageName;
				mainCategoryConfigAdmin.setImageUrl(imageUrl);
				adminService.updateMainCatagory(mainCategoryConfigAdmin);
			} catch (Exception e) {

				System.out.print("Inside catch");
				e.printStackTrace();
			}
		}

		return "modules/emptyContainer";

	}

	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String saveMainCatagoryDataGET(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute("mainCategory") MainCatagory mainCategoryConfigAdmin)
			throws Exception {
		adminService.saveMainCatagory(mainCategoryConfigAdmin);
		model.addAttribute("customView",
				"views/mainCategory/searchMainCatagory");
		model.addAttribute("mainCategoryList",
				adminService.fetchMainCatagoryDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";

	}

	@RequestMapping(value = "/getMainCatagoryForEdit", method = RequestMethod.GET)
	public String getMainCatagoryForEdit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam("id") Long id) throws Exception {
		model.addAttribute("customView", "views/mainCategory/editMainCatagory");
		List<MainCatagory> mainCategoryConfigAdmins = adminService
				.findMainCatagoryBySupplierNameForEdit("", id);
		MainCatagory mainCategoryConfigAdmin = mainCategoryConfigAdmins.get(0);
		model.addAttribute("mainCategory", mainCategoryConfigAdmin);
		model.addAttribute("id", id);
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateMainCatagoryData(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute("mainCategory") MainCatagory mainCategoryConfigAdmin)
			throws Exception {
		model.addAttribute("customView",
				"views/mainCategory/searchMainCatagory");
		model.addAttribute("mainCategoryList",
				adminService.fetchMainCatagoryDetails());
		setModelAttributes(model, SECTION_KEY);
		adminService.updateMainCatagory(mainCategoryConfigAdmin);
		if (!mainCategoryConfigAdmin.getFile().isEmpty()) {
			try {
				byte[] bytes = mainCategoryConfigAdmin.getFile().getBytes();
				String fileName = mainCategoryConfigAdmin.getFile()
						.getOriginalFilename();
				System.out.println(" file Name " + fileName);
				String ext = Files.getFileExtension(fileName);
				System.out.println(ext); //
				// Creating the directory to store file
				String pathData = request.getRealPath("");
				System.out.println(" request.getRealPath 12 " + pathData);
				File dir = new File(pathData + File.separator
						+ DatabaseConstants.MAIN_CATEGORY_IMAGE_PATH);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				String imageName = mainCategoryConfigAdmin
						.getMaincatagoryName().trim()
						+ String.valueOf(mainCategoryConfigAdmin
								.getMainCatagoryId()) + "." + ext;
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + imageName);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				String imageUrl = CoreConstants.IMAGE_URL
						+ DatabaseConstants.MAIN_CATEGORY_IMAGE_PATH
						+ imageName;
				mainCategoryConfigAdmin.setImageUrl(imageUrl);
				adminService.updateMainCatagory(mainCategoryConfigAdmin);
				
			} catch (Exception e) {

				System.out.print("Inside catch");
				e.printStackTrace();
			}
		}

		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/searchByName", method = RequestMethod.POST)
	public String getSearchByName(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("customView",
				"views/mainCategory/searchMainCatagory");
		String mainCategoryName = request.getParameter("mainCategoryName");
		model.addAttribute("mainCategoryList", adminService
				.findMainCatagoryByMainCatagoryName(mainCategoryName));
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/deleteMainCatagory", method = RequestMethod.GET)
	public String deleteMainCatagory(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam("id") Long id) throws Exception {
		System.out.println("inside delete" + id);
		adminService.deleteMainCatagory(id);
		model.addAttribute("customView",
				"views/mainCategory/searchMainCatagory");
		model.addAttribute("mainCategoryList",
				adminService.fetchMainCatagoryDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}
}