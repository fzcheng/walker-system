package com.walkersoft.flow.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.app.ApplicationRuntimeException;
import com.walker.flow.core.actor.MultiActorNextException;
import com.walker.flow.core.actor.node.AbstractActorNode;
import com.walker.flow.core.biz.BusinessUpdateFailerException;
import com.walker.flow.engine.NextTaskNotFoundException;
import com.walker.flow.meta.NextTaskOptionDTO;
import com.walker.flow.meta.TaskDefineAbstract;
import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.flow.FlowAction;
import com.walkersoft.flow.MiscUtils;
import com.walkersoft.flow.manager.ProcessInstanceManagerImpl;
import com.walkersoft.system.entity.UserCoreEntity;

/**
 * 流程运行交互处理Action实现
 * @author shikeying
 * @date 2014-8-18
 *
 */
@Controller
public class RuntimeAction extends FlowAction {

	private static final String URL_MAIN_PAGE = "flow/run/index";
	
	private static final String ATTR_ALLOWED_TRANS_BACK = "allowedBack";
	private static final String ATTR_NEXT_ACTOR_LIST = "nextActorList";
	
	private static final String DEFAULT_CALLBACK_JS = "reload(0)";
	
	@Autowired
	private ProcessInstanceManagerImpl processInstanceManager;
	
	/**
	 * 显示流程处理界面
	 * @param model
	 * @param instId 流程实例ID
	 * @param defineId 流程定义ID
	 * @param bizId 业务ID
	 * @param callbackJs 执行完任务后，界面回调的js方法
	 * @param userId 当前任务执行人ID
	 * @return
	 */
	@RequestMapping("permit/flow/runtime/index")
	public String showMainProcessPage(Model model
			, String instId, String defineId, String taskInstId, String taskDefineId
			, String bizId, String callbackJs, String userId){
		Assert.hasText(instId);
		Assert.hasText(defineId);
		Assert.hasText(taskInstId);
		Assert.hasText(taskDefineId);
		
		UserCoreEntity user = this.getCurrentUser();
		if(StringUtils.isNotEmpty(userId) && !user.getId().equals(userId)){
			this.setErrorMessage(model, null, "该任务不属于您，无法执行");
			return URL_ERROR;
		}
		
		model.addAttribute("processDefineId", defineId);
		model.addAttribute("processId", instId);
		model.addAttribute("taskInstId", taskInstId);
		// 是否允许退回操作 
		model.addAttribute(ATTR_ALLOWED_TRANS_BACK, this.isAllowedBack(taskInstId, user.getId(), user.getName()));
		
		// 下一步参与者预览
		boolean multiNextActor = false;//是否下一步多参与者标志
		List<AbstractActorNode> nextActors = this.getNextActorList(taskInstId, user.getId(), user.getName());
		if(nextActors != null && nextActors.size() > 1){
			// 存在多个参与者，就让用户自己选择
			multiNextActor = true;
		}
		model.addAttribute("multiNextActor", multiNextActor);
		model.addAttribute(ATTR_NEXT_ACTOR_LIST, nextActors);
		
		// 业务url
		String businessUrl = getBusinessUrl(taskDefineId, defineId);
		if(StringUtils.isNotEmpty(businessUrl)){
			model.addAttribute("bizUrl", businessUrl+"?id="+bizId);
		} else
			model.addAttribute("bizUrl", businessUrl);
		
		// 执行的js方法
		if(StringUtils.isEmpty(callbackJs)){
			callbackJs = DEFAULT_CALLBACK_JS;
		}
		model.addAttribute("transmitCallback", callbackJs);
		return URL_MAIN_PAGE;
	}
	
	private String getBusinessUrl(String taskDefineId, String processDefineId){
		TaskDefineAbstract task = this.getTaskDefine(taskDefineId);
		String url = task.getPageUrl();
		if(StringUtils.isNotEmpty(url)){
			return url;
		}
		return this.getProcessDefine(processDefineId).getPageUrlId();
	}
	
	/**
	 * 转发下一步
	 * @param taskInstId 任务实例ID
	 * @param opinion 意见
	 * @param selectedActor 已经存在的多个参与者字符串
	 * @param mustSelectActor 必须选择参与者开个，如果为1表示是通过抛出异常返回了多个参与者，重新选择后提交的
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("permit/flow/runtime/trans_next")
	public void transmitNext(String taskInstId, String opinion, String selectedActor
			, String mustSelectActor, HttpServletResponse response) throws IOException{
		Assert.hasText(taskInstId);
		Assert.hasText(mustSelectActor);
		UserCoreEntity user = this.getCurrentUser();
		// 下一步任务选项
		NextTaskOptionDTO nextTaskOption = null;
		try {
			if(mustSelectActor.equals("1")){
				// 选择了参与者
				if(StringUtils.isEmpty(selectedActor)){
					throw new ApplicationRuntimeException("要选择参与者，但没有提供, taskInstId = " + taskInstId);
				}
				nextTaskOption = MiscUtils.obtainNextTaskOptionInMultiActorException(selectedActor, opinion);
			}
//			else {
//				nextTaskOption = MiscUtils.obtainNextTaskOptionInMultiActorException(null, opinion);
//			}
			boolean res = processInstanceManager.execTransmitNext(taskInstId
					, user.getId(), user.getName(), opinion, nextTaskOption);
			if(res){
				this.ajaxOutPutText(MESSAGE_SUCCESS);
			} else {
				this.ajaxOutPutText("系统错误：任务执行失败。res = false");
			}
		} catch (MultiActorNextException e) {
			logger.warn("发现下一步存在多个参与用户，需要选择。");
			// 第二个参数使用"任务实例ID"，能支持每个任务实例都可以缓存选择的参与者，
			// 而不会冲突。
//			List<AbstractActorNode> memActors = UiEditorController.getMemoriedActorList(getCurrentUserId()
//					, taskInstId, null);
			List<AbstractActorNode> memActors = e.getActorList();
			if(StringUtils.isEmptyList(memActors)){
				this.ajaxOutPutText("下一步任务配置不正确，未找到配置的多个参与者");
				return;
			}
			this.ajaxOutPutText(doResponseMultiActorInfo(memActors));
		} catch (BusinessUpdateFailerException e) {
			throw new ApplicationRuntimeException("业务更新错误，暂未实现", e);
		} catch (NextTaskNotFoundException e) {
			this.ajaxOutPutText("流程错误：未找到下一步执行任务");
		}
	}
	
	private String doResponseMultiActorInfo(List<AbstractActorNode> memActors){
		StringBuilder result = new StringBuilder("multiActorNext");
		result.append(",");
		if(memActors != null){
			int i = 0;
			for(AbstractActorNode actor : memActors){
				if(i > 0){
					result.append(",");
				}
				result.append(actor.getNodeId());
				result.append("|");
				result.append(actor.getNodeName());
				i++;
			}
		}
		return result.toString();
	}
	
	@RequestMapping("permit/flow/runtime/trans_back")
	public void transmitBack(String taskInstId, String opinion
			, HttpServletResponse response) throws IOException{
		Assert.hasText(taskInstId);
		UserCoreEntity user = this.getCurrentUser();
		try{
			processInstanceManager.execTransmitBack(taskInstId, user.getId(), user.getName(), opinion);
			this.ajaxOutPutText(MESSAGE_SUCCESS);
		} catch(Exception e){
			this.systemLog("任务退回异常，taskInstId="+taskInstId + "," + e.getMessage());
			this.ajaxOutPutText("任务退回失败，详情查询系统日志");
		}
	}
}
