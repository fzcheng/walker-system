//
//  SPBaseRGBDefine.h
//  SPay.base
//
//  Created by wongfish on 15/5/8.
//  Copyright (c) 2015年 wongfish. All rights reserved.
//

#ifndef SPay_base_SPBaseRGBDefine_h
#define SPay_base_SPBaseRGBDefine_h

#define RGBCOLOR(r,g,b) [UIColor colorWithRed:(r)/255.0 green:(g)/255.0 blue:(b)/255.0 alpha:1]
#define RGBACOLOR(r,g,b,a) [UIColor colorWithRed:(r)/255.0 green:(g)/255.0 blue:(b)/255.0 \
alpha:(a)]
#define RGBACOLORCG(r,g,b,a) [[UIColor colorWithRed:(r)/255.0 green:(g)/255.0 blue:(b)/255.0 \
alpha:(a)] CGColor]
#define HSVCOLOR(h,s,v) [UIColor colorWithHue:(h) saturation:(s) value:(v) alpha:1]
#define HSVACOLOR(h,s,v,a) [UIColor colorWithHue:(h) saturation:(s) value:(v) alpha:(a)]

#define RGBA(r,g,b,a) (r)/255.0, (g)/255.0, (b)/255.0, (a)
#endif
