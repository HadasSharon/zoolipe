package com.admin.service;

import java.io.IOException;
import java.util.Date;

public interface MyEmailWebService {
	public void sendOrderConfirmation(Date orderDate, String orderId, String emailAddress) throws IOException;
}
