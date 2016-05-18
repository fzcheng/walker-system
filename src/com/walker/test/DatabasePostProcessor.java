package com.walker.test;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.walker.db.init.DatabaseInitializeException;
import com.walker.infrastructure.core.NestedRuntimeException;

/**
 * 数据库处理各个模块SQL脚本的处理器实现
 * @author shikeying
 *
 */
public class DatabasePostProcessor implements BeanPostProcessor {
	
	private final transient Log logger = LogFactory.getLog(getClass());

	private DataSource dataSource = null;
	private List<DataTableCollectable> dtcList = new ArrayList<DataTableCollectable>(8);
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		// TODO Auto-generated method stub
		if(DataSource.class.isAssignableFrom(bean.getClass())){
			dataSource = (DataSource)bean;
			logger.debug("aquire dataSource: " + dataSource);
			
		} else if(DataTableCollectable.class.isAssignableFrom(bean.getClass())){
			dtcList.add((DataTableCollectable)bean);
			logger.debug("aquire a DataTableCollectable: " + bean);
			
		} else if(DatabaseDetector.class.isAssignableFrom(bean.getClass())){
			// 根据依赖关系的拦截配置，这个是最后初始化的，在这里设置SQL收集器
			int size = dtcList.size();
			if(size == 0){
				logger.warn("no DataTableCollectable config, can't init database.");
			} else {
				DatabaseDetector ddd = (DatabaseDetector)bean;
				ddd.setDataTableCollectors(dtcList);
				logger.info("aquired '" + size + "' DataTableCollectable.");
				try{
					logger.info("=============== starting init database table ================");
					ddd.detect();
				} catch(Exception ex){
					if(ex instanceof DatabaseInitializeException){
						throw new Error("init database failed: " + ex.getMessage(), ex);
					} else
						throw new NestedRuntimeException(ex.getMessage());
				}
			}
		}
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		// TODO Auto-generated method stub
		return bean;
	}

}
