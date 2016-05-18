package com.walkersoft.system.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.walker.infrastructure.utils.StringUtils;
import com.walker.web.ui.jqgrid.JQGridColumn;
import com.walker.web.ui.jqgrid.JQGridColumnProvider;

/**
 * JQGrid分装数据提供者，为界面提供数据处理逻辑，以及列的各种展示，包括：隐藏、冻结等。
 * @author shikeying
 *
 */
public abstract class AbstractJQColumnProvider implements JQGridColumnProvider {

	private Map<String, JQGridColumn> allColumns = new LinkedHashMap<String, JQGridColumn>();
	
//	private ThreadLocal<List<String>> visibleConfig = new NamedThreadLocal<List<String>>("jqgrid.visible");
//	private ThreadLocal<List<String>> frozenConfig  = new NamedThreadLocal<List<String>>("jqgrid.visible");
	
	private Map<String, List<String>> visibleConfig = new ConcurrentHashMap<String, List<String>>(8);
	private Map<String, List<String>> frozenConfig = new ConcurrentHashMap<String, List<String>>(8);
	
	@Override
	public List<JQGridColumn> getAllColumns(){
		if(allColumns.size() == 0)
			throw new IllegalStateException();
		List<JQGridColumn> _copyed = new ArrayList<JQGridColumn>(8);
		for(JQGridColumn col : allColumns.values()){
			_copyed.add((JQGridColumn)col.clone());
		}
		return _copyed;
	}
	
	@Override
	public List<String> getVisibleColumnsName(String userId){
		return visibleConfig.get(userId);
	}
	@Override
	public List<String> getFrozenColumnsName(String userId){
		return frozenConfig.get(userId);
	}
	
	/**
	 * 拷贝对象，否则会出现并发问题
	 * @return
	 */
	private Map<String, JQGridColumn> cloneNewColumns(){
		Map<String, JQGridColumn> _copyColumns = new LinkedHashMap<String, JQGridColumn>();
		for(JQGridColumn col : allColumns.values()){
			_copyColumns.put(col.getName(), (JQGridColumn)col.clone());
		}
		return _copyColumns;
	}
	
	@Override
	public String getColumnJsonData(String userId) {
		if(allColumns.size() == 0){
			addColumns(allColumns);
			doInitVisibleColumns(userId);
		}
		
		JSONArray array = new JSONArray();
		String name = null;
		JQGridColumn column = null;
		/* 拷贝了原始列定义信息，因为多用户使用此对象，因此需要考虑并发问题 */
		for(Map.Entry<String, JQGridColumn> entry : cloneNewColumns().entrySet()){
			name = entry.getKey().toLowerCase();
			column = entry.getValue();
			if(!isVisibleColumn(name, userId)){
				column.setVisible(false);
				continue;
			}
			if(isFrozenColumn(name, userId)){
				column.setFrozen(true);
			}
			array.add(column.toJson());
		}
		return array.toString();
	}

	@Override
	public String getColumnNames(String userId) {
		if(allColumns.size() == 0){
			addColumns(allColumns);
			doInitVisibleColumns(userId);
		}
		StringBuilder sb = new StringBuilder();
		String name = null;
		for(JQGridColumn entry : allColumns.values()){
			name = entry.getName();
			if(isVisibleColumn(name, userId)){
				sb.append(entry.getAlias());
				sb.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			}
		}
		if(sb.toString().endsWith(StringUtils.DEFAULT_SPLIT_SEPARATOR)){
			sb.deleteCharAt(sb.lastIndexOf(StringUtils.DEFAULT_SPLIT_SEPARATOR));
		}
		return sb.toString();
	}
	
	private static final String JSON_KEY_DATA    = "data";
	private static final String JSON_KEY_PAGE    = "page";
	private static final String JSON_KEY_TOTAL   = "total";
	private static final String JSON_KEY_RECORDS = "records";
	private static final String JSON_KEY_ROWS    = "rows";
	
	@Override
	public String getListJsonData(int currentPage, int totalPage
			, int records, int pageSize, List<Map<String, Object>> datas, String userId){
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		
		if(datas != null && datas.size() > 0){
			JSONObject json = null;
			String name = null;
			for(Map<String, Object> data : datas){
				json = new JSONObject();
				for(Map.Entry<String, Object> entry : data.entrySet()){
					name = entry.getKey().toLowerCase();
					if(!isVisibleColumn(name, userId)){
						continue;
					}
					json.put(name, entry.getValue());
				}
				array.add(json);
			}
		}
		result.put(JSON_KEY_DATA, array);
		result.put(JSON_KEY_PAGE, currentPage);
		result.put(JSON_KEY_TOTAL, totalPage);
		result.put(JSON_KEY_RECORDS, records);
		result.put(JSON_KEY_ROWS, pageSize);
		return result.toString();
	}

	/**
	 * 添加所有需要用到的列信息。</p>
	 * 子类中通过以下方式调用：
	 * <pre>
	 * protected void addColumns(Map<String, JQGridColumn> allColumns){
	 * JQGridColumn column = new JQGridColumn();
	 * column.setName("id").setIndex("id").setFrozen(true).setWidth(200).setAlias("用户ID");
	 * allColumns.put("id", column);
	 * ...
	 * }
	 * </pre>
	 * @param allColumns
	 */
	protected abstract void addColumns(Map<String, JQGridColumn> allColumns);

	@Override
	public void setFrozenColumns(List<String> names, String userId) {
		if(names == null) return;
		frozenConfig.put(userId, names);
	}

	@Override
	public void setVisibleColumns(List<String> names, String userId) {
		if(names == null) return;
		visibleConfig.put(userId, names);
	}
	
	/**
	 * 清除掉和线程绑定的状态对象，包括：显示列、冻结列等内部状态数据。
	 */
	@Override
	public void clearData(String userId){
		visibleConfig.remove(userId);
		frozenConfig.remove(userId);
	}
	
	private boolean isVisibleColumn(String name, String userId){
		List<String> _visibles = visibleConfig.get(userId);
		if(_visibles == null || _visibles.size() == 0){
			// 用户没有设置显示列信息，默认全部显示
			return true;
		} else if(_visibles.contains(name)){
			return true;
		} else 
			return false;
	}
	
	private boolean isFrozenColumn(String name, String userId){
		List<String> _frozens = frozenConfig.get(userId);
		if(_frozens == null || _frozens.size() == 0){
			return false;
		} else if(_frozens.contains(name)){
			return true;
		} else
			return false;
	}
	
	/**
	 * 初始化可见字段数据，因为如果不初始化，可能会和数据库获得的字段数量不一致。<br>
	 * 因此这里默认是和用户配置的字段是一样的。
	 */
	private void doInitVisibleColumns(String userId){
		if(allColumns.size() == 0)
			throw new IllegalStateException();
		List<String> _visibles = visibleConfig.get(userId);
		if(_visibles == null){
			visibleConfig.put(userId, new ArrayList<String>(allColumns.keySet()));
		}
	}
}
