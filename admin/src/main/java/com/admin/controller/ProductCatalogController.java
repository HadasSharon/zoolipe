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
import com.admin.domain.ProductCatalog;
import com.admin.domain.ProductCatalog;
import com.admin.service.AdminService;
import com.google.common.io.Files;

@Controller
@RequestMapping("/" + ProductCatalogController.SECTION_KEY)
public class ProductCatalogController extends AdminAbstractController {

	protected static final String SECTION_KEY = "productCatalog";

	@Resource(name = "blAdminService")
	private AdminService adminService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getSearchWindow(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("customView",
				"views/productCatalog/searchProductCatalog");
		model.addAttribute("productCatalogList",
				adminService.fetchProductCatalogDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addProductCatalogWindow(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("customView",
				"views/productCatalog/addProductCatalog");
		model.addAttribute("manufacturerList",
				adminService.fetchManufacturerDetails());
		model.addAttribute("categoryList", adminService.fetchCategoryDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/close", method = RequestMethod.GET)
	public String closeAddWindow(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("customView",
				"views/productCatalog/searchProductCatalog");
		model.addAttribute("productCatalogList",
				adminService.fetchProductCatalogDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProductCatalogDataPOST(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@ModelAttribute("productCatalog") ProductCatalog productCatalogConfigAdmin)
			throws Exception {

		model.addAttribute("customView",
				"views/productCatalog/searchProductCatalog");
	
		setModelAttributes(model, SECTION_KEY);
		if (!productCatalogConfigAdmin.getImageFile().isEmpty()) {
			try {
				byte[] bytes = productCatalogConfigAdmin.getImageFile()
						.getBytes();
				String fileName = productCatalogConfigAdmin.getImageFile()
						.getOriginalFilename();
				System.out.println(" file Name " + fileName);
				String ext = Files.getFileExtension(fileName);
				System.out.println(ext); //
				// Creating the directory to store file
				String pathData = request.getRealPath("");
				System.out.println(" request.getRealPath 12 " + pathData);
				File dir = new File(pathData + File.separator
						+ DatabaseConstants.PRODUCT_IMAGE_PATH);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				String imageName = productCatalogConfigAdmin
						.getManufacturerPart().trim() + "." + ext;
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + imageName);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				String imageUrl = CoreConstants.IMAGE_URL
						+ DatabaseConstants.PRODUCT_IMAGE_PATH + imageName;
				productCatalogConfigAdmin.setImageUrl(imageUrl);

			} catch (Exception e) {

				System.out.print("Inside catch");
				e.printStackTrace();
			}

		}
		if (!productCatalogConfigAdmin.getPdfFile().isEmpty()) {
			try {
				byte[] bytes = productCatalogConfigAdmin.getPdfFile()
						.getBytes();
				String fileName = productCatalogConfigAdmin.getPdfFile()
						.getOriginalFilename();
				System.out.println(" file Name " + fileName);
				String ext = Files.getFileExtension(fileName);
				System.out.println(ext); //
				// Creating the directory to store file
				String pathData = request.getRealPath("");
				System.out.println(" request.getRealPath 12 " + pathData);
				File dir = new File(pathData + File.separator
						+ DatabaseConstants.PRODUCT_DATASHEET_PATH);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				String imageName = productCatalogConfigAdmin
						.getManufacturerPart().trim() + "." + ext;
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + imageName);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				String imageUrl = CoreConstants.IMAGE_URL
						+ DatabaseConstants.PRODUCT_DATASHEET_PATH + imageName;
				productCatalogConfigAdmin.setDataSheet(imageUrl);

			} catch (Exception e) {

				System.out.print("Inside catch");
				e.printStackTrace();
			}

		}
		adminService.saveProductCatalog(productCatalogConfigAdmin);
		model.addAttribute("productCatalogList",
				adminService.fetchProductCatalogDetails());
		return "modules/emptyContainer";

	}

	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String saveProductCatalogDataGET(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@ModelAttribute("productCatalog") ProductCatalog productCatalogConfigAdmin)
			throws Exception {
		adminService.saveProductCatalog(productCatalogConfigAdmin);
		model.addAttribute("customView",
				"views/productCatalog/searchProductCatalog");
		model.addAttribute("productCatalogList",
				adminService.fetchProductCatalogDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";

	}

	@RequestMapping(value = "/getProductCatalogForEdit", method = RequestMethod.GET)
	public String getProductCatalogForEdit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam("id") Long id) throws Exception {
		model.addAttribute("customView",
				"views/productCatalog/editProductCatalog");
		List<ProductCatalog> productCatalogConfigAdmins = adminService
				.findProductCatalogBySupplierNameForEdit("", id);
		ProductCatalog productCatalogConfigAdmin = productCatalogConfigAdmins
				.get(0);
		model.addAttribute("productCatalog", productCatalogConfigAdmin);
		model.addAttribute("manufacturerList",
				adminService.fetchManufacturerDetails());
		model.addAttribute("categoryList", adminService.fetchCategoryDetails());
		model.addAttribute("id", id);
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateProductCatalogData(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@ModelAttribute("productCatalog") ProductCatalog productCatalogConfigAdmin)
			throws Exception {

		model.addAttribute("customView",
				"views/productCatalog/searchProductCatalog");
	
		setModelAttributes(model, SECTION_KEY);
		if (!productCatalogConfigAdmin.getImageFile().isEmpty()) {
			try {
				byte[] bytes = productCatalogConfigAdmin.getImageFile()
						.getBytes();
				String fileName = productCatalogConfigAdmin.getImageFile()
						.getOriginalFilename();
				System.out.println(" file Name " + fileName);
				String ext = Files.getFileExtension(fileName);
				System.out.println(ext); //
				// Creating the directory to store file
				String pathData = request.getRealPath("");
				System.out.println(" request.getRealPath 12 " + pathData);
				File dir = new File(pathData + File.separator
						+ DatabaseConstants.PRODUCT_IMAGE_PATH);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				String imageName = productCatalogConfigAdmin
						.getManufacturerPart().trim() + "." + ext;
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + imageName);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				String imageUrl = CoreConstants.IMAGE_URL
						+ DatabaseConstants.PRODUCT_IMAGE_PATH + imageName;
				productCatalogConfigAdmin.setImageUrl(imageUrl);

			} catch (Exception e) {

				System.out.print("Inside catch");
				e.printStackTrace();
			}

		}
		if (!productCatalogConfigAdmin.getPdfFile().isEmpty()) {
			try {
				byte[] bytes = productCatalogConfigAdmin.getPdfFile()
						.getBytes();
				String fileName = productCatalogConfigAdmin.getPdfFile()
						.getOriginalFilename();
				System.out.println(" file Name " + fileName);
				String ext = Files.getFileExtension(fileName);
				System.out.println(ext); //
				// Creating the directory to store file
				String pathData = request.getRealPath("");
				System.out.println(" request.getRealPath 12 " + pathData);
				File dir = new File(pathData + File.separator
						+ DatabaseConstants.PRODUCT_DATASHEET_PATH);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				String imageName = productCatalogConfigAdmin
						.getManufacturerPart().trim() + "." + ext;
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + imageName);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				String dataSheetUrl = CoreConstants.IMAGE_URL
						+ DatabaseConstants.PRODUCT_DATASHEET_PATH + imageName;
				productCatalogConfigAdmin.setDataSheet(dataSheetUrl);

			} catch (Exception e) {

				System.out.print("Inside catch");
				e.printStackTrace();
			}

		}
		adminService.updateProductCatalog(productCatalogConfigAdmin);
		model.addAttribute("productCatalogList",
				adminService.fetchProductCatalogDetails());
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/searchByName", method = RequestMethod.POST)
	public String getSearchByName(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("customView",
				"views/productCatalog/searchProductCatalog");
		String productCatalogName = request.getParameter("productCatalogName");
		model.addAttribute("productCatalogList", adminService
				.findProductCatalogByProductCatalogName(productCatalogName));
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/deleteProductCatalog", method = RequestMethod.GET)
	public String deleteProductCatalog(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam("id") Long id) throws Exception {
		System.out.println("inside delete" + id);
		adminService.deleteProductCatalog(id);
		model.addAttribute("customView",
				"views/productCatalog/searchProductCatalog");
		model.addAttribute("productCatalogList",
				adminService.fetchProductCatalogDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}
}