package com.walkersoft.oa.dao;

import org.springframework.stereotype.Repository;

import com.walker.db.Sorts;
import com.walker.db.Sorts.Sort;
import com.walker.db.hibernate.SQLDaoSupport;
import com.walker.db.page.support.GenericPager;
import com.walkersoft.oa.entity.CommLeave;

@Repository("leaveDao")
public class LeaveDao extends SQLDaoSupport<CommLeave> {

private Sort timeSort = Sorts.DESC().setField("createTime");
	
	private static final String HQL_PAGE_LIST = "select leave from CommLeave leave where leave.userId = ?";
	
	public GenericPager<CommLeave> queryPageList(String userId){
		return this.queryForpage(HQL_PAGE_LIST, new Object[]{userId}, timeSort);
	}
}
