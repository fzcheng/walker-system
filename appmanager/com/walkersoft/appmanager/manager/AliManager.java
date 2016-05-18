package com.walkersoft.appmanager.manager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import com.walkersoft.appmanager.util.ali.SignUtils;

public class AliManager {

	static AliManager manager = null;
	
	// 商户PID
	public static final String PARTNER = "2088221378971981";
	// 商户收款账号
	public static final String SELLER = "2088221378971981";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALmqRhGrqUWN0YTIgh8ik85RC2Vx5EWmpIS7++3v1X45QYvms0S7xLNT/7ALgt3XiB2NYamKYLwNUzsjwkZZqSwb3LEXFFXv3zEvlRfcV9IWPqMffG8dKw6X6QO5JRAc3hL6OJOP1RHSqeFNz+FfyCHfs9nYNoDyztyUd2JWpnVrAgMBAAECgYBlXe61lRhs7hn3OhW49ALowNzM/RqZYHswMQZCCRBxwsbjoAg+PZoOIo1Vy2MexZ7+K4OnsfJQmaHYhxR5nlg621spyiCDZ0e3osGQaUWhtoeMHnqPWbQzROn3QFqQJmuO+Zrvuv8QNd3TPgwXm32HGFRmUk1WK6bya5OrHvq5MQJBAOduVOTys6OQ5FlvRR+Yly0THErUh+9shrVIojH2/p/c9HI9mZ1uO4lr6V+6rgu+DglEWp+vAyGI9h2TGlz+2LkCQQDNYCZl5nCcULY8Nt8aB2CoU2FpaQIDitMO5c7xDGUec2eSWRuEVEFG6qM903OuGAKyNxl2a1d9e/l2oFmdKSVDAkAJxWfCLtkUy9ZITGFfvyKK3aaxJh4DJtLdLo7iiyoe98Y+WIl20yOiXrMAvrIAfuq6y28EFPCN5ul1QDO5v2zJAkA1B3Ciux+0nPqRDbIJrIH+tJjgeOa2N3BL1nbastKhTDcgCLYcMw0v3IIOAr1J5JU9oxCGDS1oD0zYnorFE8y1AkEAyb92qHoD6vbMXDjunw2YDdOzwY/Z9w3VYBy9v2Rs2BJqMkF2saw8r4TdC56I1342Qtr/neniKBl6zeY2Na4+ZQ==";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	private static final int SDK_PAY_FLAG = 1;

	public static AliManager getInstance() {
		if(manager == null)
			manager = new AliManager();
		return manager;
	}
	
	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
				+ "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}
}
