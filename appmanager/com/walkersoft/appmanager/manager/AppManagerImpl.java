package com.walkersoft.appmanager.manager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.db.page.support.GenericPager;
import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.NumberGenerator;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.appmanager.dao.AppDao;
import com.walkersoft.appmanager.dao.AppMarketDao;
import com.walkersoft.appmanager.entity.AppEntity;
import com.walkersoft.appmanager.entity.AppMarketEntity;
import com.walkersoft.appmanager.response.QuerySdkIdResult;

@Service("appManager")
public class AppManagerImpl {

	@Autowired
	private AppDao appDao;
	
	@Autowired
	private AppMarketDao appmarketDao;
	
	public GenericPager<AppEntity> queryPageList(String userId){
		return appDao.queryPageList(userId);
	}

	public void execSave(AppEntity entity) {
		Assert.notNull(entity);
		//AppEntity exist = appDao.querySingleApp(entity.getAppid());

		//if(exist != null){
		//	throw new ApplicationRuntimeException("已经存在此应用id");
		//}
		long time = NumberGenerator.getSequenceNumber();
		entity.setCreate_time(time);
		entity.setLast_time(time);
		appDao.save(entity);
	}

	public void execUpdate(AppEntity entity) {
		Assert.notNull(entity);
		long time = NumberGenerator.getSequenceNumber();
		entity.setLast_time(time);
		appDao.updateEntity(entity);
	}
	
	public void execDeleteAppInfo(String id) {
		assert (StringUtils.isNotEmpty(id));
		
		
		appDao.removeById(Integer.valueOf(id));
		
		//appCacheProvider.removeCacheData(appid);
	}

	public QuerySdkIdResult queryAppSdkId(String appid, int market) {
		
		AppMarketEntity set = appmarketDao.querySingle(appid, market);
		
		QuerySdkIdResult r = new QuerySdkIdResult();
		if(set != null)
		{	
			r.setCode(200);
			r.setSdkId(set.getSdkid());
		}
		else
		{
			r.setCode(200);
			r.setSdkId(1);
		}
		return r;
	}

	public String getAppkey(String appid) {
		AppEntity entity = appDao.queryForApp(appid);
		if(entity != null)
			return entity.getAppcode();
		return null;
	}
	
	public AppEntity getByWxMchid(String wx_mch_id) {
		AppEntity entity = appDao.queryForAppByWxMchid(wx_mch_id);
		if(entity != null)
			return entity;
		return null;
	}

	public AppEntity queryApp(String id) {
		AppEntity entity = appDao.queryForAppById(Integer.valueOf(id));
		if(entity != null)
			return entity;
		return null;
	}

	public AppEntity queryByAppid(String appid) {
		AppEntity entity = appDao.queryForApp(appid);
		if(entity != null)
			return entity;
		return null;
	}
	
	/**
	 * 查询登录用户的app权限
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> queryAppListByUser(String userId) {
		return appDao.getAppListByUser(userId);
	}

	/**
	 * 查询所有的app权限
	 * @return
	 */
	public List<Map<String, Object>> queryAllAppList() {
		return appDao.getAllAppList();
	}
}
