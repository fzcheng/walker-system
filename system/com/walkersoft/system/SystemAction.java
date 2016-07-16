package com.walkersoft.system;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.walker.app.ApplicationRuntimeException;
import com.walker.db.page.ListPageContext;
import com.walker.db.page.PagerView;
import com.walker.db.page.support.GenericPager;
import com.walker.file.FileEngine;
import com.walker.file.FileEngine.StoreType;
import com.walker.file.FileMeta;
import com.walker.file.FileOperateException;
import com.walker.infrastructure.arguments.ArgumentsManager;
import com.walker.infrastructure.arguments.Variable;
import com.walker.infrastructure.arguments.VariableType;
import com.walker.infrastructure.cache.tree.CacheTreeLoadCallback;
import com.walker.infrastructure.cache.tree.CacheTreeNode;
import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.DateUtils;
import com.walker.infrastructure.utils.LongDateHelper;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.MyApplicationConfig;
import com.walkersoft.application.MyArgumentsNames;
import com.walkersoft.application.OrgType;
import com.walkersoft.application.SpringControllerSupport;
import com.walkersoft.application.UserType;
import com.walkersoft.application.cache.DepartmentCacheProvider;
import com.walkersoft.application.cache.FunctionCacheProvider;
import com.walkersoft.application.cache.UserCoreCacheProvider;
import com.walkersoft.application.security.MyUserDetails;
import com.walkersoft.application.util.CodeUtils;
import com.walkersoft.application.util.DepartmentUtils;
import com.walkersoft.system.callback.SystemUserCallback;
import com.walkersoft.system.entity.UserCoreEntity;
import com.walkersoft.system.manager.UserManagerImpl;
import com.walkersoft.system.pojo.FunctionGroup;
import com.walkersoft.system.util.FunctionUtils;

public class SystemAction extends SpringControllerSupport {

	private static final String MESSAGE_TITLE = "_title";
	private static final String MESSAGE_BODY = "_message";
	
	public static final String BASE_URL = "system/";
	
	protected static final String URL_ERROR = "system/error";
	
	protected static final String PARAMETER_PAGE = "_page";
	protected static final String CURRENT_PAGE = "currentPage";
	
//	protected DepartmentCacheProvider departmentCacheTree = (DepartmentCacheProvider)SimpleCacheManager.getCacheTreeProvider(DepartmentEntity.class);
//	protected UserCoreCacheProvider userCacheProvider = (UserCoreCacheProvider)SimpleCacheManager.getCacheProvider(UserCoreEntity.class);
//	protected FunctionCacheProvider functionCacheProvider = (FunctionCacheProvider)SimpleCacheManager.getCacheProvider(FunctionObj.class);
	
	@Autowired
	protected DepartmentCacheProvider departmentCacheProvider;
	@Autowired
	protected UserCoreCacheProvider userCacheProvider;
	@Autowired
	protected FunctionCacheProvider functionCacheProvider;
	
	
	@Autowired
	protected ArgumentsManager argumentManager;
	
	@Autowired
	protected UserManagerImpl userManager;
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// 以下这些都是DHTML组件需要使用的方法
	/**
	 * 设置Ajax列表组件的分页信息。
	 * @return 返回当前记录的索引值
	 */
	protected int setAjaxListPage(){
		int iposStart = 0;
		String posStart = this.getParameter("posStart");
		if(posStart != null && !posStart.equals("")){
			iposStart = Integer.parseInt(posStart);
		}
		ListPageContext.setCurrentPageIndex(iposStart / ListPageContext.getCurrentPageSize() + 1);
		return iposStart;
	}
	
	/**
	 * 把树形节点对象转换成<code>JSON</code>格式，此方法针对<code>DHTML-TREE</code>组件。
	 * @param treeNode
	 * @param treeChild
	 */
	protected void toJsonTree(CacheTreeNode treeNode, JSONArray treeChild
			, CacheTreeLoadCallback callback){
		if(treeNode != null){
			JSONObject jo = new JSONObject();
			jo.put("id", treeNode.getKey());
			jo.put("text", treeNode.getText());
			jo.put("userdata","[{name:'parentId',content:'"+treeNode.getParentId()+"'}]");
			if(treeNode.hasChild()){
				jo.put("child", "1");
				JSONArray children = new JSONArray();
				
				// 排序
				List<CacheTreeNode> list = new ArrayList<CacheTreeNode>();
				for(CacheTreeNode node : treeNode.getChildren()){
					if(callback == null || 
							(callback != null && callback.decide(node) != null)){
						list.add(node);
					}
				}
				Collections.sort(list);
				
				for(CacheTreeNode node : list){
					toJsonTree(node, children, callback);
				}
				jo.put("item", children);
			}
			treeChild.add(jo);
		}
	}
	
	/**
	 * 根据用户管理员类型，加载机构树的根节点</p>
	 * <li>超级管理员可以加载所有单位节点</li>
	 * <li>本单位管理员只能加载自己单位节点</li></p>
	 * 注意：该方法是专门为前端组件DHX设计的，其他的不能用。
	 * 
	 * @param rootList 加载的根节点列表，因为根节点列表存在'已删除'和'未删除'机构区别，所以由业务来提供。
	 * @return
	 */
	protected JSONObject getDepartmentTreeForJson(Collection<CacheTreeNode> rootList
			, CacheTreeLoadCallback callback){
		JSONObject treeRoot = new JSONObject();
		treeRoot.put("id", "0");
		JSONArray rootItem = new JSONArray();
		
		UserType userType = this.getCurrentUserType();
		
		if(rootList == null || rootList.size() == 0){
			treeRoot.put("item", rootItem);
			return treeRoot;
		}
		
		if(userType == UserType.SuperVisor){
			if(rootList != null){
				for(CacheTreeNode node : rootList){
					toJsonTree(node, rootItem, callback);
				}
			}
			
		} else if(userType == UserType.Administrator){
//			CacheTreeNode userOrg = departmentCacheTree.getOneRootNode(getCurrentUser().getOrgId());
			CacheTreeNode userOrg = DepartmentUtils.getOneRootNode();
			toJsonTree(userOrg, rootItem, callback);
			
		} else {
			// 普通用户不能管理机构
		}
		treeRoot.put("item", rootItem);
		return treeRoot;
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * 根据机构ID，返回机构类型枚举值
	 * @param deptId 机构ID
	 * @return
	 */
	protected OrgType getOrgType(String deptId){
		return DepartmentUtils.getOrgType(deptId);
//		DepartmentEntity department = departmentCacheTree.getCacheData(deptId);
//		if(department.getType() == DepartmentEntity.TYPE_ORG){
//			return OrgType.Org;
//		} else
//			return OrgType.Dept;
	}
	
	/**
	 * 业务可以使用此方法获得可变的参数选项，同时需要调用方强制转换类型。</br>
	 * <li>对于性能有特别要求的，可以明确定义参数方法，例如：一个参数添加一个方法。</li>
	 * <li>通常应该明确每个参数方法，这样能保证在编译器知道增加或删除了哪些方法。</li>
	 * 调用如下：
	 * <pre>
	 * int pageSize = (Integer)this.getArgumentsValue(MyArgumentsNames.SystemPageSize);
	 * </pre>
	 * @param name 参数ID的枚举类型，通常添加一个新参数，就需要增加定义。
	 * @return
	 */
	protected Object getArgumentsValue(MyArgumentsNames name){
		assert (name != null);
		Variable var = argumentManager.getVariable(name.getName());
		VariableType type = var.getType();
		if(type == VariableType.String){
			return var.getStringValue();
		} else if(type == VariableType.Boolean){
			return var.getBooleanValue();
		} else if(type == VariableType.Integer){
			return var.getIntegerValue();
		} else if(type == VariableType.Float){
			return var.getFloatValue();
		} else
			throw new IllegalArgumentException("unknown type: " + type);
	}
	
	/**
	 * 组织机构JSON格式数据引用名称
	 */
	protected static final String DEPARTMENT_JSON_SET = "departSet";
	
	/**
	 * 返回组织机构所有对象数据，数据格式为JSON。<br>
	 * 这些数据提供给界面中的树组件。</p>
	 * 方法从缓存一次性加载了所有机构数据，不适合异步的批量加载。<br>
	 * 注意：返回的数据由前端<code>ztree</code>对象使用。
	 * @param existIds 已经存在的节点ID集合,在设置节点选择时需要此参数
	 * @callback 树节点加载时的回调接口，可以在加载时自定义加载方式，如：不加载删除节点等。
	 * @param showOuter 是否显示外部结构节点，不显示设置<code>false</code>
	 * @return
	 */
	protected String getAllDepartmentTreeForJson(List<String> existIds
			, CacheTreeLoadCallback callback, boolean showOuter){
		return DepartmentUtils.getAllDepartmentTreeForJson(existIds, callback, showOuter);
	}
	
	protected String getAllDepartmentTreeForJson(){
		return DepartmentUtils.getAllDepartmentTreeForJson(null, null, false);
	}
	
	/**
	 * 返回所有机构树数据，格式为JSON，提供给<code>ztree</code>组件使用。
	 * @param callback
	 * @param showOuter 是否显示外部机构节点
	 * @return
	 */
	protected String getAllDepartmentTreeForJson(CacheTreeLoadCallback callback, boolean showOuter){
//		return getAllDepartmentTreeForJson(null, callback, showOuter);
		return DepartmentUtils.getAllDepartmentTreeForJson(null, callback, showOuter);
	}
	
	/**
	 * 返回不包含岗位的机构所有节点信息
	 * @return
	 */
	protected String getAllDepartmentTreeWithoutPostForJson(){
		return DepartmentUtils.getAllDepartmentTreeWithoutPostForJson();
	}
	
	/**
	 * 返回当前登录用户所属'单位'名称
	 * @return
	 */
	protected String getCurrentUserDeptName(){
		return DepartmentUtils.getCurrentUserDeptName();
	}

	/**
	 * 返回当前登录用户可用的所有菜单组集合
	 * @return
	 */
	protected List<FunctionGroup> getCurrentUserMenuGroups(){
		MyUserDetails userDetails = getCurrentUserDetails();
		List<FunctionGroup> list = null;
		if(getCurrentUserType() == UserType.SuperVisor){
			list = new ArrayList<FunctionGroup>(functionCacheProvider.getFunctionGroupList());
//			list = functionCacheProvider.getFunctionGroupList();
			
			// 加上带URL的子系统菜单（已通过转换变成菜单组）
			/* 加上可直接打开的子系统，即：能直接点击的子系统菜单，下面不包含其他（二三级）菜单 */
			/* 这里添加虚拟的菜单组，为了符合代码一致性，因为之前系统是根据菜单组来显示子系统菜单的 */
			List<String> systemWithUrlList = functionCacheProvider.getSystemWithUrl();
			FunctionUtils.addSystemToDestFuncGroup(systemWithUrlList, list);
		} else {
			list = userDetails.getUserFuncGroup();
		}
		
		if(list != null){
			Collections.sort(list);
		}
		return list;
	}
	
	/**
	 * 返回系统、功能组二级联动菜单需要的JSON格式数据。<br>
	 * 该方法针对cxSelect.js组件来配合使用。
	 * @return
	 */
	protected String getSystemGroupJson(){
		return functionCacheProvider.getSystemGroupJson(false);
	}
	/**
	 * 返回用户对象
	 * @param id 用户ID
	 * @return
	 */
	protected UserCoreEntity getUser(String id){
		return userCacheProvider.getCacheData(id);
	}
	
	/**
	 * 返回初始化加密后的密码内容(如果系统设置为'不加密'，则返回明文)
	 * @param loginId 登录ID，系统使用其作为盐值混淆密码
	 * @return
	 */
	protected String getEncodeInitPassword(String loginId){
		return MyApplicationConfig.encodePassword(MyApplicationConfig.getInitPassword(), loginId);
	}
	
	/**
	 * 加密给定的密码
	 * @param password 密码明文
	 * @param loginId 盐值（用户登陆ID）
	 * @return
	 */
	protected String getEncodePassword(String password, String loginId){
		Assert.hasText(password);
		return MyApplicationConfig.encodePassword(password, loginId);
	}
	
	/**
	 * 获取用户管理的回调接口，如果系统配置了就返回该对象，如果没有返回<code>null</code>。</p>
	 * 如果存在说明有外部系统集成进来，如：CMS
	 * @return
	 */
	protected SystemUserCallback getSystemUserCallback(){
		return MyApplicationConfig.getSystemUserCallback();
	}
	
	protected static final String PARAMETER_TODAY = "today";
	protected static final String PARAMETER_FIRSTDAY_MONTH = "firstDayOfMonth";
	
	/**
	 * 设置页面中默认的时间段查询条件
	 * @param model
	 */
	protected void setDefaultSearchCondition(Model model){
		model.addAttribute(PARAMETER_TODAY, DateUtils.getTodayForHuman());
		model.addAttribute(PARAMETER_FIRSTDAY_MONTH, LongDateHelper.getFirstDayOfMonth());
	}
	
	/**
	 * 设置用户密码
	 * @param userId 用户ID
	 * @param newPassword 新密码，明文
	 * @return 返回加密后的密码，如果系统配置不需要加密，则返回newPassword
	 */
	protected String setUserPassword(String userId, String newPassword){
		UserCoreEntity user = userCacheProvider.getCacheData(userId);
		if(user == null){
			// 也可以在此重新查询数据库，获取user信息
			throw new ApplicationRuntimeException("user not found in cache, user: " + userId);
		}
		String encodedPass = MyApplicationConfig.encodePassword(newPassword, user.getLoginId());
		userManager.execEditPassword(userId, encodedPass);
		/* 更新缓存 */
		user.setPassword(encodedPass);
		return encodedPass;
	}
	
	/**
	 * 返回业务错误提示页面。
	 * @param model
	 * @param title 提示标题
	 * @param message 提示内容
	 */
	protected void setErrorMessage(Model model, String title, String message){
		model.addAttribute(MESSAGE_TITLE, StringUtils.isEmpty(title)? "系统提示：" : title);
		model.addAttribute(MESSAGE_BODY, message);
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// 处理附件上传操作
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@Autowired
	protected FileEngine fileEngine;
	
	private static final String FORM_DATA_NAME = "multipart/form-data";
	
	/**
	 * 存在附件上传的页面可以调用该方法实现获取表单中的多个文件。</p>
	 * 该方法仅支持表单中的一个"file"域。
	 * @param fileFormId 表单中file域ID
	 * @param st 附件保存方式：文件或数据库
	 * @return 返回生成的附件元数据集合
	 * @throws IOException
	 */
	protected List<FileMeta> getUploadFiles(String fileFormId, StoreType st) throws IOException{
		assert (StringUtils.isNotEmpty(fileFormId));
		HttpServletRequest request = this.getRequest();
		logger.debug("+++++++++++" + request.getContentType());
		if(request.getContentType().indexOf(FORM_DATA_NAME) >= 0){
			logger.debug("存在附件..............");
			List<FileMeta> files = null;
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
			try {
				files = fileEngine.readFiles(multiRequest, fileFormId, st);
			} catch (FileOperateException e) {
				throw new IOException(e);
			}
			return files;
		} else {
			logger.debug("没有附件-----------");
			return null;
		}
	}
	
	private static final int BUFFER_SIZE = 2048 * 4;
	public static final String PARAMETER_ID = "id";
	public static final String CHARSET_ISO = "ISO-8859-1";
	
	private InputStream getInputStream(FileMeta fileMeta) throws FileNotFoundException{
		InputStream inputStream = null;
		if(fileMeta.getStoreType() == StoreType.Database){
        	inputStream = new BufferedInputStream(new ByteArrayInputStream(fileMeta.getContent()));
        } else {
        	File file = fileEngine.getFileObject(fileMeta.getPath());
        	inputStream = new BufferedInputStream(new FileInputStream(file));
        }
		return inputStream;
	}
	
	private FileMeta getFileMeta(String id){
		FileMeta fileMeta = fileEngine.getFile(id);
		if(fileMeta == null){
			throw new ApplicationRuntimeException("file not found: " + id);
		}
		return fileMeta;
	}
	
	private void doWriteFileStream(InputStream inputStream, OutputStream out) throws IOException{
//		out = response.getOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];  
        int b = inputStream.read(buffer);
        while (b != -1){  
            //4.写到输出流(out)中  
            out.write(buffer,0,b);
            b = inputStream.read(buffer);  
        }
	}
	
	/**
	 * 把文件内容写入到浏览器客户端。</br>
	 * 给定文件ID作为参数，系统会找出文件内容并读取内容直接给<code>HttpServletResponse</code>对象。
	 */
	protected void writeFileToBrownser(HttpServletResponse response){
		String id = this.getParameter(PARAMETER_ID);
		assert (StringUtils.isNotEmpty(id));
		FileMeta fileMeta = this.getFileMeta(id);
		
//		HttpServletResponse response = this.getResponse();
		
		//1.设置文件ContentType类型，这样设置，会自动判断下载文件类型  
        response.setContentType("multipart/form-data");  
        
        //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)  
        try {
			response.setHeader("Content-Disposition", "attachment;fileName="+new String(fileMeta.getFilename().getBytes(), CHARSET_ISO));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			logger.error("", e1);
			throw new ApplicationRuntimeException(null, e1);
		}
        
        InputStream inputStream = null;
        OutputStream out = null;
        
        try{
        	inputStream = getInputStream(fileMeta);
        	out = response.getOutputStream();
//	        if(fileMeta.getStoreType() == StoreType.Database){
//	        	inputStream = new BufferedInputStream(new ByteArrayInputStream(fileMeta.getContent()));
//	        } else {
//	        	File file = fileEngine.getFileObject(fileMeta.getPath());
//	        	inputStream = new BufferedInputStream(new FileInputStream(file));
//	        }
//	        response.addHeader("Content-Length", String.valueOf(fileSize));
	        
        	this.doWriteFileStream(inputStream, out);
//	        out = response.getOutputStream();
//	        byte[] buffer = new byte[BUFFER_SIZE];  
//	        int b = inputStream.read(buffer);
//	        while (b != -1){  
//	            //4.写到输出流(out)中  
//	            out.write(buffer,0,b);
//	            b = inputStream.read(buffer);  
//	        }
        } catch(IOException ex){
        	logger.error("", ex);
        	throw new ApplicationRuntimeException();
        } finally {
        	try {
				inputStream.close();
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        }
	}
	
	/**
	 * 根据请求图片的ID，返回图片资源内容（字节流）
	 */
	protected void ajaxOutputImage(){
		String fileId = this.getParameter(PARAMETER_ID);
		ajaxOutputImage(fileId);
	}
	
	/**
	 * 根据请求图片的ID，返回图片资源内容（字节流）
	 * @param id
	 */
	protected void ajaxOutputImage(String id){
//		String id = this.getParameter(PARAMETER_ID);
		Assert.hasText(id);
//		logger.debug("异步请求图片: " + id);
		FileMeta fileMeta = this.getFileMeta(id);
		
		HttpServletResponse response = this.getResponse();
		response.setCharacterEncoding(StringUtils.DEFAULT_CHARSET_UTF8);
		response.setContentType(fileMeta.getContentType());
		
		InputStream inputStream = null;
        OutputStream out = null;
        
        try{
        	inputStream = getInputStream(fileMeta);
        } catch(FileNotFoundException ex){
        	inputStream = this.getClass().getClassLoader().getResourceAsStream("none2.jpg");
        	response.setContentType(IMAGE_JPEG);
        }
        try {
			out = response.getOutputStream();
			this.doWriteFileStream(inputStream, out);
			out.flush();
		} catch (IOException e) {
			logger.error("", e);
        	throw new ApplicationRuntimeException();
		} finally {
        	try {
				inputStream.close();
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
        }
	}
	
	protected String getParameterIdValue(){
		String id = this.getParameter(PARAMETER_ID);
		assert (StringUtils.isNotEmpty(id));
		return id;
	}
	
	/**
	 * 返回ajax成功标志，同时带有当前页，结果为拼接的字符串。
	 * @param currentPageName 当前页参数的名字，可以不提供，默认为：currentPage
	 * @return
	 */
	protected String getAjaxSuccessAndCurrentPage(String currentPageName){
		if(StringUtils.isEmpty(currentPageName))
			currentPageName = "currentPage";
		String currentPage = this.getParameter(currentPageName);
		StringBuilder result = new StringBuilder();
		result.append(MESSAGE_SUCCESS); // 成功标志
		result.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
		result.append(StringUtils.isEmpty(currentPage)? "1" : currentPage); // 当前页
		return result.toString();
	}
	
	/**
	 * 加载界面中的分页数据列表方法。</br>
	 * 通过泛型方式，根据业务需要自动转换POJO类型。
	 * @param model spring model对象
	 * @param pager 分页对象
	 */
	protected <T> void loadList(Model model, GenericPager<T> pager){
		if(pager == null){
			PagerView<T> pagerView = new PagerView<T>(null);
			model.addAttribute(DEFAULT_PAGER_VIEW_NAME, pagerView);
		} else {
			PagerView<T> pagerView = ListPageContext.createPagerView(pager, DEFAULT_JS_NAME);
			model.addAttribute(DEFAULT_PAGER_VIEW_NAME, pagerView);
		}
	}
	
	/**
	 * 加载界面中的分页数据列表方法，并带有当前页信息。</br>
	 * @param model
	 * @param pager
	 */
	protected <T> void loadListWithCurrentPage(Model model, GenericPager<T> pager){
		String currentPage = this.getParameter(CURRENT_PAGE);
		if(StringUtils.isEmpty(currentPage)){
//			throw new IllegalArgumentException("not found:currentPage!");
			logger.warn("not found:currentPage!");
		} else
			ListPageContext.setCurrentPageIndex(Integer.parseInt(currentPage));
		loadList(model, pager);
	}
	
	/**
	 * 想界面中设置代码树的数据结构，对于树形(ztree)的代码选择组件，通过此方法设置数据。</p>
	 * 该方法只允许用户设置一个已经选择的值，能显示在界面选择组件中，页面中使用的属性名称已经被定义。<br>
	 * 如：
	 * <pre>
	 * 1、“专业”的代码表名称是“profession”，那么在界面中能直接引用三个值：
	 * 1.1、professionTree，用来获得代码树的json数据格式；
	 * 1.2、profession，用来获得已经选择的当前代码ID；
	 * 1.3、professionName，用来获得已经选择的当前代码名称。
	 * @param model
	 * @param codeTableName 代码表名称
	 * @param selected 需要修改的代码ID
	 */
	protected void setCodeTreeJsonToModel(Model model, String codeTableName, String selected){
		List<String> existList = null;
		if(StringUtils.isNotEmpty(selected)){
			existList = new ArrayList<String>(1);
			existList.add(selected);
		}
		setCodeTreeJsonToModel(model, codeTableName, existList);
	}
	
	/**
	 * 想界面中设置代码树的数据结构，对于树形(ztree)的代码选择组件，通过此方法设置数据。</p>
	 * 该方法允许用户设置多个已经选择的值，能显示在界面选择组件中，页面中使用的属性名称已经被定义。<br>
	 * 如：
	 * <pre>
	 * 1、“专业”的代码表名称是“profession”，那么在界面中能直接引用三个值：
	 * 1.1、professionTree，用来获得代码树的json数据格式；
	 * 1.2、profession，用来获得已经选择的当前代码ID；
	 * 1.3、professionName，用来获得已经选择的当前代码名称。
	 * </pre>
	 * @param model
	 * @param codeTableName 代码表名称
	 * @param selected 需要修改的代码ID集合
	 */
	protected void setCodeTreeJsonToModel(Model model, String codeTableName, List<String> selected){
		String selectId = null;
		String selectName = null;
		if(!StringUtils.isEmptyList(selected)){
			int size = selected.size();
			if(size == 1){
				selectId = selected.get(0);
				selectName = CodeUtils.getCodeName(selectId);
			} else if(size > 1){
				StringBuilder sb1 = new StringBuilder();
				StringBuilder sb2 = new StringBuilder();
				for(int i=0; i<size; i++){
					if(i > 0){
						sb1.append(",");
						sb2.append(",");
					}
					sb1.append(selected.get(i));
					sb2.append(CodeUtils.getCodeName(selected.get(i)));
				}
				selectId = sb1.toString();
				selectName = sb2.toString();
			}
			model.addAttribute(codeTableName, selectId);
			model.addAttribute(codeTableName+"Name", selectName);
		}
		String json = CodeUtils.getCodeTreeForJson(codeTableName, selected, null);
		model.addAttribute(codeTableName+"Tree", json);
	}
}
