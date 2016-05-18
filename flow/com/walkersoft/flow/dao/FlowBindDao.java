package com.walkersoft.flow.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.walker.db.PropertyEntry;
import com.walker.db.Sorts;
import com.walker.db.Sorts.Sort;
import com.walker.db.hibernate.SQLDaoSupport;
import com.walker.db.page.support.GenericPager;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.flow.entity.FlowBindEntity;

@Repository("flowBindDao")
public class FlowBindDao extends SQLDaoSupport<FlowBindEntity> {

	private static final String HQL_PAGE_BINDLIST = "select entity from FlowBindEntity entity where entity.type = ?";
	
//	private static final String HQL_GET_EXIST_BIND = "select entity from FlowBindEntity entity where entity.type = ? and entity.processDefineId = ? and entity.businessType = ?";
	
	private Sort timeSort = Sorts.DESC().setField("createTime");
	
	public GenericPager<FlowBindEntity> queryFlowBindPager(int type){
		return this.queryForpage(HQL_PAGE_BINDLIST, new Object[]{type}, new Sort[]{timeSort});
	}
	
	public GenericPager<FlowBindEntity> queryFlowBindPager(){
		return this.queryForEntityPage(timeSort);
	}
	
	/**
	 * 根据条件查询已经存在的绑定记录
	 * @param bindType 绑定类型
	 * @param processDefineId 流程定义ID
	 * @param businessType 业务类别ID
	 * @return 返回当前已经存在的绑定记录
	 */
	public FlowBindEntity querySingleFlowBind(int bindType, String processDefineId, String businessType){
		List<FlowBindEntity> list = this.findBy(new PropertyEntry[]{PropertyEntry.createEQ("type", bindType)
				, PropertyEntry.createEQ("processDefineId", processDefineId)
				, PropertyEntry.createEQ("businessType", businessType)}, null);
		if(!StringUtils.isEmptyList(list)){
			return list.get(0);
		} else
			return null;
	}
	
	/**
	 * 根据条件检索绑定流程集合
	 * @param bindType 绑定类型，如：单位(0)
	 * @param bindId 绑定的业务ID，如：单位ID
	 * @param businessType 流程业务分类ID
	 * @return
	 */
	public List<FlowBindEntity> queryBindFlows(int bindType, String bindId, String businessType){
		return this.findBy(new PropertyEntry[]{PropertyEntry.createEQ("type", bindType)
				, PropertyEntry.createEQ("bindId", bindId)
				, PropertyEntry.createEQ("businessType", businessType)}, null);
	}
}
