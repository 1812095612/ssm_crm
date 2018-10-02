package com.qiaosoftware.crm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qiaosoftware.crm.entity.CustomerDrain;
import com.qiaosoftware.crm.mapper.CustomerDrainMapper;
import com.qiaosoftware.crm.mapper.CustomerMapper;
import com.qiaosoftware.crm.model.Page;

@Service
public class CustomerDrainService {
	
	@Autowired
	private CustomerDrainMapper customerDrainMapper;
	
	@Autowired
	private CustomerMapper customerMapper;

	@Transactional(readOnly=true)
	public Page<CustomerDrain> getConditionPage(String pageNoStr, String customerName, String managerName) {
		
		if (customerName.equals("null")) {
			customerName = null;
		}else {
			customerName = "%" + customerName.trim() + "%";
		}
		
		if (managerName.equals("null")) {
			managerName = null;
		}else {
			managerName = "%" + managerName.trim() + "%";
		}
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("customerName", customerName);
		params.put("managerName", managerName);
		
		int totalRecordNo = (int) customerDrainMapper.getTotalRecordNo(params);
		
		Page<CustomerDrain> page = new Page<>(pageNoStr, Page.PAGE_SIZE, totalRecordNo);
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		
		int fromIndex = (pageNo - 1) * pageSize + 1;
		int endIndex = fromIndex + pageSize;
		
		params.put("fromIndex", fromIndex);
		params.put("endIndex", endIndex);
		
		List<CustomerDrain> list = customerDrainMapper.getDrainList(params);
		
		page.setList(list);
		
		return page;
	}
	
	/**
	 * 定时任务执行的方法
	 */
	@Transactional
	public void createCustomerDrain(){
		customerDrainMapper.callCustomerDrainCheckProcecure();
	}

	@Transactional(readOnly=true)
	public CustomerDrain get(Long drainId) {
		return customerDrainMapper.get(drainId);
	}

	@Transactional
	public CustomerDrain updateDelay(Long drainId, String delay) {
		
		CustomerDrain drain = customerDrainMapper.get(drainId);
		String delay2 = drain.getDelay();
		if (delay2 != null) {
			delay = delay2 + "`" + delay; 
		}
		customerDrainMapper.updateDelay(drainId, delay);
		
		return customerDrainMapper.get(drainId);
	}
	
	@Transactional
	public void updateConfirm(Long drainId, Long customerId, String reason) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("drainId", drainId);
		params.put("reason", reason);
		params.put("date", new Date());
		params.put("status", "流失");
		
		customerDrainMapper.updateConfirm(params);
		
		params.put("customerId", customerId);
		params.put("state", "流失");
		
		customerMapper.updateConfirm(params);
	}
	
}
