package com.walkersoft.appmanager.manager;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.infrastructure.utils.Assert;
import com.walkersoft.appmanager.dao.DeviceDao;
import com.walkersoft.appmanager.entity.DeviceEntity;
import com.walkersoft.appmanager.entity.OrderEntity;

@Service("deviceManager")
public class DeviceManagerImpl {

	@Autowired
	private DeviceDao deviceDao;

	public void execSave(DeviceEntity entity) {
		Assert.notNull(entity);
		deviceDao.save(entity);
	}

	public void execUpdate(DeviceEntity entity) {
		Assert.notNull(entity);
		deviceDao.updateEntity(entity);
	}

	public DeviceEntity queryDevice(String imei, String appid, Date date)
	{
		return deviceDao.querySingle(imei, appid, date);
	}
	
	/**
	 * 创建or修改 玩家发起次数数据
	 * @param order
	 * @return
	 */
	public DeviceEntity dealOrderLaunch(OrderEntity order) {
		java.util.Date date = order.getCreate_time();
		java.sql.Date sqlDate=new java.sql.Date(date.getTime());
		
		DeviceEntity device = queryDevice(order.getImei(), order.getAppid(), sqlDate);
		if(device == null)
		{
			device = new DeviceEntity();
			
			device.setAppid(order.getAppid());
			device.setImei(order.getImei());
			device.setDate(sqlDate);
			device.setTotal_count(1);
			device.setTotal_fee(order.getTotalFee());
			
			execSave(device);
		}
		else
		{
			device.setTotal_count(device.getTotal_count() + 1);
			device.setTotal_fee(device.getTotal_fee() + order.getTotalFee());
			execUpdate(device);
		}
		
		return device;
	}

	/**
	 * 处理订单成功 后 修改玩家数据
	 * @param order
	 * @return boolean 是否为首次
	 */
	public boolean dealOrderFinish(OrderEntity order) {
		java.util.Date date = order.getCreate_time();
		java.sql.Date sqlDate=new java.sql.Date(date.getTime());
		
		DeviceEntity device = queryDevice(order.getImei(), order.getAppid(), sqlDate);
		if(device == null)
		{
			device = new DeviceEntity();
			
			device.setAppid(order.getAppid());
			device.setImei(order.getImei());
			device.setDate(sqlDate);
			device.setSuc_count(1);
			device.setSuc_total_fee(order.getTotalFee());
			device.setTotal_count(1);
			device.setTotal_fee(order.getTotalFee());
			
			execSave(device);
			return true;
		}
		else
		{
			device.setSuc_count(device.getSuc_count() + 1);
			device.setSuc_total_fee(device.getSuc_total_fee() + order.getTotalFee());
			execUpdate(device);
			
			if(device.getSuc_count() == 1)
				return true;
			return false;
		}
		
	}
}
