package com.storeorder.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@Table (name = "store_order",
uniqueConstraints = {
	      @UniqueConstraint(columnNames = "ORDER_ID"),
	      @UniqueConstraint(columnNames = "PRODUCT_ID"),
	      @UniqueConstraint(columnNames = "CUSTOMER_ID")
	   })

public class StoreOrder {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name = "ORDER_ID", nullable = false,length = 20)
	private String orderId;
	
	@Column(name = "ORDER_DATE",nullable = false)
	private Date orderDate ;

	@Column(name = "SHIP_DATE",nullable = false)
	private Date shipDate ;
	
	@Column(name = "SHIP_MODE")
	private String shipMode ;
	
	@Column(name = "QUANTITY",nullable = false)
	private int quantity ;
	
	@Column(name = "DISCOUNT", precision = 3, scale = 2)
	private BigDecimal discount ;
	
	@Column(name = "PROFIT", precision = 6, scale = 2, nullable = false)
	private BigDecimal profit ;
	
	@Column(name = "PRODUCT_ID",nullable = false)
	private String productId ;
	
	@Column(name = "CUSTOMER_NAME",nullable = false)
	private String customerName ;
	
	@Column(name = "CATEGORY",nullable = false)
	private String category ;
	
	@Column(name = "CUSTOMER_ID",nullable = false)
	private String customerID ;

	@Column(name = "PRODUCT_NAME")
	private String productName;
	
	public int getId() {
		return id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	public String getShipMode() {
		return shipMode;
	}

	public void setShipMode(String shipMode) {
		this.shipMode = shipMode;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this);
	}
}
