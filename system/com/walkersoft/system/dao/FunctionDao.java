package com.walkersoft.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.walker.db.hibernate.SQLDaoSupport;
import com.walker.db.page.support.GenericPager;
import com.walker.infrastructure.utils.NumberGenerator;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.system.pojo.FunctionObj;
import com.walkersoft.system.pojo.FunctionPointer;
import com.walkersoft.system.pojo.RoleFuncObj;

/**
 * 菜单管理DAO实现
 * @author shikeying
 *
 */
@Repository("functionDao")
public class FunctionDao extends SQLDaoSupport<FunctionObj> {

//	private static final String TABLE_S_FUNCTION = "s_function";
	
	private static final String SQL_SELECT_FUNCTION = "select * from s_function order by f_type, order_num";
	private static final String SQL_SELECT_CONDITION = "select * from s_function where parent_id = ? order by f_type, order_num";
	
	private final RowMapper<FunctionObj> rowMapper = new FunctionRowMapper();
	private final PointerRowMapper pointerRowMapper = new PointerRowMapper();
	
//	public GenericPager<FunctionObj> getPageFunctionList(){
//		return this.sqlQueryForPage(TABLE_S_FUNCTION, FunctionObj.class
//				, new Object[]{}, sorts);
//	}
	
	private static final String SQL_SELECT_POINTER = "select * from s_pointer";
	
	public List<Map<String, Object>> getPointerList(){
		return this.sqlQueryForList(SQL_SELECT_POINTER, new Object[]{});
	}
	
	private static final String SQL_GET_POINTERS = "select * from s_pointer where function_id = ? order by order_num";
	
	/**
	 * 返回菜单下所有功能点集合
	 * @param functionId
	 * @return
	 */
	public List<FunctionPointer> getPointerList(String functionId){
		return this.sqlCustomQuery(SQL_GET_POINTERS, new Object[]{functionId}, pointerRowMapper);
	}
	
	public List<FunctionObj> getAllFunctions(){
		return this.sqlQuery(SQL_SELECT_FUNCTION, rowMapper);
	}
	
	public GenericPager<FunctionObj> getPageFunctionList(String parentId){
		if(StringUtils.isEmpty(parentId)){
			return this.sqlSimpleQueryPager(SQL_SELECT_FUNCTION, null, rowMapper);
		} else {
			return this.sqlSimpleQueryPager(SQL_SELECT_CONDITION, new Object[]{parentId}, rowMapper);
		}
	}
	
	private static class FunctionRowMapper implements RowMapper<FunctionObj>{
		@Override
		public FunctionObj mapRow(ResultSet rs, int arg1) throws SQLException {
			// TODO Auto-generated method stub
			FunctionObj f = new FunctionObj();
			f.setId(rs.getString("id"));
			f.setOrder_num(rs.getInt("order_num"));
			f.setF_type(rs.getInt("f_type"));
			f.setName(rs.getString("name"));
			f.setUrl(rs.getString("url"));
			f.setPointer(rs.getString("pointer"));
			f.setIs_run(rs.getInt("is_run"));
			f.setParent_id(rs.getString("parent_id"));
			f.setOpenStyle(rs.getInt("open_style"));
			return f;
		}
	}
	
	private class PointerRowMapper implements RowMapper<FunctionPointer>{
		@Override
		public FunctionPointer mapRow(ResultSet rs, int arg1)
				throws SQLException {
			FunctionPointer pointer = new FunctionPointer();
			pointer.setId(rs.getString("id"));
			pointer.setName(rs.getString("name"));
			pointer.setOrderNum(rs.getInt("order_num"));
			pointer.setFunctionId(rs.getString("function_id"));
			pointer.setUrl(rs.getString("url"));
			return pointer;
		}
	}
	
	private static final StringBuilder SQL_USER_FUNC = new StringBuilder()
	.append("select ru.role_id, rf.func_item_id, f.Name, f.f_type, rf.pointer")
	.append(" From s_role_user ru")
	.append(" , s_role_func rf")
	.append(" , s_function f")
	.append(" Where ru.user_id = ?")
	.append(" And ru.role_id = rf.role_id")
	.append(" And rf.func_item_id = f.Id")
	.append(" Order By f.order_num");
	
	/**
	 * 返回用户所能使用的菜单列表信息，存在重复的。
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getFuncListByUser(String userId) throws Exception{
		return this.sqlQueryForList(SQL_USER_FUNC.toString(), new Object[]{userId});
	}
	
	/**
	 * 返回该角色对应的所有用户ID
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List<String> getRoleUserList(String roleId) throws Exception{
		List<String> usrList = new ArrayList<String>();
		List<Map<String, Object>> list = this.sqlQueryForList(SQL_ROLE_USERID, new Object[]{roleId});
		if(list != null){
			for(Map<String, Object> roleUsr : list){
				usrList.add(roleUsr.get("USER_ID").toString());
			}
		}
		return usrList;
	}
	private static final String SQL_ROLE_USERID = "select t.user_id from s_role_user t"
			+ " where t.role_id = ?";
	
	/**
	 * 返回指定角色所包含的功能Map
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public Map<String, RoleFuncObj> getRoleFuncList(String roleId) throws Exception{
		Map<String, RoleFuncObj> roleFuncMap = new HashMap<String, RoleFuncObj>();
		List<Map<String, Object>> rfLst = this.sqlQueryForList(SQL_ROLE_FUNCS, new Object[]{roleId});
		if(rfLst == null) return roleFuncMap;
		for(Map<String, Object> _rfPo : rfLst){
			RoleFuncObj rfo = this.convertRoleFunc(_rfPo);
			roleFuncMap.put(rfo.getFuncItemId(), rfo);
		}
		return roleFuncMap;
	}
	private static final String SQL_ROLE_FUNCS = "select t.* from s_role_func t"
			+ " where t.role_id = ?";
	
	private RoleFuncObj convertRoleFunc(Map<String, Object> _rfPo){
		if(_rfPo != null){
			RoleFuncObj rfo = new RoleFuncObj();
			rfo.setId(_rfPo.get("ID").toString());
			rfo.setRoleId(_rfPo.get("ROLE_ID").toString());
			rfo.setFuncItemId(_rfPo.get("FUNC_ITEM_ID").toString());
			if(_rfPo.get("POINTER") != null){
				String[] _pLst = _rfPo.get("POINTER").toString().split(",");
				for(String _s : _pLst){
					rfo.addPointer(_s);
				}
			}
			return rfo;
		}
		return null;
	}
	
	/**
	 * 根据角色ID，删除该角色包含的所有功能
	 * @param roleId
	 */
	public void deleteFuncByRoleId(String roleId) throws Exception{
		this.update(SQL_DEL_ROLE_FUNC, new Object[]{roleId});
	}
	private static final String SQL_DEL_ROLE_FUNC = "delete from s_role_func where role_id = ?";
	
	/**
	 * 删除s_role_func表中与某个function记录有关的数据。
	 * @param functionId
	 */
	public void deleteRoleFunc(String functionId){
		this.update(SQL_DEL_ROLE_FUNC_ID, new Object[]{functionId});
	}
	private static final String SQL_DEL_ROLE_FUNC_ID = "delete from s_role_func where func_item_id = ?";
	
	/**
	 * 根据角色ID，删除该角色包含的所有用户
	 * @param roleId
	 */
	public void deleteUserByRoleId(String roleId){
		this.update(SQL_DEL_ROLE_USER, new Object[]{roleId});
	}
	private static final String SQL_DEL_ROLE_USER = "delete from s_role_user where role_id = ?";
	
	/**
	 * 删除用户具有的所有角色
	 * @param userId
	 */
	public void deleteRoleByUserId(String userId){
		this.update(SQL_DEL_USER_ROLE, new Object[]{userId});
	}
	private static final String SQL_DEL_USER_ROLE = "delete from s_role_user where user_id = ?";
	
	/**
	 * 添加角色选择的功能
	 * @param roleId
	 * @param funcIdsMap
	 * @throws Exception
	 */
	public void insertFuncByRoleId(String roleId
			, Map<String, String> funcIdsMap) throws Exception{
		if(funcIdsMap == null || funcIdsMap.size() <= 0){
			return;
		}
		for(Map.Entry<String, String> entry : funcIdsMap.entrySet()){
			this.update(SQL_INSERT_ROLE_FUNC, new Object[]{NumberGenerator.generatorHexUUID()
					, roleId, entry.getKey(), entry.getValue()});
		}
	}
	private static final String SQL_INSERT_ROLE_FUNC = "insert into s_role_func(id, role_id, func_item_id, pointer)"
			+ " values(?,?,?,?)";
	
	/**
	 * 保存角色选择用户
	 * @param roleId
	 * @param userIds
	 * @throws Exception
	 */
	public void insertUserByRoleId(String roleId
			, List<String> userIds){
		if(userIds == null || userIds.size() <= 0){
			return;
		}
		for(String userId : userIds){
			this.update(SQL_INSERT_ROLE_USER, new Object[]{NumberGenerator.generatorHexUUID()
					, roleId, userId});
		}
	}
	
	public void insertRoleByUserId(String userId, String[] roleIds){
		assert (StringUtils.isNotEmpty(userId));
		assert (roleIds != null);
		for(String roleId : roleIds){
			this.update(SQL_INSERT_ROLE_USER, new Object[]{NumberGenerator.generatorHexUUID()
					, roleId, userId});
		}
	}
	private static final String SQL_INSERT_ROLE_USER = "insert into s_role_user(id, role_id, user_id)"
			+ " values(?,?,?)";
	
	private static final String SQL_INSERT_FUNCTION = "insert into s_function(id, order_num, "
			+ "f_type, name, url, pointer, is_run, parent_id, open_style) values(?,?,?,?,?,?,?,?,?)";
	
	public void insertFunction(FunctionObj function){
		Object[] params = new Object[9];
		params[0] = function.getId();
		params[1] = function.getOrderNum();
		params[2] = function.getType();
		params[3] = function.getName();
		params[4] = function.getUrl();
		params[5] = function.getPointer();
		params[6] = function.getRun();
		params[7] = function.getParentId();
		params[8] = function.getOpenStyle();
		this.update(SQL_INSERT_FUNCTION, params);
	}
	
	private static final String SQL_UPDATE_FUNCTION = "update s_function set order_num=?, name=?,"
			+ "url=?, is_run=?, open_style=? where id=?";
	
	public void updateFunctionSystem(FunctionObj function){
		Object[] params = new Object[6];
		params[0] = function.getOrderNum();
		params[1] = function.getName();
		params[2] = function.getUrl();
		params[3] = function.getRun();
		params[4] = function.getOpenStyle();
		params[5] = function.getId();
		this.update(SQL_UPDATE_FUNCTION, params);
	}
	
	private static final String SQL_UPDATE_FUNC_GROUP = "update s_function set order_num=?, name=?,"
			+ "parent_id=?, is_run=? where id=?";
	public void updateFunctionGroup(FunctionObj function){
		Object[] params = new Object[5];
		params[0] = function.getOrderNum();
		params[1] = function.getName();
		params[2] = function.getParentId();
		params[3] = function.getRun();
		params[4] = function.getId();
		this.update(SQL_UPDATE_FUNC_GROUP, params);
	}
	
	private static final String SQL_UPDATE_FUNC_ITEM = "update s_function set order_num=?, name=?,"
			+ "parent_id=?, url=?, is_run=?, open_style=? where id=?";
	public void updateFunctionItem(FunctionObj function){
		Object[] params = new Object[7];
		params[0] = function.getOrderNum();
		params[1] = function.getName();
		params[2] = function.getParentId();
		params[3] = function.getUrl();
		params[4] = function.getRun();
		params[5] = function.getOpenStyle();
		params[6] = function.getId();
		this.update(SQL_UPDATE_FUNC_ITEM, params);
	}
	
	private static final String SQL_GET_ONE = "select * from s_function where id = ?";
	
	public FunctionObj getFunction(String id){
		return this.getJdbcTemplate().queryForObject(SQL_GET_ONE, new Object[]{id}, rowMapper);
	}
	
	public FunctionObj searchFunction(String id){
		List<FunctionObj> list = this.sqlQuery(SQL_GET_ONE, new Object[]{id}, rowMapper);
		if(list != null && list.size() > 0){
			return list.get(0);
		} else
			return null;
	}
	
	private static final String SQL_DEL_FUNCTION = "delete from s_function where id = ?";
	
	public void deleteFunction(String id){
		this.update(SQL_DEL_FUNCTION, new Object[]{id});
	}
	
	private static final String SQL_EXIST_POINTER = "select * from s_pointer where id = ?";
	
	/**
	 * 根据功能点ID查询对象
	 * @param id 功能点ID
	 * @return
	 */
	public FunctionPointer getFunctionPointer(String id){
		List<FunctionPointer> list = this.sqlCustomQuery(SQL_EXIST_POINTER, new Object[]{id}, pointerRowMapper);
		if(!StringUtils.isEmptyList(list)){
			return list.get(0);
		}
		return null;
	}
	
	private static final String SQL_INSERT_POINTER = "insert into s_pointer(id,name,order_num,"
			+ "selected,function_id,url) values(?,?,?,?,?,?)";
	
	public void insertFunctionPointer(FunctionPointer fp){
		Object[] params = new Object[6];
		params[0] = fp.getId();
		params[1] = fp.getName();
		params[2] = fp.getOrderNum();
		params[3] = fp.getSelected();
		params[4] = fp.getFunctionId();
		params[5] = fp.getUrl();
		this.update(SQL_INSERT_POINTER, params);
	}
	
	private static final String SQL_DEL_POINTER = "delete from s_pointer where id = ?";
	
	/**
	 * 删除一个功能点
	 * @param id
	 */
	public void deletePointer(String id){
		this.update(SQL_DEL_POINTER, new Object[]{id});
	}
	
	private static final String SQL_UPDATE_FUNCTION_P = "update s_function set pointer = ? where id = ?";
	
	/**
	 * 更新菜单关联的功能点字段值
	 * @param selectIds
	 * @param functionId
	 */
	public void updateFunctionPointers(String selectIds, String functionId){
		this.update(SQL_UPDATE_FUNCTION_P, new Object[]{selectIds, functionId});
	}

	
	private static final String SQL_QUERY_FID_BY_URL = "select * from s_function where URL = ?";
	/**
	 * 根据url 查找 fid
	 * @param url
	 * @return
	 */
	public FunctionObj queryFidByUrl(String url) {
		return this.getJdbcTemplate().queryForObject(SQL_QUERY_FID_BY_URL, new Object[]{url}, rowMapper);
		//return null;
	}
}
