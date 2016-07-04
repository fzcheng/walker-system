//
//  NSNumber+SPUtilsExtras.m
//  SPay.base
//
//  Created by wongfish on 15/5/13.
//  Copyright (c) 2015年 wongfish. All rights reserved.
//

#import "NSNumber+SPUtilsExtras.h"
#import "NSDate+EVTUtilsExtras.h"
@implementation NSNumber (SPUtilsExtras)

/**
 *  SPay货币类型
 *
 *  @return <#return value description#>
 */
- (NSString*)spCurrencyString{
    NSNumberFormatter *formatter = [[NSNumberFormatter alloc] init];
    //       [formatter setPositiveFormat:@"###,##0.00;"];
    formatter.numberStyle = kCFNumberFormatterCurrencyStyle;
    formatter.currencySymbol = @"¥";
    
    
    NSString *currency = [formatter stringFromNumber:self];
    NSArray *array = [currency componentsSeparatedByString:@"."];
    if (array.count == 2) {
        
        if ([array.lastObject length] > 1) {
            
            NSString *key =  [array.lastObject substringFromIndex:1];
            if ([key isEqualToString:@"0"]) {
                
                currency = [[array firstObject] stringByAppendingString:[NSString stringWithFormat:@".%@",[array.lastObject substringToIndex:1]]];
            }
        }

//        if ([array.lastObject isEqualToString:@"00"]) {
//            currency = [array firstObject];
//        }else{
//            
//            if ([array.lastObject length] > 1) {
//                
//                NSString *key =  [array.lastObject substringFromIndex:1];
//                if ([key isEqualToString:@"0"]) {
//                    
//                    currency = [[array firstObject] stringByAppendingString:[NSString stringWithFormat:@".%@",[array.lastObject substringToIndex:1]]];
//                }
//            }
//        }
    }
    return  currency;
}

- (NSString*)spInputCurrencyString{
    NSNumberFormatter *formatter = [[NSNumberFormatter alloc] init];
    //       [formatter setPositiveFormat:@"###,##0.00;"];
    formatter.numberStyle = kCFNumberFormatterCurrencyStyle;
    formatter.currencySymbol = @"¥";
    
    
    NSString *currency = [formatter stringFromNumber:self];
    NSArray *array = [currency componentsSeparatedByString:@"."];
    if (array.count == 2) {
      currency = [array firstObject];
    }
    
    return  currency;
}



/**
 *  转换为货币类型
 *
 *  @return <#return value description#>
 */
- (NSString*)currencyString{
    NSNumberFormatter *formatter = [[NSNumberFormatter alloc] init];
    formatter.numberStyle = kCFNumberFormatterCurrencyStyle;
    formatter.currencySymbol = @"¥";
    NSString *currency = [formatter stringFromNumber:self];
    return currency;
}


/**
 *  Pay货币类型转正常货币类型
 *
 *  @return <#return value description#>
 */
- (NSNumber*)spToCurrency{

    NSInteger num = [self integerValue];
    CGFloat f = num * 0.01;
    return [NSNumber numberWithDouble:f];
}

/**
 *  将服务器返回的时间戳转换,服务器是精确到毫秒的
 *
 *  @return <#return value description#>
 */
- (NSNumber*)toTimeIntervalSince1970{
    
    long long i = [self longLongValue];
    return   [NSNumber numberWithLongLong:(i / 1000)];
}

- (NSString*)toDateString{

    NSDate *date = [NSDate dateWithTimeIntervalSince1970:[self integerValue]];
    return [date dateString];
    
}
/**
 *  退款状态类型
 *
 *  @return <#return value description#>
 */
- (NSString*)refundStateString{

    NSInteger state = [self integerValue];
    NSString *stateString;
    switch (state) {
        case 1:{
           stateString = @"退款成功";
        }
            break;
        case 2:{
            stateString = @"退款失败";
        }
            break;
        case 3:
        default:
        {
            stateString = @"审核中";
        }
            break;
    }
    return stateString;
}

/**
 *  订单状态
 *
 *  @return <#return value description#>
 */
- (NSString*)orderStateString{
    
    NSInteger state = [self integerValue];
    NSString *stateString;
    
    switch (state) {
        case 1:{
            stateString = @"未支付";
        }
            break;
        case 2:{
           stateString = @"支付成功";
        }
            break;
        case 3:{
            stateString = @"已关闭";
        }
            break;
        case 4:{
          stateString = @"转入退款";
        }
            break;
        case 5:
        default:{
            stateString = @"已冲正";
        }
            break;
    }
    return stateString;
}

@end
