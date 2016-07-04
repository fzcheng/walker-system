//
//  SPBaseSystem.m
//  SPay.base
//
//  Created by wongfish on 15/5/7.
//  Copyright (c) 2015年 wongfish. All rights reserved.
//

#import "SPBaseSystem.h"
#import "SPBaseConst.h"
#import "SPBaseUntil.h"

@implementation SPBaseSystem

+ (SPBaseSystem*)sharedInstance{
    
    static SPBaseSystem *instance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        instance = [[SPBaseSystem alloc] init];
    });
    return instance;
}

- (BOOL)isAPPFirstLaunched{
    
    if (self.appLaunchedNumber > 0) {
        
        return NO;
    }
    return YES;
}

- (NSInteger)appLaunchedNumber{

    return [[[SPBaseUntil sharedInstance] userDefaults:kAPPFirstLaunchedNumberKey] integerValue];
};

- (BOOL)isLookAPPIntroductory{
    
   return [[[SPBaseUntil sharedInstance] userDefaults:kLookIntroductory] boolValue];
}


/**
 *  APP启动
 */
- (void)appLaunched{
    [self saveLaunchedNumber:(self.appLaunchedNumber+1)];
}

/**
 *  保存APP启动次数
 *
 *  @param launchedNumber <#launchedNumber description#>
 */
- (void)saveLaunchedNumber:(NSInteger)launchedNumber{
    
    [[SPBaseUntil sharedInstance] saveUserDefaults:[NSNumber numberWithInteger:launchedNumber] forKey:kAPPFirstLaunchedNumberKey];
}

/**
 *  完成引导页的查看
 */
- (void)lookIntroductoryFinish{

    [[SPBaseUntil sharedInstance] saveUserDefaults:[NSNumber numberWithBool:YES] forKey:kLookIntroductory];
}


- (NSString*)appVersion{
    return [[NSBundle mainBundle] objectForInfoDictionaryKey:@"CFBundleVersion"];;
}
@end
