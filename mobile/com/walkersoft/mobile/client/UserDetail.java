package com.walkersoft.mobile.client;

import java.io.Serializable;

/**
 * 用户信息接口定义
 * @author shikeying
 * @date 2015-3-20
 *
 */
public interface UserDetail extends Serializable {

	/**
	 * 返回用户在服务端的ID
	 * @return
	 */
	String getUserId();
	
	/**
	 * 返回用户的登录账号
	 * @return
	 */
	String getLoginName();
	
	/**
	 * 返回用户姓名，如果没有返回<code>null</code>
	 * @return
	 */
	String getName();
	
	/**
	 * 返回用户头像照片的ID，此ID为服务端的资源ID
	 * @return
	 */
	String getPhotoId();
	
	/**
	 * 性别：0_女士，1_男士，-1_未设置
	 * @return
	 */
	int getSex();
	
	/**
	 * 返回用户出生日期，如果没有返回<code>null</code>
	 * @return
	 */
	String getBirthday();
	
	/**
	 * 返回用户手机号，如果没有返回<code>null</code>
	 * @return
	 */
	String getMobile();
	
	/**
	 * 返回用户签名，如果没有返回<code>null</code>
	 * @return
	 */
	String getPersonSay();
	
	/**
	 * 返回用户绑定的邮箱地址，如果没有返回<code>null</code>
	 * @return
	 */
	String getBindEmail();
	
	/**
	 * 返回用户特定属性值，不同业务可以自定义
	 * @param key
	 * @return
	 */
	String getAttribute(String key);
	
	public static final String ATTR_USER_ID = "user_id";
	public static final String ATTR_LOGIN_NAME = "login_name";
	public static final String ATTR_NAME = "name";
	public static final String ATTR_PHOTO_ID = "photo_id";
	public static final String ATTR_SEX = "sex";
	public static final String ATTR_BIRTHDAY = "birthday";
	public static final String ATTR_MOBILE = "mobile";
	public static final String ATTR_PERSON_SAY = "person_say";
	public static final String ATTR_BIND_EMAIL = "bind_email";
}
