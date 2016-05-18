package com.walkersoft.system.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.infrastructure.arguments.Group;
import com.walker.infrastructure.arguments.Variable;
import com.walker.infrastructure.arguments.VariableType;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.log.MyLogDetail.LogType;
import com.walkersoft.system.SystemAction;

@Controller
public class ArgumentsAction extends SystemAction {

//	private ArgumentsManager argumentManager = ArgumentsManagerFactory.getArgumentsManager(MyApplicationConfig.DEFAULT_CONFIG_FILENAME);
	
	private static final String PARAMETER_GROUPID = "groupId";
	private static final String PARAMETER_DATAS = "datas";
	
	private static final String ATTRIBUTE_GROUPLIST = "groupList";
	private static final String ATTRIBUTE_VARLIST = "varList";
	private static final String ATTRIBUTE_GROUPID = "groupId";
	
	private static final String MSG_TIP_OK = "配置选项更新成功";
	private static final String MSG_TIP_NO = "没有修改任何选项，不更新。";
	
	@RequestMapping(value = "admin/args/index")
	public String index(Model model){
		getGroupData(model);
		return BASE_URL + "arguments";
	}
	
	@RequestMapping(value = "permit/admin/args/load")
	public String loadVariable(Model model){
		String groupId = this.getParameter(PARAMETER_GROUPID);
		if(StringUtils.isEmpty(groupId)){
			throw new NullPointerException("groupId is required!");
		}
		getGroupContent(groupId, model);
		return BASE_URL + "arguments_list";
	}
	
	private void getGroupData(Model model){
		List<Group> groups = argumentManager.getGroupList();
		if(groups == null || groups.size() == 0){
			model.addAttribute(ATTRIBUTE_GROUPLIST, null);
		} else {
			model.addAttribute(ATTRIBUTE_GROUPLIST, groups);
			getGroupContent(groups.get(0).getId(), model);
		}
	}
	
	/**
	 * 查询出来第一个分组信息，在第一次进入主界面时显示。
	 * @param groupId
	 * @param model
	 */
	private void getGroupContent(String groupId, Model model){
		if(StringUtils.isEmpty(groupId))
			return;
		List<Variable> datas = argumentManager.getVariableList(groupId);
		if(datas != null){
			model.addAttribute(ATTRIBUTE_VARLIST, datas);
			model.addAttribute(ATTRIBUTE_GROUPID, groupId);
		} else
			model.addAttribute(ATTRIBUTE_VARLIST, null);
	}
	
	@RequestMapping(value = "admin/args/save")
	public void save(HttpServletResponse response) throws Exception{
		String datas = this.getParameter(PARAMETER_DATAS);
		if(StringUtils.isEmpty(datas)){
			throw new IllegalArgumentException("not found parameter: datas!");
		}
		
		/**
		 * 接收参数格式：
		 * <pre>
		 * groupId,value1,value2...
		 * 数组第一个为分组ID，后面都是顺序提交的配置项的值。
		 * </pre>
		 */
		String[] args = StringUtils.toArray(datas);
		if(args == null || args.length < 1)
			throw new IllegalArgumentException("not corrected args!");
		if(args.length == 1)
			return;
		
		String groupId = args[0];
		List<String> variableVals = new ArrayList<String>(args.length-1);
		for(int i=1; i<args.length; i++){
			variableVals.add(args[i]);
		}
		
		List<Variable> oldVars = argumentManager.getVariableList(groupId);
		if(oldVars.size() != (args.length-1)){
			throw new Error("提交的配置值数量与系统定义的不一致");
		}
		
		// 修改过的放在变量中
		List<Object[]> edited = new ArrayList<Object[]>();
		
		for(int i=0; i<oldVars.size(); i++){
			if(isModified(oldVars.get(i), variableVals.get(i))){
				edited.add(new Object[]{groupId, oldVars.get(i).getId(), variableVals.get(i)});
			}
		}
		
//		if(logger.isDebugEnabled()){
//			logger.debug("共修改的参数为：" + edited.size() + "个");
//		}
		for(Object[] arr : edited){
			systemLog("修改了全局参数:" + arr[1] + "," + arr[2] , LogType.Edit);
			logger.debug(arr[1] + " : " + arr[2]);
		}
		
		if(edited.size() > 0){
			/* 保存数据 */
			argumentManager.persist(edited);
			this.ajaxOutPutText(MSG_TIP_OK);
		} else {
			this.ajaxOutPutText(MSG_TIP_NO);
		}
		
	}
	
	/**
	 * 判断给定的参数是否被修改过
	 * @param old
	 * @param newVal
	 * @return
	 */
	private boolean isModified(Variable old, String newVal){
		VariableType type = old.getType();
		if(type == VariableType.String){
			if(!old.getStringValue().equals(newVal))
				return true;
		} else if(type == VariableType.Boolean){
			if(old.getBooleanValue() != Boolean.valueOf(newVal))
				return true;
		} else if(type == VariableType.Integer){
			if(old.getIntegerValue() != Integer.parseInt(newVal))
				return true;
		} else if(type == VariableType.Float){
			if(old.getFloatValue() != Float.parseFloat(newVal))
				return true;
		}
		return false;
	}
}
