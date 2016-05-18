package com.walkersoft.system.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色功能表对象
 * @author MikeShi
 *
 */
@Deprecated
public class RoleFuncObj implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String roleId;
	private String funcItemId;
	private String pointer;
	
	private List<String> pointerLst;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getFuncItemId() {
		return funcItemId;
	}

	public void setFuncItemId(String funcItemId) {
		this.funcItemId = funcItemId;
	}

	public String getPointer() {
		return pointer;
	}

	public void setPointer(String pointer) {
		this.pointer = pointer;
	}

	public List<String> getPointerLst() {
		return pointerLst;
	}

	public void setPointerLst(List<String> pointerLst) {
		this.pointerLst = pointerLst;
	}
	
	public void addPointer(String pointerId){
		if(pointerLst == null){
			pointerLst = new ArrayList<String>();
		}
		if(!pointerLst.contains(pointerId)){
			pointerLst.add(pointerId);
		}
	}
	
	/**
	 * 判断是否存在该功能点
	 * @param pointerId
	 * @return
	 */
	public boolean isExistPointer(String pointerId){
		if(pointerLst != null && pointerLst.contains(pointerId)){
			return true;
		} else
			return false;
	}
}
