package com.walkersoft.system.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.MyApplicationContext;
import com.walkersoft.application.UserType;
import com.walkersoft.application.security.MyUserDetails;
import com.walkersoft.application.util.CodeUtils;
import com.walkersoft.system.SystemAction;
import com.walkersoft.system.entity.UserCoreEntity;
import com.walkersoft.system.manager.UserManagerImpl;
import com.walkersoft.system.pojo.FunctionGroup;
import com.walkersoft.system.pojo.FunctionObj;
import com.walkersoft.system.util.FunctionUtils;
import com.walkersoft.system.util.GroupInverseComparator;

@Controller
public class IndexAction extends SystemAction {

	private static final String NAME_SECURITY_SYSTEM = "sysList";
	private static final String NAME_SECURITY_LIST = "funcGroupLst";
	private static final String NAME_USERNAME = "username";
	private static final String NAME_ORGNAME = "orgname";
	private static final String NAME_IS_SUPERVISOR = "isSupervisor";
	
	private static final String PARAMETER_PAGEID = "pageId";
	private static final String PARAMETER_SYSTEM_ID = "sysId";
	
	/* 子系统集合排序：由小到大 */
	private static final GroupInverseComparator systemComparator = new GroupInverseComparator();
	
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request, Model model){
		List<FunctionGroup> list = getCurrentUserMenuGroups();
		
		/* 设置用户界面风格 */
		UserCoreEntity currentUser = this.getCurrentUser();
        String style = MyApplicationContext.STYLE_DEFAULT;
		
        this.userCacheProvider.getCacheData(currentUser.getId()).setStyle(style);
		logger.debug("更新了缓存中样式: " + style);
		// 更新session中样式字段
		request.getSession(false).setAttribute(MyApplicationContext.STYLE_SESSION_NAME, style);
		
		// 加载子系统数据
		List<FunctionObj> sysList = getSystemList(list);
		model.addAttribute(NAME_SECURITY_SYSTEM, sysList);
		
		logger.debug("加载了用户的权限菜单: " + list);
		if(sysList != null){
			model.addAttribute(NAME_SECURITY_LIST, getSubMenuList(sysList.get(0).getId(), list));
		}
		
		UserCoreEntity userDetails = getCurrentUser();
//		model.addAttribute(NAME_SECURITY_LIST, list);
		
		model.addAttribute(NAME_USERNAME, userDetails.getName());
		
		// 处理用户所在单位名字
		// 如果是供应商等外部用户，需要从'org_name'字段中取单位名字
		String orgName = null;
		if(userDetails.isFromOuter()){
			orgName = userDetails.getOrgName();
		} else
			orgName = this.getCurrentUserDeptName();
		model.addAttribute(NAME_ORGNAME, orgName);
		model.addAttribute(NAME_IS_SUPERVISOR, this.getCurrentUserType() == UserType.SuperVisor);
		systemLog("用户登录并进入首页");
		return "index2";
	}
	
	private static final String MENU_HTML_5 = "?fid=";
	
	@RequestMapping(value = "permit/menu")
	public void getLeftMenuHtml(HttpServletResponse response) throws IOException{
		String sysId = this.getParameter(PARAMETER_SYSTEM_ID);
		assert (StringUtils.isNotEmpty(sysId));
		
		List<FunctionGroup> subMenuList = null;
		
		if(sysId.equals(FunctionUtils.SUPERVISOR_SYS_ID)){
			// 超级管理员特有的tab标签
			subMenuList = FunctionUtils.getSupervisorGroups();
		} else
			subMenuList = getSubMenuList(sysId, getCurrentUserMenuGroups());
		if(subMenuList != null){
			StringBuilder html = new StringBuilder();
			for(FunctionGroup fg : subMenuList){
				html.append("<p class='menu_head'><img alt=\"\" src=\"");
				html.append(this.getContextPath());
				html.append("/images/menu_plus.png\"/>&nbsp;");
				html.append(fg.getName());
				html.append("</p>");
				html.append("<div class=\"menu_line\"></div>");
				html.append("<div class=\"menu_body\">");
				if(fg.getItemList() != null)
					for(FunctionObj fo : fg.getItemList()){
						html.append("<div class=\"menu_content\">");
						html.append("<a id=\"");
						html.append(fo.getId());
						html.append("\" href=\"####\" onclick=\"smenuOpen('");
						html.append(this.getContextPath());
						html.append(fo.getUrl());
						html.append(MENU_HTML_5);
						html.append(fo.getId());
						html.append("','");
						html.append(fo.getId());
						
						//添加菜单组、菜单项名称两个参数
						html.append("', '");
						html.append(fg.getName());
						html.append("', '");
						html.append(fo.getName());
						
						html.append("')\">");
						html.append("<label class=\"menu_tip\">▪</label>");
						html.append(fo.getName());
						html.append("</a></div>");
					}
				html.append("</div>");
			}
			this.ajaxOutPutText(html);
			return;
		} else
			this.ajaxOutPutText("");
	}
	
	/**
	 * 返回子系统对应的菜单组集合
	 * @param systemId 子系统ID
	 * @return
	 */
	private List<FunctionGroup> getSubMenuList(String systemId, List<FunctionGroup> list){
		assert (StringUtils.isNotEmpty(systemId));
		if(list == null) return null;
		List<FunctionGroup> result = new ArrayList<FunctionGroup>(8);
		for(FunctionGroup fg : list){
			if(fg.getParentId().equals(systemId)){
				result.add(fg);
			}
		}
		return result;
	}
	
	/**
	 * 返回用户可用的'子系统'集合
	 * @param groups
	 * @return
	 */
	private List<FunctionObj> getSystemList(List<FunctionGroup> groups){
		if(groups == null || groups.size() == 0) return null;
		List<String> systemIds = new ArrayList<String>();
		String systemId = null;
		for(FunctionGroup grp : groups){
			systemId = grp.getParentId();
			if(!systemIds.contains(systemId))
				systemIds.add(systemId);
		}
		//把存在的系统ID对象返回
		Map<String, FunctionObj> allSystems = functionCacheProvider.getAllSystem();
		List<FunctionObj> result = new ArrayList<FunctionObj>(systemIds.size());
		FunctionObj findFo = null;
		for(String sid : systemIds){
			findFo = allSystems.get(sid);
			if(findFo != null){
				result.add(findFo);
			} else
				logger.warn("加载子系统时，未找到缓存数据：" + sid);
		}
		
		// 创建默认首页 子系统
		FunctionObj home = new FunctionObj();
		home.setId("home");
		home.setF_type(FunctionObj.TYPE_SYSTEM);
		home.setName("首页");
		home.setOrderNum(-1);
		result.add(home);
		
		Collections.sort(result, systemComparator);
		return result;
	}
	
	@RequestMapping(value = "permit/help/index")
	public String help(Model model){
		String pageId = this.getParameter(PARAMETER_PAGEID);
		if(StringUtils.isEmpty(pageId)){
			pageId = "1";
		}
		if(pageId.equals("2")){
			addAttributes2(model);
		}
		return "help/guide_" + pageId;
	}
	
	private void addAttributes2(Model model){
		model.addAttribute(DEPARTMENT_JSON_SET, this.getAllDepartmentTreeForJson());
		model.addAttribute("codeSet", CodeUtils.getCodeTreeForJson("SYS_AREA"));
	}
	
	@RequestMapping(value = "permit/about")
	public String help(){
		return "help/about";
	}
	
	@RequestMapping(value = "permit/style")
	public String showStyle(){
		return BASE_URL + "style";
	}
	
	@RequestMapping(value = "permit/saveStyle")
	public String saveStyle(HttpServletRequest request){
		String style = this.getParameter(PARAMETER_STYLE);
		assert (StringUtils.isNotEmpty(style));
		UserCoreEntity currentUser = this.getCurrentUser();
		currentUser = userManager.execSaveUserStyle(currentUser.getId(), style);
		this.userCacheProvider.getCacheData(currentUser.getId()).setStyle(style);
		logger.debug("更新了缓存中样式: " + style);
		// 更新session中样式字段
		request.getSession(false).setAttribute(MyApplicationContext.STYLE_SESSION_NAME, style);
		return "go_home";
	}
	
	private static final String PARAMETER_STYLE = "style";
	
	@Autowired
	private UserManagerImpl userManager;
}