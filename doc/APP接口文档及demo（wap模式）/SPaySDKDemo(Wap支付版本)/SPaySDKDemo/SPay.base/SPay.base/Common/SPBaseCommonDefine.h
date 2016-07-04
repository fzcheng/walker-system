//
//  SPBaseCommonDefine.h
//  SPay.base
//
//  Created by wongfish on 15/5/7.
//  Copyright (c) 2015年 wongfish. All rights reserved.
//

#ifdef DEBUG
//#define NSLog(format, ...) do {                                                                          \
//fprintf(stderr, "<%s : %d> %s\n",                                           \
//[[[NSString stringWithUTF8String:__FILE__] lastPathComponent] UTF8String],  \
//__LINE__, __func__);                                                        \
//(NSLog)((format), ##__VA_ARGS__);                                           \
//fprintf(stderr, "-------\n");                                               \
//} while (0)

#define SPLog(...) NSLog(__VA_ARGS__)


#else
#define SPLog(...)          do{}while(0)
#endif