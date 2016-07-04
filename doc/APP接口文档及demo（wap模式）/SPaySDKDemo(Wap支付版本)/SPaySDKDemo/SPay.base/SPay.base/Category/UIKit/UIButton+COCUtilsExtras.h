//
//  UIButton+COCUtilsExtras.h
//  YunZo
//
//  Created by po03 on 14-5-28.
//  Copyright (c) 2014年  yunzo. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIButton (COCUtilsExtras)

@property (nonatomic,weak,readonly) UIImageView *customBackgroundImageView;


- (void)customBackgroundImageViewNormal:(UIImage*)normalImage
                      imageViewSelected:(UIImage*)imageViewSelected;

/**
 *  设置button的title
 *
 *  @param titleString <#titleString description#>
 */
- (void)buttonAllStyleTitle:(NSString*)titleString;

- (void)buttonAllStyleTitleColor:(UIColor*)color;

- (void)buttonAllStyleImage:(UIImage*)image;



/**
 *  圆样式
 *
 *  @param circleImage <#circleImage description#>
 */
- (void)buttonCircleStyle:(UIImage*)circleImage;

- (void)buttonAllImage:(UIImage*)image;

- (void)iOS7style;

/**
 *  APP运行的时候只能调用一次，不能多次调用可能把方法还原
 */
+ (void)setupInit;

@end
