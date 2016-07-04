//
//  UIAlertView+Block.h
//  YunZo
//
//  Created by po03 on 14-9-20.
//  Copyright (c) 2014年  yunzo. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef void(^AlertCompleteBlock) (NSInteger buttonIndex);

@interface UIAlertView (Block)

+(UIAlertView *) alert;


/**
 *  用Block的方式回调，这时候会默认用self作为Delegate
 *
 *  @param block <#block description#>
 */
- (void)showAlertViewWithCompleteBlock:(AlertCompleteBlock) block;
@end
