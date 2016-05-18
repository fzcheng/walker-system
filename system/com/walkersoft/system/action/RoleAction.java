package com.walkersoft.system.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.db.page.ListPageContext;
import com.walker.db.page.PagerView;
import com.walker.db.page.support.GenericPager;
import com.walker.infrastructure.cache.SimpleCacheManager;
import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.MyApplicationConfig;
import com.walkersoft.application.cache.FunctionCacheProvider;
import com.walkersoft.application.cache.UserCoreCacheProvider;
import com.walkersoft.application.log.MyLogDetail.LogType;
import com.walkersoft.application.security.event.RoleSecurityChangeEvent;
import com.walkersoft.system.SystemAction;
import com.walkersoft.system.entity.RoleEntity;
import com.walkersoft.system.entity.UserCoreEntity;
import com.walkersoft.system.manager.FunctionManagerImpl;
import com.walkersoft.system.manager.RoleManagerImpl;
import com.walkersoft.system.pojo.FunctionGroup;
import com.walkersoft.system.pojo.FunctionObj;
import com.walkersoft.system.pojo.FunctionPointer;
import com.walkersoft.system.pojo.RoleFuncObj;
import com.walkersoft.system.pojo.SysOperator;
import com.walkersoft.system.pojo.UserGroup;

@Controller
public class RoleAction extends SystemAction {

	//设置角色用户时，每行显示用户数量
	private int userCountPerRow = 8;
		
	@Autowired
	private FunctionManagerImpl functionManager;
	
	@Autowired
	private RoleManagerImpl roleManager;
	
	private FunctionCacheProvider functionCache = (FunctionCacheProvider)SimpleCacheManager.getCacheProvider(FunctionObj.class);
	private UserCoreCacheProvider userCache = (UserCoreCacheProvider)SimpleCacheManager.getCacheProvider(UserCoreEntity.class);
	
	private static final String LOG_MSG_CREATEROLE = "创建角色";
	private static final String LOG_MSG_ROLEFUNCTION = "改变角色功能";
	private static final String LOG_MSG_ROLEUSER = "改变角色用户";
	
	private static final String ROLE_INDEX = "role";
	private static final String ROLE_LIST = "role_list";
	
	@RequestMapping(value = "admin/role/index")
	public String index(Model model){
		setUserPointers(model);
		loadList(model);
		return BASE_URL + ROLE_INDEX;
	}
	
	@RequestMapping(value = "permit/admin/role/reload")
	public String reloadPage(Model model){
		loadList(model);
		return BASE_URL + ROLE_LIST;
	}
	
	private void loadList(Model model){
		// 通过此方法来设置列表想显示每页多少条记录
//		ListPageContext.setCurrentPageSize(5);
		GenericPager<RoleEntity> pager = roleManager.querySplitRoleList();
		PagerView<RoleEntity> pagerView = ListPageContext.createPagerView(pager, DEFAULT_JS_NAME);
		model.addAttribute(DEFAULT_PAGER_VIEW_NAME, pagerView);
	}
	
	
	@RequestMapping(value = "permit/admin/role/add")
	public String showAddRole(){
		return BASE_URL + "role_add"; 
	}
	
	@RequestMapping(value = "permit/admin/role/edit")
	public String showEditRole(){
		return BASE_URL + "role_edit"; 
	}
	
	
	/**
	 * 创建并保存新角色
	 * @throws Exception
	 */
	@RequestMapping("admin/role/createRole")
	public void createRole(HttpServletResponse response) throws Exception{
		String roleName = this.getParameter("roleName");
		String summary = this.getParameter("summary");
		assert (StringUtils.isNotEmpty(roleName));
		int result = roleManager.execInsertRole(roleName, summary);
		systemLog(LOG_MSG_CREATEROLE + roleName, LogType.Add);
		this.ajaxOutPutText(String.valueOf(result));
	}
	
	/**
	 * 保存角色设置的功能
	 */
	@RequestMapping("admin/role/saveRoleFunc")
	public void saveRoleFunc(HttpServletResponse response) throws IOException{
		String roleId = this.getParameter("roleId");
		String funcIds = this.getParameter("funcIds");
		
		if(roleId == null || roleId.equals("")){
			this.ajaxOutPutText("<span>系统错误：角色ID不存在！请先选择一个角色。</span>");
		}
		if(funcIds != null && funcIds.startsWith(roleId + ",")){
			// 第一个参数是roleID，所以要截取掉。
			funcIds = funcIds.substring(roleId.length()+1);
		}
		
		List<String> itemLst = new ArrayList<String>();
		List<String> pointLst = new ArrayList<String>();
		
		String[] splitIds = funcIds.split(",");
		for(String _id : splitIds){
			if(_id.startsWith("ITEM_")){
				//功能项ID
				itemLst.add(_id.replace("ITEM_", ""));
			} else {
				//功能点ID
				pointLst.add(_id);
			}
		}
		
		// key = functionId, value = pointerIds(p1,p2,...)
		Map<String, String> funcIdsMap = new HashMap<String, String>();
		
		for(String _itemId : itemLst){
			StringBuffer points = new StringBuffer();
			for(String _pointId : pointLst){
				if(_pointId.indexOf(_itemId) >= 0){
					points.append(_pointId.replace(_itemId + "_", ""));
					points.append(",");
				}
			}
			if(points.length() > 0 && points.toString().endsWith(",")){
				points.deleteCharAt(points.lastIndexOf(","));
			}
			funcIdsMap.put(_itemId, points.toString());
		}
		try {
			functionManager.execSaveRoleFunc(roleId, funcIdsMap);
			systemLog(LOG_MSG_ROLEFUNCTION + funcIdsMap.toString(), LogType.Edit);
			
			/* 更新资源加载器中的缓存数据，让权限拦截实施生效，需要使用spring的事件通知 */
			MyApplicationConfig.publishEvent(new RoleSecurityChangeEvent(roleId));
			
		} catch (Exception e) {
			this.ajaxOutPutText("<span>系统错误：保存数据时，数据库操作失败！</span>");
			e.printStackTrace();
			return;
		}
		
		this.ajaxOutPutText("<span>角色功能保存成功！</span>");
	}
	
	/**
	 * 返回设置角色功能的HTML内容
	 */
	@RequestMapping("permit/admin/role/getRoleFuncHtml")
	public void getRoleFuncHtml(HttpServletResponse response){
		StringBuffer showEditRightsHtml = new StringBuffer();
		try {
			//该角色包含的功能权限
			String roleId = this.getParameter("roleId");
			if(roleId == null || roleId.equals("")){
				this.ajaxOutPutText("加载角色权限时，角色ID不存在！");
				return;
			}
			
			//把传入的角色ID保存到隐藏域中
			showEditRightsHtml.append("<input type='hidden' id='roleId' name='roleId' value='" + roleId + "'/>");
			
			Map<String, RoleFuncObj> roleFuncMap = functionManager.queryRoleFuncList(roleId);
			
			// 1-原始菜单
			List<FunctionGroup> funcGroupLst = functionCache.getFunctionGroupList();
			
			/* 把带URL地址的子系统也当作菜单组，让用户可以分权限*/
			List<FunctionObj> systemWithUrlList = functionCache.getSystemWithUrlList();
			// 2-带URL的子系统菜单
			List<FunctionGroup> systemGroupList = null;
			if(!StringUtils.isEmptyList(systemWithUrlList)){
				systemGroupList = new ArrayList<FunctionGroup>(systemWithUrlList.size());
				FunctionGroup _fg = null;
				for(FunctionObj _fo : systemWithUrlList){
					_fg = new FunctionGroup();
					_fg.setId(_fo.getId());
					_fg.setName(_fo.getName());
					_fg.setParentId("-1");
					_fg.setOrderNum(_fo.getOrderNum());
					_fg.setUrl(true);
					systemGroupList.add(_fg);
				}
			}
			
			if(StringUtils.isEmptyList(funcGroupLst) && StringUtils.isEmptyList(systemGroupList)){
				showEditRightsHtml.append("<span>没有数据</span>");
			} else {
				showEditRightsHtml.append("<table border='0' cellpadding='0' cellspacing='0' class='table-form'>");
				if(!StringUtils.isEmptyList(funcGroupLst)){
					for(FunctionGroup _fg : funcGroupLst){
						// 不允许显示的
						if(!_fg.isVisible()) continue;
						
						//输出菜单组
						showEditRightsHtml.append(this.outputSingleFunc(0, _fg.getId(), _fg.getName(), null, false, null, null, false));
						
						//输出该组的菜单项
						List<FunctionObj> foList = _fg.getItemList();
						if(foList != null && foList.size() > 0){
							for(FunctionObj _fo : foList){
								if(roleFuncMap.get(_fo.getId()) != null){
									showEditRightsHtml.append(this.outputSingleFunc(1, _fo.getId(), _fo.getName()
											, _fg.getId(), true, _fo.getPointerList(), roleFuncMap.get(_fo.getId()), false));
								} else
									showEditRightsHtml.append(this.outputSingleFunc(1, _fo.getId(), _fo.getName()
											, _fg.getId(), false, _fo.getPointerList(), null, false));
							}
						}
						showEditRightsHtml.append("");
					}
				}
				
				if(!StringUtils.isEmptyList(systemGroupList)){
					for(FunctionGroup _fg : systemGroupList){
						if(_fg.hasUrl()){
							if(roleFuncMap.get(_fg.getId()) != null)
								showEditRightsHtml.append(this.outputSingleFunc(0, _fg.getId(), _fg.getName(), null, true, null, null, true));
							else
								showEditRightsHtml.append(this.outputSingleFunc(0, _fg.getId(), _fg.getName(), null, false, null, null, true));
								
						} else 
							throw new IllegalArgumentException("该子系统带有URL属性，但没有:" + _fg.getName());
					}
				}
				showEditRightsHtml.append("</table>");
			}
			
			this.ajaxOutPutText(showEditRightsHtml.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * 生成一个功能的checkbox HTML代码
	 * @param ftype：类型，0_功能组，1_功能项，2_功能点
	 * @param fid
	 * @param fname
	 * @param groupId 菜单组ID
	 * @param checked:标示菜单项是否选择，true选择
	 * @param pointerLst 功能点集合
	 * @param rfo
	 * @param allowedCheck 是否允许选择
	 * @return
	 */
	private String outputSingleFunc(int ftype, String fid, String fname
			, String groupId, boolean checked
			, List<FunctionPointer> pointerLst
			, RoleFuncObj rfo, boolean allowedCheck){
		StringBuffer _s = new StringBuffer();
		String showId = null;
		
		_s.append("<tr");
		if(ftype == 0){
			if(allowedCheck){
				// 允许选择，说明是带URL的子系统，ID使用和菜单项一致
				showId = "ITEM_" + fid;
			} else {
				showId = "GROUP_" + fid;
			}
			_s.append(" class='title'");
			
		} else if(ftype == 1){
			showId = "ITEM_" + fid;
		}
		_s.append("><td>");
		_s.append("<label style='width:160px'><input type='checkbox' id='");
		_s.append(showId);
		_s.append("' name='");
		_s.append(showId);
		_s.append("' value='");
		_s.append(showId + "'");
		
		if(groupId != null){
			_s.append(" class='");
			_s.append(groupId);
			_s.append("'");
		}
		//给功能组添加选择事件：全选
		if(ftype == 0){
			//临时让功能组不能选择，还需要调试
			if(!allowedCheck){
				_s.append(" disabled='true'");
			}
			
//			_s.append(" onclick='");
//			_s.append("if($(\"#" + showId + "\").attr(\"checked\")){");
//			_s.append("$(\"." + fid + "\").attr(\"checked\", true);");
//			_s.append("} else {");
//			_s.append("$(\"." + fid + "\").attr(\"checked\", false);");
//			_s.append("}'");
		} else if(ftype == 1){
			//给功能项绑定选择事件：点击让功能点可选
//			_s.append(" onclick='");
//			_s.append("$(\"." + fid + "\").attr(\"disabled\", !$(\"#" + showId + "\").attr(\"checked\"));");
//			_s.append("'");
		}
		if(checked){
			_s.append(" checked='true'");
		}
		_s.append(">");
		_s.append(fname);
		_s.append("</label>");
		
		//功能点输出
		if(pointerLst != null){
			for(FunctionPointer _p : pointerLst){
				String _showId = "POINT_" + _p.getId();
				_s.append("<label><input type='checkbox' id='");
				_s.append(_showId);
				_s.append("' name='");
				_s.append(_showId);
				_s.append("' value='");
				//功能点的ID为：功能项ID + "_" + 功能点ID
				_s.append(fid + "_" + _p.getId() + "'");
				
				//使功能点的class值与“功能ID”一致
				_s.append(" class='");
				_s.append(fid);
				_s.append("'");
				
				if(rfo != null && rfo.isExistPointer(_p.getId())){
					_s.append(" checked='true'");
				}
//				if(!checked){
//					_s.append(" disabled='true'");
//				}
				_s.append(">");
				_s.append(_p.getName());
				_s.append("</label>");
			}
		}
		_s.append("</td></tr>");
		
		return _s.toString();
	}
	
	/**
	 * 保存角色选择的用户
	 * @throws IOException
	 */
	@RequestMapping("admin/role/saveRoleUser")
	public void saveRoleUser(HttpServletResponse response) throws IOException{
		String roleId = this.getParameter("roleId");
		String userIds = this.getParameter("userIds");
		
		if(roleId == null || roleId.equals("")){
			this.ajaxOutPutText("<span>系统错误：角色ID不存在！请先选择一个角色。</span>");
		}
		if(userIds != null && userIds.startsWith(roleId + ",")){
//			userIds = userIds.replace(roleId + ",", "");
			userIds = userIds.substring(roleId.length()+1);
		}
		List<String> userList = new ArrayList<String>();
		
		String[] splitIds = userIds.split(",");
		for(String _id : splitIds){
			userList.add(_id);
		}
		try {
			functionManager.execSaveUserByRoleId(roleId, userList);
			systemLog(LOG_MSG_ROLEUSER + userList.toString(), LogType.Edit);
		} catch (Exception e) {
			this.ajaxOutPutText("<span>系统错误：保存数据时，数据库操作失败！</span>");
			e.printStackTrace();
			return;
		}
		this.ajaxOutPutText("<span>角色用户保存成功！</span>");
	}
	
	/**
	 * 返回角色用户生成的HTML信息
	 * @throws IOException
	 */
	@RequestMapping("permit/admin/role/getRoleUserHtml")
	public void getRoleUserHtml(HttpServletResponse response) throws IOException{
		
		StringBuffer showEditUsrHtml = new StringBuffer();
		
		//该角色包含的用户
		String roleId = this.getParameter("roleId");
		if(roleId == null || roleId.equals("")){
			this.ajaxOutPutText("加载角色权限时，角色ID不存在！");
			return;
		}
		
		//该角色包含的用户
		List<String> roleUsrList = null;
		try {
			roleUsrList = functionManager.queryRoleUserList(roleId);
		} catch (Exception e) {
			e.printStackTrace();
			this.ajaxOutPutText("<span>数据库操作失败！</span>");
		}
		
		//把传入的角色ID保存到隐藏域中
		showEditUsrHtml.append("<input type='hidden' id='roleId' name='roleId' value='" + roleId + "'/>");
		
		//返回系统所有用户
		
		Map<String, UserGroup> allUsers = userCache.getUsersMenuGroup();
		if(allUsers != null && allUsers.size() > 0){
			showEditUsrHtml.append("<table border='0' cellpadding='0' cellspacing='0' width='600' class='table-form'>");
			for(Map.Entry<String, UserGroup> entry : allUsers.entrySet()){
				showEditUsrHtml.append("<tr class='title'><td>");
				showEditUsrHtml.append("<label>");
				showEditUsrHtml.append(entry.getKey());
				showEditUsrHtml.append("</label>");
				showEditUsrHtml.append("</td></tr>");
				
				int counter = 0;
				for(SysOperator _user : entry.getValue().getUserList()){
					String userId = _user.getUserId();
					if(counter%userCountPerRow == 0){
						//补上上一个的单元格
						if(showEditUsrHtml.toString().endsWith("</label>")){
							showEditUsrHtml.append("</td></tr>");
						}
						//说明该换行了
						showEditUsrHtml.append("<tr><td>");
					}
					showEditUsrHtml.append("<label style='width:70px'><input type='checkbox' id='");
					showEditUsrHtml.append(userId);
					showEditUsrHtml.append("' name='");
					showEditUsrHtml.append(userId);
					showEditUsrHtml.append("' value='");
					showEditUsrHtml.append(userId + "'");
					
					if(roleUsrList != null && roleUsrList.contains(userId)){
						showEditUsrHtml.append(" checked='true'");
					}
					showEditUsrHtml.append(">");
					showEditUsrHtml.append(_user.getUserName());
					showEditUsrHtml.append("</label>");
					counter ++;
				}
				showEditUsrHtml.append("</td></tr>");
			}
			showEditUsrHtml.append("</table>");
		} else {
			showEditUsrHtml.append("<span>没有数据</span>");
		}
		this.ajaxOutPutText(showEditUsrHtml.toString());
	}
	
	@RequestMapping("admin/role/remove")
	public void deleteRole(String roleId, HttpServletResponse response) throws IOException{
		Assert.hasText(roleId);
		String tip = roleManager.execRemoveRole(roleId);
		if(tip != null){
			this.ajaxOutPutText(tip);
		} else {
			this.ajaxOutPutText(MESSAGE_SUCCESS);
		}
	}
}
