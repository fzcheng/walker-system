package com.walkersoft.system.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.walker.infrastructure.utils.Assert;

/**
 * 菜单组对象
 * @author MikeShi
 *
 */
public class FunctionGroup implements Serializable, Comparable<FunctionGroup>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private int orderNum;
	private String name;
	private List<FunctionObj> itemList;
	private String parentId;
	
	private boolean url;
	
	private boolean visible = true;
	
	/**
	 * 是否显示分组，因为在处理“带URL子系统”问题中，使用了虚拟分组（创建了这些对象）<br>
	 * 因此导致“分配角色功能”时，会显示这些数据（不应当显示），因此设置该属性标识能不能显示。
	 * @return
	 */
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	/**
	 * 判断菜单组是否存在URL地址，此方法只有在存在“带URL子系统”时才用到
	 * @return
	 */
	public boolean hasUrl() {
		return url;
	}
	public void setUrl(boolean hasUrl) {
		this.url = hasUrl;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<FunctionObj> getItemList() {
		return itemList;
	}
	public void setItemList(List<FunctionObj> itemList) {
		this.itemList = itemList;
	}
	
	public void addItem(FunctionObj fo){
		if(itemList == null){
			itemList = new ArrayList<FunctionObj>();
		}
		itemList.add(fo);
	}
	
	/**
	 * 从菜单组中删除一个菜单项
	 * @param id 删除的菜单项ID
	 */
	public void removeItem(String id){
		Assert.hasText(id);
		if(itemList != null){
			for(Iterator<FunctionObj> it = itemList.iterator(); it.hasNext();){
				if(it.next().getId().equals(id)){
					it.remove();
					break;
				}
			}
		}
	}
	
	public boolean equals(Object o){
		if(o == null) return false;
		if(o == this) return true;
		if(o instanceof FunctionGroup){
			FunctionGroup fg = (FunctionGroup)o;
			if(fg.getId().equals(this.id)){
				return true;
			}
		}
		return false;
	}
	
	public int hashCode(){
		return 31 + 31*this.id.hashCode();
	}
	
	@Override
	public int compareTo(FunctionGroup o) {
		return (this.orderNum - o.orderNum);
	}
}
