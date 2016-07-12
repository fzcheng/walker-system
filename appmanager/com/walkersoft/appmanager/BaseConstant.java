package com.walkersoft.appmanager;

public class BaseConstant {

	//订单状态
	final public static int STATUS_CREATE = 201;//创建
	final public static int STATUS_ING = 202;//扫码了二维码
	final public static int STATUS_SUCCESS = 200;//成功
	final public static int STATUS_FAIL = 203;//失败

	//通知状态
	public static final int TRANSFER_STATUS_ING = 0;
	public static final int TRANSFER_STATUS_SUCCESS = 1;
	public static final int TRANSFER_STATUS_FAIL = 2;
	
	//	微信=801
	//	支付宝=802
	//	威富通=803
	public static final int PAYCHANNEL_TEN = 801;
	public static final int PAYCHANNEL_ALI = 802;
	public static final int PAYCHANNEL_SWIFT = 803;
	
	//SDK 
	public static final int YEE_SDK = 1;//YEESDK
	public static final int M_SDK = 2;//内置sdk
}
