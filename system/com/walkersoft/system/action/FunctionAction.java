package com.walkersoft.system.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.app.ApplicationRuntimeException;
import com.walker.db.page.ListPageContext;
import com.walker.db.page.PagerView;
import com.walker.db.page.support.GenericPager;
import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.system.SystemAction;
import com.walkersoft.system.manager.FunctionManagerImpl;
import com.walkersoft.system.pojo.FunctionObj;
import com.walkersoft.system.pojo.FunctionPointer;

/**
 * 菜单功能管理
 * @author shikeying
 *
 */
@Controller
public class FunctionAction extends SystemAction {

	private static final String URL_LIST_BTN = "system/pointer_manage";
	private static final String URL_ADD_POINTER = "system/pointer_add";
	
	private static final String ATTR_SYSGROUP_JSON = "sysGroupJson";
	private static final String ATTR_FUNCTION_ID = "functionId";
	
	private static final String PARAMETER_PID = "pid";
	
	@Autowired
	private FunctionManagerImpl functionManager;
	
	@RequestMapping(value = "admin/function/index")
	public String index(Model model){
		setUserPointers(model);
		model.addAttribute(ATTR_SYSGROUP_JSON, getSystemGroupJson());
		loadList(model, (String)null);
		return BASE_URL + "fun_manage";
	}
	
	@RequestMapping(value = "permit/admin/function/reload")
	public String reloadPage(Model model){
		String parentId = this.getParameter(PARAMETER_PID);
		loadList(model, parentId);
		return BASE_URL + "fun_list";
	}
	
	private void loadList(Model model, String parentId){
		// 通过此方法来设置列表想显示每页多少条记录
//		ListPageContext.setCurrentPageSize(5);
		String _page = this.getParameter(PARAMETER_PAGE);
		if(StringUtils.isNotEmpty(_page)){
			// 存在当前页，就直接到此页
			ListPageContext.setCurrentPageIndex(Integer.parseInt(_page));
		}
		GenericPager<FunctionObj> pager = functionManager.queryPageFuncionList(parentId);
		PagerView<FunctionObj> pagerView = ListPageContext.createPagerView(pager, DEFAULT_JS_NAME);
		model.addAttribute(DEFAULT_PAGER_VIEW_NAME, pagerView);
	}
	
	@RequestMapping(value = "admin/function/show_add")
	public String showAddPage(Model model){
		model.addAttribute(ATTR_SYSGROUP_JSON, getSystemGroupJson());
		return BASE_URL + "function_add";
	}
	
	@RequestMapping(value = "permit/admin/function/save")
	public void saveFunction(@ModelAttribute("entity") FunctionObj entity
			, HttpServletResponse response) throws IOException{
		Assert.notNull(entity);
		Assert.hasText(entity.getId());
		//不允许“带URL”的子系统下面，存放任何菜单节点
		FunctionObj parent = functionCacheProvider.getCacheData(entity.getParentId());
		if(parent != null 
				&& parent.getType() == FunctionObj.TYPE_SYSTEM 
				&& !parent.isCheckChildrenOnRemove()){
			this.ajaxOutPutText("菜单'" + parent.getName() + "'下面不允许挂载任何子节点，因为其带有URL，可被打开!");
			return;
		}
		boolean res = functionManager.execSaveFunction(entity);
		if(res){
			// 更新缓存
			functionCacheProvider.putFunction(entity);
			this.ajaxOutPutText(MESSAGE_SUCCESS);
		} else
			this.ajaxOutPutText("功能ID已经存在，请输入新的ID");
	}
	
	@RequestMapping(value = "admin/function/remove")
	public void removeFunction(String id, HttpServletResponse response) throws IOException{
		assert (StringUtils.isNotEmpty(id));
		FunctionObj exist = functionCacheProvider.getCacheData(id);
		if(exist == null) throw new ApplicationRuntimeException("未找到此功能对象: " + id);
		logger.debug("......... id= " + id + ", type = " + exist.getType());
		if(exist.getType() == FunctionObj.TYPE_ITEM){
			functionManager.execDeleteFunction(id);
			functionCacheProvider.removeFunction(id);
		} else if(exist.getType() == FunctionObj.TYPE_GROUP){
			if(functionCacheProvider.existFunctionInGroup(id)){
				this.ajaxOutPutText("功能组下面存在功能项，无法删除该组");
				return;
			} else {
				functionManager.execDeleteFunction(id);
				functionCacheProvider.removeGroup(id);
			}
		} else if(exist.getType() == FunctionObj.TYPE_SYSTEM){
			if(exist.isCheckChildrenOnRemove() && functionCacheProvider.existGroupInSystem(id)){
				this.ajaxOutPutText("子系统下面存在功能组，无法删除该子系统");
				return;
//				if(functionCacheProvider.existGroupInSystem(id)){
//					this.ajaxOutPutText("子系统下面存在功能组，无法删除该子系统");
//					return;
//				} else {
//					functionManager.execDeleteFunction(id);
//					functionCacheProvider.removeSystem(id);
//				}
			} else {
				functionManager.execDeleteFunction(id);
				functionCacheProvider.removeSystem(id);
			}
		}
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	@RequestMapping(value = "permit/admin/function/list_button")
	public String showButtonList(String id, Model model){
		Assert.hasText(id);
		// 上个页面中列表的 当前页信息
		String _page = this.getParameter("_page");
		List<FunctionPointer> pointers = functionManager.queryPointerList(id);
		model.addAttribute(ATTR_FUNCTION_ID, id);
		model.addAttribute(PARAMETER_PAGE, _page);
		model.addAttribute("pointers", pointers);
		return URL_LIST_BTN;
	}
	
	@RequestMapping(value = "permit/admin/function/show_pointer")
	public String showAddPointer(String functionId, Model model){
		Assert.hasText(functionId);
		model.addAttribute(ATTR_FUNCTION_ID, functionId);
		model.addAttribute("functionName", this.functionCacheProvider.getFunctionName(functionId));
		return URL_ADD_POINTER;
	}
	
	@RequestMapping(value = "permit/admin/function/save_pointer")
	public void savePointer(@ModelAttribute("entity") FunctionPointer entity
			, HttpServletResponse response) throws IOException{
		logger.debug("........... " + entity);
		Assert.notNull(entity);
		Assert.hasText(entity.getId());
		if(functionManager.queryFunctionPointer(entity.getId()) != null){
			this.ajaxOutPutText("该按钮定义已经存在，请使用一个唯一的ID");
			return;
		}
		String existPointers = functionCacheProvider.getCacheData(entity.getFunctionId()).getPointer();
		String _pointers = functionManager.execSavePointer(entity, existPointers);
		this.functionCacheProvider.putFunctionPointer(entity, _pointers);
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	@RequestMapping(value = "permit/admin/function/del_pointer")
	public void removePointer(String pointerId, String functionId
			, HttpServletResponse response) throws IOException{
		Assert.hasText(pointerId);
		Assert.hasText(functionId);
		String existPointers = functionCacheProvider.getCacheData(functionId).getPointer();
		String _pointers = functionManager.execDeletePointer(pointerId, existPointers, functionId);
		functionCacheProvider.removeFunctionPointer(pointerId, functionId, _pointers);
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	@RequestMapping(value = "admin/function/show_edit")
	public String showEditPage(Model model, String id){
		Assert.hasText(id);
		FunctionObj functionObj = functionManager.querySingle(id);
		model.addAttribute("type", functionObj.getType());
		model.addAttribute("functionObj", functionObj);
		model.addAttribute(ATTR_SYSGROUP_JSON, getSystemGroupJson());
		
		// 设置上级ID（子系统和分组ID）
		int type = functionObj.getType();
		if(type == FunctionObj.TYPE_GROUP){
			model.addAttribute("parentSystem", functionObj.getParentId());
		} else if(type == FunctionObj.TYPE_ITEM){
			model.addAttribute("parentSystem", functionCacheProvider.getParentSystemId(functionObj));
			model.addAttribute("parentGroup", functionObj.getParentId());
		}
		return BASE_URL + "function_edit";
	}
	
	@RequestMapping(value = "permit/admin/function/edit")
	public void saveEditFunction(@ModelAttribute("entity") FunctionObj entity
			, HttpServletResponse response) throws IOException{
		Assert.notNull(entity);
		Assert.hasText(entity.getId());
		String id = entity.getId();
		int type = entity.getType();
		if(type == FunctionObj.TYPE_SYSTEM){
			// nothing...
		} else if(type == FunctionObj.TYPE_GROUP){
			if(StringUtils.isEmpty(entity.getParentId())){
				this.ajaxOutPutText("更新菜单组错误，缺少parentId: " + id);
				return;
			}
		} else {
			if(StringUtils.isEmpty(entity.getParentId()) 
					|| StringUtils.isEmpty(entity.getUrl())){
				this.ajaxOutPutText("更新菜单组错误，缺少parentId或者url: " + id);
				return;
			}
		}
		functionManager.execUpdateFunction(entity);
		
		//直接更新缓存
		functionCacheProvider.updateFunctionInfo(entity);
		
//		FunctionObj cacheData = functionCacheProvider.getCacheData(id);
//		if(cacheData == null){
//			throw new IllegalStateException("缓存中未找到编辑的菜单: " + id);
//		}
//		cacheData.setOrderNum(entity.getOrderNum());
//		cacheData.setName(entity.getName());
//		cacheData.setRun(entity.getRun());
//		if(type == FunctionObj.TYPE_SYSTEM){
//			cacheData.setUrl(entity.getUrl());
//			cacheData.setOpenStyle(entity.getOpenStyle());
//		} else if(type == FunctionObj.TYPE_GROUP){
//			cacheData.setParentId(entity.getParentId());
//		} else if(type == FunctionObj.TYPE_ITEM){
//			cacheData.setUrl(entity.getUrl());
//			cacheData.setOpenStyle(entity.getOpenStyle());
//			cacheData.setParentId(entity.getParentId());
//		}
//		functionCacheProvider.updateCacheData(id, cacheData);
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
}
