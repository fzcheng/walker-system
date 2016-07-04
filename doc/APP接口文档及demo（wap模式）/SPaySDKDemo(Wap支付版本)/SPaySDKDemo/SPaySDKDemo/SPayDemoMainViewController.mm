//
//  SPayDemoMainViewController.m
//  SPaySDKDemo
//
//  Created by wongfish on 15/6/11.
//  Copyright (c) 2015年 wongfish. All rights reserved.
//

#import "SPayDemoMainViewController.h"


#import "SPayClient.h"
#import "NSString+SPayUtilsExtras.h"
#import "NSDictionary+SPayUtilsExtras.h"


#import "SPRequestForm.h"
#import "SPHTTPManager.h"
#import "SPConst.h"

#import <MBProgressHUD.h>
#import <XMLReader.h>

#import "SPaySDKDemo-Swift.h"
#import "SPaySDKDemo-Bridging-Header.h"







@interface SPayDemoMainViewController ()<UITextViewDelegate>

/**
 *  接口类型
 */
@property (nonatomic,weak) IBOutlet UITextField *serviceText;

/**
 *  版本号
 */
@property (nonatomic,weak) IBOutlet UITextField *versionText;

/**
 *  字符集
 */
@property (nonatomic,weak) IBOutlet UITextField *charsetText;

/**
 *  签名方式
 */
@property (nonatomic,weak) IBOutlet UITextField *sign_typeText;

/**
 *  商户号
 */
@property (nonatomic,weak) IBOutlet UITextField *mch_ideText;

/**
 *  商户订单号
 */
@property (nonatomic,weak) IBOutlet UITextField *out_trade_noText;


/**
 *  商品描述
 */
@property (nonatomic,weak) IBOutlet UITextField *bodyText;

/**
 *  总金额
 */
@property (nonatomic,weak) IBOutlet UITextField *total_feeText;

/**
 *  终端IP
 */
@property (nonatomic,weak) IBOutlet UITextField *mch_create_ipText;

/**
 *  通知地址
 */
@property (nonatomic,weak) IBOutlet UITextField *notify_urlText;

@property (nonatomic,strong)  MBProgressHUD *hud;

@property (nonatomic,weak) IBOutlet UISwitch *isSwitfPay;


@end

@implementation SPayDemoMainViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    
    self.out_trade_noText.text = [NSString spay_out_trade_no];
    
    
    
    
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField{
    
    [self.view endEditing:YES];
    return YES;
}



- (void)swiftlyPay:(NSNumber*)amount
spayTokenIDString:(NSString*)spayTokenIDString
payServicesString:(NSString*)payServicesString{

    
    SPaySwiftDemoMainViewController *spay = [self.navigationController.storyboard instantiateViewControllerWithIdentifier:@"SPaySwiftDemoMainViewController"];
    spay.amount = amount;
    spay.spayTokenIDString = [spayTokenIDString copy];
    spay.payServicesString = [payServicesString copy];

    [self.navigationController pushViewController:spay animated:YES];
    


}


- (IBAction)payAction:(id)sender{
    
    
    

    
    self.hud = [MBProgressHUD showHUDAddedTo:self.navigationController.view
                                    animated:YES];
    [self.hud show:YES];


    NSString *service = self.serviceText.text;
    NSString *version = self.versionText.text;;
    NSString *charset = self.charsetText.text;
    NSString *sign_type = self.sign_typeText.text;
    NSString *mch_id = self.mch_ideText.text;
    NSString *out_trade_no = self.out_trade_noText.text;
    NSString *device_info = @"WP10000100001";
    NSString *body = self.bodyText.text;
    NSInteger total_fee = [self.total_feeText.text integerValue];
    NSString *mch_create_ip = self.mch_create_ipText.text;
    NSString *notify_url = self.notify_urlText.text;
    NSString *time_start;
    NSString *time_expire;
    NSString *nonce_str = [NSString spay_nonce_str];


    NSNumber *amount = [NSNumber numberWithInteger:total_fee];
    
    //生成提交表单
    NSDictionary *postInfo = [[SPRequestForm sharedInstance]
                              spay_pay_gateway:service
                              version:version
                              charset:charset
                              sign_type:sign_type
                              mch_id:mch_id
                              out_trade_no:out_trade_no
                              device_info:device_info
                              body:body
                              total_fee:total_fee
                              mch_create_ip:mch_create_ip
                              notify_url:notify_url
                              time_start:time_start
                              time_expire:time_expire
                              nonce_str:nonce_str];
    
    __weak typeof(self) weakSelf = self;
    
    
    //调用支付预下单接口
    [[SPHTTPManager sharedInstance] post:kSPconstWebApiInterface_spay_pay_gateway
                                paramter:postInfo
                                 success:^(id operation, id responseObject) {
                                     
                                     
                                     //返回的XML字符串,如果解析有问题可以打印该字符串
                                     //        NSString *response = [[NSString alloc] initWithData:(NSData *)responseObject encoding:NSUTF8StringEncoding];
                                     
                                     NSError *erro;
                                     //XML字符串 to 字典
                                     //!!!! XMLReader最后节点都会设置一个kXMLReaderTextNodeKey属性
                                     //如果要修改XMLReader的解析，请继承该类然后再去重写，因为SPaySDK也是调用该方法解析数据，如果修改了会导致解析失败
                                     NSDictionary *info = [XMLReader dictionaryForXMLData:(NSData *)responseObject error:&erro];
                                     
                                     NSLog(@"预下单接口返回数据-->>\n%@",info);
                                     
                                     
                                     //判断解析是否成功
                                     if (info && [info isKindOfClass:[NSDictionary class]]) {
                                         
                                         NSDictionary *xmlInfo = info[@"xml"];
                                         
                                         NSInteger status = [xmlInfo[@"status"][@"text"] integerValue];
                                         
                                         //判断SPay服务器返回的状态值是否是成功,如果成功则调起SPaySDK
                                         if (status == 0) {
                                             
                                             [weakSelf.hud hide:YES];
                                             
                                             //获取SPaySDK需要的token_id
                                             NSString *token_id = xmlInfo[@"token_id"][@"text"];
                                             
                                             //获取SPaySDK需要的services
                                             NSString *services = xmlInfo[@"services"][@"text"];
                                             
                                             
                                             if (!weakSelf.isSwitfPay.isOn) {
                                                 
                                                 //调起SPaySDK支付
                                                 [[SPayClient sharedInstance] pay:weakSelf
                                                                           amount:amount
                                                                spayTokenIDString:token_id
                                                                payServicesString:@"pay.weixin.wappay"
                                                                           finish:^(SPayClientPayStateModel *payStateModel,
                                                                                    SPayClientPaySuccessDetailModel *paySuccessDetailModel) {
                                                                               
                                                                               //更新订单号
                                                                               weakSelf.out_trade_noText.text = [NSString spay_out_trade_no];
                                                                               
                                                                               
                                                                               if (payStateModel.payState == SPayClientConstEnumPaySuccess) {
                                                                                   
                                                                                   NSLog(@"支付成功");
                                                                                   NSLog(@"支付订单详情-->>\n%@",[paySuccessDetailModel description]);
                                                                               }else{
                                                                                   NSLog(@"支付失败，错误号:%d",payStateModel.payState);
                                                                               }
                                                                               
                                                                           }];

                                                 
                                             }else{
                                             
                                                 
                                                 [weakSelf swiftlyPay:amount spayTokenIDString:token_id payServicesString:services];
                                             }
                                         }else{
                                             weakSelf.hud.labelText = xmlInfo[@"message"][@"text"];
                                             [weakSelf.hud hide:YES afterDelay:2.0];
                                         }
                                     }else{
                                         weakSelf.hud.labelText = @"预下单接口，解析数据失败";
                                         [weakSelf.hud hide:YES afterDelay:2.0];
                                     }
                                     
                                     
                                 } failure:^(id operation, NSError *error) {
                                     
                                     weakSelf.hud.labelText = @"调用预下单接口失败";
                                     [weakSelf.hud hide:YES afterDelay:2.0];
                                     NSLog(@"调用预下单接口失败-->>\n%@",error);
                                 }];
    
}



@end
