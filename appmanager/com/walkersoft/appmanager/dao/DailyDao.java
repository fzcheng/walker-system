package com.walkersoft.appmanager.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.walker.db.Sorts;
import com.walker.db.Sorts.Sort;
import com.walker.db.hibernate.SQLDaoSupport;
import com.walker.db.page.support.GenericPager;
import com.walkersoft.appmanager.entity.DailyEntity;

@Repository("dailyDao")
public class DailyDao extends SQLDaoSupport<DailyEntity> {

	
	
//	private final RowMapper<OrderEntity> rowMapper = new OrderRowMapper();
	
	private static Sort sxhSort = Sorts.ASC().setField("sxh");
	
	public DailyEntity querySingleOrderByCpOrderid(String appid, String cporderid) {
//		String[] values = {appid, cporderid};
//		return (OrderEntity)this.queryForObject(HQL_SINGLE_CPORDERID, new Object[]{appid, cporderid});
//		return this.getJdbcTemplate().queryForObject(HQL_SINGLE_CPORDERID, new Object[]{appid, cporderid}, rowMapper);
		//return this.findUniqueBy(PropertyEntry.createEQ("appid", 0));
//		return this.findUniqueBy(HQL_SINGLE_CPORDERID, new Object[]{appid, cporderid});
		return this.findUniqueBy(entityClass, new String[]{"cpOrderId", "appid"}, new Object[]{cporderid, appid});
	}

	public GenericPager<DailyEntity> queryPageList(String[] appids) {
		//TODO 20160531
		return null;
	}

	public DailyEntity queryDaily(String appid, Date date, int paychannel, int market) {
		return this.findUniqueBy(entityClass, new String[]{"appid", "date", "paychannel", "market"}, 
				new Object[]{ appid,  date,  paychannel, market});
	}

	private static final String HQL_APPID = "select daily from DailyEntity daily where appid=? and datatype=0";
	public GenericPager<DailyEntity> queryByAppid(String curappid) {
		return this.queryForpage(HQL_APPID, new Object[]{curappid}, Sorts.ASC().setField("create_time"));
	}

	
	private static final String HQL_MYAPP = "select daily from DailyEntity daily where appid in(:myapps) and datatype=0";
	public GenericPager<DailyEntity> queryByAppid(String[] appids) {
		if(appids == null || appids.length <= 0)
			return null;
		String hql = HQL_MYAPP;
		String tmp = "?";
		for(int i = 1; i < appids.length; i ++)
		{
			tmp += ",?";
		}
		hql = hql.replaceAll(":myapps", tmp);
		return this.queryForpage(hql, appids, Sorts.ASC().setField("create_time"));
	}
}
