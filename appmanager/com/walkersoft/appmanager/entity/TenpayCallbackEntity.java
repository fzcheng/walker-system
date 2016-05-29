package com.walkersoft.appmanager.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 支付宝通知
 * @author shikeying
 *
 */
@Entity
@Table(name = "yl_tenpay_callback_record")
public class TenpayCallbackEntity {
	int id;
	
	@Column(name="appid")
	String	appid;
	@Column(name="attach")
	String	attach;
	@Column(name="bank_type")
	String	bank_type;
	@Column(name="cash_fee")
	String	cash_fee;
	@Column(name="fee_type")
	String	fee_type;
	@Column(name="is_subscribe")
	String	is_subscribe;
	@Column(name="mch_id")
	String	mch_id;
	@Column(name="nonce_str")
	String	nonce_str;
	@Column(name="openid")
	String	openid;
	@Column(name="out_trade_no")
	String	out_trade_no;
	@Column(name="result_code")
	String	result_code;
	@Column(name="return_code")
	String	return_code;
	@Column(name="sign")
	String	sign;
	@Column(name="time_end")
	String	time_end;
	@Column(name="total_fee")
	String	total_fee;
	@Column(name="trade_type")
	String	trade_type;
	@Column(name="transaction_id")
	String	transaction_id;
	@Column(name="body", length=512)
	String	body;
	@Column(name="create_time")
	Timestamp	create_time;
	
	@Id
	@Column(name="id", length=11)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public TenpayCallbackEntity() {
		
	}
	
	public TenpayCallbackEntity(Map<String, String> map, String body) {
		this.setAppid(map.get("appid"));
		this.setAttach(map.get("attach"));
		this.setBank_type(map.get("bank_type"));
		this.setCash_fee(map.get("cash_fee"));
		this.setFee_type(map.get("fee_type"));
		this.setIs_subscribe(map.get("is_subscribe"));
		this.setMch_id(map.get("mch_id"));
		this.setNonce_str(map.get("nonce_str"));
		this.setOpenid(map.get("openid"));
		this.setOut_trade_no(map.get("out_trade_no"));
		this.setResult_code(map.get("result_code"));
		this.setReturn_code(map.get("return_code"));
		this.setSign(map.get("sign"));
		this.setTime_end(map.get("time_end"));
		this.setTotal_fee(map.get("total_fee"));
		this.setTrade_type(map.get("trade_type"));
		this.setTransaction_id(map.get("transaction_id"));
		
		if(body.length() >= 4090)
		{
			this.setBody("");
		}
		else
		{
			this.setBody(body);
		}
		
		Date date = new Date();       
		create_time = new Timestamp(date.getTime());
	}
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getBank_type() {
		return bank_type;
	}
	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}
	public String getCash_fee() {
		return cash_fee;
	}
	public void setCash_fee(String cash_fee) {
		this.cash_fee = cash_fee;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	public String getIs_subscribe() {
		return is_subscribe;
	}
	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
}
