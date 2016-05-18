package com.walker.websocket.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.walker.websocket.Action;

/**
 * 处理websocket系统中action的后处理器默认实现。
 * @author shikeying
 * @date 2014-12-18
 *
 */
public class ActionAcquiredPostProcessor implements BeanPostProcessor {
	
	protected transient final Log logger = LogFactory.getLog(getClass());

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanId)
			throws BeansException {
		if(Action.class.isAssignableFrom(bean.getClass())){
			ActionContext.addAction((Action)bean);
			logger.debug(".........加载到一个websocket action: " + beanId);
		}
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String arg1)
			throws BeansException {
		return bean;
	}

}
