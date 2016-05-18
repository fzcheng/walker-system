package com.walkersoft.system.manager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.db.page.support.GenericPager;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.system.dao.FunctionDao;
import com.walkersoft.system.pojo.FunctionObj;
import com.walkersoft.system.pojo.FunctionPointer;
import com.walkersoft.system.pojo.RoleFuncObj;

@Service("functionManager")
public class FunctionManagerImpl {

	@Autowired
	private FunctionDao functionDao;
	
	public GenericPager<FunctionObj> queryPageFuncionList(String parentId){
		return functionDao.getPageFunctionList(parentId);
	}
	
	/**
	 * 返回用户所能使用的菜单列表信息，存在重复的。
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> queryFuncListByUser(String userId) throws Exception{
		return functionDao.getFuncListByUser(userId);
	}
	
	/**
	 * 返回该角色对应的所有用户ID
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List<String> queryRoleUserList(String roleId) throws Exception{
		return functionDao.getRoleUserList(roleId);
	}
	
	/**
	 * 返回指定角色所包含的功能Map
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public Map<String, RoleFuncObj> queryRoleFuncList(String roleId) throws Exception{
		return functionDao.getRoleFuncList(roleId);
	}
	
	public List<FunctionPointer> queryPointerList(String functionId){
		return functionDao.getPointerList(functionId);
	}
	
	/**
	 * 根据功能点ID查询对象
	 * @param id 功能点ID
	 * @return
	 */
	public FunctionPointer queryFunctionPointer(String id){
		return functionDao.getFunctionPointer(id);
	}
	
	public FunctionObj querySingle(String id){
		return functionDao.getFunction(id);
	}
	
	/**
	 * 保存角色设置的功能，先删除老的再添加新的
	 * @param roleId
	 * @param funcIdsMap
	 * @throws Exception
	 */
	public void execSaveRoleFunc(String roleId
			, Map<String, String> funcIdsMap) throws Exception{
		functionDao.deleteFuncByRoleId(roleId);
		functionDao.insertFuncByRoleId(roleId, funcIdsMap);
	}
	
	/**
	 * 保存角色选择用户
	 * @param roleId
	 * @param userIds
	 * @throws Exception
	 */
	public void execSaveUserByRoleId(String roleId
			, List<String> userIds){
		functionDao.deleteUserByRoleId(roleId);
		functionDao.insertUserByRoleId(roleId, userIds);
	}
	
	/**
	 * 保存一个菜单功能，如果存在ID重复的返回<code>false</code>
	 * @param functionObj
	 * @return
	 */
	public boolean execSaveFunction(FunctionObj functionObj){
		if(functionDao.searchFunction(functionObj.getId()) != null){
			return false;
		}
		functionDao.insertFunction(functionObj);
		return true;
	}
	
	/**
	 * 更新菜单单个数据，注意：只更新基本属性，不更新菜单关系和类型。
	 * @param functionObj
	 */
	public void execUpdateFunction(FunctionObj functionObj){
		int type = functionObj.getType();
		if(type == FunctionObj.TYPE_SYSTEM){
			functionDao.updateFunctionSystem(functionObj);
		} else if(type == FunctionObj.TYPE_GROUP){
			functionDao.updateFunctionGroup(functionObj);
		} else {
			functionDao.updateFunctionItem(functionObj);
		}
	}
	
	public void execDeleteFunction(String id){
		functionDao.deleteFunction(id);
		functionDao.deleteRoleFunc(id);
	}
	
	/**
	 * 保存新添加的功能点
	 * @param fp
	 * @param existPointers
	 * @return 返回当前菜单中，更新的功能点信息字符串
	 */
	public String execSavePointer(FunctionPointer fp, String existPointers){
		functionDao.insertFunctionPointer(fp);
		String _pointers = null;
		if(StringUtils.isEmpty(existPointers)){
			_pointers = fp.getId();
		} else {
			_pointers = existPointers + "," + fp.getId();
		}
		functionDao.updateFunctionPointers(_pointers, fp.getFunctionId());
		return _pointers;
	}
	
	/**
	 * 删除一个功能点定义。
	 * @param id 功能点ID
	 * @param existPointers 已存在的当前功能中功能点信息
	 * @param functionId 功能ID
	 */
	public String execDeletePointer(String id, String existPointers, String functionId){
		functionDao.deletePointer(id);
		if(StringUtils.isNotEmpty(existPointers)){
			StringBuilder result = new StringBuilder();
			String[] _pids = StringUtils.toArray(existPointers);
			for(String _id : _pids){
				if(!_id.equals(id)){
					result.append(_id);
					result.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
				}
			}
			if(StringUtils.isNotEmpty(result.toString())){
				if(result.toString().endsWith(StringUtils.DEFAULT_SPLIT_SEPARATOR)){
					result.deleteCharAt(result.lastIndexOf(StringUtils.DEFAULT_SPLIT_SEPARATOR));
				}
				functionDao.updateFunctionPointers(result.toString(), functionId);
				return result.toString();
			} else {
				// 只存在一个，也被删除了，返回空的功能点
				functionDao.updateFunctionPointers(StringUtils.EMPTY_STRING, functionId);
				return StringUtils.EMPTY_STRING;
			}
		}
		return null;
	}
}
