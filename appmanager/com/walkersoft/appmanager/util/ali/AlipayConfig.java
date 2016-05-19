package com.walkersoft.appmanager.util.ali;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 商户PID
	public static final String PARTNER = "2088221378971981";
	// 商户收款账号
	public static final String SELLER = "2088221378971981";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALmqRhGrqUWN0YTIgh8ik85RC2Vx5EWmpIS7++3v1X45QYvms0S7xLNT/7ALgt3XiB2NYamKYLwNUzsjwkZZqSwb3LEXFFXv3zEvlRfcV9IWPqMffG8dKw6X6QO5JRAc3hL6OJOP1RHSqeFNz+FfyCHfs9nYNoDyztyUd2JWpnVrAgMBAAECgYBlXe61lRhs7hn3OhW49ALowNzM/RqZYHswMQZCCRBxwsbjoAg+PZoOIo1Vy2MexZ7+K4OnsfJQmaHYhxR5nlg621spyiCDZ0e3osGQaUWhtoeMHnqPWbQzROn3QFqQJmuO+Zrvuv8QNd3TPgwXm32HGFRmUk1WK6bya5OrHvq5MQJBAOduVOTys6OQ5FlvRR+Yly0THErUh+9shrVIojH2/p/c9HI9mZ1uO4lr6V+6rgu+DglEWp+vAyGI9h2TGlz+2LkCQQDNYCZl5nCcULY8Nt8aB2CoU2FpaQIDitMO5c7xDGUec2eSWRuEVEFG6qM903OuGAKyNxl2a1d9e/l2oFmdKSVDAkAJxWfCLtkUy9ZITGFfvyKK3aaxJh4DJtLdLo7iiyoe98Y+WIl20yOiXrMAvrIAfuq6y28EFPCN5ul1QDO5v2zJAkA1B3Ciux+0nPqRDbIJrIH+tJjgeOa2N3BL1nbastKhTDcgCLYcMw0v3IIOAr1J5JU9oxCGDS1oD0zYnorFE8y1AkEAyb92qHoD6vbMXDjunw2YDdOzwY/Z9w3VYBy9v2Rs2BJqMkF2saw8r4TdC56I1342Qtr/neniKBl6zeY2Na4+ZQ==";
	// 支付宝公钥
	public static final String ali_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	private static final int SDK_PAY_FLAG = 1;
	// 支付宝的公钥，无需修改该值
	//public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";

}
