package com.walkersoft.system.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.walkersoft.application.util.FunctionUtils;

/**
 * 
 * @author MikeShi
 *
 */
public class FunctionObj implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int TYPE_SYSTEM = -1;
	public static final int TYPE_GROUP = 0;
	public static final int TYPE_ITEM = 1;
	
	public static final int OPEN_STYLE_IFRAME = 0;		// iframe内部
	public static final int OPEN_STYLE_DIALOG = 1;		// 系统对话框方式
	public static final int OPEN_STYLE_MAIN_WINDOW = 2;	// 浏览器主窗口覆盖
	public static final int OPEN_STYLE_OPEN_BROWSER = 9;// 弹出新浏览器窗口
	
	private String id;
	private int orderNum;
	private int fType = 1;
	private String name;
	private String url;
	private String pointer;
	private boolean isRun = true;
	private List<FunctionPointer> pointerList;
	private String parentId;
	
	private int openStyle = OPEN_STYLE_IFRAME;
	
	public int getOpenStyle() {
		return openStyle;
	}
	public void setOpenStyle(int openStyle) {
		this.openStyle = openStyle;
	}
	
	/**
	 * 删除时，是否检查子对象。<br>
	 * 对于带URL的子系统对象，不用检查，因此不会存在孩子。
	 * @return
	 */
	public boolean isCheckChildrenOnRemove() {
		return (this.openStyle != OPEN_STYLE_OPEN_BROWSER 
				&& this.openStyle != OPEN_STYLE_MAIN_WINDOW);
	}

	// 2014-5-22
	// 是否经过SpringMVC的注解扫描并对比配置
	private boolean finishScanAnnotation = false;
	
	public boolean isFinishScanAnnotation() {
		return finishScanAnnotation;
	}
	public void setFinishScanAnnotation(boolean finishScanAnnotation) {
		this.finishScanAnnotation = finishScanAnnotation;
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
	
	public void setOrderNum(int orderNum){
		this.orderNum = orderNum;
	}
	public void setOrder_num(int orderNum) {
		this.orderNum = orderNum;
	}
	public int getType() {
		return fType;
	}
//	public void setFType(int type) {
//		fType = type;
//	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPointer() {
		return pointer;
	}
	public void setPointer(String pointer) {
		this.pointer = pointer;
	}
	
	/**
	 * 菜单是否可用
	 * @return
	 */
	public boolean isAvailable() {
		return isRun;
	}
	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}
	public List<FunctionPointer> getPointerList() {
		return pointerList;
	}
	public void setPointerList(List<FunctionPointer> pointerList) {
		this.pointerList = pointerList;
	}
	public void addPointer(FunctionPointer fp){
		if(pointerList == null){
			pointerList = new ArrayList<FunctionPointer>();
		}
		pointerList.add(fp);
	}
	
	/**
	 * 从功能菜单中，移除一个功能点对象
	 * @param pointerId
	 */
	public void removePointer(String pointerId){
		if(pointerList != null){
			for(Iterator<FunctionPointer> it = pointerList.iterator(); it.hasNext();){
				if(it.next().getId().equals(pointerId)){
					it.remove();
					break;
				}
			}
		}
	}
	
	public String getParentId() {
		return parentId;
	}
	public void setParent_id(String parentId) {
		this.parentId = parentId;
	}
	
	public String getParentName(){
		return FunctionUtils.getFunctionName(parentId);
	}
	
	public void setIs_run(int value){
		this.isRun = value == 1;
	}
	public void setRun(int value){
		setIs_run(value);
	}
	public int getRun(){
		return (this.isRun == true ? 1 : 0);
	}
	
	public void setF_type(int value){
		fType = value;
	}
	public void setType(int value){
		setF_type(value);
	}
	
	public String toString(){
		return new StringBuilder().append("order = ").append(orderNum)
				.append(", url = ").append(url)
				.append(", isRun = ").append(isRun)
				.append(", parentId = ").append(parentId)
				.append(", id=").append(id)
				.append(", name=").append(name)
				.append(", f_type=").append(fType).toString();
	}
}
