package com.walkersoft.appmanager.dao;

import java.util.List;
import java.util.Map;

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
	
	
	private static final StringBuilder SQL_USER_APP = new StringBuilder()
	.append("select ru.role_id, ra.appid, app.appname, ra.options")
	.append(" From s_role_user ru")
	.append(" , yl_role_app ra")
	.append(" , yl_app app")
	.append(" Where ru.user_id = ?")
	.append(" And ru.role_id = ra.role_id")
	.append(" And ra.appid = app.appid")
	.append(" Order By app.appid");
	/**
	 * 返回用户所能查看的app列表信息
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getAppListByUser(String userId) {
		return this.sqlQueryForList(SQL_USER_APP.toString(), new Object[]{userId});
	}

	private static final StringBuilder SQL_ALL_APP = new StringBuilder()
	.append("select app.appid, app.appname, app.options")
	.append(" From yl_app app")
	.append(" Order By app.appid");
	public List<Map<String, Object>> getAllAppList() {
		return this.sqlQueryForList(SQL_ALL_APP.toString());
	}
}
