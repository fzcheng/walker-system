package com.walkersoft.appmanager.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.walkersoft.appmanager.entity.AppEntity;
import com.walkersoft.appmanager.manager.AliManager;
import com.walkersoft.appmanager.manager.AppManagerImpl;
import com.walkersoft.appmanager.response.AliParam;
import com.walkersoft.appmanager.response.QueryAliPayParamResult;
import com.walkersoft.appmanager.response.QueryOrderResult;
import com.walkersoft.appmanager.response.QuerySdkIdResult;
import com.walkersoft.appmanager.response.QueryWxPayParamResult;
import com.walkersoft.appmanager.util.JacksonUtil;
import com.walkersoft.appmanager.util.ali.Base64;
import com.walkersoft.system.SystemAction;

/**
 * SDK
 * @author a
 *
 */

@Controller
public class SDKAction extends SystemAction {
	
	@Autowired
	private AppManagerImpl appManager;
	
	
	/**
	 * 获取支付宝支付参数 
	 * @param model
	 * @return code payinfo
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("sdk/pay/wsMobilePay")
	@ResponseBody
	public String wsMobilePay(Model model) throws UnsupportedEncodingException{
		
		/**
		 * 完整的符合支付宝参数规范的订单信息
		 */
		String orderInfo = AliManager.getInstance().getOrderInfo("测试的商品", "该测试商品的详细描述", "0.01");
		/**
		 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
		 */
		String sign = AliManager.getInstance().sign(orderInfo);
		try {
			/**
			 * 仅需对sign 做URL编码
			 */
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + AliManager.getInstance().getSignType();
		
		QueryAliPayParamResult r = new QueryAliPayParamResult();
		r.setCode(200);
		r.setMsg("");
		r.setOrderId("123123123");
		
		String payInfo_base64 = Base64.encode(payInfo.getBytes("utf-8"));
		r.setPayinfo(payInfo_base64);
		
		System.out.println("payInfo       :" + payInfo);
		System.out.println("payInfo_base64:" + payInfo_base64);
		return JacksonUtil.getJsonString4JavaPOJO(r);
	}
	
	
	
	/**
	 * 获取sdk开关
	 * @param appid market
	 * @return code sdkId
	 */
	@RequestMapping(value = "sdk/pay/getSdkId")
	@ResponseBody
	public String getSdkId(Model model){
		String appid = this.getParameter("appid");
		String market = this.getParameter("market");
		
		QuerySdkIdResult r = appManager.queryAppSdkId(appid, market);
		
		return JacksonUtil.getJsonString4JavaPOJO(r);
	}
	
	/**
	 * 获取订单结果
	 * @param orderId
	 * @return payresult
	 */
	@RequestMapping("sdk/pay/getOrderResult")
	@ResponseBody
	public String getOrderResult(Model model){
		String orderId = this.getParameter("orderId");
		
		QueryOrderResult r = new QueryOrderResult();
		return ""+r.getPayresult();
	}
	
	/**
	 * 获取微信支付参数
	 * @param entity
	 * @param response
	 * @return code msg appid orderId partnerid prepayid noncestr timestamp package sign 
	 * @throws IOException
	 */
	@RequestMapping("sdk/pay/wxapp")
	@ResponseBody
	public String wxapp(AppEntity entity, HttpServletResponse response) throws IOException{
		QueryWxPayParamResult r = new QueryWxPayParamResult();
		return JacksonUtil.getJsonString4JavaPOJO(r);
	}
	
}
