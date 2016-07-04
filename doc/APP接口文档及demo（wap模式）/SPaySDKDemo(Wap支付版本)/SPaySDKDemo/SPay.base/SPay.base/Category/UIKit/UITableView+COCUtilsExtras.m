//
//  UITableView+COCUtilsExtras.m
//  BPLFoundation
//
//  Created by fish.wong on 15/1/17.
//  Copyright (c) 2015年 fish. All rights reserved.
//

#import "UITableView+COCUtilsExtras.h"
#import <objc/runtime.h>
static char key ;
@implementation UITableView (COCUtilsExtras)


// 用Block的方式回调
- (void)touchesBeganWithEventBlock:(CompleteBlock)block
{
    if (block) {
        ////移除所有关联
        objc_removeAssociatedObjects(self);
        /**
         1 创建关联（源对象，关键字，关联的对象和一个关联策略。)
         2 关键字是一个void类型的指针。每一个关联的关键字必须是唯一的。通常都是会采用静态变量来作为关键字。
         3 关联策略表明了相关的对象是通过赋值，保留引用还是复制的方式进行关联的；关联是原子的还是非原子的。这里的关联策略和声明属性时的很类似。
         */
        objc_setAssociatedObject(self, &key, block, OBJC_ASSOCIATION_COPY);
    }
}

+ (void)setupWithInit{
    Method originalMenthod = class_getInstanceMethod([self class], @selector(touchesBegan:withEvent:));
    Method swappedMenthod = class_getInstanceMethod([self class], @selector(extrasTouchesBegan:withEvent:));
    method_exchangeImplementations(originalMenthod, swappedMenthod);
}


-(void)extrasTouchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
    CompleteBlock block = objc_getAssociatedObject(self, &key);
    if (block) {
        ///block传值
        block(touches,event);
    }
    [self extrasTouchesBegan:touches withEvent:event];
}
@end
