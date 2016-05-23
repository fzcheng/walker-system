package com.walkersoft.appmanager.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.walkersoft.appmanager.BaseConstant;
import com.walkersoft.appmanager.BaseErrorCode;
import com.walkersoft.appmanager.entity.AliCallbackEntity;
import com.walkersoft.appmanager.entity.AppEntity;
import com.walkersoft.appmanager.entity.OrderEntity;
import com.walkersoft.appmanager.manager.AliCallbackRecordImpl;
import com.walkersoft.appmanager.manager.AliManager;
import com.walkersoft.appmanager.manager.AppManagerImpl;
import com.walkersoft.appmanager.manager.OrderManagerImpl;
import com.walkersoft.appmanager.req.OrderDataReq;
import com.walkersoft.appmanager.response.QueryAliPayParamResult;
import com.walkersoft.appmanager.response.QueryOrderResult;
import com.walkersoft.appmanager.response.QuerySdkIdResult;
import com.walkersoft.appmanager.response.QueryWxPayParamResult;
import com.walkersoft.appmanager.util.JacksonUtil;
import com.walkersoft.appmanager.util.ali.AlipayNotify;
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
	
	@Autowired
	private OrderManagerImpl orderManager;
	
	@Autowired
	private AliCallbackRecordImpl alicallbackManager;
	/**
	 * 获取支付宝支付参数 
	 * @param model
	 * @return code payinfo
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("sdk/pay/wsMobilePay")
	@ResponseBody
	public String wsMobilePay(Model model) throws UnsupportedEncodingException{
		
		OrderDataReq req = parseOrderData();

		OrderEntity order = new OrderEntity();
		BaseErrorCode code = orderManager.createOrder(req, BaseConstant.PAYCHANNEL_ALI, order);
		/**
		 * 完整的符合支付宝参数规范的订单信息
		 */
		String orderInfo = AliManager.getInstance().getOrderInfo(req.getWares(), req.getWares(), order.getOrderid(), req.getTotalFee());
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
		r.setOrderId(order.getOrderid());
		
		String payInfo_base64 = Base64.encode(payInfo.getBytes("utf-8"));
		r.setPayinfo(payInfo_base64);
		
		System.out.println("payInfo       :" + payInfo);
		System.out.println("payInfo_base64:" + payInfo_base64);
		return JacksonUtil.getJsonString4JavaPOJO(r);
	}
	
	public OrderDataReq parseOrderData()
	{
		String appid = this.getParameter("appid");//必填，appid
		String market = this.getParameter("market");//必填，渠道编号，默认400
		String userId = this.getParameter("userId");//必填,用户编号
		String nickname = this.getParameter("nickname");//必填，昵称
		String waresId = this.getParameter("waresId");//必填，商品编号
		String wares = this.getParameter("wares");//必填，商品名称
		String cpOrderId = this.getParameter("cpOrderId");//可选，CP订单编号，不重复
		String ext = this.getParameter("ext");//可选，透传参数，原样返回
		String totalFee = this.getParameter("totalFee");//必填，支付金额，单位分
		String wxappid = this.getParameter("wxappid");//必填，微信APPID，不填则不启用微信支付
		
		OrderDataReq req = new OrderDataReq();
		
		req.setAppid(appid);
		req.setMarket(Integer.valueOf(market));
		req.setUserId(userId);
		req.setNickname(nickname);
		req.setWaresId(waresId);
		req.setWares(wares);
		req.setCpOrderId(cpOrderId);
		req.setExt(ext);
		req.setTotalFee(Integer.valueOf(totalFee));
		req.setWxappid(wxappid);
		
		return req;
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
		String marketStr = this.getParameter("market");
		
		int market = Integer.valueOf(marketStr);
		
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
		logger.info("getOrderResult orderId" + orderId);
		OrderEntity order = orderManager.queryOrderByOrderId(orderId);
		
		QueryOrderResult r = new QueryOrderResult();
		if(order != null)
		{
			r.setPayresult(order.getStatus());
		}
		else
		{
			r.setPayresult(-1);
		}
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
	
	
	/**
	 * 支付宝支付回调
	 * @param entity
	 * @param response
	 * @return code msg appid orderId partnerid prepayid noncestr timestamp package sign 
	 * @throws IOException
	 */
	@RequestMapping("sdk/pay/alicallback")
	@ResponseBody
	public String alicallback(Model model) throws IOException{
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = this.getRequest().getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号	String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//支付宝交易号	String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//交易状态
		String trade_status = new String(getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

		if(AlipayNotify.verify(params)){//验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			//请在这里加上商户的业务逻辑程序代码
			AliCallbackEntity callbackentity = alicallbackManager.addRecord(this.getRequest());
			
			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			
			if(trade_status.equals("TRADE_FINISHED")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
				
				orderManager.dealTradeFinished_ali(callbackentity);
				
				//注意：
				//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
				//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
			} else if (trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
				orderManager.dealTradeFinished_ali(callbackentity);
				//注意：
				//付款完成后，支付宝系统发送该交易状态通知
				//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
			}

			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
				
			return "success";	//请不要修改或删除

			//////////////////////////////////////////////////////////////////////////////////////////
		}else{//验证失败
			return "fail";
		}
	}
}
