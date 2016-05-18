package com.walkersoft.mobile.client.pojo;

import com.alibaba.fastjson.JSONObject;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.mobile.client.JsonTransfer;
import com.walkersoft.mobile.client.simp.SessionRequestData;

/**
 * 修改（重置）密码请求对象定义。</p>
 * 使用JSON输入参数，可以对请求数据加密，服务端再统一解密。
 * @author shikeying
 * @date 2015-3-6
 *
 */
public class RequestResetPass extends SessionRequestData implements
		JsonTransfer {

	private String password;
	
	private String loginId; // 用户登录ID，密码加密作为盐值使用
	
	public String getPassword() {
		return password;
	}

	public RequestResetPass setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getLoginId() {
		return loginId;
	}

	public RequestResetPass setLoginId(String loginId) {
		this.loginId = loginId;
		return this;
	}

	@Override
	public String getRequestMethod() {
		return "m/p.do?method=resetPassword";
	}

	@Override
	public String toJsonString() {
		String session = this.getSessionId();
		if(StringUtils.isEmpty(session)){
			throw new IllegalArgumentException("session没有被赋值：" + this.getClass().getName());
		}
		JSONObject json = new JSONObject();
		json.put(SessionRequestData.SESSION_NAME, this.getSessionId());
		json.put("password", this.password);
		json.put("loginId", this.loginId);
		return json.toString();
	}

}
