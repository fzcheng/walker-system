package com.walker.websocket.request;

import net.sf.json.JSONObject;

import com.walker.infrastructure.core.filter.ConvertDataException;
import com.walker.infrastructure.core.filter.Convertable;
import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.StringUtils;
import com.walker.websocket.Request;

/**
 * 把请求字符串转换成对象的转换器实现。
 * @author shikeying
 * @date 2014-12-18
 *
 */
public class RequestToJsonConvertor implements Convertable {

	private static final String ATTR_URL = "url";
	private static final String ATTR_SECURITY = "security";
	private static final String ATTR_TYPE = "type";
	private static final String ATTR_FROM = "from";
	private static final String ATTR_TO = "to";
	private static final String ATTR_CONTENT = "content";
	
	/**
	 * 把请求对象转换成json字符串
	 */
	@Override
	public Object convertFrom(Object request) throws ConvertDataException {
		throw new UnsupportedOperationException();
	}

	/**
	 * 把json字符串转换成Request对象。</p>
	 * json格式：
	 * <pre>
	 * {url:"url", security:"", type:"2", from:"user1", to:"", content:"test..."}
	 * </pre>
	 */
	@Override
	public Object convertTo(Object string) throws ConvertDataException {
		Assert.notNull(string);
		if(string instanceof String){
			if(StringUtils.isEmpty(string.toString())){
				throw new IllegalArgumentException();
			}
			JSONObject data = JSONObject.fromObject(string);
			if(StringUtils.isEmpty(data.getString(ATTR_URL))){
				throw new ConvertDataException("请求的URL不存在");
			}
			WrappedRequest request = new WrappedRequest();
			request.setUrl(data.getString(ATTR_URL));
			request.setSecurity(data.getString(ATTR_SECURITY));
			request.setType(data.getInt(ATTR_TYPE));
			request.setFrom(data.getString(ATTR_FROM));
			request.setTo(data.getString(ATTR_TO));
			request.setContent(data.getString(ATTR_CONTENT));
			return request;
		}
		throw new ConvertDataException("输入必须是字符串类型：" + string);
	}

	@Override
	public Class<?> getDestType() {
		return Request.class;
	}

	@Override
	public Class<?> getSourceType() {
		return String.class;
	}

}
