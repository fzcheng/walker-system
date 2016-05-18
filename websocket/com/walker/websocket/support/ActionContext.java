package com.walker.websocket.support;

import java.util.HashMap;
import java.util.Map;

import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.StringUtils;
import com.walker.websocket.Action;
import com.walker.websocket.ActionNotFoundException;

/**
 * websocket业务调用Action上下文对象定义。
 * @author shikeying
 * @date 2014-12-18
 *
 */
public abstract class ActionContext {

	private static final Map<String, Action> actionMap = new HashMap<String, Action>(8);
	
	public static final void addAction(Action action){
		Assert.notNull(action);
		String url = action.matchUrl();
		if(StringUtils.isEmpty(url)){
			throw new IllegalArgumentException("Action未实现matchUrl方法。");
		}
		if(actionMap.get(url) != null){
			throw new IllegalArgumentException("Action已经存在，不能重复加载: " + url);
		}
		actionMap.put(url, action);
	}
	
	/**
	 * 从系统中返回业务<code>Action</code>对象实例。
	 * @param url 请求的URL（唯一ID）
	 * @return
	 * @throws ActionNotFoundException 如果没有找到抛出该异常。
	 */
	public static final Action getAction(String url) throws ActionNotFoundException{
		Assert.hasText(url);
		Action act = actionMap.get(url);
		if(act == null){
			throw new ActionNotFoundException("action not found: " + url);
		}
		return act;
	}
}
