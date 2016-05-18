package com.walkersoft.mobile.client.pojo;

import com.alibaba.fastjson.JSONObject;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.mobile.client.result.ResultLogin;
import com.walkersoft.mobile.client.simp.AbstractResponseData;
import com.walkersoft.mobile.client.simp.DefaultUserDetail;

/**
 * 响应登录结果的对象定义
 * @author shikeying
 * @date 2015-2-11
 *
 */
public class ResponseLogin extends AbstractResponseData<ResultLogin> {

	@Override
	protected ResultLogin TranslateToObject(JSONObject dataJson) {
		DefaultUserDetail userDetail = new DefaultUserDetail();
		userDetail.setUserId(dataJson.getString("userId"));
		userDetail.setSex(dataJson.getIntValue("sex"));
		userDetail.setPersonSay(dataJson.getString("personSay"));
		userDetail.setName(dataJson.getString("userName"));
		userDetail.setMobile(dataJson.getString("mobile"));
		userDetail.setLoginName(dataJson.getString("loginName"));
		userDetail.setBirthday(StringUtils.EMPTY_STRING);
		userDetail.setBindMail(dataJson.getString("email"));
		
		return new ResultLogin().setUserId(dataJson.getString("userId"))
				.setUserName(dataJson.getString("userName"))
				.setSession(dataJson.getString("m_session"))
				.setUserDetail(userDetail);
	}

}
