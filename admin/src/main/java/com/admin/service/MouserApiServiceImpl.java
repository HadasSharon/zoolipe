package com.admin.service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
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

import javax.imageio.ImageIO;
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

import com.admin.CoreConstants;
import com.admin.Util.DatabaseConstants;
import com.admin.dao.MouserApiDao;
import com.admin.domain.Category;
import com.admin.domain.Keyword;
import com.admin.domain.MainCatagory;
import com.admin.domain.MainSubCatagory;
import com.admin.domain.ManufacturerConfig;
import com.admin.domain.PriceTag;
import com.admin.domain.ProductCatalog;
import com.admin.domain.SubcategoryImpl;
import com.admin.domain.SupplierCatalog;
import com.admin.domain.SupplierConfig;
import com.admin.domain.SupplierPriceConfig;
import com.google.common.io.Files;

@Service
public class MouserApiServiceImpl implements MouserApiService {
	private static final long serialVersionUID = -7836597420751793534L;

	// public static String keyword;
	@Autowired
	public MouserApiDao mouserApiDao;

	public ArrayList<ProductCatalog> processRequest(String[] keyword,
			HttpServletRequest request) {
		ArrayList<ProductCatalog> productCatalogImplList = null;
		productCatalogImplList = new ArrayList<ProductCatalog>();
		System.out.println(keyword);
		try {

			String SOAPUrl = "http://api.mouser.com/service/searchapi.asmx";
			String xmlFile2Send = "";
			String SOAPAction = "";

			for (int iterator = 0; iterator < keyword.length; iterator++) {
				System.out.println("Inside IF line 83 ---> "
						+ keyword[iterator]);
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
						+ "<keyword>" + keyword[iterator] + "</keyword>"
						+ "<records>1000</records>"
						+ "<startingRecord>1</startingRecord>"
						+ "<searchOptions>" + "RohsAndInStock"
						+ "</searchOptions>" + "</SearchByKeyword>"
						+ "</soap:Body>" + "</soap:Envelope>";
				SOAPAction = "http://api.mouser.com/service/SearchByKeyword";

				SOAPAction = "http://api.mouser.com/service/SearchByKeyword";

				// Create the connection where we're going to send the file.
				URL url = new URL(SOAPUrl);
				URLConnection connection = url.openConnection();
				HttpURLConnection httpConn = (HttpURLConnection) connection;

				SOAPMessage message;
				message = new MouserApiServiceImpl()
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

				// Currency API for $ to Rs.
				String currency = null;

				try {
					Document cur = loadTestDocument("http://www.webservicex.net/CurrencyConvertor.asmx/ConversionRate?FromCurrency=USD&ToCurrency=INR"); //
					System.out.println(cur.getElementsByTagName("double")
							.item(0).getTextContent());
					currency = cur.getElementsByTagName("double").item(0)
							.getTextContent();
					System.out.println("currency" + currency); //
					if (!currency.equals("-1")) {
						mouserApiDao.updateCurrency(currency);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				double currencyINR = mouserApiDao.getCurrency();
				// System.out.println(" Present INR for $1 USD is "+currencyINR+" ====== "+cur);

				int i = Integer.parseInt(numberOfResult);
				System.out.println("i value is " + i);
				System.out.println("currencyINR " + currencyINR);

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
							// try{
							// URL mousreImgeUrl = new URL(FILE_PATH);
							System.out.print("FILE_PATH" + FILE_PATH);
							/*
							 * String pathData = request.getRealPath("");
							 * System.out.println(" request.getRealPath 12 " +
							 * pathData); File dir = new File(pathData +
							 * File.separator +
							 * DatabaseConstants.PRODUCT_IMAGE_PATH); if
							 * (!dir.exists()) dir.mkdirs();
							 * 
							 * // Create the file on server String imageName =
							 * doc .getElementsByTagName(
							 * "ManufacturerPartNumber").item(j)
							 * .getTextContent() + ".jpg"; File serverFile = new
							 * File(dir.getAbsolutePath() + File.separator +
							 * imageName);
							 * 
							 * String imageUrl = CoreConstants.IMAGE_URL +
							 * DatabaseConstants.PRODUCT_IMAGE_PATH + imageName;
							 * 
							 * InputStream is = mousreImgeUrl.openStream();
							 * OutputStream os = new
							 * FileOutputStream(serverFile);
							 * System.out.print("available" + is.available());
							 * byte[] bytesData = new byte[2048]; int length =
							 * 0;
							 * 
							 * while ((length = is.read(bytesData)) != -1) {
							 * System.out.print("length" + length);
							 * os.write(bytesData, 0, length); }
							 * 
							 * is.close(); os.close();
							 * 
							 * productCatalogImpl.setImageUrl(imageUrl);
							 * }catch(Exception e){ e.printStackTrace(); }
							 */
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

						String lf = doc.getElementsByTagName("LifecycleStatus")
								.item(j).getTextContent();
						String purl=doc.getElementsByTagName("ProductDetailUrl").item(j)
								.getTextContent();
						String reeling=doc.getElementsByTagName("Reeling").item(j)
								.getTextContent();

						System.out.println("LifelCycleStatus" + lf);
						System.out.println("ProductDetailUrl" + purl);
						System.out.println("reeling" + reeling);
						
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
						System.out.println("PriceBreakssssssssss"
								+ productCatalogImpl.getPriceBreaks());

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

						/*
						 * try { // Contacting the URL URL mousreDataSheetUrl =
						 * new URL( productCatalogImpl.getDataSheet()); String
						 * pathData1 = request.getRealPath("");
						 * System.out.println(" request.getRealPath 12 " +
						 * pathData1); File dir = new File(pathData1 +
						 * File.separator +
						 * DatabaseConstants.PRODUCT_DATASHEET_PATH); if
						 * (!dir.exists()) dir.mkdirs();
						 * 
						 * // Create the file on server String dataSheetName =
						 * doc .getElementsByTagName(
						 * "ManufacturerPartNumber").item(j) .getTextContent() +
						 * ".pdf"; File serverFile = new
						 * File(dir.getAbsolutePath() + File.separator +
						 * dataSheetName);
						 * 
						 * String dataSheetUrl = CoreConstants.IMAGE_URL +
						 * DatabaseConstants.PRODUCT_DATASHEET_PATH +
						 * dataSheetName; InputStream isDataSheet =
						 * mousreDataSheetUrl .openStream(); OutputStream os =
						 * new FileOutputStream(serverFile);
						 * 
						 * byte[] bytesData = new byte[2048]; int length = 0;
						 * 
						 * while ((length = isDataSheet.read(bytesData)) != -1)
						 * {
						 * 
						 * os.write(bytesData, 0, length); }
						 * 
						 * isDataSheet.close(); os.close();
						 * productCatalogImpl.setDataSheet(dataSheetUrl); }
						 * catch (Exception npe) {
						 * System.out.println("FAILED.\n[" + npe.getMessage() +
						 * "]\n"); }
						 */

						long manuFacturerId = mouserApiDao
								.getManufacturerId(productCatalogImpl
										.getManufacturerName());
						if (manuFacturerId == 0) {
							ManufacturerConfig manufacturersImpl = new ManufacturerConfig();
							manufacturersImpl
									.setManuFacturerName(productCatalogImpl
											.getManufacturerName());
							manuFacturerId = mouserApiDao
									.saveManufacturer(manufacturersImpl);
						}
						productCatalogImpl.setManufacturerId(manuFacturerId);

						List<SubcategoryImpl> subcategoryImplList = mouserApiDao
								.findVatDetailsOnSubCategoryName(productCatalogImpl
										.getCategoryName());

						String mainCategoryName = null;
						String mainSubCategory = null;
						System.out.println("Size" + subcategoryImplList.size());
						if (subcategoryImplList.size() != 0) {
							mainCategoryName = subcategoryImplList.get(0)
									.getCategory();
							mainSubCategory = subcategoryImplList.get(0)
									.getMainSubCategory();
							// category =
							// subcategoryImplList.get(0) .getSubCategory();

						} else {
							mainCategoryName = "Unknown";
							mainSubCategory = "Unknown";
						}

						long categoryId = mouserApiDao
								.getCategoryId(productCatalogImpl
										.getCategoryName());
						System.out.println("Category Name"
								+ productCatalogImpl.getCategoryName()
								+ "Category Id" + categoryId);

						/*
						 * if (maincategoryId == 0) { MainCatagory mainCatagory
						 * = new MainCatagory();
						 * mainCatagory.setMaincatagoryName(mainCategoryName);
						 * maincategoryId = mouserApiDao
						 * .saveMaincategory(mainCatagory); }
						 */

						/*
						 * if (mainSubcategoryId == 0) { MainSubCatagory
						 * mainSubCatagory = new MainSubCatagory();
						 * mainSubCatagory
						 * .setMainSubCatagoryName(mainSubCategory);
						 * mainSubCatagory.setMainCatagoryId(maincategoryId);
						 * mainSubcategoryId = mouserApiDao
						 * .saveMainSubcategory(mainSubCatagory); }
						 */

						if (categoryId == 0) {
							Category categoryObj = new Category();
							categoryObj.setCatagoryName(productCatalogImpl
									.getCategoryName());
							/*
							 * categoryObj.setVat(subcategoryImplList.get(0)
							 * .getVat());
							 * categoryObj.setHfi(subcategoryImplList.get(0)
							 * .getHfi());
							 * categoryObj.setCurrencyMarkup(subcategoryImplList
							 * .get(0).getCurrencyMarkup());
							 * categoryObj.setDuty(subcategoryImplList.get(0)
							 * .getDuty());
							 */
							categoryObj.setMainSubcatagoryId(260);
							categoryId = mouserApiDao.saveCategory(categoryObj);
						} else {
							long maincategoryId = mouserApiDao
									.getMainCategoryId(mainCategoryName);
							long mainSubcategoryId = mouserApiDao
									.getMainSubCategoryId(mainSubCategory);
							mouserApiDao.updateMainSubCategory(maincategoryId,
									mainSubcategoryId);
							mouserApiDao.updateCategory(mainSubcategoryId,
									categoryId);

						}
						productCatalogImpl.setCategoryId(categoryId);
						productCatalogImpl.setStatus("LOCKED");
						long productCatalogId = mouserApiDao
								.getProductCatalogId(productCatalogImpl
										.getManufacturerPart());
						// Inserting into product catatalog Table
						if (productCatalogId == 0) {
							productCatalogId = mouserApiDao
									.saveProductCataLog(productCatalogImpl);
						} /*
						 * else { productCatalogImpl
						 * .setProductCatalogId(productCatalogId); mouserApiDao
						 * .updateProductCataLog(productCatalogImpl); }
						 */

						long supplierId = mouserApiDao
								.getSupplierId("Aqtronics");
						String sku = productCatalogImpl.getManufacturerPart()
								+ String.valueOf(supplierId);
						if (supplierId == 0) {
							SupplierConfig supplierConfig = new SupplierConfig();
							supplierConfig.setSupplierName("Aqtronics");
							supplierId = mouserApiDao
									.insertSupplier(supplierConfig);
						}

						long supplierCatalogId = mouserApiDao
								.getSupplierCatalogId(supplierId,
										productCatalogId);

						supplierCatalogueImpl
								.setProductCatalogId(productCatalogId);
						supplierCatalogueImpl.setSku(sku);
						supplierCatalogueImpl.setSupplierId(supplierId);
						if (supplierCatalogId == 0) {
							supplierCatalogId = mouserApiDao
									.insertSupplierCatalog(supplierCatalogueImpl);
						}
						Category category = mouserApiDao
								.getCategoryDetailsBasedOn(categoryId);
						try {
							if (category.getVat() != 0) {
								calculatePrice(category,
										productCatalogImpl.getPriceBreaks(),
										currencyINR,
										productCatalogImpl
												.getManufacturerPart(),
										productCatalogId, supplierId, sku);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						productCatalogImpl
								.setSupplierCatalog(supplierCatalogueImpl);

						productCatalogImplList.add(productCatalogImpl);

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
	public MouserApiServiceImpl() {
	}

	// ////////////////////////////////////////
	@Override
	public long saveMaincategory(MainCatagory mainCatagory) {
		// TODO Auto-generated method stub
		return mouserApiDao.saveMaincategory(mainCatagory);
	}

	@Override
	public long getMainCategoryId(String manufacturerName) {
		// TODO Auto-generated method stub
		return mouserApiDao.getMainCategoryId(manufacturerName);
	}

	@Override
	public long saveMainSubcategory(MainSubCatagory mainCatagory) {
		// TODO Auto-generated method stub
		return mouserApiDao.saveMainSubcategory(mainCatagory);
	}

	@Override
	public long getMainSubCategoryId(String manufacturerName) {
		// TODO Auto-generated method stub
		return mouserApiDao.getMainSubCategoryId(manufacturerName);
	}

	@Override
	public long saveCategory(Category category) {
		// TODO Auto-generated method stub
		return mouserApiDao.saveCategory(category);
	}

	@Override
	public long getCategoryId(String manufacturerName) {
		// TODO Auto-generated method stub
		return mouserApiDao.getCategoryId(manufacturerName);
	}

	@Override
	public long getProductCatalogId(String manufacturerPart) {
		// TODO Auto-generated method stub
		return mouserApiDao.getProductCatalogId(manufacturerPart);
	}

	@Override
	public long updateProductCataLog(ProductCatalog productCatalogImpl) {
		// TODO Auto-generated method stub
		return mouserApiDao.updateProductCataLog(productCatalogImpl);
	}

	@Override
	public long saveProductCataLog(ProductCatalog productCatalogImpl) {
		// TODO Auto-generated method stub
		return mouserApiDao.saveProductCataLog(productCatalogImpl);
	}

	@Override
	public long saveSupplierCataLog(SupplierCatalog productCatalogImpl) {
		// TODO Auto-generated method stub
		return mouserApiDao.saveSupplierCataLog(productCatalogImpl);
	}

	@Override
	public long saveManufacturer(ManufacturerConfig manufacturersImpl) {
		// TODO Auto-generated method stub
		return mouserApiDao.saveManufacturer(manufacturersImpl);
	}

	@Override
	public long getManufacturerId(String manufacturerName) {
		// TODO Auto-generated method stub
		return mouserApiDao.getManufacturerId(manufacturerName);
	}

	@Override
	public List<SubcategoryImpl> findVatDetailsOnSubCategoryName(
			String subCategoryName) {
		// TODO Auto-generated method stub
		return mouserApiDao.findVatDetailsOnSubCategoryName(subCategoryName);
	}

	public ArrayList<PriceTag> calculatePrice(Category category,
			String priceBreaks, double currencyINR,
			String manuFacturerPartNumber, long productCatalogId,
			long supplierId, String sku) {
		ArrayList<PriceTag> priceTagImplsList = new ArrayList<PriceTag>();
		double cur_markup = category.getCurrencyMarkup();
		double dty = category.getDuty();
		double hfin = category.getHfi();
		double vt = category.getVat();
		hfin = hfin / 100;
		dty = dty / 100;
		vt = vt / 100;

		// Splitting the whole string in to two parts
		String[] temp = priceBreaks.split("USD");
		System.out.println("priceBreaks" + priceBreaks);
		System.out.println("temp.length" + temp.length);
		PriceTag priceTagImpl = null;
		if (temp.length != 1) {
			for (int j1 = 0; j1 < temp.length; j1++) {
				priceTagImpl = new PriceTag();
				int rup = j1;
				String semi = temp[j1];
				try {
					System.out.println("Semi" + semi);

					String[] sem = semi.split(java.util.regex.Pattern
							.quote("$"));
					String ss = "";
					System.out.println(sem[1]);
					String[] semi1 = sem[1].split(java.util.regex.Pattern
							.quote(","));
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

						priceTagImpl.setSalePrice(Float.parseFloat(ss));
						priceTagImpl.setRetailPrice(Float.parseFloat(ss));
						priceTagImpl.setTax(type);

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				priceTagImpl.setSku(sku);
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
		}
		return priceTagImplsList;
	}

	@Override
	public long getSupplierId(String supplierName) {
		// TODO Auto-generated method stub
		return mouserApiDao.getSupplierId(supplierName);
	}

	@Override
	public long getSupplierCatalogId(long supplierId, long productCatalogId) {
		// TODO Auto-generated method stub
		return mouserApiDao.getSupplierCatalogId(supplierId, productCatalogId);
	}

	@Override
	public long insertSupplierCatalog(SupplierCatalog supplierCatalog) {
		// TODO Auto-generated method stub
		return mouserApiDao.insertSupplierCatalog(supplierCatalog);
	}

	@Override
	public long updateSupplierCatalog(SupplierCatalog supplierCatalog) {
		// TODO Auto-generated method stub
		return mouserApiDao.updateSupplierCatalog(supplierCatalog);
	}

	@Override
	public long insertSupplier(SupplierConfig supplierConfig) {
		// TODO Auto-generated method stub
		return mouserApiDao.insertSupplier(supplierConfig);

	}

	@Override
	public List<SupplierPriceConfig> getSupplierPriceConfigDetails(
			long supplierId, long catgeoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Keyword> getKeywordList() {
		// TODO Auto-generated method stub
		return mouserApiDao.getKeywordList();
	}

}
