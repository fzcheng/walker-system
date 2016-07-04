//
//  SPBaseUntil.m
//  SPay.base
//
//  Created by wongfish on 15/5/7.
//  Copyright (c) 2015年 wongfish. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SPBaseUntil.h"

@implementation SPBaseUntil

+ (SPBaseUntil*)sharedInstance{
    
    static SPBaseUntil *instance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        instance = [[SPBaseUntil alloc] init];
    });
    return instance;
}

/**
 *  保存UserDefaults
 *
 *  @param info 值
 *  @param key  键
 */
- (void)saveUserDefaults:(id)info
                  forKey:(NSString*)key{
    if (key && info) {
        NSUserDefaults *ud = [NSUserDefaults standardUserDefaults];
        [ud setObject:info forKey:key];
        [ud synchronize];
    }
}

/**
 *  通过键获取保存在UserDefaults里面的值
 *
 *  @param key 键
 *
 *  @return <#return value description#>
 */
- (id)userDefaults:(NSString*)key{
    if (key) {
        NSUserDefaults *ud = [NSUserDefaults standardUserDefaults];
        return [ud objectForKey:key];
    }
    return nil;
}



/**
 *  切换到指定的stroyboard
 *
 *  @param stroyboardName <#stroyboardName description#>
 */
- (void)pushToStroyboard:(NSString *)stroyboardName{
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:stroyboardName bundle:nil];
    UIViewController *toCtrl = [storyboard instantiateInitialViewController];
    [[UIApplication sharedApplication] delegate].window.rootViewController = toCtrl;
}

/**
 *  切换到指定的控制器
 *
 *  @param viewCtrl       控制器
 *  @param stroyboardName stroyboard
 *  @param ctrlName       to控制器
 *
 *  @return <#return value description#>
 */
- (id)pushfromViewController:(id)viewCtrl
                  stroyboard:(NSString *)stroyboardName
          viewControllerName:(NSString *)ctrlName{

    
    UIViewController *fromCtrl = (UIViewController *)viewCtrl;
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:stroyboardName bundle:nil];
    UIViewController *toCtrl = [storyboard instantiateViewControllerWithIdentifier:ctrlName];
    [fromCtrl.navigationController pushViewController:toCtrl animated:YES];
    return toCtrl;
};

@end
