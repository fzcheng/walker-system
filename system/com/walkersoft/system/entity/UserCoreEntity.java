package com.walkersoft.system.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.MyApplicationContext;
import com.walkersoft.application.util.DepartmentUtils;

@Entity
@Table(name = "s_user_core")
public class UserCoreEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1036816062609195139L;

	/**
	 * 
	 */
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	@Id
	@Column(name="id", length=36)
	private String id;
	
	@Column(name = "create_time")
	private long createTime;
	
	@Column(name = "order_num")
	private long orderNum = 0;
	
	@Column(name = "name", length=100)
	private String name;
	
	@Column(name = "login_id", length=50)
	private String loginId;
	
	@Column(name = "password", length=50)
	private String password;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "type")
	private int type = TYPE_NORMAL;
	
	public static final int TYPE_SUPER = 0;
	public static final int TYPE_ADMIN = 1;
	public static final int TYPE_NORMAL = 2;
	
	@Column(name = "enable")
	private int enable = 1;
	
	@Column(name = "status")
	private int status = STATUS_NORMAL;
	
	public static final int STATUS_NORMAL = 0;
	public static final int STATUS_DELETE = 1;
	
	@Column(name = "org_id", length=36)
	private String orgId;
	
	@Column(name = "department_id", length=36)
	private String departmentId;
	
	@Column(name = "summary", length=200)
	private String summary;
	
	@Column(name = "style", length=20)
	private String style = MyApplicationContext.STYLE_DEFAULT;
	
	public static final int FROM_TYPE_INNER = 0;
	public static final int FROM_TYPE_OUTER = 1;
	
	@Column(name="from_type")
	private int fromType = FROM_TYPE_INNER;
	
	public static final int SEX_WOMAN = 0;
	public static final int SEX_MAN = 1;
	
	@Column(name="sex")
	private int sex = SEX_MAN;
	
	@Column(name = "tel", length=20)
	private String tel;
	
	@Column(name = "mobile", length=15)
	private String mobile;
	
	@Column(name = "email", length=50)
	private String email;
	
	@Transient
	private String personSay;
	
	/**
	 * 返回用户个性签名，移动端使用，冗余字段
	 * @return
	 */
	public String getPersonSay() {
		return personSay;
	}

	public void setPersonSay(String personSay) {
		this.personSay = personSay;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getFromType() {
		return fromType;
	}

	public void setFromType(int fromType) {
		this.fromType = fromType;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(long orderNum) {
		this.orderNum = orderNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getDepartmentId() {
		return departmentId;
	}
	
	private static final String DESC_NO_DEPT = "不属于任何部门";
	
	/**
	 * 返回部门名称
	 * @return
	 */
	public String getDepartmentName(){
		if(isSupervisor()){
			return StringUtils.EMPTY_STRING;
		}
		if(StringUtils.isEmpty(departmentId)){
			return DESC_NO_DEPT;
		}
		return DepartmentUtils.getOrgName(departmentId);
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public boolean enabled(){
		return (this.enable == 1);
	}
	
	public boolean isDeleted(){
		return (this.status == STATUS_DELETE);
	}
	
	public boolean isSupervisor(){
		return type == TYPE_SUPER;
	}
	
	public boolean isAdmin(){
		return type == TYPE_ADMIN;
	}
	/**
	 * 返回是否'外部用户'，如果是返回<code>true</code>
	 * @return
	 */
	public boolean isFromOuter(){
		return fromType == FROM_TYPE_OUTER;
	}
	
	public String toString(){
		return new StringBuilder().append("id=").append(id)
				.append(", loginId=").append(loginId)
				.append(", name=").append(name)
				.append(", order=").append(orderNum)
				.append(", type=").append(type)
				.append(", enabled=").append(enable)
				.append(", status=").append(status)
				.append(", org=").append(orgId)
				.append(", departId=").append(departmentId).toString();
	}

	public boolean equals(Object o){
		if(o == null) return false;
		if(o instanceof UserCoreEntity){
			if(((UserCoreEntity)o).getId().equals(this.getId()))
				return true;
		}
		return false;
	}
	
	public int hashCode(){
		return 31 + this.id.hashCode();
	}
	
	@Transient
	private String[] roleIds;

	public String[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	
	@Column(name = "org_name", length=100)
	private String orgName;

	/**
	 * 返回用户所在单位的名称
	 * @return
	 */
	public String getOrgName() {
		if(StringUtils.isNotEmpty(orgName)){
			return orgName;
		}
		if(StringUtils.isNotEmpty(orgId)){
			String s = DepartmentUtils.getOrgName(orgId);
			return s;
		}
		return StringUtils.EMPTY_STRING;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
}
