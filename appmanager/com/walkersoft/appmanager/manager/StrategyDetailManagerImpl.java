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
import com.walkersoft.appmanager.entity.StrategyGroupEntity;

@Service("strategyDetailManager")
public class StrategyDetailManagerImpl {

	@Autowired
	private StrategyGroupDetailDao strategyGroupDetailDao;
	
	@Autowired
	private StrategyGroupDao strategyGroupDao;
	
	@Autowired
	private StrategyDao strategyDao;
	
	//group detail
	public GenericPager<StrategyGroupDetailEntity> queryGroupDetailPageList(int groupid){
		return strategyGroupDetailDao.queryPageList(groupid);
	}

	//group detail
		public GenericPager<StrategyGroupDetailEntity> queryGroupDetailPageList(){
			return strategyGroupDetailDao.queryPageList();
		}
		
	public void execSaveGroupDetail(StrategyGroupDetailEntity entity) {
		Assert.notNull(entity);

		strategyGroupDetailDao.save(entity);
	}

	public void execUpdateGroupDetail(StrategyGroupDetailEntity entity) {
		Assert.notNull(entity);
		strategyGroupDetailDao.updateEntity(entity);
	}

	//group
	public GenericPager<StrategyGroupEntity> queryGroupPageList(){
		return strategyGroupDao.queryPageList();
	}
	
	public void execSaveGroup(StrategyGroupEntity entity) {
		Assert.notNull(entity);
	
		strategyGroupDao.save(entity);
	}
	
	public void execUpdateGroup(StrategyGroupEntity entity) {
		Assert.notNull(entity);
		strategyGroupDao.updateEntity(entity);
	}

	
	//strategy
	public StrategyEntity queryStrategy(int strategy_id) {
		
		return strategyDao.querySingle(strategy_id);
	}

	public GenericPager<StrategyEntity> queryStrategyPageList() {
		return strategyDao.queryPageList();
	}

}
