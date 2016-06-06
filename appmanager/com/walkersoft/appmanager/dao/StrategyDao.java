package com.walkersoft.appmanager.dao;

import org.springframework.stereotype.Repository;

import com.walker.db.hibernate.SQLDaoSupport;
import com.walkersoft.appmanager.entity.StrategyEntity;

@Repository("strategyDao")
public class StrategyDao extends SQLDaoSupport<StrategyEntity> {

	public StrategyEntity querySingle(int strategy_id) {
		return this.findUniqueBy(entityClass, new String[]{"id"}, new Object[]{strategy_id});
	}
	
}
