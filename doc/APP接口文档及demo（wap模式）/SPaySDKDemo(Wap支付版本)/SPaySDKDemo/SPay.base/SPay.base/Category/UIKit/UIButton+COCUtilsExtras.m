//
//  UIButton+COCUtilsExtras.m
//  YunZo
//
//  Created by po03 on 14-5-28.
//  Copyright (c) 2014年  yunzo. All rights reserved.
//

#import "UIButton+COCUtilsExtras.h"
#import <objc/runtime.h>
static char imageViewkey;
static char imageKey;
static NSString *normalKey =  @"normal";
static NSString *selectedKey =  @"Selected";
@implementation UIButton (COCUtilsExtras)


/**
 *  设置button的title
 *
 *  @param titleString <#titleString description#>
 */
- (void)buttonAllStyleTitle:(NSString*)titleString{ 
    [self setTitle:titleString forState:UIControlStateNormal];
    [self setTitle:titleString forState:UIControlStateSelected];
    [self setTitle:titleString forState:UIControlStateHighlighted];
}



- (void)buttonAllStyleTitleColor:(UIColor*)color{
    [self setTitleColor:color forState:UIControlStateNormal];
    [self setTitleColor:color forState:UIControlStateSelected];
    [self setTitleColor:color forState:UIControlStateHighlighted];
}


- (void)buttonAllStyleImage:(UIImage*)image{
    [self setBackgroundImage:image forState:UIControlStateNormal];
    [self setBackgroundImage:image forState:UIControlStateSelected];
    [self setBackgroundImage:image forState:UIControlStateHighlighted];
}

- (void)buttonAllImage:(UIImage*)image{
    [self setImage:image forState:UIControlStateNormal];
    [self setImage:image forState:UIControlStateSelected];
    [self setImage:image forState:UIControlStateHighlighted];
}

/**
 *  圆样式
 *
 *  @param circleImage <#circleImage description#>
 */
- (void)buttonCircleStyle:(UIImage*)circleImage{

    CALayer *maskLayer = [CALayer layer];
    maskLayer.backgroundColor = [[UIColor colorWithRed:1.0f green:1.0f blue:1.0f alpha:0.0f] CGColor];
    maskLayer.contents = (id)[circleImage CGImage];
    maskLayer.contentsGravity = kCAGravityCenter;
    maskLayer.frame = CGRectMake(0,
                                 0.0f,
                                 self.frame.size.width,
                                 self.frame.size.height);
    self.layer.mask = maskLayer;
}

- (void)iOS7style{
    [self setBackgroundColor:[UIColor whiteColor]];
    self.layer.borderWidth = 0.5f;
    self.layer.borderColor = [UIColor grayColor].CGColor;
    self.layer.cornerRadius = 6.0f;
}

- (UIImageView*)customBackgroundImageView{

    UIImageView *backgroundImageView = objc_getAssociatedObject(self, &imageViewkey);
    
    if (!backgroundImageView) {
        
        backgroundImageView = [[UIImageView alloc] initWithFrame:self.bounds];
        [self insertSubview:backgroundImageView atIndex:0];
         objc_setAssociatedObject(self, &imageViewkey, backgroundImageView, OBJC_ASSOCIATION_RETAIN);
    }
    return backgroundImageView;
}

- (void)customBackgroundImageViewNormal:(UIImage*)normalImage
                      imageViewSelected:(UIImage*)imageViewSelected{
    
    NSMutableDictionary  *iamgeInfo =[[NSMutableDictionary alloc] init];
    if (normalImage) {
        [iamgeInfo setObject:normalImage forKey:normalKey];
    }
    if (imageViewSelected) {
        [iamgeInfo setObject:imageViewSelected forKey:selectedKey];
    }
    
    self.customBackgroundImageView.image = normalImage;
    objc_setAssociatedObject(self, &imageKey, iamgeInfo, OBJC_ASSOCIATION_COPY);
    [self addTarget:self action:@selector(normalEvent:) forControlEvents:UIControlEventTouchCancel];
    [self addTarget:self action:@selector(selectedEvent:) forControlEvents:UIControlEventTouchUpInside];    

}


- (NSDictionary*)customImages{
    NSDictionary  *iamgeInfo = objc_getAssociatedObject(self, &imageKey);
    if ([iamgeInfo isKindOfClass:[NSDictionary class]]) {
        return iamgeInfo;
    }
    return nil;
}

- (void)normalEvent:(UIButton*)sender{

    self.customBackgroundImageView.image = [self customImages][normalKey];
}

- (void)selectedEvent:(UIButton*)sender{
   self.customBackgroundImageView.image = [self customImages][selectedKey];
}

- (void)acustomSelected:(BOOL)bl{
    if (bl) {
        [self selectedEvent:self];
    }else{
        [self normalEvent:self];
    }
    [self acustomSelected:bl];
}



/**
 *  APP运行的时候只能调用一次，不能多次调用可能把方法还原
 */
+ (void)setupInit{
    Method originalMenthod = class_getInstanceMethod([self class], @selector(setSelected:));
    Method swappedMenthod = class_getInstanceMethod([self class], @selector(acustomSelected:));
    method_exchangeImplementations(originalMenthod, swappedMenthod);
}

@end
