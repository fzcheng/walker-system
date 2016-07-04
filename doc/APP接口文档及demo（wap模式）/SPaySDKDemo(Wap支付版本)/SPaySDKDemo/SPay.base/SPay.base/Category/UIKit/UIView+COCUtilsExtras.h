//
//  UIView+COCUtilsExtras.h
//  BPLFoundation
//
//  Created by fish.wong on 15/1/6.
//  Copyright (c) 2015年 fish. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIView (COCUtilsExtras)



#pragma mark - frame
/**
 *  设置view的Width
 *
 *  @param width <#width description#>
 */
-(void)setViewWidth:(CGFloat)width;

/**
 *  设置view的height
 *
 *  @param height <#width description#>
 */
-(void)setViewHeight:(CGFloat)height;

/**
 *  设置view的x
 *
 *  @param x <#width description#>
 */
-(void)setViewX:(CGFloat)x;


/**
 *  设置view的y
 *
 *  @param y <#width description#>
 */
-(void)setViewY:(CGFloat)y;


/**
 *  获取view的 X+W值
 *
 *  @return <#return value description#>
 */
- (CGFloat)viewXW;

/**
 *  获取view的Y+H值
 *
 *  @return <#return value description#>
 */
- (CGFloat)viewYH;



/**
 *  将一个正方形的view切成圆形
 */
- (void)viewToRound;



@end
