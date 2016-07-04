//
//  NSData_EvtUtilsExtras.h
//  EVTUtils
//
//  Created by xeonwell on 28/09/2011.
//  Copyright 2011 EVT, Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

void *NewBase64Decode(
					  const char *inputBuffer,
					  size_t length,
					  size_t *outputLength);

char *NewBase64Encode(
					  const void *inputBuffer,
					  size_t length,
					  bool separateLines,
					  size_t *outputLength);

@interface NSData (NSData_EvtUtilsExtras)



@property (nonatomic, readonly) NSString* md5Hash;

@property (nonatomic, readonly) NSString* sha1Hash;

+ (NSData *)dataFromBase64String:(NSString *)aString;
- (NSString *)base64EncodedString;

//URLEncode
- (NSString *)urlEncodedString;

/**
 *  NSData To NSMutableDictionary
 *
 *  @return
 */
- (NSMutableDictionary*)parseResponseDataToJSON;

@end
