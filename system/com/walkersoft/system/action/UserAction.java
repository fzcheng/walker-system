package com.walkersoft.system.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.db.page.ListPageContext;
import com.walker.db.page.PagerView;
import com.walker.db.page.support.GenericPager;
import com.walker.infrastructure.cache.tree.CacheTreeLoadCallback;
import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.MyApplicationConfig;
import com.walkersoft.application.OrgType;
import com.walkersoft.application.UserExistException;
import com.walkersoft.application.UserType;
import com.walkersoft.application.cache.DepartmentCacheProvider.NotWantedDeleteCallback;
import com.walkersoft.application.log.MyLogDetail.LogType;
import com.walkersoft.application.util.CodeUtils;
import com.walkersoft.system.SystemAction;
import com.walkersoft.system.entity.DepartmentEntity;
import com.walkersoft.system.entity.RoleEntity;
import com.walkersoft.system.entity.UserCoreEntity;

@Controller
public class UserAction extends SystemAction {

//	@Autowired
//	private UserManagerImpl userManager;
	
	/* 不需要加载已删除机构树，的回调实现 */
	private static final CacheTreeLoadCallback callback = new NotWantedDeleteCallback();
	
	private static final String LOG_MSG_USERADD = "创建用户";
	private static final String LOG_MSG_USERDEL = "删除用户";
	private static final String LOG_MSG_USEREASE = "彻底删除用户";
	private static final String LOG_MSG_USEREDIT = "编辑用户";
	
	private static final String ATTRIBUTE_ROLE_LIST = "roleList";
	private static final String ATTRIBUTE_USER = "user";
	
	@RequestMapping(value = "admin/user/index")
	public String index(Model model){
		setUserPointers(model);
		model.addAttribute(DEPARTMENT_JSON_SET, this.getAllDepartmentTreeForJson(callback, true));
//		model.addAttribute(DEPARTMENT_JSON_SET, getAllDepartmentTreeWithoutPostForJson());
		loadList(model);
		return BASE_URL + "user";
	}
	
	@RequestMapping("permit/admin/user/loadTree")
	public void loadTree(HttpServletResponse response) throws IOException{
//		this.ajaxOutPutJson(getDepartmentTreeForJson(departmentCacheTree.getAvailableRootList(), callback));
		this.ajaxOutPutJson(this.getAllDepartmentTreeForJson(callback, true));
	}
	
	private void loadList(Model model){
		// 单位或部门ID
		String orgId = this.getParameter("orgId");
		GenericPager<UserCoreEntity> pager = null;
		
		UserType userType = getCurrentUserType();
		
		if(userType == UserType.SuperVisor && StringUtils.isEmpty(orgId)){
			pager = userManager.queryAllUser();
			
		} else {
			if(userType == UserType.Administrator && StringUtils.isEmpty(orgId)){
				orgId = getCurrentUser().getOrgId();
			}
			/* 普通用户不允许管理系统数据 */
			if(userType == UserType.User){
				pager = ListPageContext.createEmptyGenericPager();
			} else {
				if(userType == UserType.SuperVisor 
						&& StringUtils.isNotEmpty(orgId) && orgId.equals(CodeUtils.ZTREE_FROM_OUTTER)){
					pager = userManager.queryOuterUser();
				} else {
					if(getOrgType(orgId) == OrgType.Org){
						pager = userManager.queryOrgUser(orgId);
					} else
						pager = userManager.queryDeptUser(orgId);
				}
			}
		}
		
		// 放入用户单位名称
		List<UserCoreEntity> userList = pager.getDatas();
		if(userList != null){
			for(UserCoreEntity user : userList){
				if(!user.isFromOuter()){
					/* 只有内部用户才从机构缓存中取名字 */
					user.setOrgName(departmentCacheProvider.getDepartmentName(user.getOrgId()));
				}
			}
		}
			
		PagerView<UserCoreEntity> pagerView = ListPageContext.createPagerView(pager, DEFAULT_JS_NAME);
		model.addAttribute(DEFAULT_PAGER_VIEW_NAME, pagerView);
	}
	
	@RequestMapping(value = "permit/admin/user/reload.do")
	public String reloadPage(Model model){
		loadList(model);
		return BASE_URL + "user_list";
	}
	
	@RequestMapping("permit/admin/user/add")
	public String showAddUser(Model model){
		String parentId = this.getParameter(NAME_PARENT_ID);
		assert (StringUtils.isNotEmpty(parentId));
		model.addAttribute(NAME_PARENT_ID, parentId);
		model.addAttribute(NAME_PARENT_NAME, departmentCacheProvider.get(parentId).getText());
		
		// 展示角色列表
		List<RoleEntity> roleList = userManager.queryAllRoles();
		model.addAttribute(ATTRIBUTE_ROLE_LIST, roleList == null ? null : roleList);
		return BASE_URL + "user_add";
	}
	
	@RequestMapping("permit/admin/user/showEdit")
	public String showEdit(Model model){
		String userId = this.getParameter(NAME_USER_ID);
		assert (StringUtils.isNotEmpty(userId));
		UserCoreEntity user = userManager.queryUser(userId);
		model.addAttribute(ATTRIBUTE_USER, user);
		List<RoleEntity> roleList = userManager.queryUserEditRoles(userId);
		model.addAttribute(ATTRIBUTE_ROLE_LIST, roleList == null ? null : roleList);
		model.addAttribute(NAME_PARENT_NAME, getUserDepartmentName(user));
		return BASE_URL + "user_edit";
	}
	
	@RequestMapping("permit/admin/user/show_post")
	public String showPost(Model model, String id){
		Assert.hasText(id);
		UserCoreEntity user = this.getUser(id);
		model.addAttribute("userName", user.getName());
		model.addAttribute(DEPARTMENT_JSON_SET, this.getAllDepartmentTreeForJson());
		return "system/user_post_set";
	}
	
	private String getUserDepartmentName(UserCoreEntity user){
		String departId = user.getDepartmentId();
		if(StringUtils.isEmpty(departId)){
			return departmentCacheProvider.getDepartmentName(user.getOrgId());
		}
		return departmentCacheProvider.getDepartmentName(departId);
	}
	
	private static final String MSG_USER_EXIST = "当前登录ID已经存在";
	
	@RequestMapping("admin/user/save")
	public void saveUser(@ModelAttribute("entity") UserCoreEntity entity
			, HttpServletResponse response) throws IOException{
		if(MyApplicationConfig.isSupervisor(entity.getLoginId())){
			this.ajaxOutPutText(MSG_USER_EXIST);
			return;
		}
		String parentId = this.getParameter(NAME_PARENT_ID);
		if(departmentCacheProvider.getCacheData(parentId).isOrg()){
			entity.setOrgId(parentId);
		} else {
			DepartmentEntity department = departmentCacheProvider.getRootTreeNode(parentId);
			entity.setOrgId(department.getId());
			entity.setDepartmentId(parentId);
		}
		logger.debug("----------> 当前登录用户: " + getCurrentUser().getLoginId());
		
		String roleIdsStr = this.getParameter(NAME_ROLE_IDS);
		String[] roleIds = StringUtils.toArray(roleIdsStr);
		
		/* 设置用户密码，检查系统是否设置加密方式 */
//		entity.setPassword(MyApplicationConfig.encodePassword(MyApplicationConfig.getInitPassword(), entity.getLoginId()));
		entity.setPassword(getEncodeInitPassword(entity.getLoginId()));
		String msg = userManager.execSaveUser(entity, roleIds, getSystemUserCallback());
		if(msg == null){
			userCacheProvider.putCacheData(entity.getId(), entity);
		}
		systemLog(LOG_MSG_USERADD + entity.getName() + entity.getLoginId(), LogType.Add);
		this.ajaxOutPutText(msg == null ? "" : msg);
	}
	
	@RequestMapping("admin/user/edit")
	public void editUser(@ModelAttribute("entity") UserCoreEntity entity
			, HttpServletResponse response) throws IOException{
		if(MyApplicationConfig.isSupervisor(entity.getLoginId())){
			this.ajaxOutPutText(MSG_USER_EXIST);
			return;
		}
		String roleIdsStr = this.getParameter(NAME_ROLE_IDS);
		String[] roleIds = StringUtils.toArray(roleIdsStr);
		
		// 注意：如果修改了登录ID，必须把密码重新用盐值修改，因为目前loginId = 盐值
		// loginId改变，盐值就改变，必须更新密码字段。
		String initPassword = this.getEncodeInitPassword(entity.getLoginId());
		
		try{
			UserCoreEntity updatedUser = userManager.execUpdateUser(entity
					, roleIds, getSystemUserCallback(), initPassword);
			userCacheProvider.updateCacheData(entity.getId(), updatedUser);
			systemLog(LOG_MSG_USEREDIT + entity.getName() + entity.getLoginId(), LogType.Edit);
		} catch(UserExistException e){
			this.ajaxOutPutText(e.getMessage());
		}
	}
	
	@RequestMapping("admin/user/delete")
	public void deleteUser(HttpServletResponse response) throws IOException{
		String[] ids = StringUtils.toArray(this.getParameter("ids"));
		if(ids == null)
			throw new NullPointerException("not found ids in deleteUser().");
		userManager.execDelete(ids, getSystemUserCallback());
		userCacheProvider.setUsersDelete(ids);
		systemLog(LOG_MSG_USERDEL + getUserLogins(ids), LogType.Delete);
		this.ajaxOutPutText(MSG_DELETE_OK);
	}
	
	@RequestMapping("admin/user/ease")
	public void easeUser(HttpServletResponse response) throws IOException{
		String[] ids = StringUtils.toArray(this.getParameter("ids"));
		if(ids == null)
			throw new NullPointerException("not found ids in easeUser().");
//		userManager.execDelete(ids, systemUserCallback);
		systemLog(LOG_MSG_USEREASE + getUserLogins(ids), LogType.Delete);
		userManager.execEase(ids);
		userCacheProvider.removeUsers(ids);
		this.ajaxOutPutText(MSG_EASE_OK);
	}
	
	@RequestMapping("admin/user/pass_reset")
	public void resetPass(String id, HttpServletResponse response) throws IOException{
		assert (StringUtils.isNotEmpty(id));
		/* 保存修改密码 */
		String resetPass = MyApplicationConfig.getDefaultResetPass();
		this.setUserPassword(id, resetPass);
//		String encodedPass = MyApplicationConfig.encodePassword(resetPass, this.getUser(id).getLoginId());
//		logger.debug("重置的密码---------》: " + encodedPass);
//		userManager.execEditPassword(id, encodedPass);
//		/* 更新缓存 */
//		userCacheProvider.getCacheData(id).setPassword(encodedPass);
		systemLog(new StringBuilder().append("用户密码被重置：").append(id).toString(), LogType.Edit);
		this.ajaxOutPutText(MSG_EASE_OK);
	}
	
	private String getUserLogins(String[] ids){
		StringBuilder sb = new StringBuilder("[");
		for(String id : ids){
			sb.append(userCacheProvider.getUserName(id));
			sb.append(",");
		}
		if(sb.toString().endsWith(","))
			sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append("]");
		return sb.toString();
	}
	
	private static final String NAME_PARENT_NAME = "parentName";
	private static final String NAME_PARENT_ID = "parentId";
	private static final String NAME_ROLE_IDS = "roleIds";
	private static final String NAME_USER_ID = "id";
	
	public static final String MSG_DELETE_OK = "用户成功删除";
	public static final String MSG_EASE_OK = "彻底删除用户成功";
}
