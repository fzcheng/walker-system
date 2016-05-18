package com.walkersoft.mobile.client.pojo;

import java.util.HashMap;
import java.util.Map;

import com.walkersoft.mobile.client.MapTransfer;
import com.walkersoft.mobile.client.RequestData;

/**
 * 登录请求对象
 * @author shikeying
 * @date 2015-2-11
 *
 */
public class RequestLogin implements RequestData, MapTransfer {

	private String loginName;
	
	private String password;
	
	public String getLoginName() {
		return loginName;
	}

	public RequestLogin setLoginName(String loginName) {
		this.loginName = loginName;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public RequestLogin setPassword(String password) {
		this.password = password;
		return this;
	}

//	@Override
//	public String toJsonString() {
//		JSONObject json = new JSONObject();
//		json.put("loginId", this.loginName);
//		json.put("password", this.password);
//		return json.toJSONString();
//	}

	@Override
	public String getRequestMethod() {
		return "m/login.do";
	}

	@Override
	public Map<String, String> toMap() {
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("loginId", this.loginName);
		params.put("password", this.password);
		return params;
	}

}
