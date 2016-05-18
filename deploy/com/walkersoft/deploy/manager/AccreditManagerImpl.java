package com.walkersoft.deploy.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.app.ApplicationRuntimeException;
import com.walker.db.Sorts;
import com.walker.db.Sorts.Sort;
import com.walker.db.page.support.GenericPager;
import com.walker.file.FileEngine;
import com.walker.file.FileMeta;
import com.walker.file.FileOperateException;
import com.walker.file.support.SimpleFileMeta;
import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.NumberGenerator;
import com.walker.infrastructure.utils.StringUtils;
import com.walker.security.admin.FileLisenceGenerator;
import com.walker.security.admin.LisenceGenerator;
import com.walkersoft.deploy.entity.AccreditEntity;
import com.walkersoft.system.dao.SystemBaseDao;

/**
 * 授权管理service事务
 * @author shikeying
 *
 */
@Service("accreditManager")
public class AccreditManagerImpl {

	private LisenceGenerator lisence = new FileLisenceGenerator();
	
	@Autowired
	private FileEngine fileEngine;
	
	@Autowired
	private SystemBaseDao systemBaseDao;
	
	private final Sort timeSort = Sorts.DESC().setField("createTime");
	
	public GenericPager<AccreditEntity> queryAccreditList(){
		return systemBaseDao.queryForEntityPageByType(timeSort, AccreditEntity.class);
	}
	
	public void execSave(AccreditEntity entity) throws Exception{
		Assert.notNull(entity);
		entity.setId(NumberGenerator.getSequenceNumber());
		entity.setCreateTime(entity.getId());
		
		// 协议输入参数
		StringBuilder input = new StringBuilder();
		input.append("walkersoft");
		input.append(",");
		input.append(entity.getAuthType());
		input.append(",");
		input.append(StringUtils.isEmpty(entity.getMacAddress())? "none":entity.getMacAddress());
		input.append(",");
		input.append(entity.getStartTime());
		input.append(",");
		input.append(entity.getEndTime());
		
		byte[] fileData = lisence.generate(input.toString());
		FileMeta fileMeta = new SimpleFileMeta();
		fileMeta.setFilename("walkersoft-sn-" + entity.getId() + ".bin");
		fileMeta.setContentType("application/bin");
		fileMeta.setFileExt("bin");
		fileMeta.setContent(fileData);
		fileMeta.setGroup("accredit");
		
		List<FileMeta> files = new ArrayList<FileMeta>();
		files.add(fileMeta);
		
		try {
			List<String> ids = fileEngine.writeFiles(files);
			entity.setFileId(ids.get(0));
		} catch (FileOperateException e) {
			throw new ApplicationRuntimeException();
		}
		systemBaseDao.save(entity);
	}
}
