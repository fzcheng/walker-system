package com.walkersoft.mobile.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.walkersoft.mobile.Callback;
import com.walkersoft.mobile.CallbackManager;

/**
 * 搜寻用户配置的手机调用回掉实现（手机业务处理ACTION）实例
 * @author shikeying
 * @date 2015-1-14
 *
 */
public class CallbackSearchPostProcessor implements BeanPostProcessor {

	private final transient Log logger = LogFactory.getLog(getClass());
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		if(Callback.class.isAssignableFrom(bean.getClass())){
			CallbackManager.addCallback((Callback)bean);
			logger.debug("注册缓存对象: " + beanName);
		}
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

}
