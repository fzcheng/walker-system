package com.walkersoft.system.portlet;

import java.io.File;
import java.util.Properties;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.walkersoft.portlet.simp.BasePortlet;
import com.walkersoft.system.pojo.SysInfo;

@Component("sysInfoPortlet")
public class SysInfoPortlet extends BasePortlet {

	private Properties props = System.getProperties(); //获得系统属性集 
	
	public SysInfoPortlet(){
		setId("sysInfoPortlet");
		setTitle("系统运行环境");
		setDescription("应用程序以及虚拟机参数");
		setIncludePage("/system/portlet/sys_info.ftl");
	}
	
	@Override
	public void reload(Model model) throws Exception {
		SysInfo sysInfo = new SysInfo();
		sysInfo.setOsName(props.getProperty("os.name"));
		sysInfo.setOsVersion(props.getProperty("os.version"));
		sysInfo.setJdkVersion(props.getProperty("java.version"));
		sysInfo.setTempDir(props.getProperty("java.io.tmpdir"));
		
//		OperatingSystemMXBean osmb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		sysInfo.setPhysicalMemoryTotal(Runtime.getRuntime().totalMemory() / 1024/1024 + " MB");
		sysInfo.setPhysicalMemoryFree(Runtime.getRuntime().freeMemory() / 1024/1024 + " MB");
		
		File[] roots = File.listRoots();//获取磁盘分区列表
		for(File file : roots){
			sysInfo.addDiscInfo(file.getPath(), file.getFreeSpace());
		}
		
		model.addAttribute("sysInfo", sysInfo);
	}
}
