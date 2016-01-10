package com.mycompany.persistance.domain;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="semikart_order_item")
public class SemikartOrderItem {
	@Id
	@GeneratedValue
	@Column(name="order_item_id")
	private long orderItemId;
	@Column(name = "ORDER_ID")
		private long orderId;
	@Column(name = "manufacturer_part")
	private String manufacturerPart;
	@Column(name = "supplier_id")
	private String supplierId;
	@Column(name = "quantity")
	private int quantityOrdered;
	@Column(name = "price")
	private float price;
	@Column(name = "TOTAL_PRICE")
	private float totalPrice;
	@Column(name = "vat")
	private float vat;
	public long getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(long orderItemId) {
		this.orderItemId = orderItemId;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getManufacturerPart() {
		return manufacturerPart;
	}
	public void setManufacturerPart(String manufacturerPart) {
		this.manufacturerPart = manufacturerPart;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public float getVat() {
		return vat;
	}
	public void setVat(float vat) {
		this.vat = vat;
	}
	public int getQuantityOrdered() {
		return quantityOrdered;
	}
	public void setQuantityOrdered(int quantityOrdered) {
		this.quantityOrdered = quantityOrdered;
	}

}
