package com.kd.manage.entity;

import java.math.BigDecimal;
import java.util.Date;

public class GoodsDetail extends BaseEntity {

	//商品编号
	private String ginfoId;


	//商品名称
	private String goodsName;
	//订单号id
	private String tradeorderId;
	//商品总价
	private Double amount;
	//购买份数
	private Integer num;
	//单价
	private Double price;
	//商品类型
	private String typeCode;
	//商家
	private String storeCode;
	//创建时间
	private Date create_time;
	//更新时间
	private Date update_time;
	//所在用户
	private String userID;
	//商品对象
	private GoodsInfo goodsInfo;

	public String getGinfoId() {
		return ginfoId;
	}

	public void setGinfoId(String ginfoId) {
		this.ginfoId = ginfoId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getTradeorderId() {
		return tradeorderId;
	}

	public void setTradeorderId(String tradeorderId) {
		this.tradeorderId = tradeorderId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public GoodsInfo getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(GoodsInfo goodsInfo) {
		this.goodsInfo = goodsInfo;
	}
}
