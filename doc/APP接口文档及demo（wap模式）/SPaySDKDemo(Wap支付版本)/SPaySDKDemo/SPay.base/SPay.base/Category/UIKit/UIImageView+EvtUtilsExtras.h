//
//  UIImageView+EvtUtilsExtras.h
//  YunZo
//
//  Created by po03 on 14-8-21.
//  Copyright (c) 2014年  yunzo. All rights reserved.
//

#import <UIKit/UIKit.h>
#define circleStyleImage ([UIImage imageNamed:@"inviteUserPhotoDefaultCircle@2x.png"])

@interface UIImageView (EvtUtilsExtras)

/**
 *  圆样式
 *
 *  @param circleImage <#circleImage description#>
 */
- (void)imageCircleStyle:(UIImage*)circleImage;



/**
 *  ImageView自适应
 */
- (void)imageViewContentModeScaleAspectFill;


@end
