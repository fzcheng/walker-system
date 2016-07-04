//
//  UIImage+EvtUtilsExtras.m
//  YunZo
//
//  Created by po03 on 14/10/21.
//  Copyright (c) 2014年  yunzo. All rights reserved.
//

#import "UIImage+EvtUtilsExtras.h"

@implementation UIImage (EvtUtilsExtras)
- (UIImage *)scaleImage:(float)scaleSize

{
    UIImage *image = self;
    UIGraphicsBeginImageContext(CGSizeMake(image.size.width * scaleSize, image.size.height * scaleSize));
    
    [image drawInRect:CGRectMake(0, 0, image.size.width * scaleSize, image.size.height * scaleSize)];
    UIImage *scaledImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    return scaledImage;
    
}


- (UIImage *)reSizeImage:(CGSize)reSize
{
    
     UIImage *image = self;
    UIGraphicsBeginImageContext(CGSizeMake(reSize.width, reSize.height));
    [image drawInRect:CGRectMake(0, 0, reSize.width, reSize.height)];
    UIImage *reSizeImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    return reSizeImage;
    
}
@end
