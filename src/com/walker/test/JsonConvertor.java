package com.walker.test;

import java.util.ArrayList;
import java.util.List;

import com.walker.infrastructure.cache.tree.CacheTreeNode;
import com.walker.infrastructure.cache.tree.DefaultCacheTreeNode;
import com.walker.infrastructure.core.filter.ConvertDataException;
import com.walker.infrastructure.core.filter.Convertable;
import com.walker.infrastructure.utils.StringUtils;

/**
 * 把<code>CacheTreeNode</code>与Json字符串之间的转换。<br>
 * 目前仅实现转换成JSON格式，并未实现JSON到对象树的反向转换。
 * @author shikeying
 *
 */
public class JsonConvertor implements Convertable {

	private static final String DEFAULT_KEY_NAME = "v";
	private static final String DEFAULT_VALUE_NAME = "n";
	private static final String DEFAULT_CHILD_NAME = "s";
	
	private String keyName;
	private String valueName;
	private String childName;
	
	public JsonConvertor(){
		this.keyName = DEFAULT_KEY_NAME;
		this.valueName = DEFAULT_VALUE_NAME;
		this.childName = DEFAULT_CHILD_NAME;
	}
	public JsonConvertor(String keyName, String valueName, String childName){
		this.keyName = keyName;
		this.valueName = valueName;
		this.childName = childName;
	}
	
	@Override
	public Object convertFrom(Object arg0) throws ConvertDataException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/**
	 * 把缓存树节点对象转换成JSON字符串
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object convertTo(Object cacheTreeNodes) throws ConvertDataException {
		// TODO Auto-generated method stub
		if(cacheTreeNodes == null) return null;
		if(cacheTreeNodes instanceof List<?>){
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for(CacheTreeNode ctn : (List<CacheTreeNode>)cacheTreeNodes){
				sb.append(doCreatePrimaryObject(ctn));
				sb.append(",");
			}
			if(sb.toString().endsWith(StringUtils.DEFAULT_SPLIT_SEPARATOR)){
				sb.deleteCharAt(sb.lastIndexOf(StringUtils.DEFAULT_SPLIT_SEPARATOR));
			}
			sb.append("]");
//			JSONArray array = JSONArray.fromObject(sb);
//			System.out.println(array);
			return sb.toString();
		} else 
			throw new IllegalArgumentException();
	}
	
	private String doCreatePrimaryObject(CacheTreeNode node){
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append(keyName);
		sb.append(":'");
		sb.append(node.getKey());
		sb.append("',");
		sb.append(valueName);
		sb.append(":'");
		sb.append(node.getText());
		sb.append("'");
		if(node.hasChild()){
			sb.append(",");
			sb.append(childName);
			sb.append(":[");
			for(CacheTreeNode n : node.getChildren()){
				sb.append(doCreatePrimaryObject(n));
				sb.append(StringUtils.DEFAULT_SPLIT_SEPARATOR);
			}
			if(sb.toString().endsWith(StringUtils.DEFAULT_SPLIT_SEPARATOR)){
				sb.deleteCharAt(sb.lastIndexOf(StringUtils.DEFAULT_SPLIT_SEPARATOR));
			}
			sb.append("]");
		}
		sb.append("}");
		return sb.toString();
	}
	
	@Override
	public Class<?> getDestType() {
		// TODO Auto-generated method stub
		return String.class;
	}

	@Override
	public Class<?> getSourceType() {
		// TODO Auto-generated method stub
		return CacheTreeNode.class;
	}

	public static void main(String[] args) throws Exception{
		JsonConvertor jc = new JsonConvertor();
		CacheTreeNode root = new DefaultCacheTreeNode("root", "机构", null, null);
		
		CacheTreeNode n1 = new DefaultCacheTreeNode("n1", "n1", null, "root");
		CacheTreeNode n11= new DefaultCacheTreeNode("n1_1", "n1_1name", null, "n1");
		CacheTreeNode n12= new DefaultCacheTreeNode("n1_2", "n1_2name", null, "n1");
		n1.addChild(n11);
		n1.addChild(n12);
		
		root.addChild(n1);
		
		CacheTreeNode n2 = new DefaultCacheTreeNode("n2", "名字n2", null, "root");
		root.addChild(n2);
		
		List<CacheTreeNode> list = new ArrayList<CacheTreeNode>();
		list.add(root);
		
		System.out.println(jc.convertTo(list));
	}
}
