package com.walkersoft.mobile.client.simp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.mobile.client.ResponseData;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;

/**
 * 抽象的响应数据转换标准，把返回的json字符串转换成用户特定对象。</p>
 * 该类由APP客户端使用，因此里面<code>JSON</code>库必须与客户端一致，使用阿里库
 * @author shikeying
 *
 * @param <T>
 */
public abstract class AbstractResponseData<T> implements ResponseData<T> {

	protected final transient Log logger = LogFactory.getLog(getClass());
	
	private boolean resultStatus = false;
	
	private String resultMessage = null;
	
	private T data = null;
	
	@Override
	public void toObjectFromJson(String jsonObject) {
		if(StringUtils.isEmpty(jsonObject)){
			throw new IllegalArgumentException();
		}
		JSONObject jsonObj = JSONObject.parseObject(jsonObject);
		resultStatus = jsonObj.getBoolean(KEY_CODE);
		resultMessage = jsonObj.getString(KEY_MESSAGE);
		if(resultStatus){
			String dataContent = jsonObj.getString(KEY_DATA);
			// 因为可能data属性是空的，所以要加上以上的判断
			if(StringUtils.isNotEmpty(dataContent)){
				JSONObject dataJson = jsonObj.getJSONObject(KEY_DATA);
				logger.debug("...........responseData = " + dataJson);
				if(dataJson != null){
					if(dataJson.containsKey(KEY_DATAS)){
						// 包含集合数据datas
						String dataList = dataJson.getString(KEY_DATAS);
						if(StringUtils.isNotEmpty(dataList)){
							data = translateToObject(dataJson.getJSONArray(KEY_DATAS));
						}
					} else {
						data = TranslateToObject(dataJson);
					}
				}
			}
		}
	}

	@Override
	public boolean getResultStatus() {
		return this.resultStatus;
	}

	@Override
	public String getResultMessage() {
		return this.resultMessage;
	}

	@Override
	public T getResultData() {
		return data;
	}

	/**
	 * 把解析过的实际业务数据转换成用户业务对象。
	 * @param dataJson
	 * @return
	 */
	protected abstract T TranslateToObject(JSONObject dataJson);
	
	/**
	 * 转换成业务对象，与TranslateToObject不同的是输入参数，<br>
	 * 因为兼容原因，所以该方法没有设置为抽象方法。
	 * @param jsonArray
	 * @return
	 */
	protected T translateToObject(JSONArray jsonArray){
		return null;
	}
}
