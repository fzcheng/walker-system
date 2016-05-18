package com.walkersoft.mobile.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.infrastructure.utils.NumberGenerator;
import com.walkersoft.mobile.dao.LoginDao;
import com.walkersoft.mobile.entity.Login;
import com.walkersoft.mobile.entity.User;
import com.walkersoft.system.dao.UserDao;
import com.walkersoft.system.entity.UserCoreEntity;

@Service("loginManager")
public class LoginManagerImpl {

	@Autowired
	private LoginDao loginDao;
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 根据sessionID返回登陆记录
	 * @param sessionId
	 * @return
	 */
	public Login queryLoginInfo(String sessionId){
		return loginDao.get(sessionId);
	}
	
	public UserCoreEntity queryUserByLoginId(String loginId){
		UserCoreEntity user = userDao.getUserByLoginId(loginId);
		if(user != null){
			User mobileUser = userDao.get(User.class, user.getId());
			if(mobileUser != null){
				user.setPersonSay(mobileUser.getPersonSay());
			}
		}
		return user;
	}
	
	/**
	 * 保存（或更新）手机端登陆信息
	 * @param userId 用户ID
	 * @param sessionId 生成给过手机端的sessionID
	 * @return
	 */
	public Login execSaveLoginInfo(String userId, String sessionId){
		Login login = queryLoginInfo(sessionId);
		if(login == null){
			// 不存在登陆记录
			login = new Login();
			login.setId(sessionId);
			long time = NumberGenerator.getSequenceNumber();
			login.setCreateTime(time);
			login.setUpdateTime(time);
			login.setUserId(userId);
		} else {
			// 已存在更新
			login.setUpdateTime(NumberGenerator.getSequenceNumber());
			login.addOneTime();
		}
		loginDao.save(login);
		return login;
	}
}
