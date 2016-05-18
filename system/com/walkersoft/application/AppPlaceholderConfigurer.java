package com.walkersoft.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import com.walker.app.ApplicationRuntimeException;
import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.JarDeployer;
import com.walker.infrastructure.utils.StringUtils;

/**
 * walkersoft应用程序属性文件加载器，分布在不同jar包中的属性文件自动依据命名规则加载。<br>
 * 在模块打包jar后，每个包中可能会有属性文件，这些文件以app_前缀命名，系统默认自动加载。<br>
 * 通过spring的配置无法达到自动加载不同jar中属性文件的目的，因此需要重写加载方法。
 * @author shikeying
 * @date 2014-11-11
 *
 */
public class AppPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer {

//	/* 部署的jar前缀 */
//	private String deployJarPrefix = null;
	
	/**
	 * 设置部署jar包的文件名前缀，默认为:walkersoft-resource-
	 * @param deployJarPrefix
	 */
	public void setDeployJarPrefix(String deployJarPrefix) {
		if(StringUtils.isNotEmpty(deployJarPrefix)){
			JarDeployer.DEPLOY_JAR_PREFIX = deployJarPrefix;
		}
	}
	
	/**
	 * 设置加载配置文件的前缀名。<br>
	 * 系统可以根据此前缀，搜索所有lib包中（jar）的properties配置文件。
	 * @param prefix 如：app_
	 */
	public void setLocationPrefix(String prefix) throws Exception{
//		locations = (new Resource[] { location });
//		localProperties = (new Properties[] { properties });
		Assert.hasText(prefix);
		
		List<Properties> allProperties = new ArrayList<Properties>(8);
		
		// 先加载classpath下的所有properties文件
		File classpathFolder = new File(JarDeployer.classpathAbsolute);
		File[] files = classpathFolder.listFiles();
		if(files != null && files.length > 0){
			for(File f : files){
				if(f.isDirectory()) continue;
				if(f.getName().startsWith(prefix)){
					Properties prop = new Properties();
					prop.load(new ClassPathResource(f.getName()).getInputStream());
					allProperties.add(prop);
				}
			}
		}
		
		// 添加系统模块jar包中的properties文件
		List<File> availableJars = JarDeployer.getMatchDeployedJars(JarDeployer.DEPLOY_JAR_PREFIX);
		if(!StringUtils.isEmptyList(availableJars)){
			List<Properties> tempList = null;
			for(File jarFile : availableJars){
				// jar包中，带有resource-的是模板文件包，不再处理
				if(jarFile.getName().indexOf("resource-") >= 0){
					continue;
				}
				tempList = JarDeployer.getJarRootResources(jarFile.getAbsolutePath(), prefix);
				if(!StringUtils.isEmptyList(tempList)){
					allProperties.addAll(tempList);
				}
			}
			if(localProperties == null){
				localProperties = allProperties.toArray(new Properties[0]);
				logger.debug("setLocationPrefix(): localProperties还没有数据，直接设置。");
			} else {
				Properties[] src = allProperties.toArray(new Properties[0]);
//				logger.debug("src.length: " + src.length + ", localProperties.length: " + localProperties.length);
				copyProperties(src);
				logger.debug("setLocationPrefix(): localProperties已经存在，追加数据。");
			}
		}
	}
	
	/**
	 * 设置classpath中的属性文件，在部署时只会有一个app_config.properties;<br>
	 * 但在开发模式中，需要仍然把system/flow等包中属性文件包含进来。
	 * @param configFiles
	 */
	@Deprecated
	public void setClasspathConfigList(List<Object> configFiles){
		if(!StringUtils.isEmptyList(configFiles)){
			List<Properties> list = new ArrayList<Properties>(2);
			ClassPathResource resource = null;
			try{
				for(Object file : configFiles){
					resource = new ClassPathResource(file.toString());
					if(resource != null){
						Properties prop = new Properties();
						prop.load(resource.getInputStream());
						list.add(prop);
					}
				}
			} catch(IOException ex){
				throw new ApplicationRuntimeException("", ex);
			}
			
			if(localProperties == null){
				localProperties = list.toArray(new Properties[0]);
				logger.debug("setClasspathConfigList(): localProperties还没有数据，直接设置。");
			} else {
				Properties[] src = list.toArray(new Properties[0]);
//				System.arraycopy(src, 0, localProperties, 0, src.length);
				copyProperties(src);
				logger.debug("setClasspathConfigList(): localProperties已经存在，追加数据。");
			}
		}
	}
	
	private void copyProperties(Properties[] src){
		if(src.length > 0){
			Properties[] result = new Properties[src.length + localProperties.length];
			int i = 0;
			for(Properties p : src){
				result[i] = p;
				i++;
			}
			for(Properties p : localProperties){
				result[i] = p;
				i++;
			}
			localProperties = result;
		}
	}
}
