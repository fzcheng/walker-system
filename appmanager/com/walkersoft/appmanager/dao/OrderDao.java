package com.walkersoft.appmanager.dao;

import org.springframework.stereotype.Repository;

import com.walker.db.Sorts;
import com.walker.db.Sorts.Sort;
import com.walker.db.hibernate.SQLDaoSupport;
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

}
