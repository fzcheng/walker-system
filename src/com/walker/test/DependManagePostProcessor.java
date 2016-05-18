package com.walker.test;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * 处理系统定义BEAN依赖关系的后处理器实现。
 * @author shikeying
 * @date 2014-6-5
 *
 */
public class DependManagePostProcessor implements BeanFactoryPostProcessor {

	protected final transient Log logger = LogFactory.getLog(getClass());
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
			throws BeansException {
		// TODO Auto-generated method stub
		doCheckDatabaseDependon(beanFactory);
	}

	/**
	 * 检查并设置数据库表结构初始化需要的依赖关系
	 * @param beanFactory
	 */
	private void doCheckDatabaseDependon(final ConfigurableListableBeanFactory beanFactory){
		String[] dsNames = beanFactory.getBeanNamesForType(DataSource.class);
		if(dsNames == null || dsNames.length == 0){
			logger.warn("no dataSource bean defined in applicationContext, can't depend on DataSource.");
			return;
		}
		if(dsNames.length > 1){
			logger.warn("发现多个数据源(DataSource)定义，请手动配置依赖，确定依赖哪个数据源!");
			return;
		}
		if(dsNames.length == 1){
			String dataSourceName = dsNames[0];
			String[] dtcNames = beanFactory.getBeanNamesForType(DataTableCollectable.class);
			
			List<String> denpendBeans = new ArrayList<String>();
			denpendBeans.add(dataSourceName);
			
			if(dtcNames != null){
				for(String dtc: dtcNames){
					denpendBeans.add(dtc);
				}
			}
			
			String[] databaseDetectorNames = beanFactory.getBeanNamesForType(DatabaseDetector.class);
			if(databaseDetectorNames == null){
				logger.debug("......... not found databaseDetectorNames!");
				return;
			}
			logger.info("find databaseDetectorName: " + databaseDetectorNames[0]);
			try{
				BeanDefinition databaseDetectorBean = beanFactory.getBeanDefinition(databaseDetectorNames[0]);
				databaseDetectorBean.setDependsOn(denpendBeans.toArray(new String[0]));
			} catch(NoSuchBeanDefinitionException ex){
				logger.warn("no bean defined: databaseDetector.");
			}
		}
	}
}
