package com.walkersoft.appmanager.manager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.walker.infrastructure.utils.Assert;
import com.walkersoft.appmanager.BaseConstant;
import com.walkersoft.appmanager.entity.OrderEntity;
import com.walkersoft.appmanager.util.YL_HttpUtils;

/**
 * 通知给下游
 * @author a
 *
 */
@Repository("transferService")
public class TransferService {
	
	protected static final Log logger = LogFactory.getLog(TransferService.class);
	
	@Autowired
	OrderManagerImpl orderManager;
	
	@Autowired
	private AppManagerImpl appManager;
	
	@Autowired
	TransferRecordService transferRecordService;
	
	//最大线程数
	public final static int MAX_T_SIZE = 10;
	static ExecutorService transferThreadPool;//通知线程池
	static ScheduledExecutorService retransferThreadPool;//重发通知线程池
	
	static
	{
		transferThreadPool = Executors.newFixedThreadPool(MAX_T_SIZE);
		retransferThreadPool = Executors.newScheduledThreadPool(MAX_T_SIZE);
	}

	/**
	 * 添加通知
	 * @param order
	 */
	public void addTransfer(final OrderEntity order) {
		transferThreadPool.execute(new Runnable(){
			
			@Override
			public void run() {
				dealTransfer(order);
			}
		});
		
	}
	
	/**
	 * 添加重发通知
	 * @param order
	 * delaySec 延迟时间 秒
	 */
	public void addReTransfer(final OrderEntity order, int delaySec) {
		
		TimerTask task1 = new TimerTask()  
        {  
            @Override  
            public void run()  
            {  
            	dealTransfer(order); 
            }  
        };  
        
//		retransferThreadPool.execute(new Runnable(){
//			
//			@Override
//			public void run() {
//				dealTransfer(order);
//			}
//		});
        retransferThreadPool.schedule(task1, delaySec, TimeUnit.SECONDS);
	}

	/**
	 * 执行具体通知操作
	 * @param order
	 */
	protected void dealTransfer(OrderEntity order) {
		
		order = orderManager.queryOrderByOrderId(order.getOrderid());
		Assert.notNull(order);
		if(order.getTransfer_status() == BaseConstant.TRANSFER_STATUS_SUCCESS)
		{
			return;
		}
		
		Map<String, String> params = new HashMap<String, String>();
		params = buildMap(order);
		
		String url = order.getTransfer_url();
		String str = "";
		try {
			String content = YL_HttpUtils.getContent(params);
			logger.info("dealTransfer: url-" + url + " content-" + content);
			
			int id = transferRecordService.addRecord(order, url, content);
			if(url == null || url.equals(""))
				str = "url is null";
			else
				str = YL_HttpUtils.URLPost(url,params);
			transferRecordService.updateRecordResponse(id, str);
			
			logger.info("dealTransfer: orderid-" + order.getOrderid() + " resp-" + str);
			if(str != null && str.equals("success"))
			{
				dealTransferSuccess(order);
			}
			else
			{
				dealTransferFail(order);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			dealTransferFail(order);
		}
	}

	/**
	 * 通知失败
	 * @param order
	 */
	private void dealTransferFail(OrderEntity order) {
		order.setTransfer_count(order.getTransfer_count() + 1);
		
		if(order.getTransfer_count() >= 10)
		{
			order.setTransfer_status(BaseConstant.TRANSFER_STATUS_FAIL);
			orderManager.execUpdate(order);
		}
		else
		{
			orderManager.execUpdate(order);
			addReTransfer(order, 10);
		}
	}

	/**
	 * 通知成功
	 * @param order
	 */
	private void dealTransferSuccess(OrderEntity order) {
		order.setTransfer_status(BaseConstant.TRANSFER_STATUS_SUCCESS);
		order.setTransfer_count(order.getTransfer_count() + 1);
		
		orderManager.execUpdate(order);
	}

	private Map<String, String> buildMap(OrderEntity order) {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("appid", order.getAppid());
		params.put("cpOrderId", order.getCpOrderId());
		params.put("ext", order.getExt());
		params.put("market", ""+order.getMarket());
		params.put("nickname", order.getNickname());
		params.put("orderId", order.getOrderid());
		params.put("orderTime", ""+order.getCreate_time());
		params.put("payMode", ""+order.getPaychannel());
		params.put("payTime", ""+order.getPay_time());
		params.put("status", ""+order.getStatus());
		params.put("totalFee", ""+order.getTotalFee());
		params.put("userId", order.getUserId());
		params.put("wares", order.getWares());
		params.put("waresId", order.getWaresId());
		
		String appkey = appManager.getAppkey(""+order.getAppid());
		
		String signori = getQueryString(params) + appkey;
		String sign = DigestUtils.md5Hex(signori);
		params.put("sign", sign);
		
		return params;
	}
	
	private String getQueryString(Map<String,String> parameters) {
        Object[] keys = parameters.keySet().toArray();
        Arrays.sort(keys);
        StringBuffer buf = new StringBuffer();
        for (int index = 0; index < keys.length; index ++) {
            String key= (String)keys[index];
            String value = parameters.get(key);
            if (value == null) {
                value = "";
            }
            try {
                buf.append("&").append(key).append("=").append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
            }
        }
        String queryString = buf.toString();
        if (!queryString.isEmpty()) {
            queryString = queryString.substring(1);
        }
        return queryString;
	}

}
