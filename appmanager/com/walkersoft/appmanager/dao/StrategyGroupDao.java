package com.walkersoft.appmanager.dao;

import org.springframework.stereotype.Repository;

import com.walker.db.hibernate.SQLDaoSupport;
import com.walker.db.page.support.GenericPager;
import com.walkersoft.appmanager.entity.StrategyGroupEntity;

@Repository("strategyGroupDao")
public class StrategyGroupDao extends SQLDaoSupport<StrategyGroupEntity> {

	public GenericPager<StrategyGroupEntity> queryPageList() {
		return this.queryForEntityPage();
	}
	
}
