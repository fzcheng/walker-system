package com.walker.test;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.walker.infrastructure.utils.StringUtils;

/**
 * 默认的缓存树节点实现
 * @author shikeying
 *
 */
public class DefaultCacheTreeNode implements CacheTreeNode {

	private String key;
	private String text;
	private Object source;
	
	private String parentId;
	
	private Map<String, CacheTreeNode> children = new TreeMap<String, CacheTreeNode>();
	
	public DefaultCacheTreeNode(String key, String text
			, Object source, String parentId){
		assert(StringUtils.isNotEmpty(key));
		assert(StringUtils.isNotEmpty(text));
		this.key = key;
		this.text = text;
		this.source = source;
		if(StringUtils.isNotEmpty(parentId))
			this.parentId = parentId;
	}
	
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return text;
	}

	@Override
	public Object getSource() {
		// TODO Auto-generated method stub
//		throw new UnsupportedOperationException("no source.");
		return this.source;
	}

	@Override
	public boolean hasChild() {
		// TODO Auto-generated method stub
		return this.children.size() > 0;
	}

	@Override
	public int getChildrenSize() {
		// TODO Auto-generated method stub
		return this.children.size();
	}

	@Override
	public Collection<CacheTreeNode> getChildren() {
		// TODO Auto-generated method stub
		if(hasChild())
			return children.values();
		return null;
	}

	@Override
	public CacheTreeNode search(String key) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(key)) return null;
		if(this.key.equals(key))
			return this;
		if(!hasChild())
			return null;
		
		CacheTreeNode child = null;
		for(Iterator<CacheTreeNode> i = children.values().iterator(); i.hasNext();){
			child = i.next().search(key);
			if(child != null)
				return child;
		}
		return null;
	}

	@Override
	public void addChild(CacheTreeNode node) {
		// TODO Auto-generated method stub
		if(node != null)
			children.put(node.getKey(), node);
	}

	@Override
	public String getParentId() {
		// TODO Auto-generated method stub
		return this.parentId;
	}

	public String toString(){
		return new StringBuilder().append("{key = ").append(key)
				.append(", text = ").append(text)
				.append(", parentId = ").append(parentId)
				.append("}").toString();
	}
}
