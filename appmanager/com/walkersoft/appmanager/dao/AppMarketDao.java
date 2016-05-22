package com.walkersoft.appmanager.dao;

import org.springframework.stereotype.Repository;

import com.walker.db.hibernate.SQLDaoSupport;
import com.walker.db.page.support.GenericPager;
import com.walkersoft.appmanager.entity.AppMarketEntity;

@Repository("appmarketDao")
public class AppMarketDao extends SQLDaoSupport<AppMarketEntity> {

	public GenericPager<AppMarketEntity> queryPageList(String userId){
		return this.queryForEntityPage();
	}
	
	public AppMarketEntity querySingle(String appid, int market) {
		return this.findUniqueBy(entityClass, new String[]{"appid", "market"}, new Object[]{appid, market});
	}

	public AppMarketEntity queryForAppMarketById(int id) {
		return findUniqueBy(entityClass, new String[]{"id"}, new Object[]{id});
	}

}
