package com.walkersoft.appmanager.dao;

import java.sql.Date;

import org.springframework.stereotype.Repository;

import com.walker.db.hibernate.SQLDaoSupport;
import com.walkersoft.appmanager.entity.DeviceEntity;
import com.walkersoft.appmanager.entity.OrderEntity;

@Repository("deviceDao")
public class DeviceDao extends SQLDaoSupport<DeviceEntity> {

	public DeviceEntity querySingle(String imei, String appid, Date date) {
		return this.findUniqueBy(entityClass, new String[]{"imei", "appid", "date"}, new Object[]{imei, appid, date});
	}
	
}
