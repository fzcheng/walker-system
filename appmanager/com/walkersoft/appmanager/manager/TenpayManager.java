package com.walkersoft.appmanager.manager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.swiftpass.util.MD5;
import com.swiftpass.util.SignUtils;
import com.swiftpass.util.XmlUtils;
import com.walkersoft.appmanager.entity.AppEntity;
import com.walkersoft.appmanager.entity.OrderEntity;
import com.walkersoft.appmanager.util.DateTimeUtil;
import com.walkersoft.appmanager.util.swift.SwiftpassConfig;
import com.walkersoft.appmanager.util.tenpay.TenpayConfig;
import com.walkersoft.appmanager.util.tenpay.TenpayHttpClient;

/**
 * 支付包订单生成
 * @author a
 *
 */
public class TenpayManager {

	static TenpayManager manager = null;

	public static TenpayManager getInstance() {
		if(manager == null)
			manager = new TenpayManager();
		return manager;
	}

	public Map<String, String> queryPrepayId(HttpServletRequest request, AppEntity app, OrderEntity order) throws XmlPullParserException, IOException
	{
		//AppID：wxd9c0c13bacc6d9b0
		//AppSecret：3b0a6de73552bb92822c21f229f0e97e
		//应用签名：7ea0ec5ad7087525caa841b9aeee9c66
		//包名：com.magicbirds.master
		//微信支付商户号:1301475301
		//商户key=7ea0ec5ad7087525caa841b9aeee9c66
		String appid = TenpayConfig.appid;
		String paternerKey = TenpayConfig.paternerKey;
		String mch_id = TenpayConfig.mch_id;
		
		if(app != null)
		{
			appid = app.getWx_appid();
			paternerKey = app.getWx_parternerKey(); 
			mch_id = app.getWx_mch_id();
		}
		
		String out_trade_no = order.getOrderid();
		TreeMap<String, String> paraMap = new TreeMap<String, String>();
		paraMap.put("appid", appid);
		paraMap.put("attach", order.getWaresId());
		paraMap.put("body", order.getWares());
		paraMap.put("mch_id", mch_id);
		paraMap.put("nonce_str", create_nonce_str());

		paraMap.put("out_trade_no", out_trade_no);
		String addrip = request.getRemoteAddr();
		if(addrip.startsWith("0:0:0"))
			addrip = "127.0.0.1";
		paraMap.put("spbill_create_ip", addrip);
		paraMap.put("total_fee", ""+order.getTotalFee());
		paraMap.put("trade_type", "APP");
		paraMap.put("notify_url", TenpayConfig.notify_url);
		String sign = getSign(paraMap, paternerKey);
		paraMap.put("sign", sign);

		// 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
		String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

		String xml = ArrayToXml(paraMap);

		TenpayHttpClient httpClient = new TenpayHttpClient();
		httpClient.setReqContent(url);
		
		// 预付商品id
				String prepay_id = "";
		if (httpClient.callHttpPost(url, xml)) {
			String resContent = httpClient.getResContent();
			if (resContent.indexOf("prepay_id") != -1) {
				//prepayid = JsonUtil.getJsonValue(resContent, "prepayid");
				Map<String, String> map = doXMLParse(resContent);
				prepay_id = (String) map.get("prepay_id");
			}
			//this.setDebugInfo("resContent:" + resContent);
		}
	
		TreeMap<String, String> payMap = new TreeMap<String, String>();
		
		payMap.put("appid", appid);
		payMap.put("partnerid", mch_id);
		payMap.put("timestamp", create_timestamp());
		payMap.put("noncestr", create_nonce_str());
		//payMap.put("package", "prepay_id=" + prepay_id);
		payMap.put("package", "Sign=WXPay");
		payMap.put("prepayid", prepay_id);
		String paySign = getSign(payMap, paternerKey);
		
		payMap.put("orderId", order.getOrderid());
		payMap.put("sign_method", "sha1");
		payMap.put("code", "200");
		payMap.put("msg", "success");
		payMap.put("sign", paySign);
		
		return payMap;
	}

	/**
	 * map转成xml
	 * 
	 * @param arr
	 * @return
	 */
	public String ArrayToXml(Map<String, String> arr) {
		String xml = "<xml>";

		Iterator<Entry<String, String>> iter = arr.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String val = entry.getValue();
			xml += "<" + key + ">" + val + "</" + key + ">";
		}

		xml += "</xml>";
		return xml;
	}

	private String create_nonce_str() {
			String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
			String res = "";
			for (int i = 0; i < 16; i++) {
				Random rd = new Random();
				res += chars.charAt(rd.nextInt(chars.length() - 1));
			}
			return res;
	}
	
	private String getAddrIp(HttpServletRequest request){
		return request.getRemoteAddr();
	}

	private String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
	
	private String getTradeNo(){
		String timestamp = DateTimeUtil.formatDate(new Date(), "yyyyMMddHHmmssSSS");
		return "HZNO" + timestamp;
	}
	
	private String getSign(TreeMap<String, String> params, String paternerKey )
			throws UnsupportedEncodingException {
		String string1 = createSignOriStr(params, false);
		String stringSignTemp = string1 + "key=" + paternerKey;
		String signValue = DigestUtils.md5Hex(stringSignTemp).toUpperCase();
		return  signValue;
	}

	private String createSignOriStr(TreeMap<String, String> params, boolean b) {
		StringBuffer sb = new StringBuffer();
		Set es = params.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if(null != v && !"".equals(v) 
					&& !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}

		return sb.toString();
	}

	public Map<String, String> doXMLParse(String xml)
			throws XmlPullParserException, IOException {

		InputStream inputStream = new ByteArrayInputStream(xml.getBytes());

		Map<String, String> map = null;

		XmlPullParser pullParser = XmlPullParserFactory.newInstance()
				.newPullParser();

		pullParser.setInput(inputStream, "UTF-8"); // 为xml设置要解析的xml数据

		int eventType = pullParser.getEventType();

		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				map = new HashMap<String, String>();
				break;

			case XmlPullParser.START_TAG:
				String key = pullParser.getName();
				if (key.equals("xml"))
					break;

				String value = pullParser.nextText();
				map.put(key, value);

				break;

			case XmlPullParser.END_TAG:
				break;

			}

			eventType = pullParser.next();

		}

		return map;
	}
}
