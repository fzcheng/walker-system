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

	private DailyEntity queryDaily(String appid, Date date, int paychannel, int market) {
		return dailyDao.queryDaily( appid,  date,  paychannel, market);
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
			createOrAdd(order.getAppid(), order.getCreate_time(), order.getPaychannel(), order.getMarket(), order);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void createOrAdd(String appid, Timestamp time, int paychannel, int market, OrderEntity order) {
		Date date = time;
		DailyEntity daily = queryDaily(appid, date, paychannel, market);
		if(daily != null)
		{
			daily.setOrder_count(daily.getOrder_count() + 1);
			daily.setOrder_total_fee(daily.getOrder_total_fee() + order.getTotalFee());
			execUpdate(daily);
		}
		else
		{
			Timestamp now = new Timestamp(date.getTime());
			
			DailyEntity entity = new DailyEntity();
			entity.setAppid(appid);
			entity.setCreate_time(now);
			
			java.sql.Date sqlDate=new java.sql.Date(date.getTime());
			
			entity.setDate(sqlDate);
			entity.setLast_time(now);
			entity.setMarket(market);
			entity.setOrder_count(1);
			entity.setOrder_total_fee(order.getTotalFee());
			entity.setPaychannel(paychannel);
			
			execSave(entity);
		}
	}
}
