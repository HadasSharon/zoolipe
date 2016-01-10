package com.mycompany.api.endpoint.restForMouser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.text.StrTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.mycompany.persistance.dao.MouserApiDao;
import com.mycompany.persistance.dao.MouserApiDaoImpl;
import com.mycompany.persistance.domain.Category;
import com.mycompany.persistance.domain.MainCatagory;
import com.mycompany.persistance.domain.MainSubCatagory;
import com.mycompany.persistance.domain.ManufacturerConfig;
import com.mycompany.persistance.domain.PriceTag;
import com.mycompany.persistance.domain.ProductCatalog;
import com.mycompany.persistance.domain.SubcategoryImpl;
import com.mycompany.persistance.domain.SupplierCatalog;
import com.mycompany.persistance.domain.SupplierConfig;
import com.mycompany.persistance.domain.SupplierPriceConfig;

@Service
public class MouserApiServiceRestImpl {
	private static final long serialVersionUID = -7836597420751793534L;

	// public static String keyword;

	public MouserApiDaoRestImpl mouserApiDao = null;

	public ArrayList<ProductCatalog> processRequest(String keyword,
			String checkBox[]) {
		ArrayList<ProductCatalog> productCatalogImplList = null;
		productCatalogImplList = new ArrayList<ProductCatalog>();
		mouserApiDao = new MouserApiDaoRestImpl();
		System.out.println(keyword);
		try {
			if (keyword.contains("&")) {
				System.out.println("BEFORE ------> " + keyword);
				String[] parts = keyword.split("&");
				keyword = parts[0];
				keyword += parts[1];
				System.out.println("AFTER ------> " + keyword);
			}

			String RoHSnStocked = "None";
			System.out.println("!@#$%^&*()_++_)(*&^%$#@!===>BEFORE==> "
					+ RoHSnStocked);

			if (checkBox != null) {
				System.out.println(checkBox.length);
				String temp = "";
				for (int i = 0; i < checkBox.length; i++) {
					if (checkBox[i].equals("RoHS")) {
						RoHSnStocked = "Rohs";
						if (temp.equals(""))
							temp = "Rohs";
						else
							RoHSnStocked = "RohsAndInStock";
					}
					if (checkBox[i].equals("Stocked")) {
						RoHSnStocked = "InStock";
						if (temp.equals(""))
							temp = "InStock";
						else
							RoHSnStocked = "RohsAndInStock";
					}
				}
				System.out.println("!@#$%^&*()_++_)(*&^%$#@!===> "
						+ RoHSnStocked);
			}

			String SOAPUrl = "http://api.mouser.com/service/searchapi.asmx";
			String xmlFile2Send = "";
			String SOAPAction = "";

			if (keyword.equals("")) {
				System.out.println("Inside NULL IF ---> " + keyword);
				// response.sendRedirect("index.jsp");
			} else {
				char c = keyword.charAt(0);
				System.out.println("Inside NULL ELSE line 81 ---> " + c);
				if (c >= '0' && c <= '9') {
					System.out.println("Inside IF line 83 ---> " + keyword);
					xmlFile2Send = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
							+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
							+ "<soap:Header>"
							+ "<MouserHeader xmlns=\"http://api.mouser.com/service\">"
							+ "<AccountInfo>"
							+ "<PartnerID>97d569b4-1f3c-46fd-a1a7-7a86a2221d6a</PartnerID>"
							+ "</AccountInfo>"
							+ "</MouserHeader>"
							+ "</soap:Header>"
							+ "<soap:Body>"
							+ "<SearchByPartNumber xmlns=\"http://api.mouser.com/service\">"
							+ "<mouserPartNumber>" + keyword
							+ "</mouserPartNumber>" + "</SearchByPartNumber>"
							+ "</soap:Body>" + "</soap:Envelope>";
					SOAPAction = "http://api.mouser.com/service/SearchByPartNumber";
				} else {
					System.out.println("Inside ELSE line 101 ---> " + keyword);
					xmlFile2Send = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
							+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
							+ "<soap:Header>"
							+ "<MouserHeader xmlns=\"http://api.mouser.com/service\">"
							+ "<AccountInfo>"
							+ "<PartnerID>97d569b4-1f3c-46fd-a1a7-7a86a2221d6a</PartnerID>"
							+ "</AccountInfo>"
							+ "</MouserHeader>"
							+ "</soap:Header>"
							+ "<soap:Body>"
							+ "<SearchByKeyword xmlns=\"http://api.mouser.com/service\">"
							+ "<keyword>" + keyword + "</keyword>"
							+ "<records>1000</records>"
							+ "<startingRecord>1</startingRecord>"
							+ "<searchOptions>" + RoHSnStocked
							+ "</searchOptions>" + "</SearchByKeyword>"
							+ "</soap:Body>" + "</soap:Envelope>";
					SOAPAction = "http://api.mouser.com/service/SearchByKeyword";
				}

				// Create the connection where we're going to send the file.
				URL url = new URL(SOAPUrl);
				URLConnection connection = url.openConnection();
				HttpURLConnection httpConn = (HttpURLConnection) connection;

				SOAPMessage message;
				message = new MouserApiServiceRestImpl()
						.getSoapMessageFromString(xmlFile2Send);
				URLEncoder.encode(xmlFile2Send, "UTF-8");
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				message.writeTo(bout);

				byte[] b = bout.toByteArray();

				// Set the appropriate HTTP parameters.
				httpConn.setRequestProperty("Content-Length",
						String.valueOf(b.length));
				httpConn.setRequestProperty("Content-Type",
						"text/xml; charset=utf-8");
				httpConn.setRequestProperty("SOAPAction", SOAPAction);
				httpConn.setRequestMethod("POST");
				httpConn.setDoOutput(true);
				httpConn.setDoInput(true);

				// Everything's set up; send the XML that was read in to b.
				OutputStream out = httpConn.getOutputStream();
				out.write(b);
				out.close();

				// Read the response and write it to standard out.
				InputStreamReader isr = new InputStreamReader(
						httpConn.getInputStream());
				BufferedReader in = new BufferedReader(isr);

				String inputLine = null;
				DocumentBuilder builder = null;
				try {
					builder = DocumentBuilderFactory.newInstance()
							.newDocumentBuilder();
				} catch (ParserConfigurationException e1) {
					e1.printStackTrace();
				}
				InputSource src = new InputSource();

				while ((inputLine = in.readLine()) != null) {
					// System.out.println(inputLine);
					src.setCharacterStream(new StringReader(inputLine));
				}

				Document doc = null;
				try {
					doc = builder.parse(src);
				} catch (SAXException e1) {
					e1.printStackTrace();
				}

				String numberOfResult = doc
						.getElementsByTagName("NumberOfResult").item(0)
						.getTextContent();
				// String availability =
				// doc.getElementsByTagName("Availability").item(0).getTextContent();
				// String category =
				// doc.getElementsByTagName("Category").item(0).getTextContent();
				// String priceBreaks =
				// doc.getElementsByTagName("PriceBreaks").item(0).getTextContent();
				// System.out.println(numberOfResult+" ====> "+availability+" ====> "+category+" ====> "+priceBreaks);

				// Currency API for $ to Rs.
				String currency = null;
				/*
				 * try { Document cur = loadTestDocument(
				 * "http://www.webservicex.net/CurrencyConvertor.asmx/ConversionRate?FromCurrency=USD&ToCurrency=INR"
				 * ); //
				 * System.out.println(cur.getElementsByTagName("double").item
				 * (0).getTextContent()); currency =
				 * cur.getElementsByTagName("double").item(0) .getTextContent();
				 * // System.out.println(currency); //
				 * request.setAttribute("CurrencyUSDtoINR", currency); } catch
				 * (Exception e) { e.printStackTrace(); }
				 */
				/*
				 * double currencyINR; // static if dynamic currency not
				 * available // then // it will use if (currency != null) {
				 * currencyINR =65; } else {
				 * 
				 * }
				 */

				double currencyINR = 66.66;
				// System.out.println(" Present INR for $1 USD is "+currencyINR+" ====== "+cur);

				int i = Integer.parseInt(numberOfResult);
				System.out.println("i value is " + i);

				String FILE_PATH = "http://citsacademy.com/img/std01.jpg";
				ProductCatalog productCatalogImpl = null;
				SupplierCatalog supplierCatalogueImpl = null;
				try {

					for (int j = 0; j < i; j++) {
						productCatalogImpl = new ProductCatalog();
						supplierCatalogueImpl = new SupplierCatalog();
						if (doc.getElementsByTagName("ImagePath").item(j) == null) {
							productCatalogImpl.setImageUrl(FILE_PATH);
						} else {
							FILE_PATH = doc.getElementsByTagName("ImagePath")
									.item(j).getTextContent();
							productCatalogImpl.setImageUrl(FILE_PATH);
						}
						String[] stock = doc
								.getElementsByTagName("Availability").item(j)
								.getTextContent().split(" ");
						if (StringUtils.isNotBlank(stock[0])) {
							if (!stock[0].equals("None")) {
								supplierCatalogueImpl.setStock(Integer
										.parseInt(stock[0]));

							}
						}
						productCatalogImpl.setDataSheet(doc
								.getElementsByTagName("DataSheetUrl").item(j)
								.getTextContent());
						productCatalogImpl.setDescription(doc
								.getElementsByTagName("Description").item(j)
								.getTextContent());
						productCatalogImpl.setCategoryName(doc
								.getElementsByTagName("Category").item(j)
								.getTextContent());
						String[] leadTime = doc
								.getElementsByTagName("LeadTime").item(j)
								.getTextContent().split(" ");
						if (leadTime != null) {
							supplierCatalogueImpl.setLeadTime(Integer
									.parseInt(leadTime[0]));
						}
						/*
						 * mou.setLifecycleStatus(doc
						 * .getElementsByTagName("LifecycleStatus").item(j)
						 * .getTextContent());
						 */
						productCatalogImpl.setManufacturerName(doc
								.getElementsByTagName("Manufacturer").item(j)
								.getTextContent());
						productCatalogImpl.setManufacturerPart(doc
								.getElementsByTagName("ManufacturerPartNumber")
								.item(j).getTextContent());
						supplierCatalogueImpl.setMinu(Integer.parseInt(doc
								.getElementsByTagName("Min").item(j)
								.getTextContent()));
						supplierCatalogueImpl.setMult(Integer.parseInt(doc
								.getElementsByTagName("Mult").item(j)
								.getTextContent()));
						productCatalogImpl.setSemikartPart(doc
								.getElementsByTagName("MouserPartNumber")
								.item(j).getTextContent());
						productCatalogImpl.setPriceBreaks(doc
								.getElementsByTagName("PriceBreaks").item(j)
								.getTextContent());

						/*
						 * mou.setProductDetailUrl(doc
						 * .getElementsByTagName("ProductDetailUrl").item(j)
						 * .getTextContent());
						 * mou.setReeling(doc.getElementsByTagName
						 * ("Reeling").item(j) .getTextContent());
						 */
						productCatalogImpl.setRohs(doc
								.getElementsByTagName("ROHSStatus").item(j)
								.getTextContent());
						/*
						 * mou.setSuggestedReplacement(doc
						 * .getElementsByTagName("SuggestedReplacement")
						 * .item(j).getTextContent()); mou.setMultiSimBlue(doc
						 * .getElementsByTagName("MultiSimBlue").item(j)
						 * .getTextContent());
						 */

						productCatalogImpl.setIsNewProduct("N");
						productCatalogImpl.setIsFeaturedProduct("N");
						productCatalogImpl.setApiName("Mouser");

						long manuFacturerId = mouserApiDao
								.getManufacturerId(productCatalogImpl
										.getManufacturerName());
						System.out.println("manuFacturerId" + manuFacturerId);
						System.out.println("manufacturerpart"
								+ productCatalogImpl.getManufacturerPart());

						/*
						 * if (manuFacturerId == 0) { ManufacturerConfig
						 * manufacturersImpl = new ManufacturerConfig();
						 * manufacturersImpl
						 * .setManuFacturerName(productCatalogImpl
						 * .getManufacturerName()); manuFacturerId =
						 * mouserApiDao .saveManufacturer(manufacturersImpl); }
						 * productCatalogImpl.setManufacturerId(manuFacturerId);
						 * 
						 * 
						 * List<SubcategoryImpl> subcategoryImplList =
						 * mouserApiDao
						 * .findVatDetailsOnSubCategoryName(productCatalogImpl
						 * .getCategoryName());
						 * 
						 * 
						 * String mainCategoryName = keyword; String
						 * mainSubCategory = keyword;
						 * 
						 * 
						 * if (subcategoryImplList != null) { mainCategoryName =
						 * subcategoryImplList.get(0) .getCategory();
						 * mainSubCategory = subcategoryImplList.get(0)
						 * .getMainSubCategory(); category =
						 * subcategoryImplList.get(0) .getSubCategory();
						 * 
						 * }
						 * 
						 * 
						 * long categoryId = mouserApiDao
						 * .getCategoryId(productCatalogImpl
						 * .getCategoryName());
						 * System.out.println("Category Name" +
						 * productCatalogImpl.getCategoryName() + "Category Id"
						 * + categoryId); long maincategoryId =
						 * mouserApiDao.getMainCategoryId(mainCategoryName); if
						 * (maincategoryId == 0) { MainCatagory mainCatagory =
						 * new MainCatagory();
						 * mainCatagory.setMaincatagoryName(mainCategoryName);
						 * maincategoryId = mouserApiDao
						 * .saveMaincategory(mainCatagory); }
						 * 
						 * long mainSubcategoryId =
						 * mouserApiDao.getMainSubCategoryId(mainSubCategory);
						 * if (mainSubcategoryId == 0) { MainSubCatagory
						 * mainSubCatagory = new MainSubCatagory();
						 * mainSubCatagory
						 * .setMainSubCatagoryName(mainSubCategory);
						 * mainSubCatagory.setMainCatagoryId(maincategoryId);
						 * mainSubcategoryId = mouserApiDao
						 * .saveMainSubcategory(mainSubCatagory); }
						 * 
						 * if (categoryId == 0) { Category categoryObj = new
						 * Category();
						 * categoryObj.setCatagoryName(productCatalogImpl
						 * .getCategoryName());
						 * categoryObj.setMainSubcatagoryId(mainSubcategoryId);
						 * categoryId = mouserApiDao.saveCategory(categoryObj);
						 * } productCatalogImpl.setCategoryId(categoryId);
						 * 
						 * long productCatalogId = mouserApiDao
						 * .getProductCatalogId(productCatalogImpl
						 * .getManufacturerPart()); // Inserting into product
						 * catatalog Table if (productCatalogId == 0) {
						 * productCatalogId = mouserApiDao
						 * .saveProductCataLog(productCatalogImpl); } else {
						 * productCatalogImpl
						 * .setProductCatalogId(productCatalogId); mouserApiDao
						 * .updateProductCataLog(productCatalogImpl); }
						 * 
						 * 
						 * long supplierId = mouserApiDao
						 * .getSupplierId("Aqtronics"); if (supplierId == 0) {
						 * SupplierConfig supplierConfig = new SupplierConfig();
						 * supplierConfig.setSupplierName("Aqtronics");
						 * supplierId = mouserApiDao
						 * .insertSupplier(supplierConfig); }
						 * 
						 * long supplierCatalogId = mouserApiDao
						 * .getSupplierCatalogId(supplierId, productCatalogId);
						 * 
						 * supplierCatalogueImpl
						 * .setProductCatalogId(productCatalogId);
						 * supplierCatalogueImpl.setSupplierId(supplierId); if
						 * (supplierCatalogId == 0) { supplierCatalogId =
						 * mouserApiDao
						 * .insertSupplierCatalog(supplierCatalogueImpl); } else
						 * { supplierCatalogueImpl
						 * .setSupplierCatalogueId(supplierCatalogId);
						 * mouserApiDao
						 * .updateSupplierCataLog(supplierCatalogueImpl); }
						 * 
						 * 
						 * long supplierPriceConfigId = mouserApiDao
						 * .getSupplierPriceConfigId(supplierId, categoryId); if
						 * (supplierPriceConfigId == 0) { SupplierPriceConfig
						 * supplierPriceConfig = new SupplierPriceConfig();
						 * supplierPriceConfig.setCategoryId(categoryId);
						 * supplierPriceConfig.setCurrencyMarkup(2f);
						 * supplierPriceConfig.setVat(14.51f);
						 * supplierPriceConfig.setHfi(15f);
						 * supplierPriceConfig.setDuty(30);
						 * supplierPriceConfig.setSupplierId(supplierId);
						 * supplierPriceConfigId = mouserApiDao
						 * .insertSupplierPriceConfig(supplierPriceConfig); }
						 * List<SupplierPriceConfig> supplierPriceConfigList =
						 * mouserApiDao
						 * .getSupplierPriceConfigDetails(supplierId,
						 * categoryId); calculatePrice(supplierPriceConfigList,
						 * productCatalogImpl.getPriceBreaks(), currencyINR,
						 * productCatalogImpl.getManufacturerPart(),
						 * productCatalogId, supplierId); productCatalogImpl
						 * .setSupplierCatalog(supplierCatalogueImpl);
						 * 
						 * productCatalogImplList.add(productCatalogImpl);
						 */

					} // end of For loop

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productCatalogImplList;
	}

	/**
	 * Getting IP Address of the Client.
	 */
	public static final String _255 = "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
	public static final Pattern pattern = Pattern.compile("^(?:" + _255
			+ "\\.){3}" + _255 + "$");

	public static String longToIpV4(long longIp) {
		int octet3 = (int) ((longIp >> 24) % 256);
		int octet2 = (int) ((longIp >> 16) % 256);
		int octet1 = (int) ((longIp >> 8) % 256);
		int octet0 = (int) ((longIp) % 256);

		return octet3 + "." + octet2 + "." + octet1 + "." + octet0;
	}

	public static long ipV4ToLong(String ip) {
		String[] octets = ip.split("\\.");
		return (Long.parseLong(octets[0]) << 24)
				+ (Integer.parseInt(octets[1]) << 16)
				+ (Integer.parseInt(octets[2]) << 8)
				+ Integer.parseInt(octets[3]);
	}

	public static boolean isIPv4Private(String ip) {
		long longIp = ipV4ToLong(ip);
		return (longIp >= ipV4ToLong("10.0.0.0") && longIp <= ipV4ToLong("10.255.255.255"))
				|| (longIp >= ipV4ToLong("172.16.0.0") && longIp <= ipV4ToLong("172.31.255.255"))
				|| longIp >= ipV4ToLong("192.168.0.0")
				&& longIp <= ipV4ToLong("192.168.255.255");
	}

	public static boolean isIPv4Valid(String ip) {
		return pattern.matcher(ip).matches();
	}

	public static String getIpFromRequest(HttpServletRequest request) {
		String ip;
		boolean found = false;
		if ((ip = request.getHeader("x-forwarded-for")) != null) {
			StrTokenizer tokenizer = new StrTokenizer(ip, ",");
			while (tokenizer.hasNext()) {
				ip = tokenizer.nextToken().trim();
				if (isIPv4Valid(ip) && !isIPv4Private(ip)) {
					found = true;
					break;
				}
			}
		}
		if (!found) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * Converting String XML to SOAP Message.
	 */
	private SOAPMessage getSoapMessageFromString(String xml)
			throws SOAPException, IOException {
		MessageFactory factory = MessageFactory.newInstance();
		SOAPMessage message = factory
				.createMessage(
						new MimeHeaders(),
						new ByteArrayInputStream(xml.getBytes(Charset
								.forName("UTF-8"))));
		return message;
	}

	/**
	 * Dynamic Currency Getter from USD To INR .
	 */
	private static Document loadTestDocument(String url) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		return factory.newDocumentBuilder().parse(new URL(url).openStream());
	}

	/**
	 * Default constructor.
	 */
	public MouserApiServiceRestImpl() {
	}

	/*public ArrayList<PriceTagImpl> calculatePrice(
			List<SupplierPriceConfig> subcategoryImplList, String priceBreaks,
			double currencyINR, String manuFacturerPartNumber,
			long productCatalogId, long supplierId) {
		mouserApiDao = new MouserApiDaoImpl();
		ArrayList<PriceTagImpl> priceTagImplsList = new ArrayList<PriceTagImpl>();
		double cur_markup = subcategoryImplList.get(0).getCurrencyMarkup();
		double dty = subcategoryImplList.get(0).getDuty();
		double hfin = subcategoryImplList.get(0).getHfi();
		double vt = subcategoryImplList.get(0).getVat();
		hfin = hfin / 100;
		dty = dty / 100;
		vt = vt / 100;

		// Splitting the whole string in to two parts
		String[] temp = priceBreaks.split("USD");
		System.out.println("priceBreaks" + priceBreaks);
		PriceTagImpl priceTagImpl = null;
		for (int j1 = 0; j1 < temp.length; j1++) {
			priceTagImpl = new PriceTagImpl();
			int rup = j1;
			String semi = temp[j1];
			String[] sem = semi.split(java.util.regex.Pattern.quote("$"));
			String ss = "";
			String[] semi1 = sem[1].split(java.util.regex.Pattern.quote(","));
			if (semi1.length < 2) {
				ss = semi1[0];
			} else {
				ss = semi1[0] + semi1[1];
			}
			double dolToRup;
			if (ss != null) { // semi1[1] != null) {
				dolToRup = Double.parseDouble(ss);// semi1[1]);
				dolToRup = dolToRup * currencyINR;
				dolToRup = dolToRup + cur_markup; // adding
				dolToRup = dolToRup + (dolToRup * dty);
				dolToRup = dolToRup + (dolToRup * hfin);
				String temp11 = String.format("%.2f", dolToRup);
				ss = temp11;
				if (rup == 0) {
				}

				String type;
				Double dd = vt * 100;
				String temp1111 = String.format("%.2f", dd);
				if (dty == 0 && vt == 0) {
					type = "Exclusive of Duty & VAT";
				} else {
					type = "Inclusive of Duty + " + temp1111
							+ "% VAT as Applicable";
				}

				priceTagImpl.setQuentity(Integer.parseInt(sem[0]));
				priceTagImpl.setPrice(Float.parseFloat(ss));
				priceTagImpl.setTax(type);

			}

			long priceTagId = mouserApiDao.getPriceTagId(supplierId,
					productCatalogId, priceTagImpl.getQuentity());
			priceTagImpl.setSupplierId(supplierId);
			priceTagImpl.setProductCatalogId(productCatalogId);
			if (priceTagId == 0) {
				mouserApiDao.insertPriceTag(priceTagImpl);
			} else {
				priceTagImpl.setPriceTagId(priceTagId);
				mouserApiDao.updatePriceTag(priceTagImpl);
			}
			priceTagImplsList.add(priceTagImpl);
		}
		return priceTagImplsList;
	}*/

}
