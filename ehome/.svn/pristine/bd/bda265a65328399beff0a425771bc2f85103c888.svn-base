package com.ehome.cloud.puhui.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class PhOrder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5735028291660703746L;

	private Integer id;// 主键

	private String orderNum;// 订单号

	private String payTransactionNo;// 支付交易号

	private Date payTime;// 支付时间

	private Integer payWay;// 支付方式。0微信，1支付宝

	private BigDecimal originalPrice;// 原价

	private Float discount;// 折扣

	private BigDecimal payMoney;// 支付金额

	private BigDecimal handlingCharge;// 手续费

	private BigDecimal discountMoney;// 优惠金额

	private Integer discountType;// 折扣类型，0普通折扣，1工会会员折扣

	private String customerName;// 买单用户名

	private Integer customerId;// 用户ID

	private String merchantName;// 商家名称

	private Integer merchantId;// 商家ID

	private String merchantAddr;// 商家所在区域

	private Integer province;// 商家所在省份

	private Integer city;// 商家所在城市

	private Integer county;// 商家所在区域

	private String remarks;// 备注

	private Integer issettle;// 是否结算。0未结算，1已结算

	private Integer settleUserId;// 结算人员ID

	private String settleUserName;// 结算人员名称

	private Date settleTime;// 结算时间

	private String settleSemarks;// 结算备注

	private Date exportTime;// 导出时间

	// 用来查询支付时间的区间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endDate;

	private String imgUrl;// 图片路径

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum == null ? null : orderNum.trim();
	}

	public String getPayTransactionNo() {
		return payTransactionNo;
	}

	public void setPayTransactionNo(String payTransactionNo) {
		this.payTransactionNo = payTransactionNo == null ? null
				: payTransactionNo.trim();
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public BigDecimal getHandlingCharge() {
		return handlingCharge;
	}

	public void setHandlingCharge(BigDecimal handlingCharge) {
		this.handlingCharge = handlingCharge;
	}

	public BigDecimal getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(BigDecimal discountMoney) {
		this.discountMoney = discountMoney;
	}

	public Integer getDiscountType() {
		return discountType;
	}

	public void setDiscountType(Integer discountType) {
		this.discountType = discountType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName == null ? null : customerName.trim();
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName == null ? null : merchantName.trim();
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantAddr() {
		return merchantAddr;
	}

	public void setMerchantAddr(String merchantAddr) {
		this.merchantAddr = merchantAddr == null ? null : merchantAddr.trim();
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getCounty() {
		return county;
	}

	public void setCounty(Integer county) {
		this.county = county;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks == null ? null : remarks.trim();
	}

	public Integer getIssettle() {
		return issettle;
	}

	public void setIssettle(Integer issettle) {
		this.issettle = issettle;
	}

	public Integer getSettleUserId() {
		return settleUserId;
	}

	public void setSettleUserId(Integer settleUserId) {
		this.settleUserId = settleUserId;
	}

	public String getSettleUserName() {
		return settleUserName;
	}

	public void setSettleUserName(String settleUserName) {
		this.settleUserName = settleUserName == null ? null : settleUserName
				.trim();
	}

	public Date getSettleTime() {
		return settleTime;
	}

	public void setSettleTime(Date settleTime) {
		this.settleTime = settleTime;
	}

	public String getSettleSemarks() {
		return settleSemarks;
	}

	public void setSettleSemarks(String settleSemarks) {
		this.settleSemarks = settleSemarks;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getExportTime() {
		return exportTime;
	}

	public void setExportTime(Date exportTime) {
		this.exportTime = exportTime;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

}