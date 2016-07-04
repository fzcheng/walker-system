//
//  SPBaseModel.m
//  SPay.base
//
//  Created by wongfish on 15/5/7.
//  Copyright (c) 2015年 wongfish. All rights reserved.
//

#import "SPBaseModel.h"
#import <objc/runtime.h>
#import "NSString+EVTUtilsExtras.h"
#import "NSArray+PDUtilsExtras.h"
#import "NSDictionary+EVTUtilsExtras.h"
#ifdef DEBUG
static NSMutableDictionary *ivarDictionay = nil;
#endif

@implementation SPBaseModel
//@synthesize rowID = _rowID;

#ifdef DEBUG
- (NSString *)description
{
    /*!
     //    unsigned int count = 0;
     //    Class cls = [self class];
     //    objc_property_t *properties = class_copyPropertyList(cls, &count);
     //    NSMutableArray *propertyArray = [[NSMutableArray alloc] initWithCapacity:count];
     //    for (unsigned int i = 0; i<count; i++)
     //    {
     //        NSString *prop = [[NSString alloc] initWithUTF8String:property_getName(properties[i])];
     //        [propertyArray addObject:prop];
     //         [prop release];
     //    }
     //    free(properties);
     //
     //    Ivar *ivars = class_copyIvarList(cls, &count);
     //    NSMutableArray *ivarArray = [[[NSMutableArray alloc] initWithCapacity:count] autorelease];
     //    @autoreleasepool {
     //
     //        for (uint i = 0; i < count; i++)
     //         {
     //            NSString *ivar = [[NSString alloc] initWithUTF8String:ivar_getName(ivars[i])];
     //            const char *varType = ivar_getTypeEncoding(ivars[i]);
     //            if (strcmp(varType, "@\"NSString\"") == 0)
     //                [ivarArray addObject:[NSString stringWithFormat:@"%@ = %@", ivar, [self valueForKey:ivar]]];
     //            else if (strcmp(varType, @encode(NSInteger)) == 0 ||
     //                     strcmp(varType, @encode(BOOL)) ||
     //                     strcmp(varType, @encode(NSUInteger)) == 0)
     //                [ivarArray addObject:[NSString stringWithFormat:@"%@ = %d", ivar, [[self valueForKey:ivar] integerValue]]];
     //            else if (strcmp(varType, @encode(double)) == 0 ||
     //                     strcmp(varType, @encode(CGFloat)) == 0)
     //                [ivarArray addObject:[NSString stringWithFormat:@"%@ = %f", ivar, [[self valueForKey:ivar] doubleValue]]];
     //            else if (strcmp(varType, "@\"NSDate\"") == 0)
     //                [ivarArray addObject:[NSString stringWithFormat:@"%@ = %@", ivar, [self valueForKey:ivar]]];
     //
     //            [ivar release];
     //
     //        }
     //    }
     //    free(ivars);
     //
     //
     //    return [ivarArray componentsJoinedByString:@", "];
     */
    
    Class cls = [self class];
    NSString *className = NSStringFromClass(cls);
    if (ivarDictionay == nil)
        ivarDictionay = [[NSMutableDictionary alloc] init];
    
    if ([ivarDictionay objectForKey:className] == nil)
    {
        NSMutableArray *ivarArray = [[NSMutableArray alloc] init];
        
        unsigned int count = 0;
        do
        {
            Ivar *ivars = class_copyIvarList(cls, &count);
            for (uint i = 0; i < count; i++)
            {
                NSString *ivar = [[NSString alloc] initWithUTF8String:ivar_getName(ivars[i])];
                [ivarArray addObject:ivar];
            }
            free(ivars);
        }
        while ((cls = class_getSuperclass(cls))!= [SPBaseModel class]);
        
        [ivarDictionay setObject:ivarArray forKey:className];
    }
    
    NSArray *ivarArray = [ivarDictionay objectForKey:className];
    NSMutableDictionary *ivarDict = [[NSMutableDictionary alloc] initWithCapacity:ivarArray.count];
    for (NSString *ivar in ivarArray)
    {
        id value = [self valueForKey:ivar];
        [ivarDict setValue:(value ? value : [NSNull null]) forKey:ivar];
    }
    
    NSString *_description = [ivarDict description];
    return _description ;
}
#endif

+ (id)instanceWithDict:(NSDictionary *)dict
{
    return [dict isKindOfClass:[NSDictionary class]] ? [[self alloc] initWithDict:dict] : nil;
}

#pragma mark -
#pragma mark ==== BaseModelProtocol ====
#pragma mark -
- (id)initWithDict:(NSDictionary *)dict
{
    if (![dict isKindOfClass:[NSDictionary class]]) return nil;
    if ((self = [self init]))
    {
        static NSString *setMethodString = @"set%@:";
        static Class stringClass = NULL;
        static dispatch_once_t onceToken;
        dispatch_once(&onceToken, ^{
            stringClass = [NSString class];
        });
        static SEL selector = NULL;
        
        //        [dict enumerateKeysAndObjectsUsingBlock:^(id key, id obj, BOOL *stop) {
        //            if ([key isKindOfClass:stringClass] && ![obj isEqual:[NSNull null]])
        //            {
        //                selector = NSSelectorFromString([NSString stringWithFormat:setMethodString, [key capitalize]]);
        //                if (selector != NULL && [self respondsToSelector:selector])
        //                    [self setValue:obj forKey:key];
        //            }
        //        }];
        //        selector = NULL;
        
        //测试中下面的代码速度高于上面注释的代码
        NSEnumerator *enumerator = [dict keyEnumerator];
        id key;
        while ((key = [enumerator nextObject]))
        {
            if (![key isKindOfClass:stringClass]) continue;
            
            
            NSString *classNameString = [NSString stringWithUTF8String:object_getClassName(self)];
            id class = NSClassFromString(classNameString);
            
            NSString *curMappingKey = key;
            //判断某些key是否需要重新映射
            if ([[class mappingKey] isForKeyExists:key]) {
           
                curMappingKey = [class mappingKey][key];
            }
            id obj = [dict safeObjectForKey:key];
            if ([[NSNull null] isEqual:obj]) continue;
            selector = NSSelectorFromString([NSString stringWithFormat:setMethodString, [self keyProcess:curMappingKey]]);
            if (selector != NULL && [self respondsToSelector:selector])
                [self setValue:obj forKey:curMappingKey];
        }
        selector = NULL;
        
    }
    return self;
}

- (NSString *)keyProcess:(NSString *)key
{
    if ([key length] == 0 || islower([key characterAtIndex:0]) == 0) return key;
    return [[key substringToIndex:1].uppercaseString stringByAppendingString:[key substringFromIndex:1]];
}

- (BOOL)stringStartsWith:(NSString *)string key:(NSString *)keyString
{
    return [self stringStartsWith:string key:keyString Options:NSCaseInsensitiveSearch];
}

- (BOOL)stringStartsWith:(NSString *)string key:(NSString *)keyString Options:(NSStringCompareOptions) compareOptions
{
    //	NSParameterAssert(str != nil && [str length]>0 && [self length]>=[str length]);
    return (keyString != nil) && ([keyString length] > 0) && ([string length] >= [keyString length])
    && ([string rangeOfString:keyString options:compareOptions].location == 0);
}

- (BOOL)stringEndsWith:(NSString *)string key:(NSString *)keyString
{
    return [self stringEndsWith:string key:keyString Options:NSCaseInsensitiveSearch];
}
- (BOOL)stringEndsWith:(NSString *)string key:(NSString *)keyString Options:(NSStringCompareOptions) compareOptions
{
    //	NSParameterAssert(str != nil && [str length]>0 && [self length]>=[str length]);
    return (keyString != nil) && ([keyString length] > 0) && ([string length] >= [keyString length])
    && ([string rangeOfString:keyString
                      options:(compareOptions | NSBackwardsSearch)].location == ([string length] - [keyString length]));
}

//- (id)initWithArray:(NSArray *)array
//{
//    if ((self = [self init]))
//	{
//        //in the real implementation, you would set a property from the array, e.g.
//		//self.items = [[array mutableCopy] autorelease];
//        [NSException raise:NSGenericException
//                    format:@"Abstract -initWithDict implementation - you need to override this"];
//	}
//	return self;
//}

- (id)initWithDict:(NSDictionary *)dict mapping:(NSDictionary *)mappingDict
{
    if (!mappingDict || ![mappingDict isKindOfClass:[NSDictionary class]]) return [self initWithDict:dict];
    if (!dict || ![dict isKindOfClass:[NSDictionary class]]) return nil;
    if ((self = [self init]))
    {
        NSEnumerator *enumerator = [dict keyEnumerator];
        id key;
        while ((key = [enumerator nextObject]))
        {
            if (![key isKindOfClass:[NSString class]]) continue;
            id newKey = [mappingDict objectForKey:key];
            if (newKey == nil || ![newKey isKindOfClass:[NSString class]]) continue;
            id obj = [dict objectForKey:key];
            if ([[NSNull null] isEqual:obj]) continue;
            SEL selector = NSSelectorFromString([NSString stringWithFormat:@"set%@:", [newKey capitalize]]);
            if (selector != NULL && [self respondsToSelector:selector])
                [self setValue:obj forKey:newKey];
        }
    }
    return self;
}

+ (NSMutableArray *)modelArrayWithDictArray:(NSArray *)dictArray
{
    if (![dictArray isKindOfClass:[NSArray class]] || dictArray.count == 0) return nil;
    
    NSMutableArray *modelArray = [NSMutableArray arrayWithCapacity:dictArray.count];
    for (NSDictionary *dictionary in dictArray)
    {
        if ([dictionary isKindOfClass:[NSDictionary class]])
        {
            id model = [[self alloc] initWithDict:dictionary];
            [modelArray addObject:model];
        }
    }
    return modelArray;
}

- (NSMutableDictionary *)dictionaryValue
{
    Class baseModelClass = [SPBaseModel class];
    if ([self isMemberOfClass:baseModelClass])
        return nil;
    NSMutableDictionary *dictionary = [NSMutableDictionary dictionary];
    Class cls = [self class];
    do
    {
        unsigned int count = 0;
        
        objc_property_t *properties = class_copyPropertyList(cls, &count);
        for (unsigned int i=0; i<count; i++)
        {
            NSString *prop = [[NSString alloc] initWithUTF8String:property_getName(properties[i])];
            id value = [self valueForKey:prop];
            if (!value)
            {
                continue;
            }
            //处理值为basemodel子类
            if ([value isKindOfClass:baseModelClass])
            {
                [dictionary setValue:[(SPBaseModel *)value dictionaryValue] forKey:[prop capitalize]];
            }
            //处理数组， 后续可以考虑处理字典
            else if ([value isKindOfClass:[NSArray class]])
            {
                NSArray *valueArray = (NSArray *)value;
                NSMutableArray *modelArray = [[NSMutableArray alloc] initWithCapacity:valueArray.count];
                for (SPBaseModel *model in valueArray)
                {
                    if ([model isKindOfClass:baseModelClass])
                        [modelArray safeAddObject:[model dictionaryValue]];
                    else
                        [modelArray addObject:model];
                }
                [dictionary setValue:modelArray forKey:[prop capitalize]];
            }
            else
                [dictionary setValue:value forKey:[prop capitalize]];
        }
        free(properties);
    }
    while ((cls = class_getSuperclass(cls))!= baseModelClass);
    
    return dictionary;
}

#pragma mark -
#pragma mark ==== NSKeyValueCodingProtocol ====
#pragma mark -

- (void)setValue:(id)value forUndefinedKey:(NSString *)key
{
    //    PDDebugLog(@"%@ is not in %@", key, NSStringFromClass([self class]));
}

- (void)setNilValueForKey:(NSString *)key
{
    //    PDDebugLog(@"set nil value for %@ in %@", key, NSStringFromClass([self class]));
}
//- (BOOL)stringEndsWith:(NSString *)string key:(NSString *)keyString
- (void)setValue:(id)value forKey:(NSString *)key
{
    [super setValue:value forKey:key];
    
}

//#pragma mark -
//#pragma mark ==== NSCodingProtocol ====
//#pragma mark -
//
////if your model is immutable - i.e. it isn't modifed at runtime, then
////you don't need to implement this stuff. you might want this for
////models where the user can modify the data and save it, or for models
////that are downloaded from a web service and need to be saved locally
//
//
//- (id)initWithCoder:(NSCoder *)aDecoder
//{
//	if ((self = [self init]))
//	{
//		////in the real implementation you would load propeties from the decoder object, e.g.
//		//self.someProperty = [aDecoder decodeObjectForKey:@"someProperty"];
//		[NSException raise:NSGenericException
//					format:@"Abstract -initWithCoder implementation - you need to override this in model %@",
//                            NSStringFromClass([self class])];
//	}
//	return self;
//}
//
//- (void)encodeWithCoder:(NSCoder *)aCoder
//{
//	////in the real implementation you would write properties to the coder object, e.g.
//	//[aCoder encodeObject:someProperty forKey:@"someProperty"];
//	[NSException raise:NSGenericException
//				format:@"Abstract -encodeWithCoder implementation - you need to override this in model %@",
//                             NSStringFromClass([self class])];
//}

+ (NSDictionary*)mappingKey{
    
    return nil;
}

@end
