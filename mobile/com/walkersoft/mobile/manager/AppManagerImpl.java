package com.walkersoft.mobile.manager;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.app.ApplicationRuntimeException;
import com.walker.db.page.support.GenericPager;
import com.walker.file.FileEngine;
import com.walker.file.FileMeta;
import com.walker.file.FileOperateException;
import com.walker.infrastructure.utils.NumberGenerator;
import com.walkersoft.mobile.dao.AppDao;
import com.walkersoft.mobile.entity.AppInfo;
import com.walkersoft.mobile.entity.AppUpdate;

/**
 * APP应用管理
 * @author shikeying
 *
 */
@Service("appManager")
public class AppManagerImpl {

	@Autowired
	private AppDao appDao;
	
	@Autowired
	private FileEngine fileEngine;
	
	public GenericPager<AppInfo> queryPageAppList(){
		return appDao.queryForEntityPage();
	}
	
	public AppInfo queryAppInfo(String appId){
		return appDao.get(appId);
	}
	
	/**
	 * 分页返回APP升级列表数据
	 * @param appId
	 * @return
	 */
	public GenericPager<AppUpdate> queryPageAppUpdateList(String appId){
		AppInfo appInfo = appDao.get(appId);
		GenericPager<AppUpdate> pager = appDao.queryPageAppUpdateList(appId);
		List<AppUpdate> datas = pager.getDatas();
		if(datas != null){
			for(AppUpdate au : datas){
				au.setAppName(appInfo.getName());
			}
		}
		return pager;
	}
	
	/**
	 * 保存新创建的APP基本信息，创建时版本为1
	 * @param appInfo
	 * @param files
	 * @return
	 */
	public String execSaveAppInfo(AppInfo appInfo, List<FileMeta> files){
		String fileId = null;
		if(files != null){
			try {
				fileId = fileEngine.writeFiles(files).get(0);
			} catch (FileOperateException e) {
				throw new ApplicationRuntimeException("file write failed in save department!", e);
			}
		} else 
			return "未上传文件";
		File file = fileEngine.getFileObject(fileId);
		appInfo.setReleaseFileId(fileId);
		appInfo.setCreateTime(NumberGenerator.getSequenceNumber());
		appInfo.setReleaseSize(file.length());
		appDao.save(appInfo);
		return null;
	}
	
	public void execRemoveAppInfo(String appId){
		appDao.removeById(appId);
	}
	
	/**
	 * 数据库中是否存在相同ID的APP，如果存在返回<code>true</code>
	 * @param id
	 * @return
	 */
	public boolean existSameId(String id){
		return appDao.existSameId(id);
	}
	
	/**
	 * 添加升级版本记录。
	 * @param appUpdate
	 * @param files
	 * @return
	 */
	public String execSaveAppUpdate(AppUpdate appUpdate, List<FileMeta> files){
		String fileId = null;
		if(files == null){
			return "未上传文件";
		}
		try {
			fileId = fileEngine.writeFiles(files).get(0);
			appUpdate.setCreateTime(NumberGenerator.getSequenceNumber());
			appUpdate.setFileId(fileId);
			appUpdate.setId(NumberGenerator.getLongSequenceNumber());
			appDao.save(appUpdate);
			
			File file = fileEngine.getFileObject(fileId);
			
			// 同时更新appInfo记录中的版本和文件大小等信息
			appDao.execUpdateAppVersionInfo(appUpdate, file.length());
			return null;
			
		} catch (FileOperateException e) {
			throw new ApplicationRuntimeException("file write failed in execSaveAppUpdate()!", e);
		}
	}
}
