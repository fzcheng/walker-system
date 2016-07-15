package com.walkersoft.appmanager.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.walker.db.Sorts;
import com.walker.db.Sorts.Sort;
import com.walker.db.hibernate.SQLDaoSupport;
import com.walker.db.page.support.GenericPager;
import com.walkersoft.appmanager.entity.OrderEntity;

@Repository("orderDao")
public class OrderDao extends SQLDaoSupport<OrderEntity> {

	private static final String HQL_SINGLE_CPORDERID = "select * from yl_order where appid=? and cpOrderId=?";
	
//	private final RowMapper<OrderEntity> rowMapper = new OrderRowMapper();
	
	private static Sort sxhSort = Sorts.ASC().setField("sxh");
	
	public OrderEntity querySingleOrderByCpOrderid(String appid, String cporderid) {
//		String[] values = {appid, cporderid};
//		return (OrderEntity)this.queryForObject(HQL_SINGLE_CPORDERID, new Object[]{appid, cporderid});
//		return this.getJdbcTemplate().queryForObject(HQL_SINGLE_CPORDERID, new Object[]{appid, cporderid}, rowMapper);
		//return this.findUniqueBy(PropertyEntry.createEQ("appid", 0));
//		return this.findUniqueBy(HQL_SINGLE_CPORDERID, new Object[]{appid, cporderid});
		return this.findUniqueBy(entityClass, new String[]{"cpOrderId", "appid"}, new Object[]{cporderid, appid});
	}

	public OrderEntity queryOrderByPayOrderId(String payOrderid, int paychannel) {
		return this.findUniqueBy(entityClass, new String[]{"payOrderid", "paychannel"}, new Object[]{payOrderid, paychannel});
	}

	public OrderEntity queryOrderByOrderId(String orderid) {
		return this.findUniqueBy(entityClass, new String[]{"orderid"}, new Object[]{orderid});
	}
	
	private static final String HQL_QUERY_ORDER = "select daily from OrderEntity daily where 1=1";
	public GenericPager<OrderEntity> queryByAppid(String curappid, String status) {
		String hql = HQL_QUERY_ORDER;
		List<Object> objs = new ArrayList<Object>();
		if(curappid != null && !curappid.equals(""))
		{
			hql += " and appid=?";
			objs.add(curappid);
		}
		if(status != null)
		{
			hql += " and status=?";
			objs.add(Integer.valueOf(status));			
		}
		
		return this.queryForpage(hql, objs.toArray(), Sorts.DESC().setField("create_time"));
	}

	
	private static final String HQL_MYAPP = "select daily from OrderEntity daily where appid in(:myapps) and status=? ";
	public GenericPager<OrderEntity> queryByAppid(String[] appids, int status) {
		if(appids == null || appids.length <= 0)
			return null;
		String hql = HQL_MYAPP;
		String tmp = "?";
		for(int i = 1; i < appids.length; i ++)
		{
			tmp += ",?";
		}
		hql = hql.replaceAll(":myapps", tmp);
		return this.queryForpage(hql, appids, Sorts.DESC().setField("create_time"));
	}

}
