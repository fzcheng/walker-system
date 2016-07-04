using swiftpass.utils;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.IO;

namespace WFTpayInterface
{
    public partial class Downpass : System.Web.UI.Page
    {  
        private PayHttpClient pay = new PayHttpClient();
        private ClientResponseHandler resHandler = new ClientResponseHandler();
        private Dictionary<string, string> cfg = new Dictionary<string, string>(1);
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {

            }
        } 

        /// <summary>
        /// 获取大写的MD5签名结果
        /// </summary>
        /// <param name="encypStr">需要签名的串</param>
        /// <param name="charset">编码</param>
        /// <returns>返回大写的MD5签名结果</returns>
        public static string GetMD5(string encypStr)
        {
            string retStr;
            MD5CryptoServiceProvider m5 = new MD5CryptoServiceProvider();

            //创建md5对象
            byte[] inputBye;
            byte[] outputBye;

            //使用GB2312编码方式把字符串转化为字节数组．
            try
            {
                inputBye = Encoding.GetEncoding("utf-8").GetBytes(encypStr);
            }
            catch (Exception ex)
            {
                inputBye = Encoding.GetEncoding("GB2312").GetBytes(encypStr);
                Console.WriteLine(ex);
            }
            outputBye = m5.ComputeHash(inputBye);

            retStr = System.BitConverter.ToString(outputBye);
            retStr = retStr.Replace("-", "").ToUpper();
            return retStr;
        }

        protected void down_Click(object sender, EventArgs e)
        {
            if (td1.Text.ToString().Replace(" ", "") != string.Empty)
            {
                if (td1.Text.ToString().Replace(" ", "").Length == 8)
                {
                    this.cfg = Utils.loadCfg();
                    string mch_id = this.cfg["mch_id"];//商户号
                    string key = this.cfg["key"];//密钥
                    string req_url = "https://download.swiftpass.cn/gateway";//请求URL
                    string service = "pay.bill.merchant";
                    string version = this.cfg["version"];//版本 
                    string charset = "";//字符集
                    string bill_date = td1.Text.ToString().Replace(" ", "");//账单日期
                    string bill_type = "ALL";//账单类型
                    string sign_type = "MD5";//签名方式 MD5
                    string nonce_str = Utils.random();//随机字符串 

                    string SLstr = "bill_date=" + bill_date +
                        "&bill_type=" + bill_type + "&mch_id=" + mch_id +
                        "&nonce_str=" + nonce_str + "&service=" + service +
                        "&sign_type=" + sign_type + "&version=" + version +
                        "&key=" + key;
                    string sign = GetMD5(SLstr);//签名 

                    ArrayList allvalue = new ArrayList();
                    allvalue.Add("mch_id");
                    allvalue.Add("version");
                    allvalue.Add("service");
                    allvalue.Add("sign_type");
                    allvalue.Add("nonce_str");
                    allvalue.Add("sign");
                    allvalue.Add("bill_type");
                    allvalue.Add("bill_date");
                    //allvalue.Add("charset");
                    ArrayList allkey = new ArrayList();
                    allkey.Add(mch_id);
                    allkey.Add(version);
                    allkey.Add(service);
                    allkey.Add(sign_type);
                    allkey.Add(nonce_str);
                    allkey.Add(sign);
                    allkey.Add(bill_type);
                    allkey.Add(bill_date);
                    //allkey.Add(charset);
                    Hashtable parameters = new Hashtable();
                    for (int i = 0; i < allvalue.Count; i++)
                    {
                        parameters.Add(allvalue[i], allkey[i]);
                    }
                    string data = Utils.toXml(parameters);

                    Dictionary<string, string> reqContent = new Dictionary<string, string>();
                    reqContent.Add("url", req_url);
                    reqContent.Add("data", data);
                    Dictionary<string, object> resMsg = new Dictionary<string, object>();
                    pay.setReqContent(reqContent);
                    pay.call();
                    string msg = pay.getResContent();
                    string path=bill_date+"-"+DateTime.Now.ToLongTimeString().Replace(":","")+".txt";
                 
                    Response.Clear();
                    Response.Buffer = true;
                    Response.ContentType = "text/richtext";
                    //Response.ContentType = "application/octet-stream";
                    Response.AddHeader("content-disposition", "attachment; filename=" + HttpUtility.UrlEncode(path, Encoding.UTF8));
                    Response.Write(msg);
                    Response.Flush();
                    Response.End();
                }
                else
                {
                    Response.Write("<script>alert('您输入的日期格式不正确！请重新输入！')</script>");
                }
            }
            else
            {
                Response.Write("<script>alert('您输入的日期为空！请重新输入！')</script>");
            }
            

        }
        /// <summary>
        /// 设置参数值
        /// </summary>
        /// <param name="parameter">参数名</param>
        /// <param name="parameterValue">参数值</param>
        public void setParameter(ArrayList parameter, ArrayList parameterValue)
        {
            Hashtable parameters = new Hashtable();
            for (int i = 0; i < parameter.Count; i++)
            {
                for (int j = 0; j < parameterValue.Count; j++)
                {
                    parameters.Add(parameter[i], parameterValue[j]); 
                }
            }
                //parameters.Add(parameter, parameterValue); 
           
        }
        public  string toXml(Hashtable _params)
        {
            StringBuilder sb = new StringBuilder("<xml>");
            foreach (DictionaryEntry de in _params)
            {
                string key = de.Key.ToString();
                sb.Append("<").Append(key).Append("><![CDATA[").Append(de.Value.ToString()).Append("]]></").Append(key).Append(">");
            }

            return sb.Append("</xml>").ToString();
        }
    }
}