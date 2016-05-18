package com.walkersoft.flow.pojo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.walker.infrastructure.utils.StringUtils;

/**
 * 任务节点的设计器对象
 * @author shikeying
 *
 */
public class TaskNode {

	private String id;
	
	private String x;
	private String y;
	
	private String procesDefineId;
	
	private StringBuilder inPositions = new StringBuilder();
	private StringBuilder outPositions = new StringBuilder();
	
	private Map<String, AnchorIn> anchorIns = new TreeMap<String, AnchorIn>();
	private Map<String, AnchorOut> anchorOuts = new TreeMap<String, AnchorOut>();
	
	
	public TaskNode(){}

	public String getId() {
		return id;
	}

	public TaskNode setId(String id) {
		this.id = id;
		return this;
	}

	public String getX() {
		return x;
	}

	public TaskNode setX(String x) {
		this.x = x;
		return this;
	}

	public String getY() {
		return y;
	}

	public TaskNode setY(String y) {
		this.y = y;
		return this;
	}

	public String getProcesDefineId() {
		return procesDefineId;
	}

	public TaskNode setProcesDefineId(String procesDefineId) {
		this.procesDefineId = procesDefineId;
		return this;
	}

	public String getInPositions() {
		return inPositions.toString();
	}

	public TaskNode setInPositions(String inPositions) {
		this.inPositions = new StringBuilder(inPositions);
		return this;
	}

	public String getOutPositions() {
		return outPositions.toString();
	}

	public TaskNode setOutPositions(String outPositions) {
		this.outPositions = new StringBuilder(outPositions);
		return this;
	}
	
	/**
	 * 添加一个可用的进入位置信息，如：TopBottom<br>
	 * inPositions中的位置信息用英文逗号分隔。
	 * @param position
	 */
	public void addInPosition(String position){
		assert (StringUtils.isNotEmpty(position));
		if(inPositions.length() == 0){
			inPositions.append(position);
		} else {
			inPositions.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			inPositions.append(position);
		}
	}
	
	/**
	 * 添加一个可用的进入位置信息，如：TopBottom<br>
	 * outPositions中的位置信息用英文逗号分隔。
	 * @param position
	 */
	public void addOutPosition(String position){
		assert (StringUtils.isNotEmpty(position));
		if(outPositions.length() == 0){
			outPositions.append(position);
		} else {
			outPositions.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			outPositions.append(position);
		}
	}
	
	public String toString(){
		return new StringBuilder().append("id=").append(id)
				.append(", x=").append(x)
				.append(", y=").append(y)
				.append(", inPositions=").append(inPositions)
				.append(", outPositions=").append(outPositions).toString();
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// anchor in or out information
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public void addAnchorIn(AnchorIn anchorIn){
		assert (anchorIn != null);
		anchorIns.put(anchorIn.getId(), anchorIn);
	}
	
	public void addAnchorOut(AnchorOut anchorOut){
		assert (anchorOut != null);
		anchorOuts.put(anchorOut.getId(), anchorOut);
	}
	
	public Iterator<AnchorIn> getAnchorsInList(){
		if(anchorIns.size() == 0) return null;
//		return new ArrayList<AnchorIn>(anchorIns.values());
		return anchorIns.values().iterator();
	}
	
	public Iterator<AnchorOut> getAnchorsOutList(){
		if(anchorOuts.size() == 0) return null;
//		return new ArrayList<AnchorOut>(anchorOuts.values());
		return anchorOuts.values().iterator();
	}
	
	/**
	 * 删除与给定任务有关联的节点信息
	 * @param taskDefineId 关联的任务ID
	 */
	public void removeAnchorAsso(String taskDefineId){
		for(Iterator<AnchorIn> it = anchorIns.values().iterator(); it.hasNext();){
			if(it.next().getFromNode().equals(taskDefineId)){
				it.remove();
			}
		}
		for(Iterator<AnchorOut> it = anchorOuts.values().iterator(); it.hasNext();){
			if(it.next().getNextNode().equals(taskDefineId)){
				it.remove();
			}
		}
	}
	
	/**
	 * 根据输入参数，删除一个输出点信息
	 * @param outPosition
	 */
	public void removeAnchorOut(String outPosition){
		for(Iterator<AnchorOut> it = anchorOuts.values().iterator(); it.hasNext();){
			if(it.next().getPosition().equals(outPosition)){
				// 找出这个输出节点，删除。因为输出节点都是独立的不会重复，所以不用考虑nextPosition
				it.remove();
				break;
			}
		}
	}
	
	/**
	 * 根据输入参数，删除一个输出点信息。输入点比输出多一个参数，因为输入点可被复用。
	 * @param inPosition 输入位置
	 * @param fromTaskNode 上个节点ID
	 */
	public void removeAnchorIn(String inPosition, String fromTaskNode){
		AnchorIn ai = null;
		for(Iterator<AnchorIn> it = anchorIns.values().iterator(); it.hasNext();){
			ai = it.next();
			if(ai.getPosition().equals(inPosition) && ai.getFromNode().equals(fromTaskNode)){
				it.remove();
				break;
			}
		}
	}
	
	public void updateAnchorOut(String outPosition, String nextNode, String nextPosition){
		AnchorOut anchorOut = null;
		for(Iterator<AnchorOut> it = anchorOuts.values().iterator(); it.hasNext();){
			anchorOut = it.next();
			if(anchorOut.getPosition().equals(outPosition)){
				anchorOut.setNextNode(nextNode).setNextPosition(nextPosition);
				break;
			}
		}
	}
	
	/**
	 * 更新anchorIn对象信息：from_node, form_position
	 * @param inPosition 当前anchor的位置
	 * @param oldFromTaskId 上一步连接的任务ID
	 * @param changedSrcTaskId 上一步改变的任务ID
	 * @param changedSrcPosition 上一步改变的位置
	 */
	public void updateAnchorIn(String inPosition, String oldFromTaskId
			, String changedSrcTaskId, String changedSrcPosition){
		AnchorIn ai = null;
		for(Iterator<AnchorIn> it = anchorIns.values().iterator(); it.hasNext();){
			ai = it.next();
			if(ai.getPosition().equals(inPosition) && ai.getFromNode().equals(oldFromTaskId)){
				ai.setFromNode(changedSrcTaskId).setFromPosition(changedSrcPosition);
				break;
			}
		}
	}
	
	/**
	 * 仅更新anchorOut对象的position属性
	 * @param outPosition
	 * @param changedOutPosition
	 */
	public void updateAnchorOutPosition(String outPosition, String changedOutPosition){
		AnchorOut anchorOut = null;
		for(Iterator<AnchorOut> it = anchorOuts.values().iterator(); it.hasNext();){
			anchorOut = it.next();
			if(anchorOut.getPosition().equals(outPosition)){
				anchorOut.setPosition(changedOutPosition);
				break;
			}
		}
	}
	
	public void updateAnchorInPosition(String inPosition
			, String oldFromTaskId, String changedInPosition){
		AnchorIn ai = null;
		for(Iterator<AnchorIn> it = anchorIns.values().iterator(); it.hasNext();){
			ai = it.next();
			if(ai.getPosition().equals(inPosition) && ai.getFromNode().equals(oldFromTaskId)){
				ai.setPosition(changedInPosition);
				break;
			}
		}
	}
	
	/**
	 * 返回任务可用的所有输入节点位置信息，在界面上展示使用
	 * @return 返回Map，格式：key = 位置, value = true
	 */
	public Map<String, String> getInPositionMap(){
		Map<String, String> result = new HashMap<String, String>();
		String[] inp = inPositions.toString().split(StringUtils.DEFAULT_SPLIT_SEPARATOR);
		for(String p : inp){
			result.put(p, "true");
		}
		return result;
	}
	
	public Map<String, String> getOutPositionMap(){
		Map<String, String> result = new HashMap<String, String>();
		String[] outp = outPositions.toString().split(StringUtils.DEFAULT_SPLIT_SEPARATOR);
		for(String p : outp){
			result.put(p, "true");
		}
		return result;
	}
}
