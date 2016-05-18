package com.walkersoft.application.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;

import com.walker.app.ApplicationRuntimeException;
import com.walker.infrastructure.cache.AbstractCacheProvider;
import com.walker.infrastructure.cache.support.Cache;
import com.walker.infrastructure.cache.tree.CacheTreeNode;
import com.walker.infrastructure.cache.tree.DefaultCacheTreeNode;
import com.walker.infrastructure.core.filter.ConvertDataException;
import com.walker.infrastructure.core.filter.Convertable;
import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.StringUtils;
import com.walker.web.ui.JsonSelectConvertor;
import com.walkersoft.system.dao.FunctionDao;
import com.walkersoft.system.pojo.FunctionGroup;
import com.walkersoft.system.pojo.FunctionObj;
import com.walkersoft.system.pojo.FunctionPointer;
import com.walkersoft.system.util.FuncGrpComparator;
import com.walkersoft.system.util.FuncObjComparator;
import com.walkersoft.system.util.FunctionUtils;

public class FunctionCacheProvider extends AbstractCacheProvider<FunctionObj> {

	@Autowired
	private FunctionDao functionDao;
	
	//菜单组List
	private List<FunctionGroup> funcGroupList = new ArrayList<FunctionGroup>();
	//功能点Map,格式:<功能点ID, 功能点对象>
	public Map<String, FunctionPointer> funcPointMap = new HashMap<String, FunctionPointer>();
	
	private List<FunctionObj> allFunctions = new ArrayList<FunctionObj>();
	
	/* 所有子系统记录 */
	private Map<String, FunctionObj> allSystems = new TreeMap<String, FunctionObj>();
	
	private static final int F_TYPE_SYS = -1;
	
	/* 是否注解已扫描完成，功能URL，如果系统启动完成并且，菜单已经与注解中URL比较过， */
	/* 则设置为true,因为系统需要根据jar包是否存在来实现菜单的显示。第一个登录的用户会触发该操作。 */
	/* 2014-5-22 时克英修改*/
	private AtomicBoolean finishScanAnnotation = new AtomicBoolean(false);
	private final Object lock = new Object();
	
	@Override
	public String getProviderName() {
		// TODO Auto-generated method stub
		return "system.cache.function";
	}

	@Override
	public Class<FunctionObj> getProviderType() {
		// TODO Auto-generated method stub
		return FunctionObj.class;
	}

	@Override
	public void destroy() throws Exception {
		super.destroy();
		allSystems.clear();
		allFunctions.clear();
		funcPointMap.clear();
		funcGroupList.clear();
	}
	
	@Override
	protected int loadDataToCache(Cache cache) {
		// TODO Auto-generated method stub
		if(functionDao == null)
			throw new IllegalStateException("functionDao is required!");
		List<FunctionObj> list = functionDao.getAllFunctions();
		allFunctions.addAll(list);
		
		if(list != null){
			for(FunctionObj entity : list){
				cache.put(entity.getId(), entity);
			}
			
			//功能点记录加载到缓存
			List<Map<String, Object>> pointerList = functionDao.getPointerList();
			if(pointerList != null)
				getAllPointerMap(pointerList);
			
			funcGroupList = getAllFunctionObjList(list);
			for(FunctionGroup _fg : funcGroupList){
				logger.info("-----------加载模块：" + _fg.getName());
			}
			// 找到子系统记录
			for(FunctionObj obj : list){
				if(obj.getType() == F_TYPE_SYS){
					allSystems.put(obj.getId(), obj);
				}
			}
			return list.size();
		}
		return 0;
	}

	private void getAllPointerMap(List<Map<String, Object>> pointerList){
		for(Map<String, Object> _p : pointerList){
			FunctionPointer fp = new FunctionPointer();
			fp.setId(_p.get("ID").toString());
			fp.setName(_p.get("NAME").toString());
			fp.setOrderNum(Integer.parseInt(_p.get("ORDER_NUM").toString()));
			fp.setFunctionId(_p.get("FUNCTION_ID").toString());
			if(_p.get("URL") != null)
				fp.setUrl(_p.get("URL").toString());
			funcPointMap.put(fp.getId(), fp);
		}
	}
	
	private List<FunctionGroup> getAllFunctionObjList(List<FunctionObj> funcPoList){
		//菜单组Map
		Map<String, FunctionGroup> funcGroupMap = new HashMap<String, FunctionGroup>();
		//菜单组List
		List<FunctionGroup> _funcGroupList = new ArrayList<FunctionGroup>();
		//菜单项List
		List<FunctionObj> itemList = new ArrayList<FunctionObj>();
		
		//从缓存加载功能点Map
//		Map<String, FunctionPointer> funcPointMap = funcPointMap;
		
		for(FunctionObj _funcPo : funcPoList){
			if(_funcPo.getType() == 0){
				//功能组
				FunctionGroup fg = convertFuncGroup(_funcPo);
				if(fg == null)
					throw new NullPointerException();
				funcGroupMap.put(fg.getId(), fg);
			} else {
				//功能项
				itemList.add(convertItem(_funcPo));
			}
		}
		//菜单项列表排序
		Collections.sort(itemList, new FuncObjComparator());
		
		//组合菜单组合菜单项
		for(FunctionObj _fo : itemList){
			FunctionGroup group = funcGroupMap.get(_fo.getParentId());
			if(group != null){
				group.addItem(_fo);
			}
		}
		
		for(Map.Entry<String, FunctionGroup> entry : funcGroupMap.entrySet()){
			_funcGroupList.add(entry.getValue());
			//菜单组排序
			Collections.sort(_funcGroupList, new FuncGrpComparator());
		}
		
		return _funcGroupList;
	}
	
	private static FunctionGroup convertFuncGroup(FunctionObj _funcPo){
		if(_funcPo != null){
			FunctionGroup fg = new FunctionGroup();
			fg.setId(_funcPo.getId());
			fg.setName(_funcPo.getName());
			fg.setOrderNum(_funcPo.getOrderNum());
			fg.setParentId(_funcPo.getParentId());
			return fg;
		}
		return null;
	}
	
	private FunctionObj convertItem(FunctionObj _funcPo){
		if(_funcPo != null){
			FunctionObj fo = new FunctionObj();
			fo.setId(_funcPo.getId());
			fo.setName(_funcPo.getName());
			fo.setOrderNum(_funcPo.getOrderNum());
			if(StringUtils.isNotEmpty(_funcPo.getPointer())){
				String pointerIds = _funcPo.getPointer();
				fo.setPointer(pointerIds);
				String[] pointers = pointerIds.split(",");
				for(String _pointerId : pointers){
					fo.addPointer(funcPointMap.get(_pointerId));
				}
			}
			fo.setUrl(_funcPo.getUrl() == null ? "" : _funcPo.getUrl());
			fo.setRun(_funcPo.isAvailable());
			fo.setParentId(_funcPo.getParentId());
			return fo;
		}
		return null;
	}
	
	public List<FunctionObj> getAllFunctions() {
		if(funcGroupList != null){
			List<FunctionObj> result = new ArrayList<FunctionObj>();
			for(FunctionGroup fg : funcGroupList){
				if(fg.getItemList() != null){
					result.addAll(fg.getItemList());
//					for(FunctionObj fo : fg.getItemList())
//						result.add(fo);
				}
			}
			return result;
		}
		return null;
	}

	/**
	 * 返回用户可用的所有菜单组</p>
	 * 该方法在加入'子系统'之前使用，即：仅存在两级菜单时使用。
	 * @return
	 */
	public List<FunctionGroup> getFunctionGroupList(){
		return funcGroupList;
	}
	
	/**
	 * 根据子系统ID，返回所有菜单组集合</p>
	 * 该方法在使用三级菜单时使用。
	 * @param sysId
	 * @return
	 */
	public List<FunctionGroup> getFunctionGroupList(String sysId){
		assert (StringUtils.isNotEmpty(sysId));
		if(funcGroupList.size() <= 0) return null;
		List<FunctionGroup> result = new ArrayList<FunctionGroup>(8);
		for(FunctionGroup fg : funcGroupList){
			if(fg.getParentId().equals(sysId)){
				result.add(fg);
			}
		}
		return result;
	}
	
	/**
	 * 组合当前用户能用的菜单组
	 * @param userFuncs 从数据库中加载过的用户能用的功能集合，包括：子系统、菜单项
	 * @param systemWithUrl 带URL地址的子系统菜单ID集合
	 */
	public List<FunctionGroup> combinUserFuncGroup(Map<String, List<String>> userFuncs){
		
		// 初始化最终菜单，过滤掉没有加载的模块
		this.filterUnloadModulesMenu();
				
		if(userFuncs == null || userFuncs.size() <= 0){
			return null;
		}
		
		//包含用户菜单的新列表
		List<FunctionGroup> userMenuGroup = new ArrayList<FunctionGroup>();
		
		//临时列表，存放功能组ID，便于检查，里面是否已经存在了用户包含的功能组
		List<String> tempLst = new ArrayList<String>();
		
		List<FunctionGroup> tempFuncGroupLst = funcGroupList;
		for(FunctionGroup _fg : tempFuncGroupLst){
//			System.out.println("-----------count=" + count + ", _fg=" + _fg.getName());
			if(_fg.getItemList() != null){
				for(FunctionObj _fo : _fg.getItemList()){
//					System.out.println("-----菜单项：" + _fo.getName());
					List<String> pList = userFuncs.get(_fo.getId());
					//存在这个菜单项
					if(pList != null){
						if(!tempLst.contains(_fg.getId())){
							//说明userMenuGroup中没有这个功能组了
							tempLst.add(_fg.getId());
							
							FunctionGroup fgroup = new FunctionGroup();
							fgroup.setId(_fg.getId());
							fgroup.setName(_fg.getName());
							fgroup.setOrderNum(_fg.getOrderNum());
							fgroup.setParentId(_fg.getParentId());
							
							setUserGroupFunc(_fo, userFuncs, fgroup, pList);
							
							userMenuGroup.add(fgroup);
//							System.out.println("----------放入了菜单组：" + userMenuGroup.get(count).getName());
							
							
						} else {
							//说明userMenuGroup里面已经有此功能组
//							System.out.println("--------count=" + count);
//							System.out.println("count=" + count + ",---------- 已经存在了功能组：" + userMenuGroup.get(count).getName());
							FunctionGroup fgroup = userMenuGroup.get(tempLst.size()-1);
							setUserGroupFunc(_fo, userFuncs, fgroup, pList);
						}
					}
				}
			}
		}
		return userMenuGroup;
	}
	
	/**
	 * 根据SpringMVC注解中的扫描情况，过滤掉不存在的菜单URL，避免出现有菜单，但点击报错的情况。
	 */
	private void filterUnloadModulesMenu(){
		if(!finishScanAnnotation.get()){
			synchronized (lock) {
				// 删除的功能列表
				List<String> removedList = new ArrayList<String>();
				if(funcGroupList != null && funcGroupList.size() > 0){
					// 1-删除无用的菜单项
					FunctionObj fo = null;
					for(FunctionGroup fg : funcGroupList){
						if(StringUtils.isEmptyList(fg.getItemList())){
							// 如果组中不包含任何菜单项，忽略
							continue;
						}
						for(Iterator<FunctionObj> it = fg.getItemList().iterator(); it.hasNext();){
							fo = it.next();
							if(!fo.isFinishScanAnnotation()){
								removedList.add(fo.getId());
								it.remove(); // 没有与注解匹配的菜单都要去掉，说明没有此jar包!
							}
						}
					}
//					// 2-菜单组中没有菜单的也要把此'菜单组'去掉
//					FunctionGroup fg = null;
//					for(Iterator<FunctionGroup> it2 = funcGroupList.iterator(); it2.hasNext();){
//						fg = it2.next();
//						if(fg.getItemList() == null || fg.getItemList().size() == 0){
//							removedList.add(fg.getId());
//							it2.remove();
//						}
//					}
//					// 3-更新子系统记录
//					Map<String, FunctionObj> _systemMap = new HashMap<String, FunctionObj>(8);
//					String _systemId = null;
//					FunctionObj _system = null;
//					for(FunctionGroup group : funcGroupList){
//						_systemId = group.getParentId();
//						_system = allSystems.get(_systemId);
//						if(_system == null)
//							throw new ApplicationRuntimeException("not found system: " + _systemId);
//						if(_systemMap.get(_systemId) == null){
//							_systemMap.put(_systemId, _system);
//						}
//					}
//					if(_systemMap.size() == 0)
//						throw new ApplicationRuntimeException("no system be loaded!");
//					if(allSystems.size() > _systemMap.size()){
//						for(String _id: allSystems.keySet()){
//							if(_systemMap.get(_id) == null){
//								removedList.add(_id); // 被删除的子系统ID
//							}
//						}
//					}
//					allSystems = _systemMap;
					
					// 4-把<code>allFunctions</code>中没有被加载的菜单也同时去掉
					if(removedList.size() > 0 && allFunctions != null){
						FunctionObj functionObj = null;
						for(Iterator<FunctionObj> it = allFunctions.iterator(); it.hasNext();){
							functionObj = it.next();
							if(removedList.contains(functionObj.getId())){
								logger.debug("删除菜单: " + functionObj);
								it.remove();
							}
						}
					}
				}
				
				// 加上带有URL地址的子系统菜单
				List<String> systemWithUrl = this.getSystemWithUrl();
				List<FunctionGroup> dummyGroupWithUrl = FunctionUtils.createDummyGroupWithUrlSystems(systemWithUrl);
				if(dummyGroupWithUrl != null){
					funcGroupList.addAll(dummyGroupWithUrl);
				}
				
				finishScanAnnotation.compareAndSet(false, true);
				logger.info("......执行了菜单功能的最终过滤处理: " + allSystems);
			}
		}
	}
	
	private void setUserGroupFunc(FunctionObj _fo
			, Map<String, List<String>> userFuncs
			, FunctionGroup fgroup
			, List<String> pList){
		FunctionObj fobj = new FunctionObj();
		fobj.setId(_fo.getId());
		fobj.setName(_fo.getName());
		fobj.setOrderNum(_fo.getOrderNum());
		fobj.setParentId(_fo.getParentId());
		fobj.setRun(_fo.isAvailable());
		fobj.setUrl(_fo.getUrl());
//		fobj.setPointerList(null);
		
		//用户功能点
		if(pList.size() > 0){
			for(String _pt : pList){
				FunctionPointer funcPoint = funcPointMap.get(_pt);
				fobj.addPointer(funcPoint);
			}
		}
		fgroup.addItem(fobj);
	}
	
	/**
	 * 返回功能名称
	 * @param functionId 功能ID
	 * @return
	 */
	public String getFunctionName(String functionId){
		if(StringUtils.isEmpty(functionId))
			return StringUtils.EMPTY_STRING;
		return this.getCacheData(functionId).getName();
	}
	
	/**
	 * 返回功能URL
	 * @param functionId
	 * @return
	 */
	public String getFunctionUrl(String functionId){
		assert (StringUtils.isNotEmpty(functionId));
		FunctionObj f = this.getCacheData(functionId);
		if(f == null) throw new NullPointerException("not found function in cache: " + functionId);
		return f.getUrl();
	}
	
	/**
	 * 返回功能点URL
	 * @param pointerId
	 * @return
	 */
	public String getPointerUrl(String pointerId){
		assert (StringUtils.isNotEmpty(pointerId));
		FunctionPointer fp = funcPointMap.get(pointerId);
		if(fp == null) throw new NullPointerException("not found pointer in cache: " + pointerId);
		return fp.getUrl();
	}
	
	/**
	 * 返回系统所有菜单项，即：三级菜单，带连接地址的。
	 * @return
	 */
	public List<String> getFunctionItemsUrl(){
		List<String> result = new ArrayList<String>();
		List<FunctionObj> list = null;
		for(FunctionGroup fg : funcGroupList){
			list = fg.getItemList();
			if(list != null){
				for(FunctionObj fo : list){
					result.add(fo.getUrl());
				}
			}
		}
		for(FunctionPointer fp : funcPointMap.values()){
			result.add(fp.getUrl());
		}
		return result;
	}
	
	/**
	 * 返回所有功能的功能点集合，此方法被超极管理员调用，可以访问任何功能。</p>
	 * 数据格式如下：
	 * <pre>
	 * key = fid, value = Map&lt;pid, pid&gt;
	 * </pre>
	 * @return
	 */
	public Map<String, Map<String, String>> getAllFunctionPointers(){
		//存放用户所有可用功能项，里面map是功能点id
		Map<String, Map<String, String>> userFuncMap = new HashMap<String, Map<String, String>>();
		Map<String, String> pointerMap = null;
		String pointers = null;
		String[] pIds = null;
		for(FunctionObj f : allFunctions){
			pointers = f.getPointer();
			if(StringUtils.isEmpty(pointers)){
				userFuncMap.put(f.getId(), new HashMap<String, String>(1));
			} else {
				pIds = StringUtils.toArray(pointers);
				if(pIds == null || pIds.length <= 0)
					throw new RuntimeException("error: not found pointers in function: " + f.getId());
				
				pointerMap = new HashMap<String, String>(4);
				for(String pid : pIds){
					pointerMap.put(pid, pid);
				}
				userFuncMap.put(f.getId(), pointerMap);
			}
		}
		return userFuncMap;
	}
	
	/**
	 * 返回所有子系统记录（一级菜单集合）
	 * @return
	 */
	public Map<String, FunctionObj> getAllSystem(){
		return allSystems;
	}
	
	/**
	 * 返回带URL地址的子系统菜单集合<ID>
	 * @return
	 */
	public List<String> getSystemWithUrl(){
		if(allSystems != null){
			List<String> result = new ArrayList<String>(2);
			FunctionObj fo = null;
			for(Map.Entry<String, FunctionObj> entry : allSystems.entrySet()){
				fo = entry.getValue();
				if(fo.getType() == FunctionObj.TYPE_SYSTEM && StringUtils.isNotEmpty(fo.getUrl())){
					result.add(entry.getKey());
				}
			}
			return result;
		}
		return null;
	}
	public List<FunctionObj> getSystemWithUrlList(){
		if(allSystems != null){
			List<FunctionObj> result = new ArrayList<FunctionObj>(2);
			for(FunctionObj fo : allSystems.values()){
				if(fo.getType() == FunctionObj.TYPE_SYSTEM 
						&& StringUtils.isNotEmpty(fo.getUrl())){
					result.add(fo);
				}
			}
			return result;
		}
		return null;
	}
	
	/**
	 * 返回系统、功能组二级联动菜单需要的JSON格式数据。<br>
	 * 该方法针对cxSelect.js组件来配合使用。
	 * @param onlySystem 是否只有子系统，如果设置为<code>true</code>说明，只要子系统，否则会把功能项数据也加上。
	 * @return
	 */
	public String getSystemGroupJson(boolean onlySystem){
		List<CacheTreeNode> list = new ArrayList<CacheTreeNode>();
		CacheTreeNode systemNode = null;
		for(FunctionObj sys : allSystems.values()){
			systemNode = new DefaultCacheTreeNode(sys.getId(), sys.getName(), null, null);
			if(!onlySystem){
				for(FunctionObj fo : allFunctions){
					if(fo.getParentId() != null && fo.getParentId().equals(sys.getId())){
						systemNode.addChild(new DefaultCacheTreeNode(fo.getId(), fo.getName(),null, fo.getParentId()));
					}
				}
			}
			list.add(systemNode);
		}
		try {
			return jsonCacheTreeConvertor.convertTo(list).toString();
		} catch (ConvertDataException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
	/* 把缓存节点数据转换成cxSelect组件需要的json格式的转换器 */
	/* JQuery.cxSelect组件是一个select同步联动列表组件，但它的数据格式具有通用性 */
	private Convertable jsonCacheTreeConvertor = new JsonSelectConvertor();
	
	/**
	 * 给定一个菜单对象，返回其最上级（子系统）ID
	 * @param function
	 * @return
	 */
	public String getParentSystemId(FunctionObj function){
		int type = function.getType();
		if(type == FunctionObj.TYPE_GROUP){
			return function.getParentId();
		} else if(type == FunctionObj.TYPE_ITEM){
			FunctionObj group = this.getCacheData(function.getParentId());
			if(group == null){
				throw new IllegalStateException("缓存中未找到菜单组对象: " + function.getParentId());
			}
			return group.getParentId();
		}
		throw new IllegalArgumentException();
	}
	
	/**
	 * 新添加一个功能对象到缓存中，包括：子系统、菜单组、菜单项
	 * @param function
	 */
	public void putFunction(FunctionObj function){
		int type = function.getType();
		if(type == FunctionObj.TYPE_SYSTEM){
			allSystems.put(function.getId(), function);
		} else if(type == FunctionObj.TYPE_GROUP){
			funcGroupList.add(convertFuncGroup(function));
		} else if(type == FunctionObj.TYPE_ITEM){
			getGroup(function.getParentId()).addItem(function);
		}
		allFunctions.add(function);
		// 缓存对象也添加，目前数据存放重复，此缓存类需要重构!
		putCacheData(function.getId(), function);
	}
	
	private FunctionGroup getGroup(String groupId){
		for(FunctionGroup fg : funcGroupList){
			if(fg.getId().equals(groupId))
				return fg;
		}
		throw new IllegalArgumentException("groupId not found: " + groupId);
	}
	
	/**
	 * 删除一个功能菜单项
	 * @param id
	 */
	public void removeFunction(String id){
		FunctionObj fo = null;
		for(Iterator<FunctionObj> it = allFunctions.iterator(); it.hasNext();){
			fo = it.next();
			if(fo.getId().equals(id)){
				it.remove();
				// 把菜单组中涉及的这个孩子也删掉
				for(Iterator<FunctionObj> it2 = getGroup(fo.getParentId()).getItemList().iterator(); it.hasNext();){
					if(it2.next().getId().equals(id)){
						it2.remove();
						break;
					}
				}
				break;
			}
		}
		this.removeCacheData(id);
	}
	
	/**
	 * 删除一个菜单分组
	 * @param id
	 */
	public void removeGroup(String id){
		FunctionGroup fg = null;
		for(Iterator<FunctionGroup> it = funcGroupList.iterator(); it.hasNext();){
			fg = it.next();
			if(fg.getItemList() != null && fg.getItemList().size() > 0){
				throw new ApplicationRuntimeException("菜单组存在菜单项，无法删除");
			}
			it.remove();
			break;
		}
		// 所有菜单集合中被删除
		doRemoveFromAllFunctions(id);
	}
	
	/**
	 * 删除一个子系统菜单
	 * @param id
	 */
	public void removeSystem(String id){
		allSystems.remove(id);
		doRemoveFromAllFunctions(id);
	}
	
	private void doRemoveFromAllFunctions(String id){
		// 所有菜单集合中被删除
		for(Iterator<FunctionObj> it = allFunctions.iterator(); it.hasNext();){
			if(it.next().getId().equals(id)){
				it.remove();
				break;
			}
		}
		this.removeCacheData(id);
	}
	
	/**
	 * 菜单组中是否还存在菜单项
	 * @param groupId
	 * @return
	 */
	public boolean existFunctionInGroup(String groupId){
		FunctionGroup fg = null;
		for(Iterator<FunctionGroup> it = funcGroupList.iterator(); it.hasNext();){
			fg = it.next();
			if(fg.getId().equals(groupId) && fg.getItemList() != null && fg.getItemList().size() > 0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 子系统下面是否存在功能分组，如果存在返回<code>true</code>
	 * @param systemId
	 * @return
	 */
	public boolean existGroupInSystem(String systemId){
		FunctionGroup fg = null;
		for(Iterator<FunctionGroup> it = funcGroupList.iterator(); it.hasNext();){
			fg = it.next();
			if(fg.getParentId().equals(systemId)){
				return true;
			}
		}
		return false;
	}
	
	public void putFunctionPointer(FunctionPointer fp, String _pointers){
		Assert.notNull(fp);
		this.funcPointMap.put(fp.getId(), fp);
		FunctionObj fo = getCacheData(fp.getFunctionId());
		fo.addPointer(fp);
		fo.setPointer(_pointers);
		//
		for(FunctionObj _funcObj : getGroup(fo.getParentId()).getItemList()){
			if(_funcObj.getId().equals(fo.getId())){
				_funcObj.addPointer(fp);
				_funcObj.setPointer(_pointers);
				break;
			}
		}
	}
	
	/**
	 * 从缓存中移除功能点
	 * @param pointerId 移除的功能点ID
	 * @param functionId 功能ID
	 * @param _pointers 功能中已经存在的功能点ID字符串，如：p1,p2...
	 */
	public void removeFunctionPointer(String pointerId, String functionId, String _pointers){
		Assert.hasText(pointerId);
		Assert.hasText(functionId);
		FunctionObj fo = getCacheData(functionId);
		fo.removePointer(pointerId);
		fo.setPointer(_pointers);
		funcPointMap.remove(pointerId);
		for(FunctionObj _funcObj : getGroup(fo.getParentId()).getItemList()){
			if(_funcObj.getId().equals(fo.getId())){
				_funcObj.removePointer(pointerId);
				_funcObj.setPointer(_pointers);
				break;
			}
		}
	}
	
	/**
	 * 修改菜单后，需要执行该方法更新缓存，否则菜单关系不会变化。
	 * @param functionObj 要更新的菜单对象
	 */
	public void updateFunctionInfo(FunctionObj functionObj){
		int type = functionObj.getType();
		String id = functionObj.getId();
		if(type == FunctionObj.TYPE_SYSTEM){
			allSystems.remove(id);
			allSystems.put(id, functionObj);
			doUpdateFunctionBasic(functionObj);
			
		} else if(type == FunctionObj.TYPE_GROUP){
			for(FunctionGroup fg : funcGroupList){
				if(fg.getId().equals(id)){
					fg.setOrderNum(functionObj.getOrderNum());
					fg.setName(functionObj.getName());
//					fg.setRun(functionObj.getRun());
					fg.setParentId(functionObj.getParentId());
					break;
				}
			}
			doUpdateFunctionBasic(functionObj);
			
		} else if(type == FunctionObj.TYPE_ITEM){
			
			String changedGroupId = functionObj.getParentId();
			String oldGroupId = getFunctionFromAllfuncList(id).getParentId();
			FunctionGroup changedGroup = null;
			FunctionGroup oldGroup = null;
			
			// 已经被更新的菜单缓存对象
			FunctionObj changedItem = doUpdateFunctionBasic(functionObj);
			
			// 找到菜单项所在的组
			for(FunctionGroup fg : funcGroupList){
				if(fg.getId().equals(changedGroupId)){
					changedGroup = fg;
				} else if(fg.getId().equals(oldGroupId)){
					oldGroup = fg;
				}
			}
			// 从老的组中删除，加入新的组
			if(oldGroup != null){
				oldGroup.removeItem(id);
				if(!changedGroup.getId().equals(changedItem.getParentId())){
					throw new IllegalArgumentException("两次获得的'菜单组'不一致：changed = " + changedGroup.getId() + ", item.parentId = " + changedItem.getParentId());
				}
				changedGroup.addItem(changedItem);
			}
		}
	}
	
	/**
	 * 更新缓存对象中，allFunctions中的冗余对象数据，此对象设计不合理，和原始缓存中对象重复。</p>
	 * 在后续更新中一定要统一“对象关系”与“对象本身”缓存的分离设计，避免重复缓存同一个对象。
	 * @param functionObj
	 * @return 返回更新过的缓存对象
	 */
	private FunctionObj doUpdateFunctionBasic(FunctionObj functionObj){
		FunctionObj fo = getFunctionFromAllfuncList(functionObj.getId());
		fo.setOrderNum(functionObj.getOrderNum());
		fo.setName(functionObj.getName());
		fo.setRun(functionObj.getRun());
		int type = functionObj.getType();
		if(type == FunctionObj.TYPE_SYSTEM){
			fo.setUrl(functionObj.getUrl());
			fo.setOpenStyle(functionObj.getOpenStyle());
		} else if(type == FunctionObj.TYPE_GROUP){
			fo.setParentId(functionObj.getParentId());
		} else if(type == FunctionObj.TYPE_ITEM){
			fo.setUrl(functionObj.getUrl());
			fo.setOpenStyle(functionObj.getOpenStyle());
			fo.setParentId(functionObj.getParentId());
		}
		this.updateCacheData(functionObj.getId(), fo);
		return fo;
	}
	private FunctionObj getFunctionFromAllfuncList(String id){
		for(FunctionObj fo : allFunctions){
			if(fo.getId().equals(id))
				return fo;
		}
		return null;
	}
}
