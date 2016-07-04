//
//  SPBaseViewController.m
//  SPay.base
//
//  Created by wongfish on 15/5/7.
//  Copyright (c) 2015年 wongfish. All rights reserved.
//

#import "SPBaseViewController.h"
@interface SPBaseViewController ()

@end

@implementation SPBaseViewController

#pragma mark - dealloc
- (void)dealloc{
    _backButton = nil;
    _commitButtonBar = nil;
}


- (void)viewDidLoad {
    [super viewDidLoad];


    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


- (void)viewWillAppear:(BOOL)animated{
    
    if (_pushWillAppearCount == 0) {
        
        [self setupWithDatas];
        [self setupWithViews];
        [self setupWithAction];
    }
    _pushWillAppearCount++;
    [super viewWillAppear:animated];
}


/**
 *  返回
 */
- (void)backAction {
    [self backAction:nil type:0];
}

- (IBAction)backEventAction:(id)sender{
    [self backAction];
}

/**
 *
 *
 *  @param sender <#sender description#>
 */
- (IBAction)commitButton:(id)sender{

}


- (void)backAction:(id)info
              type:(int)type{
    
    if (self.spBaseCtrlCurPageWillBackAction) {
        self.spBaseCtrlCurPageWillBackAction(nil);
    }
    
    if (self.spBaseCtrlWillBackAction) {
        self.spBaseCtrlWillBackAction(self,info,type);
    }
    
    if (self.isPresentCome) {
        [self dismissViewControllerAnimated:YES completion:nil];
    }else{
        [self.navigationController popViewControllerAnimated:YES];
    }
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event{
    [self hideKeyboard];
}

/**
 *  影藏键盘
 */
-(void)hideKeyboard{
    [self.view endEditing:YES];
}


/**
 *  初始化view
 */
- (void)setupWithViews{
    

}

/**
 *  初始化数据
 */
- (void)setupWithDatas{
    
}

/**
 *  初始化Action
 */
- (void)setupWithAction{
    
    
}


/**
 *  注册键盘通知
 *
 *  @param keyboardBlock 回调通知
 */
- (void)registerKeyboardNotificat:(void (^)(NSNotification *note, const NSString *keyboardStateKey))keyboardBlock{
    
    
    [[NSNotificationCenter defaultCenter] addObserverForName:UIKeyboardWillShowNotification object:nil
                                                       queue:[NSOperationQueue mainQueue]
                                                  usingBlock:^(NSNotification *note) {
                                                      keyboardBlock(note,UIKeyboardWillShowNotification);
                                                  }];
    
    [[NSNotificationCenter defaultCenter] addObserverForName:UIKeyboardWillHideNotification object:nil
                                                       queue:[NSOperationQueue mainQueue]
                                                  usingBlock:^(NSNotification *note) {
                                                      keyboardBlock(note,UIKeyboardWillHideNotification);
                                                  }];
}


@end
