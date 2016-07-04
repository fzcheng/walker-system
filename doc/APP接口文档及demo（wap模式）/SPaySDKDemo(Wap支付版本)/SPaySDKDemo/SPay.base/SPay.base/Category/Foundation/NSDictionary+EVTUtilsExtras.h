//
//  NSDictionary+EVTUtilsExtras.h
//  BPLFoundation
//
//  Created by fish.wong on 14/12/16.
//  Copyright (c) 2014年 fish. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreGraphics/CoreGraphics.h>
#import <UIKit/UIKit.h>

@interface NSDictionary (EVTUtilsExtras)

/**
 *  NSDictionary to NSString
 *
 *  @return <#return value description#>
 */
- (NSString*)parseResponseJSONToString;
- (id)customObjectForKey:(id)aKey;

/**
 *  字典转HTTP get参数
 *
 *  @return <#return value description#>
 */
- (NSString*)jsonToHttpGetParameter;


/**
 *  判断字典中某个key是否存在
 *
 *  @param keyName 存在返回YES
 *
 *  @return <#return value description#>
 */
- (BOOL)isForKeyExists:(NSString*)keyName;



/**
 *  安全获取字典里面的值
 *
 *  @param key <#key description#>
 *
 *  @return <#return value description#>
 */
- (id)safeObjectForKey:(NSString*)key;

@end

@interface NSMutableDictionary (EVTUtilsExtras)
//安全设置指定key的对象值
- (void)safeSetObject:(id)anObject forKey:(id)aKey;

- (void)setPoint:(CGPoint)value forKey:(NSString *)key;
- (void)setSize:(CGSize)value forKey:(NSString *)key;
- (void)setRect:(CGRect)value forKey:(NSString *)key;

@end
