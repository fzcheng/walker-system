//
//  SPBaseAppDelegate.m
//  SPay.base
//
//  Created by wongfish on 15/5/7.
//  Copyright (c) 2015年 wongfish. All rights reserved.
//

#import "SPBaseAppDelegate.h"
#import "SPBaseSystem.h"

@implementation SPBaseAppDelegate



+ (SPBaseAppDelegate*)sharedInstance{
    
    static SPBaseAppDelegate *instance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        instance = [[SPBaseAppDelegate alloc] init];
    });
    return instance;
}


/**
 *  APP启动方法
 *
 *  @param application   <#application description#>
 *  @param launchOptions <#launchOptions description#>
 *
 *  @return <#return value description#>
 */
- (void)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions{

    [[SPBaseSystem sharedInstance] appLaunched];
};

@end
