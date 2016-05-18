package com.walkersoft.application;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.walker.app.ApplicationCallback;
import com.walker.app.ApplicationRuntimeException;
import com.walker.infrastructure.utils.Assert;
import com.walkersoft.application.security.SystemSecurityCallback;
import com.walkersoft.system.callback.SystemUserCallback;

/**
 * 应用程序后处理器 - 回调接口的处理，启动扫描所有配置的实现类。</p>
 * 同时可以使用该对象的静态方法获取某个bean。
 * @author shikeying
 * @date 2014-11-25
 *
 */
public class ApplicationCallbackPostProcessor implements BeanPostProcessor {

	private final transient Log logger = LogFactory.getLog(getClass());
	
	private static final Map<Class<?>, Object> callbackReference = new java.util.HashMap<Class<?>, Object>(4);
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		if(ApplicationCallback.class.isAssignableFrom(bean.getClass())){
			Object existBean = null;
			if(SystemUserCallback.class.isAssignableFrom(bean.getClass())){
				logger.info("...... 找到一个用户管理回调实现：" + bean.getClass().getName());
				existBean = callbackReference.get(SystemUserCallback.class);
				if(existBean != null){
					throw new ApplicationRuntimeException("...... 回调实现类已经加载，不能重复配置: " + existBean.getClass().getName());
				}
				callbackReference.put(SystemUserCallback.class, bean);
			} else if(SystemSecurityCallback.class.isAssignableFrom(bean.getClass())){
				logger.info("...... 找到一个安全回调接口实现: " + bean.getClass().getName());
				existBean = callbackReference.get(SystemSecurityCallback.class);
				if(existBean != null){
					throw new ApplicationRuntimeException("...... 回调实现类已经加载，不能重复配置: " + existBean.getClass().getName());
				}
				callbackReference.put(SystemSecurityCallback.class, bean);
			}
			
//			String beanClass = bean.getClass().getName();
//			if(!callbackReference.containsKey(beanClass)){
//				logger.debug("...... 加载了一个回调接口类：" + beanClass);
//				callbackReference.put(beanClass, bean);
//			} else
//				logger.debug("...... 回调实现类已经加载，不能重复配置: " + beanClass);
		}
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	/**
	 * 返回给定的回调实现bean，如果不存在返回<code>null</code>
	 * @param callbackClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T getCallbackObject(Class<T> callbackClass){
		Assert.notNull(callbackClass);
		Object bean = callbackReference.get(callbackClass);
		if(bean != null){
			return (T)bean;
		}
		return null;
	}
}
