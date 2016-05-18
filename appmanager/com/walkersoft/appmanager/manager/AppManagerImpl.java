package com.walkersoft.appmanager.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.app.ApplicationRuntimeException;
import com.walker.db.page.support.GenericPager;
import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.NumberGenerator;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.appmanager.dao.AppDao;
import com.walkersoft.appmanager.entity.AppEntity;
import com.walkersoft.appmanager.response.QuerySdkIdResult;
import com.walkersoft.flow.entity.FlowBindEntity;

@Service("appManager")
public class AppManagerImpl {

	@Autowired
	private AppDao appDao;
	
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

	public void execDeleteAppInfo(String appid) {
		assert (StringUtils.isNotEmpty(appid));
		
		
		appDao.removeById(Integer.valueOf(appid));
		
		//appCacheProvider.removeCacheData(appid);
	}

	public QuerySdkIdResult queryAppSdkId(String appid, String market) {
		
		QuerySdkIdResult r = new QuerySdkIdResult();
		r.setCode(200);
		r.setSdkId(1);
		return r;
	}
}
