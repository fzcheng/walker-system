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
}
