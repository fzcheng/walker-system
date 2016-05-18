package com.walkersoft.mobile.client.simp;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.walkersoft.mobile.client.UserDetail;

public class DefaultUserDetail implements UserDetail {

	/**
	 * 
	 */
	private static final long serialVersionUID = 45933205828292716L;
	
	private Map<String, String> attributes = new HashMap<String, String>(8);
	
	private String userId;
	
	private String loginName;
	
	private String name;
	
	private String photoId;
	
	private int sex = 1;
	
	private String birthday;
	
	private String mobile;
	
	private String bindMail;
	
	private String personSay;
	
	public DefaultUserDetail(){}

//	public DefaultUserDetail(Map<String, String> attributes){
//		Assert.notNull(attributes);
//		this.attributes = attributes;
//	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setBindMail(String bindMail) {
		this.bindMail = bindMail;
	}

	public void setPersonSay(String personSay) {
		this.personSay = personSay;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	@Override
	public String getUserId() {
//		String userId = attributes.get(ATTR_USER_ID);
//		if(userId == null){
//			throw new IllegalArgumentException();
//		}
		return userId;
	}

	@Override
	public String getLoginName() {
//		String loginName = attributes.get(ATTR_LOGIN_NAME);
//		if(loginName == null){
//			throw new IllegalArgumentException();
//		}
		return loginName;
	}

	@Override
	public String getName() {
//		return attributes.get(ATTR_NAME);
		return name;
	}

	@Override
	public String getPhotoId() {
//		return attributes.get(ATTR_PHOTO_ID);
		return this.photoId;
	}

	@Override
	public int getSex() {
//		String sex = attributes.get(ATTR_SEX);
//		if(sex != null){
//			return Integer.parseInt(sex);
//		}
//		return -1;
		return sex;
	}

	@Override
	public String getBirthday() {
//		return attributes.get(ATTR_BIRTHDAY);
		return this.birthday;
	}

	@Override
	public String getMobile() {
//		return attributes.get(ATTR_MOBILE);
		return this.mobile;
	}

	@Override
	public String getPersonSay() {
//		return attributes.get(ATTR_PERSON_SAY);
		return this.personSay;
	}

	@Override
	public String getBindEmail() {
//		return attributes.get(ATTR_BIND_EMAIL);
		return this.bindMail;
	}

	@Override
	public String getAttribute(String key) {
		return attributes.get(key);
	}

	@Override
	public String toString(){
		return JSONObject.toJSONString(this);
	}
}
