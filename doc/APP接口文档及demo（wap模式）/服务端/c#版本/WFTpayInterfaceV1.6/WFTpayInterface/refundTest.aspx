<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="refundTest.aspx.cs" Inherits="WFTpayInterface.refundTest" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>退款测试</title>
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
                    退款测试 
                </div>
                <div class="nrmain">
                    <div class="filedkd">商户订单号&nbsp;&nbsp; ：<asp:TextBox ID="tbout_trade_no" runat="server" Width="267px" Height="26px" MaxLength="32" ToolTip="长度32"></asp:TextBox>
                        <asp:Label ID="Label1" runat="server" Text="*长度32"></asp:Label>
                    </div>
                     <div class="filedkd">威富通订单号  ：<asp:TextBox ID="tbtransaction_id" runat="server" Width="267px" Height="26px" ToolTip="长度32"></asp:TextBox>
                        <asp:Label ID="Label8" runat="server" Text="*长度32"></asp:Label>
                    </div> 
                    <div class="filedkd">商户退款单号 ：<asp:TextBox ID="tbout_refund_no" runat="server" Width="267px" Height="26px" ToolTip="长度32"></asp:TextBox>
                        <asp:Label ID="Label2" runat="server" ForeColor="Red" Text="*长度32"></asp:Label>
                        <asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server" ControlToValidate="tbout_refund_no" ErrorMessage="*商户退款单号不能为空！"></asp:RequiredFieldValidator>
                    </div>
                   
                    <div class="filedkd">总金额&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ：<asp:TextBox ID="tbtotal_fee" Width="267px" Height="26px" runat="server" TextMode="Number" ToolTip="单位：分 整型">1</asp:TextBox>
                        <asp:Label ID="Label4" runat="server" ForeColor="Red" Text="*单位：分 整型"></asp:Label>
                        <asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" ControlToValidate="tbtotal_fee" ErrorMessage="*总金额不能为空"></asp:RequiredFieldValidator>
                    </div>
                     <div class="filedkd">退款金额&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  ：<asp:TextBox ID="tbrefund_fee" Width="267px" Height="26px" runat="server" TextMode="Number" ToolTip="单位：分 整型"></asp:TextBox>
                        <asp:Label ID="Label3" runat="server" ForeColor="Red" Text="*单位：分 整型"></asp:Label>
                        <asp:RequiredFieldValidator ID="RequiredFieldValidator5" runat="server" ControlToValidate="tbrefund_fee" ErrorMessage="*退款金额不能为空"></asp:RequiredFieldValidator>
                    </div>
                    <div class="filedkd">退款渠道&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ：<asp:DropDownList ID="Drrefund_channel" runat="server" Width="267px" Height="26px" Enabled="False">
                        <asp:ListItem Value="ORIGINAL">原路返回(默认)</asp:ListItem>
                        </asp:DropDownList>
                    </div>
                   
                    <div class="filedkd">
                        <asp:Button ID="btnok" runat="server" Font-Bold="True" Font-Names="Arial" Font-Size="14pt" ForeColor="Blue" Height="34px" Text="确定" Width="124px" OnClick="btnok_Click" />
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
