package com.walkersoft.mobile.action;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.cache.UserCoreCacheProvider;
import com.walkersoft.application.util.DepartmentUtils;
import com.walkersoft.mobile.Callback;
import com.walkersoft.mobile.CallbackManager;
import com.walkersoft.mobile.NoMethodException;
import com.walkersoft.mobile.entity.User;
import com.walkersoft.mobile.manager.LoginManagerImpl;
import com.walkersoft.mobile.manager.MobileUserManagerImpl;
import com.walkersoft.mobile.util.JsonUtils;
import com.walkersoft.system.SystemAction;
import com.walkersoft.system.entity.UserCoreEntity;

/**
 * 手机端请求分发起，是总控制器，任何业务都由该控制器转发。
 * @author shikeying
 * @date 2015-1-14
 *
 */
@Controller
public class DispatchAction extends SystemAction {

	private final transient Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	protected UserCoreCacheProvider userCacheProvider;
	
	@Autowired
	private LoginManagerImpl loginManager;
	
	@Autowired
	private MobileUserManagerImpl mobileUserManager;
	
	/* 缓存登陆过的用户信息，key = sessionId, value = loginId */
	private final Map<String, String> loginCachedData = new HashMap<String, String>();
	
	/**
	 * 手机端处理总入口地址
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("m/p")
	public void index(HttpServletResponse response) throws IOException{
		String jsonData = this.getParameter(Callback.PARAM_DATAS);
		String session = null;
		
		boolean jsonMode = false; // 是否使用json模式传输请求参数
		JSONObject parameters = null;
		
		if(StringUtils.isNotEmpty(jsonData)){
			logger.debug("接收参数: " + jsonData);
			// APP使用了json方式传递数据
			// 使用解码器解码参数内容，目前测试，先不解码
			
			parameters = JSONObject.fromObject(jsonData);
			session = parameters.getString(Callback.PARAM_SESSION);
			jsonMode = true;
			logger.info("...........使用了json提交模式: " + session);
		} else {
			// 使用普通表单提供参数
			session = this.getParameter(Callback.PARAM_SESSION);
		}
		
		if(StringUtils.isEmpty(session)){
			this.ajaxOutPutJson(JsonUtils.getErrorJson("接口调用失败，缺少session信息"));
			return;
		}
		
		if(loginCachedData.get(session) == null){
			this.ajaxOutPutJson(JsonUtils.getErrorJson("接口调用失败，未登陆或没有通过身份认证"));
			return;
		}
		
		// 获得用户ID
		String userId = JsonUtils.getUserId(session);
		
		Collection<Callback> callbacks = CallbackManager.getCallbackList();
		if(callbacks == null || callbacks.size() == 0){
			this.ajaxOutPutJson(JsonUtils.getErrorJson("未定义任何业务处理callback"));
			return;
		}
		
		for(Callback cb : callbacks){
			try {
				if(jsonMode){
					cb.invokeCallback(parameters, userId, response);
				} else {
					cb.invokeCallback(userId, response);
				}
				break;
			} catch (NoMethodException e) {
				logger.debug("未找到处理方法，查找下一个callback");
				continue;
			}
		}
	}
	
	@RequestMapping("m/login")
	public void login(String loginId, String password
			, HttpServletResponse response) throws IOException{
//		HttpServletRequest request = this.getRequest();
//		String loginId2 = request.getParameter("loginId");
//		logger.debug("............ " + loginId2);
		if(StringUtils.isEmpty(loginId) || StringUtils.isEmpty(password)){
			this.ajaxOutPutJson(JsonUtils.getErrorJson("缺少参数：用户名和密码"));
			return;
		}
		String sessionId = getCachedLoginSession(loginId);
		
		String userId = null;
		UserCoreEntity user = null;
		boolean existCacheSession = false; // 缓存中是否存在session
		
		if(StringUtils.isEmpty(sessionId)){
			// 缓存不存在，验证
			user = loginManager.queryUserByLoginId(loginId);
			if(user == null){
				this.ajaxOutPutJson(JsonUtils.getErrorJson("用户名或密码错误"));
				return;
			}
			userId = user.getId();
			
//			if(this.getEncodePassword(password, loginId).equals(user.getPassword())){
//				sessionId = JsonUtils.getLoginSessionId(userId);
//				// 更新数据库登陆信息
//				loginManager.execSaveLoginInfo(userId, sessionId);
//				// 登陆信息放入缓存
//				loginCachedData.put(sessionId, loginId);
//			} else {
//				// 返回错误提示
//				this.ajaxOutPutJson(JsonUtils.getErrorJson("用户名或密码错误"));
//				return;
//			}
		} else {
			// 存在缓存，直接通过验证
			userId = JsonUtils.getUserId(sessionId);
			user = DepartmentUtils.getUser(userId);
			existCacheSession = true;
		}
		
		if(!this.getEncodePassword(password, loginId).equals(user.getPassword())){
			// 返回错误提示
			this.ajaxOutPutJson(JsonUtils.getErrorJson("用户名或密码错误"));
			return;
		}
		
		// 登陆信息放入缓存
		if(!existCacheSession){
			sessionId = JsonUtils.getLoginSessionId(userId);
			loginCachedData.put(sessionId, loginId);
		}
		// 更新登陆记录
		loginManager.execSaveLoginInfo(userId, sessionId);
		
		// 给客户端返回用户信息
		this.ajaxOutPutJson(JsonUtils.getSuccessJson(getLoginJsonData(user, sessionId)));
	}
	
	private JSONObject getLoginJsonData(UserCoreEntity user, String sessionId){
		JSONObject json = new JSONObject();
		json.put("m_session", sessionId);
		json.put("userId", user.getId());
		json.put("userName", user.getName());
		json.put("loginName", user.getLoginId());
		json.put("mobile", user.getMobile());
		json.put("sex", user.getSex());
		json.put("email", user.getEmail());
		json.put("personSay", user.getPersonSay());
		return json;
	}
	
	private String getCachedLoginSession(String loginId){
		for(Map.Entry<String, String> entry : loginCachedData.entrySet()){
			if(entry.getValue().equals(loginId)){
				return entry.getKey();
			}
		}
		return null;
	}
	
	@RequestMapping("m/updater")
	public void checkUpdater(String appId, int appType, int appVersionCode
			, HttpServletResponse response) throws IOException{
		JSONObject d = new JSONObject();
		d.put("availableUpdate", true);
		d.put("forceUpdate", false);
		d.put("versionCode", 1);
		d.put("versionName", "1.0");
		d.put("summary", "本次测试更新");
		d.put("downloadUrl", "myapp"); // 使用固定文件ID测试
		d.put("fileSize", 1134592);
		d.put("filename", "myapp_update.apk");
		
		this.ajaxOutPutJson(JsonUtils.getSuccessJson(d));
	}
	
	@RequestMapping("m/down")
	public void downloadAppFile(String id
			, HttpServletResponse response) throws IOException{
		Assert.hasText(id);
		logger.info("用户更新下载APP: " + id);
		// 模拟一个更新文件
//		this.ajaxOutputFileStream(contentType, filePath);
		this.writeFileToBrownser(response);
	}
	
	@RequestMapping("m/my_photo")
	public void downloadUserPhoto(
			HttpServletResponse response) throws IOException{
		String sessionId = this.getParameter("m_session");
		if(StringUtils.isNotEmpty(sessionId)){
			User user = mobileUserManager.queryUser(JsonUtils.getUserId(sessionId));
			if(user != null && StringUtils.isNotEmpty(user.getPhoto())){
				logger.debug("获取用户头像照片：" + user.getPhoto());
				this.ajaxOutputImage(user.getPhoto());
			} else {
				logger.debug("用户没有上传过头像，无法加载");
			}
		}
	}
}
