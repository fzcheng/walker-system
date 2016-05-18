package com.walkersoft.flow.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.flow.core.actor.AbstractBaseActor;
import com.walker.flow.core.actor.BusinessExpendActor;
import com.walker.flow.core.actor.SelectScopeAndFixActor;
import com.walker.flow.core.actor.SelectScopeListActor;
import com.walker.flow.core.actor.SelectScopeTreeActor;
import com.walker.flow.core.actor.SingleGivenActor;
import com.walker.flow.core.actor.node.AbstractActorNode;
import com.walker.flow.meta.ActorAssign;
import com.walker.flow.meta.ProcessDefine;
import com.walker.flow.meta.TaskBaseBehavior;
import com.walker.flow.meta.TaskBaseBehavior.NodeType;
import com.walker.flow.meta.TaskDefineAbstract;
import com.walker.flow.meta.TaskIn;
import com.walker.flow.meta.TaskOut;
import com.walker.flow.meta.definition.AffluenceTask;
import com.walker.flow.meta.definition.EdgeTask;
import com.walker.flow.meta.definition.SingleTask;
import com.walker.flow.meta.definition.SplitAndAffluenceTask;
import com.walker.flow.meta.definition.SplitTask;
import com.walker.flow.meta.definition.SubProcessTask;
import com.walker.infrastructure.utils.NumberGenerator;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.MyApplicationConfig;
import com.walkersoft.flow.MiscUtils;
import com.walkersoft.flow.TaskConditionAction;
import com.walkersoft.flow.manager.DesignManagerImpl;
import com.walkersoft.flow.pojo.AnchorIn;
import com.walkersoft.flow.pojo.AnchorOut;
import com.walkersoft.flow.pojo.PositionAjustor;
import com.walkersoft.flow.pojo.TaskNode;

/**
 * 流程设计器界面操作
 * @author shikeying
 *
 */
@Controller
public class DesignAction extends TaskConditionAction {

	private static final String URL_DESIGN = FLOW_BASE + "define/flow_design";
	
	private static final String URL_DESIGN_START = FLOW_BASE + "define/design_start";
	private static final String URL_ADD_SINGLE = FLOW_BASE + "define/task_add_single";
	private static final String URL_ADD_SPLIT = FLOW_BASE + "define/task_add_split";
	private static final String URL_ADD_AFFLUENCE = FLOW_BASE + "define/task_add_afflu";
	private static final String URL_ADD_SPLIT_AFF = FLOW_BASE + "define/task_add_affluencesplit";
	private static final String URL_ADD_SUBPROCESS = FLOW_BASE + "define/task_add_subprocess";
	private static final String URL_ADD_EDGE = FLOW_BASE + "define/task_add_single";
	
	private static final String URL_DESIGN_AJUST_POS = FLOW_BASE + "define/design_edit_position";
	private static final String URL_DESIGN_ACTOR = FLOW_BASE + "actor/actor_option";
	
	private static final String ATTR_TASK_START = "start";
	private static final String ATTR_PROCESS_ID = "processDefineId";
	private static final String ATTR_TASK = "task";
	private static final String ATTR_ACTOR_TYPE = "actorType";
	private static final String ATTR_TASK_IN_LISTENER = "taskInListener";
	private static final String ATTR_TASK_OUT_LISTENER = "taskOutListener";
	private static final String ATTR_TASK_LIST = "taskList";
	private static final String ATTR_TASK_NODE_LIST = "taskNodeList";
	private static final String ATTR_TASK_CONNECTION = "taskConnection";
	private static final String ATTR_TASK_IN_MAP = "inPositions";
	private static final String ATTR_TASK_OUT_MAP = "outPositions";
	private static final String ATTR_TASK_OUT_LIST = "taskOutList";
	private static final String ATTR_TASK_EDIT_MODE = "isEdit";
	private static final String ATTR_TASK_TYPE = "taskType";
	
	private static final String ATTR_ACTOR_MODEL = "actor_type";
	private static final String ATTR_ACTOR_BEAN = "actor_bean";
	private static final String ATTR_ACTOR_VALUE = "actor_value";
	
	private static final String PARAMETER_TASKTYPE = "taskType";
	private static final String PARAMETER_PROCESS_ID = "processDefineId";
	private static final String PARAMETER_IS_START = "isStart";
	private static final String PARAMETER_SRC_TASK_ID = "srcTask";
	private static final String PARAMETER_DEST_TASK_ID = "destTask";
	private static final String PARAMETER_SRC_OUT_POSITION = "srcOutPosition";
	private static final String PARAMETER_DEST_IN_POSITION = "destInPosition";
	private static final String PARAMETER_NODE_POSITIONS = "positions";
	private static final String PARAMETER_NEXT_IDS = "p_nextIds";
	private static final String PARAMETER_CONDITIONS = "p_conditions";
	private static final String PARAMETER_DEFAULT_TASK = "defaultTask";
	
	private static final String PARAMETER_ACTOR_TYPE = "actor_type";
	private static final String PARAMETER_ACTOR_BEAN = "actor_bean";
	private static final String PARAMETER_ACTOR_VALUE = "actor_value";
	
	private static final String JSPLUMB_NODE_PREFIX = "Window";
	
	private static final String DRAFT_PARAM_SPLIT = "&";

	@Autowired
	private DesignManagerImpl designManager;
	
	@RequestMapping("permit/flow/define/design")
	public String index(Model model){
		String processDefineId = this.getParameter(PARAM_ID);
		doViewProcessDefine(model, processDefineId);
		return URL_DESIGN;
	}
	
	@RequestMapping("permit/flow/define/view")
	public String viewProcessDefine(Model model){
		String processDefineId = this.getParameter(PARAM_ID);
		doViewProcessDefine(model, processDefineId);
		return URL_VIEW_PROCESS;
	}
	
	private void doViewProcessDefine(Model model, String processDefineId){
		assert (StringUtils.isNotEmpty(processDefineId));
		List<TaskDefineAbstract> taskList = this.getTaskDefineList(processDefineId);
		List<TaskNode> taskNodeList = this.getTaskAvailablePositions(getTaskIds(taskList));
		model.addAttribute(ATTR_PROCESS_DEFINE, getFlowCacheFactory().getProcessDefine(processDefineId));
		model.addAttribute(ATTR_TASK_LIST, taskList);
		model.addAttribute(ATTR_TASK_NODE_LIST, taskNodeList);
		model.addAttribute(ATTR_TASK_CONNECTION, getTaskConnectionInfo(taskNodeList));
	}
	
	private List<String> getTaskIds(List<TaskDefineAbstract> taskList){
		List<String> ids = new ArrayList<String>();
		if(taskList != null){
			for(TaskDefineAbstract task : taskList){
				ids.add(task.getId());
			}
		}
		return ids;
	}
	
	/**
	 * 返回界面中节点连接线的数组信息，如：<br>
	 * instance.connect({uuids:["Window2BottomCenter", "Window3TopCenter"], editable:true});
	 * @param taskNodeList
	 * @return
	 */
	private List<String> getTaskConnectionInfo(List<TaskNode> taskNodeList){
		List<String> result = null;
		if(taskNodeList != null){
			result = new ArrayList<String>();
			Iterator<AnchorOut> outList = null;
			StringBuilder aRecord = null;
			for(TaskNode tn : taskNodeList){
				outList = tn.getAnchorsOutList();
				if(outList != null){
					AnchorOut ao = null;
					for(Iterator<AnchorOut> it = outList; it.hasNext();){
						ao = it.next();
						aRecord = new StringBuilder();
						aRecord.append(JSPLUMB_NODE_PREFIX);
						aRecord.append(ao.getTaskNodeId());
						aRecord.append(ao.getPosition());
						aRecord.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
						aRecord.append(JSPLUMB_NODE_PREFIX);
						aRecord.append(ao.getNextNode());
						aRecord.append(ao.getNextPosition());
						result.add(aRecord.toString());
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 创建新任务界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "permit/flow/design/create")
	public String createTask(Model model){
		String processDefineId = this.getParameter(PARAM_ID);
		assert (StringUtils.isNotEmpty(processDefineId));
		ProcessDefine pd = getFlowCacheFactory().getProcessDefineWithTasks(processDefineId);
		if(pd == null) throw new IllegalArgumentException("processDefine not found in cache.");
		if(pd != null && pd.getAllTaskList() != null && pd.getAllTaskList().size() > 0){
			logger.debug("........跳转到新任务界面");
			model.addAttribute(ATTR_TASK_START, false);
		} else {
			logger.debug("........跳转到开始任务");
			model.addAttribute(ATTR_TASK_START, true);
		}
		model.addAttribute(ATTR_PROCESS_ID, processDefineId);
		return URL_DESIGN_START;
	}
	
	@RequestMapping(value = "permit/flow/design/showCreateTask")
	public String showCreateTask(Model model){
		String taskType = this.getParameter(PARAMETER_TASKTYPE);
		String processDefineId = this.getParameter(PARAMETER_PROCESS_ID);
		String isStart = this.getParameter(PARAMETER_IS_START);
		assert (StringUtils.isNotEmpty(taskType));
		assert (StringUtils.isNotEmpty(processDefineId));
		assert (StringUtils.isNotEmpty(isStart));
		
		model.addAttribute(ATTR_TASK_EDIT_MODE, false);
		model.addAttribute(ATTR_PROCESS_ID, processDefineId);
		model.addAttribute(ATTR_TASK_START, Boolean.valueOf(isStart) == true ? "1" : "0");
		model.addAttribute(ATTR_ACTOR_TYPE, getActorTypeInfo());
		model.addAttribute(ATTR_TASK_IN_LISTENER, getTaskInListenerInfo());
		model.addAttribute(ATTR_TASK_OUT_LISTENER, getTaskOutListenerInfo());
		model.addAttribute(ATTR_TASK_TYPE, taskType);
		
		// 选择进入不同任务类型创建界面
		if(taskType.equals(TaskBaseBehavior.TASK_TYPE_SINGLE)){
			return URL_ADD_SINGLE;
		} else if(taskType.equals(TaskBaseBehavior.TASK_TYPE_SPLIT)){
			return URL_ADD_SPLIT;
		} else if(taskType.equals(TaskBaseBehavior.TASK_TYPE_AFFLUENCE)){
			return URL_ADD_AFFLUENCE;
		} else if(taskType.equals(TaskBaseBehavior.TASK_TYPE_SPLIT_AFFLUENCE)){
			return URL_ADD_SPLIT_AFF;
		} else if(taskType.equals(TaskBaseBehavior.TASK_TYPE_SUBPROCESS)){
			return URL_ADD_SUBPROCESS;
		} else if(taskType.equals(TaskBaseBehavior.TASK_TYPE_EDGE)){
			return URL_ADD_EDGE;
		} else
			throw new IllegalArgumentException("unsupported task type: " + taskType);
	}
	
	@RequestMapping(value = "permit/flow/design/show_edit_task")
	public String showEditTask(String id, Model model){
		assert (StringUtils.isNotEmpty(id));
		TaskDefineAbstract task = getTaskDefine(id);
		String taskType = task.getNodeTypeIndex();
		model.addAttribute(ATTR_TASK_EDIT_MODE, true);
		model.addAttribute(ATTR_PROCESS_ID, task.getId());
		model.addAttribute(ATTR_TASK_START, task.isStart() == true ? "1" : "0");
		model.addAttribute(ATTR_TASK, task);
		model.addAttribute(ATTR_ACTOR_TYPE, getActorTypeInfo());
		model.addAttribute(ATTR_TASK_IN_LISTENER, getTaskInListenerInfo());
		model.addAttribute(ATTR_TASK_OUT_LISTENER, getTaskOutListenerInfo());
		
		// 选择进入不同任务类型创建界面
		if(taskType.equals(TaskBaseBehavior.TASK_TYPE_SINGLE)){
			return URL_ADD_SINGLE;
		} else if(taskType.equals(TaskBaseBehavior.TASK_TYPE_SPLIT)){
			model.addAttribute(ATTR_TASK_OUT_LIST, getTaskOutList(id));
			return URL_ADD_SPLIT;
		} else if(taskType.equals(TaskBaseBehavior.TASK_TYPE_AFFLUENCE)){
			return URL_ADD_AFFLUENCE;
		} else if(taskType.equals(TaskBaseBehavior.TASK_TYPE_SPLIT_AFFLUENCE)){
			return URL_ADD_SPLIT_AFF;
		} else if(taskType.equals(TaskBaseBehavior.TASK_TYPE_SUBPROCESS)){
			return URL_ADD_SUBPROCESS;
		} else if(taskType.equals(TaskBaseBehavior.TASK_TYPE_EDGE)){
			return URL_ADD_EDGE;
		} else
			throw new IllegalArgumentException("unsupported task type: " + taskType);
	}
	
	@RequestMapping(value = "permit/flow/design/save_single")
	public void saveSingleTask(@ModelAttribute("entity") SingleTask entity
			, HttpServletResponse response) throws IOException{
		doSaveTask(entity);
	}
	
	private void doSaveTask(TaskDefineAbstract entity) throws IOException{
		if(entity == null)
			throw new IllegalArgumentException("saved taskDefine not found.");
		TaskIn taskIn = null;
		if(entity.isStart()){
			taskIn = new TaskIn();
			taskIn.setId(MiscUtils.generateSortId());
//			taskIn.setTaskDefineId(taskDefineId); // 在manager层获取
			taskIn.setProcessDefineId(entity.getProcessDefineId());
			taskIn.setPreviousTask(TaskBaseBehavior.START_NAME);
		}
		TaskNode taskNode = designManager.execSaveTask(entity, taskIn);
		
		// 加入缓存
		addTaskToCache(entity, taskIn, taskNode);
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	@RequestMapping(value = "permit/flow/design/save_split")
	public void saveSplitTask(@ModelAttribute("entity") SplitTask entity
			, HttpServletResponse response) throws IOException{
		doSaveTask(entity);
	}
	
	@RequestMapping(value = "permit/flow/design/save_affluence")
	public void saveAffluenceTask(@ModelAttribute("entity") AffluenceTask entity
			, HttpServletResponse response) throws IOException{
		doSaveTask(entity);
	}
	
	@RequestMapping(value = "permit/flow/design/save_edge")
	public void saveEdgeTask(@ModelAttribute("entity") EdgeTask entity
			, HttpServletResponse response) throws IOException{
		doSaveTask(entity);
	}
	
	@RequestMapping(value = "permit/flow/design/save_split_affluence")
	public void saveAffluenceSplitTask(@ModelAttribute("entity") SplitAndAffluenceTask entity
			, HttpServletResponse response) throws IOException{
		doSaveTask(entity);
	}
	
	@RequestMapping(value = "permit/flow/design/save_subporcess")
	public void saveSubporcessTask(@ModelAttribute("entity") SubProcessTask entity
			, HttpServletResponse response) throws IOException{
		doSaveTask(entity);
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// 编辑保存任务数据方法
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	@RequestMapping(value = "permit/flow/design/edit_single")
	public void saveEditSingleTask(@ModelAttribute("entity") SingleTask entity
			, HttpServletResponse response) throws IOException{
		doSaveEditTask(entity);
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	/**
	 * 编辑保存的任务信息，只包括任务的基本通用信息，不包括特性信息，如：分拆、汇聚等
	 * @param entity
	 */
	private void doSaveEditTask(TaskDefineAbstract entity){
		if(entity == null || StringUtils.isEmpty(entity.getId()))
			throw new IllegalArgumentException("edit taskDefine not found.");
		designManager.execEditTask(entity);
		updateTaskCache(entity);
	}
	
	@RequestMapping(value = "permit/flow/design/edit_split")
	public void saveEditSplitTask(@ModelAttribute("entity") SplitTask entity
			, HttpServletResponse response) throws IOException{
		// 更新实体，
		doEditSplitTask(entity);
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	private void doEditSplitTask(TaskDefineAbstract entity){
		if(StringUtils.isEmpty(entity.getId())){
			throw new IllegalArgumentException("taskId not found.");
		}
		// 接收的条件参数
		List<Object[]> conditionList = null;
		// 接收的默认选择任务
		String defaultTask = null;
		
		// 如果存在条件，更新条件表达式
		if(entity.getSplitType() == TaskBaseBehavior.SPLIT_TYPE_CONDITION){
			String nextIds = this.getParameter(PARAMETER_NEXT_IDS);
			String conditions = this.getParameter(PARAMETER_CONDITIONS);
			if(StringUtils.isEmpty(nextIds) || StringUtils.isEmpty(conditions)){
				throw new IllegalArgumentException("nextIds or conditions must be set in splitTask.");
			}
			String[] nextIdsArray = nextIds.split(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			String[] conditionArray = conditions.split(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			conditionList = new ArrayList<Object[]>();
			Object[] obj = null;
			for(int i=0; i<nextIdsArray.length; i++){
				obj = new Object[4];
				obj[0] = conditionArray[i]; // 用户输入表达式
				obj[1] = getExpression(conditionArray[i].toString()); // 系统转换表达式
				obj[2] = entity.getId(); // 任务ID
				obj[3] = nextIdsArray[i];// 下一步任务ID
				conditionList.add(obj);
			}
		} else if(entity.getSplitType() == TaskBaseBehavior.SPLIT_TYPE_USERSELECT){
			// 如果用户选择，更新默认选择
			defaultTask = this.getParameter(PARAMETER_DEFAULT_TASK);
			if(StringUtils.isEmpty(defaultTask)) throw new IllegalArgumentException("defaultTask not found.");
		}
		designManager.execEditSplitTask(entity, conditionList, defaultTask);
		// 更新缓存
		updateSplitTaskCache(entity, conditionList, defaultTask);
	}
	
	@RequestMapping(value = "permit/flow/design/edit_affluence")
	public void saveEditAffluenceTask(@ModelAttribute("entity") AffluenceTask entity
			, HttpServletResponse response) throws IOException{
		doSaveEditTask(entity);
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	@RequestMapping(value = "permit/flow/design/edit_split_affluence")
	public void saveEditSplitAffuenceTask(@ModelAttribute("entity") SplitAndAffluenceTask entity
			, HttpServletResponse response) throws IOException{
		doEditSplitTask(entity);
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	@RequestMapping(value = "permit/flow/design/edit_edge")
	public void saveEditEdgeTask(@ModelAttribute("entity") EdgeTask entity
			, HttpServletResponse response) throws IOException{
		doSaveEditTask(entity);
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	@RequestMapping(value = "permit/flow/design/edit_subprocess")
	public void saveEditSubprocessTask(@ModelAttribute("entity") SubProcessTask entity
			, HttpServletResponse response) throws IOException{
		doSaveEditTask(entity);
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// 连接、编辑任务节点方法
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@RequestMapping(value = "permit/flow/design/new_connection")
	public void createTaskConnection(HttpServletResponse response) throws IOException{
		String processDefineId = this.getParameter(PARAMETER_PROCESS_ID);
		String srcTaskDefineId = this.getParameter(PARAMETER_SRC_TASK_ID);
		String destTaskDefineId= this.getParameter(PARAMETER_DEST_TASK_ID);
		String srcOutPosition  = this.getParameter(PARAMETER_SRC_OUT_POSITION);
		String destInPosition  = this.getParameter(PARAMETER_DEST_IN_POSITION);
		
		assert (StringUtils.isNotEmpty(processDefineId));
		assert (StringUtils.isNotEmpty(srcTaskDefineId));
		assert (StringUtils.isNotEmpty(destTaskDefineId));
		assert (StringUtils.isNotEmpty(srcOutPosition));
		assert (StringUtils.isNotEmpty(destInPosition));
		
		// 对于同一个任务，不允许有两个输出节点同时连接到同一个目的节点，避免重复连接
		if(isSameTaskConnection(srcTaskDefineId, destTaskDefineId)){
			this.ajaxOutPutText("已经存在该连接，同一个任务连接不能重复创建。");
			return;
		}
		
		Object[] result = designManager.execNewConnection(processDefineId
				, srcTaskDefineId, srcOutPosition, destTaskDefineId, destInPosition);
		
		// 更新缓存对象
		this.addTaskConnection((TaskOut)result[0], (TaskIn)result[1]
				, (AnchorOut)result[2], (AnchorIn)result[3]);
		
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	@RequestMapping(value = "permit/flow/design/del_connection")
	public void deleteTaskConnection(HttpServletResponse response) throws IOException{
		String processDefineId = this.getParameter(PARAMETER_PROCESS_ID);
		String srcTaskDefineId = this.getParameter(PARAMETER_SRC_TASK_ID);
		String destTaskDefineId= this.getParameter(PARAMETER_DEST_TASK_ID);
		String srcOutPosition  = this.getParameter(PARAMETER_SRC_OUT_POSITION);
		String destInPosition  = this.getParameter(PARAMETER_DEST_IN_POSITION);
		
		assert (StringUtils.isNotEmpty(processDefineId));
		assert (StringUtils.isNotEmpty(srcTaskDefineId));
		assert (StringUtils.isNotEmpty(destTaskDefineId));
		assert (StringUtils.isNotEmpty(srcOutPosition));
		assert (StringUtils.isNotEmpty(destInPosition));
		
		designManager.execDeleteConnection(processDefineId
				, srcTaskDefineId, srcOutPosition, destTaskDefineId, destInPosition);
		// 更新缓存
		this.removeTaskConnection(srcTaskDefineId, destTaskDefineId, srcOutPosition, destInPosition);
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	private static final String PARAMETER_OLD_SRC_TASK_ID = "oldSrcTask";
	private static final String PARAMETER_CNN_SRC_TASK_ID = "chnSrcTask";
	private static final String PARAMETER_OLD_SRC_POSITION = "oldSrcPos";
	private static final String PARAMETER_CHN_SRC_POSITION = "chnSrcPos";
	private static final String PARAMETER_TARGET_TASK_ID = "targetTask";
	private static final String PARAMETER_TARGET_POSITION = "targetPos";
	
	/**
	 * 改变连接线源节点的位置，从一个任务移动到另一个任务
	 * @param response
	 */
	@RequestMapping(value = "permit/flow/design/change_src_ep")
	public void changeSrcEndpoint(HttpServletResponse response) throws IOException{
		String oldSrcTaskId       = this.getParameter(PARAMETER_OLD_SRC_TASK_ID);
		String changedSrcTaskId   = this.getParameter(PARAMETER_CNN_SRC_TASK_ID);
		String oldSrcPosition     = this.getParameter(PARAMETER_OLD_SRC_POSITION);
		String changedSrcPosition = this.getParameter(PARAMETER_CHN_SRC_POSITION);
		String targetTaskId       = this.getParameter(PARAMETER_TARGET_TASK_ID);
		String targetPosition     = this.getParameter(PARAMETER_TARGET_POSITION);
		String processDefineId    = this.getParameter(PARAMETER_PROCESS_ID);
		
		assert (StringUtils.isNotEmpty(oldSrcTaskId));
		assert (StringUtils.isNotEmpty(changedSrcTaskId));
		assert (StringUtils.isNotEmpty(oldSrcPosition));
		assert (StringUtils.isNotEmpty(changedSrcPosition));
		assert (StringUtils.isNotEmpty(targetTaskId));
		assert (StringUtils.isNotEmpty(targetPosition));
		assert (StringUtils.isNotEmpty(processDefineId));
		
		// 对于同一个任务，不允许有两个输出节点同时连接到同一个目的节点，避免重复连接
		if(isSameTaskConnection(changedSrcTaskId, targetTaskId)){
			this.ajaxOutPutText("已经存在该连接，同一个任务连接不能重复创建。");
			return;
		}
		
		Object[] result = designManager.execChangeSrcEndpoint(oldSrcTaskId, changedSrcTaskId
				, oldSrcPosition, changedSrcPosition
				, targetTaskId, targetPosition, processDefineId);
		
		if(result == null || result.length != 2){
			throw new IllegalArgumentException();
		}
		this.updateChangeSrcEndpoint(oldSrcTaskId, changedSrcTaskId
				, oldSrcPosition, changedSrcPosition, targetTaskId, targetPosition
				, processDefineId, (TaskOut)result[0], (AnchorOut)result[1]);
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	private static final String PARAMETER_OLD_TARGET_TASK = "oldTargetTask";
	private static final String PARAMETER_CHN_TARGET_TASK = "chnTargetTask";
	private static final String PARAMETER_OLD_TARGET_POSITION = "oldTargetPos";
	private static final String PARAMETER_CHN_TARGET_POSITION = "chnTargetPos";
	private static final String PARAMETER_SOURCE_TASK_ID = "sourceTask";
	private static final String PARAMETER_SOURCE_POSITION = "sourcePos";
	
	@RequestMapping(value = "permit/flow/design/change_dest_ep")
	public void changeDestEndpoint(HttpServletResponse response) throws IOException{
		String oldTargetTaskId       = this.getParameter(PARAMETER_OLD_TARGET_TASK);
		String changedTargetTaskId   = this.getParameter(PARAMETER_CHN_TARGET_TASK);
		String oldTargetPosition     = this.getParameter(PARAMETER_OLD_TARGET_POSITION);
		String changedTargetPosition = this.getParameter(PARAMETER_CHN_TARGET_POSITION);
		String sourceTaskId          = this.getParameter(PARAMETER_SOURCE_TASK_ID);
		String sourcePosition        = this.getParameter(PARAMETER_SOURCE_POSITION);
		String processDefineId       = this.getParameter(PARAMETER_PROCESS_ID);
		
		assert (StringUtils.isNotEmpty(oldTargetTaskId));
		assert (StringUtils.isNotEmpty(changedTargetTaskId));
		assert (StringUtils.isNotEmpty(oldTargetPosition));
		assert (StringUtils.isNotEmpty(changedTargetPosition));
		assert (StringUtils.isNotEmpty(sourceTaskId));
		assert (StringUtils.isNotEmpty(sourcePosition));
		assert (StringUtils.isNotEmpty(processDefineId));
		
		// 对于同一个任务，不允许有两个输出节点同时连接到同一个目的节点，避免重复连接
		if(isSameTaskConnection(sourceTaskId, changedTargetTaskId)){
			this.ajaxOutPutText("已经存在该连接，同一个任务连接不能重复创建。");
			return;
		}
				
		Object[] result = designManager.execChangeDestEndpoint(
				oldTargetTaskId, changedTargetTaskId
				, oldTargetPosition, changedTargetPosition
				, sourceTaskId, sourcePosition, processDefineId);
		if(result == null || result.length != 2){
			throw new IllegalArgumentException();
		}
		this.updateChangeDestEndpoint(oldTargetTaskId, changedTargetTaskId
				, oldTargetPosition, changedTargetPosition, sourceTaskId, sourcePosition
				, processDefineId, (TaskIn)result[0], (AnchorIn)result[1]);
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	@RequestMapping(value = "permit/flow/design/change_src_pos")
	public void changeSrcPosition(HttpServletResponse response) throws IOException{
		String sourceTaskId = this.getParameter(PARAMETER_SOURCE_TASK_ID);
		String oldSourcePosition = this.getParameter(PARAMETER_OLD_SRC_POSITION);
		String changedSourcePosition = this.getParameter(PARAMETER_CHN_SRC_POSITION);
		String targetTaskId = this.getParameter(PARAMETER_TARGET_TASK_ID);
		String targetPosition = this.getParameter(PARAMETER_TARGET_POSITION);
		
		assert (StringUtils.isNotEmpty(sourceTaskId));
		assert (StringUtils.isNotEmpty(oldSourcePosition));
		assert (StringUtils.isNotEmpty(changedSourcePosition));
		assert (StringUtils.isNotEmpty(targetTaskId));
		assert (StringUtils.isNotEmpty(targetPosition));
		
		designManager.execChangeSrcPosition(sourceTaskId
				, oldSourcePosition, changedSourcePosition
				, targetTaskId, targetPosition);
		
		this.updateChangeSrcPosition(sourceTaskId
				, oldSourcePosition, changedSourcePosition
				, targetTaskId, targetPosition);
		
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	@RequestMapping(value = "permit/flow/design/change_dest_pos")
	public void changeDestPosition(HttpServletResponse response) throws IOException{
		String targetTaskId = this.getParameter(PARAMETER_TARGET_TASK_ID);
		String fromTask = this.getParameter(PARAMETER_SOURCE_TASK_ID);
		String fromPosition = this.getParameter(PARAMETER_SOURCE_POSITION);
		String oldTargetPosition = this.getParameter(PARAMETER_OLD_TARGET_POSITION);
		String changedTargetPosition = this.getParameter(PARAMETER_CHN_TARGET_POSITION);
		
		assert (StringUtils.isNotEmpty(targetTaskId));
		assert (StringUtils.isNotEmpty(fromTask));
		assert (StringUtils.isNotEmpty(fromPosition));
		assert (StringUtils.isNotEmpty(oldTargetPosition));
		assert (StringUtils.isNotEmpty(changedTargetPosition));
		
		designManager.execChangeDestPosition(targetTaskId
				, fromTask, fromPosition, oldTargetPosition, changedTargetPosition);
		
		this.updateChangeDestPosition(targetTaskId
				, fromTask, fromPosition, oldTargetPosition, changedTargetPosition);
		
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	/**
	 * 保存画布草稿（位置）
	 * @param positions 
	 * 位置信息集合，<br>
	 * 集合中每个元素格式为：任务ID&距离顶部距离&距离左边距离，<br>
	 * 例如：1&408&60,2&84&432 
	 * @param response
	 */
	@RequestMapping(value = "permit/flow/design/save_draft")
	public void saveDraft(HttpServletResponse response) throws IOException{
		String positions = this.getParameter(PARAMETER_NODE_POSITIONS);
		if(StringUtils.isEmpty(positions)) throw new IllegalArgumentException();
		List<Object[]> parameters = new ArrayList<Object[]>();
		Object[] p = null;
		for(String s : positions.split(StringUtils.DEFAULT_SPLIT_SEPARATOR)){
			p = s.split(DRAFT_PARAM_SPLIT);
			parameters.add(p);
		}
		// 保存数据库
		designManager.execUpdateNodePositions(parameters);
		// 更新缓存
		updateTaskNodePositionCache(parameters);
		// 返回客户端成功
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// 以下为设计器右键弹出菜单功能
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@RequestMapping(value = "permit/flow/design/remove_task")
	public void removeTask(String id, HttpServletResponse response) throws IOException{
		assert (StringUtils.isNotEmpty(id));
		designManager.execDeleteTask(id);
		removeTaskCache(id);
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	@RequestMapping(value = "permit/flow/design/show_ajust_pos")
	public String showAjustPosition(String id, Model model){
		assert (StringUtils.isNotEmpty(id));
		TaskDefineAbstract task = getTaskDefine(id);
		if(task == null) throw new IllegalStateException("task not found in cache: " + id);
		model.addAttribute(ATTR_TASK, task);
		model.addAttribute(ATTR_TASK_IN_MAP, getTaskNodeInPositions(id));
		model.addAttribute(ATTR_TASK_OUT_MAP, getTaskNodeOutPositions(id));
		return URL_DESIGN_AJUST_POS;
	}
	
	@RequestMapping(value = "permit/flow/design/save_ajust_pos")
	public void ajustPosition(@ModelAttribute("entity") PositionAjustor entity
			, HttpServletResponse response) throws IOException{
		assert (entity != null);
		logger.debug("+++++++++++++++ position: " + entity);
		String taskId = entity.getTaskDefineId();
		if(entity.isSelectNothing()){
			this.ajaxOutPutText("没有打开任何选项，没有提交任何数据。");
			return;
		}
		
		TaskDefineAbstract task = getTaskDefine(taskId);
		if(task.getNodeType() == NodeType.singleTask && !entity.validateSingleTask()){
			this.ajaxOutPutText("单任务只能选择一个输入、一个输出选项。");
			return;
		}
		if(task.getNodeType() == NodeType.splitTask && !entity.validateSplitTask()){
			this.ajaxOutPutText("分拆任务只能选择一个输入选项和多个输出选项。");
			return;
		}
		if(task.getNodeType() == NodeType.affluenceTask && !entity.validateAffluenceTask()){
			this.ajaxOutPutText("汇聚任务只能选择多个输入选项和一个输出选项。");
			return;
		}
		if(task.getNodeType() == NodeType.splitAndAffluenceTask 
				&& !entity.validateSplitAndAffluence()){
			this.ajaxOutPutText("分拆+汇聚任务只能选择多个输入选项和多个输出选项。");
			return;
		}
		if(task.getNodeType() == NodeType.edgeTask && !entity.validateEdgeTask()){
			this.ajaxOutPutText("边界任务只能选择一个输入选项，不能选择输出。");
			return;
		}
		if(task.getNodeType() == NodeType.subProcessTask){
			if(task.isStart() && !entity.validateSplitTask()){
				this.ajaxOutPutText("子流程任务作为开始节点，只能选择一个输入和多个输出。");
				return;
			}
			if(!task.isStart() && !entity.validateSplitAndAffluence()){
				this.ajaxOutPutText("子流程任务必须选择不少于1个输入和不少于1个输出选项。");
				return;
			}
		}
		if(entity.isSelectOnlyInOrOut() && !task.isStart()){
			this.ajaxOutPutText("输入、输出节点都要选择，不允许只选择一种。");
			return;
		}
		TaskNode taskNode = this.getTaskNode(taskId);
		logger.debug("taskNode.outPosition: " + taskNode.getOutPositions());
		logger.debug("entity.outPosition: " + entity.getSelectOutPositions());
		if(taskNode.getInPositions().equals(entity.getSelectInPositions()) 
				&& taskNode.getOutPositions().equals(entity.getSelectOutPositions())){
			this.ajaxOutPutText("没有改变任何位置，不需要保存。");
			return;
		}
		designManager.execAjustNodePosition(taskId, entity, taskNode);
		// 更新缓存
		removeTaskOnlyAsso(taskId);
		updateTaskNodeInOutPos(taskId, entity.getSelectInPositions(), entity.getSelectOutPositions());
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	@RequestMapping(value = "permit/flow/design/set_end")
	public void setTaskEnd(String id, String processDefineId
			, HttpServletResponse response) throws IOException{
		assert (StringUtils.isNotEmpty(id));
		assert (StringUtils.isNotEmpty(processDefineId));
		
		if(isExistEndTask(processDefineId)){
			this.ajaxOutPutText("已经存在结束任务，不能重复指定。");
			return;
		}
		TaskOut endTaskout = designManager.execSetTaskEnd(id, processDefineId);
		setTaskEndCache(endTaskout);
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	/**
	 * 取消任务为'结束任务'
	 * @param id
	 * @param processDefineId
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "permit/flow/design/cancel_end")
	public void cancelTaskEnd(String id, String processDefineId
			, HttpServletResponse response) throws IOException{
		assert (StringUtils.isNotEmpty(id));
		assert (StringUtils.isNotEmpty(processDefineId));
		TaskDefineAbstract task = this.getTaskDefine(id);
		if(!task.isEnd()){
			this.ajaxOutPutText("该任务不是结束任务，不能被取消结束");
			return;
		}
		designManager.execCancelTaskEnd(processDefineId, id);
		this.cancelTaskEndCache(processDefineId, id);
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	/**
	 * 显示参与者配置界面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "permit/flow/design/show_actor")
	public String showActorPage(String id, Model model){
		assert (StringUtils.isNotEmpty(id));
		TaskDefineAbstract task = getTaskDefine(id);
		String actorTypeBean = task.getActorType();
		model.addAttribute(ATTR_PROCESS_ID, task.getId());
		model.addAttribute(ATTR_TASK_START, task.isStart() == true ? "1" : "0");
		model.addAttribute(ATTR_TASK, task);
		model.addAttribute(ATTR_ACTOR_SINGLE_GIVEN, getActorSingleGiven());
		model.addAttribute(ATTR_ACTOR_BUSINESS_EXP, getActorBusinessExpend());
		model.addAttribute(ATTR_ACTOR_SELECT_FIXED, getActorSelectScopeFix());
		model.addAttribute(ATTR_ACTOR_SELECT_TREE, getActorSelectScopeTree());
		model.addAttribute(ATTR_ACTOR_SELECT_LIST, getActorSelectScopeList());
//		model.addAttribute(DEPARTMENT_JSON_SET, this.getAllDepartmentTreeForJson());
		
		// 如果已经存在数据，就显示在界面中
		String actorModelType = getActorModelType(actorTypeBean);
		if(StringUtils.isEmpty(actorModelType)){
			throw new IllegalArgumentException("not found actorModelType.");
		}
		
		model.addAttribute(ATTR_ACTOR_MODEL, actorModelType);
		model.addAttribute(ATTR_ACTOR_BEAN, actorTypeBean);
		
		if(actorModelType.equals(ATTR_ACTOR_SELECT_FIXED) 
				|| actorModelType.equals(ATTR_ACTOR_SELECT_LIST)){
			List<ActorAssign> aaList = this.getActorAssignList(id);
			Map<String, String> actorValue = new TreeMap<String, String>();
			if(aaList != null && aaList.size() > 0){
				for(ActorAssign aa : aaList){
					actorValue.put(aa.getActorId(), aa.getActorName());
				}
			}
			model.addAttribute(ATTR_ACTOR_VALUE, actorValue);
		}
		return URL_DESIGN_ACTOR;
	}
	
	private String getActorModelType(String actorTypeBean){
		String actorModelType = "";
		AbstractBaseActor actorBean = (AbstractBaseActor)MyApplicationConfig.getBeanObject(actorTypeBean);
		Class<?> actorType = actorBean.getClass();
		if(SingleGivenActor.class.isAssignableFrom(actorType)){
			actorModelType = ATTR_ACTOR_SINGLE_GIVEN;
		} else if(BusinessExpendActor.class.isAssignableFrom(actorType)){
			actorModelType = ATTR_ACTOR_BUSINESS_EXP;
		} else if(SelectScopeAndFixActor.class.isAssignableFrom(actorType)){
			actorModelType = ATTR_ACTOR_SELECT_FIXED;
		} else if(SelectScopeTreeActor.class.isAssignableFrom(actorType)){
			actorModelType = ATTR_ACTOR_SELECT_TREE;
		} else if(SelectScopeListActor.class.isAssignableFrom(actorType)){
			actorModelType = ATTR_ACTOR_SELECT_LIST;
		}
		return actorModelType;
	}
	
	/**
	 * 返回参与者模型中，选择树范围需要用的树节点数据（JSON格式）<br>
	 * 当用户选择“从树结构中选择范围”时，触发该方法。
	 * @param beanId
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "permit/flow/actor/get_scope_tree")
	public void getActorScopeTreeNode(String beanId
			, HttpServletResponse response) throws IOException{
		assert (StringUtils.isNotEmpty(beanId));
		SelectScopeTreeActor actor = this.getScopeTreeActor(beanId);
		List<AbstractActorNode> list = actor.getDefineRootNodeList();
		this.ajaxOutPutText(this.getActorForDepartmentTreeJson(list));
	}
	
	/**
	 * 返回参与者模型中，选择固定人员选项时，树结构数据，转换为JSON格式。
	 * @param beanId
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "permit/flow/actor/get_fix_tree")
	public void getActorFixTreeNode(String beanId
			, HttpServletResponse response) throws IOException{
		assert (StringUtils.isNotEmpty(beanId));
		SelectScopeAndFixActor actor = this.getScopeFixActor(beanId);
		List<AbstractActorNode> list = actor.getDefineRootNodeList();
		this.ajaxOutPutText(this.getActorForDepartmentTreeJson(list));
	}
	
	/**
	 * 点击“选择固定人员树”节点时，返回用户集合信息，使用字符串标识。<br>
	 * 格式：id&name,id&name,...前端JS转换成数组使用。
	 * @param deptId 单位或部门ID
	 * @param beanId 参与者模型中配置的beanId，这个主要针对固定人员树的配置
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "permit/flow/actor/get_fix_list")
	public void getActorFixNodeList(String deptId, String beanId
			, HttpServletResponse response) throws IOException{
		assert (StringUtils.isNotEmpty(deptId));
		assert (StringUtils.isNotEmpty(beanId));
		SelectScopeAndFixActor actor = this.getScopeFixActor(beanId);
		List<AbstractActorNode> list = actor.getDefineChildrenNodeList(deptId);
		doResponseSelectOptionData(list);
	}
	
	private void doResponseSelectOptionData(List<AbstractActorNode> list) throws IOException{
		if(list != null && list.size() > 0){
			StringBuilder result = new StringBuilder();
			for(AbstractActorNode n : list){
				result.append(n.getNodeId());
				result.append("&");
				result.append(n.getNodeName());
				result.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			}
			if(result.toString().endsWith(StringUtils.DEFAULT_SPLIT_SEPARATOR))
				result.deleteCharAt(result.lastIndexOf(StringUtils.DEFAULT_SPLIT_SEPARATOR));
			this.ajaxOutPutText(result.toString());
		} else
			this.ajaxOutPutText(StringUtils.EMPTY_STRING);
	}
	
	/**
	 * 选择“选择列表范围”时，根据配置的beanID不同分别加载不同的范围列表数据，如：角色列表等。
	 * @param beanId
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "permit/flow/actor/get_scope_list")
	public void getActorScopeList(String beanId
			, HttpServletResponse response) throws IOException{
		assert (StringUtils.isNotEmpty(beanId));
		SelectScopeListActor actor = this.getScopeListActor(beanId);
		List<AbstractActorNode> list = actor.getDefineNodeList();
		doResponseSelectOptionData(list);
	}
	
	/**
	 * 保存或更新actor参与者字段信息，包括：actor_assign表
	 * @param taskId
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "permit/flow/actor/edit_actor")
	public void saveOrUpdateActorInfo(String taskId
			, HttpServletResponse response) throws IOException{
		String paramActorType = this.getParameter(PARAMETER_ACTOR_TYPE); // 用户选择的参与者模型
		String beanId = this.getParameter(PARAMETER_ACTOR_BEAN); // 用户选择的beanId
		assert (StringUtils.isNotEmpty(taskId));
		assert (StringUtils.isNotEmpty(paramActorType));
		// 存放更新的参与者分配结果
		List<ActorAssign> parameters = null;
		
		TaskDefineAbstract task = getTaskDefine(taskId);
		
		if(paramActorType.equals(ATTR_ACTOR_SINGLE_GIVEN) 
				|| paramActorType.equals(ATTR_ACTOR_BUSINESS_EXP)){
			// 1-直接更新任务记录中，actor_type字段即可
			designManager.execSaveOrUpdateActorInfo(beanId, taskId, null);
			// 2-直接通过内存引用对象更新缓存
			task.setActorType(beanId);
			this.removeTaskActorAssigns(taskId);
			
		} else if(paramActorType.equals(ATTR_ACTOR_SELECT_FIXED) 
				|| paramActorType.equals(ATTR_ACTOR_SELECT_LIST)){
			// 1-直接更新任务记录中，actor_type字段
			// 2-更新actor_assign中记录集合
			String assignValues = this.getParameter(PARAMETER_ACTOR_VALUE);
			if(StringUtils.isEmpty(assignValues)){
				throw new IllegalArgumentException("not found assignValue.");
			}
			String[] values = assignValues.split(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			String[] actorInfo = null;
			parameters = new ArrayList<ActorAssign>();
			ActorAssign aa = null;
			int i = 1;
			for(String v : values){
				actorInfo = v.split(DRAFT_PARAM_SPLIT);
				aa = new ActorAssign();
				aa.setId(NumberGenerator.generatorHexUUID());
				aa.setTaskDefineId(taskId);
				aa.setActorId(actorInfo[0]);
				aa.setActorName(actorInfo[1]);
				aa.setOrderNum(i++);
				aa.setProcessDefineId(task.getProcessDefineId());
				parameters.add(aa);
			}
			designManager.execSaveOrUpdateActorInfo(beanId, taskId, parameters);
			
			// 3-更新缓存
			task.setActorType(beanId);
			this.updateActorAssignCache(taskId, parameters);
		}
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	/**
	 * 验证流程是否合法，如果不符合规范，会给出响应提示。
	 * @param processDefineId 流程定义ID
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "permit/flow/design/validate_process")
	public void validateProcess(String processDefineId
			, HttpServletResponse response) throws IOException{
		assert (StringUtils.isNotEmpty(processDefineId));
		String msg = doValidateProcessResult(processDefineId);
		if(msg != null){
			this.ajaxOutPutText(msg);
			return;
		} else
			this.ajaxOutPutText(MESSAGE_SUCCESS);
//		if(getProcessDefine(processDefineId) == null){
//			this.ajaxOutPutText("流程不存在: " + processDefineId);
//			return;
//		}
//		List<String> messages = this.validateProcessDesign(processDefineId);
//		if(messages == null){
//			this.ajaxOutPutText(MESSAGE_SUCCESS);
//		} else {
//			StringBuilder msg = new StringBuilder();
//			for(String s : messages){
//				msg.append(s);
//				msg.append("\n\r");
//			}
//			this.ajaxOutPutText(msg.toString());
//		}
	}
	
	/**
	 * 验证流程是否合法，如果不符合规范，返回提示信息，如果成功返回<code>null</code>。
	 * @param processDefineId
	 * @return
	 */
	private String doValidateProcessResult(String processDefineId){
		if(getProcessDefine(processDefineId) == null){
			return "流程不存在: " + processDefineId;
		}
		List<String> messages = this.validateProcessDesign(processDefineId);
		if(messages != null){
			StringBuilder msg = new StringBuilder();
			for(String s : messages){
				msg.append(s);
				msg.append("\n\r");
			}
			return msg.toString();
		}
		return null;
	}
	
	@RequestMapping(value = "permit/flow/design/publish_process")
	public void publishProcess(String processDefineId
			, HttpServletResponse response) throws IOException{
		assert (StringUtils.isNotEmpty(processDefineId));
		String msg = doValidateProcessResult(processDefineId);
		if(msg != null){
			this.ajaxOutPutText(msg);
			return;
		}
		ProcessDefine process = this.getProcessDefine(processDefineId);
		if(process.getPublishStatus() == 1){
			this.ajaxOutPutText("流程已经发布，不允许重复发布");
			return;
		}
		designManager.execPublishProcess(processDefineId);
		this.updateProcessToPublish(processDefineId);
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	private static final String ATTR_ACTOR_SINGLE_GIVEN = "SingleGivenActor";
	private static final String ATTR_ACTOR_BUSINESS_EXP = "BusinessExpendActor";
	private static final String ATTR_ACTOR_SELECT_FIXED = "SelectScopeAndFixActor";
	private static final String ATTR_ACTOR_SELECT_TREE  = "SelectScopeTreeActor";
	private static final String ATTR_ACTOR_SELECT_LIST  = "SelectScopeListActor";
}
