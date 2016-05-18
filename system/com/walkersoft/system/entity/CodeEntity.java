package com.walkersoft.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 系统代码对象
 * @author shikeying
 *
 */
@Entity
@Table(name = "s_code")
public class CodeEntity {

	public static final int CODE_TYPE_TABLE = 0;
	public static final int CODE_TYPE_ITEM  = 1;
	
	private String id;
		
	private long sxh;
	private String name;
	private String codeId;
	private int codeType = CODE_TYPE_ITEM;
	private String parentId;
	private int codeSec = 0;
	private int childSum = 0; // 子代码数量

	@GenericGenerator(name="idGenerator", strategy="uuid")
	@Id
	@GeneratedValue(generator="idGenerator")
	@Column(name="id", length=36)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "sxh")
	public long getSxh() {
		return sxh;
	}
	public void setSxh(long sxh) {
		this.sxh = sxh;
	}
	
	@Column(name = "name", length=50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "code_id", length = 100)
	public String getCodeId() {
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	
	@Column(name = "code_type")
	public int getCodeType() {
		return codeType;
	}
	public void setCodeType(int codeType) {
		this.codeType = codeType;
	}
	
	@Column(name = "parent_id", length = 36)
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	@Column(name = "code_sec")
	public int getCodeSec() {
		return codeSec;
	}
	public void setCodeSec(int codeSec) {
		this.codeSec = codeSec;
	}
	
	@Column(name = "child_sum")
	public int getChildSum() {
		return childSum;
	}
	public void setChildSum(int childSum) {
		this.childSum = childSum;
	}
}
