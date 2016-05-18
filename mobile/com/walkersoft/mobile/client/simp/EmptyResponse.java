package com.walkersoft.mobile.client.simp;

import com.alibaba.fastjson.JSONObject;

/**
 * 返回空业务数据的响应对象定义。</p>
 * 在很多业务中服务端仅返回成功标识，并不返回特定业务，如：修改密码。
 * @author shikeying
 * @date 2015-3-6
 *
 */
public class EmptyResponse extends AbstractResponseData<String> {

	@Override
	protected String TranslateToObject(JSONObject dataJson) {
		return null;
	}

}
