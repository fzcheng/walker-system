//
//  SPBaseSystemDefine.h
//  SPay.base
//
//  Created by wongfish on 15/5/7.
//  Copyright (c) 2015年 wongfish. All rights reserved.
//

#ifndef SPay_base_SPBaseSystemDefine_h
#define SPay_base_SPBaseSystemDefine_h

/**
 *  是否是iOS7
 */
#define iOS7 ([[UIDevice currentDevice].systemVersion floatValue] >= 7.0)
/**
 *  是否是iOS8
 */
#define iOS8 ([[UIDevice currentDevice].systemVersion floatValue] >= 8.0)


/**
 *  是否是iPhone6的屏幕
 */
#define iPhone6 ([UIScreen instancesRespondToSelector:@selector(currentMode)] ? CGSizeEqualToSize(CGSizeMake(750, 1334), [[UIScreen mainScreen] currentMode].size) : NO)

/**
 *  是否是iPhone6_Plus的屏幕
 */
#define iPhone6_Plus ([UIScreen instancesRespondToSelector:@selector(currentMode)] ? CGSizeEqualToSize(CGSizeMake(1242, 2208), [[UIScreen mainScreen] currentMode].size) : NO)

/**
 *  是否是iPhone4的屏幕
 */
#define iPhone4 ([UIScreen instancesRespondToSelector:@selector(currentMode)] ? CGSizeEqualToSize(CGSizeMake(640, 960), [[UIScreen mainScreen] currentMode].size) : NO)





/**
 *  是否是iPhone5的屏幕
 */
#define iPhone5 ([UIScreen instancesRespondToSelector:@selector(currentMode)] ? CGSizeEqualToSize(CGSizeMake(640, 1136), [[UIScreen mainScreen] currentMode].size) : NO)

/**
 *  屏幕高度
 */
#define IPHONE_HEIGHT ([UIScreen mainScreen].bounds.size.height)
/**
 *  屏幕宽度
 */
#define IPHONE_WIDTH  ([UIScreen mainScreen].bounds.size.width)


#endif
