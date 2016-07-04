//
//  SPBaseSystem.h
//  SPay.base
//
//  Created by wongfish on 15/5/7.
//  Copyright (c) 2015年 wongfish. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface SPBaseSystem : NSObject
+ (SPBaseSystem*)sharedInstance;

/**
 *  APP是否是第一次启动,YES表示第一次启动
 */
@property (nonatomic,readonly) BOOL isAPPFirstLaunched;

/**
 *  APP启动次数
 */
@property (nonatomic,readonly) NSInteger appLaunchedNumber;

/**
 *  是否查看引导页
 */
@property (nonatomic,readonly) BOOL isLookAPPIntroductory;

/**
 *  APP版本号
 */
@property (nonatomic,copy) NSString *appVersion;

/**
 *  APP启动
 */
- (void)appLaunched;

/**
 *  完成引导页的查看
 */
- (void)lookIntroductoryFinish;
@end
