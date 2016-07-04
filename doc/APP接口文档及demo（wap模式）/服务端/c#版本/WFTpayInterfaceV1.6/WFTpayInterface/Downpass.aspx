<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Downpass.aspx.cs" Inherits="WFTpayInterface.Downpass" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>对账单下载</title>
    <link href="css/wft.css" rel="stylesheet" type="text/css"></link>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <div id="header"> 
            <div class="htleft"><a href="Index.aspx"> <img src="image/zhifu_logo.png" /></a></div>
            <div class="htright"> 客服电话：400-990-3238（每天9:00-18:00） </div>
        </div>

        <div id="content">
            <div id="menu">
                <div id="item">
                    <div class="span">接口测试</div>
                    <ul>
                        <li><a href="Index.aspx" >支付测试</a></li>
                        <li><a href="queryOrder.aspx" >订单查询测试</a></li>
                        <li><a href="refundTest.aspx" >退款测试</a></li>
                        <li><a href="queryRefund.aspx"  >退款查询测试</a></li>
                        <li><a href="Downpass.aspx">对账单下载</a></li>
                        
                    </ul>
                </div>
                
            </div>
            <div id="auto_center">
                <div class="nrtitle">
                    对账单下载
                </div>
                <div class="nrmain">
                <p />
                日期：<asp:TextBox ID="td1" runat="server" Height="26px" Width="179px"></asp:TextBox>
                <asp:Label ID="Label1" runat="server" ForeColor="Red" Text="请注意：输入日期格式：YYYYMMDD例如：20150101"></asp:Label>
                <br />
                <asp:Button ID="down" runat="server" Height="44px" OnClick="down_Click" Text="下载（Download）" Width="239px" Font-Bold="True" Font-Names="Arial" ForeColor="Blue" Font-Size="Large" />

                </div>

            </div>
            </div>
            <div id="footer">
                <p>深圳市威富通科技有限公司</p>
                <p>深圳市南山区高新南一道9号中科技大厦25楼</p>
                <p>客服热线：<em>400-990-3238</em></p>
            </div>
   
    </div>
    </form>
</body>
</html>
