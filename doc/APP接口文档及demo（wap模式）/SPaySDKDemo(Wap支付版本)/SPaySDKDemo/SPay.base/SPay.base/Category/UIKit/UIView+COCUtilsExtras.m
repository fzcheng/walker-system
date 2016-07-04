//
//  UIView+COCUtilsExtras.m
//  BPLFoundation
//
//  Created by fish.wong on 15/1/6.
//  Copyright (c) 2015年 fish. All rights reserved.
//

#import "UIView+COCUtilsExtras.h"

@implementation UIView (COCUtilsExtras)


#pragma mark - frame
/**
 *  设置view的Width
 *
 *  @param width <#width description#>
 */
-(void)setViewWidth:(CGFloat)width{
    CGRect newframe = self.frame;
    newframe.size.width = width;
    self.frame = newframe;
}

/**
 *  设置view的height
 *
 *  @param height <#width description#>
 */
-(void)setViewHeight:(CGFloat)height{
    CGRect newframe = self.frame;
    newframe.size.height = height;
    self.frame = newframe;
}

/**
 *  设置view的x
 *
 *  @param x <#width description#>
 */
-(void)setViewX:(CGFloat)x{
    CGRect newframe = self.frame;
    newframe.origin.x = x;
    self.frame = newframe;
}

/**
 *  设置view的y
 *
 *  @param y <#width description#>
 */
-(void)setViewY:(CGFloat)y{
    CGRect newframe = self.frame;
    newframe.origin.y = y;
    self.frame = newframe;
}


/**
 *  获取view的 X+W值
 *
 *  @return <#return value description#>
 */
- (CGFloat)viewXW{
    return self.frame.origin.x + self.frame.size.width;
}

/**
 *  获取view的Y+H值
 *
 *  @return <#return value description#>
 */
- (CGFloat)viewYH{
    return self.frame.origin.y + self.frame.size.height;
}



/**
 *  将一个正方形的view切成圆形
 */
- (void)viewToRound{
    self.layer.cornerRadius = self.bounds.size.height / 2.0f;
    self.layer.masksToBounds = YES;
}





@end
