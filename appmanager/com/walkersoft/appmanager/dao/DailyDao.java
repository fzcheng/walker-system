package com.walkersoft.appmanager.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.walker.db.Sorts;
import com.walker.db.Sorts.Sort;
import com.walker.db.hibernate.SQLDaoSupport;
import com.walker.db.page.support.GenericPager;
import com.walkersoft.appmanager.entity.DailyEntity;
import com.walkersoft.appmanager.entity.OrderEntity;

@Repository("dailyDao")
public class DailyDao extends SQLDaoSupport<DailyEntity> {

	private static final String HQL_SINGLE_CPORDERID = "select * from yl_order where appid=? and cpOrderId=?";
	
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

}
