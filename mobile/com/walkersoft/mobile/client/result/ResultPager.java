package com.walkersoft.mobile.client.result;

import com.walker.db.page.ListPageContext;

public class ResultPager {

	public static final int DEFAULT_PAGE_SIZE  = 15;
	
	/* 当前给定的页码索引，如：1 = 第一页 */
	private int startIndex = 0;
	
	/* 每页设置的数据条数，即：每页显示几条 */
	private int pageSize  = DEFAULT_PAGE_SIZE;
	
	private int pageCount = 0;
	
	private int totalRows = 0;
	
	
}
