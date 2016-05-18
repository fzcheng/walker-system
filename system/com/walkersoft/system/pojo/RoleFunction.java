package com.walkersoft.system.pojo;

import java.util.ArrayList;
import java.util.List;

import com.walker.infrastructure.utils.StringUtils;

/**
 * 功能与角色对应对象定义</p>
 * 这个是新定义对象，原来老对象<code>RoleFuncObj</code>是从老功能移植过来的，新代码中不再应用。
 * @author shikeying
 *
 */
public class RoleFunction {

	private String roleId;
	
	private String functionId;
	
	private List<String> pointerList = new ArrayList<String>(4);
	
	public RoleFunction(){}
	
	public String getRoleId() {
		return roleId;
	}

	public String getFunctionId() {
		return functionId;
	}

	public List<String> getPointerList() {
		return pointerList;
	}

	public RoleFunction setRoleId(String roleId) {
		if(StringUtils.isEmpty(roleId)) return this;
		this.roleId = roleId;
		return this;
	}

	public RoleFunction setFunctionId(String functionId) {
		if(StringUtils.isEmpty(functionId)) return this;
		this.functionId = functionId;
		return this;
	}

	public RoleFunction setPointer(String pointer){
		if(StringUtils.isNotEmpty(pointer)){
			String[] array = StringUtils.toArray(pointer);
			if(array != null){
				for(String s : array)
					pointerList.add(s);
			}
		}
		return this;
	}
	
	public boolean equals(Object o){
		if(o == null) return false;
		if(this == o) return true;
		if(o instanceof RoleFunction){
			RoleFunction rf = (RoleFunction)o;
			if(rf.getRoleId().equals(this.roleId) 
					&& rf.getFunctionId().equals(this.functionId)){
				return true;
			}
		}
		return false;
	}
	
	public int hashCode(){
		return 31 + 7*this.roleId.hashCode() + 11*this.functionId.hashCode();
	}
	
	public String toString(){
		return new StringBuilder().append("RoleFunction: ")
				.append("role = ").append(roleId)
				.append(", function = ").append(functionId)
				.append(", pointer = ").append(pointerList).toString();
	}
	
	public static void main(String[] args){
		String test = "CODE_ADD";
		String[] array = StringUtils.toArray(test);
		if(array != null){
			for(String s : array)
				System.out.println("..... " + s);
		}
	}
}
