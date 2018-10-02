package com.qiaosoftware.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qiaosoftware.crm.entity.Order;
import com.qiaosoftware.crm.mapper.OrderMapper;
import com.qiaosoftware.crm.model.Page;

@Service
public class OrderService {

	@Autowired
	private OrderMapper orderMapper;

	@Transactional(readOnly=true)
	public Page<Order> getPage(String pageNoStr, Long customerId) {
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("customerId", customerId);
		
		int totalRecordNo = (int) orderMapper.getTotalRecordNo(params);
		
		Page<Order> page = new Page<>(pageNoStr, Page.PAGE_SIZE, totalRecordNo);
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		
		int fromIndex = (pageNo - 1) * pageSize + 1;
		int endIndex = fromIndex + pageSize;
		
		params.put("fromIndex", fromIndex);
		params.put("endIndex", endIndex);
		
		List<Order> list = orderMapper.getList(params);
		
		page.setList(list);
		
		return page;
	}

	@Transactional(readOnly=true)
	public Order get(Long id) {
		return orderMapper.get(id);
	}
	
}
