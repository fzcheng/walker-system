package com.walkersoft.flow.pojo;

/**
 * 设计器中，节点进入的对象定义。</p>
 * 它包含了当前任务所有进入节点信息，包括：上个节点的离开信息
 * @author shikeying
 * @date 2014-7-7
 *
 */
public class AnchorIn {

	private String id;
	// 关联的任务节点ID
	private String taskNodeId;
	
	// 进入节点的位置
	private String position;
	
	// 上一个节点ID
	private String fromNode;
	// 上一个节点离开的位置信息
	private String fromPosition;
	
	public AnchorIn(){}

	public String getId() {
		return id;
	}

	public AnchorIn setId(String id) {
		this.id = id;
		return this;
	}

	public String getTaskNodeId() {
		return taskNodeId;
	}

	public AnchorIn setTaskNodeId(String taskNodeId) {
		this.taskNodeId = taskNodeId;
		return this;
	}

	public String getPosition() {
		return position;
	}

	public AnchorIn setPosition(String position) {
		this.position = position;
		return this;
	}

	public String getFromNode() {
		return fromNode;
	}

	public AnchorIn setFromNode(String fromNode) {
		this.fromNode = fromNode;
		return this;
	}

	public String getFromPosition() {
		return fromPosition;
	}

	public AnchorIn setFromPosition(String fromPosition) {
		this.fromPosition = fromPosition;
		return this;
	}
	
	public String toString(){
		return new StringBuilder().append("id=").append(id)
				.append(", nodeId=").append(taskNodeId)
				.append(", position=").append(position)
				.append(", fromNode=").append(fromNode)
				.append(", fromPosition=").append(fromPosition).toString();
	}
}
