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
//			支付宝=802
//			银联=803（暂未开通）
//			移动卡=804
//			电信卡=805
//			联通卡=806
//			其他=807
	public static final int PAYCHANNEL_ALI = 802;
}
