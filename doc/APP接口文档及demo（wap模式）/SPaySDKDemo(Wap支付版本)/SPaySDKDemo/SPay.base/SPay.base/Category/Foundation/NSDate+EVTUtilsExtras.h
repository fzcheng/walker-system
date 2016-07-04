//
//  NSDate_EVTUtilsExtras.h
//  EVTUtils
//
//  Created by xeonwell on 28/09/2011.
//  Copyright 2011 EVT, Inc. All rights reserved.
//
#define D_MINUTE	60
#define D_HOUR		3600
#define D_DAY		86400
#define D_WEEK		604800
#define D_YEAR		31556926

#import <Foundation/Foundation.h>


@interface NSDate (NSDate_EVTUtilsExtras)

//获取当天时间00：00开始
+ (NSDate*) today;

//按指定字符串格式返回当前日期格式化后的字符串
- (NSString *)stringWithFormat:(NSString *)format;
- (NSString*)dateString;

- (NSString*)todayString;

- (NSString *)stringWithCurrentTimeZoneFormat:(NSString *)format;

//按指定字符串格式返回当前日期 语言 格式化后的字符串
- (NSString *)stringWithFormat:(NSString *)format locale:(NSString *)locale;

// Relative dates from the current date
+ (NSDate *) dateTomorrow;
+ (NSDate *) dateYesterday;
+ (NSDate *) dateWithDaysFromNow: (NSUInteger) days;
+ (NSDate *) dateWithDaysBeforeNow: (NSUInteger) days;
+ (NSDate *) dateWithHoursFromNow: (NSUInteger) dHours;
+ (NSDate *) dateWithHoursBeforeNow: (NSUInteger) dHours;
+ (NSDate *) dateWithMinutesFromNow: (NSUInteger) dMinutes;
+ (NSDate *) dateWithMinutesBeforeNow: (NSUInteger) dMinutes;


/**
 *  获取时间线
 *
 *  @return
 */
- (NSString *)timeLine;
@end
