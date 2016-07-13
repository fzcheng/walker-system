package com.walkersoft.appmanager.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.walker.db.PropertyEntry;
import com.walker.db.Sorts;
import com.walker.db.Sorts.Sort;
import com.walker.db.hibernate.SQLDaoSupport;
import com.walker.db.page.support.GenericPager;
import com.walkersoft.appmanager.entity.AppMarketEntity;
import com.walkersoft.appmanager.entity.DailyEntity;

@Repository("appmarketDao")
public class AppMarketDao extends SQLDaoSupport<AppMarketEntity> {

	public GenericPager<AppMarketEntity> queryPageList(String userId){
		return this.queryForEntityPage();
	}
	
	private static final String HQL_APPID = "select daily from AppMarketEntity daily where appid=? and market=?";
//	public GenericPager<DailyEntity> queryByAppid(String curappid) {
//		return this.queryForpage(HQL_APPID, new Object[]{curappid}, Sorts.DESC().setField("date"));
//	}
	
	public List<AppMarketEntity> queryList(String appid, int market) {
		PropertyEntry p[] = new PropertyEntry[2];
		p[0] = PropertyEntry.createEQ("appid", appid);
		p[1] = PropertyEntry.createEQ("market", market);
		
		Sort seqSort = Sorts.ASC().setField("seq");
		//return this.findBy(new String[]{"appid", "market"}, new Object[]{appid, market});
		//return this.findBy(entityClass, p, new Sort[]{seqSort});
		return this.queryForpage(HQL_APPID, new Object[]{appid, market}, Sorts.DESC().setField("seq")).getDatas();
	}

	public AppMarketEntity queryForAppMarketById(int id) {
		return findUniqueBy(entityClass, new String[]{"id"}, new Object[]{id});
	}

	public AppMarketEntity queryForAppMarketByKey(String appid, int market,
			int group_id) {
		return findUniqueBy(entityClass, new String[]{"app.appid", "market", "strategyGroup.group_id"}, new Object[]{appid,market,group_id});
	}

}
