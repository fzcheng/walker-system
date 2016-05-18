package com.walker.websocket.response;

import net.sf.json.JSONObject;

import com.walker.infrastructure.core.filter.ConvertDataException;
import com.walker.infrastructure.core.filter.Convertable;
import com.walker.infrastructure.utils.Assert;
import com.walker.websocket.Response;

/**
 * 把Response对象转换成JSON字符串的实现。
 * @author shikeying
 * @date 2014-12-18
 *
 */
public class ResponseToJsonConvertor implements Convertable {

	private static final String ATTR_CODE = "code";
	private static final String ATTR_SUMMARY = "summary";
	private static final String ATTR_CONTENT = "content";
	
	@Override
	public Object convertFrom(Object string) throws ConvertDataException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object convertTo(Object response) throws ConvertDataException {
		Assert.notNull(response);
		if(Response.class.isAssignableFrom(response.getClass())){
			Response responseObj = (Response)response;
			JSONObject json = new JSONObject();
			json.put(ATTR_CODE, responseObj.getCode());
			json.put(ATTR_SUMMARY, responseObj.getSummary());
			json.put(ATTR_CONTENT, responseObj.getContent());
			return json.toString();
		}
		throw new ConvertDataException("response必须是Response对象");
	}

	@Override
	public Class<?> getDestType() {
		return String.class;
	}

	@Override
	public Class<?> getSourceType() {
		return Response.class;
	}

}
