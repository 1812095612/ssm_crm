package com.qiaosoftware.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qiaosoftware.crm.entity.CustomerActivity;
import com.qiaosoftware.crm.mapper.CustomerActivityMapper;
import com.qiaosoftware.crm.model.Page;

@Service
public class CustomerActivityService {
	
	@Autowired
	private CustomerActivityMapper customerActivityMapper;

	@Transactional(readOnly=true)
	public Page<CustomerActivity> getPageList(String pageNoStr, Long customerId) {
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("customerId", customerId);
		
		int totalRecordNo = (int) customerActivityMapper.getTotalRecordNo(params);
		
		Page<CustomerActivity> page = new Page<>(pageNoStr, Page.PAGE_SIZE, totalRecordNo);
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		
		int fromIndex = (pageNo - 1) * pageSize + 1;
		int endIndex = fromIndex + pageSize;
		
		params.put("fromIndex", fromIndex);
		params.put("endIndex", endIndex);
		
		List<CustomerActivity> list = customerActivityMapper.getList(params);
		
		page.setList(list);
		
		return page;
	}

	@Transactional
	public void save(CustomerActivity customerActivity) {
		customerActivityMapper.save(customerActivity);
	}

	@Transactional(readOnly=true)
	public CustomerActivity get(Long id) {
		return customerActivityMapper.get(id);
	}
	
	@Transactional
	public void update(CustomerActivity customerActivity) {
		customerActivityMapper.update(customerActivity);
	}

	@Transactional
	public void delete(Long activityId) {
		customerActivityMapper.delete(activityId);
	}
	
}
