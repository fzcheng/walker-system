//
//  NSDate_EVTUtilsExtras.m
//  EVTUtils
//
//  Created by xeonwell on 28/09/2011.
//  Copyright 2011 EVT, Inc. All rights reserved.
//

#import "NSDate+EVTUtilsExtras.h"
#import "SPBaseConst.h"

#define DATE_COMPONENTS (NSCalendarUnitYear| NSCalendarUnitMonth | NSCalendarUnitDay |NSWeekCalendarUnit |  NSCalendarUnitHour | NSCalendarUnitMinute | NSCalendarUnitSecond | NSCalendarUnitWeekday | NSCalendarUnitWeekdayOrdinal)
#define CURRENT_CALENDAR [NSCalendar currentCalendar]

@implementation NSDate (NSDate_EVTUtilsExtras)

+ (NSDate*)today
{
  NSCalendar *gregorian = [[NSCalendar alloc] initWithCalendarIdentifier:NSCalendarIdentifierGregorian];
  
  NSDateComponents *todayComponents = [gregorian components:(NSCalendarUnitDay | NSCalendarUnitMonth | NSCalendarUnitYear) fromDate:[NSDate date]];
  NSInteger theDay = [todayComponents day];
  NSInteger theMonth = [todayComponents month];
  NSInteger theYear = [todayComponents year];
  
  NSDateComponents *components = [[NSDateComponents alloc] init];
  [components setDay:theDay]; 
  [components setMonth:theMonth]; 
  [components setYear:theYear];
  
  NSDate* todayDate = [gregorian dateFromComponents:components];
    
  return todayDate;
}

- (NSString *)stringWithCurrentTimeZoneFormat:(NSString *)format{
	NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    formatter.locale  = [NSLocale currentLocale];
	[formatter setDateFormat:format];
	NSString *result = [formatter stringFromDate:self];
	return result;
}

- (NSString *)stringWithFormat:(NSString *)format{
	NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    formatter.locale  = [NSLocale currentLocale];
	formatter.timeZone = [NSTimeZone timeZoneWithName:@"HKT"];
	[formatter setDateFormat:format];
	NSString *result = [formatter stringFromDate:self];
	return result;
}
- (NSString*)dateString{

    if ([self isKindOfClass:[NSString class]]) {
        return nil;
    }else{
        return [self stringWithFormat:kBPLDateYMDHMSFormatString];
    }
}

- (NSString*)todayString{
  return [self stringWithFormat:@"yyyy-MM-dd"];
}

- (NSString *)stringWithFormat:(NSString *)format locale:(NSString *)locale{
	NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
  [formatter setTimeZone:[NSTimeZone localTimeZone]];
  NSLocale *aLocale = [[NSLocale alloc] initWithLocaleIdentifier:locale];
  [formatter setLocale:aLocale];
	[formatter setDateFormat:format];
	NSString *result = [formatter stringFromDate:self];
	return result;
}

#pragma mark Relative Dates

+ (NSDate *) dateWithDaysFromNow: (NSUInteger) days
{
	NSTimeInterval aTimeInterval = [[NSDate date] timeIntervalSinceReferenceDate] + D_DAY * days;
	NSDate *newDate = [NSDate dateWithTimeIntervalSinceReferenceDate:aTimeInterval];
	return newDate;	
}

+ (NSDate *) dateWithDaysBeforeNow: (NSUInteger) days
{
	NSTimeInterval aTimeInterval = [[NSDate date] timeIntervalSinceReferenceDate] - D_DAY * days;
	NSDate *newDate = [NSDate dateWithTimeIntervalSinceReferenceDate:aTimeInterval];
	return newDate;	
}

+ (NSDate *) dateTomorrow
{
	return [NSDate dateWithDaysFromNow:1];
}

+ (NSDate *) dateYesterday
{
	return [NSDate dateWithDaysBeforeNow:1];
}

+ (NSDate *) dateWithHoursFromNow: (NSUInteger) dHours
{
	NSTimeInterval aTimeInterval = [[NSDate date] timeIntervalSinceReferenceDate] + D_HOUR * dHours;
	NSDate *newDate = [NSDate dateWithTimeIntervalSinceReferenceDate:aTimeInterval];
	return newDate;	
}

+ (NSDate *) dateWithHoursBeforeNow: (NSUInteger) dHours
{
	NSTimeInterval aTimeInterval = [[NSDate date] timeIntervalSinceReferenceDate] - D_HOUR * dHours;
	NSDate *newDate = [NSDate dateWithTimeIntervalSinceReferenceDate:aTimeInterval];
	return newDate;	
}

+ (NSDate *) dateWithMinutesFromNow: (NSUInteger) dMinutes
{
	NSTimeInterval aTimeInterval = [[NSDate date] timeIntervalSinceReferenceDate] + D_MINUTE * dMinutes;
	NSDate *newDate = [NSDate dateWithTimeIntervalSinceReferenceDate:aTimeInterval];
	return newDate;		
}

+ (NSDate *) dateWithMinutesBeforeNow: (NSUInteger) dMinutes
{
	NSTimeInterval aTimeInterval = [[NSDate date] timeIntervalSinceReferenceDate] - D_MINUTE * dMinutes;
	NSDate *newDate = [NSDate dateWithTimeIntervalSinceReferenceDate:aTimeInterval];
	return newDate;		
}

- (NSDate *) dateYesterday
{
    return [self dateWithDaysBefore:1];
}

- (NSDate *) dateTomorrow
{
    return [self dateAfterDays:1];
}

- (NSDate *) dateWithDaysBefore:(NSUInteger)days
{
    NSTimeInterval aTimeInterval = [self timeIntervalSinceReferenceDate] - D_DAY * days;
	NSDate *newDate = [NSDate dateWithTimeIntervalSinceReferenceDate:aTimeInterval];
	return newDate;
}

- (NSDate *) dateAfterDays:(NSUInteger)days
{
    NSTimeInterval aTimeInterval = [self timeIntervalSinceReferenceDate] + D_DAY * days;
	NSDate *newDate = [NSDate dateWithTimeIntervalSinceReferenceDate:aTimeInterval];
	return newDate;
}



/**
 *  获取时间线
 *
 *  @return
 */
- (NSString *)timeLine
{
    NSCalendar *calendarStart = [NSCalendar currentCalendar];
    NSDateComponents *componentsStart = [calendarStart components:(kCFCalendarUnitYear | kCFCalendarUnitMonth | kCFCalendarUnitDay | kCFCalendarUnitHour | kCFCalendarUnitMinute | kCFCalendarUnitSecond) fromDate:self];
    
    NSInteger startYear = [componentsStart year];
    NSInteger startMonth = [componentsStart month];
    NSInteger startDay = [componentsStart day];
    NSInteger startHour = [componentsStart hour];
    NSInteger startMinus = [componentsStart minute];
    //NSInteger startSecond = [componentsStart second];
    
    
    NSDate *now = [NSDate date];
    NSCalendar *calendar = [NSCalendar currentCalendar];
    NSDateComponents *components = [calendar components:(kCFCalendarUnitYear | kCFCalendarUnitMonth | kCFCalendarUnitDay | kCFCalendarUnitHour | kCFCalendarUnitMinute | kCFCalendarUnitSecond) fromDate:now];
    
    NSInteger endYear = [components year];
    
    NSInteger endDay = [components day];
    NSInteger endMonth = [components month];
    //    NSInteger endHour = [components hour];
    //    NSInteger endMinus = [components minute];
    //    NSInteger endSecond = [components second];
    
    NSInteger year = endYear - startYear;
    NSInteger month = endMonth - startMonth;
    NSInteger day = (30 * month) + endDay - startDay;
    
    //    NSInteger hour = endHour - startHour;
    //    NSInteger minute = endMinus - startMinus;
    //    NSInteger second = endSecond - startSecond;
    //    NSLog(@"day1 %d day2 %d",startDay,endDay);
    //    NSLog(@"gap is year:%d month:%ddays:%dhours:%dminutes:%dsecond%d",year,month,day,hour,minute,second);
    
    
//    NSDate *date = [NSDate date];
//    
//    NSTimeZone *zone = [NSTimeZone systemTimeZone];
//    
//    NSInteger interval = [zone secondsFromGMTForDate: date];
//    
//    NSDate *localeDate = [date  dateByAddingTimeInterval: interval];
    
    float time =  fabs([self timeIntervalSinceNow]);
    //    NSLog(@"time interval since now %f",time);
    int minus = time/60;
    //    NSLog(@"minus %d  day %d year %d",minus,day,year);
    if (minus <= 1) {//一分钟内
        return @"刚刚";
    }
    else
        if (1 < minus && minus <= 60) {//一小时内
            return [NSString stringWithFormat:@"%d分钟前",minus];
        }
        else
            if (60 < minus && day == 0) {//今天
                return [NSString stringWithFormat:@"今天 %.2ld:%.2ld",(long)startHour,(long)startMinus];
            }else
                if (day == 1 && year == 0) {//昨天
                    return [NSString stringWithFormat:@"昨天 %.2ld:%.2ld",(long)startHour,(long)startMinus];
                }else
                    if (day > 1 && year == 0) {//今年
                        return [NSString stringWithFormat:@"%.2ld-%.2ld %.2ld:%.2ld",(long)startMonth,(long)startDay,(long)startHour,(long)startMinus];
                    }else//其他年
                        return [NSString stringWithFormat:@"%.4ld-%.2ld-%.2ld %.2ld:%.2ld",(long)startYear,(long)startMonth,(long)startDay,(long)startHour,(long)startMinus];
    
    
    
}
@end
