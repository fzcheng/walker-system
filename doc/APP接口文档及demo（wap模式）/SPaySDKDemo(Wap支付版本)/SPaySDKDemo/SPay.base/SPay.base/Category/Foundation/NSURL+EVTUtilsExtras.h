//
//  NSURL+EVTUtilsExtras.h
//  EVTUtils
//
//  Created by xeon well on 13/3/12.
//  Copyright (c) 2012 eightcolor, Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSURL (EVTUtilsExtras)
/*!
 @brief     根据给定的url以及参数字典, 生成NSURL类的实例
 @param     urlString URL字符串
 @param     paramDict 参数字典
 @return    生成NSURL类的实例
 */
+ (id)URLWithString:(NSString *)urlString paramDictionary:(NSDictionary *)paramDict;
- (id)initWithString:(NSString *)urlString paramDictionary:(NSDictionary *)paramDict;

/*!
 @brief     根据给定字符串和format参数生成NSURL实例
 @return    NSURL实例
 */
+ (id)URLWithFormat:(NSString *)format, ... NS_FORMAT_FUNCTION(1,2);
@end
