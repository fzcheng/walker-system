package com.walkersoft.flow.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.flow.meta.ActorAssign;
import com.walker.flow.meta.TaskBaseBehavior;
import com.walker.flow.meta.TaskBaseBehavior.NodeType;
import com.walker.flow.meta.TaskDefineAbstract;
import com.walker.flow.meta.TaskIn;
import com.walker.flow.meta.TaskOut;
import com.walker.flow.web.jsplump.PlumpDefine;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.flow.MiscUtils;
import com.walkersoft.flow.dao.DesignDao;
import com.walkersoft.flow.pojo.AnchorIn;
import com.walkersoft.flow.pojo.AnchorOut;
import com.walkersoft.flow.pojo.PositionAjustor;
import com.walkersoft.flow.pojo.TaskNode;

@Service("designManager")
public class DesignManagerImpl {

	@Autowired
	private DesignDao designDao;
	
	public TaskNode execSaveTask(TaskDefineAbstract task, TaskIn taskIn){
		String id = String.valueOf(designDao.getMaxTaskId());
		task.setId(id);
		task.setCreateTime(System.currentTimeMillis());
		designDao.insertTask(task);
		if(task.isStart() && taskIn != null){
			// 如果是第一个任务，自动写入任务进入节点信息，默认为START
			taskIn.setTaskDefineId(id);
			designDao.insertTaskIn(taskIn);
		}
		// 写入新建节点默认的坐标信息
		TaskNode taskNode = createStartNode(id, task.getNodeType()
				, task.isStart(), task.getProcessDefineId());
		designDao.insertTaskNode(taskNode);
		return taskNode;
	}
	
	private TaskNode createStartNode(String taskDefineId, NodeType taskType
			, boolean isStart, String processDefineId){
		TaskNode tn = new TaskNode().setId(taskDefineId)
				.setX(DEFAULT_XY).setY(DEFAULT_XY).setProcesDefineId(processDefineId);
		if(taskType == NodeType.singleTask){
			tn.addInPosition(PlumpDefine.TOP_CENTER);
			tn.addOutPosition(PlumpDefine.BOTTOM_CENTER);
			
		} else if(taskType == NodeType.splitTask){
			tn.addInPosition(PlumpDefine.TOP_CENTER);
			tn.addOutPosition(PlumpDefine.TOP_LEFT);
			tn.addOutPosition(PlumpDefine.TOP_RIGHT);
			tn.addOutPosition(PlumpDefine.LEFT_MIDDLE);
			tn.addOutPosition(PlumpDefine.RIGHT_MIDDLE);
			tn.addOutPosition(PlumpDefine.BOTTOM_LEFT);
			tn.addOutPosition(PlumpDefine.BOTTOM_RIGHT);
			tn.addOutPosition(PlumpDefine.BOTTOM_CENTER);
			
		} else if(taskType == NodeType.affluenceTask){
			tn.addInPosition(PlumpDefine.TOP_CENTER); // 汇聚节点可以用多个输入节点，方便不同方向连接
			tn.addInPosition(PlumpDefine.LEFT_MIDDLE);
			tn.addInPosition(PlumpDefine.RIGHT_MIDDLE);
			tn.addOutPosition(PlumpDefine.BOTTOM_CENTER);
			
		} else if(taskType == NodeType.splitAndAffluenceTask 
				|| taskType == NodeType.subProcessTask){
			if(taskType == NodeType.subProcessTask && isStart){
				// 子流程任务如果是开始任务，不能有输入节点
				tn.addInPosition(PlumpDefine.TOP_CENTER);
			} else {
				tn.addInPosition(PlumpDefine.TOP_LEFT);
				tn.addInPosition(PlumpDefine.TOP_CENTER);
				tn.addInPosition(PlumpDefine.TOP_RIGHT);
			}
			tn.addOutPosition(PlumpDefine.LEFT_MIDDLE);
			tn.addOutPosition(PlumpDefine.RIGHT_MIDDLE);
			tn.addOutPosition(PlumpDefine.BOTTOM_LEFT);
			tn.addOutPosition(PlumpDefine.BOTTOM_RIGHT);
			tn.addOutPosition(PlumpDefine.BOTTOM_CENTER);
			
		} else if(taskType == NodeType.edgeTask){
			tn.addInPosition(PlumpDefine.TOP_CENTER);
			tn.addInPosition(PlumpDefine.LEFT_MIDDLE);
			tn.addInPosition(PlumpDefine.RIGHT_MIDDLE);
			tn.addInPosition(PlumpDefine.BOTTOM_CENTER);
		} else {
			throw new UnsupportedOperationException("unsupported nodeType: " + taskType);
		}
		return tn;
	}
	
	private static final String DEFAULT_XY = "20";
	
	/**
	 * 创建新的任务连接线，从当前离开节点到下一个任务的进入节点。
	 * @param processDefineId 流程ID
	 * @param srcTaskDefineId 当前任务ID
	 * @param srcOutPosition 当前离开的节点位置
	 * @param destTaskDefineId 下一步任务ID
	 * @param destInPosition 目的节点进入的位置
	 * @return 返回保存的任务连接信息。
	 */
	public Object[] execNewConnection(String processDefineId
			, String srcTaskDefineId, String srcOutPosition
			, String destTaskDefineId, String destInPosition){
		
		// 当前任务的输出节点
		TaskOut srcTaskOut = new TaskOut();
		srcTaskOut.setId(MiscUtils.generateSortId());
		srcTaskOut.setProcessDefineId(processDefineId);
		srcTaskOut.setTaskDefineId(srcTaskDefineId);
		srcTaskOut.setNextTask(destTaskDefineId);
		srcTaskOut.setOrderNum(designDao.getMaxTaskoutOrder());
		designDao.insertTaskout(srcTaskOut);
		
		// 下一个任务的输入节点
		TaskIn destTaskIn = new TaskIn();
		destTaskIn.setId(MiscUtils.generateSortId());
		destTaskIn.setProcessDefineId(processDefineId);
		destTaskIn.setTaskDefineId(destTaskDefineId);
		destTaskIn.setPreviousTask(srcTaskDefineId);
		designDao.insertTaskIn(destTaskIn);
		
		// 当前任务离开节点位置信息
		AnchorOut anchorOut = new AnchorOut().setId(MiscUtils.generateSortId())
				.setTaskNodeId(srcTaskDefineId)
				.setPosition(srcOutPosition)
				.setNextNode(destTaskDefineId)
				.setNextPosition(destInPosition);
		designDao.insertAnchorOut(anchorOut);
		
		// 下一个任务进入节点位置信息
		AnchorIn anchorIn = new AnchorIn().setId(MiscUtils.generateSortId())
				.setTaskNodeId(destTaskDefineId)
				.setPosition(destInPosition)
				.setFromNode(srcTaskDefineId)
				.setFromPosition(srcOutPosition);
		designDao.insertAnchorIn(anchorIn);
		
		Object[] result = new Object[4];
		result[0] = srcTaskOut;
		result[1] = destTaskIn;
		result[2] = anchorOut;
		result[3] = anchorIn;
		return result;
	}
	
	public void execDeleteConnection(String processDefineId
			, String srcTaskDefineId, String srcOutPosition
			, String destTaskDefineId, String destInPosition){
		designDao.deleteTaskOut(processDefineId, srcTaskDefineId, destTaskDefineId);
		designDao.deleteTaskIn(processDefineId, destTaskDefineId, srcTaskDefineId);
		designDao.deleteAnchorOut(srcTaskDefineId, srcOutPosition);
		designDao.deleteAnchorIn(destTaskDefineId, destInPosition, srcTaskDefineId);
	}
	
	/**
	 * 把任务的离开节点移动到其他任务的离开（输出）节点
	 * @param oldSrcTaskId 移动之前的源任务节点ID
	 * @param changedSrcTaskId 移动之后的源任务节点ID
	 * @param oldSrcPosition 移动之前的源节点位置
	 * @param changedSrcPosition 移动之后的源节点位置
	 * @param targetTaskId 当前操作的任务ID，即：目的任务的ID
	 * @param targetPosition 目的任务的输入节点位置
	 * @param processDefineId 当前流程ID
	 * @return 返回中间创建的对象，方便更新缓存
	 */
	public Object[] execChangeSrcEndpoint(String oldSrcTaskId, String changedSrcTaskId
			, String oldSrcPosition, String changedSrcPosition
			, String targetTaskId, String targetPosition
			, String processDefineId){
		// 改变了任务的输出节点，移到了其他任务上
		Object[] result = new Object[2];
		
		/* 改变节点的输入、输出信息 */
		designDao.deleteTaskOut(processDefineId, oldSrcTaskId, targetTaskId);
		TaskOut addTaskout = new TaskOut();
		addTaskout.setId(MiscUtils.generateSortId());
		addTaskout.setProcessDefineId(processDefineId);
		addTaskout.setTaskDefineId(changedSrcTaskId);
		addTaskout.setNextTask(targetTaskId);
		addTaskout.setOrderNum(designDao.getMaxTaskoutOrder());
		designDao.insertTaskout(addTaskout);
		result[0] = addTaskout;
		designDao.updateTaskIn(processDefineId, targetTaskId, changedSrcTaskId);
		
		/* 改变节点的位置信息 */
		designDao.deleteAnchorOut(oldSrcTaskId, oldSrcPosition);
		designDao.updateAnchorIn(changedSrcTaskId, changedSrcPosition
				, targetTaskId, targetPosition, oldSrcTaskId);
		AnchorOut ao = new AnchorOut().setId(MiscUtils.generateSortId())
				.setTaskNodeId(changedSrcTaskId)
				.setPosition(changedSrcPosition)
				.setNextNode(targetTaskId)
				.setNextPosition(targetPosition);
		designDao.insertAnchorOut(ao);
		result[1] = ao;
		
		return result;
	}
	
	/**
	 * 把任务的进入节点，从一个任务移动到另一个任务中，目标节点变化了。
	 * @param oldTargetTaskId 移动之前目标任务ID
	 * @param changedTargetTaskId 移动之后目标任务ID
	 * @param oldTargetPosition 移动之前目标的位置
	 * @param changedTargetPosition 移动之后目标的位置
	 * @param sourceTaskId 源任务节点（离开）任务ID
	 * @param sourcePosition 源任务位置信息
	 * @param processDefineId 流程ID
	 * @return 返回中间创建的对象，方便更新缓存
	 */
	public Object[] execChangeDestEndpoint(String oldTargetTaskId, String changedTargetTaskId
			, String oldTargetPosition, String changedTargetPosition
			, String sourceTaskId, String sourcePosition
			, String processDefineId){
		
		Object[] result = new Object[2];
		
		// 改变任务输入、输出节点
		designDao.deleteTaskIn(processDefineId, oldTargetTaskId, sourceTaskId);
		TaskIn addTaskIn = new TaskIn();
		addTaskIn.setId(MiscUtils.generateSortId());
		addTaskIn.setProcessDefineId(processDefineId);
		addTaskIn.setTaskDefineId(changedTargetTaskId);
		addTaskIn.setPreviousTask(sourceTaskId);
		result[0] = addTaskIn;
		designDao.insertTaskIn(addTaskIn);
		designDao.updateTaskOut(processDefineId, sourceTaskId, changedTargetTaskId);
		
		/* 改变节点的位置信息 */
		designDao.deleteAnchorIn(oldTargetTaskId, oldTargetPosition, sourceTaskId);
		AnchorIn ai = new AnchorIn().setId(MiscUtils.generateSortId())
				.setTaskNodeId(changedTargetTaskId)
				.setPosition(changedTargetPosition)
				.setFromNode(sourceTaskId).setFromPosition(sourcePosition);
		designDao.insertAnchorIn(ai);
		designDao.updateAnchorOut(changedTargetTaskId
				, changedTargetPosition, sourceTaskId, sourcePosition);
		result[1] = ai;
		return result;
	}
	
	/**
	 * 改变当前任务的输出节点位置信息，即：节点内变位置
	 * @param sourceTaskId 当前任务ID
	 * @param oldSourcePosition 原始位置
	 * @param changedSourcePosition 变动后位置
	 * @param targetTaskId 目的任务ID
	 * @param targetPosition 目的节点位置
	 */
	public void execChangeSrcPosition(String sourceTaskId
			, String oldSourcePosition, String changedSourcePosition
			, String targetTaskId, String targetPosition){
		assert (StringUtils.isNotEmpty(sourceTaskId));
		assert (StringUtils.isNotEmpty(oldSourcePosition));
		assert (StringUtils.isNotEmpty(changedSourcePosition));
		designDao.updateAnchorOutPosition(changedSourcePosition
				, sourceTaskId, oldSourcePosition);
		designDao.updateAnchorInPrePosition(changedSourcePosition
				, targetTaskId, targetPosition, sourceTaskId);
	}
	
	/**
	 * 改变当前任务的输入节点位置信息，即：节点内变位置
	 * @param targetTaskId
	 * @param oldTargetPosition
	 * @param changedTargetPosition
	 */
	public void execChangeDestPosition(String targetTaskId
			, String fromTask, String fromPosition
			, String oldTargetPosition, String changedTargetPosition){
		assert (StringUtils.isNotEmpty(targetTaskId));
		assert (StringUtils.isNotEmpty(oldTargetPosition));
		assert (StringUtils.isNotEmpty(changedTargetPosition));
		designDao.updateAnchorInPosition(changedTargetPosition
				, targetTaskId, oldTargetPosition, fromTask);
		designDao.updateAnchorOutNextPosition(changedTargetPosition, targetTaskId, fromPosition);
	}
	
	/**
	 * 批量更新任务节点坐标信息。
	 * @param parameters 任务坐标集合，集合中每个对象为数组，里面有三个值，<br>
	 * 分别对应：x,y,id
	 */
	public void execUpdateNodePositions(List<Object[]> parameters){
		assert (parameters != null);
		if(parameters.size() == 0){
			return;
		}
		designDao.batchUpdateNodePositions(parameters);
	}
	
	/**
	 * 删除一个任务定义节点
	 * @param taskDefineId
	 */
	public void execDeleteTask(String taskDefineId){
		assert (StringUtils.isNotEmpty(taskDefineId));
		// 删除其他任务中与该任务有关联的连线节点
		designDao.deleteTaskInAsso(taskDefineId);
		designDao.deleteTaskOutAsso(taskDefineId);
		designDao.deleteTaskDefine(taskDefineId);
		
		designDao.deleteAnchorInAsso(taskDefineId);
		designDao.deleteAnchorOutAsso(taskDefineId);
		designDao.deleteTaskNode(taskDefineId);
	}
	
	/**
	 * 调整任务节点上的位置，此时需要删除当前任务中与其他任务有关联的任何信息。<br>
	 * 即：用户调整了节点位置后，该节点所有连接线等于重新初始化，已有的都被清除。
	 * @param taskDefineId
	 */
	public void execAjustNodePosition(String taskDefineId
			, PositionAjustor ajustor, TaskNode oldCacheNode){
		designDao.deleteTaskInAsso(taskDefineId);
		designDao.deleteTaskOutAsso(taskDefineId);
		designDao.deleteAnchorInAsso(taskDefineId);
		designDao.deleteAnchorOutAsso(taskDefineId);
		
		// 同时还要更新任务节点的子表，因为主表数据还在，所以子表不会被关联删除
		designDao.deleteTaskoutWithoutEnd(taskDefineId);
		designDao.deleteTaskInWithoutStart(taskDefineId);
		designDao.deleteAllAnchorOut(taskDefineId);
		designDao.deleteAllAnchorIn(taskDefineId);
		
		// 防止其中一个为空值更新错误
		String changedInPos  = ajustor.getSelectInPositions();
		String changedOutPos = ajustor.getSelectOutPositions();
		if(StringUtils.isEmpty(changedInPos))
			changedInPos = oldCacheNode.getInPositions();
		if(StringUtils.isEmpty(changedOutPos))
			changedOutPos = oldCacheNode.getOutPositions();
		designDao.updateTaskNodeInOutPos(changedInPos, changedOutPos, taskDefineId);
	}
	
	/**
	 * 设置任务为结束任务
	 * @param taskDefineId
	 * @param processDefineId
	 */
	public TaskOut execSetTaskEnd(String taskDefineId, String processDefineId){
		TaskOut taskOut = new TaskOut();
		taskOut.setId(MiscUtils.generateSortId());
		taskOut.setTaskDefineId(taskDefineId);
		taskOut.setProcessDefineId(processDefineId);
		taskOut.setNextTask(TaskBaseBehavior.END_NAME);
		taskOut.setOrderNum(designDao.getMaxTaskoutOrder());
		designDao.insertTaskout(taskOut);
		designDao.updateTaskEnd(taskDefineId);
		return taskOut;
	}
	
	/**
	 * 取消任务为结束任务。
	 * @param taskDefineId
	 * @param processDefineId
	 */
	public void execCancelTaskEnd(String processDefineId, String taskDefineId){
		designDao.deleteEndTaskout(taskDefineId);
		designDao.updateCancelTaskEnd(taskDefineId);
	}
	
	/**
	 * 更新分拆任务定义记录，同时需要处理分拆类型。
	 * @param task
	 * @param conditions
	 * @param defaultNextTask
	 */
	public void execEditSplitTask(TaskDefineAbstract task
			, List<Object[]> conditions, String defaultNextTask){
		designDao.updateTask(task);
		if(task.getSplitType() == TaskBaseBehavior.SPLIT_TYPE_CONDITION){
			designDao.batchUpdateSplitConditions(conditions);
		} else if(task.getSplitType() == TaskBaseBehavior.SPLIT_TYPE_USERSELECT){
			// 如果已经存在了默认选择，必须更新为0，否则会出现多个默认任务的情况。
			designDao.updateRemoveDefaultTask(task.getId());
			// 更新新的默认任务
			designDao.updateDefaultTask(task.getId(), defaultNextTask);
		}
	}
	
	/**
	 * 编辑保存任务基本信息
	 * @param task 任务对象
	 */
	public void execEditTask(TaskDefineAbstract task){
		designDao.updateTask(task);
	}
	
	/**
	 * 更新任务定义中参与者类型以及分配信息
	 * @param actorTypeBean 修改过的beanId
	 * @param taskId 任务ID
	 * @param actorAssigns 用户提交的新的分配结果集合
	 */
	public void execSaveOrUpdateActorInfo(String actorTypeBean
			, String taskId, List<ActorAssign> actorAssigns){
		designDao.updateTaskActor(actorTypeBean, taskId);
		
		// 1-先删除之前已经存在的sw_actor_assign表中记录，再创建新的
		// 因为用户可能会从各种模型之间切换，因此需要先删除老actor_assign数据
		designDao.deleteTaskActorAssign(taskId);
		if(actorAssigns != null && actorAssigns.size() > 0){
			// 说明选择的类型是：固定人员或者范围列表 方式
			List<Object[]> parameters = new ArrayList<Object[]>();
			Object[] p = null;
			for(ActorAssign aa : actorAssigns){
				p = new Object[6];
				p[0] = aa.getId();
				p[1] = aa.getTaskDefineId();
				p[2] = aa.getActorId();
				p[3] = aa.getActorName();
				p[4] = aa.getOrderNum();
				p[5] = aa.getProcessDefineId();
				parameters.add(p);
			}
			designDao.insertTaskActorAssign(parameters);
		}
	}
	
	public void execPublishProcess(String processDefineId){
		designDao.updateProcessPublishStatus(1, processDefineId);
	}
	
	public void execCancelPublishProcess(String processDefineId){
		designDao.updateProcessPublishStatus(0, processDefineId);
	}
}
