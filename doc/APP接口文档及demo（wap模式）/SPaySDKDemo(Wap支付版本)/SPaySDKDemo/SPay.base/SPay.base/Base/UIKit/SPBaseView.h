//
//  SPBaseView.h
//  SPay.base
//
//  Created by wongfish on 15/5/7.
//  Copyright (c) 2015年 wongfish. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@class SPBaseView;

/**
 *  view block 回调
 *
 *  @param info <#info description#>
 *  @param type <#type description#>
 */
typedef void (^SPBaseViewActionCallBack)(id info,
                                         int type);


/**
 *  view block通知
 *
 *  @param spView   <#spView description#>
 *  @param info     <#info description#>
 *  @param type     <#type description#>
 *  @param callBack <#callBack description#>
 */
typedef void (^SPBaseViewAction)(SPBaseView *spView,
                                 id info,
                                 int type,
                                 SPBaseViewActionCallBack callBack);

@interface SPBaseView : UIView



/**
 *  初始化一个SPBaseView子类对象，必须是使用XIB
 *
 *  @return <#return value description#>
 */
+ (SPBaseView *)setupWithXIBInit;


/**
 *  初始化一个SPBaseView子类的静态对象，必须是使用XIB
 *
 *  @return <#return value description#>
 */
+ (SPBaseView*)instanceXib;


/**
 *  初始化view
 *
 *  @return <#return value description#>
 */
+ (SPBaseView *)setupWithInit;

/**
 *  view block通知
 */
@property (nonatomic, copy) SPBaseViewAction spBaseViewActionBlock;

/**
 *  view索引
 */
@property (nonatomic,assign) NSInteger index;


/**
 *  加载数据
 *
 *  @param info <#info description#>
 */
- (void)loadWithData:(id)info;


/**
 *  初始化view
 */
- (void)setupWithViews;

/**
 *  初始化数据
 */
- (void)setupWithDatas;


/**
 *  初始化Action
 */
- (void)setupWithAction;


@end
