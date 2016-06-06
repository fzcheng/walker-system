package com.walkersoft.appmanager.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.db.page.support.GenericPager;
import com.walker.infrastructure.utils.Assert;
import com.walkersoft.appmanager.dao.StrategyDao;
import com.walkersoft.appmanager.dao.StrategyGroupDao;
import com.walkersoft.appmanager.dao.StrategyGroupDetailDao;
import com.walkersoft.appmanager.entity.StrategyEntity;
import com.walkersoft.appmanager.entity.StrategyGroupDetailEntity;

@Service("strategyDetailManager")
public class StrategyDetailManagerImpl {

	@Autowired
	private StrategyGroupDetailDao strategyGroupDetailDao;
	
	@Autowired
	private StrategyGroupDao strategyGroupDao;
	
	@Autowired
	private StrategyDao strategyDao;
	
	public GenericPager<StrategyGroupDetailEntity> queryGroupDetailPageList(int groupid){
		return strategyGroupDetailDao.queryPageList(groupid);
	}

	public void execSave(StrategyGroupDetailEntity entity) {
		Assert.notNull(entity);

		strategyGroupDetailDao.save(entity);
	}

	public void execUpdate(StrategyGroupDetailEntity entity) {
		Assert.notNull(entity);
		strategyGroupDetailDao.updateEntity(entity);
	}

	public StrategyEntity queryStrategy(int strategy_id) {
		
		return strategyDao.querySingle(strategy_id);
	}

}
