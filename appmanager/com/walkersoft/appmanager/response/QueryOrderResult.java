package com.walkersoft.appmanager.response;

public class QueryOrderResult {

	int payresult;//200支付成功 201创建订单 202待处理 203支付失败 -2支付取消 -1未知错误 0网络异常

	public int getPayresult() {
		return payresult;
	}

	public void setPayresult(int payresult) {
		this.payresult = payresult;
	}
}
