package com.walkersoft.appmanager.manager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
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

/**
 * 支付包订单生成
 * @author a
 *
 */
public class SwiftManager {

	static SwiftManager manager = null;

	public static SwiftManager getInstance() {
		if(manager == null)
			manager = new SwiftManager();
		return manager;
	}
	
	public Map<String, String> queryPrepayId(HttpServletRequest req, AppEntity app, OrderEntity order) {
		
		//SortedMap<String,String> map = XmlUtils.getParameterMap(req);
		SortedMap<String,String> map = new TreeMap<String,String>();
        map.put("mch_id", SwiftpassConfig.mch_id);
        
        map.put("notify_url", SwiftpassConfig.notify_url);
        map.put("nonce_str", String.valueOf(new Date().getTime()));
        
        map.put("body", "SPay收款"); // 商品名称
        map.put("service", "unified.trade.pay"); // 支付类型
        map.put("version", "1.0"); // 版本
        map.put("out_trade_no", order.getOrderid()); //订单号
        map.put("mch_create_ip", "127.0.0.1"); // 机器ip地址
        map.put("total_fee", ""+order.getTotalFee()); // 总金额 分
        map.put("limit_credit_pay", "0"); // 是否限制信用卡支付， 0：不限制（默认），1：限制
        
        Map<String,String> params = SignUtils.paraFilter(map);
        StringBuilder buf = new StringBuilder((params.size() +1) * 10);
        SignUtils.buildPayParams(buf,params,false);
        String preStr = buf.toString();
        String sign = MD5.sign(preStr, "&key=" + SwiftpassConfig.key, "utf-8");
        map.put("sign", sign.toUpperCase());
        
        String reqUrl = SwiftpassConfig.req_url;
        System.out.println("reqUrl：" + reqUrl);
        
        System.out.println("reqParams:" + XmlUtils.parseXML(map));
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        String res = null;
        Map<String,String> resultMap = new HashMap<String,String>();
        try {
            HttpPost httpPost = new HttpPost(reqUrl);
            StringEntity entityParams = new StringEntity(XmlUtils.parseXML(map),"utf-8");
            httpPost.setEntity(entityParams);
            //httpPost.setHeader("Content-Type", "text/xml;charset=ISO-8859-1");
            client = HttpClients.createDefault();
            response = client.execute(httpPost);
            if(response != null && response.getEntity() != null){
            	resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
                res = XmlUtils.toXml(resultMap);
                System.out.println("请求结果：" + res);
                
                if(resultMap.containsKey("sign")){
                    if(!SignUtils.checkParam(resultMap, SwiftpassConfig.key)){
                        res = "验证签名不通过";
                        resultMap.put("code", "-100");
                        resultMap.put("msg", res);
                    }else{
                        if("0".equals(resultMap.get("status"))){
                            
                            
                            String token_id = resultMap.get("token_id");
                            String services = resultMap.get("services");
                            
                            resultMap.put("code", "200");
                            resultMap.put("msg", "success");
                        }else{
                            
                            resultMap.put("code", "-100");
                            resultMap.put("msg", resultMap.get("message"));
                        }
                        
                    }
                } 
                else
                {
                	resultMap.put("code", "-100");
                    resultMap.put("msg", "操作失败");
                }
            }else{
                resultMap.put("code", "-100");
                resultMap.put("msg", "操作失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code", "-100");
            resultMap.put("msg", "系统异常");
        } 
        resultMap.put("orderId", order.getOrderid());
        
//        TreeMap<String, String> payMap = new TreeMap<String, String>();
//		
//		payMap.put("appid", appid);
//		payMap.put("partnerid", mch_id);
//		payMap.put("timestamp", create_timestamp());
//		payMap.put("noncestr", create_nonce_str());
//		//payMap.put("package", "prepay_id=" + prepay_id);
//		payMap.put("package", "Sign=WXPay");
//		payMap.put("prepayid", prepay_id);
//		String paySign = getSign(payMap, paternerKey);
//		
//		payMap.put("orderId", order.getOrderid());
//		payMap.put("sign_method", "sha1");
//		payMap.put("code", "200");
//		payMap.put("msg", "success");
//		payMap.put("sign", paySign);
        
//		String paySign = getSign(resultMap, paternerKey);
//		resultMap.put("sign", paySign);
        
        return resultMap;
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
