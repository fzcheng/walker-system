package com.walkersoft.flow.pojo;

import com.walker.flow.web.jsplump.PlumpDefine;
import com.walker.infrastructure.utils.StringUtils;

/**
 * 调整节点位置对象定义，用来接收参数
 * @author shikeying
 *
 */
public class PositionAjustor {

	public static final int CLOSE = 0;
	public static final int INPUT = -1;
	public static final int OUTPUT = 1;
	
	private String taskDefineId;
	
	public String getTaskDefineId() {
		return taskDefineId;
	}

	public void setTaskDefineId(String taskDefineId) {
		this.taskDefineId = taskDefineId;
	}

	private int topLeft = CLOSE;
	private int topCenter = CLOSE;
	private int topRight = CLOSE;
	
	private int middleLeft = CLOSE;
	private int middleRight = CLOSE;
	
	private int bottomLeft = CLOSE;
	private int bottomCenter = CLOSE;
	private int bottomRight = CLOSE;
	
	/* 输入、输出的数量 */
	private int inCount = 0;
	private int outCount = 0;
	
	private void addCount(int status){
		if(status == INPUT) inCount++;
		else if(status == OUTPUT) outCount++;
	}

	public int getTopCenter() {
		return topCenter;
	}

	public void setTopCenter(int topCenter) {
		addCount(topCenter);
		this.topCenter = topCenter;
	}

	public int getTopRight() {
		return topRight;
	}

	public void setTopRight(int topRight) {
		addCount(topRight);
		this.topRight = topRight;
	}

	public int getMiddleLeft() {
		return middleLeft;
	}

	public void setMiddleLeft(int middleLeft) {
		addCount(middleLeft);
		this.middleLeft = middleLeft;
	}

	public int getMiddleRight() {
		return middleRight;
	}

	public void setMiddleRight(int middleRight) {
		addCount(middleRight);
		this.middleRight = middleRight;
	}

	public int getBottomLeft() {
		return bottomLeft;
	}

	public void setBottomLeft(int bottomLeft) {
		addCount(bottomLeft);
		this.bottomLeft = bottomLeft;
	}

	public int getBottomCenter() {
		return bottomCenter;
	}

	public void setBottomCenter(int bottomCenter) {
		addCount(bottomCenter);
		this.bottomCenter = bottomCenter;
	}

	public int getBottomRight() {
		return bottomRight;
	}

	public void setBottomRight(int bottomRight) {
		addCount(bottomRight);
		this.bottomRight = bottomRight;
	}

	public int getTopLeft() {
		return topLeft;
	}

	public void setTopLeft(int topLeft) {
		addCount(topLeft);
		this.topLeft = topLeft;
	}
	
	public String toString(){
		return new StringBuilder().append("topLeft=").append(topLeft)
				.append(", topCenter=").append(topCenter)
				.append(", topRight=").append(topRight)
				.append(", middleLeft=").append(middleLeft)
				.append(", middleRight=").append(middleRight)
				.append(", bottomLeft=").append(bottomLeft)
				.append(", bottomCenter=").append(bottomCenter)
				.append(", bottomRight=").append(bottomRight).toString();
	}
	
	/**
	 * 是否没有选择任何选项，如果是返回<code>true</code>
	 * @return
	 */
	public boolean isSelectNothing(){
//		if(topLeft == CLOSE 
//				&& topCenter == CLOSE 
//				&& topRight == CLOSE 
//				&& middleLeft == CLOSE 
//				&& middleRight == CLOSE 
//				&& bottomLeft == CLOSE 
//				&& bottomCenter == CLOSE 
//				&& bottomRight == CLOSE){
//			return true;
//		}
//		return false;
		if(inCount == 0 && outCount == 0)
			return true;
		else
			return false;
	}
	
	/**
	 * 是否只选择了输入节点或者输出节点，如果是返回<code>true</code><br>
	 * 在界面中是不允许用户仅选择输入（或输出）节点的，必须要既有输入也有输出。
	 * @return
	 */
	public boolean isSelectOnlyInOrOut(){
		if(getSelectInPositions() == null || getSelectOutPositions() == null){
			return true;
		}
		return false;
	}
	
	/**
	 * 验证是否符合单任务节点的位置选择，对于单任务只能有一个输入和一个输出位置。<br>
	 * 如果验证符合，就返回<code>true</code>
	 * @return
	 */
	public boolean validateSingleTask(){
		return (inCount == 1 && outCount == 1);
	}
	
	public boolean validateSplitTask(){
		return (inCount == 1 && outCount >= 1);
	}
	
	public boolean validateAffluenceTask(){
		return (inCount >= 1 && outCount == 1);
	}
	
	public boolean validateSplitAndAffluence(){
		return (inCount >= 1 && outCount >= 1);
	}
	
	public boolean validateEdgeTask(){
		return (inCount == 1 && outCount == 0);
	}
	
	private StringBuilder inPositions = null;
	private StringBuilder outPositions = null;
	
	/**
	 * 返回输入位置字符串，多个用逗号分隔，如：TopCenter,BottomRight,...
	 * @return
	 */
	public String getSelectInPositions(){
		if(inPositions == null){
			inPositions = new StringBuilder();
			if(topLeft == INPUT){
				inPositions.append(PlumpDefine.TOP_LEFT);
				inPositions.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			}
			if(topCenter == INPUT){
				inPositions.append(PlumpDefine.TOP_CENTER);
				inPositions.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			}
			if(topRight == INPUT){
				inPositions.append(PlumpDefine.TOP_RIGHT);
				inPositions.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			}
			if(middleLeft == INPUT){
				inPositions.append(PlumpDefine.LEFT_MIDDLE);
				inPositions.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			}
			if(middleRight == INPUT){
				inPositions.append(PlumpDefine.RIGHT_MIDDLE);
				inPositions.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			}
			if(bottomLeft == INPUT){
				inPositions.append(PlumpDefine.BOTTOM_LEFT);
				inPositions.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			}
			if(bottomCenter == INPUT){
				inPositions.append(PlumpDefine.BOTTOM_CENTER);
				inPositions.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			}
			if(bottomRight == INPUT){
				inPositions.append(PlumpDefine.BOTTOM_RIGHT);
				inPositions.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			}
			if(inPositions.toString().endsWith(StringUtils.DEFAULT_SPLIT_SEPARATOR)){
				inPositions.deleteCharAt(inPositions.lastIndexOf(StringUtils.DEFAULT_SPLIT_SEPARATOR));
			}
		}
		return inPositions.toString();
	}
	
	public String getSelectOutPositions(){
		if(outPositions == null){
			outPositions = new StringBuilder();
			if(topLeft == OUTPUT){
				outPositions.append(PlumpDefine.TOP_LEFT);
				outPositions.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			}
			if(topCenter == OUTPUT){
				outPositions.append(PlumpDefine.TOP_CENTER);
				outPositions.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			}
			if(topRight == OUTPUT){
				outPositions.append(PlumpDefine.TOP_RIGHT);
				outPositions.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			}
			if(middleLeft == OUTPUT){
				outPositions.append(PlumpDefine.LEFT_MIDDLE);
				outPositions.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			}
			if(middleRight == OUTPUT){
				outPositions.append(PlumpDefine.RIGHT_MIDDLE);
				outPositions.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			}
			if(bottomLeft == OUTPUT){
				outPositions.append(PlumpDefine.BOTTOM_LEFT);
				outPositions.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			}
			if(bottomCenter == OUTPUT){
				outPositions.append(PlumpDefine.BOTTOM_CENTER);
				outPositions.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			}
			if(bottomRight == OUTPUT){
				outPositions.append(PlumpDefine.BOTTOM_RIGHT);
				outPositions.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			}
			if(outPositions.toString().endsWith(StringUtils.DEFAULT_SPLIT_SEPARATOR)){
				outPositions.deleteCharAt(outPositions.lastIndexOf(StringUtils.DEFAULT_SPLIT_SEPARATOR));
			}
		}
		return outPositions.toString();
	}
}
