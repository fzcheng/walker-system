//
//  SPBaseView.m
//  SPay.base
//
//  Created by wongfish on 15/5/7.
//  Copyright (c) 2015年 wongfish. All rights reserved.
//

#import "SPBaseView.h"

@implementation SPBaseView


#pragma mark - static action
/**
 *  初始化一个SPBaseView子类的静态对象，必须是使用XIB
 *
 *  @return <#return value description#>
 */
+ (SPBaseView*)instanceXib
{
    static SPBaseView *instance = nil;
    static dispatch_once_t onceToken;
    static NSString *previousClassNameString = @"";
    NSString *classNameString = [NSString stringWithUTF8String:object_getClassName(self)];
    
    
    //如果对象不一致则重新实例化一个单列对象
    if (![previousClassNameString isEqualToString:classNameString]) {
        previousClassNameString = classNameString;
        instance = nil;
        onceToken = 0;
    }
    
    dispatch_once(&onceToken, ^{
        instance = [self setupWithXIBInit];
    });
    return instance;
}


/**
 *  初始化一个SPBaseView子类对象，必须是使用XIB
 *
 *  @return <#return value description#>
 */
+ (SPBaseView *)setupWithXIBInit {
    NSString *classNameString = [NSString stringWithUTF8String:object_getClassName(self)];
    
    NSArray *nib = [[NSBundle mainBundle] loadNibNamed:classNameString owner:self options:nil];
    
    SPBaseView *aView = nil;
    if ([nib count] > 0) {
        
        aView = [nib objectAtIndex:0];
        return aView;
    }
    return nil;
}

/**
 *  初始化view
 *
 *  @return <#return value description#>
 */
+ (SPBaseView *)setupWithInit{

    SPBaseView *aView = [[SPBaseView alloc] init];
    return aView;
}

#pragma mark - dealloc
- (void)dealloc{

    self.spBaseViewActionBlock = nil;
}


#pragma mark - object action
/**
 *  加载数据
 *
 *  @param info <#info description#>
 */
- (void)loadWithData:(id)info{

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


@end
