package com.walkersoft.system.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.walker.db.hibernate.SQLDaoSupport;
import com.walker.db.page.support.GenericPager;
import com.walker.file.FileEngine.StoreType;
import com.walker.file.FileMeta;
import com.walker.file.support.JdbcFileEngineDao;
import com.walker.infrastructure.utils.StringUtils;

@Repository("fileDao")
public class FileDao extends SQLDaoSupport<FileMeta> {

	@Autowired
	private JdbcFileEngineDao fileEngine;
	
	private static final String SQL_SEARCH_FILE1 = "select * from s_file order by create_time desc";
	
	public GenericPager<FileMeta> searchFile(StoreType storeType, String filename, String ext
			, long start, long end){
		GenericPager<FileMeta> pager = null;
		if(storeType == null && filename == null && ext == null 
				&& start == 0 && end == 0){
			pager = this.sqlSimpleQueryPager(SQL_SEARCH_FILE1, null, fileEngine.getNoByteRowMapper());
		} else {
//			if(start == 0) start = new Date().getTime();
//			if(end   == 0) end   = new Date().getTime();
			
			List<Object> pList = new ArrayList<Object>(5);
			StringBuilder sql = new StringBuilder("select * from s_file where 1=1");
			
			if(start > 0){
				sql.append(" and create_time >= ?");
				pList.add(start);
			}
			if(end > 0){
				sql.append(" and create_time <= ?");
				pList.add(end);
			}
			
			if(storeType != null){
				sql.append(" and store_type = ?");
				pList.add(storeType.getIndex());
			}
			if(StringUtils.isNotEmpty(filename)){
				sql.append(" and file_name like ?");
				pList.add("%"+filename+"%");
			}
			if(StringUtils.isNotEmpty(ext)){
				sql.append(" and ext = ?");
				pList.add(ext);
			}
			sql.append(" order by create_time desc");
			pager = this.sqlSimpleQueryPager(sql.toString(), pList.toArray(), fileEngine.getNoByteRowMapper());
		}
		return pager;
	}
}
