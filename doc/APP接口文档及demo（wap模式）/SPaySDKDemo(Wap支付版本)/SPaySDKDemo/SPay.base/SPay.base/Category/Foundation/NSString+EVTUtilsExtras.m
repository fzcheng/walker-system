//
//  NSStringExtras.m
//  EVTUtils
//
//  Created by xeonwell on 17/06/2011.
//  Copyright 2011 Evt, Inc. All rights reserved.
//

#import "NSString+EVTUtilsExtras.h"
#import "NSData+EvtUtilsExtras.h"
#import "NSDate+EVTUtilsExtras.h"
#import "SPBaseConst.h"

@implementation NSString (NSString_EVTUtilsExtras)

+ (NSString *)createUUID
{
	CFUUIDRef theUUID = CFUUIDCreate(NULL);
	CFStringRef string = CFUUIDCreateString(NULL, theUUID);
	CFRelease(theUUID);
	return (NSString *)CFBridgingRelease(string);
}

#pragma mark - Utils
- (BOOL)isEmpty
{
    return self.length == 0;
}

- (BOOL)isWhitespaceAndNewlines
{
	NSCharacterSet* whitespace = [NSCharacterSet whitespaceAndNewlineCharacterSet];
	for (NSInteger i = 0; i < self.length; ++i)
    {
		unichar c = [self characterAtIndex:i];
		if (![whitespace characterIsMember:c])
        {
			return NO;
		}
	}
	return YES;
}

- (BOOL)isContainChinese
{
    if (self.length == 0) return NO;
//    static NSUInteger hanziStart    = 19968;
//    static NSUInteger hanziEnd      = 40869;
    for (NSUInteger i = 0,j = self.length; i < j; i++)
    {
        unichar c = [self characterAtIndex:i];
        if (c >= 19968 && c <= 40869) return YES;
    }
    return NO;
}

- (BOOL)isSixNumbersPassword
{
	return [self matches:@"^\\d{6}$"];
}

/**
 *  是否是spay登录密码
 *
 *  @return <#return value description#>
 */
- (BOOL)isSPayLoginPassword{

    NSString *password = [self stringByTrimmingCharactersInSet:
                          [NSCharacterSet whitespaceAndNewlineCharacterSet]];
    
    if (password &&
        ![password isEmpty] &&
        password.length >= 6 &&
        password.length < 16) {
        
        return YES;
    }
    return NO;
}

/**
 *  是否是更新密码
 *
 *  @return <#return value description#>
 */
- (BOOL)isSPayUpdatePassword{
    
    NSString *password = [self stringByTrimmingCharactersInSet:
                          [NSCharacterSet whitespaceAndNewlineCharacterSet]];
    if (password &&
        ![password isEmpty] &&
        password.length >= 8 &&
        password.length < 16) {
        
        return YES;
    }
    return NO;
}


/**
 *  是否是spay登录账户
 *
 *  @return <#return value description#>
 */
- (BOOL)isSPayLoginAcount{

    
    NSString *acount = [self stringByTrimmingCharactersInSet:
                          [NSCharacterSet whitespaceAndNewlineCharacterSet]];
    
    if (acount &&
        ![acount isEmpty] &&
        acount.length > 6 &&
        acount.length < 20) {
        return YES;
    }
    return NO;
}

/**
 *  是否是一个Url连接
 *
 *  @return <#return value description#>
 */
- (BOOL)isHttpUrl{
    NSString *urlRegEx =
    @"(http|https)://((\\w)*|([0-9]*)|([-|_])*)+([\\.|/]((\\w)*|([0-9]*)|([-|_])*))+";
    NSPredicate *urlTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", urlRegEx];
    return [urlTest evaluateWithObject:self];
}

- (NSString *)trim
{
    return [self stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
}

- (NSString *)removeWhiteSpace
{
	return [[self componentsSeparatedByCharactersInSet:[NSCharacterSet whitespaceCharacterSet]] 
            componentsJoinedByString:@""];
}

- (NSString *) stringByUrlEncoding
{
	return (NSString *)CFBridgingRelease(CFURLCreateStringByAddingPercentEscapes(NULL,
                                                                (CFStringRef)self,  
                                                                NULL,  
                                                                (CFStringRef)@"!*'();:@&=+$,/?%#[]",  
                                                                kCFStringEncodingUTF8));
}

/**
 *  NSString To NSMutableDictionary
 *
 *  @return <#return value description#>
 */
- (NSMutableDictionary*)parseResponseStringToJSON{
    id json = nil;
    @try {
        NSError *error = nil;
        NSData *JSONData = [self dataUsingEncoding:NSUTF8StringEncoding];
        json = [NSJSONSerialization JSONObjectWithData:JSONData
                                               options:NSJSONReadingMutableContainers
                                                 error:&error];
        if(error == nil){
            return json;
        }else{
            return nil;
        }
    }
    @catch (NSException *exception) {
        
    }
    @finally {
        
        if (json && [json isKindOfClass:[NSDictionary class]]) {
            
            return json;
        }else{
            
            return nil;
        }
        
    }
}

/**
 *  JsonArray to NSMutableArray
 *
 *  @return <#return value description#>
 */
- (NSMutableArray*)parseResponseArrayStringToJSONArray{
    id json = nil;
    @try {
        NSError *error = nil;
        NSData *JSONData = [self dataUsingEncoding:NSUTF8StringEncoding];
        json = [NSJSONSerialization JSONObjectWithData:JSONData
                                               options:NSJSONReadingMutableContainers
                                                 error:&error];
        if(error == nil){
            return json;
        }else{
            return nil;
        }
    }
    @catch (NSException *exception) {
        
    }
    @finally {
        
        if (json && [json isKindOfClass:[NSArray class]]) {
            
            return [[NSMutableArray alloc]initWithArray:json];
        }else{
            
            return nil;
        }
        
    }
}


/**
 *  金额转大写
 *
 *  @param money <#money description#>
 *
 *  @return <#return value description#>
 */
-(NSString *)digitUppercase
{
    
    NSString *money = self;
    NSMutableString *moneyStr=[[NSMutableString alloc] initWithString:[NSString stringWithFormat:@"%.2f",[money doubleValue]]];
    NSArray *MyScale=@[@"分", @"角", @"元", @"拾", @"佰", @"仟", @"万", @"拾", @"佰", @"仟", @"亿", @"拾", @"佰", @"仟", @"兆", @"拾", @"佰", @"仟" ];
    NSArray *MyBase=@[@"零", @"壹", @"贰", @"叁", @"肆", @"伍", @"陆", @"柒", @"捌", @"玖"];
    
    NSArray *integerArray = @[@"拾", @"佰", @"仟", @"万", @"拾万", @"佰万", @"仟万", @"亿", @"拾亿", @"佰亿", @"仟亿", @"兆", @"拾兆", @"佰兆", @"仟兆"];
    
    
    NSMutableString *M=[[NSMutableString alloc] init];
    [moneyStr deleteCharactersInRange:NSMakeRange([moneyStr rangeOfString:@"."].location, 1)];
    for(NSInteger i=moneyStr.length;i>0;i--)
    {
        NSInteger MyData=[[moneyStr substringWithRange:NSMakeRange(moneyStr.length-i, 1)] integerValue];
        [M appendString:MyBase[MyData]];
        
        //判断是否是整数金额
        if (i == moneyStr.length) {
            NSInteger l = [[moneyStr substringFromIndex:1] integerValue];
            if (MyData > 0 &&
                l == 0 ) {
                NSString *integerString = @"";
                if (moneyStr.length > 3) {
                    integerString = integerArray[moneyStr.length-4];
                }
                [M appendString:[NSString stringWithFormat:@"%@%@",integerString,@"元整"]];
                break;
            }
        }
        
        if([[moneyStr substringFromIndex:moneyStr.length-i+1] integerValue]==0
           &&i!=1
           &&i!=2)
        {
            [M appendString:@"元整"];
            break;
        }
        [M appendString:MyScale[i-1]];
    }
    return M;
}


/**
 *  讲类似于20150605112215的格式转换为时间格式
 *
 *  @return <#return value description#>
 */
- (NSString*)tradetime_toDateString{
    
    NSString *tradetime = @"";
    if (self.length == 14) {
        
        NSString *string1 = [self substringWithRange:NSMakeRange(0, 4)];
        tradetime = [NSString stringWithFormat:@"%@-",string1];
        
        tradetime = [NSString stringWithFormat:@"%@%@-",tradetime,[self substringWithRange:NSMakeRange(4, 2)]];
        tradetime = [NSString stringWithFormat:@"%@%@ ",tradetime,[self substringWithRange:NSMakeRange(6, 2)]];
        
        tradetime = [NSString stringWithFormat:@"%@%@:",tradetime,[self substringWithRange:NSMakeRange(8, 2)]];
        
        tradetime = [NSString stringWithFormat:@"%@%@:",tradetime,[self substringWithRange:NSMakeRange(10, 2)]];
        tradetime = [NSString stringWithFormat:@"%@%@",tradetime,[self substringWithRange:NSMakeRange(12, 2)]];
        
        return tradetime;
        
    }
    return self;
}



- (NSString *)stringByUrlDecoding
{
    NSString *result = [(NSString *)self stringByReplacingOccurrencesOfString:@"+" withString:@" "];
    result = [result stringByReplacingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
    return result;
}


- (NSString *)capitalize
{
    if ([self length] == 0 || islower([self characterAtIndex:0]) == 0) return self;
    return [[self substringToIndex:1].uppercaseString stringByAppendingString:[self substringFromIndex:1]];
}

- (NSString *)lowercaseFirstString
{
    if ([self length] == 0 || isupper([self characterAtIndex:0]) == 0) return self;
    return [[self substringToIndex:1].lowercaseString stringByAppendingString:[self substringFromIndex:1]];
}

- (BOOL)matches:(NSString *)pattern 
{
    return [self rangeOfString:pattern options:NSRegularExpressionSearch].location != NSNotFound;
}

- (BOOL)matchesWithCaseInsensitive:(NSString *)pattern
{
    return [self rangeOfString:pattern 
                       options:NSRegularExpressionSearch | NSCaseInsensitiveSearch].location != NSNotFound;    
}
- (NSString *)stringByReplacingRegularString:(NSString *)regString withString:(NSString *)replaceString
{
    NSError *error = nil;
    NSRegularExpression *regularExpression = [NSRegularExpression
                                           regularExpressionWithPattern:regString
                                           options:NSRegularExpressionCaseInsensitive
                                           error:&error];
    if (error)
    {
//        PDVerboseLog(@"regular expression error: %@", [error localizedDescription]);
        return self;
    }

    return [regularExpression stringByReplacingMatchesInString:self
                                                       options:0
                                                         range:NSMakeRange(0, self.length)
                                                  withTemplate:replaceString];

}

- (BOOL)startsWith:(NSString *)str
{
	return [self startsWith:str Options:NSCaseInsensitiveSearch];
}

- (BOOL)startsWith:(NSString *)str Options:(NSStringCompareOptions) compareOptions
{
//	NSParameterAssert(str != nil && [str length]>0 && [self length]>=[str length]);
	return (str != nil) && ([str length] > 0) && ([self length] >= [str length])  
	&& ([self rangeOfString:str options:compareOptions].location == 0);
}

- (BOOL)endsWith:(NSString *)str
{
	return [self endsWith:str Options:NSCaseInsensitiveSearch];
}
- (BOOL)endsWith:(NSString *)str Options:(NSStringCompareOptions) compareOptions
{
//	NSParameterAssert(str != nil && [str length]>0 && [self length]>=[str length]);
	return (str != nil) && ([str length] > 0) && ([self length] >= [str length]) 
	&& ([self rangeOfString:str 
                    options:(compareOptions | NSBackwardsSearch)].location == ([self length] - [str length]));
}
- (BOOL)containsString:(NSString *)str
{
	return [self containsString:str Options:NSCaseInsensitiveSearch];
}

- (BOOL)containsString:(NSString *)str Options:(NSStringCompareOptions) compareOptions
{
	return (str != nil) && ([str length] > 0) && ([self length] >= [str length]) 
    && ([self rangeOfString:str options:compareOptions].location != NSNotFound);
}

- (BOOL)equalsString:(NSString *)str
{
//	return (str != nil) && ([self length] == [str length]) && [[self lowercaseString] isEqualToString:[str lowercaseString]];
	return (str != nil) && ([self length] == [str length]) 
    && ([self rangeOfString:str options:NSCaseInsensitiveSearch].location == 0);
}

- (NSDate *)convertToCurrentDateWithFormat:(NSString *)format
{
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
	//    [formatter setDateStyle:kCFDateFormatterFullStyle];
	//    formatter.timeZone = [NSTimeZone defaultTimeZone];
    formatter.locale  = [NSLocale currentLocale];
    [formatter setDateFormat:format];
    NSDate *date = [formatter dateFromString:self];
    return date;
}



- (NSDate *)convertToDateWithFormat:(NSString *)format
{
 
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
//    [formatter setDateStyle:kCFDateFormatterFullStyle];
//    formatter.timeZone = [NSTimeZone defaultTimeZone];
    formatter.locale  = [NSLocale currentLocale];
    formatter.timeZone = [NSTimeZone timeZoneWithName:@"HKT"];
    [formatter setDateFormat:format];
    NSDate *date = [formatter dateFromString:self];
    return date;
}

- (NSURL *)convertToURL
{
    if (self.length)
        return [NSURL URLWithString:self];
    return nil;
}

//- (BOOL)isEmailFormat
//{
//    //xxx@xxx.com(.cn)
//    NSArray *breakArray = [self componentsSeparatedByString:@"@"];
//    if ([breakArray count] != 2 
//        || ((NSString *)[breakArray objectAtIndex:0]).length == 0 
//        || ((NSString *)[breakArray objectAtIndex:1]).length == 0)
//        return NO;
//
//    NSString *string = [breakArray objectAtIndex:1];
//    breakArray = [string componentsSeparatedByString:@"."];
//    if (breakArray.count <= 1
//        || ((NSString *)[breakArray objectAtIndex:0]).length == 0
//        || ((NSString *)[breakArray objectAtIndex:1]).length == 0
//        || ((NSString *)[breakArray objectAtIndex:(breakArray.count - 1)]).length == 0) 
//        return NO;
//
//    return YES;
//}


- (NSString*)dateString{
    NSDate *date;
    if ([self isKindOfClass:[NSDate class]]) {
        date = (NSDate*)self;
        return [date stringWithFormat:kBPLDateYMDHMSFormatString];
    }else{
        return self;
    }
}
- (NSString *)moneyDot1Mask
{
    if (self == nil)
    {
        return @"0";
    }
    
    NSString *strPrice= @"";
    if ([self intValue] == [self floatValue])
    {
        strPrice = [NSString stringWithFormat:@"%d", [self intValue]];
    }
    else
    {
        strPrice = [NSString stringWithFormat:@"%.1f", [self floatValue]];                
    }
    return strPrice;
}

- (NSString *)filterString:(NSArray *)charArray
{
    if (self == nil)
    {
        return @"";
    }
    
    NSString *resString = self;

    for (int i=0; i<[charArray count]; i++)
    {
        NSString *tempString = [charArray objectAtIndex:i];
        resString = [resString stringByReplacingOccurrencesOfString:tempString withString:@""];
    }
    return resString;
}

- (NSString *)mobileMask
{
    if (![self isMobileNumber]) return self;
    return [NSString stringWithFormat:@"%@****%@", [self substringToIndex:3], [self substringFromIndex:7]];
}

- (BOOL)isMobileNumber
{
//    return [self matches:@"^1[3458]{1}[0-9]{9}$"];
    NSString *mobileRegex = @"1\\d{10}";
    NSPredicate *mobilTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", mobileRegex];
    return [mobilTest evaluateWithObject:self];
}

- (BOOL)isVerificationCode{
    NSString *verificationCodeRegex = @"\\d{4}";
    NSPredicate *verificationCode = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", verificationCodeRegex];
    return [verificationCode evaluateWithObject:self];
}


- (BOOL)isValidUsername
{
    return [self matches:@"^[_a-zA-Z0-9\\u4e00-\\u9fa5]{4,16}$"];
}

- (BOOL)isValidMemberCardUserName
{
    return [self matches:@"^[_a-zA-Z_.\\u4e00-\\u9fa5]{1,16}$"];
}

- (BOOL)isValidChineseName
{
    return [self matches:@"^[\\u4e00-\\u9fa5]{1,6}$"];
}

- (BOOL)isValidPassword
{
//    return [self matches:@"^[\\-_a-zA-Z0-9]{6,16}$"];
        return [self matches:@"^[\\-_a-zA-Z0-9]{1,16}$"];
}


- (int)bytesLength
{
	int strlength = 0;
	char* p = (char*)[self cStringUsingEncoding:NSUnicodeStringEncoding];
	NSUInteger count = [self lengthOfBytesUsingEncoding:NSUnicodeStringEncoding];
	for (int i = 0 ; i < count ; i++)
	{
		if (*p)
		{
			p++;
			strlength++;
		}
		else
		{
			p++;
		}
	}
	return (strlength+1)/2;
}


#pragma mark -  fish add
/**
 *  解析HTTP中GET的请求参数
 *
 *  @return <#return value description#>
 */
-(NSDictionary*)resolveHTTPGETParameter{
    
    NSRange range = [self rangeOfString:@"?"];
    NSString *hTTPGETParameterString = [self copy];
    
    //判断是否需要把网址移除
    if (range.location != NSNotFound) {
        hTTPGETParameterString = [self substringFromIndex:(range.location + range.length)];
    }
    
    NSArray *hTTPGETParameterArray = [hTTPGETParameterString componentsSeparatedByString:@"&"];
    //是否分割出来了参数
    if (hTTPGETParameterArray &&
        hTTPGETParameterArray.count) {
        
        
        NSMutableDictionary *parameterDicy = [[NSMutableDictionary alloc] init];
        for (NSString *parameterItemString in hTTPGETParameterArray) {
            
            NSArray *itemArray = [parameterItemString componentsSeparatedByString:@"="];
            //判断参数格式是否合法
            if (itemArray &&
                itemArray.count == 2) {
                
                [parameterDicy setValue:itemArray[1] forKey:itemArray[0]];
            }else{
               return nil;
            }
        }
        
        return parameterDicy;
    }
    
    return nil;
}


/**
 *  解析HTTP Get参数
 *
 *  @return <#return value description#>
 */
-(NSDictionary*)parseHTTGetParameter{

    NSArray *hTTPGETParameterArray = [self componentsSeparatedByString:@"&"];
    //是否分割出来了参数
    if (hTTPGETParameterArray &&
        hTTPGETParameterArray.count) {
        
        
        NSMutableDictionary *parameterDicy = [[NSMutableDictionary alloc] init];
        for (NSString *parameterItemString in hTTPGETParameterArray) {
            
            NSArray *itemArray = [parameterItemString componentsSeparatedByString:@"="];
            //判断参数格式是否合法
            if (itemArray &&
                itemArray.count == 2) {
                
                [parameterDicy setValue:itemArray[1] forKey:itemArray[0]];
            }else{
                return nil;
            }
        }
        
        return parameterDicy;
    }
    
    return nil;
}

/**
 *  获取网址的绝对路径
 *
 *  @return <#return value description#>
 */
- (NSString*)stringUrlAbsolutePath{

    NSRange range = [self rangeOfString:@"?"];
    if (range.location != NSNotFound) {
       NSString *urlAbsolutePath = [self substringToIndex:range.location];
       return urlAbsolutePath;
    }
    return self;
    
}
/**
 *  身份证加密显示
 *
 *  @return <#return value description#>
 */
- (NSString*)identificationHideString{
    
    if (self.length == 0 ) {
        return @"";
    }else if(self.length == 1){
        return @"*";
    }else{
        
        NSString *hideString = [self substringToIndex:1];
        for (int i = 1; i < self.length-1; i++) {
           
            hideString = [NSString stringWithFormat:@"%@%@",hideString,@"*"];
        }
        hideString = [NSString stringWithFormat:@"%@%@",hideString,[self substringFromIndex:(self.length-1)]];
        
        return hideString;
    }
}

- (NSNumber*)currencyNumber{
    return  [NSNumber numberWithDouble:self.doubleValue];
}





#pragma mark XML Extensions
+ (NSString *)encodeXMLCharactersIn : (NSString *)source
{
    if (![source isKindOfClass:[NSString class]] || !source)
        return @"";
	
    NSString *result = [NSString stringWithString:source];
	
    if ([result rangeOfString:@"&"].location != NSNotFound)
        result = [[result componentsSeparatedByString:@"&"] componentsJoinedByString:@"&amp;"];
	
    if ([result rangeOfString:@"<"].location != NSNotFound)
        result = [[result componentsSeparatedByString:@"<"] componentsJoinedByString:@"&lt;"];
	
    if ([result rangeOfString:@">"].location != NSNotFound)
        result = [[result componentsSeparatedByString:@">"] componentsJoinedByString:@"&gt;"];
	
    if ([result rangeOfString:@"\""].location != NSNotFound)
        result = [[result componentsSeparatedByString:@"\""] componentsJoinedByString:@"&quot;"];
	
    if ([result rangeOfString:@"'"].location != NSNotFound)
        result = [[result componentsSeparatedByString:@"'"] componentsJoinedByString:@"&apos;"];
	
    return result;
}

+ (NSString *)decodeXMLCharactersIn:(NSString *)source
{
    if (![source isKindOfClass:[NSString class]] || !source)
        return @"";
	
    NSString *result = [NSString stringWithString:source];
	
    if ([result rangeOfString:@"&amp;"].location != NSNotFound)
        result = [[result componentsSeparatedByString:@"&amp;"] componentsJoinedByString:@"&"];
	
    if ([result rangeOfString:@"&lt;"].location != NSNotFound)
        result = [[result componentsSeparatedByString:@"&lt;"] componentsJoinedByString:@"<"];
	
    if ([result rangeOfString:@"&gt;"].location != NSNotFound)
        result = [[result componentsSeparatedByString:@"&gt;"] componentsJoinedByString:@">"];
	
    if ([result rangeOfString:@"&quot;"].location != NSNotFound)
        result = [[result componentsSeparatedByString:@"&quot;"] componentsJoinedByString:@"\""];
	
    if ([result rangeOfString:@"&apos;"].location != NSNotFound)
        result = [[result componentsSeparatedByString:@"&apos;"] componentsJoinedByString:@"'"];
	
    if ([result rangeOfString:@"&nbsp;"].location != NSNotFound)
        result = [[result componentsSeparatedByString:@"&nbsp;"] componentsJoinedByString:@" "];
	
    if ([result rangeOfString:@"&#8220;"].location != NSNotFound)
        result = [[result componentsSeparatedByString:@"&#8220;"] componentsJoinedByString:@"\""];
	
    if ([result rangeOfString:@"&#8221;"].location != NSNotFound)
        result = [[result componentsSeparatedByString:@"&#8221;"] componentsJoinedByString:@"\""];
	
	if ([result rangeOfString:@"&#039;"].location != NSNotFound)
        result = [[result componentsSeparatedByString:@"&#039;"] componentsJoinedByString:@"'"];
	
    return result;
}

#pragma mark - hashing
- (NSString *)base64Encoding
{
	NSData *stringData = [self dataUsingEncoding:NSUTF8StringEncoding];
	NSString *encodedString = [stringData base64EncodedString];
	
	return encodedString;
}
///////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * Calculate the md5 hash using CC_MD5.
 *
 * @returns md5 hash of this string.
 */
- (NSString*)md5Hash 
{
	return [[self dataUsingEncoding:NSUTF8StringEncoding] md5Hash];
}


///////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * Calculate the SHA1 hash using CommonCrypto CC_SHA1.
 *
 * @returns SHA1 hash of this string.
 */
- (NSString*)sha1Hash 
{
	return [[self dataUsingEncoding:NSUTF8StringEncoding] sha1Hash];
}


- (NSString *)stringByAppendURLKey:(NSString *)keyName Parameter:(id)parameter
{
    NSMutableString *mutableString = [NSMutableString stringWithString:self];
    return [mutableString appendURLKey:keyName Parameter:parameter];
}
@end


@implementation NSMutableString (PDUtilsExtras)

- (NSMutableString *)appendURLKey:(NSString *)keyName Parameter:(id)parameter
{
    if (!keyName || !parameter)
    {
//        PDDebugLog(@"url %@ paramter is nil", keyName);
        return self;
    }
    BOOL needsQMark = [self rangeOfString:@"?"].location == NSNotFound;
    if (needsQMark)
        [self appendString:@"?"];
    
    BOOL charOnEnd = [self hasSuffix:@"&"] || [self hasSuffix:@"?"];
    if (!charOnEnd)
        [self appendString:@"&"];
    
    if ([parameter isKindOfClass:[NSArray class]])
    {
        NSArray *paramArray = (NSArray *)parameter;
        for (NSUInteger i = 0, j = paramArray.count; i < j; i++)
        {
            [self appendFormat:@"%@%@[]=%@", (i > 0 ? @"&" : @""), keyName, [paramArray objectAtIndex:i]];
        }
    }else {
        [self appendFormat:@"%@=%@", keyName, parameter];
    }
    return self;
}



@end
