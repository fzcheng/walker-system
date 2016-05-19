package com.walkersoft.appmanager.manager;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.infrastructure.utils.Assert;
import com.walkersoft.appmanager.BaseConstant;
import com.walkersoft.appmanager.BaseErrorCode;
import com.walkersoft.appmanager.dao.OrderDao;
import com.walkersoft.appmanager.entity.AliCallbackEntity;
import com.walkersoft.appmanager.entity.OrderEntity;
import com.walkersoft.appmanager.req.OrderDataReq;

@Service("orderManager")
public class OrderManagerImpl {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private TransferService transferService;
	
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
	
	public BaseErrorCode createOrder(OrderDataReq req, int paychannel, OrderEntity order) {
		
		OrderEntity oldorder = queryOrderByCpOrderId(req.getAppid(), req.getCpOrderId());
		if(oldorder != null)
		{
			order.setAppid(oldorder.getAppid());
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
			
			return BaseErrorCode.Error_Duplicate;
		}
		else
		{
			order.setAppid(req.getAppid());
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
			
			order.setOrderid(getOutTradeNo());
			execSave(order);
			
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
		key = key + r.nextInt(1000000);
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
			execUpdate(order);
			
			transferService.addTransfer(order);
		}
	}
	
}
