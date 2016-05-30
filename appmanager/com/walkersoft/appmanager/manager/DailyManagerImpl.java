package com.walkersoft.appmanager.manager;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.db.page.support.GenericPager;
import com.walker.infrastructure.utils.Assert;
import com.walkersoft.appmanager.dao.AppMarketDao;
import com.walkersoft.appmanager.dao.DailyDao;
import com.walkersoft.appmanager.entity.DailyEntity;
import com.walkersoft.appmanager.entity.OrderEntity;

@Service("dailyManager")
public class DailyManagerImpl {

	@Autowired
	private DailyDao dailyDao;
	
	@Autowired
	private AppMarketDao appmarketDao;
	
	public GenericPager<DailyEntity> queryPageList(String[] appids){
		return dailyDao.queryPageList(appids);
	}

	public void execSave(DailyEntity entity) {
		Assert.notNull(entity);
		//AppEntity exist = appDao.querySingleApp(entity.getAppid());

		//if(exist != null){
		//	throw new ApplicationRuntimeException("已经存在此应用id");
		//}
		Date date = new Date();       
		Timestamp time = new Timestamp(date.getTime());
		entity.setCreate_time(time);
		entity.setLast_time(time);
		dailyDao.save(entity);
	}

	public void execUpdate(DailyEntity entity) {
		Assert.notNull(entity);
		Date date = new Date();       
		Timestamp time = new Timestamp(date.getTime());
		entity.setLast_time(time);
		dailyDao.updateEntity(entity);
	}

	/**
	 * 订单成功后处理 daily 统计
	 * @param order
	 */
	public void dealOrderFinish(OrderEntity order)
	{
		try{
			
		}
		catch(Exception e)
		{
			
		}
	}
}
