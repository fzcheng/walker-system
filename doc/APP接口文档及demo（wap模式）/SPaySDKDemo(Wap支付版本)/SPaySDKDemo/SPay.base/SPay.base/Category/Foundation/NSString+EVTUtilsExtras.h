//
//  NSStringExtras.h
//  EVTUtils
//
//  Created by xeonwell on 17/06/2011.
//  Copyright 2011 Evt, Inc. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface NSString (NSString_EVTUtilsExtras)
//获取UUID
+ (NSString *)createUUID;

#pragma mark utils
//是否为空
- (BOOL)isEmpty;
//当前字符串是否只包含空白字符和换行符
- (BOOL)isWhitespaceAndNewlines;
/*!
 @brief     是否包含汉字
 @return    是否包含汉字
 */
- (BOOL)isContainChinese;
//是否是6为数字
- (BOOL)isSixNumbersPassword;

/**
 *  是否是spay登录密码
 *
 *  @return <#return value description#>
 */
- (BOOL)isSPayLoginPassword;

/**
 *  是否是更新密码
 *
 *  @return <#return value description#>
 */
- (BOOL)isSPayUpdatePassword;

/**
 *  是否是spay登录账户
 *
 *  @return <#return value description#>
 */
- (BOOL)isSPayLoginAcount;

/**
 *  是否是一个Url连接
 *
 *  @return <#return value description#>
 */
- (BOOL)isHttpUrl;

//去除字符串前后的空白,包含换行符
- (NSString *)trim;
//去除字符串中所有空白
- (NSString *)removeWhiteSpace;
//将字符串以URL格式编码
- (NSString *)stringByUrlEncoding;

/**
 *  NSString To NSMutableDictionary
 *
 *  @return <#return value description#>
 */
- (NSMutableDictionary*)parseResponseStringToJSON;

/**
 *  JsonArray to NSMutableArray
 *
 *  @return <#return value description#>
 */
- (NSMutableArray*)parseResponseArrayStringToJSONArray;

/**
 *  字符串URL解码
 *
 *  @return NSString
 */
- (NSString *)stringByUrlDecoding;
/*!
 @brief     大写第一个字符
 @return    格式化后的字符串
 */
- (NSString *)capitalize;
/*!
 @brief     小写第一个字符
 @return    格式化后的字符串
 */
- (NSString *)lowercaseFirstString;
//正则匹配, ios3.2+
- (BOOL)matches:(NSString *)pattern;
- (BOOL)matchesWithCaseInsensitive:(NSString *)pattern;
//正则替换 ios4.0+
- (NSString *)stringByReplacingRegularString:(NSString *)regString withString:(NSString *)replaceString;

//以给定字符串开始,忽略大小写
- (BOOL)startsWith:(NSString *)str;
//以指定条件判断字符串是否以给定字符串开始
- (BOOL)startsWith:(NSString *)str Options:(NSStringCompareOptions) compareOptions;


/*!
 @brief 以给定字符串结束，忽略大小写
 @code
 BOOL isEnd = [@"asdf" endsWith:@"df"];
 @endcode
 @param str 给定字符串
 @return 是否以给定字符串结尾
 */
- (BOOL)endsWith:(NSString *)str;
/*!
 @brief 以指定条件判断字符串是否以给定字符串结尾
 @param str 给定字符串
 @param compareOptions NSStringCompareOptions 枚举值
 */
- (BOOL)endsWith:(NSString *)str Options:(NSStringCompareOptions)compareOptions;
/*!
 @brief 包含给定的字符串, 忽略大小写
 @param str 给定字符串
 */
- (BOOL)containsString:(NSString *)str;
//以指定条件判断是否包含给定的字符串
- (BOOL)containsString:(NSString *)str Options:(NSStringCompareOptions)compareOptions;
//判断字符串是否相同，忽略大小写
- (BOOL)equalsString:(NSString *)str;
//将字符串转换成NSDate
- (NSDate *)convertToCurrentDateWithFormat:(NSString *)format;
//将字符串转换成NSDate
- (NSDate *)convertToDateWithFormat:(NSString *)format;
//转换为url
- (NSURL *)convertToURL;
//判断一个字符串是否邮箱
//- (BOOL)isEmailFormat;

//过滤掉字符串中指定字符
- (NSString *)filterString:(NSArray *)charArray;

//金额保留一位小数，无小数时显示整数
- (NSString *)moneyDot1Mask;
//电话号码处理 18612341234 -> 186****1234

- (NSString *)mobileMask;

/**
 *  是否是手机号码
 *
 *  @return <#return value description#>
 */
- (BOOL)isMobileNumber;

/**
 *  是否是验证码格式
 *
 *  @return <#return value description#>
 */
- (BOOL)isVerificationCode;

- (BOOL)isValidUsername;
//是否是正确的会员卡用户名
- (BOOL)isValidMemberCardUserName;
//判断是否是汉字
- (BOOL)isValidChineseName;
- (BOOL)isValidPassword;
- (NSString*)dateString;
//字符长度
- (int)bytesLength;

#pragma mark -  fish add
/**
 *  解析HTTP中GET的请求参数
 *
 *  @return <#return value description#>
 */
-(NSDictionary*)resolveHTTPGETParameter;

/**
 *  解析HTTP Get参数
 *
 *  @return <#return value description#>
 */
-(NSDictionary*)parseHTTGetParameter;

/**
 *  获取网址的绝对路径
 *
 *  @return <#return value description#>
 */
- (NSString*)stringUrlAbsolutePath;

/**
 *  身份证加密显示
 *
 *  @return <#return value description#>
 */
- (NSString*)identificationHideString;

/**
 *  金额转大写
 *
 *  @param money <#money description#>
 *
 *  @return <#return value description#>
 */
-(NSString *)digitUppercase;


/**
 *  讲类似于20150605112215的格式转换为时间格式
 *
 *  @return <#return value description#>
 */
- (NSString*)tradetime_toDateString;

- (NSNumber*)currencyNumber;



#pragma mark xml extensions
//编码与解码xml所用的字符串
+ (NSString *)encodeXMLCharactersIn : (NSString *)source;
+ (NSString *)decodeXMLCharactersIn : (NSString *)source;

#pragma mark Hashing
- (NSString *) base64Encoding;
@property (nonatomic, readonly) NSString* md5Hash;
@property (nonatomic, readonly) NSString* sha1Hash;

#pragma mark url
- (NSString *)stringByAppendURLKey:(NSString *)keyName Parameter:(id)parameter;
@end

@interface NSMutableString (PDUtilsExtras)

// append a parameter to an url
//注意未处理#号
- (NSMutableString *)appendURLKey:(NSString *)keyName Parameter:(id)parameter;



@end
