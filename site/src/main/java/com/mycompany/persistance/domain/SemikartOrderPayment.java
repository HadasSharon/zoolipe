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
@Table(name = "semikart_order_payment")
public class SemikartOrderPayment {
	@Id
	@GeneratedValue
	@Column(name = "PAYMENT_TRANSACTION_ID")
	private long paymentTransactionId;
	@Column(name = "PAYMENT_TRANSACTION_NO")
	private String paymentTransactionNumber;
	@Column(name = "TRANSACTION_AMOUNT")
	private float transactionAmount;
	@Column(name = "CUSTOMER_IP_ADDRESS")
	private float customerIpAddress;
	@Column(name = "TRANSACTION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date transactionDate;
	@Column(name = "RAW_RESPONSE")
	private String rawResponse;
	@Column(name = "PAYMENT_STATUS")
	private String paymentStatus;
	@Column(name = "TRANSACTION_TYPE")
	private String transactionType;
	@Column(name = "PAYMENT_TYPE")
	private String paymentType;
	@Column(name = "GATEWAY_TYPE")
	private String gatewayType;
	@Column(name = "ORDER_ID")
	private Long orderId;
	@Column(name = "bank_ref_num")
	private String bankRefNo;
	@Column(name = "bankcode")
	private String bankCode;
	@Column(name = "unique_ref_id")
	private String uniqueRefId;
	@Column(name = "unmapped_status")
	private String unmappedStatus;
	@Column(name = "customer_id")
	private long customerId;

	public float getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(float transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public float getCustomerIpAddress() {
		return customerIpAddress;
	}

	public void setCustomerIpAddress(float customerIpAddress) {
		this.customerIpAddress = customerIpAddress;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getRawResponse() {
		return rawResponse;
	}

	public void setRawResponse(String rawResponse) {
		this.rawResponse = rawResponse;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getGatewayType() {
		return gatewayType;
	}

	public void setGatewayType(String gatewayType) {
		this.gatewayType = gatewayType;
	}

	public String getBankRefNo() {
		return bankRefNo;
	}

	public void setBankRefNo(String bankRefNo) {
		this.bankRefNo = bankRefNo;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getUniqueRefId() {
		return uniqueRefId;
	}

	public void setUniqueRefId(String uniqueRefId) {
		this.uniqueRefId = uniqueRefId;
	}

	public String getUnmappedStatus() {
		return unmappedStatus;
	}

	public void setUnmappedStatus(String unmappedStatus) {
		this.unmappedStatus = unmappedStatus;
	}



	public long getPaymentTransactionId() {
		return paymentTransactionId;
	}

	public void setPaymentTransactionId(long paymentTransactionId) {
		this.paymentTransactionId = paymentTransactionId;
	}

	public String getPaymentTransactionNumber() {
		return paymentTransactionNumber;
	}

	public void setPaymentTransactionNumber(String paymentTransactionNumber) {
		this.paymentTransactionNumber = paymentTransactionNumber;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

}
