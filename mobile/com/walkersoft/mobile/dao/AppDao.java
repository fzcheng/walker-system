package com.walkersoft.mobile.dao;

import org.springframework.stereotype.Repository;

import com.walker.db.Sorts;
import com.walker.db.Sorts.Sort;
import com.walker.db.hibernate.SQLDaoSupport;
import com.walker.db.page.support.GenericPager;
import com.walkersoft.mobile.entity.AppInfo;
import com.walkersoft.mobile.entity.AppUpdate;

/**
 * APP管理Dao实现
 * @author shikeying
 * @date 2015-3-19
 *
 */
@Repository
public class AppDao extends SQLDaoSupport<AppInfo> {

	private final Sort timeSort = Sorts.DESC().setField("createTime");
	private final Sort timeSort2 = Sorts.ASC().setField("createTime");
	
	private static final String HQL_GET_APP_UPDATE_PAGE = "select au from AppUpdate au where au.appId = ?";
	
	public GenericPager<AppInfo> queryPageAppList(){
		return this.queryForEntityPage(timeSort);
	}
	
	/**
	 * 分页返回APP升级列表数据
	 * @param appId
	 * @return
	 */
	public GenericPager<AppUpdate> queryPageAppUpdateList(String appId){
		return this.queryForpageByType(HQL_GET_APP_UPDATE_PAGE, new Object[]{appId}, timeSort2, AppUpdate.class);
	}
	
	public boolean existSameId(String id){
		AppInfo appInfo = this.get(id);
		return appInfo != null;
	}
	
	/**
	 * 添加新版本升级记录时，需要更新appInfo记录中相关版本信息和文件大小。
	 * @param appUpdate
	 * @param size
	 */
	public void execUpdateAppVersionInfo(AppUpdate appUpdate, long size){
		AppInfo appInfo = this.get(appUpdate.getAppId());
		appInfo.setReleaseFileId(appUpdate.getFileId());
		appInfo.setReleaseForce(appUpdate.getUpdateForce());
		appInfo.setReleaseSize(size);
		appInfo.setReleaseVersion(appUpdate.getVersionCode());
		appInfo.setReleaseText(appUpdate.getSummary());
		this.save(appInfo);
	}
}
