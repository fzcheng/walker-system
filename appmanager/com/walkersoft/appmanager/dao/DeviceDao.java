package com.walkersoft.appmanager.dao;

import org.springframework.stereotype.Repository;

import com.walker.db.hibernate.SQLDaoSupport;
import com.walkersoft.appmanager.entity.DeviceEntity;

@Repository("deviceDao")
public class DeviceDao extends SQLDaoSupport<DeviceEntity> {

	
}
