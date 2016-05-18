package com.walkersoft.mobile.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户扩展信息，手机端需要
 * @author shikeying
 * @date 2015-3-17
 *
 */
@Entity
@Table(name="s_mobile_user")
public class User {

	@Id
	@Column(name = "id", length=50)
	private String id;
	
	@Column(name = "create_time")
	private long createTime;
	
	@Column(name = "photo", length=36)
	private String photo;
	
	@Column(name = "person_say", length=100)
	private String personSay;

	public String getId() {
		return id;
	}

	public User setId(String id) {
		this.id = id;
		return this;
	}

	public long getCreateTime() {
		return createTime;
	}

	public User setCreateTime(long createTime) {
		this.createTime = createTime;
		return this;
	}

	/**
	 * 返回用户照片的资源引用ID
	 * @return
	 */
	public String getPhoto() {
		return photo;
	}

	public User setPhoto(String photo) {
		this.photo = photo;
		return this;
	}

	/**
	 * 返回个性签名
	 * @return
	 */
	public String getPersonSay() {
		return personSay;
	}

	public User setPersonSay(String personSay) {
		this.personSay = personSay;
		return this;
	}
	
	public String toString(){
		return new StringBuilder().append("[id=").append(id)
				.append(", photo=").append(photo)
				.append(", personSay=").append(personSay)
				.append("]").toString();
	}
	
}
