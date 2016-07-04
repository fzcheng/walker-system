using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using swiftpass.utils;
using System.Collections;
using System.Collections.Specialized;
using System.IO;
using System.Xml;
using System.Web.Script.Serialization;
using System.Text;

namespace WFTpayInterface
{
    public partial class Index : System.Web.UI.Page
    { 
        private ClientResponseHandler resHandler = new ClientResponseHandler();
        private PayHttpClient pay = new PayHttpClient();
        private RequestHandler reqHandler = null;
        private Dictionary<string, string> cfg = new Dictionary<string, string>(1);
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                this.tbout_trade_no.Text = Utils.Nmrandom();//商户订单号 
                //this.tbmch_create_ip.Text = Request.UserHostAddress;
               
                string userHostAddress = HttpContext.Current.Request.UserHostAddress;
                
                if (string.IsNullOrEmpty(userHostAddress))
                {
                    userHostAddress = HttpContext.Current.Request.ServerVariables["REMOTE_ADDR"];
                }
               
                if (string.IsNullOrEmpty(userHostAddress))
                {
                    userHostAddress = HttpContext.Current.Request.UserHostAddress;
                }
                //最后判断获取是否成功，并检查IP地址的格式（检查其格式非常重要）
                if (!string.IsNullOrEmpty(userHostAddress) && IsIP(userHostAddress))
                {
                       this.tbmch_create_ip.Text = userHostAddress;
                }
                else{
                  this.tbmch_create_ip.Text =  "127.0.0.1";
                }
            }
        }
        /// <summary>
        /// 检查IP地址格式
        /// </summary>
        /// <param name="ip"></param>
        /// <returns></returns>  
        public static bool IsIP(string ip)
        {
            return System.Text.RegularExpressions.Regex.IsMatch(ip, @"^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$");
        }
        /// <summary>
        /// 订单提交
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        protected void btnok_Click(object sender, EventArgs e)
        {
            if (tbtotal_fee.Text == "0")
            {
                Response.Write("<script>alert('总金额不能为【0】！')</script>");
            }
            else
            {                
                this.reqHandler = new RequestHandler(null);
                //加载配置数据
                this.cfg = Utils.loadCfg(); ;
                //初始化数据  
                this.reqHandler.setGateUrl(this.cfg["req_url"].ToString());
                this.reqHandler.setKey(this.cfg["key"].ToString());
                this.reqHandler.setParameter("out_trade_no", tbout_trade_no.Text);//商户订单号
                this.reqHandler.setParameter("body", tbbody.Text);//商品描述
                this.reqHandler.setParameter("attach", tbattach.Text);//附加信息
                this.reqHandler.setParameter("total_fee", tbtotal_fee.Text);//总金额
                this.reqHandler.setParameter("mch_create_ip", tbmch_create_ip.Text);//终端IP 
                this.reqHandler.setParameter("service", "unified.trade.pay");//接口类型： 
                this.reqHandler.setParameter("mch_id", this.cfg["mch_id"].ToString());//必填项，商户号，由威富通分配
                this.reqHandler.setParameter("version", this.cfg["version"].ToString());//接口版本号
                this.reqHandler.setParameter("notify_url", this.cfg["notify_url"].ToString());
                //通知地址，必填项，接收威富通通知的URL，需给绝对路径，255字符内;此URL要保证外网能访问   
                this.reqHandler.setParameter("nonce_str", Utils.random());//随机字符串，必填项，不长于 32 位
                this.reqHandler.setParameter("charset", "UTF-8");//字符集
                this.reqHandler.setParameter("sign_type", "MD5");//签名方式   
                this.reqHandler.createSign();//创建签名
                //以上参数进行签名
                string data = Utils.toXml(this.reqHandler.getAllParameters());//生成XML报文
                Dictionary<string, string> reqContent = new Dictionary<string, string>();
                reqContent.Add("url", this.reqHandler.getGateUrl());
                reqContent.Add("data", data);
                this.pay.setReqContent(reqContent);

                if (this.pay.call())
                {
                    this.resHandler.setContent(this.pay.getResContent());
                    this.resHandler.setKey(this.cfg["key"].ToString());
                    Hashtable param = this.resHandler.getAllParameters();
                    if (this.resHandler.isTenpaySign())
                    {
                        //当返回状态与业务结果都为0时才返回，其它结果请查看接口文档
                        //if (int.Parse(param["status"].ToString()) == 0 && int.Parse(param["result_code"].ToString()) == 0)//后续result_code会返回出来。
                        if (int.Parse(param["status"].ToString()) == 0 )
                        {
                            Response.Write("<script>alert('调用预下单取得token_id=" + param["token_id"].ToString() 
                                + "                             支付类型services=" + param["services"].ToString() + "')</script>"); 
                           //services是返回全部的支付类型，此处仅用pay.weixin.wappay
                        }
                        else
                        {
                            Response.Write("<script>alert('错误代码：" + param["err_code"] + ",错误信息：" + param["err_msg"] + "')</script>");
                        }

                    }
                    else
                    {
                        Response.Write("<script>alert('错误代码：" + param["status"] + ",错误信息：" + param["message"] + "')</script>");
                    }
                }
                else
                {
                    Response.Write("<script>alert('错误代码：" + this.pay.getResponseCode() + ",错误信息：" + this.pay.getErrInfo() + "')</script>");
                }
            }
        }
       
    }
}