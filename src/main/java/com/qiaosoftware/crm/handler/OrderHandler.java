package com.qiaosoftware.crm.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qiaosoftware.crm.entity.Order;
import com.qiaosoftware.crm.model.Page;
import com.qiaosoftware.crm.service.OrderItemService;
import com.qiaosoftware.crm.service.OrderService;

@RequestMapping("/order")
@Controller
public class OrderHandler {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@RequestMapping("/details/{orderId}")
	public String details(
						@PathVariable("orderId") Long orderId,
						Map<String, Object> map){
		
		Order order = orderService.get(orderId);
		map.put("order", order);
		
		return "order/details";
	}
	
	@RequestMapping("/list/{customerId}")
	public String list(@PathVariable("customerId") Long customerId,
					  Map<String, Object> map,
					  @RequestParam(value="pageNoStr",required=false) String pageNoStr){
		
		Page<Order> page = orderService.getPage(pageNoStr, customerId);
		
		map.put("page", page);
		map.put("customerId", customerId);
		
		return "order/list";
	}
	
}
