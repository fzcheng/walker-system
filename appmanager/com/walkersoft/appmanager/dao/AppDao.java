package com.walkersoft.appmanager.dao;

import org.springframework.stereotype.Repository;

import com.walker.db.hibernate.SQLDaoSupport;
import com.walker.db.page.support.GenericPager;
import com.walkersoft.appmanager.entity.AppEntity;

@Repository("appDao")
public class AppDao extends SQLDaoSupport<AppEntity> {

	//private Sort timeSort = Sorts.DESC().setField("createTime");
	
	//private static final String HQL_PAGE_LIST = "select * from yl_app";
	
	public GenericPager<AppEntity> queryPageList(String userId){
		return this.queryForEntityPage();
	}

	public AppEntity queryForApp(String appid) {
		return findUniqueBy(entityClass, new String[]{"appid"}, new Object[]{appid});
	}

	public AppEntity queryForAppById(int id) {
		return findUniqueBy(entityClass, new String[]{"id"}, new Object[]{id});
	}

	public AppEntity queryForAppByWxMchid(String wx_mch_id) {
		return findUniqueBy(entityClass, new String[]{"wx_mch_id"}, new Object[]{wx_mch_id});
	}

	public AppEntity queryForAppByAppid(String appid) {
		// TODO Auto-generated method stub
		return null;
	}
}
