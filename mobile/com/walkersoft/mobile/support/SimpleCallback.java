package com.walkersoft.mobile.support;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.mobile.AbstractCallback;

/**
 * 简单的回掉实现，目前使用测试用户模拟登陆
 * @author shikeying
 * @date 2015-1-14
 *
 */
public abstract class SimpleCallback extends AbstractCallback {

//	@Override
//	protected UserCoreEntity getUserInfo(String session) {
//		// 测试学员：4028bc034adca349014adca51d3d0002
//		UserCoreEntity user = new UserCoreEntity();
//		user.setId("4028bc034adca349014adca51d3d0002");
//		user.setName("学员1");
//		return user;
//	}

	/**
	 * 返回业务数据中的json数据，格式如下：
	 * <pre>
	 * { "datas":
	 *   [jsonArray...]
	 * }
	 * </pre>
	 * @param jsonArray
	 * @return
	 */
	protected JSONObject getDatasForJson(JSONArray jsonArray){
		JSONObject data = new JSONObject();
		if(jsonArray != null){
			data.put("datas", jsonArray);
		} else
			data.put("datas", StringUtils.EMPTY_STRING);
		return data;
	}
	
}
