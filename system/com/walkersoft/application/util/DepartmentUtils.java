package com.walkersoft.application.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.walker.infrastructure.cache.SimpleCacheManager;
import com.walker.infrastructure.cache.tree.CacheTreeLoadCallback;
import com.walker.infrastructure.cache.tree.CacheTreeNode;
import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.StringUtils;
import com.walker.web.RequestAwareContext;
import com.walkersoft.application.MyApplicationConfig;
import com.walkersoft.application.OrgType;
import com.walkersoft.application.UserType;
import com.walkersoft.application.cache.DepartmentCacheProvider;
import com.walkersoft.application.cache.UserCoreCacheProvider;
import com.walkersoft.system.entity.DepartmentEntity;
import com.walkersoft.system.entity.UserCoreEntity;

/**
 * 组织机构工具类，通过组织机构信息的获取，包含常用各种方法。
 * @author shikeying
 * @date 2014-09-15
 *
 */
public class DepartmentUtils {

	protected static DepartmentCacheProvider departmentCacheTree = (DepartmentCacheProvider)SimpleCacheManager.getCacheTreeProvider(DepartmentEntity.class);
	protected static UserCoreCacheProvider userCoreCacheProvider = (UserCoreCacheProvider)SimpleCacheManager.getCacheProvider(UserCoreEntity.class);
	
	public static final String NAME_ROOT = "组织机构";
	
	public static SelectOrgCallback selectOrgCallback = new SelectOrgCallback();
	
	private DepartmentUtils(){}
	
	/**
	 * 得到系统当前登录用户基本信息
	 * @return
	 */
	public static UserCoreEntity getCurrentUser(){
		return MyApplicationConfig.getCurrentUser();
	}
	
	/**
	 * 返回用户类型
	 * @return 枚举类型
	 */
	public static UserType getCurrentUserType(){
		UserCoreEntity u = MyApplicationConfig.getCurrentUser();
		if(u.isSupervisor())
			return UserType.SuperVisor;
		else if(u.isAdmin())
			return UserType.Administrator;
		else
			return UserType.User;
	}
	
	/**
	 * 返回系统所有用户，包括：内部和外部用户。</p>
	 * 该方法通常作为测试方法是用，其他模块应当按照某种业务条件来获得用户，<br>
	 * 否则在数据量较大时，会对系统内存和web页面造成影响。
	 * @return
	 * @author shikeying
	 * @date 2015-1-12
	 */
	public static List<UserCoreEntity> getAllUserList(){
		return userCoreCacheProvider.getAllUsers();
	}
	
	/**
	 * 返回用户对象
	 * @param userId 用户ID
	 * @return
	 */
	public static UserCoreEntity getUser(String userId){
		return userCoreCacheProvider.getCacheData(userId);
	}
	
	/**
	 * 返回用户名字
	 * @param userId 用户ID
	 * @return
	 */
	public static String getUserName(String userId){
		return getUser(userId).getName();
	}
	
	/**
	 * 根据机构ID，返回机构类型枚举值
	 * @param deptId 机构ID
	 * @return
	 */
	public static OrgType getOrgType(String deptId){
		DepartmentEntity department = departmentCacheTree.getCacheData(deptId);
		if(department.getType() == DepartmentEntity.TYPE_ORG){
			return OrgType.Org;
		} else
			return OrgType.Dept;
	}
	
	/**
	 * 返回当前登录用户所属'单位'名称
	 * @return
	 */
	public static String getCurrentUserDeptName(){
		UserCoreEntity user = MyApplicationConfig.getCurrentUser();
		String orgId = user.getOrgId();
		if(StringUtils.isNotEmpty(orgId)){
			return departmentCacheTree.getDepartmentName(orgId);
		}
		return null;
	}
	
	/**
	 * 根据ID返回机构名称，如果未找到会抛出异常。
	 * @param orgId
	 * @return
	 * @throws NullPointerException
	 */
	public static final String getOrgName(String orgId){
		Assert.hasText(orgId);
		return departmentCacheTree.getDepartmentName(orgId);
	}
	
	/**
	 * 根据输入用户ID，返回用户所在部门名称。
	 * @param userId 用户ID
	 * @return
	 */
	public static String getUserDeptName(String userId){
		UserCoreEntity user = userCoreCacheProvider.getCacheData(userId);
		if(user == null)
			throw new IllegalArgumentException("缓存中未找到用户: " + userId);
		if(StringUtils.isNotEmpty(user.getDepartmentId())){
			return departmentCacheTree.getDepartmentName(user.getDepartmentId());
		}
		return null;
	}
	
	/**
	 * 返回用户所在单位名称
	 * @param userId 用户ID
	 * @return
	 */
	public static String getUserOrgName(String userId){
		UserCoreEntity user = userCoreCacheProvider.getCacheData(userId);
		if(user == null)
			throw new IllegalArgumentException("缓存中未找到用户: " + userId);
		return user.getOrgName();
	}
	
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
	public static String getAllDepartmentTreeForJson(List<String> existIds
			, CacheTreeLoadCallback callback, boolean showOuter){
		List<CacheTreeNode> deptList = departmentCacheTree.getAvailableRootList();
		JSONArray array = new JSONArray();
		
		UserType userType = getCurrentUserType();
		
		// 创建一个虚拟的根节点，因为多单位情况下，是没有根节点的
		// 注意：这句话主要是获得web程序名称，因为ztree自定义图标使用了绝对路径。
		aquirePostIcon();
		
		array.add(CodeUtils.createJsonTree(CodeUtils.ZTREE_FIRST_PARENT, NAME_ROOT, "0"
				, false, true, "1_open.png"));
		
		if(userType == UserType.SuperVisor){
			if(deptList != null && deptList.size() > 0){
				for(CacheTreeNode node : deptList){
					CodeUtils.recurseTree(node, array, existIds, callback);
				}
			}
			if(showOuter){
				/* 超级管理员可以看到自定义的“外部机构”节点，如：供应商等 */
				array.add(CodeUtils.createJsonTree(CodeUtils.ZTREE_FROM_OUTTER
						, CodeUtils.ZTREE_FROM_OUTTER_NAME, CodeUtils.ZTREE_FIRST_PARENT
						, false, false, null));
			}
			
		} else if(userType == UserType.Administrator){
			CacheTreeNode userOrg = departmentCacheTree.getOneRootNode(MyApplicationConfig.getCurrentUser().getOrgId());
			CodeUtils.recurseTree(userOrg, array, existIds, callback);
		} else {
			// 普通用户不能管理机构
		}
		return array.toString();
	}
	
	public static String getAllDepartmentTreeForJson(){
		return getAllDepartmentTreeForJson(null, null, false);
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// 隐藏岗位的树展示方法
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private static WithoutPostCallback withoutPostCallback = new WithoutPostCallback();
	
	/**
	 * 返回不包含岗位的机构所有节点信息
	 * @return
	 */
	public static String getAllDepartmentTreeWithoutPostForJson(){
		return getAllDepartmentTreeForJson(null, withoutPostCallback, false);
	}
	
	public static class WithoutPostCallback implements CacheTreeLoadCallback{
		@Override
		public CacheTreeNode decide(CacheTreeNode node) {
			if(node != null){
				Object source = node.getSource();
				if(source != null){
					DepartmentEntity entity = (DepartmentEntity)source;
					if(entity.isPost())
						return null;
				} else
					throw new NullPointerException("source not found.");
			}
			return node;
		}
	}
	
	/**
	 * 返回所有机构树数据，格式为JSON，提供给<code>ztree</code>组件使用。
	 * @param callback
	 * @param showOuter 是否显示外部机构节点
	 * @return
	 */
	public static String getAllDepartmentTreeForJson(CacheTreeLoadCallback callback, boolean showOuter){
		return getAllDepartmentTreeForJson(null, callback, showOuter);
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// 返回岗位树的相关方法
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * 返回某个部门下，所有岗位树，数据格式为JSON。<br>
	 * ztree组件使用
	 * @param departId 部门ID
	 * @param existIds 已经存在的岗位ID
	 * @param callback 树节点数据的过滤回调实现
	 * @return
	 */
	public static String getPostTreeForJson(String departId
			, List<String> existIds, CacheTreeLoadCallback callback){
		// 获得该部门下的机构树对象
		CacheTreeNode deptCacheTree = departmentCacheTree.get(departId);
		JSONArray array = new JSONArray();
		
		// 创建一个虚拟的岗位根节点
		if(deptCacheTree != null){
			array.add(CodeUtils.createJsonTree(CodeUtils.ZTREE_FIRST_PARENT, deptCacheTree.getText(), "0", false, true, "ztree/img/diy/3.png"));
			CodeUtils.recurseTree(deptCacheTree, array, existIds, callback);
		} else {
			array.add(CodeUtils.createJsonTree(CodeUtils.ZTREE_FIRST_PARENT, NAME_ROOT, "0", false, true, "ztree/img/diy/3.png"));
		}
		
		return array.toString();
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~ 岗位树结束 ~~~~~~~~~~~~~~~~~~~~~~~
	
	
	/**
	 * 返回当前登录用户所在单位下机构对象节点信息，包括：子节点信息。
	 * @return
	 */
	public static CacheTreeNode getOneRootNode(){
		return departmentCacheTree.getOneRootNode(getCurrentUser().getOrgId());
	}
	
	/**
	 * 根据单位“自定义属性”来查找匹配的单位集合。
	 * @param attr 自定义属性名称
	 * @return
	 */
	public static final List<DepartmentEntity> getOrgListByAttr(String attr){
		if(StringUtils.isEmpty(attr)){
			return null;
		}
		List<CacheTreeNode> allOrgs = departmentCacheTree.getAvailableRootList();
		if(StringUtils.isEmptyList(allOrgs)) return null;
		
		List<DepartmentEntity> result = new ArrayList<DepartmentEntity>();
		
		DepartmentEntity entity = null;
		for(CacheTreeNode n : allOrgs){
			entity = (DepartmentEntity)n.getSource();
			if(StringUtils.isNotEmpty(entity.getAttribute()) && entity.getAttribute().equalsIgnoreCase(attr)){
				result.add(entity);
			}
		}
		return result;
	}
	
	/**
	 * 只选择机构（没有部门）的回调实现
	 * @author shikeying
	 *
	 */
	public static class SelectOrgCallback implements CacheTreeLoadCallback{
		@Override
		public CacheTreeNode decide(CacheTreeNode node) {
			if(node != null){
				Object source = node.getSource();
				if(source != null){
					DepartmentEntity entity = (DepartmentEntity)source;
					if(!entity.isOrg() || entity.getStatus() == 1)
						return null;
				} else
					throw new NullPointerException("source not found.");
			}
			return node;
		}
	}
	
	private static String ICON_POST = null;
	
	public static String getICON_POST() {
		return ICON_POST;
	}

	private static final void aquirePostIcon(){
		if(ICON_POST == null){
			StringBuilder sb = new StringBuilder();
			String contextPath = RequestAwareContext.getCurrentRequest().getContextPath();
			if(StringUtils.isNotEmpty(contextPath)){
				sb.append(contextPath).append("/");
			} else
				sb.append("/");
//			sb.append("script/lib/ztree/img/diy/3.png");
			sb.append("script/lib/ztree/img/diy/");
			ICON_POST = sb.toString();
		}
	}
}
