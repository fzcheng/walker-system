//
//  SPBaseCollectionViewCell.h
//  SPay.base
//
//  Created by wongfish on 15/5/7.
//  Copyright (c) 2015年 wongfish. All rights reserved.
//

#import <UIKit/UIKit.h>


@class SPBaseCollectionViewCell;

/**
 *  view block 回调
 *
 *  @param info <#info description#>
 *  @param type <#type description#>
 */
typedef void (^SPBaseCollectionViewCellActionCallBack)(id info,
                                                  int type);


/**
 *  view block通知
 *
 *  @param spView   <#spView description#>
 *  @param info     <#info description#>
 *  @param type     <#type description#>
 *  @param callBack <#callBack description#>
 */
typedef void (^SPBaseTableViewCellAction)(SPBaseCollectionViewCell *spCell,
                                          id info,
                                          int type,
                                          SPBaseCollectionViewCellActionCallBack callBack);


@interface SPBaseCollectionViewCell : UICollectionViewCell

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
