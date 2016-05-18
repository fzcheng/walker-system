package com.walkersoft.application.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.walker.db.hibernate.SQLDaoSupport;

@Repository("databaseSearchDao")
public class DatabaseSearchDao extends SQLDaoSupport<Object> {

	/**
	 * 给定表，分页查询表中数据记录
	 * @param tableName 表名
	 * @param currentPage 当前页
	 * @param pageSize 分页大小
	 * @return
	 */
	public List<Map<String, Object>> getPagedDataList(String tableName
			,int currentPage, int pageSize){
		int startIndex = (currentPage - 1) * pageSize;
		String sql = "select * from " + tableName;
		return this.sqlQueryForList(sql, null, startIndex, pageSize, null);
	}
}
