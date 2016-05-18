package com.walkersoft.oa.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.db.page.support.GenericPager;
import com.walkersoft.oa.dao.LeaveDao;
import com.walkersoft.oa.entity.CommLeave;

@Service("leaveManager")
public class LeaveManagerImpl {

	@Autowired
	private LeaveDao leaveDao;
	
	public GenericPager<CommLeave> queryPageList(String userId){
		return leaveDao.queryPageList(userId);
	}
}
