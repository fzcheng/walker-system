package com.walkersoft.portlet;

import org.springframework.ui.Model;

/**
 * <code>portlet</code>定义，它描述了一个窗口小部件的特性。
 * @author shikeying
 * @date 2014-10-27
 *
 */
public interface Portlet {

	/**
	 * 返回ID
	 * @return
	 */
	String getId();
	
	/**
	 * 返回标题
	 * @return
	 */
	String getTitle();
	
	/**
	 * 返回描述
	 * @return
	 */
	String getDescription();
	
	/**
	 * 返回<code>portlet</code>对应的模板文件。
	 * @return
	 */
	String getIncludePage();
	
	void setId(String id);
	
	void setTitle(String title);
	
	void setDescription(String description);
	
	void setIncludePage(String page);
	
	void setOuterUrl(boolean outerUrl);
	
	/**
	 * 重新加载数据
	 * @param model
	 * @throws Exception
	 */
	void reload(Model model) throws Exception;
	
	/**
	 * 是否加载外部链接，如果是返回<code>true</code>加载外部URL。</br>
	 * 默认返回<code>false</code>。
	 * @return
	 */
	boolean isOuterUrl();
	
	
}
