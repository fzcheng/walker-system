//
//  UIImageView+EvtUtilsExtras.m
//  YunZo
//
//  Created by po03 on 14-8-21.
//  Copyright (c) 2014年  yunzo. All rights reserved.
//

#import "UIImageView+EvtUtilsExtras.h"

@implementation UIImageView (EvtUtilsExtras)


/**
 *  圆样式
 *
 *  @param circleImage <#circleImage description#>
 */
- (void)imageCircleStyle:(UIImage*)circleImage{
    CALayer *maskLayer = [CALayer layer];
    maskLayer.backgroundColor = [[UIColor colorWithRed:1.0f green:1.0f blue:1.0f alpha:0.0f] CGColor];
    maskLayer.contents = (id)[circleImage CGImage];
    maskLayer.contentsGravity = kCAGravityCenter;
    maskLayer.frame = CGRectMake(0,
                                 0.0f,
                                 self.frame.size.width,
                                 self.frame.size.height);
    self.layer.mask = maskLayer;
}


/**
 *  ImageView自适应
 */
- (void)imageViewContentModeScaleAspectFill{
    [self setContentMode:UIViewContentModeScaleAspectFill];
    [self setClipsToBounds:YES];
}
@end
