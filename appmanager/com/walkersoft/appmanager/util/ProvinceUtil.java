package com.walkersoft.appmanager.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class ProvinceUtil {

	static ProvinceUtil self = null;
	private static Map<Integer, String> provinceNames = new HashMap<Integer, String>();
	private static Map<String, Integer> provinceIds = new HashMap<String, Integer>();
	
	public static final Object p[][] = { 
			{ 0, "未知" },
			{ 1, "北京" }, { 2, "上海" }, { 3, "天津" },
			{ 4, "重庆" }, { 5, "黑龙江" }, { 6, "吉林" }, { 7, "辽宁" },
			{ 8, "内蒙" }, { 9, "河北" }, { 10, "山西" }, { 11, "陕西" },
			{ 12, "山东" }, { 13, "新疆" }, { 14, "西藏" }, { 15, "青海" },
			{ 16, "甘肃" }, { 17, "宁夏" }, { 18, "河南" }, { 19, "江苏" },
			{ 20, "湖北" }, { 21, "浙江" }, { 22, "安徽" }, { 23, "福建" },
			{ 24, "江西" }, { 25, "湖南" }, { 26, "贵州" }, { 27, "四川" },
			{ 28, "广东" }, { 29, "云南" }, { 30, "广西" }, { 31, "海南" },
			{ 32, "香港" }, { 33, "澳门" }, { 34, "台湾" } };
	
	public static ProvinceUtil getInstance()
	{
		if(self == null)
		{
			self = new ProvinceUtil();
			self.Compone();
		}
		return self;
	}
	
	private void Compone() {
		provinceNames = new HashMap<Integer, String>();
		provinceIds = new HashMap<String, Integer>();
		for(Object s[] : p)
		{
			provinceNames.put(Integer.valueOf(""+s[0]), ""+s[1]);
			provinceIds.put(""+s[1], Integer.valueOf(""+s[0]));
		}
	}
	
	public String getProviceName(int id)
	{
		String name =  provinceNames.get(id);
		if(name == null)
			return "未知";
		return name;
	}
	
	public int getProvinceId(String name)
	{
		Integer id = provinceIds.get(name);
		if(id == null)
			return 0;
		return id;
	}

	public int getProvinceId(HttpServletRequest request) {
		IPSeeker iPSeeker =IPSeeker.getInstance();
		String ip = iPSeeker.getIpAddr(request);
		String txnProvince = iPSeeker.getAddress(ip);
		System.out.println("----txnProvince----:" + txnProvince);
		if(txnProvince.length() >= 2)
			txnProvince = txnProvince.substring(0,2);
		int provinceid = ProvinceUtil.getInstance().getProvinceId(txnProvince);
		return provinceid;
	}
}
