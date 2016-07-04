//
//  SPBaseLineView.h
//  SPay.base
//
//  Created by wongfish on 15/6/8.
//  Copyright (c) 2015年 wongfish. All rights reserved.
//

#import "SPBaseView.h"

typedef enum {
    
    //显示上-下分割线
    SPBaseLineTopBottom = 0,
    
    //显示中间分割线
    SPBaseLineCenter = 1
}SPBaseLinType;
//分割线类型

@interface SPBaseLineView : SPBaseView

@property(nonatomic,assign) SPBaseLinType lineStyle;

@end
