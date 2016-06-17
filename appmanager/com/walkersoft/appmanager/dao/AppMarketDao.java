package com.walkersoft.appmanager.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.walker.db.PropertyEntry;
import com.walker.db.Sorts;
import com.walker.db.Sorts.Sort;
import com.walker.db.hibernate.SQLDaoSupport;
import com.walker.db.page.support.GenericPager;
import com.walkersoft.appmanager.entity.AppMarketEntity;

@Repository("appmarketDao")
public class AppMarketDao extends SQLDaoSupport<AppMarketEntity> {

	public GenericPager<AppMarketEntity> queryPageList(String userId){
		return this.queryForEntityPage();
	}
	
	public List<AppMarketEntity> queryList(String appid, int market) {
		PropertyEntry p[] = new PropertyEntry[2];
		p[0] = new PropertyEntry();
		p[0].setName("appid");
		p[0].setValue(appid);
		
		p[1] = new PropertyEntry();
		p[1].setName("market");
		p[1].setValue(market);
		
		Sort seqSort = Sorts.ASC().setField("seq");
		//return this.findBy(new String[]{"appid", "market"}, new Object[]{appid, market});
		return this.findBy(p, new Sort[]{seqSort});
	}

	public AppMarketEntity queryForAppMarketById(int id) {
		return findUniqueBy(entityClass, new String[]{"id"}, new Object[]{id});
	}

}
