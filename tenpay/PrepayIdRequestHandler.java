package com.walkersoft.appmanager.util.tenpay;

import java.util.Dictionary;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.walkersoft.appmanager.util.tenpay.client.TenpayHttpClient;
import com.walkersoft.appmanager.util.tenpay.util.ConstantUtil;
import com.walkersoft.appmanager.util.tenpay.util.JsonUtil;
import com.walkersoft.appmanager.util.tenpay.util.Sha1Util;


public class PrepayIdRequestHandler extends RequestHandler {

	public PrepayIdRequestHandler(HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);
	}

	/**
	 * 创建签名SHA1
	 * 
	 * @param signParams
	 * @return
	 * @throws Exception
	 */
	public String createSHA1Sign() {
		StringBuffer sb = new StringBuffer();
		Set es = super.getAllParameters().entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			sb.append(k + "=" + v + "&");
		}
		String params = sb.substring(0, sb.lastIndexOf("&"));
		String appsign = Sha1Util.getSha1(params);
		this.setDebugInfo(this.getDebugInfo() + "\r\n" + "sha1 sb:" + params);
		this.setDebugInfo(this.getDebugInfo() + "\r\n" + "app sign:" + appsign);
		return appsign;
	}

	// 提交预支付
	public String sendPrepay() throws JSONException {
		Dictionary<String, String> requestParams = new Dictionary<String, String>();
        requestParams.Add("appid", AppID);
        requestParams.Add("attach", record.No);
        requestParams.Add("body", record.Memo);
        requestParams.Add("mch_id", mch_id);
        requestParams.Add("nonce_str", TextHelper.CreateRandString(32));
        requestParams.Add("notify_url", CallbackUrl + "?id=" + record.Id + "&userid=" + record.UsersId + "&amount=" + record.Amount);
        requestParams.Add("out_trade_no", record.No);
        requestParams.Add("spbill_create_ip", RemoteIpAddress);
        requestParams.Add("time_expire", DateTime.Now.AddMinutes(30).ToString("yyyyMMddHHmmss"));//订单失效时间30分钟
        requestParams.Add("total_fee", record.Amount.ToString());
        requestParams.Add("fee_type", "1");
        requestParams.Add("trade_type", "APP");
        requestParams.Add("sign", CreateSign(requestParams, true));

        String content = HttpHelper.Post("https://api.mch.weixin.qq.com/pay/unifiedorder", CreateXml(requestParams));
        
//		String prepayid = "";
//		StringBuffer sb = new StringBuffer("{");
//		Set es = super.getAllParameters().entrySet();
//		Iterator it = es.iterator();
//		while (it.hasNext()) {
//			Map.Entry entry = (Map.Entry) it.next();
//			String k = (String) entry.getKey();
//			String v = (String) entry.getValue();
//			if (null != v && !"".equals(v) && !"appkey".equals(k)) {
//				sb.append("\"" + k + "\":\"" + v + "\",");
//			}
//		}
//		String params = sb.substring(0, sb.lastIndexOf(","));
//		params += "}";
//
//		String requestUrl = super.getGateUrl();
//		this.setDebugInfo(this.getDebugInfo() + "\r\n" + "requestUrl:"
//				+ requestUrl);
//		TenpayHttpClient httpClient = new TenpayHttpClient();
//		httpClient.setReqContent(requestUrl);
//		String resContent = "";
//		this.setDebugInfo(this.getDebugInfo() + "\r\n" + "post data:" + params);
//		if (httpClient.callHttpPost(requestUrl, params)) {
//			resContent = httpClient.getResContent();
//			if (2 == resContent.indexOf("prepayid")) {
//				prepayid = JsonUtil.getJsonValue(resContent, "prepayid");
//			}
//			this.setDebugInfo(this.getDebugInfo() + "\r\n" + "resContent:"
//					+ resContent);
//		}
//		return prepayid;
	}

	// 判断access_token是否失效
	public String sendAccessToken() {
		String accesstoken = "";
		StringBuffer sb = new StringBuffer("{");
		Set es = super.getAllParameters().entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"appkey".equals(k)) {
				sb.append("\"" + k + "\":\"" + v + "\",");
			}
		}
		String params = sb.substring(0, sb.lastIndexOf(","));
		params += "}";

		String requestUrl = super.getGateUrl();
//		this.setDebugInfo(this.getDebugInfo() + "\r\n" + "requestUrl:"
//				+ requestUrl);
		TenpayHttpClient httpClient = new TenpayHttpClient();
		httpClient.setReqContent(requestUrl);
		String resContent = "";
//		this.setDebugInfo(this.getDebugInfo() + "\r\n" + "post data:" + params);
		if (httpClient.callHttpPost(requestUrl, params)) {
			resContent = httpClient.getResContent();
			if (2 == resContent.indexOf(ConstantUtil.ERRORCODE)) {
				accesstoken = resContent.substring(11, 16);//获取对应的errcode的值
			}
		}
		return accesstoken;
	}
}
