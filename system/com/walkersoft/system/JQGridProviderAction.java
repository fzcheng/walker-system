package com.walkersoft.system;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.walker.db.page.support.MapPager;
import com.walker.infrastructure.utils.StringUtils;
import com.walker.web.ui.jqgrid.JQGridColumn;
import com.walker.web.ui.jqgrid.JQGridColumnProvider;

/**
 * JQGrid组件展示<code>Action</code>默认实现。</p>
 * 封装了简单的操作，包含：列显示、隐藏、列冻结控制等。<br>
 * 需要使用的业务可以继承该控制器，实现一些必要的方法。
 * @author shikeying
 *
 */
public abstract class JQGridProviderAction extends SystemAction {

	private static final String ATTR_COLUMN_NAMES = "columnNames";
	private static final String ATTR_COLUMN_DATAS = "columnDatas";
	private static final String ATTR_ALL_COLUMNS  = "allColumns";
	
	private static final String PARAMETER_VISIBLE_COLUMN = "visibles";
	private static final String PARAMETER_FROZEN_COLUMN  = "frozens";
	private static final String PARAMETER_JQGRID_PAGE    = "page";
	private static final String PARAMETER_JQGRID_ROWS    = "rows";
	
	private JQGridColumnProvider userColumnProvider = null;
	
	/**
	 * 初始化JQGrid数据提供者，同时处理界面中用户选择选项的数据，并根据用户输入发送不同界面信息。<br>
	 * 在进入列表界面时调用该方法。
	 * @param model
	 */
	protected void initJQGridPage(Model model){
		if(userColumnProvider == null){
			userColumnProvider = createJQGridProvider();
		}
		if(userColumnProvider == null){
			throw new IllegalStateException("需要实现一个JQGridColumnProvider实例对象");
		}
		
		String userId = this.getCurrentUser().getId();
		
		// 接收界面上用户选择的显示、冻结列信息
		String visibles = this.getParameter(PARAMETER_VISIBLE_COLUMN);
		String frozens  = this.getParameter(PARAMETER_FROZEN_COLUMN);
		if(StringUtils.isNotEmpty(visibles)){
			userColumnProvider.setVisibleColumns(StringUtils.asList(StringUtils.toArray(visibles)),userId);
		}
		if(StringUtils.isNotEmpty(frozens)){
			userColumnProvider.setFrozenColumns(StringUtils.asList(StringUtils.toArray(frozens)),userId);
		}
		model.addAttribute(ATTR_COLUMN_NAMES, userColumnProvider.getColumnNames(userId));
		model.addAttribute(ATTR_COLUMN_DATAS, userColumnProvider.getColumnJsonData(userId));
		
		List<JQGridColumn> allColumns = userColumnProvider.getAllColumns();
		List<String> visibleColumns = userColumnProvider.getVisibleColumnsName(userId);
		List<String> frozenColumns  = userColumnProvider.getFrozenColumnsName(userId);
		logger.debug(".....用户: " + userId + ", 冻结的列有: " + frozenColumns);
		for(JQGridColumn col : allColumns){
			if(visibleColumns != null && !visibleColumns.contains(col.getName())){
				col.setVisible(false);
			} else {
				col.setVisible(true);
			}
			if(frozenColumns != null && frozenColumns.contains(col.getName())){
				col.setFrozen(true);
			} else {
				col.setFrozen(false);
			}
		}
		model.addAttribute(ATTR_ALL_COLUMNS, allColumns);
//		userColumnProvider.clearData(userId);
	}
	
	/**
	 * 获取JQGrid列表数据的方法，通过异步Ajax调用来动态获取表格数据。<br>
	 * 子类在Ajax请求方法中调用该方法。
	 * @throws IOException
	 */
	protected void doAquireJQGridData() throws IOException{
		String page = this.getParameter(PARAMETER_JQGRID_PAGE);
		String rows = this.getParameter(PARAMETER_JQGRID_ROWS);
		int pageIndex = 1;
		int pageSize  = 15;
		if(StringUtils.isNotEmpty(page)){
			pageIndex = Integer.parseInt(page);
		}
		if(StringUtils.isNotEmpty(rows)){
			pageSize = Integer.parseInt(rows);
		}
		String userId = this.getCurrentUser().getId();
		
//		MapPager pager = userManager.queryPagerUserList(pageIndex, pageSize);
		MapPager pager = queryMapPager(pageIndex, pageSize);
		List<Map<String, Object>> datas = pager.getDatas();
		this.ajaxOutPutJson(userColumnProvider.getListJsonData(pager.getPageIndex()
				, pager.getPageCount(), pager.getTotalRows(), pager.getPageSize(), datas, userId));
	}
	
	/**
	 * 用户根据业务，实现自己的查询数据的方法，必须使用SQL语句查询，<br>
	 * 需要使用字段值信息，所有不能用Hibernate
	 * @param pageIndex
	 * @param pageSize
	 * @return 返回一个<code>MapPager</code>分页对象
	 */
	protected abstract MapPager queryMapPager(int pageIndex, int pageSize);
	
	/**
	 * 用户子类实现一个获取列表字段的提供者实现。参考demo
	 * @return
	 */
	protected abstract JQGridColumnProvider createJQGridProvider();
}
