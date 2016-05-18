package com.walkersoft.application;

/**
 * 系统可变参数命名类，它维护了这些参数的ID值，通过枚举来引用。</br>
 * 在开发中，每次加入新参数，除了在配置文件中加入，还要再次对象中加入枚举值，方便应用引用。
 * @author shikeying
 *
 */
public enum MyArgumentsNames {

	/**
	 * 参数ID：超级管理员密码
	 */
	SystemSuperPass{
		public String getName(){
			return ID_SYSTEM_SUPERPASS;
		}
	},
	SystemUserInitPass{
		public String getName(){
			return ID_SYSTEM_USER_INITPASS;
		}
	},
	SystemUserResetPass{
		public String getName(){
			return ID_SYSTEM_USER_RESETPASS;
		}
	},
	SystemShowValidCode{
		public String getName(){
			return ID_SHOW_VALIDCODE;
		}
	},
	SystemCompany{
		public String getName(){
			return ID_SYSTEM_COMPANY;
		}
	},
	AttachRootPathWin{
		public String getName(){
			return ID_ATTACH_PATH_WIN;
		}
	},
	AttachRootPathUnix{
		public String getName(){
			return ID_ATTACH_PATH_UNIX;
		}
	},
	LogoPath{
		public String getName(){
			return ID_LOGO_PATH;
		}
	};
	
	private static final String ID_SYSTEM_SUPERPASS = "SYSTEM.SUPERPASS";
	private static final String ID_SYSTEM_USER_INITPASS = "SYSTEM.USER_INITPASS";
	private static final String ID_SYSTEM_USER_RESETPASS = "SYSTEM.USER_RESETPASS";
	private static final String ID_SHOW_VALIDCODE = "SYSTEM.SHOW_VALIDCODE";
	private static final String ID_SYSTEM_COMPANY = "SYSTEM.COMPANY";
	
	private static final String ID_ATTACH_PATH_WIN = "ATTACHMENT.PATH_WIN";
	private static final String ID_ATTACH_PATH_UNIX = "ATTACHMENT.PATH_UNIX";
	private static final String ID_LOGO_PATH = "HOME.SETTING.LOGO_PATH";
	
	/**
	 * 返回枚举的可变参数的名字，这个将作为key来查询参数对象。</p>
	 * 这个名字与在配置文件（如：app_variables.xml）中ID一致，</br>
	 * 这样避免到处使用字符ID，使系统整体统一，只需要在一个地方维护字符串。
	 * @return
	 */
	public String getName(){
		throw new AbstractMethodError();
	}
}
