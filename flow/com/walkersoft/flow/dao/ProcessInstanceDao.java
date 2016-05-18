package com.walkersoft.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.walker.db.hibernate.SQLDaoSupport;
import com.walker.db.page.support.GenericPager;
import com.walker.flow.client.FlowClient;
import com.walkersoft.flow.pojo.AwaitTask;
import com.walkersoft.flow.pojo.ProcessItem;
import com.walkersoft.flow.pojo.TaskItem;

/**
 * 流程实例管理DAO定义。
 * @author shikeying
 * @date 2014-10-17
 *
 */
@Repository("processInstanceDao")
public class ProcessInstanceDao extends SQLDaoSupport<AwaitTask> {

	private final FlowClient flowClient = FlowClient.getInstance();
	
	private final AwaitTaskMapper awaitTaskMapper = new AwaitTaskMapper();
	private final ProcessItemMapper processItemMapper = new ProcessItemMapper();
	private final ProcessItemBaseMapper processItemBaseMapper = new ProcessItemBaseMapper();
	private final TaskItemMapper taskItemMapper = new TaskItemMapper();
	
	private static final String SQL_AWAIT_TASK_SELECT = "SELECT ti.ID taskInstId, ti.TASK_DEFINE_ID taskDefineId, ti.NAME taskName, ti.user_id, ti.user_name "
			+ ", ti.CREATE_TIME arriveTime, pi.ID processInstId, pi.PROCESS_DEFINE_ID, pi.NAME processName, pi.CREATE_TIME, pi.BUSINESS_ID "
			+ ", pi.is_end, pi.end_time, pi.is_terminate, pi.terminate_time, pi.is_pause, pi.pause_time "
			+ "FROM sw_task_inst ti, sw_process_inst pi ";
			
	private static final String SQL_AWAIT_TASK_LIST = SQL_AWAIT_TASK_SELECT
			+ "WHERE ti.TASK_STATUS = 0 AND ti.USER_ID = ? AND ti.GLOBAL_PROCESS_INST = pi.ID order by ti.CREATE_TIME desc";
	
	/**
	 * 返回用户的代办任务集合
	 * @param userId 用户ID
	 * @return
	 */
	public GenericPager<AwaitTask> getAwaitTaskPager(String userId){
		return this.sqlSimpleQueryPager(SQL_AWAIT_TASK_LIST, new Object[]{userId}, awaitTaskMapper);
	}
	
	private static final String SQL_AWAIT_TASK_ALL = SQL_AWAIT_TASK_SELECT
			+ "WHERE ti.TASK_STATUS = 0 AND ti.GLOBAL_PROCESS_INST = pi.ID order by ti.CREATE_TIME desc";
	
	/**
	 * 返回所有等待办理的任务列表
	 * @return
	 */
	public GenericPager<AwaitTask> getAwaitTaskPager(){
		return this.sqlSimpleQueryPager(SQL_AWAIT_TASK_ALL, null, awaitTaskMapper);
	}
	
	private static final String SQL_ALL_PROCESS_INST_LIST = "SELECT pi.*, ti.name taskName, ti.user_name taskActor "
			+ "FROM sw_process_inst pi LEFT JOIN sw_task_inst ti "
			+ "ON pi.ID = ti.GLOBAL_PROCESS_INST AND ti.TASK_STATUS = 0 order by pi.create_time desc";
	
	/**
	 * 返回所有流程实例列表，此列表能看到任务执行情况。
	 * @return
	 */
	public GenericPager<ProcessItem> getAllProcessListPager(){
		return this.sqlGeneralQueryPager(SQL_ALL_PROCESS_INST_LIST, null, this.processItemMapper);
	}
	
	private static final String SQL_GET_PROCESS_INSTANCE = "select * from sw_process_inst where id=?";
	
	/**
	 * 返回一条流程实例记录，用来展示
	 * @param processInstId
	 * @return
	 */
	public ProcessItem getProcessInstance(String processInstId){
		return this.sqlQuerySingle(SQL_GET_PROCESS_INSTANCE, new Object[]{processInstId}, processItemBaseMapper);
	}
	
	private static final String SQL_GET_TASK_INST_LIST = "SELECT ti.*"
			+ "FROM sw_task_inst ti "
			+ "WHERE ti.PROCESS_INST_ID = ? ORDER BY ti.CREATE_TIME ASC";
	
	/**
	 * 返回某个流程实例对应的所有任务实例集合
	 * @param processInstId 流程实例ID
	 * @return
	 */
	public List<TaskItem> getTaskInstanceList(String processInstId){
		return this.sqlCustomQuery(SQL_GET_TASK_INST_LIST, new Object[]{processInstId}, taskItemMapper);
	}
	
	private class AwaitTaskMapper implements RowMapper<AwaitTask>{
		@Override
		public AwaitTask mapRow(ResultSet rs, int arg1) throws SQLException {
			AwaitTask task = new AwaitTask();
			task.setBusinessId(rs.getString("business_id"));
			task.setProcessCreateTime(rs.getLong("create_time"));
			task.setProcessDefineId(rs.getString("process_define_id"));
			task.setProcessInstanceId(rs.getString("processInstId"));
			task.setProcessName(rs.getString("processName"));
			task.setTaskArrivedTime(rs.getLong("arriveTime"));
			task.setTaskDefineId(rs.getString("taskDefineId"));
			task.setTaskInstanceId(rs.getString("taskInstId"));
			task.setTaskName(rs.getString("taskName"));
			task.setEnd(rs.getInt("is_end"));
			task.setEndTime(rs.getLong("end_time"));
			task.setTerminate(rs.getInt("is_terminate"));
			task.setTerminateTime(rs.getLong("terminate_time"));
			task.setPause(rs.getInt("is_pause"));
			task.setPauseTime(rs.getLong("pause_time"));
			task.setUserId(rs.getString("user_id"));
			task.setUserName(rs.getString("user_name"));
			return task;
		}
	}
	
	private class ProcessItemBaseMapper implements RowMapper<ProcessItem>{
		@Override
		public ProcessItem mapRow(ResultSet rs, int arg1) throws SQLException {
			ProcessItem pi = new ProcessItem();
			pi.setId(rs.getString("id"));
			pi.setBusinessId(rs.getString("business_id"));
			pi.setBusinessType(rs.getString("business_type"));
			pi.setCreateTime(rs.getLong("create_time"));
			pi.setCreatorId(rs.getString("user_id"));
			pi.setCreatorName(rs.getString("user_name"));
			pi.setEnd(rs.getInt("is_end"));
			pi.setEndTime(rs.getLong("end_time"));
			pi.setPause(rs.getInt("is_pause"));
			pi.setPause_time(rs.getLong("pause_time"));
			pi.setGlobalProcessInst(rs.getString("global_process_inst"));
			pi.setName(rs.getString("name"));
			pi.setProcessDefineId(rs.getString("process_define_id"));
			pi.setSubProcess(rs.getInt("sub_process"));
			pi.setTerminate(rs.getInt("is_terminate"));
			pi.setTerminateTime(rs.getLong("terminate_time"));
			pi.setProcessTemplate(flowClient.getFlowCacheFactory().getProcessDefine(pi.getProcessDefineId()).getName());
			return pi;
		}
	}
	
	private class ProcessItemMapper implements RowMapper<ProcessItem>{
		@Override
		public ProcessItem mapRow(ResultSet rs, int arg1) throws SQLException {
			ProcessItem pi = processItemBaseMapper.mapRow(rs, arg1);
//			pi.setId(rs.getString("id"));
//			pi.setBusinessId(rs.getString("business_id"));
//			pi.setBusinessType(rs.getString("business_type"));
//			pi.setCreateTime(rs.getLong("create_time"));
//			pi.setCreatorId(rs.getString("user_id"));
//			pi.setCreatorName(rs.getString("user_name"));
//			pi.setEnd(rs.getInt("is_end"));
//			pi.setEndTime(rs.getLong("end_time"));
//			pi.setPause(rs.getInt("is_pause"));
//			pi.setPause_time(rs.getLong("pause_time"));
//			pi.setGlobalProcessInst(rs.getString("global_process_inst"));
//			pi.setName(rs.getString("name"));
//			pi.setProcessDefineId(rs.getString("process_define_id"));
//			pi.setSubProcess(rs.getInt("sub_process"));
//			pi.setTerminate(rs.getInt("is_terminate"));
//			pi.setTerminateTime(rs.getLong("terminate_time"));
			pi.setTaskName(rs.getString("taskName"));
			pi.setUserName(rs.getString("taskActor"));
			return pi;
		}
	}
	
	private class TaskItemMapper implements RowMapper<TaskItem>{
		@Override
		public TaskItem mapRow(ResultSet rs, int arg1) throws SQLException {
			TaskItem taskInst = new TaskItem();
			taskInst.setTaskDefineId(rs.getString("TASK_DEFINE_ID"));
			taskInst.setId(rs.getString("ID"));
			taskInst.setName(rs.getString("NAME"));
			taskInst.setCreateTime(rs.getLong("CREATE_TIME"));
			taskInst.setSubProcess(rs.getInt("SUB_PROCESS") == 1);
			taskInst.setTaskStatus(rs.getInt("TASK_STATUS"));
			taskInst.setUpdateTime(rs.getLong("UPDATE_TIME"));
			taskInst.setEndTime(rs.getLong("END_TIME"));
			taskInst.setUserId(rs.getString("USER_ID"));
			taskInst.setUserName(rs.getString("USER_NAME"));
			taskInst.setOpinion(rs.getString("OPINION"));
			taskInst.setPreviousTaskInstId(rs.getString("PREVIOUS_TASK"));
			taskInst.setNextTaskInstId(rs.getString("NEXT_TASK"));
			taskInst.setProcessInstId(rs.getString("PROCESS_INST_ID"));
			taskInst.setPrimaryTaskInst(rs.getString("PRIMARY_TASK_INST"));
			taskInst.setGlobalProcessInst(rs.getString("GLOBAL_PROCESS_INST"));
//			taskInst.setTaskDefineObject(null);
			return taskInst;
		}
	}
}
