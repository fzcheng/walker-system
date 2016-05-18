package com.walkersoft.mobile.client;

/**
 * 请求对象接口定义。</p>
 * 接口方法调用过程中，使用对象来定义，提高代码编写效率。<br>
 * 该接口定义了远程访问请求所需要的操作规范。
 * @author shikeying
 * @date 2015-2-11
 *
 */
public interface RequestData {

	/**
	 * 返回本次远程调用的方法名称（或局部URL），如：/request.do?method=test
	 * @return
	 */
	String getRequestMethod();
	
}
