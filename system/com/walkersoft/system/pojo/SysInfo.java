package com.walkersoft.system.pojo;

import java.util.Map;
import java.util.TreeMap;

public class SysInfo {

	private String cpu;
	private String osName;
	private String osVersion;
	
	private String jdkVersion;
	
	private String physicalMemoryTotal;
	private String physicalMemoryFree;
	
	private Map<String, String> discSpaces = new TreeMap<String, String>();
	
	private String tempDir;

	public String getTempDir() {
		return tempDir;
	}

	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getJdkVersion() {
		return jdkVersion;
	}

	public void setJdkVersion(String jdkVersion) {
		this.jdkVersion = jdkVersion;
	}

	public String getPhysicalMemoryTotal() {
		return physicalMemoryTotal;
	}

	public void setPhysicalMemoryTotal(String physicalMemoryTotal) {
		this.physicalMemoryTotal = physicalMemoryTotal;
	}

	public String getPhysicalMemoryFree() {
		return physicalMemoryFree;
	}

	public void setPhysicalMemoryFree(String physicalMemoryFree) {
		this.physicalMemoryFree = physicalMemoryFree;
	}

	public Map<String, String> getDiscSpaces() {
		return discSpaces;
	}
	
	public void addDiscInfo(String path, long size){
		discSpaces.put(path, size/1024/1024/1024+" G");
	}
}
