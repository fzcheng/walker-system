package com.walkersoft.flow.pojo;

/**
 * 设计器中，节点离开的对象定义。</p>
 * 它包含了当前任务所有离开节点信息，包括：下一个节点的离开信息
 * @author shikeying
 *
 */
public class AnchorOut {

	private String id;
	// 关联的任务节点ID
	private String taskNodeId;
	
	// 进入节点的位置
	private String position;
	
	private String nextNode;
	private String nextPosition;
	
	private String summary;
	
	public AnchorOut(){}

	public String getId() {
		return id;
	}

	public AnchorOut setId(String id) {
		this.id = id;
		return this;
	}

	public String getTaskNodeId() {
		return taskNodeId;
	}

	public AnchorOut setTaskNodeId(String taskNodeId) {
		this.taskNodeId = taskNodeId;
		return this;
	}

	public String getPosition() {
		return position;
	}

	public AnchorOut setPosition(String position) {
		this.position = position;
		return this;
	}

	public String getNextNode() {
		return nextNode;
	}

	public AnchorOut setNextNode(String nextNode) {
		this.nextNode = nextNode;
		return this;
	}

	public String getNextPosition() {
		return nextPosition;
	}

	public AnchorOut setNextPosition(String nextPosition) {
		this.nextPosition = nextPosition;
		return this;
	}

	public String getSummary() {
		return summary;
	}

	public AnchorOut setSummary(String summary) {
		this.summary = summary;
		return this;
	}
	
	public String toString(){
		return new StringBuilder().append("id=").append(id)
				.append(", nodeId=").append(taskNodeId)
				.append(", position=").append(position)
				.append(", nextNode=").append(nextNode)
				.append(", nextPosition=").append(nextPosition)
				.append(", summary=").append(summary).toString();
	}
}
