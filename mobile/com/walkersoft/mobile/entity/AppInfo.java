package com.walkersoft.mobile.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="s_mobile_app")
public class AppInfo {

	/**
	 * 是否强制升级
	 */
	public static final int FORCE_UPDATE_YES = 1;
	public static final int FORCE_UPDATE_NO = 0;
	
	@Id
	@Column(name = "id", length=60)
	private String id;
	
	@Column(name = "create_time")
	private long createTime;
	
	@Column(name = "name", length=90)
	private String name;
	
	@Column(name = "summary", length=255)
	private String summary;
	
	@Column(name = "release_version")
	private int releaseVersion = 0;
	
	@Column(name = "release_size")
	private long releaseSize = 0;
	
	@Column(name = "release_fileid", length=36)
	private String releaseFileId;
	
	@Column(name = "release_force")
	private int releaseForce = FORCE_UPDATE_NO;
	
	@Column(name = "release_text", length=255)
	private String releaseText;

	public String getId() {
		return id;
	}

	public AppInfo setId(String id) {
		this.id = id;
		return this;
	}

	public long getCreateTime() {
		return createTime;
	}

	public AppInfo setCreateTime(long createTime) {
		this.createTime = createTime;
		return this;
	}

	public String getName() {
		return name;
	}

	public AppInfo setName(String name) {
		this.name = name;
		return this;
	}

	public String getSummary() {
		return summary;
	}

	public AppInfo setSummary(String summary) {
		this.summary = summary;
		return this;
	}

	public int getReleaseVersion() {
		return releaseVersion;
	}

	public AppInfo setReleaseVersion(int releaseVersion) {
		this.releaseVersion = releaseVersion;
		return this;
	}

	public long getReleaseSize() {
		return releaseSize;
	}

	public AppInfo setReleaseSize(long releaseSize) {
		this.releaseSize = releaseSize;
		return this;
	}

	public String getReleaseFileId() {
		return releaseFileId;
	}

	public AppInfo setReleaseFileId(String releaseFileId) {
		this.releaseFileId = releaseFileId;
		return this;
	}

	public int getReleaseForce() {
		return releaseForce;
	}

	public AppInfo setReleaseForce(int releaseForce) {
		this.releaseForce = releaseForce;
		return this;
	}

	public String getReleaseText() {
		return releaseText;
	}

	public AppInfo setReleaseText(String releaseText) {
		this.releaseText = releaseText;
		return this;
	}

	public String toString(){
		return new StringBuilder().append("[id=").append(id)
				.append(", name=").append(name)
				.append(", releaseVersion=").append(releaseVersion)
				.append("]").toString();
	}
}
