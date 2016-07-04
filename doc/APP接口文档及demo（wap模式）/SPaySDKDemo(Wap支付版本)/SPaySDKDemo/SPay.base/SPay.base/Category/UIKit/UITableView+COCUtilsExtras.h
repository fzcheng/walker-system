//
//  UITableView+COCUtilsExtras.h
//  BPLFoundation
//
//  Created by fish.wong on 15/1/17.
//  Copyright (c) 2015年 fish. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef void(^CompleteBlock) (NSSet *touches, UIEvent* event);

@interface UITableView (COCUtilsExtras)

- (void)touchesBeganWithEventBlock:(CompleteBlock)block;
+ (void)setupWithInit;
@end
