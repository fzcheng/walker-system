package com.walkersoft.system.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.file.FileEngine.StoreType;
import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.StringUtils;
import com.walker.web.WebPageException;
import com.walkersoft.application.log.MyLogDetail.LogType;
import com.walkersoft.system.SystemAction;
import com.walkersoft.system.entity.DepartmentEntity;
import com.walkersoft.system.manager.DepartmentManagerImpl;

@Controller
public class DepartmentAction extends SystemAction {
	
	/**
	 * 参数名：是否部门
	 */
	private static final String IS_DEPARTMENT_NAME = "isDepartment";
	private static final String PARENT_ID_NAME = "parentId";
	private static final String CURRENT_ENTITY_NAME = "entity";
	private static final String PARENT_NODE_NAME = "parentName";
	private static final String IS_EDIT_NAME = "isEdit";
	
	private static final String MSG_TIP_NAME = "msg";
	private static final String MSG_TIP_EXISTUSER = "机构下存在用户，不能删除";
	
	private static final String LOG_MSG_SAVEDEPT = "创建机构";
	private static final String LOG_MSG_DELDEPT = "删除机构";
	private static final String LOG_MSG_EASEDEPT = "彻底删除机构";
	private static final String LOG_MSG_EDITDEPT = "更新机构";
	private static final String LOG_MSG_EDIT_IN = "进入了编辑机构页面:";
	
	@Autowired
	private DepartmentManagerImpl departmentManager;
	
	@RequestMapping(value = "admin/department/index")
	public String index(Model model){
		setUserPointers(model);
//		model.addAttribute(DEPARTMENT_JSON_SET, this.getAllDepartmentTreeForJson());
		return BASE_URL + "department";
	}
	
	@RequestMapping("permit/admin/department/loadTree")
	public void loadTree(HttpServletResponse response) throws IOException{
//		this.ajaxOutPutJson(getDepartmentTreeForJson(departmentCacheTree.getRootList(), null));
		this.ajaxOutPutJson(this.getAllDepartmentTreeForJson());
	}
	
	private static final String PARAMETER_CANEDIT = "canEdit";
	private static final String PARAMETER_CAN_DEL = "canDel";
	
	@RequestMapping(value = "admin/department/show")
	public String showDetail(Model model){
		String id = this.getParameter("id");
		assert(StringUtils.isNotEmpty(id));
		
		// 是否显示：编辑、删除按钮
		String canEdit = this.getParameter(PARAMETER_CANEDIT);
		String canDel  = this.getParameter(PARAMETER_CAN_DEL);
		
		DepartmentEntity entity = departmentManager.queryDepartment(id);
		model.addAttribute(CURRENT_ENTITY_NAME, entity);
		model.addAttribute(PARAMETER_CANEDIT, StringUtils.isEmpty(canEdit) ? false : true);
		model.addAttribute(PARAMETER_CAN_DEL, StringUtils.isEmpty(canDel) ? false : true);
		
		if(entity.getType() == DepartmentEntity.TYPE_DEPT)
			model.addAttribute(PARENT_NODE_NAME, getParentName(entity.getParentId()));
		return BASE_URL + "department_view";
	}
	
	@RequestMapping(value = "permit/admin/department/showAddOrg")
	public String showAddOrg(Model model){
		String parentId = getParameter(PARENT_ID_NAME);
		if(StringUtils.isNotEmpty(parentId))
			model.addAttribute(PARENT_ID_NAME, parentId);
		model.addAttribute(IS_DEPARTMENT_NAME, false);
		return BASE_URL + "department_add";
	}
	
	/**
	 * 显示添加机构页面（包括：单位和部门）
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "permit/admin/department/showAddDepartment")
	public String showAddDepartment(ModelMap model){
		model.addAttribute(IS_DEPARTMENT_NAME, true);
		model.addAttribute(PARENT_ID_NAME, getParameter(PARENT_ID_NAME));
		return BASE_URL + "department_add";
	}
	
	/**
	 * 保存新机构（包括：单位和部门）
	 * @param entity
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "admin/department/save")
	public String saveDepartment(@ModelAttribute("entity") DepartmentEntity entity
			, Model model, HttpServletRequest request) throws IOException{
		if(entity.getType() == DepartmentEntity.TYPE_ORG){
			departmentManager.execSave(entity, this.getUploadFiles("fileLogo", StoreType.FileSystem));
		} else {
			/* 保存部门，需要更新上级节点对应孩子数量 */
			departmentManager.execSaveDepartment(entity);
			model.addAttribute(PARENT_NODE_NAME, getParentName(entity.getParentId()));
		}
		model.addAttribute(CURRENT_ENTITY_NAME, entity);
		departmentCacheProvider.putCacheData(entity.getId(), entity);
		systemLog(LOG_MSG_SAVEDEPT + entity.getName(), LogType.Add);
		return BASE_URL + "department_view";
	}
	
	@RequestMapping("admin/department/deleteOrg")
	public String delete(Model model){
		String id = this.getParameter("id");
		assert (StringUtils.isNotEmpty(id));
		
		if(haveUserInDepartment(id)){
			model.addAttribute(MSG_TIP_NAME, MSG_TIP_EXISTUSER);
		} else {
			DepartmentEntity deleted = departmentManager.execDelete(id);
			departmentCacheProvider.updateCacheData(id, deleted);
			systemLog(LOG_MSG_DELDEPT+departmentCacheProvider.getDepartmentName(id), LogType.Delete);
		}
		return BASE_URL + "department_tip";
	}
	
	@RequestMapping("admin/department/easeOrg")
	public String ease(Model model){
		String id = this.getParameter("id");
		assert (StringUtils.isNotEmpty(id));
		
		if(haveUserInDepartment(id)){
			model.addAttribute(MSG_TIP_NAME, MSG_TIP_EXISTUSER);
		} else {
			departmentManager.execEase(id);
			departmentCacheProvider.removeCacheData(id);
			systemLog(LOG_MSG_EASEDEPT + id, LogType.Delete);
		}
		return BASE_URL + "department_tip"; 
	}
	
	private boolean haveUserInDepartment(String orgId){
		return this.userCacheProvider.hasUserInDepartment(orgId);
	}
	
	@RequestMapping("permit/admin/department/showEdit")
	public String showEditPage(Model model){
		String id = this.getParameter("id");
		assert (StringUtils.isNotEmpty(id));
		DepartmentEntity entity = departmentManager.queryDepartment(id);
		model.addAttribute(IS_EDIT_NAME, true);
		model.addAttribute(CURRENT_ENTITY_NAME, entity);
		if(entity.isOrg()){
			model.addAttribute(IS_DEPARTMENT_NAME, false);
		} else {
			model.addAttribute(IS_DEPARTMENT_NAME, true);
			model.addAttribute(PARENT_NODE_NAME, getParentName(entity.getParentId()));
		}
			
		systemLog(LOG_MSG_EDIT_IN + entity.getName());
		return BASE_URL + "department_edit";
	}
	
	@RequestMapping("admin/department/edit")
	public String saveEdit(@ModelAttribute("entity") DepartmentEntity entity
			, Model model){
		if(entity.getId() == null)
			throw new WebPageException("机构编号未找到", (String)null);
		departmentCacheProvider.updateCacheData(entity.getId(), departmentManager.execUpdate(entity));
		systemLog(LOG_MSG_EDITDEPT + entity.getName(), LogType.Add);
		return BASE_URL + "department_view";
	}
	
	private String getParentName(String parentId){
		assert (StringUtils.isNotEmpty(parentId));
		return departmentCacheProvider.getDepartmentName(parentId);
	}
	
	/**
	 * 显示添加岗位页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "permit/admin/department/show_add_post")
	public String showAddPost(Model model, String parentId){
		Assert.hasText(parentId);
		DepartmentEntity dept = departmentCacheProvider.getCacheData(parentId);
		if(!dept.isDept() && !dept.isPost()){
//			this.setErrorMessage(model, null, "请选择一个部门");
			model.addAttribute(MSG_TIP_NAME, "请选择一个部门或岗位");
			return BASE_URL + "department_tip";
		}
		model.addAttribute(PARENT_ID_NAME, parentId);
		return URL_POST_ADD;
	}
	
	@RequestMapping(value = "permit/admin/department/save_post")
	public String savePost(@ModelAttribute("entity") DepartmentEntity entity
			, Model model) throws IOException{
		Assert.notNull(entity);
		departmentManager.execSaveDepartment(entity);
		model.addAttribute(PARENT_NODE_NAME, getParentName(entity.getParentId()));
		
		departmentCacheProvider.putCacheData(entity.getId(), entity);
		systemLog("创建岗位：" + entity.getName(), LogType.Add);
		
		return BASE_URL + "department_view";
	}
	
	private static final String URL_POST_ADD = "system/post_add";
}
