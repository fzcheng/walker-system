//
//  SPBaseViewController.h
//  SPay.base
//
//  Created by wongfish on 15/5/7.
//  Copyright (c) 2015年 wongfish. All rights reserved.
//

#import <UIKit/UIKit.h>


@class SPBaseViewController;


/**
 *  上个页面即将返回的方法
 *
 *  @param backCtrl 从某个页面返回回来的，控制器
 *  @param info     数据源（自定义）
 *  @param type     类型（自定义）
 */
typedef void (^SPBaseViewControllerWillBackAction)(SPBaseViewController *backCtrl,
                                                   id info,
                                                   int type);


/**
 *  本页面将要执行返回的时候调用方法
 *
 *  @param toCtrl 将要返回的上一级控制器,可以为nil
 */
typedef void (^SPBaseViewControllerCurPageWillBackAction)(SPBaseViewController *toCtrl);


@interface SPBaseViewController : UIViewController

/**
 *   viewWillAppear调用次数
 */
@property (nonatomic,assign,readonly)  NSInteger pushWillAppearCount;

/**
 *  采用何种方式进入的该控制器，YES表示Present进入的，NO表示Push进入。
 */
@property (nonatomic,assign) BOOL isPresentCome;


/**
 *  上个页面即将返回的方法
 */
@property (nonatomic,copy) SPBaseViewControllerWillBackAction spBaseCtrlWillBackAction;

/**
 *  本页面将要执行返回的时候调用方法
 */
@property (nonatomic,copy) SPBaseViewControllerCurPageWillBackAction spBaseCtrlCurPageWillBackAction;

/**
 *  返回按钮
 */
@property (nonatomic,strong) IBOutlet UIBarButtonItem *backButton;


/**
 *  提交按钮
 */
@property (nonatomic,strong) IBOutlet UIBarButtonItem *commitButtonBar;



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

/**
 *  退出当前控制器
 *
 *  @param sender <#sender description#>
 */
- (IBAction)backEventAction:(id)sender;

/**
 *
 *
 *  @param sender <#sender description#>
 */
- (IBAction)commitButton:(id)sender;

/**
 *  退出当前控制器
 */
- (void)backAction;

/**
 *  隐藏键盘
 */
-(void)hideKeyboard;


/**
 *  注册键盘通知
 *
 *  @param keyboardBlock 回调通知
 */
- (void)registerKeyboardNotificat:(void (^)(NSNotification *note, const NSString *keyboardStateKey))keyboardBlock;
@end
