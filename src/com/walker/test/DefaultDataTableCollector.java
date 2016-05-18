package com.walker.test;

import java.util.List;

import com.walker.infrastructure.utils.StringUtils;

public class DefaultDataTableCollector implements DataTableCollectable {

	private List<String> filenames = null;
	
	private String testSql;
	
	private boolean forceOption = false;
	
	@Override
	public List<String> getScriptFiles() {
		// TODO Auto-generated method stub
		return this.filenames;
	}

	@Override
	public String getTestSql() {
		// TODO Auto-generated method stub
		return this.testSql;
	}

	@Override
	public boolean isForceOption() {
		// TODO Auto-generated method stub
		return this.forceOption;
	}

	@Override
	public void setScriptFiles(List<String> filenames) {
		// TODO Auto-generated method stub
		assert (filenames != null && filenames.size() > 0);
		this.filenames = filenames;
	}

	@Override
	public void setTestSql(String sql) {
		// TODO Auto-generated method stub
		assert (StringUtils.isNotEmpty(sql));
		this.testSql = sql;
	}

	@Override
	public void setForceOption(boolean forceOption) {
		// TODO Auto-generated method stub
		this.forceOption = forceOption;
	}

}
