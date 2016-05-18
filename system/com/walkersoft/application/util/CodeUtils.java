package com.walkersoft.application.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.walker.app.ApplicationRuntimeException;
import com.walker.infrastructure.cache.SimpleCacheManager;
import com.walker.infrastructure.cache.tree.AbstractTreeGenerator;
import com.walker.infrastructure.cache.tree.CacheTreeLoadCallback;
import com.walker.infrastructure.cache.tree.CacheTreeNode;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.cache.CodeCacheProvider;
import com.walkersoft.system.entity.CodeEntity;

/**
 * 系统代码以及树操作的各种方法，工具类。</p>
 * 该对象中方法只能在运行时被调用，不能在启动时使用。
 * @author shikeying
 * @date 2014-8-29
 *
 */
public class CodeUtils {

	private static CodeCacheProvider codeCacheTree = (CodeCacheProvider)SimpleCacheManager.getCacheTreeProvider(CodeEntity.class);
	
	private  CodeUtils(){}
	
	/**
	 * 返回树结构数据，json格式。</p>
	 * 数据由自定义的<code>AbstractTreeGenerator</code>对象产生，用户可自定义该抽象类。</p>
	 * 该方法可把普通的树节点数据，生成为树对象，并转换为json格式，由<code>ztree</code>组件使用。
	 * @param generator
	 * @return
	 */
	public static <T> String getObjectTreeForJson(AbstractTreeGenerator<T> generator){
		if(generator == null) return null;
		CacheTreeNode root = generator.getTreeRoot();
//		if(root == null)
//			throw new IllegalArgumentException("not found root in AbstractTreeGenerator: " + generator.getClass().getName());
		JSONArray array = new JSONArray();
		if(root != null){
			root.setOpen(true);
			recurseTree(root, array, null, null);
		}
		return array.toString();
	}
	
	/**
	 * 返回一个代码表树结构数据，格式为JSON字符串。
	 * @param key 代码表ID
	 * @param existIds 已经存在的节点ID集合
	 * @param callback 过滤数据的回调接口实现
	 * @return
	 */
	public static String getCodeTreeForJson(String key, List<String> existIds
			, CacheTreeLoadCallback callback){
		CacheTreeNode node = codeCacheTree.getOneRootNode(key);
		JSONArray array = new JSONArray();
		if(node != null){
			recurseTree(node, array, existIds, callback);
		}
		return array.toString();
	}
	
	/**
	 * 返回一个代码表树结构数据，格式为JSON字符串。
	 * @param key 代码表ID
	 * @return
	 */
	public static String getCodeTreeForJson(String key){
		return getCodeTreeForJson(key, null, null);
	}
	
	/**
	 * 返回代码名称，如果没有查询到就返回空字符串。
	 * @param codeId
	 * @return
	 */
	public static String getCodeNameAny(String codeId){
		if(StringUtils.isEmpty(codeId)) return null;
		String name = getCodeName(codeId);
		if(StringUtils.isEmpty(name)){
			return StringUtils.EMPTY_STRING;
		}
		return name;
	}
	
	/**
	 * 返回代码名称
	 * @param codeId 代码ID
	 * @return
	 */
	public static String getCodeName(String codeId){
		return codeCacheTree.get(codeId).getText();
	}
	
	public static CodeEntity getCodeObject(String codeId){
		return codeCacheTree.getCacheData(codeId);
	}
	
	/**
	 * 返回给定代码表的子代码项，树结构
	 * @param codeTableName 代码表ID
	 * @return
	 */
	public static List<CacheTreeNode> getCodeChildrenList(String codeTableName){
		return codeCacheTree.getCodeChildrenList(codeTableName);
	}
	
	/**
	 * 返回多个代码名称
	 * @param codeIds 代码ID数组
	 * @return 返回名字数组
	 */
	public static String[] getCodeNames(String[] codeIds){
		if(codeIds == null || codeIds.length == 0)
			return null;
		int count = codeIds.length;
		String[] names = new String[count];
		for(int i=0; i<count; i++){
			names[i] = getCodeName(codeIds[i]);
		}
		return names;
	}
	
	/**
	 * 返回代码集合对象，通常在数据库中可能会存储多个代码ID，可以通过此 方法获取代码集合。
	 * @param codeIds 输入多个代码ID数组
	 * @return
	 */
	public static List<CacheTreeNode> getCodeList(String[] codeIds){
		return codeCacheTree.getCodeList(codeIds);
	}
	
	/**
	 * 返回列表形式的代码数据，通常在界面中，很多代码都是一级可以使用html<code>select</code>组件展示。</p>
	 * 如果返回树形代码选择，请使用<code>getCodeTreeForJson</code>方法，配合ztree展示。
	 * @param key
	 * @return 仅返回一级代码集合
	 */
	public static Map<String, String> getCodeListMap(String key){
		assert (StringUtils.isNotEmpty(key));
		CacheTreeNode node = codeCacheTree.searchTreeNode(key);
		if(node == null){
			throw new ApplicationRuntimeException("code table not found: " + key);
		}
		Map<String, String> result = new TreeMap<String, String>();
		if(node.hasChild()){
			for(CacheTreeNode n : node.getChildren()){
//				if(n.hasChild()){
//					throw new ApplicationRuntimeException("发现存在树形代码结构，不能生成代码列表!");
//				}
				result.put(n.getKey(), n.getText());
			}
		}
		return result;
	}
	
	/**
	 * 递归把当前给定节点下的所有子节点加入到列表中，包括当前节点。
	 * @param node
	 * @param array
	 */
	public static void recurseTree(CacheTreeNode node, JSONArray array
			, List<String> existIds, CacheTreeLoadCallback callback){
		// 如果存在当前值，就选中节点
		boolean checked = false;
		if(existIds != null && existIds.contains(node.getKey())){
			checked = true;
		}
		if(callback == null || 
				(callback != null && callback.decide(node) != null)){
			array.add(createJson2TreeNode(node, checked, false));
		}
		Collection<CacheTreeNode> children = node.getChildren();
		if(children != null){
			for(CacheTreeNode child : children){
				recurseTree(child, array, existIds, callback);
			}
		}
	}
	
	public static JSONObject createJson2TreeNode(CacheTreeNode node, boolean checked, boolean opened){
		String id = node.getKey();
		String name = node.getText();
		String pid = node.getParentId();
		assert (StringUtils.isNotEmpty(id));
		assert (StringUtils.isNotEmpty(name));
		assert (StringUtils.isNotEmpty(pid));
		
		if(pid.equals("0")){
			pid = ZTREE_FIRST_PARENT;
		}
		if(node.isOpen()){
			opened = true;
		}
		return createJsonTree(id, name, pid, checked, opened, node.getIcon());
	}
	
	public static JSONObject createJsonTree(String id, String name, String pid, boolean checked, boolean opened, String icon){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(ZTREE_ID, id);
		jsonObj.put(ZTREE_NAME, name);
		jsonObj.put(ZTREE_PID, pid);
		if(checked){
			jsonObj.put(ZTREE_CHECKED, true);
		}
		if(opened){
			jsonObj.put(ZTREE_OPENED, true);
		}
		if(StringUtils.isNotEmpty(icon)){
			jsonObj.put("icon", DepartmentUtils.getICON_POST() + icon);
		}
		return jsonObj;
	}
	
	public static final String ZTREE_ID = "id";
	public static final String ZTREE_NAME = "name";
	public static final String ZTREE_PID = "pid";
	public static final String ZTREE_FIRST_PARENT = "root";
	public static final String ZTREE_CHECKED = "checked";
	public static final String ZTREE_OPENED = "open";
	public static final String ZTREE_FROM_OUTTER = "from_outer";
	public static final String ZTREE_FROM_OUTTER_NAME = "[外部机构]";
}
