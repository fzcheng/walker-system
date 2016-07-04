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
    public partial class queryRefund : System.Web.UI.Page
    {
        private ClientResponseHandler resHandler = new ClientResponseHandler();
        private PayHttpClient pay = new PayHttpClient();
        private RequestHandler reqHandler = null;
        private Dictionary<string, string> cfg = new Dictionary<string, string>(1);
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {

            }
        }

        protected void btnok_Click(object sender, EventArgs e)
        {
            if (tbout_trade_no.Text == "" && tbtransaction_id.Text == "")
            {
                Response.Write("<script>alert('请输入商户订单号或者威富通订单号！')</script>");
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
                this.reqHandler.setParameter("transaction_id", tbtransaction_id.Text);//威富通订单号     
                this.reqHandler.setParameter("out_refund_no", tbout_refund_no.Text);//商户退款单号
                this.reqHandler.setParameter("refund_id", tbrefund_id.Text);//威富通退款单号 

                this.reqHandler.setParameter("service", "unified.trade.refundquery");//接口 unified.trade.refundquery 
                this.reqHandler.setParameter("mch_id", this.cfg["mch_id"].ToString());//必填项，商户号，由威富通分配
                this.reqHandler.setParameter("version", this.cfg["version"].ToString());//接口版本号 
                this.reqHandler.setParameter("nonce_str", Utils.random());//随机字符串，必填项，不长于 32 位
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
                        //当返回状态与业务结果都为0时才返回结果，其它结果请查看接口文档
                        if (int.Parse(param["status"].ToString()) == 0 && int.Parse(param["result_code"].ToString()) == 0)
                        {
                            Utils.writeFile("查询退款", param);
                            Response.Write("<script>alert('查询退款成功，请查看result.txt文件！')</script>");
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