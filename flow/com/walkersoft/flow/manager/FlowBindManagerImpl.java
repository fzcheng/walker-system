package com.walkersoft.flow.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.app.ApplicationRuntimeException;
import com.walker.db.page.support.GenericPager;
import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.NumberGenerator;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.flow.dao.FlowBindDao;
import com.walkersoft.flow.entity.FlowBindEntity;

@Service("flowBindManager")
public class FlowBindManagerImpl {

	@Autowired
	private FlowBindDao flowBindDao;
	
	public GenericPager<FlowBindEntity> queryFlowBindPager(int type){
		return flowBindDao.queryFlowBindPager(type);
	}
	
	public GenericPager<FlowBindEntity> queryFlowBindPager(){
		return flowBindDao.queryFlowBindPager();
	}
	
	/**
	 * 外部模块调用方法：
	 * <pre>
	 * 查询绑定到-单位-的流程集合
	 * </pre>
	 * @param orgId 单位ID
	 * @param businessType 流程业务类型，如：请假、项目管理，此参数在系统中定义。
	 * @return
	 */
	public List<FlowBindEntity> queryBindOrgFlows(String orgId, String businessType){
		Assert.hasText(orgId);
		if(StringUtils.isEmpty(businessType)){
			businessType = "0";
		}
		return flowBindDao.queryBindFlows(FlowBindEntity.TYPE_ORG, orgId, businessType);
	}
	
	public void execSave(FlowBindEntity entity){
		Assert.notNull(entity);
		FlowBindEntity exist = flowBindDao.querySingleFlowBind(entity.getType()
				, entity.getProcessDefineId(), entity.getBusinessType());
		if(exist != null){
			throw new ApplicationRuntimeException("已经存在绑定数据");
		}
		long time = NumberGenerator.getSequenceNumber();
		entity.setId(time);
		entity.setCreateTime(time);
		flowBindDao.save(entity);
	}
	
	public void execRemove(long id){
		flowBindDao.removeById(id);
	}
}
