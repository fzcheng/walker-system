package com.walkersoft.mobile.dao;

import org.springframework.stereotype.Repository;

import com.walker.db.hibernate.SQLDaoSupport;
import com.walker.infrastructure.utils.NumberGenerator;
import com.walkersoft.mobile.entity.User;

/**
 * 移动端用户扩展信息管理DAO实现
 * @author shikeying
 * @date 2015-3-17
 *
 */
@Repository
public class MobileUserDao extends SQLDaoSupport<User> {

	public void execSaveUserPhoto(){
		
	}
	
	/**
	 * 更新用户头像信息，存在就更新photo字段，不存在就新建该记录
	 * @param userId
	 * @param photoId
	 */
	public void execUpdatePhotoId(String userId, String photoId){
		User user = this.get(userId);
		if(user == null){
			user = new User();
			user.setId(userId)
				.setCreateTime(NumberGenerator.getSequenceNumber())
				.setPhoto(photoId);
		} else {
			user.setPhoto(photoId);
		}
		this.save(user);
	}
}
