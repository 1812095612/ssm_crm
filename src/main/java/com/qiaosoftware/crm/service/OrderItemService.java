package com.qiaosoftware.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qiaosoftware.crm.mapper.OrderItemMapper;

@Service
public class OrderItemService {

	@Autowired
	private OrderItemMapper orderItemMapper;
	
}
