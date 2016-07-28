package com.walkersoft.appmanager.manager;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.db.page.support.GenericPager;
import com.walker.infrastructure.utils.Assert;
import com.walkersoft.appmanager.BaseConstant;
import com.walkersoft.appmanager.BaseErrorCode;
import com.walkersoft.appmanager.dao.OrderDao;
import com.walkersoft.appmanager.entity.AliCallbackEntity;
import com.walkersoft.appmanager.entity.AppEntity;
import com.walkersoft.appmanager.entity.OrderEntity;
import com.walkersoft.appmanager.entity.TenpayCallbackEntity;
import com.walkersoft.appmanager.req.OrderDataReq;
import com.walkersoft.appmanager.util.IPSeeker;
import com.walkersoft.appmanager.util.ProvinceUtil;
import com.walkersoft.system.pojo.AppGroup;

@Service("orderManager")
public class OrderManagerImpl {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private TransferService transferService;
	
	@Autowired
	private DailyManagerImpl dailyManager;
	
	@Autowired
	private DeviceManagerImpl deviceManager;
	
	@Autowired
	private AppManagerImpl appManager;
	
	public void execSave(OrderEntity entity) {
		Assert.notNull(entity);
		Date date = new Date();       
		Timestamp time = new Timestamp(date.getTime());
		
		entity.setCreate_time(time);
		entity.setLast_time(time);
		orderDao.save(entity);
	}
	
	public void execUpdate(OrderEntity entity) {
		Assert.notNull(entity);
		Date date = new Date();       
		Timestamp time = new Timestamp(date.getTime());
		
		entity.setLast_time(time);
		orderDao.updateEntity(entity);
	}
	
	public OrderEntity queryOrder(int id) {
		return orderDao.queryById(id);
	}
	
	public OrderEntity queryOrderByCpOrderId(String appid, String cporderid)
	{
		return orderDao.querySingleOrderByCpOrderid(appid, cporderid);
	}
	
	public OrderEntity queryOrderByPayOrderId(String payOrderid, int paychannel) {
		return orderDao.queryOrderByPayOrderId(payOrderid, paychannel);
	}

	public OrderEntity queryOrderByOrderId(String orderid) {
		return orderDao.queryOrderByOrderId(orderid);
	}
	
	public BaseErrorCode createOrder(HttpServletRequest request, OrderDataReq req, int paychannel, OrderEntity order) {
		
		AppEntity app = appManager.queryByAppid(req.getAppid());
		if(app == null)
		{
			return BaseErrorCode.Error_NoApp;
		}
		
		OrderEntity oldorder = queryOrderByCpOrderId(req.getAppid(), req.getCpOrderId());
		if(oldorder != null)
		{
			order.setAppid(oldorder.getAppid());
			order.setAppname(oldorder.getAppname());
			order.setCpOrderId(oldorder.getCpOrderId());
			order.setExt(oldorder.getExt());
			order.setMarket(oldorder.getMarket());
			order.setNickname(oldorder.getNickname());
			order.setPaychannel(paychannel);
			order.setStatus(oldorder.getStatus());
			order.setTotalFee(oldorder.getTotalFee());
			order.setUserId(oldorder.getUserId());
			order.setWares(oldorder.getWares());
			order.setWaresId(oldorder.getWaresId());
			order.setOrderid(oldorder.getOrderid());
			order.setImei(oldorder.getImei());
			order.setMobile(oldorder.getMobile());
			order.setModel(oldorder.getModel());
			order.setVersion(oldorder.getVersion());
			
			return BaseErrorCode.Error_Duplicate;
		}
		else
		{
			order.setAppid(req.getAppid());
			order.setAppname(app.getAppname());
			order.setCpOrderId(req.getCpOrderId());
			order.setExt(req.getExt());
			order.setMarket(req.getMarket());
			order.setNickname(req.getNickname());
			order.setPaychannel(paychannel);
			order.setStatus(BaseConstant.STATUS_CREATE);
			order.setTotalFee(req.getTotalFee());
			order.setUserId(req.getUserId());
			order.setWares(req.getWares());
			order.setWaresId(req.getWaresId());
			order.setTransfer_url(req.getNotify_url());
			
			order.setOrderid(getOutTradeNo());
			
			order.setImei(req.getImei());
			order.setMobile(req.getMobile());
			order.setModel(req.getModel());
			order.setVersion(req.getVersion());
			
			
			IPSeeker iPSeeker =IPSeeker.getInstance();
			String ip = iPSeeker.getIpAddr(request);
			int provinceid = ProvinceUtil.getInstance().getProvinceId(request);
			//TODO 计算设置省份id
			order.setIp(ip);
			order.setProvince(provinceid);
			
			order.setHost(request.getRemoteHost());
			
			execSave(order);
			
			//创建or修改 玩家数据 发起数据
			deviceManager.dealOrderLaunch(order);
			
			return BaseErrorCode.Success;
		}
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + (r.nextInt(1000000) + 1000000);
		key = key.substring(0, 20);
		return key;
	}

	/**
	 * 处理ali订单通知
	 * @param callbackentity
	 */
	public void dealTradeFinished_ali(AliCallbackEntity callbackentity) {
		OrderEntity order = queryOrderByOrderId(callbackentity.getOut_trade_no());
		if(order.getIsdeal() == 1)
		{
			//已处理过
		}
		else{
			order.setStatus(BaseConstant.STATUS_SUCCESS);
			order.setRetCode(callbackentity.getTrade_status());
			order.setPayOrderid(callbackentity.getTrade_no());
			order.setIsdeal(1);
			execUpdate(order);
			
			dealCountAndTransfer(order);
		}
	}

	public void dealTradeFinished_tenpay(TenpayCallbackEntity callbackentity) {
		OrderEntity order = queryOrderByOrderId(callbackentity.getOut_trade_no());
		if(order.getIsdeal() == 1)
		{
			//已处理过
		}
		else{
			order.setStatus(BaseConstant.STATUS_SUCCESS);
			order.setRetCode(callbackentity.getResult_code());
			order.setPayOrderid(callbackentity.getTransaction_id());
			order.setIsdeal(1);
			execUpdate(order);
			
			dealCountAndTransfer(order);
		}
	}

	public void dealTradeFinished_swiftpay(Map<String, String> map) {
		String out_trade_no = map.get("out_trade_no");
		String pay_result = map.get("pay_result");
		String transaction_id = map.get("transaction_id");//威富通订单号
		OrderEntity order = queryOrderByOrderId(out_trade_no);
		if(order.getIsdeal() == 1)
		{
			//已处理过
		}
		else{
			order.setStatus(BaseConstant.STATUS_SUCCESS);
			order.setRetCode(pay_result);
			order.setPayOrderid(transaction_id);
			order.setIsdeal(1);
			execUpdate(order);
			
			dealCountAndTransfer(order);
		}
	}
	
	/**
	 * 处理统计 通知
	 * @param order
	 */
	private void dealCountAndTransfer(OrderEntity order)
	{
		boolean isfirst = deviceManager.dealOrderFinish(order);
		dailyManager.dealOrderFinish(order, isfirst);
		transferService.addTransfer(order);
	}

	/**
	 * 处理手动重发通知
	 * @param id
	 */
	public void dealReTranser(int id)
	{
		OrderEntity order = this.queryOrder(id);
		transferService.addTransfer(order);
	}
	
	public GenericPager<OrderEntity> queryPageList(List<AppGroup> apps, String status) {
		return orderDao.queryByAppid(apps, status);
	}

	public GenericPager<OrderEntity> queryPageList(String curappid, String status) {
		return orderDao.queryByAppid(curappid, status);
	}

	public GenericPager<OrderEntity> queryPageList(String cpOrderid, String orderid,
			String payOrderid) {
		return orderDao.queryByAppid(cpOrderid, orderid, payOrderid);
	}
}
