package com.walkersoft.appmanager.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.db.page.support.GenericPager;
import com.walkersoft.appmanager.dao.AppMarketDao;
import com.walkersoft.appmanager.entity.AppMarketEntity;

@Service("appmarketManager")
public class AppMarketManagerImpl {

	@Autowired
	private AppManagerImpl appManager;
	
	@Autowired
	private AppMarketDao appmarketDao;

	public GenericPager<AppMarketEntity> queryPageList(String currentUserId) {
		return appmarketDao.queryPageList(currentUserId);
	}

	public AppMarketEntity queryAppMarket(String id) {
		AppMarketEntity entity = appmarketDao.queryForAppMarketById(Integer.valueOf(id));
		if(entity != null)
			return entity;
		return null;
	}
	
	public AppMarketEntity queryAppMarket(String appid, int market, int group_id) {
		AppMarketEntity entity = appmarketDao.queryForAppMarketByKey(appid, market, group_id);
		if(entity != null)
			return entity;
		return null;
	}

	public void execSave(AppMarketEntity entity) {
		appmarketDao.save(entity);
	}

	public void execUpdate(AppMarketEntity entity) {
		appmarketDao.updateEntity(entity);
	}

	public void execDeleteAppMarketInfo(String id) {
		appmarketDao.removeById(Integer.valueOf(id));
	}
	

}
