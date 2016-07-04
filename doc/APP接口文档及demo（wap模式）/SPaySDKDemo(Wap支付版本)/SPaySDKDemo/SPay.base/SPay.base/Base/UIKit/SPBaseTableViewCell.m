//
//  SPBaseTableViewCell.m
//  SPay.base
//
//  Created by wongfish on 15/5/7.
//  Copyright (c) 2015年 wongfish. All rights reserved.
//

#import "SPBaseTableViewCell.h"

@implementation SPBaseTableViewCell



#pragma mark - static action
/**
 *  初始化一个SPBaseView子类的静态对象，必须是使用XIB
 *
 *  @return <#return value description#>
 */
+ (SPBaseTableViewCell*)instanceXib
{
    static SPBaseTableViewCell *instance = nil;
    static dispatch_once_t onceToken;
    static NSString *previousClassNameString = @"";
    NSString *classNameString = [NSString stringWithUTF8String:object_getClassName(self)];
    
    
    //如果对象不一致则重新实例化一个单列对象
    if (![previousClassNameString isEqualToString:classNameString]) {
        previousClassNameString = classNameString;
        instance = nil;
        onceToken = 0;
    }
    
    dispatch_once(&onceToken, ^{
        instance = [self setupWithXIBInit];
    });
    return instance;
}


/**
 *  初始化一个SPBaseView子类对象，必须是使用XIB
 *
 *  @return <#return value description#>
 */
+ (SPBaseTableViewCell *)setupWithXIBInit {
    NSString *classNameString = [NSString stringWithUTF8String:object_getClassName(self)];
    
    return [self setupWithXIBInit:classNameString];
}

/**
 *  初始化一个SPBaseView子类对象，必须是使用XIB
 *
 *  @param identifier <#identifier description#>
 *
 *  @return <#return value description#>
 */
+ (SPBaseTableViewCell *)setupWithXIBInit:(NSString*)identifier{
    
    NSArray *nib = [[NSBundle mainBundle] loadNibNamed:identifier owner:self options:nil];
    SPBaseTableViewCell *aCell = nil;
    if ([nib count] > 0) {
        
        aCell = [nib objectAtIndex:0];
        return aCell;
    }
    return nil;
}



#pragma mark - dealloc
- (void)dealloc{
    
    self.spBaseCellActionBlock = nil;
    self.indexPath = nil;
}

- (id)initWithCoder:(NSCoder *)aDecoder {
    self = [super initWithCoder:aDecoder];
    if (self) {
        
        [self setupWithViews];
    }
    return self;
}

#pragma mark - object action
/**
 *  加载数据
 *
 *  @param info <#info description#>
 */
- (void)loadWithData:(id)info{
    
}


/**
 *  初始化view
 */
- (void)setupWithViews{
    
}

/**
 *  初始化数据
 */
- (void)setupWithDatas{
    
}

/**
 *  初始化Action
 */
- (void)setupWithAction{
    
}


@end
