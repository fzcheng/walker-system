package com.walkersoft.mobile.util;

import net.sf.json.JSONObject;

import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.mobile.Callback;

public abstract class JsonUtils {

	public static String getErrorJson(String error){
		JSONObject json = new JSONObject();
		json.put(Callback.PARAM_CODE, false);
		json.put(Callback.PARAM_MESSAGE, error == null ? StringUtils.EMPTY_STRING : error);
		json.put(Callback.PARAM_DATA, StringUtils.EMPTY_STRING);
		return json.toString();
	}
	
	public static String getSuccessJson(JSONObject data){
		JSONObject json = new JSONObject();
		json.put(Callback.PARAM_CODE, true);
		json.put(Callback.PARAM_MESSAGE, Callback.MESSAGE_SUCCESS);
		json.put(Callback.PARAM_DATA, data == null ? StringUtils.EMPTY_STRING : data.toString());
		return json.toString();
	}
	
	private static final String SESSION_CODE = "604204228";
	
	/**
	 * 根据userId生成sessionId
	 * @param userId
	 * @return
	 */
	public static final String getLoginSessionId(String userId){
		Assert.hasText(userId);
		return userId + SESSION_CODE;
	}
	
	/**
	 * 根据sessionId获得userId
	 * @param sessionId
	 * @return
	 */
	public static final String getUserId(String sessionId){
		Assert.hasText(sessionId);
		int size = sessionId.length();
		if(size <= 9){
			throw new IllegalArgumentException();
		}
		return sessionId.substring(0, (size-9));
	}
}
