package com.walkersoft.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.walker.db.hibernate.SQLDaoSupport;
import com.walker.flow.meta.TaskDefineAbstract;
import com.walker.flow.meta.TaskIn;
import com.walker.flow.meta.TaskOut;
import com.walkersoft.flow.pojo.AnchorIn;
import com.walkersoft.flow.pojo.AnchorOut;
import com.walkersoft.flow.pojo.TaskNode;

/**
 * 流程设计器编辑DAO实现
 * @author shikeying
 * @date 2014-7-2
 *
 */
@Repository("DesignDao")
public class DesignDao extends SQLDaoSupport<TaskDefineAbstract> {

	private static final String SQL_INSERT_TASK = "insert into SW_TASK_DEFINE(id,name,create_time,"
			+ "task_type,process_define_id,listener_in,listener_out,is_start,is_end,affluence_type,"
			+ "split_type,actor_type,sub_process_id,next_name,previous_name,page_url,access_role,"
			+ "multi_user,actor_exec,mod_task) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	public void insertTask(TaskDefineAbstract task){
		Object[] params = new Object[20];
		params[0] = task.getId();
		params[1] = task.getName();
		params[2] = System.currentTimeMillis();
		params[3] = task.getNodeTypeIndex();
		params[4] = task.getProcessDefineId();
		params[5] = task.getTaskInListenerName();
		params[6] = task.getTaskOutListenerName();
		params[7] = task.getStartTask();
		params[8] = task.getEndTask();
		params[9] = task.getAffluenceType();
		params[10]= task.getSplitType();
		params[11]= task.getActorType();
		params[12]= task.getSubProcessId();
		params[13]= task.getNextName();
		params[14]= task.getPreviousName();
		params[15]= task.getPageUrl();
		params[16]= task.getAccessRole();
		params[17]= task.getMultiUser();
		params[18]= task.getActorExec();
		params[19]= task.isModTask()? 1 : 0;
//		params[20]= 0;
		this.update(SQL_INSERT_TASK, params);
	}
	
	private static final String SQL_UPDATE_TASK = "update SW_TASK_DEFINE set name=?, listener_in=?, listener_out=?" 
			+ ", affluence_type=?, split_type=?, actor_type=?, sub_process_id=?, next_name=?, previous_name=?" 
			+ ", page_url=?, access_role=?, multi_user=?, actor_exec=?, mod_task=? where id=?";
	
	public void updateTask(TaskDefineAbstract task){
		Object[] params = new Object[15];
		params[0] = task.getName();
		params[1] = task.getTaskInListenerName();
		params[2] = task.getTaskOutListenerName();
		params[3] = task.getAffluenceType();
		params[4] = task.getSplitType();
		params[5] = task.getActorType();
		params[6] = task.getSubProcessId();
		params[7] = task.getNextName();
		params[8] = task.getPreviousName();
		params[9] = task.getPageUrl();
		params[10]= task.getAccessRole();
		params[11]= task.getMultiUser();
		params[12]= task.getActorExec();
		params[13]= task.isModTask()? 1 : 0;
		params[14]= task.getId();
		this.update(SQL_UPDATE_TASK, params);
	}
	
	private static final String SQL_MAX_TASKID = "select d.id from SW_TASK_DEFINE d, (select max(create_time) max_time from SW_TASK_DEFINE) as t2 where d.CREATE_TIME = t2.max_time";
	
	/**
	 * 找出当前任务记录最大值，并+1
	 * @return
	 */
	public int getMaxTaskId(){
		List<Map<String, Object>> result = this.getJdbcTemplate().queryForList(SQL_MAX_TASKID);
		if(result == null || result.size() == 0){
			return 1;
		}
		int num = Integer.valueOf(result.get(0).get("id").toString());
		if(num > 0){
			return num + 1;
		} else
			return 1;
	}
	
	private static final String SQL_INSERT_TASK_IN = "insert into SW_TASK_IN(id,task_define_id,previous_task,process_define_id) "
			+ "values(?,?,?,?)";
	
	/**
	 * 向任务进入表，插入第一个进入记录，表示任务的上级节点为“开始”
	 * @param taskDefineId
	 * @param processDefineId
	 */
	public void insertTaskIn(TaskIn taskIn){
		Object[] params = new Object[4];
		params[0] = taskIn.getId();
		params[1] = taskIn.getTaskDefineId();
		params[2] = taskIn.getPreviousTask();
		params[3] = taskIn.getProcessDefineId();
		this.update(SQL_INSERT_TASK_IN, params);
	}
	
	private static final String SQL_INSERT_TASKNODE = "insert into sw_design_node("
			+ "id,x,y,in_positions,out_positions,process_define_id) values(?,?,?,?,?,?)";
			
	/**
	 * 写入任务节点坐标信息到数据库
	 * @param node
	 */
	public void insertTaskNode(TaskNode node){
		Object[] params = new Object[6];
		params[0] = node.getId();
		params[1] = node.getX();
		params[2] = node.getY();
		params[3] = node.getInPositions();
		params[4] = node.getOutPositions();
		params[5] = node.getProcesDefineId();
		this.update(SQL_INSERT_TASKNODE, params);
	}
	
	private static final String SQL_UPDATE_TASKNODE_XY = "update sw_design_node set "
			+ "x = ?, y = ? where id = ?";
	
	/**
	 * 更新任务节点坐标信息
	 * @param id
	 * @param x
	 * @param y
	 */
	public void updateTaskNodePosition(String id, String x, String y){
		this.update(SQL_UPDATE_TASKNODE_XY, new Object[]{x, y, id});
	}
	
	/**
	 * 批量更新任务节点坐标信息。
	 * @param parameters 任务坐标集合，集合中每个对象为数组，里面有三个值，<br>
	 * 分别对应：x,y,id
	 */
	public void batchUpdateNodePositions(List<Object[]> parameters){
		this.batchUpdate(SQL_UPDATE_TASKNODE_XY, parameters);
	}
	
	private static final String SQL_UPDATE_TASKNODE_POS = "update sw_design_node set "
			+ "in_positions = ?, out_positions = ? where id = ?";
	
	public void updateTaskNodeInOutPos(String inPositions, String outPositions, String taskDefineId){
		this.update(SQL_UPDATE_TASKNODE_POS, new Object[]{inPositions, outPositions, taskDefineId});
	}
	
	private static final String SQL_MAX_TASKOUT_NUM = "select max(order_num) from SW_TASK_OUT";
	
	public int getMaxTaskoutOrder(){
		int result = this.getJdbcTemplate().queryForInt(SQL_MAX_TASKOUT_NUM);
		logger.debug("-----------> max = " + result);
		return result + 1;
	}
	
	private static final String SQL_INSERT_TASK_OUT = "insert into SW_TASK_OUT("
			+ "id,task_define_id,next_task,order_num,conditions,expression,default_task,"
			+ "process_define_id) values(?,?,?,?,?,?,?,?)";
	
	public void insertTaskout(TaskOut taskout){
		Object[] params = new Object[8];
		params[0] = taskout.getId();
		params[1] = taskout.getTaskDefineId();
		params[2] = taskout.getNextTask();
		params[3] = taskout.getOrderNum();
		params[4] = taskout.getCondition();
		params[5] = taskout.getExpression();
		params[6] = taskout.getDefaultTask();
		params[7] = taskout.getProcessDefineId();
		this.update(SQL_INSERT_TASK_OUT, params);
	}
	
	private static final String SQL_INSERT_ANCHOR_OUT = "insert into sw_design_anchor_out("
			+ "id,node_id,position,next_node,next_position,summary) values(?,?,?,?,?,?)";
	
	public void insertAnchorOut(AnchorOut anchorOut){
		Object[] params = new Object[6];
		params[0] = anchorOut.getId();
		params[1] = anchorOut.getTaskNodeId();
		params[2] = anchorOut.getPosition();
		params[3] = anchorOut.getNextNode();
		params[4] = anchorOut.getNextPosition();
		params[5] = anchorOut.getSummary();
		this.update(SQL_INSERT_ANCHOR_OUT, params);
	}
	
	private static final String SQL_INSERT_ANCHOR_IN = "insert into sw_design_anchor_in("
			+ "id,node_id,position,from_node,from_position) values(?,?,?,?,?)";
	
	public void insertAnchorIn(AnchorIn anchorIn){
		Object[] params = new Object[5];
		params[0] = anchorIn.getId();
		params[1] = anchorIn.getTaskNodeId();
		params[2] = anchorIn.getPosition();
		params[3] = anchorIn.getFromNode();
		params[4] = anchorIn.getFromPosition();
		this.update(SQL_INSERT_ANCHOR_IN, params);
	}
	
	private static final String SQL_DELETE_TASK_OUT = "delete from SW_TASK_OUT where "
			+ "process_define_id = ? and task_define_id = ? and next_task = ?";
	
	public void deleteTaskOut(String processDefineId, String taskDefineId, String nextTaskId){
		this.update(SQL_DELETE_TASK_OUT, new Object[]{processDefineId, taskDefineId, nextTaskId});
	}
	
	private static final String SQL_DELETE_TASK_OUT2 = "delete from SW_TASK_OUT where "
			+ "task_define_id = ? and next_task <> 'END'";
	public void deleteTaskoutWithoutEnd(String taskDefineId){
		this.update(SQL_DELETE_TASK_OUT2, new Object[]{taskDefineId});
	}
	
	private static final String SQL_DELETE_END_TASKOUT = "delete from SW_TASK_OUT where "
			+ "task_define_id = ? and next_task = 'END'";
	public void deleteEndTaskout(String taskDefineId){
		this.update(SQL_DELETE_END_TASKOUT, new Object[]{taskDefineId});
	}
	
	private static final String SQL_DELETE_TASK_IN = "delete from SW_TASK_IN where "
			+ "process_define_id = ? and task_define_id = ? and previous_task = ?";
	
	public void deleteTaskIn(String processDefineId, String taskDefineId, String previousTask){
		this.update(SQL_DELETE_TASK_IN, new Object[]{processDefineId, taskDefineId, previousTask});
	}
	
	private static final String SQL_DELETE_TASK_IN2 = "delete from SW_TASK_IN where "
			+ "task_define_id = ? and previous_task <> 'START'";
	public void deleteTaskInWithoutStart(String taskDefineId){
		this.update(SQL_DELETE_TASK_IN2, new Object[]{taskDefineId});
	}
	
	private static final String SQL_UPDATE_TASK_IN = "update SW_TASK_IN set previous_task = ? "
			+ "where process_define_id = ? and task_define_id = ?";
	
	/**
	 * 更新taskIn对应的上一步任务ID
	 * @param processDefineId
	 * @param taskDefineId
	 * @param previousTaskId
	 */
	public void updateTaskIn(String processDefineId
			, String taskDefineId, String previousTaskId){
		Object[] params = new Object[3];
		params[0] = previousTaskId;
		params[1] = processDefineId;
		params[2] = taskDefineId;
		this.update(SQL_UPDATE_TASK_IN, params);
	}
	
	private static final String SQL_UPDATE_TASK_OUT = "update SW_TASK_OUT set next_task = ? "
			+ "where process_define_id = ? and task_define_id = ?";
	
	public void updateTaskOut(String processDefineId
			, String taskDefineId, String nextTaskId){
		this.update(SQL_UPDATE_TASK_OUT, new Object[]{nextTaskId, processDefineId, taskDefineId});
	}
	
	private static final String SQL_DELETE_ANCHOR_OUT = "delete from sw_design_anchor_out "
			+ "where node_id = ? and position = ?";
	
	public void deleteAnchorOut(String nodeId, String position){
		this.update(SQL_DELETE_ANCHOR_OUT, new Object[]{nodeId, position});
	}
	
	private static final String SQL_DELETE_ANCHOR_OUT2 = "delete from sw_design_anchor_out "
			+ "where node_id = ?";
	public void deleteAllAnchorOut(String nodeId){
		this.update(SQL_DELETE_ANCHOR_OUT2, new Object[]{nodeId});
	}
	
	private static final String SQL_DELETE_ANCHOR_IN = "delete from sw_design_anchor_in "
			+ "where node_id = ? and position = ? and from_node = ?";
	
	public void deleteAnchorIn(String nodeId, String position, String fromTask){
		this.update(SQL_DELETE_ANCHOR_IN, new Object[]{nodeId, position, fromTask});
	}
	
	private static final String SQL_DELETE_ANCHOR_IN2 = "delete from sw_design_anchor_in "
			+ "where node_id = ?";
	public void deleteAllAnchorIn(String nodeId){
		this.update(SQL_DELETE_ANCHOR_IN2, new Object[]{nodeId});
	}
	
	private static final String SQL_UPDATE_ANCHOR_IN = "update sw_design_anchor_in set "
			+ "from_node = ?, from_position = ? where node_id = ? and position = ? and from_node = ?";
	
	public void updateAnchorIn(String fromNode
			, String fromPosition, String nodeId, String position, String oldSrcTaskId){
		this.update(SQL_UPDATE_ANCHOR_IN, new Object[]{fromNode, fromPosition, nodeId, position, oldSrcTaskId});
	}
	
	private static final String SQL_UPDATE_ANCHOR_OUT = "update sw_design_anchor_out set "
			+ "next_node = ?, next_position = ? where node_id = ? and position = ?";
	
	public void updateAnchorOut(String nextNode
			, String nextPosition, String nodeId, String position){
		this.update(SQL_UPDATE_ANCHOR_OUT, new Object[]{nextNode, nextPosition, nodeId, position});
	}
	
	private static final String SQL_UPDATE_ANCHOR_OUT_POS = "update sw_design_anchor_out set "
			+ "position = ? where node_id = ? and position = ?";
	
	public void updateAnchorOutPosition(String changedPosition
			, String nodeId, String oldPosition){
		this.update(SQL_UPDATE_ANCHOR_OUT_POS, new Object[]{changedPosition, nodeId, oldPosition});
	}
	
	private static final String SQL_UPDATE_ANCHOR_OUT_NEXT_POS = "update sw_design_anchor_out set "
			+ "next_position = ? where node_id = ? and position = ?";
	/**
	 * 仅改变anchorOut下一步的位置信息
	 * @param changedNextPosition
	 * @param nodeId
	 * @param outPosition
	 */
	public void updateAnchorOutNextPosition(String changedNextPosition
			, String nodeId, String outPosition){
		this.update(SQL_UPDATE_ANCHOR_OUT_NEXT_POS, new Object[]{changedNextPosition, nodeId, outPosition});
	}
	
	private static final String SQL_UPDATE_ANCHOR_IN_POS = "update sw_design_anchor_in set "
			+ "position = ? where node_id = ? and position = ? and from_node = ?";
	
	public void updateAnchorInPosition(String changedPosition
			, String nodeId, String oldPosition, String fromTask){
		this.update(SQL_UPDATE_ANCHOR_IN_POS, new Object[]{changedPosition, nodeId, oldPosition, fromTask});
	}
	
	private static final String SQL_UPDATE_ANCHOR_IN_PRE_POS = "update sw_design_anchor_in set "
			+ "from_position = ? where node_id = ? and position = ? and from_node = ?";
	public void updateAnchorInPrePosition(String changedFromPosition
			, String nodeId, String inPosition, String fromTask){
		this.update(SQL_UPDATE_ANCHOR_IN_PRE_POS, new Object[]{changedFromPosition, nodeId, inPosition, fromTask});
	}
	
	private static final String SQL_DELETE_TASK = "delete from SW_TASK_DEFINE where id = ?";
	
	public void deleteTaskDefine(String id){
		this.update(SQL_DELETE_TASK, new Object[]{id});
	}
	
	private static final String SQL_DELETE_TASK_NODE = "delete from sw_design_node where id = ?";
	
	public void deleteTaskNode(String id){
		this.update(SQL_DELETE_TASK_NODE, new Object[]{id});
	}
	
	/* 删除与给定任务ID关联的任务节点信息 */
	
	private static final String SQL_DELETE_TASKIN_ASSO = "delete from SW_TASK_IN where previous_task = ?";
	private static final String SQL_DELETE_TASKOUT_ASSO = "delete from SW_TASK_OUT where next_task = ?";
	
	public void deleteTaskInAsso(String taskDefineId){
		this.update(SQL_DELETE_TASKIN_ASSO, new Object[]{taskDefineId});
	}
	
	public void deleteTaskOutAsso(String taskDefineId){
		this.update(SQL_DELETE_TASKOUT_ASSO, new Object[]{taskDefineId});
	}
	
	/* 删除与给定任务ID关联的任务位置信息 */
	
	private static final String SQL_DELETE_ANCHORIN_ASSO = "delete from sw_design_anchor_in "
			+ "where from_node = ?";
	private static final String SQL_DELETE_ANCHOROUT_ASSO = "delete from sw_design_anchor_out "
			+ "where next_node = ?";
	
	public void deleteAnchorInAsso(String taskDefineId){
		this.update(SQL_DELETE_ANCHORIN_ASSO, new Object[]{taskDefineId});
	}
	public void deleteAnchorOutAsso(String taskDefineId){
		this.update(SQL_DELETE_ANCHOROUT_ASSO, new Object[]{taskDefineId});
	}
	
	private static final String SQL_UPDATE_SPLIT_CONDITION = "update SW_TASK_OUT set "
			+ "conditions=?, expression=? where task_define_id=? and next_task=?";
	
	/**
	 * 批量更新分拆任务中条件字段：用户输入条件、表达式
	 * @param parameters 集合中是数组，数组格式：用户条件、表达式、任务ID、下一步ID
	 */
	public void batchUpdateSplitConditions(List<Object[]> parameters){
		this.batchUpdate(SQL_UPDATE_SPLIT_CONDITION, parameters);
	}
	
	private static final String SQL_REMOVE_DEFAULT_TASK = "update SW_TASK_OUT set "
			+ "default_task=0 where task_define_id=?";
	private static final String SQL_UPDATE_DEFAULT_TASK = "update SW_TASK_OUT set "
			+ "default_task=1 where task_define_id=? and next_task=?";
	
	public void updateRemoveDefaultTask(String taskDefineId){
		this.update(SQL_REMOVE_DEFAULT_TASK, new Object[]{taskDefineId});
	}
	/**
	 * 更新分拆任务中，用户选择的默认任务字段
	 * @param taskDefineId
	 * @param nextTask
	 */
	public void updateDefaultTask(String taskDefineId, String nextTask){
		this.update(SQL_UPDATE_DEFAULT_TASK, new Object[]{taskDefineId, nextTask});
	}
	
	private static final String SQL_UPDATE_TASK_ACTOR = "update SW_TASK_DEFINE set "
			+ "actor_type = ? where id = ?";
	
	public void updateTaskActor(String actorType, String taskDefineId){
		this.update(SQL_UPDATE_TASK_ACTOR, new Object[]{actorType, taskDefineId});
	}
	
	private static final String SQL_UPDATE_TASK_END = "update SW_TASK_DEFINE set is_end = 1 where id = ?";
	private static final String SQL_UPDATE_TASK_END_NOT = "update SW_TASK_DEFINE set is_end = 0 where id = ?";
	
	public void updateTaskEnd(String taskDefineId){
		this.update(SQL_UPDATE_TASK_END, new Object[]{taskDefineId});
	}
	public void updateCancelTaskEnd(String taskDefineId){
		this.update(SQL_UPDATE_TASK_END_NOT, new Object[]{taskDefineId});
	}
	
	
	private static final String SQL_INSERT_TASK_ACTORASSIGN = "insert into SW_ACTOR_ASSIGN(id,task_define_id"
			+ ",actor_id,actor_name,order_num,process_define_id) values(?,?,?,?,?,?)";
	
	/**
	 * 批量保存actor_assign记录集合
	 * @param parameters
	 */
	public void insertTaskActorAssign(List<Object[]> parameters){
		this.batchUpdate(SQL_INSERT_TASK_ACTORASSIGN, parameters);
	}
	
	private static final String SQL_DELETE_TASK_ACTORASSIGN = "delete from SW_ACTOR_ASSIGN "
			+ "where task_define_id = ?";
	
	public void deleteTaskActorAssign(String taskDefineId){
		this.update(SQL_DELETE_TASK_ACTORASSIGN, new Object[]{taskDefineId});
	}
	
	private static final String SQL_UPDATE_PROCESS_STATUS = "update sw_process_define set "
			+ "publish_status = ? where id = ?";
	/**
	 * 更新流程发布状态
	 * @param status 发布状态：0_未发布，1_已发布
	 * @param processDefineId
	 */
	public void  updateProcessPublishStatus(int status, String processDefineId){
		this.update(SQL_UPDATE_PROCESS_STATUS, new Object[]{status, processDefineId});
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// anchor cache
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private static final String SQL_SELECT_NODES = "select * from sw_design_node order by id";
	private static final String SQL_SELECT_ANCHOR_IN = "select * from sw_design_anchor_in";
	private static final String SQL_SELECT_ANCHOR_OUT = "select * from sw_design_anchor_out";
	
	private RowMapper<TaskNode> taskNodeRowMapper = new TaskNodeRowMapper();
	private RowMapper<AnchorIn> anchorInRowMapper = new AnchorInRowMapper();
	private RowMapper<AnchorOut> anchorOutRowMapper = new AnchorOutRowMapper();
	
	public List<TaskNode> getAllTaskNodeList(){
		return this.getJdbcTemplate().query(SQL_SELECT_NODES, taskNodeRowMapper);
	}
	
	public List<AnchorIn> getAllAnchorInList(){
		return this.getJdbcTemplate().query(SQL_SELECT_ANCHOR_IN, anchorInRowMapper);
	}
	
	public List<AnchorOut> getAllAnchorOutList(){
		return this.getJdbcTemplate().query(SQL_SELECT_ANCHOR_OUT, anchorOutRowMapper);
	}
	
	private class TaskNodeRowMapper implements RowMapper<TaskNode> {
		@Override
		public TaskNode mapRow(ResultSet rs, int index) throws SQLException {
			return new TaskNode().setId(rs.getString("id"))
					.setX(rs.getString("x"))
					.setY(rs.getString("y"))
					.setInPositions(rs.getString("in_positions"))
					.setOutPositions(rs.getString("out_positions"));
		}
	}
	
	private class AnchorInRowMapper implements RowMapper<AnchorIn> {
		@Override
		public AnchorIn mapRow(ResultSet rs, int arg1) throws SQLException {
			return new AnchorIn().setId(rs.getString("id"))
					.setTaskNodeId(rs.getString("node_id"))
					.setPosition(rs.getString("position"))
					.setFromNode(rs.getString("from_node"))
					.setFromPosition(rs.getString("from_position"));
		}
	}
	
	private class AnchorOutRowMapper implements RowMapper<AnchorOut> {
		@Override
		public AnchorOut mapRow(ResultSet rs, int arg1) throws SQLException {
			return new AnchorOut().setId(rs.getString("id"))
					.setTaskNodeId(rs.getString("node_id"))
					.setPosition(rs.getString("position"))
					.setNextNode(rs.getString("next_node"))
					.setNextPosition(rs.getString("next_position"))
					.setSummary(rs.getString("summary"));
		}
	}
}
