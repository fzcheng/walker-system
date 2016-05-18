package com.walkersoft.oa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.walker.infrastructure.utils.DateUtils;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.util.DepartmentUtils;

/**
 * 请假表对应实体
 * @author shikeying
 *
 */
@Entity
@Table(name = "comm_leave")
public class CommLeave {

	@Id
	@Column(name="id")
	private long id;
	
	@Column(name = "create_time")
	private long createTime;
	
	@Column(name = "user_id", length = 36)
	private String userId;
	
	@Column(name = "user_name", length = 20)
	private String userName;
	
	@Column(name = "type", length = 36)
	private String type;
	
	@Column(name="date_from")
	private int dateFrom;
	
	@Column(name="date_to")
	private int dateTo;
	
	@Column(name = "part_start", length = 36)
	private String partStart;
	
	@Column(name = "part_end", length = 36)
	private String partEnd;
	
	@Column(name="request_days")
	private int requestDays = 0;
	
	@Column(name = "summary", length = 150)
	private String summary;
	
	@Column(name = "request_process_id", length = 36)
	private String requestProcessId;
	
	@Column(name="after")
	private int after = 0;
	
	@Column(name="after_time")
	private long afterTime;
	
	@Column(name="after_date")
	private int afterDate;
	
	@Column(name = "after_part", length = 36)
	private String afterPart;
	
	@Column(name="result_days")
	private int resultDays;
	
	@Column(name = "after_process_id", length = 36)
	private String afterProcessId;
	
	@Column(name="done")
	private int done = 0;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDateFrom() {
		return dateFrom;
	}
	public String getDateFromShow(){
		if(dateFrom == 0) return StringUtils.EMPTY_STRING;
		return DateUtils.toShowDate(dateFrom);
	}

	public void setDateFrom(int dateFrom) {
		this.dateFrom = dateFrom;
	}

	public int getDateTo() {
		return dateTo;
	}

	public void setDateTo(int dateTo) {
		this.dateTo = dateTo;
	}

	public String getPartStart() {
		return partStart;
	}

	public void setPartStart(String partStart) {
		this.partStart = partStart;
	}

	public String getPartEnd() {
		return partEnd;
	}

	public void setPartEnd(String partEnd) {
		this.partEnd = partEnd;
	}

	public int getRequestDays() {
		return requestDays;
	}

	public void setRequestDays(int requestDays) {
		this.requestDays = requestDays;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getRequestProcessId() {
		return requestProcessId;
	}

	public void setRequestProcessId(String requestProcessId) {
		this.requestProcessId = requestProcessId;
	}

	public int getAfter() {
		return after;
	}

	public void setAfter(int after) {
		this.after = after;
	}

	public long getAfterTime() {
		return afterTime;
	}

	public void setAfterTime(long afterTime) {
		this.afterTime = afterTime;
	}

	public int getAfterDate() {
		return afterDate;
	}

	public void setAfterDate(int afterDate) {
		this.afterDate = afterDate;
	}

	public String getAfterPart() {
		return afterPart;
	}

	public void setAfterPart(String afterPart) {
		this.afterPart = afterPart;
	}

	public int getResultDays() {
		return resultDays;
	}

	public void setResultDays(int resultDays) {
		this.resultDays = resultDays;
	}

	public String getAfterProcessId() {
		return afterProcessId;
	}

	public void setAfterProcessId(String afterProcessId) {
		this.afterProcessId = afterProcessId;
	}

	public int getDone() {
		return done;
	}

	public void setDone(int done) {
		this.done = done;
	}
	
	public String getUserDeptName(){
		return DepartmentUtils.getUserOrgName(userId);
	}
}
