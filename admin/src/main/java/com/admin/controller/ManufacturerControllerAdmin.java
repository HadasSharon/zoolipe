package com.admin.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.broadleafcommerce.openadmin.dto.ClassMetadata;
import org.broadleafcommerce.openadmin.dto.ClassTree;
import org.broadleafcommerce.openadmin.dto.Entity;
import org.broadleafcommerce.openadmin.dto.SectionCrumb;
import org.broadleafcommerce.openadmin.web.controller.AdminAbstractController;
import org.broadleafcommerce.openadmin.web.form.entity.EntityForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Constants;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.admin.CoreConstants;
import com.admin.Util.DatabaseConstants;
import com.admin.domain.AdminImpl;
import com.admin.domain.ProductCatalog;
import com.admin.domain.ManufacturerConfig;
import com.admin.service.AdminService;
import com.google.common.io.Files;

@Controller
@RequestMapping("/" + ManufacturerControllerAdmin.SECTION_KEY)
public class ManufacturerControllerAdmin extends AdminAbstractController {

	protected static final String SECTION_KEY = "manufacturer";
	@Resource(name = "blAdminService")
	private AdminService adminService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getSearchWindow(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("customView",
				"views/manufacturer/searchManufacturer");
		model.addAttribute("manufacturerList",
				adminService.fetchManufacturerDetails());

		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addManufacturerWindow(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("customView", "views/manufacturer/addManufacturer");
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/close", method = RequestMethod.GET)
	public String closeAddWindow(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("customView",
				"views/manufacturer/searchManufacturer");
		model.addAttribute("manufacturerList",
				adminService.fetchManufacturerDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveManufacturerDataPOST(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@ModelAttribute("manufacturer") ManufacturerConfig manufacturerConfigAdmin)
			throws Exception {

		model.addAttribute("customView",
				"views/manufacturer/searchManufacturer");
		model.addAttribute("manufacturerList",
				adminService.fetchManufacturerDetails());
		setModelAttributes(model, SECTION_KEY);
		long manufacturerId=adminService.saveManufacturer(manufacturerConfigAdmin);
		manufacturerConfigAdmin.setManufacturerId(manufacturerId);
		if (!manufacturerConfigAdmin.getFile().isEmpty()) {
			try {
				byte[] bytes = manufacturerConfigAdmin.getFile().getBytes();
				String fileName = manufacturerConfigAdmin.getFile()
						.getOriginalFilename();
				System.out.println(" file Name " + fileName);
				String ext = Files.getFileExtension(fileName);
				System.out.println(ext); //
				// Creating the directory to store file
				String pathData = request.getRealPath("");
				System.out.println(" request.getRealPath 12 " + pathData);
				File dir = new File(pathData + File.separator
						+ DatabaseConstants.MENUFACTURER_IMAGE_PATH);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				String imageName = manufacturerConfigAdmin
						.getManuFacturerName().trim()
						+ String.valueOf(manufacturerConfigAdmin
								.getManufacturerId()) + "." + ext;
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + imageName);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				String imageUrl = CoreConstants.IMAGE_URL
						+ DatabaseConstants.MENUFACTURER_IMAGE_PATH + imageName;
				manufacturerConfigAdmin.setImageUrl(imageUrl);
				adminService.updateManufacturer(manufacturerConfigAdmin);
			} catch (Exception e) {

				System.out.print("Inside catch");
				e.printStackTrace();
			}
		}
		
		return "modules/emptyContainer";

	}

	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String saveManufacturerDataGET(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@ModelAttribute("manufacturer") ManufacturerConfig manufacturerConfigAdmin)
			throws Exception {
		adminService.saveManufacturer(manufacturerConfigAdmin);
		model.addAttribute("customView",
				"views/manufacturer/searchManufacturer");
		model.addAttribute("manufacturerList",
				adminService.fetchManufacturerDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";

	}

	@RequestMapping(value = "/getManufacturerForEdit", method = RequestMethod.GET)
	public String getManufacturerForEdit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam("id") Long id) throws Exception {
		model.addAttribute("customView", "views/manufacturer/editManufacturer");
		List<ManufacturerConfig> manufacturerConfigAdmins = adminService
				.findManufacturerBySupplierNameForEdit("", id);
		ManufacturerConfig manufacturerConfigAdmin = manufacturerConfigAdmins
				.get(0);
		model.addAttribute("manufacturer", manufacturerConfigAdmin);
		model.addAttribute("id", id);
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateManufacturerData(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@ModelAttribute("manufacturer") ManufacturerConfig manufacturerConfigAdmin)
			throws Exception {
		System.out.println("inside update manufacturer");

		model.addAttribute("customView",
				"views/manufacturer/searchManufacturer");
		model.addAttribute("manufacturerList",
				adminService.fetchManufacturerDetails());
		System.out.print("updateManufacturerLogoAction1 ");
		setModelAttributes(model, SECTION_KEY);
		adminService.updateManufacturer(manufacturerConfigAdmin);
		if (!manufacturerConfigAdmin.getFile().isEmpty()) {
			try {
				byte[] bytes = manufacturerConfigAdmin.getFile().getBytes();
				String fileName = manufacturerConfigAdmin.getFile()
						.getOriginalFilename();
				System.out.println(" file Name " + fileName);
				String ext = Files.getFileExtension(fileName);
				System.out.println(ext); //
				// Creating the directory to store file
				String pathData = request.getRealPath("");
				System.out.println(" request.getRealPath 12 " + pathData);
				File dir = new File(pathData + File.separator
						+ DatabaseConstants.MENUFACTURER_IMAGE_PATH);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				String imageName = manufacturerConfigAdmin
						.getManuFacturerName().trim()
						+ String.valueOf(manufacturerConfigAdmin
								.getManufacturerId()) + "." + ext;
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + imageName);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				String imageUrl = CoreConstants.IMAGE_URL
						+ DatabaseConstants.MENUFACTURER_IMAGE_PATH + imageName;
				manufacturerConfigAdmin.setImageUrl(imageUrl);
				adminService.updateManufacturer(manufacturerConfigAdmin);
			
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
				"views/manufacturer/searchManufacturer");
		String manufacturerName = request.getParameter("manufacturerName");
		model.addAttribute("manufacturerList", adminService
				.findManufacturerByManufacturerName(manufacturerName));
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

	@RequestMapping(value = "/deleteManufacturer", method = RequestMethod.GET)
	public String deleteManufacturer(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam("id") Long id) throws Exception {
		System.out.println("inside delete" + id);
		adminService.deleteManufacturer(id);
		model.addAttribute("customView",
				"views/manufacturer/searchManufacturer");
		model.addAttribute("manufacturerList",
				adminService.fetchManufacturerDetails());
		setModelAttributes(model, SECTION_KEY);
		return "modules/emptyContainer";
	}

}