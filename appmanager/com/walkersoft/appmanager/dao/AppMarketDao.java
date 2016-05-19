package com.walkersoft.appmanager.dao;

import org.springframework.stereotype.Repository;

import com.walker.db.hibernate.SQLDaoSupport;
import com.walkersoft.appmanager.entity.AppMarketEntity;

@Repository("appmarketDao")
public class AppMarketDao extends SQLDaoSupport<AppMarketEntity> {

	public AppMarketEntity querySingle(String appid, int market) {
		return this.findUniqueBy(entityClass, new String[]{"appid", "market"}, new Object[]{appid, market});
	}

}
