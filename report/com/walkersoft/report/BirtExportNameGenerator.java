package com.walkersoft.report;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.birt.report.utility.filename.IFilenameGenerator;

import com.walker.infrastructure.utils.StringUtils;

/**
 * BIRT导出报表命名规范自定义实现。
 * @author shikeying
 * @date 2014-8-10
 *
 */
public class BirtExportNameGenerator implements IFilenameGenerator {

	private static final String PARAM_FORMAT = "__format";
	
	@SuppressWarnings("rawtypes")
	@Override
	public String getFilename(String baseName, String extension, String outputType, Map options) {
		// TODO Auto-generated method stub
		String fileExt = "";
		for(Object o : options.values()){
			if(o instanceof HttpServletRequest){
				HttpServletRequest request = (HttpServletRequest)o;
				fileExt = request.getParameter(PARAM_FORMAT);
				break;
			}
		}
		if(StringUtils.isNotEmpty(fileExt)){
			return baseName + "." + fileExt;
		}
		return baseName;
	}

}
