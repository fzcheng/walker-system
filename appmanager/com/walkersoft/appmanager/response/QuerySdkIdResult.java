package com.walkersoft.appmanager.response;

public class QuerySdkIdResult {

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getSdkId() {
		return sdkId;
	}
	public void setSdkId(int sdkId) {
		this.sdkId = sdkId;
	}
	
	int code;//200成功 2011参数错误
	int sdkId;//1-自有支付 2-内置支付
}
