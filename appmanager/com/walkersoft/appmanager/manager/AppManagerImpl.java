package com.walkersoft.appmanager.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.db.page.support.GenericPager;
import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.NumberGenerator;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.appmanager.BaseConstant;
import com.walkersoft.appmanager.dao.AppDao;
import com.walkersoft.appmanager.dao.AppMarketDao;
import com.walkersoft.appmanager.entity.AppEntity;
import com.walkersoft.appmanager.entity.AppMarketEntity;
import com.walkersoft.appmanager.entity.ConditionDate;
import com.walkersoft.appmanager.entity.ConditionLocation;
import com.walkersoft.appmanager.entity.StrategyCondition;
import com.walkersoft.appmanager.entity.StrategyEntity;
import com.walkersoft.appmanager.entity.StrategyGroupDetailEntity;
import com.walkersoft.appmanager.response.QuerySdkIdResult;
import com.walkersoft.appmanager.util.RandomUtil;

@Service("appManager")
public class AppManagerImpl {

	@Autowired
	private AppDao appDao;
	
	@Autowired
	private AppMarketDao appmarketDao;
	
	@Autowired
	private StrategyDetailManagerImpl strategyDetailManager;
	
	public GenericPager<AppEntity> queryPageList(String userId){
		return appDao.queryPageList(userId);
	}

	public void execSave(AppEntity entity) {
		Assert.notNull(entity);
		//AppEntity exist = appDao.querySingleApp(entity.getAppid());

		//if(exist != null){
		//	throw new ApplicationRuntimeException("已经存在此应用id");
		//}
		long time = NumberGenerator.getSequenceNumber();
		entity.setCreate_time(time);
		entity.setLast_time(time);
		appDao.save(entity);
	}

	public void execUpdate(AppEntity entity) {
		Assert.notNull(entity);
		long time = NumberGenerator.getSequenceNumber();
		entity.setLast_time(time);
		appDao.updateEntity(entity);
	}
	
	public void execDeleteAppInfo(String id) {
		assert (StringUtils.isNotEmpty(id));
		
		
		appDao.removeById(Integer.valueOf(id));
		
		//appCacheProvider.removeCacheData(appid);
	}

	public QuerySdkIdResult queryAppSdkId(String appid, int market, int locationid) {
		
		AppMarketEntity appMarket = appmarketDao.querySingle(appid, market);
		
		QuerySdkIdResult r = new QuerySdkIdResult();
		if(appMarket != null)
		{
			int strategyGroupId = appMarket.getStratety_groupid();
			if(strategyGroupId > 0)
			{
				int sdkid = BaseConstant.M_SDK;
				List<StrategyGroupDetailEntity> detailList = strategyDetailManager.queryGroupDetailPageList(strategyGroupId).getDatas();
				for(StrategyGroupDetailEntity e : detailList)
				{
					if(e.getIsuse() == 1)
					{
						StrategyEntity strategy = strategyDetailManager.queryStrategy(e.getStrategy_id());
						//根据时间端 区域 百分比 判断是否满足条件
						Date curtime = new Date();
						if(isCompareCondition(strategy, curtime, locationid))
						{
							sdkid = strategy.getSdkid();
							break;
						}
					}
				}
				
				r.setCode(200);
				r.setSdkId(sdkid);
			}
			else
			{
				r.setCode(200);
				r.setSdkId( BaseConstant.M_SDK);
			}
		}
		else
		{
			r.setCode(200);
			r.setSdkId( BaseConstant.M_SDK);
		}
		return r;
	}

	//根据时间端 区域 百分比 判断是否满足 strategy条件
	private boolean isCompareCondition(StrategyEntity strategy, Date curtime,
			int locationid) {
		
		List<StrategyCondition> cList = new ArrayList<StrategyCondition>();
		
		if(strategy.getType1() > 0)
		{
			StrategyCondition c = new StrategyCondition();
			c.setType(strategy.getType1());
			c.setValue(strategy.getValue1());
			cList.add(c);
		}
		
		if(strategy.getType2() > 0)
		{
			StrategyCondition c = new StrategyCondition();
			c.setType(strategy.getType2());
			c.setValue(strategy.getValue2());
			cList.add(c);
		}
		
		if(strategy.getType3() > 0)
		{
			StrategyCondition c = new StrategyCondition();
			c.setType(strategy.getType3());
			c.setValue(strategy.getValue3());
			cList.add(c);
		}
		
		for(StrategyCondition c : cList)
		{
			try{
				// 1-时段 2-地区 3-百分比
				if(c.getType() == 1)
				{
					boolean isIn = false;
					//把value转换成时间段list 然后进行判断
					List<ConditionDate> l = c.getConditionDate();
					for(ConditionDate cd : l)
					{
						if(cd.isContain(curtime))
						{
							isIn = true;
							break;
						}
					}
					
					if(isIn == false)
					{
						return false;
					}
				}
				else if(c.getType() == 2)
				{
					//把value转换成地区id list 然后进行判断
					boolean isIn = false;
					//把value转换成时间段list 然后进行判断
					List<ConditionLocation> l = c.getConditionLocation();
					for(ConditionLocation cd : l)
					{
						if(cd.getLocation() == locationid)
						{
							isIn = true;
							break;
						}
					}
					
					if(isIn == false)
					{
						return false;
					}
				}
				else if(c.getType() == 3)
				{
					int br = Integer.valueOf(c.getValue());
					//直接随机按百分比判断
					int r = RandomUtil.getIntRandom(100);
					if(r >= br)
					{
						return false;
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public String getAppkey(String appid) {
		AppEntity entity = appDao.queryForApp(appid);
		if(entity != null)
			return entity.getAppcode();
		return null;
	}
	
	public AppEntity getByWxMchid(String wx_mch_id) {
		AppEntity entity = appDao.queryForAppByWxMchid(wx_mch_id);
		if(entity != null)
			return entity;
		return null;
	}

	public AppEntity queryApp(String id) {
		AppEntity entity = appDao.queryForAppById(Integer.valueOf(id));
		if(entity != null)
			return entity;
		return null;
	}

	public AppEntity queryByAppid(String appid) {
		AppEntity entity = appDao.queryForApp(appid);
		if(entity != null)
			return entity;
		return null;
	}
	
	/**
	 * 查询登录用户的app权限
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> queryAppListByUser(String userId) {
		return appDao.getAppListByUser(userId);
	}

	/**
	 * 查询所有的app权限
	 * @return
	 */
	public List<Map<String, Object>> queryAllAppList() {
		return appDao.getAllAppList();
	}

	/**
	 * 查询登录用户的appid列表
	 * @param userId
	 * @return
	 */
	public List<String> queryUserAppList(String userId) {
		List<String> appList = new ArrayList<String>();
		List<Map<String, Object>> list = queryAppListByUser(userId);
		if(list != null){
			for(Map<String, Object> app : list){
				appList.add(app.get("appid").toString());
			}
		}
		return appList;
	}

	public void execSaveUserApps(String userId, List<String> appList) {
		appDao.deleteUserAppByUserId(userId);
		appDao.insertUserAppByUserId(userId, appList);
	}
}
