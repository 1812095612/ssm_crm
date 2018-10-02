package com.qiaosoftware.crm.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.qiaosoftware.crm.entity.CustomerDrain;
import com.qiaosoftware.crm.model.Page;
import com.qiaosoftware.crm.service.CustomerDrainService;


@RequestMapping("/drain")
@Controller
public class CustomerDrainHandler {

	@Autowired
	private CustomerDrainService customerDrainService;
	
	
	@RequestMapping(value="/confirm/{drainId}/{customerId}",method=RequestMethod.PUT)
	public String Confirm(@PathVariable("drainId") Long drainId,
			@PathVariable("customerId") Long customerId,
			@RequestParam(value="reason",required=false) String reason){
		
		customerDrainService.updateConfirm(drainId, customerId, reason);
			
		return "redirect:/drain/list";
	}
	
	@RequestMapping(value="/confirm/{drainId}/{customerId}",method=RequestMethod.GET)
	public String toConfirm(@PathVariable("drainId") Long drainId,
						  @PathVariable("customerId") Long customerId,
						  Map<String, Object> map){
		
		CustomerDrain drain = customerDrainService.get(drainId);
		map.put("drain", drain);
		
		return "drain/confirm";
	}
	
	@RequestMapping(value="/delay/{drainId}",method=RequestMethod.PUT)
	public String delay(@PathVariable("drainId") Long drainId,
			@RequestParam(value="delay",required=false) String delay,
			Map<String, Object> map){
		
		CustomerDrain drain = customerDrainService.updateDelay(drainId, delay);
		
		map.put("drain", drain);
		
		return "drain/delay";
	}
	
	@RequestMapping(value="/delay/{drainId}",method=RequestMethod.GET)
	public String toDelay(@PathVariable("drainId") Long drainId,
						Map<String, Object> map){
		
		CustomerDrain drain = customerDrainService.get(drainId);
		map.put("drain", drain);
		
		return "drain/delay";
	}
	
	@RequestMapping("/list")
	public String list(
			@RequestParam(value="pageNoStr",required=false) String pageNoStr,
			@RequestParam(value="search_LIKE_customerName",required=false,defaultValue="null") String customerName,
			@RequestParam(value="search_LIKE_managerName",required=false,defaultValue="null") String managerName,
			Map<String, Object> map){
		
		Page<CustomerDrain> page = customerDrainService.getConditionPage(pageNoStr, customerName, managerName);
		map.put("page", page);
		
		return "drain/list";
	}
	
}
