//
//  NSDictionary+EVTUtilsExtras.m
//  BPLFoundation
//
//  Created by fish.wong on 14/12/16.
//  Copyright (c) 2014年 fish. All rights reserved.
//

#import "NSDictionary+EVTUtilsExtras.h"

@implementation NSDictionary (EVTUtilsExtras)


/**
 *  NSDictionary to NSString
 *
 *  @return <#return value description#>
 */
- (NSString*)parseResponseJSONToString{
    
    NSError *error;
    NSData *jsonData;
    @try {
        BOOL prettyPrint = NO;
        jsonData =  [NSJSONSerialization dataWithJSONObject:self
                                                    options:(NSJSONWritingOptions)    (prettyPrint ? NSJSONWritingPrettyPrinted : 0)
                                                      error:&error];
    }
    @catch (NSException *exception) {
        
        
    }
    @finally {
        
        if (!jsonData || ![jsonData isKindOfClass:[NSData class]]) {
            return nil;
        } else {
            return [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
        }
    }
}

/**
 *  字典转HTTP get参数
 *
 *  @return <#return value description#>
 */
- (NSString*)jsonToHttpGetParameter{
    NSString *aParameter = @"";
    for (NSInteger i=0; i < self.allKeys.count ; i++) {
        NSString *key = self.allKeys[i];
        NSString *val = [self objectForKey:key];
        if (i != 0) {
            aParameter = [NSString stringWithFormat:@"%@&%@=%@",aParameter,key,val];
        }else{
            aParameter = [NSString stringWithFormat:@"%@%@=%@",aParameter,key,val];
        }
    }
    return aParameter;
}


- (id)customObjectForKey:(id)aKey{

    id infoVal = [self objectForKey:aKey];
    if (!infoVal) {
        infoVal = @"";
    }
    return infoVal;
};

/**
 *  判断字典中某个key是否存在
 *
 *  @param keyName 存在返回YES
 *
 *  @return <#return value description#>
 */
- (BOOL)isForKeyExists:(NSString*)keyName{
    if ([[self allKeys] containsObject:keyName]) {
        return YES;
    }
    return NO;
}


/**
 *  安全获取字典里面的值
 *
 *  @param key <#key description#>
 *
 *  @return <#return value description#>
 */
- (id)safeObjectForKey:(NSString*)key{
    
    id val = [self objectForKey:key];
    
    if ([val isEqual:[NSNull null]]) {
        
        return nil;
    }
    return val;
}

@end


@implementation NSMutableDictionary (PDUtilsExtras)

- (void)safeSetObject:(id)anObject forKey:(id)aKey
{
    if (anObject)
        [self setObject:anObject forKey:aKey];
}

- (void)setPoint:(CGPoint)value forKey:(NSString *)key
{
    NSDictionary *dictionary = (NSDictionary *)CFBridgingRelease(CGPointCreateDictionaryRepresentation(value));
    [self setValue:dictionary forKey:key];
}

- (void)setSize:(CGSize)value forKey:(NSString *)key
{
    NSDictionary *dictionary = (NSDictionary *)CFBridgingRelease(CGSizeCreateDictionaryRepresentation(value));
    [self setValue:dictionary forKey:key];
}

- (void)setRect:(CGRect)value forKey:(NSString *)key
{
    NSDictionary *dictionary = (NSDictionary *)CFBridgingRelease(CGRectCreateDictionaryRepresentation(value));
    [self setValue:dictionary forKey:key];
}

@end
