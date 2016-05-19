package com.walkersoft.appmanager.manager;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.walker.db.PropertyEntry;
import com.walkersoft.appmanager.dao.Transfer_recordDAO;
import com.walkersoft.appmanager.entity.OrderEntity;
import com.walkersoft.appmanager.entity.TransferRecordEntity;


/**
 * 回调记录
 * @author a
 *
 */

@Repository("transferRecordService")
public class TransferRecordService {

	@Autowired
	Transfer_recordDAO transferRecrodDao;
	
	/**
	 * 添加记录 - ipos
	 * @param order
	 * @param url
	 * @param content
	 * @return
	 */
	public int addRecord(OrderEntity order, String url, String content) {
		try{
			TransferRecordEntity record = new TransferRecordEntity();
			record.setOrdertype(0);//ipos
			record.setData(content);
			record.setOrderid(order.getOrderid());
			record.setTid(order.getCpOrderId());
			record.setPaychannel(order.getPaychannel());
			record.setUrl(order.getTransfer_url());
			
			transferRecrodDao.save(record);
			return record.getId();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/** 
	 * 更新通知结果
	 * @param id
	 * @param str
	 */
	public void updateRecordResponse(int id, String str) {
		try{
			if(id > 0)
			{
				TransferRecordEntity record = transferRecrodDao.findUniqueBy(PropertyEntry.createEQ("id", id));
				if(record != null)
				{
					record.setResponse(str);
					
					Date date = new Date();       
					Timestamp time = new Timestamp(date.getTime());
					record.setLast_time(time);
					transferRecrodDao.updateEntity(record);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
