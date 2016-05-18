package com.walkersoft.flow.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.db.PropertyEntry;
import com.walker.db.Sorts;
import com.walker.db.Sorts.Sort;
import com.walker.db.page.support.GenericPager;
import com.walker.flow.client.SpringWorkerClient;
import com.walker.flow.core.actor.MultiActorNextException;
import com.walker.flow.core.biz.BusinessUpdateFailerException;
import com.walker.flow.engine.NextTaskNotFoundException;
import com.walker.flow.meta.NextTaskOptionDTO;
import com.walkersoft.flow.dao.ProcessInstanceDao;
import com.walkersoft.flow.entity.BizDataEntity;
import com.walkersoft.flow.pojo.AwaitTask;
import com.walkersoft.flow.pojo.ProcessItem;
import com.walkersoft.flow.pojo.TaskItem;

@Service("processInstanceManager")
public class ProcessInstanceManagerImpl {

//	private final transient Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private ProcessInstanceDao processInstanceDao;
	
	/**
	 * 返回用户的代办任务集合
	 * @param userId 用户ID
	 * @return
	 */
	public GenericPager<AwaitTask> queryAwaitTaskPager(String userId){
		return processInstanceDao.getAwaitTaskPager(userId);
	}
	
	/**
	 * 返回所有等待办理的任务列表
	 * @return
	 */
	public GenericPager<AwaitTask> queryAwaitTaskPager(){
		return processInstanceDao.getAwaitTaskPager();
	}
	
	/**
	 * 返回所有流程实例列表，此列表能看到任务执行情况。
	 * @return
	 */
	public GenericPager<ProcessItem> queryAllProcessListPager(){
		return processInstanceDao.getAllProcessListPager();
	}
	
	private final Sort bizDataSort = Sorts.ASC().setField("createTime");
	
	/**
	 * 返回一条流程实例记录，用来展示
	 * @param processInstId
	 * @return 数组，0 = 流程实例对象，1 = 流程创建参数列表
	 */
	public Object[] queryProcessInstance(String processInstId){
		Object[] result = new Object[2];
		result[0] = processInstanceDao.getProcessInstance(processInstId);
		result[1] = processInstanceDao.findBy(BizDataEntity.class
				, PropertyEntry.createEQ("processInstId", processInstId), bizDataSort);
		return result;
	}
	
	/**
	 * 返回某个流程实例对应的所有任务实例集合
	 * @param processInstId 流程实例ID
	 * @return
	 */
	public List<TaskItem> queryTaskInstanceList(String processInstId){
		return processInstanceDao.getTaskInstanceList(processInstId);
	}
	
	/**
	 * 转发操作
	 * @param taskInstId
	 * @param loginUserId
	 * @param loginUserName
	 * @param opinion 意见
	 * @param nextOption 下一个任务选项，在多人选择抛出异常时使用
	 * @return
	 * @throws MultiActorNextException
	 * @throws BusinessUpdateFailerException
	 * @throws NextTaskNotFoundException
	 */
	public boolean execTransmitNext(String taskInstId, String loginUserId
			, String loginUserName, String opinion, NextTaskOptionDTO nextOption) 
		throws MultiActorNextException, BusinessUpdateFailerException, NextTaskNotFoundException{
		return SpringWorkerClient.transmitNext(taskInstId, loginUserId, loginUserName, opinion, nextOption);
	}
	
	/**
	 * 退回到流程上一步
	 * @param taskInstId 任务实例ID
	 * @param loginUserId
	 * @param loginUserName
	 * @param opinion 意见备注
	 * @return 退回成功返回<code>true</code>
	 */
	public void execTransmitBack(String taskInstId
			, String loginUserId, String loginUserName, String opinion) throws Exception{
		SpringWorkerClient.transmitBackPrevious(taskInstId, loginUserId, loginUserName, opinion);
	}
}
