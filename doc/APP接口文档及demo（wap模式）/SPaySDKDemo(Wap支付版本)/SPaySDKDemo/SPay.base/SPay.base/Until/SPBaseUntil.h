//
//  SPBaseUntil.h
//  SPay.base
//
//  Created by wongfish on 15/5/7.
//  Copyright (c) 2015年 wongfish. All rights reserved.
//

#import <Foundation/Foundation.h>

#define SPBaseUntilInstance  ([SPBaseUntil sharedInstance])

@interface SPBaseUntil : NSObject
+ (SPBaseUntil*)sharedInstance;

/**
 *  保存UserDefaults
 *
 *  @param info 值
 *  @param key  键
 */
- (void)saveUserDefaults:(id)info
                  forKey:(NSString*)key;

/**
 *  通过键获取保存在UserDefaults里面的值
 *
 *  @param key 键
 *
 *  @return <#return value description#>
 */
- (id)userDefaults:(NSString*)key;

/**
 *  切换到指定的stroyboard
 *
 *  @param stroyboardName <#stroyboardName description#>
 */
- (void)pushToStroyboard:(NSString *)stroyboardName;

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
          viewControllerName:(NSString *)ctrlName;

@end
