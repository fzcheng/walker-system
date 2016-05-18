package com.walkersoft.mobile.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.app.ApplicationRuntimeException;
import com.walker.file.FileEngine;
import com.walker.file.FileMeta;
import com.walker.file.FileOperateException;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.mobile.dao.MobileUserDao;
import com.walkersoft.mobile.entity.User;

@Service("mobileUserManager")
public class MobileUserManagerImpl {

	@Autowired
	private MobileUserDao mobileUserDao;
	
	@Autowired
	private FileEngine fileEngine;
	
	/**
	 * 返回用户扩展信息记录
	 * @param userId
	 * @return
	 */
	public User queryUser(String userId){
		return mobileUserDao.get(userId);
	}
	
	/**
	 * 更新用户头像信息，存在就更新photo字段，不存在就新建该记录
	 * @param userId
	 * @param files 上传的头像，集合中应当只有一个
	 */
	public void execUpdatePhotoId(String userId, List<FileMeta> files){
		String photoId = null;
		if(files != null){
			try {
//				files.get(0).setGroup("test");
				photoId = fileEngine.writeFiles(files).get(0);
			} catch (FileOperateException e) {
				throw new ApplicationRuntimeException("file write failed in execSaveUserExtInfo!", e);
			}
		}
		
		if(StringUtils.isNotEmpty(photoId)){
			mobileUserDao.execUpdatePhotoId(userId, photoId);
		}
	}
	
}
