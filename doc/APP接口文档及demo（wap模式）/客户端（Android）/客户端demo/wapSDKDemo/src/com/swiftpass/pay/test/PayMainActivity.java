package com.swiftpass.pay.test;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.swiftpass.cn.pay.test.R;
import com.switfpass.pay.MainApplication;
import com.switfpass.pay.activity.PayPlugin;
import com.switfpass.pay.bean.RequestMsg;
import com.switfpass.pay.utils.MD5;
import com.switfpass.pay.utils.SignUtils;
import com.switfpass.pay.utils.Util;
import com.switfpass.pay.utils.XmlUtils;

public class PayMainActivity extends Activity
{
    String TAG = "PayMainActivity";
    
    private EditText money, body, mchId, notifyUrl, orderNo, signKey, appId, seller_id, credit_pay;
    
    @Override
    protected void onResume()
    {
        super.onResume();
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
    }
    
    @Override
    protected void onStop()
    {
        super.onStop();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_main);
        
        Button button = (Button)findViewById(R.id.submitPay);
        
        money = (EditText)findViewById(R.id.money);
        
        body = (EditText)findViewById(R.id.body);
        
        mchId = (EditText)findViewById(R.id.mchId);
        
        notifyUrl = (EditText)findViewById(R.id.notifyUrl);
        
        orderNo = (EditText)findViewById(R.id.orderNo);
        
        signKey = (EditText)findViewById(R.id.signKey);
        
        appId = (EditText)findViewById(R.id.appId);
        
        seller_id = (EditText)findViewById(R.id.seller_id);
        
        credit_pay = (EditText)findViewById(R.id.credit_pay);
        
        button.setOnClickListener(new View.OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                
                new GetPrepayIdTask().execute();
            }
        });
    }
    
    private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String, String>>
    {
        
        private ProgressDialog dialog;
        
        private String accessToken;
        
        public GetPrepayIdTask(String accessToken)
        {
            this.accessToken = accessToken;
        }
        
        public GetPrepayIdTask()
        {
        }
        
        @Override
        protected void onPreExecute()
        {
            dialog =
                ProgressDialog.show(PayMainActivity.this,
                    getString(R.string.app_tip),
                    getString(R.string.getting_prepayid));
        }
        
        @Override
        protected void onPostExecute(Map<String, String> result)
        {
            if (dialog != null)
            {
                dialog.dismiss();
            }
            if (result == null)
            {
                Toast.makeText(PayMainActivity.this, getString(R.string.get_prepayid_fail), Toast.LENGTH_LONG).show();
            }
            else
            {
                if (result.get("status").equalsIgnoreCase("0")) // 成功
                {
                    
                    Toast.makeText(PayMainActivity.this, R.string.get_prepayid_succ, Toast.LENGTH_LONG).show();
                    RequestMsg msg = new RequestMsg();
                    msg.setTokenId(result.get("token_id"));
                    // 微信wap支付
                    msg.setTradeType(MainApplication.PAY_WX_WAP);
                    PayPlugin.unifiedH5Pay(PayMainActivity.this, msg);
                    
                    //                    // 手Q wap支付
                    //                    msg.setTradeType(MainApplication.PAY_QQ_WAP);
                    //                    PayPlugin.unifiedH5Pay(PayMainActivity.this, msg);
                    //                    
                }
                else
                {
                    Toast.makeText(PayMainActivity.this, getString(R.string.get_prepayid_fail), Toast.LENGTH_LONG)
                        .show();
                }
                
            }
            
        }
        
        @Override
        protected void onCancelled()
        {
            super.onCancelled();
        }
        
        @Override
        protected Map<String, String> doInBackground(Void... params)
        {
            // 统一预下单接口
            String url = "https://pay.swiftpass.cn/pay/gateway";
            
            String entity = getParams();
            
            Log.d(TAG, "doInBackground, url = " + url);
            Log.d(TAG, "doInBackground, entity = " + entity);
            
            byte[] buf = Util.httpPost(url, entity);
            if (buf == null || buf.length == 0)
            {
                return null;
            }
            String content = new String(buf);
            Log.d(TAG, "doInBackground, content = " + content);
            try
            {
                return XmlUtils.parse(content);
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        }
    }
    
    private String genNonceStr()
    {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }
    
    String payOrderNo;
    
    /**
     * 组装参数
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    private String getParams()
    {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("body", "SPay收款"); // 商品名称
        params.put("service", "unified.trade.pay"); // 支付类型
        params.put("version", "1.0"); // 版本
        params.put("mch_id", "7551000001"); // 威富通商户号
        //        params.put("mch_id", mchId.getText().toString()); // 威富通商户号
        params.put("notify_url", "http://zhifu.dev.swiftpass.cn/spay/notify"); // 后台通知url
        params.put("nonce_str", genNonceStr()); // 随机数
        payOrderNo = genOutTradNo();
        params.put("out_trade_no", payOrderNo); //订单号
        params.put("mch_create_ip", "127.0.0.1"); // 机器ip地址
        params.put("total_fee", money.getText().toString()); // 总金额
        params.put("limit_credit_pay", credit_pay.getText().toString()); // 是否限制信用卡支付， 0：不限制（默认），1：限制
        String sign = createSign("9d101c97133837e13dde2d32a5054abb", params); // 9d101c97133837e13dde2d32a5054abb 威富通密钥
        
        params.put("sign", sign); // sign签名
        
        return XmlUtils.toXml(params);
    }
    
    public String createSign(String signKey, Map<String, String> params)
    {
        StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
        SignUtils.buildPayParams(buf, params, false);
        buf.append("&key=").append(signKey);
        String preStr = buf.toString();
        String sign = "";
        // 获得签名验证结果
        try
        {
            sign = MD5.md5s(preStr).toUpperCase();
        }
        catch (Exception e)
        {
            sign = MD5.md5s(preStr).toUpperCase();
        }
        return sign;
    }
    
    private String genOutTradNo()
    {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }
    
    public void buildPayParams(StringBuilder sb, Map<String, String> payParams, boolean encoding)
    {
        List<String> keys = new ArrayList<String>(payParams.keySet());
        Collections.sort(keys);
        for (String key : keys)
        {
            sb.append(key).append("=");
            if (encoding)
            {
                sb.append(urlEncode(payParams.get(key)));
            }
            else
            {
                sb.append(payParams.get(key));
            }
            sb.append("&");
        }
        sb.setLength(sb.length() - 1);
    }
    
    public String urlEncode(String str)
    {
        try
        {
            return URLEncoder.encode(str, "UTF-8");
        }
        catch (Throwable e)
        {
            return str;
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        
        if (data == null)
        {
            return;
        }
        
        String respCode = data.getExtras().getString("resultCode");
        if (!TextUtils.isEmpty(respCode) && respCode.equalsIgnoreCase("success"))
        {
            //标示支付成功
            Toast.makeText(PayMainActivity.this, "支付成功", Toast.LENGTH_LONG).show();
        }
        else
        { //其他状态NOPAY状态：取消支付，未支付等状态
            Toast.makeText(PayMainActivity.this, "未支付", Toast.LENGTH_LONG).show();
        }
        
        super.onActivityResult(requestCode, resultCode, data);
        
    }
}
