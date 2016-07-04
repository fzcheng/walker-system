//
//  NSNumber+SPUtilsExtras.h
//  SPay.base
//
//  Created by wongfish on 15/5/13.
//  Copyright (c) 2015年 wongfish. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
@interface NSNumber (SPUtilsExtras)

/**
 *  转换为货币格式
 *
 *  @return <#return value description#>
 */
- (NSString*)spCurrencyString;

/**
 *  转换为货币类型
 *
 *  @return <#return value description#>
 */
- (NSString*)currencyString;


/**
 *  SPay货币类型转正常货币类型
 *
 *  @return <#return value description#>
 */
- (NSNumber*)spToCurrency;

/**
 *  退款状态类型
 *
 *  @return <#return value description#>
 */
- (NSString*)refundStateString;

/**
 *  将服务器返回的时间戳转换,服务器是精确到毫秒的
 *
 *  @return <#return value description#>
 */
- (NSNumber*)toTimeIntervalSince1970;

/**
 *  订单状态
 *
 *  @return <#return value description#>
 */
- (NSString*)orderStateString;

- (NSString*)spInputCurrencyString;

- (NSString*)toDateString;
@end
