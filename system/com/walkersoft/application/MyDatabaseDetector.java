package com.walkersoft.application;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;

import com.walker.db.DatabaseType;
import com.walker.db.init.DataTableInitializeExecutor;
import com.walker.db.init.DefaultDataTableInitializeExecutor;
import com.walker.infrastructure.utils.StringUtils;

/**
 * 初始化系统数据库的检测对象定义</p>
 * 它从<code>spring</code>环境中，最早启动，并尝试检测数据库中表结构是否存在，</br>
 * 如果不存在将自动初始化默认的脚本。</p>
 * 系统数据库脚本目前只有MySQL和ORACEL两个，根据配置的数据库类型来确定。
 * @author shikeying
 *
 */
public class MyDatabaseDetector implements InitializingBean {

	private final DataTableInitializeExecutor dtExecutor = new DefaultDataTableInitializeExecutor();
	
	private DatabaseType dbType = null;
	
	private DataSource dataSource;
	
	// 是否强制检查数据库表是否存在，默认不检查
	private boolean forceOption = false;
	
	/* 测试数据库的语句，可以指定 */
	private String testSql;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		if(dbType == null)
			throw new Error("databaseType not set!");
		if(dataSource == null)
			throw new Error("dataSource not set!");
		
		if(dbType == DatabaseType.MYSQL){
			dtExecutor.setClasspathScript("init_sql/utf8-mysql.SQL");
		} else if(dbType == DatabaseType.ORACLE){
			dtExecutor.setClasspathScript("init_sql/utf8-oracle.SQL");
		} else if(dbType == DatabaseType.DERBY){
			dtExecutor.setClasspathScript("init_sql/utf8-derby.SQL");
		} else {
			throw new UnsupportedOperationException("not supported database script: " + dbType);
		}
		if(StringUtils.isNotEmpty(testSql)){
			dtExecutor.setTestSql(testSql);
		}
		dtExecutor.setDataSource(dataSource);
		dtExecutor.setForceOption(forceOption);
		dtExecutor.execute();
	}

	public void setDatabaseType(int index){
		dbType = DatabaseType.getType(index);
	}

	public void setDataSource(DataSource dataSource) {
		assert (dataSource != null);
		this.dataSource = dataSource;
	}

	public void setTestSql(String testSql) {
		this.testSql = testSql;
	}

	/**
	 * 设置是否强制检查数据库表的存在，如果设置<code>true</code>系统会在启动时查找表信息，如果没有就创建。</br>
	 * 默认为<code>false</code>
	 * @param forceOption
	 */
	public void setForceOption(boolean forceOption) {
		this.forceOption = forceOption;
	}
}
