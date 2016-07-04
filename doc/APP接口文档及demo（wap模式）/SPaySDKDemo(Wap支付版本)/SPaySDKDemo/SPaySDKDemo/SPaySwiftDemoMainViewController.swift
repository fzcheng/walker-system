//
//  SPaySwiftDemoMainViewController.swift
//  SPaySDKDemo
//
//  Created by wongfish on 16/3/15.
//  Copyright © 2016年 wongfish. All rights reserved.
//


import Foundation
import UIKit



class SPaySwiftDemoMainViewController:UIViewController{

    var spayTokenIDString : NSString = ""
    var amount :NSNumber = 0.0
    var payServicesString :NSString = ""
    
  
    
    override func viewDidLoad(){
    
        super.viewDidLoad()
        
   
         SPayClient.sharedInstance().pay(self, amount: amount, spayTokenIDString: spayTokenIDString as String, payServicesString: payServicesString as String) { (payStateModel, paySuccessDetailModel) -> Void in
            
            if(payStateModel.payState == SPayClientConstEnumPaySuccess){
                NSLog("%@", "支付成功");
            }else{
            
                NSLog("%@", "支付失败");
            }
            
        };
        
       
              
    }
    
}
