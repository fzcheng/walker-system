package com.walkersoft.mobile.client;


/**
 * 响应客户端的对象接口规范定义。</p>
 * 客户端接收到的json字符串最终会转换为该对象的具体实现，<br>
 * 业务中直接调用该对象操作，提高效率。
 * @author shikeying
 * @date 2015-2-11
 *
 */
public interface ResponseData<T> {

	/**
	 * 返回调用结果状态，成功为<code>true</code>
	 * @return
	 */
	boolean getResultStatus();
	
	/**
	 * 返回调用结果描述
	 * @return
	 */
	String getResultMessage();
	
	/**
	 * 把响应的Json字符串转换成结果对象。
	 * @param jsonObject
	 * @return
	 */
	void toObjectFromJson(String jsonObject);
	
	/**
	 * 获得最终使用的业务对象，就是json中data的属性。</p>
	 * Json格式如下：
	 * <pre>
	 * {
     * "code":"true",
     * "message":"success",
     * "data":{"score":"60"}
     * }
     * 或者
     * {
    "code":"true",
    "message":"success",
    "data":
    {
      "datas": 
      [
        {
	  "id":"1",
	  "name":"下列哪种语言支持跨平台",
	  "type":"0", // 试题类型：0_单选，1_多选，2_判断
	  "answer":"A",
	  "options":
	  [
	    {"name":"A", "content":"JAVA"}, // 都是String
	    {"name":"B", "content":"C++"},
	    {"name":"C", "content":"Delphi"}
	  ]
	},
	{
	  "id":"2",              // long
	  "name":"月亮是恒星么", // String
	  "type":"2", // 试题类型：0_单选，1_多选，2_判断 // int
	  "answer":"B",          // String
	  "options":[]
	}
      ]
    }
  }
	 * </pre>
	 * @param dataJson
	 * @return
	 */
	T getResultData();
	
	public static final String KEY_CODE = "code";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_DATA = "data";
	public static final String KEY_DATAS = "datas";
}
