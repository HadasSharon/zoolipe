package com.mycompany;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ResponseServlet
 */
@WebServlet("/ResponseServlet")
public class ResponseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResponseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("rawtypes")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("--------- Inside Response Servlet ---------");
		response.setContentType("text/html");
		
		// Create a session object if it is already not  created.
	    HttpSession session = request.getSession(true);
	    
		String FirstName = request.getParameter("firstname");
		String UniqueReferenceID = request.getParameter("mihpayid"); 
		String ModeofTransaction = request.getParameter("mode");
		String StatusofPayment = request.getParameter("status");
		String TransactionID = request.getParameter("txnid");
		String Amount = request.getParameter("amount");
		//String ProductInfo = request.getParameter("productinfo");
		String BankCode = request.getParameter("bankcode");
		String BankReferenceNumber = request.getParameter("bank_ref_num");
		String UnMappedStatus = request.getParameter("unmappedstatus");
		String EmailId = request.getParameter("email");
		
			
		String ResponseHash = request.getParameter("hash");
		String productinfo = request.getParameter("productinfo");		
		
		/*PaymentGateway pg = new PaymentGateway();
		String SALT = pg.getSALT();
		String key = pg.getKey();
		
		//SALT|status||||||udf5|udf4|udf3|udf2|udf1|email|firstname|productinfo|amount|txnid|key
		//SALT|status|||||||||||email|firstname|productinfo|amount|txnid|key
		String password1 = SALT+"|"+StatusofPayment+"|||||||||||"+EmailId+"|"+FirstName+"|"+productinfo+"|"+Amount+"|"+TransactionID+"|"+key; 
		java.security.MessageDigest md1 = null;
		try {
			md1 = java.security.MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e2) {
			e2.printStackTrace();
		}
		md1.update(password1.getBytes());	        
		byte byteData1[] = md1.digest();	 
		//convert the byte to hex format method 1
		StringBuffer sb1 = new StringBuffer();
		for (int i = 0; i < byteData1.length; i++) {
			sb1.append(Integer.toString((byteData1[i] & 0xff) + 0x100, 16).substring(1));
		}
		String hash = sb1.toString();
		System.out.println("Recheck Hex format : "+hash);
		System.out.println("Response Hex format : "+ResponseHash);
		
		if(hash.equals(ResponseHash)){
			System.out.println("HASH ------> EQUAL");

			double shipping_cost = (Double)session.getAttribute("ShippingCost");
			
			String ManufacturerPart = "";
			String Supplier = "";
			String Price = "";
			String Quantity = "";
			String SubTotal = "";
			String Vat = "";
			
			//System.out.println(FirstName);
			Key: <%=request.getParameter("key") %><br/>
			Hash: <%=request.getParameter("hash") %><br/>
			Error: <%=request.getParameter("error") %><br/>
			
		    Connection con = null;
			PreparedStatement ps = null;
			PreparedStatement ps1 = null;
			PreparedStatement ps2 = null;
			try {
				beans.Connect_db cont = new beans.Connect_db();
			  	con = cont.getConnection();
			  	
				ArrayList nmp = (ArrayList)session.getAttribute("ManuPart");//request.getParameter("udf1");
				Iterator itr=nmp.iterator();
				
				ps = con.prepareStatement("insert into "+cont.getDb()+".order(order_date, total_amt, emailid, transaction_id, payment_status, transaction_type, bank_ref_num, bankcode, unique_ref_id, unmapped_status, customer_id, shipping_cost) values (NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"); 
				
				ps.setString(1,Amount); 
				ps.setString(2,EmailId); 
				ps.setString(3,TransactionID);
				ps.setString(4,StatusofPayment); 
				ps.setString(5,ModeofTransaction); 
				ps.setString(6,BankReferenceNumber);
				ps.setString(7,BankCode); 
				ps.setString(8,UniqueReferenceID);
				ps.setString(9,UnMappedStatus);
				ps.setString(10,CustomerAddressId);
				ps.setString(11,String.valueOf(shipping_cost));
	        
				int i = ps.executeUpdate(); 
				//System.out.println(i); 
	
				if(i!=0)    { 
					System.out.println("Your data has been stored in the Order Table"); 
							
					ps1 = con.prepareStatement("insert into "+cont.getDb()+".order_item(transaction_id, manufacturer_part, supplier, quantity, price, total, customer_name, emailid, vat) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
					
					while(itr.hasNext()){  
						OrderItem obj = (OrderItem)itr.next();
						ManufacturerPart = obj.getManufacturerPartNumber();
						Supplier = obj.getSupplier();
						Price = obj.getPrice();
						Quantity = obj.getQuantity();	
						SubTotal = obj.getSubTotal();
						Vat = obj.getVat();
						
						ps1.setString(1,TransactionID); 
						ps1.setString(2,ManufacturerPart); 
						ps1.setString(3,Supplier);
						ps1.setString(4,Quantity); 
						ps1.setString(5,Price); 
						ps1.setString(6,SubTotal);
						ps1.setString(7,FirstName); 
						ps1.setString(8,EmailId);
						ps1.setString(9,Vat);
			        
						int i1 = ps1.executeUpdate();
						
						if(i1!=0)    { 
							System.out.println("Your data has been stored in the Order-Item Table"); 
							
							ps2 = con.prepareStatement("insert into "+cont.getDb()+".order_status_track (transaction_id, order_status, status_date) values (?, ?, NOW())");
							ps2.setString(1, TransactionID); 
							ps2.setString(2, "Order Placed"); 
							int i2 = ps1.executeUpdate();
							if(i2!=0)  { 
								System.out.println("Your data has been stored in the order_status_track Table"); 
							}
							
							//Confirmation Mail
							String result = null;
							beans.EmailDetails emailDetails = new beans.EmailDetails();
							
						    // Get recipient's email-ID, message & subject-line from index.html page
						    final String to = EmailId;
						    final String subject = "SemiKart Order Status";	
						    
						    // Sender's email ID and password needs to be mentioned
						    final String from = emailDetails.getFromEmail();
						    final String pass = emailDetails.getPassword();
						    final String port = emailDetails.getPort();
						    final String protocol = emailDetails.getProtocol();
	
						    // Defining the gmail host
						    String host = emailDetails.getHostName();
	
						    // Creating Properties object	    
						    Properties properties = new Properties();
						    properties.put("mail.transport.protocol", protocol);
						    properties.put("mail.smtp.host", host);
						    properties.put("mail.smtp.port", port);
						    properties.put("mail.smtp.auth", "true");
						    Authenticator authenticator = new Authenticator() {
						        protected PasswordAuthentication getPasswordAuthentication() {
						            return new PasswordAuthentication(from, pass);
						        }
						    };
						    
						    // Authorized the Session object.
						    Session mailSession1 = Session.getDefaultInstance(properties, authenticator); 
						    try {
						        // Create a default MimeMessage object.
						        MimeMessage message = new MimeMessage(mailSession1);
						        // Set From: header field of the header.
						        message.setFrom(new InternetAddress(from));
						        // Set To: header field of the header.
						        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
						        // Set Subject: header field
						        message.setSubject(subject);
						        // Now set the actual message
						        //message.setText(messg);
						        //final String messsgg = "<font color='#bb4c41'>Transaction of Payment is "+StatusofPayment+" and your Order ID is "+TransactionID+".</font>";
						        
						        String messsgg1 = "<font color='#bb4c41'>Dear Customer,<br><br>Thank you for placing an order with SEMIKART.<br><br>Your order is being processed. You will receive an e-mail confirmation when your order ships.<br><br>"
						        		+ "Your Salesorder Number is - "+TransactionID+"<br>Your Web ID is - "+CustomerId+"<br><br>"
						        		+ "All transactions with SEMIKART / AQT, including its subsidiaries and/or affiliates, are subject to SK/AQT Terms of Use and Conditions of Order, available at "
						        		+ "<a href='http://semikart.com/beta/'>Semikart</a>."
						        		+ "<br><br>Customer Service and Sales<br>"
						        		+ "Snehasri � Customer  Service and sales  - Semikart � snehasri@semikart.com</font>";
						        message.setContent("<h3>"+messsgg1+"</h3>","text/html");
						        // Send message
						        Transport.send(message);
						        result = "Your mail sent successfully....";
						    } catch (MessagingException mex) {
						        mex.printStackTrace();
						        result = "Error: unable to send mail....";
						    }finally{
						    	System.out.println(result);
						    }
						}
					}*/
					//session.invalidate();
					//session.removeAttribute("cart");
					//session.removeAttribute("CustomerAddressId");
					//response.sendRedirect("NoBack.jsp");
				//	RequestDispatcher dispatcher = request.getRequestDispatcher("/NoBack.jsp?txnid="+TransactionID);
					//dispatcher.forward(request, response);
				/*} 
				else    { 
					System.out.println("Your data could not be stored in the database"); 
					RequestDispatcher dispatcher = request.getRequestDispatcher("/ShoppingCart.jsp");
					dispatcher.forward(request, response);
				} 
			} catch (Exception e1) {
				e1.printStackTrace();
				RequestDispatcher dispatcher = request.getRequestDispatcher("/ShoppingCart.jsp");
				dispatcher.forward(request, response);
			} finally {
				if (ps1 != null) {
		        	try {
						ps1.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
		        }
		        if (ps2 != null) {
		        	try {
						ps2.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
		        }
		        if (ps != null) {
		        	try {
						ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
		        }
		        if (con != null) {
		        	try {
		        		con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
		        }
			}
		}else{			
			System.out.println("HASH ------> NOT-EQUAL");
			
			//Confirmation Mail
			String result = null;
			beans.EmailDetails emailDetails = new beans.EmailDetails();
			
		    // Get recipient's email-ID, message & subject-line from index.html page
		    final String to = EmailId;
		    final String subject = "SemiKart Order Failed";	
		    
		    // Sender's email ID and password needs to be mentioned
		    final String from = emailDetails.getFromEmail();
		    final String pass = emailDetails.getPassword();
		    final String port = emailDetails.getPort();
		    final String protocol = emailDetails.getProtocol();

		    // Defining the gmail host
		    String host = emailDetails.getHostName();

		    // Creating Properties object	    
		    Properties properties = new Properties();
		    properties.put("mail.transport.protocol", protocol);
		    properties.put("mail.smtp.host", host);
		    properties.put("mail.smtp.port", port);
		    properties.put("mail.smtp.auth", "true");
		    Authenticator authenticator = new Authenticator() {
		        protected PasswordAuthentication getPasswordAuthentication() {
		            return new PasswordAuthentication(from, pass);
		        }
		    };
		    
		    // Authorized the Session object.
		    Session mailSession1 = Session.getDefaultInstance(properties, authenticator); 
		    try {
		        // Create a default MimeMessage object.
		        MimeMessage message = new MimeMessage(mailSession1);
		        // Set From: header field of the header.
		        message.setFrom(new InternetAddress(from));
		        // Set To: header field of the header.
		        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		        // Set Subject: header field
		        message.setSubject(subject);
		        // Now set the actual message
		        //message.setText(messg);
		        final String messsgg = "<font color='#bb4c41'>Your Transaction of Payment is not successful and Please again once.</font>";
		        message.setContent("<h3>"+messsgg+"</h3>","text/html");
		        // Send message
		        Transport.send(message);
		        result = "Your mail sent successfully....";
		    } catch (MessagingException mex) {
		        mex.printStackTrace();
		        result = "Error: unable to send mail....";
		    }finally{
		    	System.out.println(result);
		    }
		    */
		   // RequestDispatcher dispatcher = request.getRequestDispatcher("/ShoppingCart.jsp");
			//dispatcher.forward(request, response);		
		//}
	}

}
