package com.mycompany.persistance.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="semikart_order")
public class SemikartOrder {
	@Id
	@GeneratedValue
	@Column(name="ORDER_ID")
	private long orderId;
	@Column(name = "order_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;
	
	@Column(name = "delivery_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date delivaryDate;
	
	
	@Column(name = "DATE_UPDATED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdated;
	@Column(name = "EMAIL_ADDRESS")
	private String emailAddress;
	@Column(name = "ORDER_NUMBER")
	private String orderNumber;
	@Column(name = "ORDER_STATUS")
	private String orderStatus;
	@Column(name = "ORDER_SUBTOTAL")
	private float orderSubtotal;
	@Column(name = "ORDER_TOTAL")
	private float orderTotal;
	@Column(name = "TOTAL_SHIPPING")
	private float totalShipping;
	@Column(name = "TOTAL_TAX")
	private float totalTax;
	@Column(name = "CUSTOMER_ID")
	private long customerId;
	@Column(name = "CUSTOMER_ADDRESS_ID")
	private long customerAddressId;
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Date getDelivaryDate() {
		return delivaryDate;
	}
	public void setDelivaryDate(Date delivaryDate) {
		this.delivaryDate = delivaryDate;
	}
	public Date getDateUpdated() {
		return dateUpdated;
	}
	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public float getOrderSubtotal() {
		return orderSubtotal;
	}
	public void setOrderSubtotal(float orderSubtotal) {
		this.orderSubtotal = orderSubtotal;
	}
	public float getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(float orderTotal) {
		this.orderTotal = orderTotal;
	}
	public float getTotalShipping() {
		return totalShipping;
	}
	public void setTotalShipping(float totalShipping) {
		this.totalShipping = totalShipping;
	}
	public float getTotalTax() {
		return totalTax;
	}
	public void setTotalTax(float totalTax) {
		this.totalTax = totalTax;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public long getCustomerAddressId() {
		return customerAddressId;
	}
	public void setCustomerAddressId(long customerAddressId) {
		this.customerAddressId = customerAddressId;
	}

  
	
}
