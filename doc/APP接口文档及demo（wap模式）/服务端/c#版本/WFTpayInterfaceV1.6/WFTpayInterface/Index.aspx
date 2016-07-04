<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Index.aspx.cs" Inherits="WFTpayInterface.Index" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>微信 支付测试页面</title>
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
                        <li><a href="Downpass.aspx" >对账单下载</a></li>
                        
                    </ul>
                </div>
                
            </div>
            <div id="auto_center">
                <div class="nrtitle">
                    支付测试（微信 支付）
                </div>
                <div class="nrmain">
                    <div class="filedkd">商户订单号&nbsp;&nbsp; ：<asp:TextBox ID="tbout_trade_no" runat="server" Width="267px" Height="26px" MaxLength="32" ToolTip="长度32"></asp:TextBox>
                        <asp:Label ID="Label1" runat="server" ForeColor="Red" Text="*长度32"></asp:Label>
                        <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" ControlToValidate="tbout_trade_no" ErrorMessage="*商户订单号不能为空！"></asp:RequiredFieldValidator>
                    </div>
                    <div class="filedkd">商品描述&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ：<asp:TextBox ID="tbbody" runat="server" Width="267px" Height="26px" ToolTip="长度127" TextMode="MultiLine">测试购买商品</asp:TextBox>
                        <asp:Label ID="Label2" runat="server" ForeColor="Red" Text="*长度127"></asp:Label>
                        <asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server" ControlToValidate="tbbody" ErrorMessage="*商品描述不能为空！"></asp:RequiredFieldValidator>
                    </div>
                    <div class="filedkd">附加信息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ：<asp:TextBox ID="tbattach" runat="server" Width="267px" Height="26px" ToolTip="长度128" TextMode="MultiLine">附加信息</asp:TextBox>
                        <asp:Label ID="Label3" runat="server" ForeColor="Black" Text="长度128"></asp:Label>
                    </div>
                    <div class="filedkd">总金额&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ：<asp:TextBox ID="tbtotal_fee" Width="267px" Height="26px" runat="server" TextMode="Number" ToolTip="单位：分 整型">1</asp:TextBox>
                        <asp:Label ID="Label4" runat="server" ForeColor="Red" Text="*单位：分 整型"></asp:Label>
                        <asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" ControlToValidate="tbtotal_fee" ErrorMessage="*总金额不能为空"></asp:RequiredFieldValidator>
                    </div>
                    <div class="filedkd">终端IP&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ：<asp:TextBox ID="tbmch_create_ip" Height="26px" runat="server" ToolTip="长度16" >127.0.0.1</asp:TextBox>
                        <asp:Label ID="Label5" runat="server" ForeColor="Red" Text="*长度16"></asp:Label>
                        <asp:RequiredFieldValidator ID="RequiredFieldValidator4" runat="server" ControlToValidate="tbmch_create_ip" ErrorMessage="*终端IP不能为空！"></asp:RequiredFieldValidator>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </div>
                    
                    <div class="filedkd">订单生成时间：<asp:TextBox ID="tbtime_start" runat="server" Height="26px" ToolTip="长度14;yyyyMMddHHmmss" TextMode="Number"></asp:TextBox>
                        <asp:Label ID="Label6" runat="server" ForeColor="Black" Text="长度14;yyyyMMddHHmmss"></asp:Label>
                    </div>
                    <div class="filedkd">订单超时时间：<asp:TextBox ID="tbtime_expire" runat="server" Height="26px" ToolTip="长度14;yyyyMMddHHmmss" TextMode="Number"></asp:TextBox>
                        <asp:Label ID="Label7" runat="server" ForeColor="Black" Text="长度14;yyyyMMddHHmmss"></asp:Label>
                    </div>
                    <div class="filedkd">
                        <asp:Button ID="btnok" runat="server" Font-Bold="True" Font-Names="Arial" Font-Size="14pt" ForeColor="Blue" Height="34px" Text="确定" Width="124px" OnClick="btnok_Click" />
                   
                        <br />
                   
                        <asp:Label ID="result" runat="server" Font-Bold="True" Font-Size="12pt" ForeColor="White" BackColor="Black" BorderColor="Red" BorderWidth="3px"></asp:Label>
                   
                    </div>

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
