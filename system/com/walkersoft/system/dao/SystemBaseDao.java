package com.walkersoft.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.walker.db.hibernate.SQLDaoSupport;
import com.walker.db.page.support.GenericPager;
import com.walker.infrastructure.utils.LongCalendar;
import com.walker.infrastructure.utils.NumberGenerator;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.log.MyLogDetail;
import com.walkersoft.application.log.MyLogDetail.LogType;
import com.walkersoft.system.pojo.ExportObj;

@Repository("systemBaseDao")
public class SystemBaseDao extends SQLDaoSupport<MyLogDetail> {

	private static final String SQL_SEARCH_1 = "select log.create_time, log.login_user, log.type, log.content from s_log log where log.login_user = ? and log.type = ? and log.create_time >= ? and log.create_time <= ? order by log.create_time desc";
	private static final String SQL_SEARCH_2 = "select log.create_time, log.login_user, log.type, log.content from s_log log where log.login_user = ? and log.create_time >= ? and log.create_time <= ? order by log.create_time desc";
	private static final String SQL_SEARCH_3 = "select log.create_time, log.login_user, log.type, log.content from s_log log where log.type = ? and log.create_time >= ? and log.create_time <= ? order by log.create_time desc";
	private static final String SQL_SEARCH_4 = "select log.create_time, log.login_user, log.type, log.content from s_log log where log.create_time >= ? and log.create_time <= ? order by log.create_time desc";
	
	private static final MyLogDetailRowMapper rowMapper = new MyLogDetailRowMapper();
	
	/**
	 * 分页查询日志记录，该方法测试手机端分页调用。
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public GenericPager<MyLogDetail> searchLog(int startIndex, int pageSize){
		return this.sqlSimpleQueryPager("select * from s_log "
				, null, rowMapper, startIndex, pageSize);
	}
	
	/**
	 * 根据条件，分页查询日志记录
	 * @param loginId 登录用户
	 * @param logType 日志类型
	 * @param start 开始时间（毫秒值）
	 * @param end   结束时间（毫秒值）
	 * @return
	 */
	public GenericPager<MyLogDetail> searchLog(String loginId, LogType logType, long start, long end){
		if(start == 0) start = new Date().getTime();
		if(end   == 0) end   = new Date().getTime();
		GenericPager<MyLogDetail> pager = null;
		if(StringUtils.isNotEmpty(loginId) && logType != null){
			pager = this.sqlSimpleQueryPager(SQL_SEARCH_1
					, new Object[]{loginId, logType.getValue(), start, end}, rowMapper);
			
		} else if(StringUtils.isNotEmpty(loginId) && logType == null){
			pager = this.sqlSimpleQueryPager(SQL_SEARCH_2
					, new Object[]{loginId, start, end}, rowMapper);
			
		} else if(StringUtils.isEmpty(loginId) && logType != null){
			pager = this.sqlSimpleQueryPager(SQL_SEARCH_3
					, new Object[]{logType.getValue(), start, end}, rowMapper);
			
		} else {
			pager = this.sqlSimpleQueryPager(SQL_SEARCH_4
					, new Object[]{start, end}, rowMapper);
		}
//			throw new IllegalArgumentException("日志查询条件错误: loginId = " + loginId + ", logType = " + logType + ", startTime = " + start + ", endTime = " + end);
		return pager;
	}
	
	private static class MyLogDetailRowMapper implements RowMapper<MyLogDetail> {
		@Override
		public MyLogDetail mapRow(ResultSet rs, int arg1) throws SQLException {
			// TODO Auto-generated method stub
			return new MyLogDetail(rs.getString("login_user"))
				.setType(MyLogDetail.getLogType(rs.getInt("type")))
				.setContent(rs.getString("content"))
				.setCreateTime(rs.getLong("create_time"));
		}
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// 查询系统上传文件数据
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// 数据库导出、导入使用的方法
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private static final String SQL_INSERT_DB = "insert into s_db(id, create_time, creator_name, "
			+ "type, name, file_id, export_type, summary) values(?,?,?,?,?,?,?,?)";
	
	/**
	 * 导出数据时，保存导出记录
	 * @param exportType 导出类型
	 * @param summary 备注
	 * @param fileId 导出存储的文件ID
	 * @param creatorName 创建人
	 */
	public void insertExport(int exportType, String summary, String fileId, String creatorName){
		Object[] params = new Object[8];
		params[0] = NumberGenerator.generatorHexUUID();
		params[1] = System.currentTimeMillis();
		params[2] = creatorName;
		params[3] = 1;
		params[4] = LongCalendar.getCurrentDate() + "_导出_" + NumberGenerator.getSequenceNumber();
		params[5] = fileId;
		params[6] = exportType;
		params[7] = summary;
		this.update(SQL_INSERT_DB, params);
	}
	
	private static final String SQL_SELECT_EXPORT = "select * from s_db where type=? order by create_time desc";
	
	public GenericPager<ExportObj> getExportList(){
		return this.sqlGeneralQueryPager(SQL_SELECT_EXPORT, new Object[]{1}, exportRowMapper);
	}
	
	private ExportRowMapper exportRowMapper = new ExportRowMapper();
	
	private class ExportRowMapper implements RowMapper<ExportObj>{
		@Override
		public ExportObj mapRow(ResultSet rs, int arg1) throws SQLException {
			ExportObj obj = new ExportObj();
			obj.setId(rs.getString("id"));
			obj.setCreateTime(rs.getLong("create_time"));
			obj.setCreatorName(rs.getString("creator_name"));
			obj.setExportType(rs.getInt("export_type"));
			obj.setName(rs.getString("name"));
			obj.setFileId(rs.getString("file_id"));
			obj.setSummary(rs.getString("summary"));
			return obj;
		}
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// 导入数据库操作
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * 保存导入数据库记录
	 * @param summary
	 * @param fileId
	 * @param creatorName
	 */
	public void insertImport(String summary, String fileId, String creatorName){
		Object[] params = new Object[8];
		params[0] = NumberGenerator.generatorHexUUID();
		params[1] = System.currentTimeMillis();
		params[2] = creatorName;
		params[3] = 0;
		params[4] = LongCalendar.getCurrentDate() + "_导入_" + NumberGenerator.getSequenceNumber();
		params[5] = fileId;
		params[6] = 0;
		params[7] = summary;
		this.update(SQL_INSERT_DB, params);
	}
	
	public GenericPager<ExportObj> getImportList(){
		return this.sqlGeneralQueryPager(SQL_SELECT_EXPORT, new Object[]{0}, exportRowMapper);
	}
}
