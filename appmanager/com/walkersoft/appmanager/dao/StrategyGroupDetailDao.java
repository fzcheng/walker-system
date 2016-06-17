package com.walkersoft.appmanager.dao;

import org.springframework.stereotype.Repository;

import com.walker.db.Sorts;
import com.walker.db.hibernate.SQLDaoSupport;
import com.walker.db.page.support.GenericPager;
import com.walkersoft.appmanager.entity.StrategyGroupDetailEntity;

@Repository("strategyGroupDetailDao")
public class StrategyGroupDetailDao extends SQLDaoSupport<StrategyGroupDetailEntity> {

	private static final String HQL_GROUPID = "select groupdetail from StrategyGroupDetailEntity groupdetail where groupid=?";
	public GenericPager<StrategyGroupDetailEntity> queryPageList(int groupid) {
		return this.queryForpage(HQL_GROUPID, new Object[]{groupid}, Sorts.ASC().setField("seq"));
	}
	
	public GenericPager<StrategyGroupDetailEntity> queryPageList() {
		return this.queryForEntityPage();
	}
}
