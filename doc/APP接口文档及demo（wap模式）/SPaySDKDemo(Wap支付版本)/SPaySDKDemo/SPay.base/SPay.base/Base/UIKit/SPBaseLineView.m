//
//  SPBaseLineView.m
//  SPay.base
//
//  Created by wongfish on 15/6/8.
//  Copyright (c) 2015年 wongfish. All rights reserved.
//

#import "SPBaseLineView.h"

@implementation SPBaseLineView

- (void)drawRect:(CGRect)rect{
    CGContextRef con = UIGraphicsGetCurrentContext();
    CGContextSetRGBStrokeColor(con, 0.5625f, 0.5625f, 0.5625f, 0.5);
    
    switch (self.lineStyle) {
        case SPBaseLineTopBottom:{
            CGContextMoveToPoint(con, 0, 0);
            CGContextAddLineToPoint(con, rect.size.width, 0);
            
            CGContextMoveToPoint(con, 0, rect.size.height);
            CGContextAddLineToPoint(con, rect.size.width, rect.size.height);
        }
            break;
        case SPBaseLineCenter:{
           
            CGFloat y = ceil(rect.size.height / 2.0f);
            CGContextMoveToPoint(con, 0, y);
            CGContextAddLineToPoint(con, rect.size.width, y);
        }
            break;
        default:
            break;
    }
    
    CGContextDrawPath(con, kCGPathStroke);
}

@end
