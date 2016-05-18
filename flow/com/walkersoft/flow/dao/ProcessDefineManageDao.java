package com.walkersoft.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.walker.db.hibernate.SQLDaoSupport;
import com.walker.db.page.support.GenericPager;
import com.walker.flow.meta.ProcessDefine;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.flow.pojo.ProcessIdentifier;

/**
 * 流程定义管理DAO
 * @author shikeying
 *
 */
@Repository("processDefineManageDao")
public class ProcessDefineManageDao extends SQLDaoSupport<ProcessIdentifier> {

	private ProcessIdentifierRowMapper piMapper = new ProcessIdentifierRowMapper();
	private ProcessRowMapper processRowMapper = new ProcessRowMapper();
	
	private static final String GET_PAGED_LIST = "SELECT v.INTER_IDENTIFIER v_id, v.CREATE_TIME v_time, v.PROCESS_DEFINE_ID v_pid, d.* " 
			+ "FROM sw_process_version v, sw_process_define d " 
			+ "WHERE v.PROCESS_DEFINE_ID = d.ID ORDER BY v.CREATE_TIME DESC";
	
	/**
	 * 分页返回可管理的流程列表
	 * @return
	 */
	public GenericPager<ProcessIdentifier> getPagedProcessIdentifierList(){
		return this.sqlSimpleQueryPager(GET_PAGED_LIST, null, piMapper);
	}
	
	private class ProcessIdentifierRowMapper implements RowMapper<ProcessIdentifier> {
		@Override
		public ProcessIdentifier mapRow(ResultSet rs, int index)
				throws SQLException {
			// TODO Auto-generated method stub
			ProcessIdentifier pi = new ProcessIdentifier();
			pi.setIdentifier(rs.getString("v_id"));
			pi.setCreateTime(rs.getLong("v_time"));
			
			ProcessDefine pd = toProcessDefine(rs);
//			ProcessDefine pd = new ProcessDefine();
//			pd.setId(rs.getString("id"));
//			pd.setName(rs.getString("name"));
//			pd.setCreateTime(rs.getLong("create_time"));
//			pd.setPublishStatus(rs.getInt("publish_status"));
//			pd.setInterIdentifier(rs.getString("inter_identifier"));
//			pd.setVersion(rs.getInt("version"));
//			pd.setSubProcess(rs.getInt("sub_process"));
//			pd.setWorkflowCreateListener(rs.getString("listener_create"));
//			pd.setWorkflowEndListener(rs.getString("listener_end"));
//			pd.setSummary(rs.getString("summary"));
//			pd.setBusinessType(rs.getString("business_type"));
//			pd.setDeleteStatus(rs.getInt("delete_status"));
//			pd.setPageUrl(rs.getString("page_url_id"));
//			pd.setPageUrlId(rs.getString("page_url_id"));
//			pd.setWorkDayLimit(rs.getInt("workday_limit"));
//			pd.setPicture(rs.getString("picture"));
//			pd.setRemindTemplate(rs.getString("remind_template"));
			
			pi.setProcessDefine(pd);
			return pi;
		}
	}
	
	private static final String SQL_INSERT_PROCESS_DEFINE = "insert into sw_process_define(id,name,create_time,create_user,publish_status,"
			+ "inter_identifier,version,sub_process,listener_create,listener_end,summary,business_type,delete_status,page_url_id,"
			+ "workday_limit,picture,remind_template,old_base_label) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	public void insertProcessDefine(ProcessDefine processDefine){
		Object[] params = new Object[18];
		params[0] = processDefine.getId();
		params[1] = processDefine.getName();
		params[2] = System.currentTimeMillis();
		params[3] = "admin";
		params[4] = 0; //新创建的流程是未发布状态：0
		params[5] = processDefine.getInterIdentifier(); // 让用户输入标识，需要检查重复
		params[6] = 1;
		params[7] = processDefine.getSubProcess();
		params[8] = processDefine.getCreateListenerName();
		params[9] = processDefine.getEndListenerName();
		params[10] = processDefine.getSummary();
		params[11]= processDefine.getBusinessType();
		params[12]= 0; //删除状态：未删除，0
		params[13]= processDefine.getPageUrlId();
		params[14]= processDefine.getWorkDayLimit();
		params[15]= processDefine.getPicture();
		params[16]= processDefine.getRemindTemplate();
		params[17]= null;
		this.update(SQL_INSERT_PROCESS_DEFINE, params);
	}
	
	private static final String SQL_INSERT_VERSION = "insert into sw_process_version(inter_identifier,create_time,process_define_id) values(?,?,?)";
	
	public void insertIdentifier(String identifier, String processDefineId){
		Object[] params = new Object[3];
		params[0] = identifier;
		params[1] = System.currentTimeMillis();
		params[2] = processDefineId;
		this.update(SQL_INSERT_VERSION, params);
	}
	
	private static final String SQL_MAX_PROCESSID = "select d.id from sw_process_define d, (select max(create_time) max_time from sw_process_define) t2 where d.CREATE_TIME = t2.max_time";
	
	public int getMaxProcessId(){
//		int result = this.getJdbcTemplate().queryForInt(SQL_MAX_PROCESSID);
		List<Map<String, Object>> result = this.getJdbcTemplate().queryForList(SQL_MAX_PROCESSID);
		if(result == null || result.size() == 0){
			return 1;
		}
		int num = Integer.valueOf(result.get(0).get("id").toString());
		if(num > 0){
			return num + 1;
		} else
			return 1;
	}
	
	private static final String SQL_QUERY_IDENTIFIER = "select * from sw_process_version v where v.inter_identifier = ?";
	
	/**
	 * 查找存在的流程标识记录
	 * @param identifier
	 * @return
	 */
	public Object getProcessDefineObj(String identifier){
		List<Map<String, Object>> result = this.getJdbcTemplate().queryForList(SQL_QUERY_IDENTIFIER, new Object[]{identifier});
		if(result != null && result.size() > 0){
			return result.get(0);
		} else
			return null;
	}
	
	private static final String SQL_PROCESS_DEPRECATED = "update sw_process_define set delete_status = 1 where id = ?";
	
	/**
	 * 更新流程定义'状态为已废弃'，即：更新删除状态
	 * @param ProcessDefineId
	 */
	public void updateProcessDeprecated(String ProcessDefineId){
		this.update(SQL_PROCESS_DEPRECATED, new Object[]{ProcessDefineId});
	}
	
	private static final String SQL_DELETE_PROCESS_IDENTIFIER = "delete from sw_process_define where inter_identifier = ?";
	
	/**
	 * 根据标识，删除定义表中所有流程历史数据
	 * @param identifier
	 */
	public void deleteProcessDefine(String identifier){
		this.update(SQL_DELETE_PROCESS_IDENTIFIER, new Object[]{identifier});
	}
	
	private static final String SQL_DELETE_VERSION = "delete from sw_process_version where inter_identifier = ?";
	
	/**
	 * 根据主键，删除version表中的记录
	 * @param identifier
	 */
	public void deleteProcessVersion(String identifier){
		this.update(SQL_DELETE_VERSION, new Object[]{identifier});
	}
	
	private static final String SQL_UPDATE_PROCESS = "update sw_process_define set name=?, "
			+ "sub_process=?, listener_create=?, listener_end=?, summary=?, "
			+ "business_type=?, page_url_id=?, workday_limit=?, remind_template=? "
			+ "where id=?";
	
	public void updateProcessDefine(ProcessDefine processDefine){
		if(StringUtils.isEmpty(processDefine.getId())){
			throw new IllegalArgumentException();
		}
		Object[] params = new Object[10];
		params[0] = processDefine.getName();
		params[1] = processDefine.getSubProcess();
		params[2] = processDefine.getCreateListenerName();
		params[3] = processDefine.getEndListenerName();
		params[4] = processDefine.getSummary();
		params[5] = processDefine.getBusinessType();
		params[6] = processDefine.getPageUrlId();
		params[7] = processDefine.getWorkDayLimit();
		params[8] = processDefine.getRemindTemplate();
		params[9] = processDefine.getId();
		this.update(SQL_UPDATE_PROCESS, params);
	}
	
	private static final String SQL_QUERY_ID_PROCESS = "select * from sw_process_define where id = ?";
	
	/**
	 * 根据ID查询流程定义记录
	 * @param id
	 * @return
	 */
	public ProcessDefine getProcessDefine(String id){
		return this.getJdbcTemplate().queryForObject(SQL_QUERY_ID_PROCESS
				, new Object[]{id}, processRowMapper);
	}
	
	private static final String SQL_QUERY_VERSION_IDENTIFIER = "select inter_identifier from sw_process_version where process_define_id = ?";
	
	/**
	 * 从流程版本表中查询对应流程的标识名称
	 * @param processDefineId
	 * @return
	 */
	public String getIdentifier(String processDefineId){
		Map<String, Object> result = this.getJdbcTemplate().queryForMap(SQL_QUERY_VERSION_IDENTIFIER, new Object[]{processDefineId});
		if(result != null && result.size() > 0){
			return result.get("inter_identifier").toString();
		}
		return null;
	}
	
	private class ProcessRowMapper implements RowMapper<ProcessDefine>{
		@Override
		public ProcessDefine mapRow(ResultSet rs, int i)
				throws SQLException {
			// TODO Auto-generated method stub
			return toProcessDefine(rs);
		}
	}
	
	private ProcessDefine toProcessDefine(ResultSet rs) throws SQLException{
		ProcessDefine pd = new ProcessDefine();
		pd.setId(rs.getString("id"));
		pd.setName(rs.getString("name"));
		pd.setCreateTime(rs.getLong("create_time"));
		pd.setPublishStatus(rs.getInt("publish_status"));
		pd.setInterIdentifier(rs.getString("inter_identifier"));
		pd.setVersion(rs.getInt("version"));
		pd.setSubProcess(rs.getInt("sub_process"));
		pd.setWorkflowCreateListener(rs.getString("listener_create"));
		pd.setWorkflowEndListener(rs.getString("listener_end"));
		pd.setSummary(rs.getString("summary"));
		pd.setBusinessType(rs.getString("business_type"));
		pd.setDeleteStatus(rs.getInt("delete_status"));
		pd.setPageUrl(rs.getString("page_url_id"));
		pd.setPageUrlId(rs.getString("page_url_id"));
		pd.setWorkDayLimit(rs.getInt("workday_limit"));
		pd.setPicture(rs.getString("picture"));
		pd.setRemindTemplate(rs.getString("remind_template"));
		return pd;
	}
}
