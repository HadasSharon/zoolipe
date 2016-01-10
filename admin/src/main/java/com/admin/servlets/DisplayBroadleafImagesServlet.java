/**
 * Copyright (c) 2014 by Sunplus System Solutions PVT Ltd.
 * www.sunplussolutions.com
 * All rights reserved.
 * This software is the confidential and proprietary information of
 * Sunplus System Solutions PVT Ltd. You shall not disclose such Confidential 
 * Information and shall use it only in accordance with the terms 
 * of the license agreement you entered into with Sunplus System Solutions PVT Ltd.
 * Created : <date>
 * 
 * @author <a href=mailto:<emailid of author>><Author name></a>
 * @version :SUN ERP+:2.0
 */
package com.admin.servlets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;

import com.admin.Util.DatabaseConstants;
import com.google.common.io.Files;

@WebServlet("/marketplaceImg/*")
public class DisplayBroadleafImagesServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println("Path" + request.getPathInfo());
			String requestedUrl = request.getPathInfo();
			String ext = Files.getFileExtension(requestedUrl);
			System.out.println(ext);
			if (StringUtils.isNotBlank(ext)) {
				response.setContentType("image/" + ext);

				String pathToWeb = getServletContext().getRealPath(
						File.separator)
						+ "/marketplaceImg/" + request.getPathInfo();
				System.out.println("pathToWeb" + pathToWeb);
				File f = new File(pathToWeb);

				//BufferedImage bi = ImageIO.read(f);
				OutputStream out = response.getOutputStream();
				//ImageIO.write(bi, ext, out);
				//out.close();

				InputStream is =new FileInputStream(f);
				byte[] bytesData = new byte[2048];
				int length = 0;
				while ((length = is.read(bytesData)) != -1) {
					System.out.print("length" + length);
					out.write(bytesData, 0, length);
				}
				is.close();
				out.close();
			} else {
				PrintWriter out = response.getWriter();
				out.print("Image Not Found");
			}

		} catch (Exception ex) {

			ex.printStackTrace();
		} finally {

		}

	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
}
