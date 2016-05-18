package com.walkersoft.flow;

import java.util.List;

import com.walker.flow.core.condition.expression.ConditionInterpretor;
import com.walker.flow.meta.TaskBaseBehavior;
import com.walker.flow.meta.TaskDefineAbstract;
import com.walker.flow.meta.TaskOut;
import com.walker.infrastructure.utils.StringUtils;

/**
 * 处理任务执行条件的控制器
 * @author shikeying
 *
 */
public class TaskConditionAction extends FlowAction {

	protected List<TaskOut> getTaskOutList(String taskDefineId){
		List<TaskOut> list = getFlowCacheFactory().getTaskOutList(taskDefineId);
		if(list != null){
			for(TaskOut to : list){
				to.setNextTaskName(getTaskName(to.getNextTask()));
			}
		}
		return list;
	}
	
	/**
	 * 把用户输入的条件表达式转换成系统识别的后缀表达式
	 * @param userInput
	 * @return
	 */
	protected String getExpression(String userInput){
		assert (StringUtils.isNotEmpty(userInput));
		ConditionInterpretor et = new ConditionInterpretor(userInput);
		return et.interprete();
	}
	
	/**
	 * 编辑分拆任务时，更新任务缓存
	 * @param task 任务对象
	 * @param conditionList 条件集合，此参数在'条件分拆'方式时使用
	 * @param defaultNextTask 默认选择的下一步任务ID，此参数在'用户分拆'方式时使用
	 */
	protected void updateSplitTaskCache(TaskDefineAbstract task
			, List<Object[]> conditionList, String defaultNextTask){
		taskDefineCache.updateCacheData(task.getId(), task);
		List<TaskOut> list = null;
		if(task.getSplitType() == TaskBaseBehavior.SPLIT_TYPE_CONDITION){
			list = getFlowCacheFactory().getTaskOutList(task.getId());
			Object[] conditionObj = null;
			for(TaskOut to : list){
				conditionObj = getTaskOutCondition(to.getNextTask(), conditionList);
				if(conditionObj == null) throw new IllegalArgumentException();
				to.setCondition(conditionObj[0].toString());
				to.setExpression(conditionObj[1].toString());
			}
		} else if(task.getSplitType() == TaskBaseBehavior.SPLIT_TYPE_USERSELECT){
			list = getFlowCacheFactory().getTaskOutList(task.getId());
			for(TaskOut to : list){
				if(to.getNextTask().equals(defaultNextTask)){
					to.setDefaultTask(1);
				} else {
					to.setDefaultTask(0);
				}
			}
		}
		logger.debug("........更新后的taskoutList = " + getFlowCacheFactory().getTaskOutList(task.getId()));
	}
	
	private Object[] getTaskOutCondition(String nextTask, List<Object[]> conditionList){
		for(Object[] obj : conditionList){
			if(obj[3].toString().equals(nextTask)){
				return obj;
			}
		}
		return null;
	}
}
