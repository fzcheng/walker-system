package com.walkersoft.appmanager.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
/**
 * 支付宝通知
 * @author shikeying
 *
 */
@Entity
@Table(name = "yl_ali_callback_record")
public class AliCallbackEntity {
	int id;
	
	@Column(name="notify_time")
	String	notify_time;
	@Column(name="notify_type")
	String	notify_type;
	@Column(name="notify_id")
	String	notify_id;
	@Column(name="sign_type")
	String	sign_type;
	@Column(name="sign")
	String	sign;
	@Column(name="out_trade_no")
	String	out_trade_no;
	@Column(name="subject")
	String	subject;
	@Column(name="payment_type")
	String	payment_type;
	@Column(name="trade_no")
	String	trade_no;
	@Column(name="trade_status")
	String	trade_status;
	@Column(name="seller_id")
	String	seller_id;
	@Column(name="seller_email")
	String	seller_email;
	@Column(name="buyer_id")
	String	buyer_id;
	@Column(name="buyer_email")
	String	buyer_email;
	@Column(name="total_fee")
	float	total_fee;
	@Column(name="quantity")
	int	quantity;
	@Column(name="price")
	float	price;
	@Column(name="body")
	String	body;
	@Column(name="gmt_create")
	String	gmt_create;
	@Column(name="gmt_payment")
	String	gmt_payment;
	@Column(name="is_total_fee_adjust")
	String	is_total_fee_adjust;
	@Column(name="use_coupon")
	String	use_coupon;
	@Column(name="discount")
	String	discount;
	@Column(name="refund_status")
	String	refund_status;
	@Column(name="gmt_refund")
	String	gmt_refund;
	
	@Id
	@Column(name="id", length=11)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNotify_time() {
		return notify_time;
	}
	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
	}
	public String getNotify_type() {
		return notify_type;
	}
	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}
	public String getNotify_id() {
		return notify_id;
	}
	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getTrade_status() {
		return trade_status;
	}
	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public String getSeller_email() {
		return seller_email;
	}
	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}
	public String getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}
	public String getBuyer_email() {
		return buyer_email;
	}
	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}
	public float getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(float total_fee) {
		this.total_fee = total_fee;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getGmt_create() {
		return gmt_create;
	}
	public void setGmt_create(String gmt_create) {
		this.gmt_create = gmt_create;
	}
	public String getGmt_payment() {
		return gmt_payment;
	}
	public void setGmt_payment(String gmt_payment) {
		this.gmt_payment = gmt_payment;
	}
	public String getIs_total_fee_adjust() {
		return is_total_fee_adjust;
	}
	public void setIs_total_fee_adjust(String is_total_fee_adjust) {
		this.is_total_fee_adjust = is_total_fee_adjust;
	}
	public String getUse_coupon() {
		return use_coupon;
	}
	public void setUse_coupon(String use_coupon) {
		this.use_coupon = use_coupon;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getRefund_status() {
		return refund_status;
	}
	public void setRefund_status(String refund_status) {
		this.refund_status = refund_status;
	}
	public String getGmt_refund() {
		return gmt_refund;
	}
	public void setGmt_refund(String gmt_refund) {
		this.gmt_refund = gmt_refund;
	}
	
	public AliCallbackEntity() {
		
	}
	
	public AliCallbackEntity(HttpServletRequest req) {
		this.setNotify_time(req.getParameter("notify_time"));
		this.setNotify_type(req.getParameter("notify_type"));
		this.setNotify_id(req.getParameter("notify_id"));
		this.setSign_type(req.getParameter("sign_type"));
		this.setSign(req.getParameter("sign"));
		this.setOut_trade_no(req.getParameter("out_trade_no"));
		this.setSubject(req.getParameter("subject"));
		this.setPayment_type(req.getParameter("payment_type"));
		this.setTrade_no(req.getParameter("trade_no"));
		this.setTrade_status(req.getParameter("trade_status"));
		this.setSeller_id(req.getParameter("seller_id"));
		this.setSeller_email(req.getParameter("seller_email"));
		this.setBuyer_id(req.getParameter("buyer_id"));
		this.setBuyer_email(req.getParameter("buyer_email"));
		this.setTotal_fee(Float.valueOf(req.getParameter("total_fee")));
		this.setQuantity(Integer.valueOf(req.getParameter("quantity")));
		this.setPrice(Float.valueOf(req.getParameter("price")));
		this.setBody(req.getParameter("body"));
		this.setGmt_create(req.getParameter("gmt_create"));
		this.setGmt_payment(req.getParameter("gmt_payment"));
		this.setIs_total_fee_adjust(req.getParameter("is_total_fee_adjust"));
		this.setUse_coupon(req.getParameter("use_coupon"));
		this.setDiscount(req.getParameter("discount"));
		this.setRefund_status(req.getParameter("refund_status"));
		this.setGmt_refund(req.getParameter("gmt_refund"));
	}
}
