package com.walkersoft.flow.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.db.page.support.GenericPager;
import com.walker.flow.meta.ProcessDefine;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.flow.dao.ProcessDefineManageDao;
import com.walkersoft.flow.pojo.ProcessIdentifier;

@Service("processDefineManager")
public class ProcessDefineManagerImpl {

	@Autowired
	private ProcessDefineManageDao processDefineManageDao;
	
	/**
	 * 返回流程定义管理列表
	 * @return
	 */
	public GenericPager<ProcessIdentifier> queryPagedProcessIdentifierList(){
		return processDefineManageDao.getPagedProcessIdentifierList();
	}
	
	/**
	 * 返回已经存在的流程标识
	 * @param identifier
	 * @return
	 */
	public Object queryExistIdentifier(String identifier){
		return processDefineManageDao.getProcessDefineObj(identifier);
	}
	
	public ProcessDefine queryProcessDefine(String processDefineId){
		return processDefineManageDao.getProcessDefine(processDefineId);
	}
	
	public void execSaveProcess(ProcessDefine processDefine){
		int maxId = processDefineManageDao.getMaxProcessId();
		processDefine.setId(String.valueOf(maxId));
		processDefineManageDao.insertProcessDefine(processDefine);
		processDefineManageDao.insertIdentifier(processDefine.getInterIdentifier()
				, processDefine.getId());
	}
	
	/**
	 * 修改流程概要信息
	 * @param processDefine
	 */
	public void execUpdateProcess(ProcessDefine processDefine){
		String id = processDefine.getId();
		if(StringUtils.isEmpty(id))
			throw new IllegalArgumentException("id is required.");
		processDefineManageDao.updateProcessDefine(processDefine);
	}
	
	/**
	 * 废弃流程，删除标记，废弃的流程不允许做其他更新操作，只能查看或被删除。
	 * @param processDefineId
	 */
	public void execDeprecateProcess(String processDefineId){
		processDefineManageDao.updateProcessDeprecated(processDefineId);
	}
	
	/**
	 * 彻底删除流程，包括历史版本信息
	 * @param processDefineId 流程定义ID
	 */
	public void execDeleteProcess(String processDefineId){
		assert (StringUtils.isNotEmpty(processDefineId));
		String identifier = processDefineManageDao.getIdentifier(processDefineId);
		processDefineManageDao.deleteProcessDefine(identifier);
		processDefineManageDao.deleteProcessVersion(identifier);
	}
}
