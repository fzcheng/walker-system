package com.walker.test;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.walker.db.DatabaseType;
import com.walker.db.init.DataTableInitializeExecutor;
import com.walker.db.init.DatabaseInitializeException;
import com.walker.db.init.DefaultDataTableInitializeExecutor;
import com.walker.infrastructure.utils.StringUtils;

/**
 * 数据库表结构初始化"探测器"</p>
 * 它负责应用系统中各个模块的表结构的检查与初始化，根据各模块的配置自动初始化表结构。</br>
 * <pre>
 * 1、会在应用系统启动过程中扫描所有的<code>DataTableCollectable</code>对象，每个对象定义了一个模块的数据库初始化脚本。</br>
 * 2、通过执行扫描到的每个模块的数据库脚本并初始化，如果出现错误，会在控制台生成错误提示，通常上层调用者应终止系统的执行。</br>
 * 3、系统会自动生成依赖关系，如：<code>DatabaseDetector</code>依赖数据源和<code>DataTableCollectable</code>对象等。
 * </pre>
 * @author shikeying
 *
 */
public class DatabaseDetector {
	
	private final transient Log logger = LogFactory.getLog(getClass());

	private DatabaseType dbType = null;
	
	private DataSource dataSource;
	
	private List<DataTableCollectable> dtcList = new ArrayList<DataTableCollectable>(8);
	
	public void setDatabaseType(int index){
		dbType = DatabaseType.getType(index);
	}

	public void setDataSource(DataSource dataSource) {
		assert (dataSource != null);
		this.dataSource = dataSource;
	}
	
	public void setDataTableCollectors(List<DataTableCollectable> dtcList){
		assert (dtcList != null);
		this.dtcList = dtcList;
	}
	
	public void addDataTableCollector(DataTableCollectable dtc){
		assert (dtc != null);
		this.dtcList.add(dtc);
	}
	
	public void detect() throws DatabaseInitializeException {
		if(dbType == null)
			throw new Error("databaseType not set!");
		if(dataSource == null)
			throw new Error("dataSource not set!");
		
		if(dtcList.size() == 0){
			logger.info("-------------> no DataTableCollectable found!");
			return;
		}
		
		for(DataTableCollectable dtc : dtcList){
			this.doCheckTable(dtc);
		}
	}
	
	private void doCheckTable(DataTableCollectable dtc) throws DatabaseInitializeException{
		List<String> filenames = dtc.getScriptFiles();
		if(filenames == null){
			logger.warn("not found DataTableCollectable!");
			return;
		}
		/* 文件名称应该是classpath下面的，如：conf/mysql_init.sql */
		String scriptFilename = getFilenames(filenames);
		
		DataTableInitializeExecutor dtExecutor = new DefaultDataTableInitializeExecutor();
		String testSql = dtc.getTestSql();
		if(StringUtils.isNotEmpty(testSql)){
			dtExecutor.setTestSql(testSql);
		}
		dtExecutor.setClasspathScript(scriptFilename);
		dtExecutor.setDataSource(dataSource);
		dtExecutor.setForceOption(dtc.isForceOption());
		dtExecutor.execute();
	}
	
	private String getFilenames(List<String> filenames){
		if(filenames.size() == 1){
			logger.warn("模块中只发现了一个SQL脚本文件，无法支持多数据库应用: " + filenames.get(0));
			return filenames.get(0);
		}
		String result = null;
		for(String filename : filenames){
			if(dbType == DatabaseType.MYSQL){
				if(filename.toLowerCase().indexOf(DatabaseType.NAME_MYSQL) >= 0){
					result = filename;
					break;
				}
			} else if(dbType == DatabaseType.ORACLE){
				if(filename.toLowerCase().indexOf(DatabaseType.NAME_ORACLE) >= 0){
					result = filename;
					break;
				}
			} else if(dbType == DatabaseType.DERBY){
				if(filename.toLowerCase().indexOf(DatabaseType.NAME_DERBY) >= 0){
					result = filename;
					break;
				}
			} else {
				throw new UnsupportedOperationException("not supported database script: " + dbType);
			}
		}
		if(result == null){
//			logger.error("根据当前数据库类型，未找到匹配的SQL脚本文件，请检查开发配置!");
			throw new Error("根据当前数据库类型，未找到匹配的SQL脚本文件，请检查开发配置!");
		}
		return result;
	}
}
