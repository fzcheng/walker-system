//
//  SPBaseTableViewCell.h
//  SPay.base
//
//  Created by wongfish on 15/5/7.
//  Copyright (c) 2015年 wongfish. All rights reserved.
//

#import <UIKit/UIKit.h>



@class SPBaseTableViewCell;

/**
 *  view block 回调
 *
 *  @param info <#info description#>
 *  @param type <#type description#>
 */
typedef void (^SPBaseTableViewCellActionCallBack)(id info,
                                         int type);


/**
 *  view block通知
 *
 *  @param spView   <#spView description#>
 *  @param info     <#info description#>
 *  @param type     <#type description#>
 *  @param callBack <#callBack description#>
 */
typedef void (^SPBaseTableViewCellAction)(SPBaseTableViewCell *spCell,
                                 id info,
                                 int type,
                                 SPBaseTableViewCellActionCallBack callBack);

@interface SPBaseTableViewCell : UITableViewCell


/**
 *  cell block通知
 */
@property (nonatomic, copy) SPBaseTableViewCellAction spBaseCellActionBlock;

/**
 *  cell索引
 */
@property (nonatomic,assign) NSInteger index;

/**
 *  cell索引
 */
@property(nonatomic, strong) NSIndexPath *indexPath;


/**
 *  初始化一个SPBaseView子类对象，必须是使用XIB
 *
 *  @return <#return value description#>
 */
+ (SPBaseTableViewCell *)setupWithXIBInit;


/**
 *  初始化一个SPBaseView子类的静态对象，必须是使用XIB
 *
 *  @return <#return value description#>
 */
+ (SPBaseTableViewCell*)instanceXib;


/**
 *  初始化一个SPBaseView子类对象，必须是使用XIB
 *
 *  @param identifier <#identifier description#>
 *
 *  @return <#return value description#>
 */
+ (SPBaseTableViewCell *)setupWithXIBInit:(NSString*)identifier;



/**
 *  加载数据
 *
 *  @param info <#info description#>
 */
- (void)loadWithData:(id)info;


/**
 *  初始化view
 */
- (void)setupWithViews;

/**
 *  初始化数据
 */
- (void)setupWithDatas;


/**
 *  初始化Action
 */
- (void)setupWithAction;


@end