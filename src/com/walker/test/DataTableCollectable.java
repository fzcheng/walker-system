package com.walker.test;

import java.util.List;

/**
 * 数据库表结构收集器定义
 * @author shikeying
 * @date 2014-6-5
 *
 */
public interface DataTableCollectable {

	/**
	 * 返回设置的SQL脚本文件名字集合
	 * @return
	 */
	List<String> getScriptFiles();
	
	/**
	 * 返回测试SQL语句
	 * @return
	 */
	String getTestSql();
	
	/**
	 * 返回是否强制检查数据库：表结构是否存在。
	 * @return
	 */
	boolean isForceOption();
	
	void setScriptFiles(List<String> filenames);
	
	void setTestSql(String sql);
	
	/**
	 * 设置是否强制检查数据库表的存在，如果设置<code>true</code>系统会在启动时查找表信息，如果没有就创建。</br>
	 * 默认为<code>false</code>
	 * @param forceOption
	 */
	void setForceOption(boolean forceOption);
}
