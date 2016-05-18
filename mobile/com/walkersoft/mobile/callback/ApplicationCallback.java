package com.walkersoft.mobile.callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.walker.app.ApplicationRuntimeException;
import com.walker.file.FileEngine.StoreType;
import com.walker.file.FileMeta;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.MyApplicationConfig;
import com.walkersoft.application.cache.UserCoreCacheProvider;
import com.walkersoft.mobile.InvokeException;
import com.walkersoft.mobile.manager.MobileUserManagerImpl;
import com.walkersoft.mobile.support.SimpleCallback;
import com.walkersoft.system.entity.UserCoreEntity;
import com.walkersoft.system.manager.UserManagerImpl;

/**
 * 应用程序公用的调用接口，不涉及业务。<br>
 * 这些接口是在用户登录之后使用，必须带有<code>sessionId</code>参数
 * @author shikeying
 * @date 2015-3-6
 *
 */
@Component
public class ApplicationCallback extends SimpleCallback {

	@Autowired
	protected UserManagerImpl userManager;
	
	@Autowired
	protected UserCoreCacheProvider userCacheProvider;
	
	@Autowired
	private MobileUserManagerImpl mobileUserManager;
	
	@Override
	protected List<String> getMethodList() {
		List<String> list = new ArrayList<String>();
		list.add("resetPassword");
		list.add("uploadPhoto");
//		list.add("getUserPhoto");
		return list;
	}

	@Override
	protected JSONObject doInvokeData(String method, String userId)
			throws Exception {
//		throw new UnsupportedOperationException();
		JSONObject data = null;
		if(method.equals("uploadPhoto")){
			this.saveUserExtInfo(userId);
		}
		return data;
	}

	@Override
	protected JSONObject doInvokeData(String method, String userId,
			JSONObject parameters) throws Exception {
		JSONObject data = null;
		if(method.equals("resetPassword")){
			this.resetPassword(userId
					, parameters.getString("password")
					, parameters.getString("loginId"));
		} else {}
		return data;
	}

	/**
	 * 修改（重置）用户密码：目前仅需要设置新密码，没有验证码
	 */
	private void resetPassword(String userId
			, String password, String loginId) throws InvokeException{
		if(StringUtils.isEmpty(password) || StringUtils.isEmpty(loginId)){
			throw new InvokeException("缺少参数：password或loginId");
		}
		UserCoreEntity user = userCacheProvider.getCacheData(userId);
		if(user == null){
			// 也可以在此重新查询数据库，获取user信息
			throw new ApplicationRuntimeException("user not found in cache, user: " + userId);
		}
		String encodedPass = MyApplicationConfig.encodePassword(password, loginId);
		userManager.execEditPassword(userId, encodedPass);
		/* 更新缓存 */
		user.setPassword(encodedPass);
	}
	
	/**
	 * 修改更新用户个人扩展（手机端）信息，包括：头像、签名等
	 * @param userId
	 * @throws InvokeException
	 */
	private void saveUserExtInfo(String userId) throws InvokeException{
		List<FileMeta> files = null;
		try {
			files = this.getUploadFiles("file", StoreType.FileSystem);
		} catch (IOException e) {
			throw new InvokeException("接收上传用户头像异常", e);
		}
		
		if(files != null){
			mobileUserManager.execUpdatePhotoId(userId, files);
			logger.debug(".........更新了用户头像: " + userId);
		}
	}
	
//	private void getUserPhoto(String userId) throws InvokeException{
//		Assert.hasText(userId);
//		User user = mobileUserManager.queryUser(userId);
//		if(user != null && StringUtils.isNotEmpty(user.getPhoto())){
//			logger.debug("获取用户头像照片：" + user.getPhoto());
//			this.ajaxOutputImage(user.getPhoto());
//		} else {
//			logger.debug("用户没有上传过头像，无法加载");
//		}
//	}
}
