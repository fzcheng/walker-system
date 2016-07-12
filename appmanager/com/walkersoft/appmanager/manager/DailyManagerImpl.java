package com.walkersoft.appmanager.manager;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.db.page.support.GenericPager;
import com.walker.infrastructure.utils.Assert;
import com.walkersoft.appmanager.dao.AppMarketDao;
import com.walkersoft.appmanager.dao.DailyDao;
import com.walkersoft.appmanager.entity.DailyEntity;
import com.walkersoft.appmanager.entity.OrderEntity;
import com.walkersoft.system.pojo.AppGroup;

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
	 * @param isfirst  设备是否是第一次
	 */
	public void dealOrderFinish(OrderEntity order, boolean isfirst)
	{
		try{
			createOrAdd(order.getAppid(), order.getAppname(), order.getCreate_time(), order.getPaychannel(), order.getMarket(), order, isfirst);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void createOrAdd(String appid, String appname, Timestamp time, int paychannel, int market, OrderEntity order, boolean isfirst) {
		createOrAdd_detail(appid, appname, time, paychannel, market, order, isfirst);
		createOrAdd_detail(appid, appname, time, -1, -1, order, isfirst);
		createOrAdd_detail(appid, appname, time, -1, market, order, isfirst);
		createOrAdd_detail(appid, appname, time, paychannel, -1, order, isfirst);
	}
	
	private void createOrAdd_detail(String appid, String appname, Timestamp time, int paychannel, int market, OrderEntity order, boolean isfirst) {
		int datatype = 0;
		if(paychannel == -1 && market == -1)
		{
			datatype = DailyEntity.DATATYPE_ALL;
		}
		else if(market == -1)
		{
			datatype = DailyEntity.DATATYPE_PAYCHANNEL;
		}
		else if(paychannel == -1)
		{
			datatype = DailyEntity.DATATYPE_MARKET;
		}
		else
		{
			datatype = DailyEntity.DATATYPE_PAYCHANNEL_MARKET;
		}
		
		Date date = time;
		DailyEntity daily = queryDaily(appid, date, paychannel, market);
		if(daily != null)
		{
			daily.setOrder_count(daily.getOrder_count() + 1);
			daily.setOrder_total_fee(daily.getOrder_total_fee() + order.getTotalFee());
			if(isfirst)
				daily.setDevice_count(daily.getDevice_count() + 1);
			execUpdate(daily);
		}
		else
		{
			Timestamp now = new Timestamp(date.getTime());
			
			DailyEntity entity = new DailyEntity();
			entity.setAppid(appid);
			entity.setAppname(appname);
			entity.setCreate_time(now);
			entity.setDatatype(datatype);
			
			java.sql.Date sqlDate=new java.sql.Date(date.getTime());
			
			entity.setDate(sqlDate);
			entity.setLast_time(now);
			entity.setMarket(market);
			entity.setOrder_count(1);
			entity.setOrder_total_fee(order.getTotalFee());
			entity.setPaychannel(paychannel);
			entity.setDevice_count(1);
			
			execSave(entity);
		}
	}

	/**
	 * 查询
	 * @param apps
	 * @return
	 */
	public GenericPager<DailyEntity> queryPageList(List<AppGroup> apps) {
		
		String[] appids = new String[apps.size() * 2];
		for(int i = 0; i < apps.size(); i ++)
		{
			AppGroup a = apps.get(i);
			appids[i] = a.getAppid();
			appids[i+1] = a.getAppid();
		}

		return dailyDao.queryByAppid(appids);
	}

	/**
	 * 查询
	 * @param apps
	 * @return
	 */
	public GenericPager<DailyEntity> queryPageList(String curappid) {
		return dailyDao.queryByAppid(curappid);
	}
}
