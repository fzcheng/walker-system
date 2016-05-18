package com.walkersoft.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.walker.db.Sorts;
import com.walker.db.Sorts.Sort;
import com.walker.db.hibernate.SQLDaoSupport;
import com.walker.db.page.support.GenericPager;
import com.walker.infrastructure.utils.NumberGenerator;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.system.entity.RoleEntity;
import com.walkersoft.system.pojo.RoleFunction;

@Repository("roleDao")
public class RoleDao extends SQLDaoSupport<RoleEntity> {

	private static final Sort[] defaultSorts = new Sort[]{Sorts.ASC().setField("type"), Sorts.DESC().setField("createTime")};
	
	private static final String SQL_ROLE_FUNCTION = "select t.role_id, t.func_item_id, t.pointer from s_role_func t";
	private static final String SQL_ROLE_INFO = "select r.id, r.name, r.summary from s_role r order by r.r_type asc, r.create_time desc";
	private static final String SQL_USER_ROLE = "select ru.role_id from s_role_user ru where ru.user_id = ?";
	private static final String SQL_ROLE_USER = "select ru.user_id from s_role_user ru where ru.role_id = ?";
	
	private static final RowMapper<RoleFunction> roleFunctionMapper = new RoleFunctionRowMapper();
	private static final RowMapper<RoleEntity> roleInfoMapper = new RoleBasicInfoRowMapper();
	private static final RoleUserMapper roleUserMapper = new RoleUserMapper();
	
	public GenericPager<RoleEntity> getRoleList(){
		return this.queryForEntityPage(defaultSorts);
	}
	
	/**
	 * 查询系统所有角色集合，按照类型排序
	 * @return
	 */
	public List<RoleEntity> getAllRoleList(){
		return this.findBy(defaultSorts[0]);
	}
	
	/**
	 * 返回角色对应的用户ID集合
	 * @param roleId 角色ID
	 * @return
	 */
	public List<String> getRoleUserIds(String roleId){
		return this.sqlCustomQuery(SQL_ROLE_USER, new Object[]{roleId}, roleUserMapper);
	}
	
	private static class RoleUserMapper implements RowMapper<String>{
		@Override
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			return rs.getString("user_id");
		}
	}
	
	/**
	 * 返回系统所有角色与功能对应记录
	 * @return
	 */
	public List<RoleFunction> getRoleFunctionList(){
		return this.sqlCustomQuery(SQL_ROLE_FUNCTION, roleFunctionMapper);
	}
	
	public int insertRole(String roleName, String summary){
		assert(StringUtils.isNotEmpty(roleName));
		assert(StringUtils.isNotEmpty(summary));
		
		if(this.findUniqueBy("name", roleName) != null){
			return 1;
		}
		RoleEntity entity = new RoleEntity();
		entity.setName(roleName);
		entity.setSummary(summary);
		entity.setCreateTime(NumberGenerator.getSequenceNumber());
		this.save(entity);
		return 0;
	}
	
	private static final String SQL_INSERT_ROLE = "insert into s_role(id,create_time,"
			+ "r_type, name, summary) values(?,?,?,?,?)";
	
	public void insertRole(RoleEntity entity){
		this.update(SQL_INSERT_ROLE, new Object[]{entity.getId()
				, entity.getCreateTime()
				, entity.getType(), entity.getName(), entity.getSummary()});
	}
	
	private static class RoleFunctionRowMapper implements RowMapper<RoleFunction>{
		@Override
		public RoleFunction mapRow(ResultSet rs, int arg1)
				throws SQLException {
			// TODO Auto-generated method stub
			return new RoleFunction()
				.setRoleId(rs.getString("role_id"))
				.setFunctionId(rs.getString("func_item_id"))
				.setPointer(rs.getString("pointer"));
		}
	}
	
	/**
	 * 返回所有角色信息集合，仅包括ID、名称、备注三个字段
	 * @return
	 */
	public List<RoleEntity> getRoleBasicInfoList(){
		return this.sqlCustomQuery(SQL_ROLE_INFO, roleInfoMapper);
	}
	
	private static class RoleBasicInfoRowMapper implements RowMapper<RoleEntity> {
		@Override
		public RoleEntity mapRow(ResultSet rs, int arg1) throws SQLException {
			// TODO Auto-generated method stub
			RoleEntity entity = new RoleEntity();
			entity.setId(rs.getString("id"));
			entity.setName(rs.getString("name"));
			entity.setSummary(rs.getString("summary"));
			return entity;
		}
	}
	
	/**
	 * 返回用户对应的所有角色ID集合
	 * @param userId
	 * @return
	 */
	public List<String> getRoleIds(String userId){
		assert (StringUtils.isNotEmpty(userId));
		return this.sqlCustomQuery(SQL_USER_ROLE, new Object[]{userId}, roleIdMapper);
	}
	
	private static final RoleIdMapper roleIdMapper = new RoleIdMapper();
	
	private static class RoleIdMapper implements RowMapper<String> {
		@Override
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			// TODO Auto-generated method stub
			return rs.getString("role_id");
		}
	}
}
