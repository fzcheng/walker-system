package com.walkersoft.appmanager.manager;

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

	public void addRecord(OrderEntity order) {
		// TODO Auto-generated method stub
		
	}

}
