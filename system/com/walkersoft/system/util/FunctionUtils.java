package com.walkersoft.system.util;

import java.util.ArrayList;
import java.util.List;

import com.walker.infrastructure.utils.ClassUtils;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.system.pojo.FunctionGroup;
import com.walkersoft.system.pojo.FunctionObj;

public class FunctionUtils {

	/**
	 * 把“带URL地址的子系统”转换成菜单组，加入到给定的集合中。
	 * @param systemWithUrlList 带URL子系统ID集合
	 * @param destFunctionGroup 目的菜单组集合
	 */
	public static final void addSystemToDestFuncGroup(List<String> systemWithUrlList
			, List<FunctionGroup> destFunctionGroup){
//		List<String> systemWithUrlList = functionCacheProvider.getSystemWithUrl();
		List<FunctionGroup> dummyGroupWithUrl = FunctionUtils.createDummyGroupWithUrlSystems(systemWithUrlList);
		if(dummyGroupWithUrl != null){
			destFunctionGroup.addAll(dummyGroupWithUrl);
		}
	}
	/**
	 * 对于已经存在有带URL的子系统菜单，需要构造“虚拟的菜单组”，使系统能够显示该子系统
	 * @param systemWithUrl 带URL地址的子系统ID集合
	 * @return
	 */
	public static final List<FunctionGroup> createDummyGroupWithUrlSystems(List<String> systemWithUrl){
		if(!StringUtils.isEmptyList(systemWithUrl)){
			/* 对于已经存在有带URL的子系统菜单，需要构造“虚拟的菜单组”，使系统能够显示该子系统 */
			/* 时克英 2014-12-29 */
			List<FunctionGroup> result = new ArrayList<FunctionGroup>();
			FunctionGroup fg = null;
			int i = 1;
			for(String funcId : systemWithUrl){
				fg = new FunctionGroup();
				fg.setParentId(funcId);
				fg.setId("dummy"+i);
				fg.setName("虚拟菜单组");
				fg.setVisible(false);
				i++;
				result.add(fg);
			}
			return result;
		}
		return null;
	}
	
	public static FunctionObj createFunctionObj(String id, String parentId
			, String name, int orderNum, String url){
		FunctionObj fo = new FunctionObj();
		fo.setId(id);
		fo.setParentId(parentId);
		fo.setName(name);
		fo.setOrderNum(orderNum);
		fo.setUrl(url);
		return fo;
	}
	
	public static FunctionGroup createFunctionGroup(String id, String name
			, String parentId, int orderNum){
		FunctionGroup fg = new FunctionGroup();
		fg.setId(id);
		fg.setName(name);
		fg.setParentId(parentId);
		fg.setOrderNum(orderNum);
		return fg;
	}
	
	/**
	 * 返回超级管理员特有的权限分组集合。
	 * @return
	 */
	public static List<FunctionGroup> getSupervisorGroups(){
		if(supervisorGroups.size() == 0){
			FunctionGroup dbGroup = createFunctionGroup("db_grp", "数据库管理", SUPERVISOR_SYS_ID, 1);
			FunctionObj dbExport = createFunctionObj("db_grp_exp", "db_grp", "数据备份"
					, 1, "/supervisor/db/export.do");
			FunctionObj dbImport = createFunctionObj("db_grp_imp", "db_grp", "数据导入"
					, 2, "/supervisor/db/import.do");
			dbGroup.addItem(dbExport);
			dbGroup.addItem(dbImport);
			supervisorGroups.add(dbGroup);
			
			FunctionGroup securityGroup = createFunctionGroup("security_grp", "安全管理", SUPERVISOR_SYS_ID, 2);
			
			// 加入授权管理功能
			if(ClassUtils.isPresent("com.walkersoft.deploy.action.AccreditAction", FunctionUtils.class.getClassLoader())){
				FunctionObj accredit = createFunctionObj("accredit"
						, "security_grp", "授权文件", 1, "/supervisor/accredit/index.do");
				securityGroup.addItem(accredit);
			}
			
			// 加入部署检测功能
			FunctionObj deploy = createFunctionObj("deploy"
					, "security_grp", "部署检测", 2, "/supervisor/deploy.do");
			securityGroup.addItem(deploy);
			
			supervisorGroups.add(securityGroup);
		}
		return supervisorGroups;
	}
	
	private static final List<FunctionGroup> supervisorGroups = new ArrayList<FunctionGroup>();
	
	public static final String SUPERVISOR_SYS_ID = "supervisor";
}
