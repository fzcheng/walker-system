package com.walkersoft.flow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.walker.flow.cache.SpringWorkerCacheFactory;
import com.walker.flow.client.FlowClient;
import com.walker.flow.client.ProcessData;
import com.walker.flow.client.UiEditorController;
import com.walker.flow.core.actor.BusinessExpendActor;
import com.walker.flow.core.actor.SelectScopeAndFixActor;
import com.walker.flow.core.actor.SelectScopeListActor;
import com.walker.flow.core.actor.SelectScopeTreeActor;
import com.walker.flow.core.actor.SingleGivenActor;
import com.walker.flow.core.actor.node.AbstractActorNode;
import com.walker.flow.core.actor.node.DefaultFolderNode;
import com.walker.flow.meta.ActorAssign;
import com.walker.flow.meta.ProcessDefine;
import com.walker.flow.meta.TaskBaseBehavior;
import com.walker.flow.meta.TaskDefineAbstract;
import com.walker.flow.meta.TaskIn;
import com.walker.flow.meta.TaskOut;
import com.walker.flow.meta.validate.ActorRelationValidator;
import com.walker.flow.meta.validate.LinesConnectionValidator;
import com.walker.flow.meta.validate.MiscTaskValidator;
import com.walker.flow.meta.validate.ProcessGeneralValidator;
import com.walker.flow.meta.validate.PublishWorkflowValidator;
import com.walker.flow.meta.validate.TaskRelationValidator;
import com.walker.flow.ncache.ActorAssignCache;
import com.walker.flow.ncache.ProcessDefineCache;
import com.walker.flow.ncache.TaskDefineCache;
import com.walker.flow.ncache.TaskInCache;
import com.walker.flow.ncache.TaskOutCache;
import com.walker.infrastructure.cache.support.Cachable;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.util.CodeUtils;
import com.walkersoft.application.util.DepartmentUtils;
import com.walkersoft.flow.pojo.AnchorIn;
import com.walkersoft.flow.pojo.AnchorOut;
import com.walkersoft.flow.pojo.TaskNode;
import com.walkersoft.system.SystemAction;

/**
 * 支持流程管理操作的控制器
 * @author shikeying
 *
 */
public class FlowAction extends SystemAction {

	protected static final String FLOW_BASE = "flow/";
	protected static final String URL_VIEW_PROCESS = FLOW_BASE + "define/flow_view";
	
	private FlowClient flowClient = null;
	private UiEditorController uiEditor = null;
	
	private static final Object lock = new Object();
	
	
	@Autowired
	protected ProcessDefineCache processDefineCache;
	
	@Autowired
	protected TaskDefineCache taskDefineCache;
	
	@Autowired
	protected TaskInCache taskInCache;
	
	@Autowired
	protected TaskOutCache taskOutCache;
	
	@Autowired
	private TaskPositionCache taskPositionCache;
	
	@Autowired
	private ActorAssignCache actorAssignCache;
	
	/**
	 * 返回流程界面编辑服务对象，提供了各种界面控制参数。
	 * @return
	 */
	protected UiEditorController getUiEditor(){
		if(flowClient == null){
			synchronized (lock) {
				flowClient = FlowClient.getInstance();
				uiEditor = flowClient.getUiEditor();
			}
		}
		if(uiEditor == null)
			throw new NullPointerException("uiEditor is required.");
		return uiEditor;
	}
	
	/**
	 * 返回系统定义的所有，流程业务类别数据。
	 * @return Map，key = 业务类别ID， value = 业务类别描述
	 */
	protected Map<String, String> getBusinessTypeInfo(){
		getUiEditor();
		return uiEditor.getBusinessTypeMap();
	}
	
	protected Map<String, String> getProcessCreateListenerInfo(){
		getUiEditor();
		return uiEditor.getFlowCreateListenerInfo();
	}
	
	protected Map<String, String> getProcessEndListenerInfo(){
		getUiEditor();
		return uiEditor.getFlowEndListenerInfo();
	}
	
	/**
	 * 返回系统定义的流程参与者集合信息
	 * @return
	 */
	protected Map<String, String> getActorTypeInfo(){
		getUiEditor();
		return uiEditor.getActorTypeInfo();
	}
	
	protected Map<String, String> getActorSingleGiven(){
		getUiEditor();
		return uiEditor.getActorSet(SingleGivenActor.class);
	}
	
	protected Map<String, String> getActorBusinessExpend(){
		getUiEditor();
		return uiEditor.getActorSet(BusinessExpendActor.class);
	}
	
	protected Map<String, String> getActorSelectScopeFix(){
		getUiEditor();
		return uiEditor.getActorSet(SelectScopeAndFixActor.class);
	}
	
	protected Map<String, String> getActorSelectScopeTree(){
		getUiEditor();
		return uiEditor.getActorSet(SelectScopeTreeActor.class);
	}
	
	protected Map<String, String> getActorSelectScopeList(){
		getUiEditor();
		return uiEditor.getActorSet(SelectScopeListActor.class);
	}
	
	protected SelectScopeTreeActor getScopeTreeActor(String beanId){
		getUiEditor();
		return uiEditor.getActorModelDefinition(beanId, SelectScopeTreeActor.class);
	}
	
	protected SelectScopeAndFixActor getScopeFixActor(String beanId){
		getUiEditor();
		return uiEditor.getActorModelDefinition(beanId, SelectScopeAndFixActor.class);
	}
	
	protected SelectScopeListActor getScopeListActor(String beanId){
		getUiEditor();
		return uiEditor.getActorModelDefinition(beanId, SelectScopeListActor.class);
	}
	
	protected SingleGivenActor getSingleGivenActor(String beanId){
		getUiEditor();
		return uiEditor.getActorModelDefinition(beanId, SingleGivenActor.class);
	}
	
	protected BusinessExpendActor getBusinessExpandActor(String beanId){
		getUiEditor();
		return uiEditor.getActorModelDefinition(beanId, BusinessExpendActor.class);
	}
	
	/**
	 * 返回系统定义的所有'任务进入事件'集合信息
	 * @return
	 */
	protected Map<String, String> getTaskInListenerInfo(){
		getUiEditor();
		return uiEditor.getTaskInListenerInfo();
	}
	
	/**
	 * 返回系统定义的所有'任务离开事件'集合信息
	 * @return
	 */
	protected Map<String, String> getTaskOutListenerInfo(){
		getUiEditor();
		return uiEditor.getTaskOutListenerInfo();
	}
	
	/**
	 * 返回流程缓存工厂
	 * @return
	 */
	protected SpringWorkerCacheFactory getFlowCacheFactory(){
		getUiEditor();
		return flowClient.getFlowCacheFactory();
	}
	
	private TaskDefineAbstract getTaskWithInOut(String taskDefineId){
		getUiEditor();
		return flowClient.getFlowCacheFactory().getTaskWithInOut(taskDefineId);
	}
	
	protected ProcessDefine getProcessDefine(String processDefineId){
		return processDefineCache.getCacheData(processDefineId);
	}
	
	/**
	 * 返回可用的主流程定义记录集合
	 * @return
	 */
	protected List<ProcessData> getAvailablePrimaryProcessList(){
		getUiEditor();
		return uiEditor.getAvailablePrimaryProcessList();
	}
	
	protected TaskDefineAbstract getTaskDefine(String taskDefineId){
		TaskDefineAbstract task = taskDefineCache.getCacheData(taskDefineId);
		if(task == null) throw new IllegalArgumentException("task not found in cache: " + taskDefineId);
		return task;
	}
	
	protected String getTaskName(String taskDefineId){
		return getTaskDefine(taskDefineId).getName();
	}
	
	protected TaskNode getTaskNode(String taskDefineId){
		return taskPositionCache.getCacheData(taskDefineId);
	}
	
	protected Map<String, String> getTaskNodeInPositions(String taskDefineId){
		return getTaskNode(taskDefineId).getInPositionMap();
	}
	
	protected Map<String, String> getTaskNodeOutPositions(String taskDefineId){
		return getTaskNode(taskDefineId).getOutPositionMap();
	}
	
	/**
	 * 加入任务对象到缓存，如果是第一步任务，需要加上默认进入节点：开始(START)
	 * @param task
	 * @param taskIn
	 */
	protected void addTaskToCache(TaskDefineAbstract task
			, TaskIn taskIn, TaskNode taskNode){
		taskDefineCache.putCacheData(task.getId(), task);
		if(task.isStart()){
			if(taskIn == null) throw new IllegalArgumentException();
			taskInCache.putCacheData(taskIn.getId(), taskIn);
		}
		taskPositionCache.putCacheData(task.getId(), taskNode);
	}
	
	/**
	 * 添加任务新连接到缓存中
	 * @param taskOut
	 * @param taskIn
	 * @param anchorOut
	 * @param anchorIn
	 */
	protected void addTaskConnection(TaskOut taskOut, TaskIn taskIn
			, AnchorOut anchorOut, AnchorIn anchorIn){
		assert (taskOut != null);
		assert (taskIn != null);
		assert (anchorOut != null);
		assert (anchorIn != null);
		taskOutCache.putCacheData(taskOut.getId(), taskOut);
		taskInCache.putCacheData(taskIn.getId(), taskIn);
		taskPositionCache.putAnchorOut(anchorOut);
		taskPositionCache.putAnchorIn(anchorIn);
	}
	
	/**
	 * 设置任务为结束，更新缓存
	 * @param taskOut
	 */
	protected void setTaskEndCache(TaskOut taskOut){
		taskOutCache.putCacheData(taskOut.getId(), taskOut);
		getTaskDefine(taskOut.getTaskDefineId()).setEndTask(1);
	}
	
	/**
	 * 取消设置任务结束标志，更新缓存
	 * @param processDefineId
	 * @param taskDefineId
	 */
	protected void cancelTaskEndCache(String processDefineId, String taskDefineId){
		deleteOneTaskOut(processDefineId, taskDefineId, TaskBaseBehavior.END_NAME);
		getTaskDefine(taskDefineId).setEndTask(0);
	}
	
	/**
	 * 删除连接线信息，包括：流程引擎中的连接信息和位置信息。<br>
	 * taskOut/taskIn/anchorOut/anchorIn
	 * @param srcTaskId 源节点任务ID
	 * @param destTaskId 目的任务ID
	 * @param srcOutPosition 源节点输出位置
	 * @param destInPosition 目的点输入位置
	 */
	protected void removeTaskConnection(String srcTaskId, String destTaskId
			, String srcOutPosition, String destInPosition){
		TaskOut taskOut = null;
		for(Iterator<Cachable> it = taskOutCache.getCache().getIterator(); it.hasNext();){
			taskOut = (TaskOut)it.next().getValue();
			if(taskOut.getTaskDefineId().equals(srcTaskId) 
					&& taskOut.getNextTask().equals(destTaskId)){
				it.remove();
				break;
			}
		}
		
		TaskIn taskIn = null;
		for(Iterator<Cachable> it = taskInCache.getCache().getIterator(); it.hasNext();){
			taskIn = (TaskIn)it.next().getValue();
			if(taskIn.getTaskDefineId().equals(destTaskId) 
					&& taskIn.getPreviousTask().equals(srcTaskId)){
				it.remove();
				break;
			}
		}
		
		taskPositionCache.removeAnchorOut(srcTaskId, srcOutPosition);
		taskPositionCache.removeAnchorIn(destTaskId, destInPosition, srcTaskId);
	}
	
	protected boolean isSameTaskConnection(String taskDefineId, String nextTaskId){
		return taskOutCache.isExistSameTaskOut(taskDefineId, nextTaskId);
	}
	
	protected boolean isStartTask(String taskId){
		return taskDefineCache.getCacheData(taskId).isStart();
	}
	
	/**
	 * 当前任务是否允许退回
	 * @param taskInstId 任务实例ID
	 * @param loginUserId 当前执行人ID
	 * @param loginUserName 当前执行人名字
	 * @return
	 */
	protected boolean isAllowedBack(String taskInstId, String loginUserId, String loginUserName){
		getUiEditor();
		return uiEditor.isAllowedBack(taskInstId, loginUserId, loginUserName);
	}
	
	protected void updateTaskCache(TaskDefineAbstract task){
		taskDefineCache.updateCacheData(task.getId(), task);
	}
	
	/**
	 * 更新任务节点坐标缓存
	 * @param parameters
	 */
	protected void updateTaskNodePositionCache(List<Object[]> parameters){
		String id, x, y;
		TaskNode taskNode = null;
		for(Object[] p : parameters){
			id = p[2].toString();
			x  = p[0].toString();
			y  = p[1].toString();
			taskNode = taskPositionCache.getCacheData(id);
			taskNode.setX(x).setY(y);
		}
	}
	
	protected void updateTaskNodeInOutPos(String id, String inPositions, String outPositions){
		TaskNode tn = taskPositionCache.getCacheData(id);
		if(StringUtils.isNotEmpty(inPositions))
			tn.setInPositions(inPositions);
		if(StringUtils.isNotEmpty(outPositions))
			tn.setOutPositions(outPositions);
	}
	
	/**
	 * 更新缓存：流程发布状态
	 * @param processDefineId
	 */
	protected void updateProcessToPublish(String processDefineId){
		getProcessDefine(processDefineId).setPublishStatus(1);
	}
	protected void updateCancelPublishStatus(String processDefineId){
		getProcessDefine(processDefineId).setPublishStatus(0);
	}
	
	/**
	 * 删除缓存中与当前任务有关联的节点（节点关系、位置）信息。
	 * @param taskDefineId 关联的任务ID
	 */
	protected void removeTaskCache(String taskDefineId){
		taskDefineCache.removeCacheData(taskDefineId);
		taskPositionCache.removeCacheData(taskDefineId);
		removeTaskOnlyAsso(taskDefineId);
	}
	
	/**
	 * 只更新缓存中，与任务有关联的连接信息，不删除任务本身。<br>
	 * 例如：在调整节点位置时，会删除所有关联的节点信息。
	 * @param taskDefineId
	 */
	protected void removeTaskOnlyAsso(String taskDefineId){
		// 删除其他节点关联的此任务信息
		TaskIn taskIn = null;
		for(Iterator<Cachable> it = taskInCache.getCache().getIterator(); it.hasNext();){
			taskIn = (TaskIn)it.next().getValue();
			if(taskIn.getPreviousTask().equals(taskDefineId)){
				it.remove();
			}
		}
		
		TaskOut taskOut = null;
		for(Iterator<Cachable> it = taskOutCache.getCache().getIterator(); it.hasNext();){
			taskOut = (TaskOut)it.next().getValue();
			if(taskOut.getNextTask().equals(taskDefineId)){
				it.remove();
			}
		}
		
		TaskNode taskNode = null;
		for(Iterator<Cachable> it = taskPositionCache.getCache().getIterator(); it.hasNext();){
			taskNode = (TaskNode)it.next().getValue();
			taskNode.removeAnchorAsso(taskDefineId);
		}
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// 改变任务节点位置的相关缓存方法
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private void deleteOneTaskOut(String processDefineId, String taskDefineId, String nextTask){
		TaskOut taskOut = null;
		for(Iterator<Cachable> it = taskOutCache.getCache().getIterator(); it.hasNext();){
			taskOut = (TaskOut)it.next().getValue();
			if(taskOut.getTaskDefineId().equals(taskDefineId) 
					&& taskOut.getNextTask().equals(nextTask) 
					&& taskOut.getProcessDefineId().equals(processDefineId)){
				it.remove();
				break;
			}
		}
	}
	
	/**
	 * 从缓存中删除当前任务中的输入节点信息
	 * @param processDefineId 流程ID（其实不要也可以）
	 * @param taskDefineId 任务定义ID
	 * @param previousTask 上一步任务ID
	 */
	private void deleteOneTaskIn(String processDefineId, String taskDefineId, String previousTask){
		TaskIn taskIn = null;
		for(Iterator<Cachable> it = taskInCache.getCache().getIterator(); it.hasNext();){
			taskIn = (TaskIn)it.next().getValue();
			if(taskIn.getTaskDefineId().equals(taskDefineId) 
					&& taskIn.getPreviousTask().equals(previousTask) 
					&& taskIn.getProcessDefineId().equals(processDefineId)){
				it.remove();
				break;
			}
		}
	}
	
	private void updateTaskOut(String processDefineId, String taskDefineId, String nextTask){
		TaskOut taskOut = null;
		for(Iterator<Cachable> it = taskOutCache.getCache().getIterator(); it.hasNext();){
			taskOut = (TaskOut)it.next().getValue();
			if(taskOut.getTaskDefineId().equals(taskDefineId) 
					&& taskOut.getProcessDefineId().equals(processDefineId)){
				taskOut.setNextTask(nextTask);
				break;
			}
		}
	}
	
	private void updateTaskIn(String processDefineId, String taskDefineId, String previousTask){
		TaskIn taskIn = null;
		for(Iterator<Cachable> it = taskInCache.getCache().getIterator(); it.hasNext();){
			taskIn = (TaskIn)it.next().getValue();
			if(taskIn.getTaskDefineId().equals(taskDefineId) 
					&& taskIn.getProcessDefineId().equals(processDefineId)){
				taskIn.setPreviousTask(previousTask);
				break;
			}
		}
	}
	
	private void removeAnchorOut(String taskDefineId, String position){
		assert (StringUtils.isNotEmpty(taskDefineId));
		assert (StringUtils.isNotEmpty(position));
		taskPositionCache.removeAnchorOut(taskDefineId, position);
	}
	
	private void removeAnchorIn(String taskDefineId, String inPosition, String fromTaskNode){
		assert (StringUtils.isNotEmpty(taskDefineId));
		assert (StringUtils.isNotEmpty(inPosition));
		assert (StringUtils.isNotEmpty(fromTaskNode));
		taskPositionCache.removeAnchorIn(taskDefineId, inPosition, fromTaskNode);
	}
	
	/**
	 * 更新缓存对象，用户改变源节点，从一个任务移动到另外一个任务，配合<code>execChangeSrcEndpoint</code>方法使用。
	 * @param oldSrcTaskId
	 * @param changedSrcTaskId
	 * @param oldSrcPosition
	 * @param changedSrcPosition
	 * @param targetTaskId
	 * @param targetPosition
	 * @param processDefineId
	 * @param to
	 * @param ao
	 */
	protected void updateChangeSrcEndpoint(String oldSrcTaskId, String changedSrcTaskId
			, String oldSrcPosition, String changedSrcPosition
			, String targetTaskId, String targetPosition
			, String processDefineId
			, TaskOut to, AnchorOut ao){
		deleteOneTaskOut(processDefineId, oldSrcTaskId, targetTaskId);
		taskOutCache.putCacheData(to.getId(), to);
		updateTaskIn(processDefineId, targetTaskId, changedSrcTaskId);
		
		removeAnchorOut(oldSrcTaskId, oldSrcPosition);
		taskPositionCache.updateAnchorIn(changedSrcTaskId
				, changedSrcPosition, targetTaskId, targetPosition, oldSrcTaskId);
		taskPositionCache.putAnchorOut(ao);
	}
	
	protected void updateChangeDestEndpoint(String oldTargetTaskId, String changedTargetTaskId
			, String oldTargetPosition, String changedTargetPosition
			, String sourceTaskId, String sourcePosition
			, String processDefineId
			, TaskIn ti, AnchorIn ai){
		deleteOneTaskIn(processDefineId, oldTargetTaskId, sourceTaskId);
		taskInCache.putCacheData(ti.getId(), ti);
		updateTaskOut(processDefineId, sourceTaskId, changedTargetTaskId);
		
		removeAnchorIn(oldTargetTaskId, oldTargetPosition, sourceTaskId);
		taskPositionCache.updateAnchorOut(changedTargetTaskId
				, changedTargetPosition, sourceTaskId, sourcePosition);
		taskPositionCache.putAnchorIn(ai);
	}
	
//	protected void updateAnchorOut(String taskDefineId
//			, String outPosition, String nextNode, String nextPosition){
//		TaskNode taskNode = taskPositionCache.getCacheData(taskDefineId);
//		taskNode.updateAnchorOut(outPosition, nextNode, nextPosition);
//	}
	
	/**
	 * 更新缓存中任务的输入位置节点，节点仅发生了位置变化，没有改变任务。<br>
	 * 因为输入节点可以被重复连接，所以要更新确定这个点需要比输出多一个参数：
	 * @param taskDefineId
	 * @param inPosition
	 * @param oldFromTaskId
	 * @param changedSrcTaskId
	 * @param changedSrcPosition
	 */
//	protected void updateAnchorIn(String taskDefineId
//			, String inPosition, String oldFromTaskId
//			, String changedSrcTaskId, String changedSrcPosition){
//		TaskNode taskNode = taskPositionCache.getCacheData(taskDefineId);
//		taskNode.updateAnchorIn(inPosition, oldFromTaskId, changedSrcTaskId, changedSrcPosition);
//	}
	
	protected void updateChangeSrcPosition(String sourceTaskId
			, String oldSourcePosition, String changedSourcePosition
			, String targetTaskId, String targetPosition){
		// 仅更新输出点的自己position信息
		TaskNode srcTaskNode = taskPositionCache.getCacheData(sourceTaskId);
		srcTaskNode.updateAnchorOutPosition(oldSourcePosition, changedSourcePosition);
		
		// 仅更新输入点的fromPosition信息
		TaskNode destTaskNode = taskPositionCache.getCacheData(targetTaskId);
		destTaskNode.updateAnchorIn(targetPosition, sourceTaskId, sourceTaskId, changedSourcePosition);
	}
	
	protected void updateChangeDestPosition(String targetTaskId
			, String fromTask, String fromPosition
			, String oldTargetPosition, String changedTargetPosition){
		// 仅更新输入点的Position信息
		TaskNode destTaskNode = taskPositionCache.getCacheData(targetTaskId);
		destTaskNode.updateAnchorInPosition(oldTargetPosition, fromTask, changedTargetPosition);
		// 仅更新输出点的nextPosition信息
		TaskNode srcTaskNode = taskPositionCache.getCacheData(fromTask);
		srcTaskNode.updateAnchorOut(fromPosition, targetTaskId, changedTargetPosition);
	}
	
	protected boolean isExistEndTask(String processDefineId){
		List<TaskDefineAbstract> tasks = getTaskDefineList(processDefineId);
		for(TaskDefineAbstract t : tasks){
			if(t.isEnd()) return true;
		}
		return false;
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// 从获取流程任务节点以及位置信息方法为界面使用
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * 返回下一步执行人集合。</p>
	 * 通过计算能在界面中（执行之前）返回下一步执行人，但仅限于下一步是单任务，不能是分拆、汇聚。<br/>
	 * 
	 * @param taskInstId
	 * @param loginUserId
	 * @param loginUserName
	 * @return
	 */
	protected List<AbstractActorNode> getNextActorList(String taskInstId
			, String loginUserId, String loginUserName){
		getUiEditor();
		return this.uiEditor.getNextActorList(taskInstId, loginUserId, loginUserName);
	}
	
	/**
	 * 根据输入流程ID，返回任务定义列表
	 * @param processDefineId
	 * @return
	 */
	protected List<TaskDefineAbstract> getTaskDefineList(String processDefineId){
		getUiEditor();
		return flowClient.getFlowCacheFactory().getTaskDefineList(processDefineId);
	}
	
	protected List<TaskNode> getTaskAvailablePositions(List<String> taskIds){
		return taskPositionCache.getTaskAvailablePositions(taskIds);
	}
	
	protected static final String PARAM_ID = "id";
	
	protected static final String ATTR_PROCESS_DEFINE = "processDefine";
	
	/**
	 * 从参与者定义中返回的节点树中，得到组织机构的信息数，在参与者模型中展示到ZTREE树中。
	 * @param list
	 * @return
	 */
	protected String getActorForDepartmentTreeJson(List<AbstractActorNode> list){
		JSONArray array = new JSONArray();
		array.add(CodeUtils.createJsonTree(CodeUtils.ZTREE_FIRST_PARENT, DepartmentUtils.NAME_ROOT, "0", false, true, null));
		if(list != null){
			for(AbstractActorNode n : list){
				recurseTree(n, CodeUtils.ZTREE_FIRST_PARENT, array, null);
			}
		}
		return array.toString();
	}
	
	private void recurseTree(AbstractActorNode node, String parent, JSONArray array, List<String> existIds){
		// 如果存在当前值，就选中节点
		boolean checked = false;
		if(existIds != null && existIds.contains(node.getNodeId())){
			checked = true;
		}
		array.add(createJson2TreeNode(node, parent, checked, false));
		if(node instanceof DefaultFolderNode){
			DefaultFolderNode folder = (DefaultFolderNode)node;
			List<AbstractActorNode> children = folder.getChildren();
			if(children != null){
				for(AbstractActorNode child : children){
					recurseTree(child, folder.getNodeId(), array, existIds);
				}
			}
		}
	}
	
	private JSONObject createJson2TreeNode(AbstractActorNode node
			, String pid, boolean checked, boolean opened){
		String id = node.getNodeId();
		String name = node.getNodeName();
//		String pid = node.getParentId();
		assert (StringUtils.isNotEmpty(id));
		assert (StringUtils.isNotEmpty(name));
		assert (StringUtils.isNotEmpty(pid));
		
		if(pid.equals("0")){
			pid = CodeUtils.ZTREE_FIRST_PARENT;
		}
		return CodeUtils.createJsonTree(id, name, pid, checked, opened, null);
	}
	
	protected List<ActorAssign> getActorAssignList(String taskDefineId){
		return actorAssignCache.getActorAssignList(taskDefineId);
	}
	
	/**
	 * 更新参与者分配缓存，先删除，再添加
	 * @param taskDefineId
	 * @param parameters
	 */
	protected void updateActorAssignCache(String taskDefineId, List<ActorAssign> parameters){
		if(parameters == null || parameters.size() == 0) return;
		removeTaskActorAssigns(taskDefineId);
		for(ActorAssign aa : parameters){
			actorAssignCache.putCacheData(aa.getId(), aa);
		}
	}
	protected void removeTaskActorAssigns(String taskDefineId){
		actorAssignCache.removeTaskActorAssigns(taskDefineId);
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// 流程校验方法
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private static final PublishWorkflowValidator processValidator = 
			new ProcessGeneralValidator(new LinesConnectionValidator(
					new TaskRelationValidator(new ActorRelationValidator(
							new MiscTaskValidator(null)))));
	
	protected PublishWorkflowValidator getProcessValidator(){
		return processValidator;
	}
	
	/**
	 * 流程设计器中，校验流程是否符合格式。</p>
	 * 如果不符合，返回提示信息；如果符合，返回<code>null</code>
	 * @param processDefineId
	 * @return
	 */
	protected List<String> validateProcessDesign(String processDefineId){
		List<String> result = new ArrayList<String>();
		List<TaskDefineAbstract> allTasks = getTaskDefineList(processDefineId);
		if(allTasks == null || allTasks.size() == 0){
			result.add("当前没有创建任何任务");
			return result;
		}
		Map<String, TaskDefineAbstract> map = new HashMap<String, TaskDefineAbstract>();
		TaskDefineAbstract first = null;
		for(TaskDefineAbstract t : allTasks){
			t = getTaskWithInOut(t.getId());
			map.put(t.getId(), t);
			if(t.isStart() && first == null){
				first = t;
			}
		}
		if(first == null){
			result.add("没有找到开始任务，流程不完整");
			return result;
		}
		
		return processValidator.handleValidate(first, map);
	}
}
