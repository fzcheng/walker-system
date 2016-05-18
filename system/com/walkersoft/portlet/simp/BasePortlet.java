package com.walkersoft.portlet.simp;

import org.springframework.ui.Model;

import com.walker.infrastructure.utils.Assert;
import com.walkersoft.portlet.Portlet;

public class BasePortlet implements Portlet {

	protected String id;
	protected String title;
	protected String description;
	
	protected String includePage;
	
	protected boolean outerUrl = false;
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getIncludePage() {
		return includePage;
	}

	@Override
	public void reload(Model model) throws Exception {

	}

	@Override
	public boolean isOuterUrl() {
		return this.outerUrl;
	}

	@Override
	public void setId(String id) {
		Assert.hasText(id);
		this.id = id;
	}

	@Override
	public void setTitle(String title) {
		Assert.hasText(title);
		this.title = title;
	}

	@Override
	public void setIncludePage(String page) {
		Assert.hasText(page);
		this.includePage = page;
	}
	
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setOuterUrl(boolean outerUrl) {
		this.outerUrl = outerUrl;
	}

	@Override
	public String toString(){
		return new StringBuilder().append("[id=").append(id)
				.append(", title=").append(title)
				.append(", page=").append(includePage)
				.append("]").toString();
	}
}
