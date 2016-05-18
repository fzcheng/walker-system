package com.walker;

import org.apache.commons.dbcp.BasicDataSource;

import com.walker.db.DatabaseType;
import com.walker.infrastructure.utils.StringUtils;

/**
 * 支持数据库类型的数据源实现
 * @author shikeying
 * @date 2014-5-23
 *
 */
public class SupportDBTypeDataSource extends BasicDataSource {

	public static final String DRIVER_NAME_DERBY = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String DRIVER_NAME_MYSQL = "com.mysql.jdbc.Driver";
	public static final String DRIVER_NAME_ORACLE = "oracle.jdbc.driver.OracleDriver";
	
	public static final int DEFAULT_PORT_DERBY = 0;
	public static final int DEFAULT_PORT_MYSQL = 3306;
	public static final int DEFAULT_PORT_ORACLE = 1521;
			
	private DatabaseType databaseType = null;
	
	private String ip;
	private int port = 0;
//	private String databaseName;

	public void setDatabaseType(int databaseTypeValue) {
		this.databaseType = DatabaseType.getType(databaseTypeValue);
		String driverName = null;
		if(databaseType == DatabaseType.DERBY){
			driverName = DRIVER_NAME_DERBY;
		} else if(databaseType == DatabaseType.MYSQL){
			driverName = DRIVER_NAME_MYSQL;
		} else if(databaseType == DatabaseType.ORACLE){
			driverName = DRIVER_NAME_ORACLE;
		} else
			throw new IllegalArgumentException("unsupported database type: " + databaseTypeValue);
		this.setDriverClassName(driverName);
	}

	public void setIp(String ip) {
		assert (StringUtils.isNotEmpty(ip));
		this.ip = ip;
	}

	public void setPort(int port) {
		assert(port > 0 && port < Integer.MAX_VALUE);
		this.port = port;
	}

	public void setDatabaseName(String databaseName) {
		if(databaseType == null){
			throw new IllegalArgumentException("databaseType must be set firstly!");
		}
		if(databaseType != DatabaseType.DERBY && StringUtils.isEmpty(ip)){
			throw new IllegalArgumentException("ip or port must be set firstly!");
		}
		assert (StringUtils.isNotEmpty(databaseName));
//		this.databaseName = databaseName;
		
		StringBuilder url = new StringBuilder();
		if(databaseType == DatabaseType.DERBY){
			url.append("jdbc:derby:").append(databaseName);
		} else if(databaseType == DatabaseType.MYSQL){
			url.append("jdbc:mysql://").append(ip).append(":")
				.append(port == 0 ? DEFAULT_PORT_MYSQL : port)
				.append("/").append(databaseName);
		} else if(databaseType == DatabaseType.ORACLE){
			url.append("jdbc:oracle:thin:@").append(ip).append(":")
				.append(port == 0 ? DEFAULT_PORT_ORACLE : port)
				.append(":").append(databaseName);
		} else
			throw new IllegalArgumentException("unsupported database type: " + databaseType);
		
		this.setUrl(url.toString());
	}
	
}
