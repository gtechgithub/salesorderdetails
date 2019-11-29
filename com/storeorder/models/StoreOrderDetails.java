package com.storeorder.models;

import java.math.BigDecimal;
import java.sql.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class StoreOrderDetails {

	private int rowId;
	private String orderId;
	private Date orderDate;
	private Date shipDate;
	private String shipMode;
	private int quantity;
	private BigDecimal discount;
	private BigDecimal profit;
	private String productId;
	private String customerName;
	private String categoty;
	private String customerId;
	private String productName;
	private String segment;
	private String country;
	private String city;
	private String state;
	private String postalCode;
	private String region;
	private String subCategory;
	private BigDecimal sales;

	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
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
	public String getCategoty() {
		return categoty;
	}
	public void setCategoty(String categoty) {
		this.categoty = categoty;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSegment() {
		return segment;
	}
	public void setSegment(String segment) {
		this.segment = segment;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public BigDecimal getSales() {
		return sales;
	}
	public void setSales(BigDecimal sales) {
		this.sales = sales;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this);
	}
}
