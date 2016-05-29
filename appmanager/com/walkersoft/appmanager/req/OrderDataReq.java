package com.walkersoft.appmanager.req;



public class OrderDataReq {

	String appid;//必填，appid
	int market;//必填，渠道编号，默认400
	String userId;//必填,用户编号
	String nickname;//必填，昵称
	String waresId;//必填，商品编号
	String wares;//必填，商品名称
	String cpOrderId;//可选，CP订单编号，不重复
	String ext;//可选，透传参数，原样返回
	String notify_url;
	int totalFee;//必填，支付金额，单位分
	String wxappid;//必填，微信APPID，不填则不启用微信支付
	
	public OrderDataReq() {
		
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public int getMarket() {
		return market;
	}

	public void setMarket(int market) {
		this.market = market;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getWaresId() {
		return waresId;
	}

	public void setWaresId(String waresId) {
		this.waresId = waresId;
	}

	public String getWares() {
		return wares;
	}

	public void setWares(String wares) {
		this.wares = wares;
	}

	public String getCpOrderId() {
		return cpOrderId;
	}

	public void setCpOrderId(String cpOrderId) {
		this.cpOrderId = cpOrderId;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public String getWxappid() {
		return wxappid;
	}

	public void setWxappid(String wxappid) {
		this.wxappid = wxappid;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
}
