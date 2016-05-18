package com.walkersoft.system.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.app.ApplicationRuntimeException;
import com.walker.file.FileEngine;
import com.walker.file.FileMeta;
import com.walker.file.FileOperateException;
import com.walker.infrastructure.utils.NumberGenerator;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.OrgType;
import com.walkersoft.system.dao.DepartmentDao;
import com.walkersoft.system.entity.DepartmentEntity;

@Service("departmentManager")
public class DepartmentManagerImpl {

	@Autowired
	private DepartmentDao departmentDao;
	
	@Autowired
	private FileEngine fileEngine;
	
	public DepartmentEntity queryDepartment(String id){
		return departmentDao.get(id);
	}
	
	/**
	 * 保存单位
	 * @param entity
	 * @param files 要上传的附件集合
	 */
	public void execSave(DepartmentEntity entity, List<FileMeta> files){
		if(files != null){
			try {
				files.get(0).setGroup("test");
				fileEngine.writeFiles(files);
			} catch (FileOperateException e) {
				throw new ApplicationRuntimeException("file write failed in save department!", e);
			}
		}
		entity.setCreateTime(NumberGenerator.getSequenceNumber());
		entity.setOrderNum(departmentDao.getMaxOrderNum(entity));
		departmentDao.save(entity);
		
		//如果是子单位，更新父节点中，孩子数量字段
		String parentId = entity.getParentId();
		if(StringUtils.isNotEmpty(parentId) && !parentId.equals("0")){
			DepartmentEntity parent = departmentDao.get(entity.getParentId());
			parent.increaseChildSum();
			departmentDao.updateEntity(parent);
		}
	}
	
	/**
	 * 保存部门
	 * @param entity
	 */
	public void execSaveDepartment(DepartmentEntity entity){
		entity.setCreateTime(NumberGenerator.getSequenceNumber());
		entity.setOrderNum(departmentDao.getMaxOrderNum(entity));
		departmentDao.save(entity);
		/* 更新父节点中，孩子数量字段 */
		DepartmentEntity parent = departmentDao.get(entity.getParentId());
		parent.increaseChildSum();
		departmentDao.updateEntity(parent);
	}
	
	public DepartmentEntity execDelete(String id){
		DepartmentEntity current = departmentDao.get(id);
		if(current.getType() == DepartmentEntity.TYPE_DEPT){
			// 删除部门，需要更新上级节点中"孩子数量"字段
			DepartmentEntity parent = departmentDao.get(current.getParentId());
			parent.decreaseChildSum();
			departmentDao.updateEntity(parent);
		}
		current.setStatus(1);
		departmentDao.updateEntity(current);
		return current;
	}
	
	/**
	 * 真正从数据库删除机构信息
	 * @param id 给定的机构ID
	 * @return 返回上级机构ID，如果已经是单位，则返回<code>null</code>
	 */
	public String execEase(String id){
		String parentId = null;
		DepartmentEntity current = departmentDao.get(id);
		if(current.getType() == OrgType.Dept.getTypeValue()){
			// 删除部门，需要更新上级节点中"孩子数量"字段
			parentId = current.getParentId();
			DepartmentEntity parent = departmentDao.get(parentId);
			parent.decreaseChildSum();
			departmentDao.updateEntity(parent);
		}
		// 删除该机构
		departmentDao.removeById(id);
		return parentId;
	}
	
	/**
	 * 更新机构信息到数据库
	 * @param entity 从界面中得到的机构业务数据
	 * @return
	 */
	public DepartmentEntity execUpdate(DepartmentEntity entity){
		String id = entity.getId();
		assert (StringUtils.isNotEmpty(id));
		DepartmentEntity loaded = departmentDao.get(id);
		loaded.setName(entity.getName());
		loaded.setSimpleName(entity.getSimpleName());
		loaded.setSystem(entity.getSystem());
		loaded.setAttribute(entity.getAttribute());
		loaded.setAdministrate(entity.getAdministrate());
		loaded.setChargeMan(entity.getChargeMan());
		loaded.setManager(entity.getManager());
		loaded.setSummary(entity.getSummary());
		departmentDao.updateEntity(loaded);
		return loaded;
	}
}
