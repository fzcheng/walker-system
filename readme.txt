1、关于URL中传输中文乱码问题解决方案，在tomcat server.xml中配置：
URIEncoding="UTF-8"。

-------------------------------------------------------------------
2、在使用https安全方式登录系统时，只需要修改application_security.xml文件中的相关内容，如下：
 2.1、把以下内容放开（注释去掉）
   <!-- 
    	https登录的配置
    	<intercept-url pattern="/login.do" requires-channel="https"/>
    	<intercept-url pattern="/j_spring_security_check" requires-channel="https"/>
    	<intercept-url pattern="/index.do" requires-channel="http"/>
    	 -->
 2.2、myAuthenticationProcessingFilter设置useHttps=true; 如下：
 	  <beans:property name="useHttps" value="true"/>
 	  
 2.3、设计文档中有默认的keystore，必须把keystore和证书导入到JDK中才能使用。
 
-------------------------------------------------------------------	  
 
3、walker-infrastructure-doc-0.3.10.zip是依赖的核心类库的文档注释，把它导入到你的eclipse环境中，就可以看到封装类库中接口的说明了。