package com.walkersoft.appmanager.util.swift;



/**
 * <一句话功能简述>
 * <功能详细描述>配置信息
 * 
 * @author  Administrator
 * @version  [版本号, 2014-8-29]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SwiftpassConfig {
    /**
     * 威富通交易密钥
     */
    public static String key = "b93ca2b0aa6b38598422e392f702b0fb";
    //public static String key = "7daa4babae15ae17eee90c9e";
    
    /**
     * 威富通商户号
     */
    public static String mch_id = "6532000070";
    //public static String mch_id = "7552900037";
    
    /**
     * 威富通请求url
     */
    public static String req_url = "https://pay.swiftpass.cn/pay/gateway";
    
    /**
     * 通知url
     */
    public static String notify_url = "http://114.55.99.232:8080/yeelosdk/sdk/pay/swiftcallback.do";
    
}
