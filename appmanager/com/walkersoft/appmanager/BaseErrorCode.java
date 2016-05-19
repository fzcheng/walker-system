package com.walkersoft.appmanager;


public enum BaseErrorCode {
	Success(0, "正常处理"),
	Error_ForceOffline(-99, "异地登录"),
	Error_Exception(-100, "异常错误"), 
	Error_CMD(-101, "cmd错误"),
	Error_APPID(-102, "app错误"),
	Error_MONEY(-103, "金额错误"),
	Error_SIGNError(-104, "签名异常"),
	Error_OrderId(-105, "订单号错误"),
	Error_NoMethod(-106, "无相关处理"),
	Error_TOBIG(-107, "数据请求过大"),
	Error_Duplicate(-108, "重复收到通知，结果确不一样"),
	Error_TId(-109, "tid错误"),
	Error_OrderNoExist(-110, "订单不存在"),
	Error_CardType(-111, "card类别错误"),
	Error_CONNETCT(-112, "请求失败，稍后再试"),;
	
	private int code;
	private String name;
	private String msg;

	private BaseErrorCode(int code, String name, String msg) {
		this.code = code;
		this.name = name;
		this.msg = msg;
	}
	
	private BaseErrorCode(int code, String msg) {
		this.code = code;
		this.name = msg;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
