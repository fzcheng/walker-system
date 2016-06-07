package com.walker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.walkersoft.appmanager.util.DateUtil;

public class Test {

	private static final int COUNT = 1000;
	
	private static Map<String, MyData> cachedData = new HashMap<String, Test.MyData>();
	
	static {
		String id = null;
		for(int i=0; i<COUNT; i++){
			id = String.valueOf(i);
			cachedData.put(id, new MyData().setId(id).setTime(System.currentTimeMillis()));
		}
	}
	
	public static void main(String[] args){
//		Map<String, Object> map = new HashMap<String, Object>();
//		Collection<Object> values = map.values();
//		values.iterator();
		
//		long time = 1388640370;
//		System.out.println("1: " + new Date(time*1000));
//		
//		long year = 2014 * 12 * 30 * 24 * 3600 * 1000;
//		System.out.println("2: " + new Date(time + year));
//		
//		System.out.println("3: " + new Date(1388640370000L));
		
//		while(true){
//			MyData md = null;
//			List<MyData> list = new ArrayList<MyData>(COUNT);
//			for(Iterator<MyData> it = cachedData.values().iterator(); it.hasNext();){
//				list.add(it.next());
//			}
//			System.out.println(".......... 处理了一次数据: " + list.size());
//		}
		System.out.println(DateUtil.getCurrentWeekDay_int());
	}
	
	private static class MyData {
		String id;
		long time = 0L;
		public String getId() {
			return id;
		}
		public MyData setId(String id) {
			this.id = id;
			return this;
		}
		public long getTime() {
			return time;
		}
		public MyData setTime(long time) {
			this.time = time;
			return this;
		}
	}
}
