package com.walkersoft.application.db;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.walker.db.export.DataExportor;
import com.walkersoft.application.dao.DatabaseSearchDao;

/**
 * 应用系统默认的数据库数据导出实现。</p>
 * 该实现仅仅需要提供一个表数据的分页查询方法。
 * @author shikeying
 *
 */
public class DefaultDatabaseExportor extends DataExportor {

	@Autowired
	private DatabaseSearchDao databaseSearchDao;
	
	@Override
	protected List<Map<String, Object>> getLimitedDataList(String tableName,
			int currentPage, int pageSize) {
		return databaseSearchDao.getPagedDataList(tableName, currentPage, pageSize);
	}

}
