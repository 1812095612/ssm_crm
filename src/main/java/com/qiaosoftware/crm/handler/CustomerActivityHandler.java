package com.qiaosoftware.crm.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qiaosoftware.crm.entity.Customer;
import com.qiaosoftware.crm.entity.CustomerActivity;
import com.qiaosoftware.crm.model.Page;
import com.qiaosoftware.crm.service.CustomerActivityService;
import com.qiaosoftware.crm.service.CustomerService;

@RequestMapping("/activity")
@Controller
public class CustomerActivityHandler {

	@Autowired
	private CustomerActivityService customerActivityService;
	
	@Autowired
	private CustomerService customerService;
	
	
	@RequestMapping("/delete/{activityId}/{customerId}")
	public String delete(@PathVariable("activityId") Long activityId,
						@PathVariable("customerId") Long customerId,
						RedirectAttributes attributes){
		
		customerActivityService.delete(activityId);
		
		attributes.addFlashAttribute("message", "删除成功！");
		
		return "redirect:/activity/list/" + customerId;
	}
	
	@RequestMapping("/saveOrUpdate/{id}")
	public String update(CustomerActivity customerActivity, RedirectAttributes attributes){
		
		customerActivityService.update(customerActivity);
		
		attributes.addFlashAttribute("message", "修改成功！");
		
		return "redirect:/activity/list/" + customerActivity.getCustomer().getId();
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Map<String, Object> map){
		
		CustomerActivity activity = customerActivityService.get(id);
		map.put("activity", activity);
		
		return "activity/input";
	}
	
	@RequestMapping("/saveOrUpdate/")
	public String save(CustomerActivity customerActivity, RedirectAttributes attributes){
		
		customerActivityService.save(customerActivity);
		
		attributes.addFlashAttribute("message", "保存成功！");
		
		return "redirect:/activity/list/" + customerActivity.getCustomer().getId();
	}
	
	@RequestMapping("/create/{customerId}")
	public String create(@PathVariable("customerId") Integer customerId, Map<String, Object> map){
		
		CustomerActivity customerActivity = new CustomerActivity();
		
		Customer customer = new Customer();
		customer.setId(customerId);
		
		customerActivity.setCustomer(customer);
		
		map.put("activity", customerActivity);
		
		return "activity/input";
	}
	
	@RequestMapping("/list/{customerId}")
	public String list(@PathVariable("customerId") Long customerId,
						@RequestParam(value="pageNoStr",required=false) String pageNoStr,
						Map<String, Object> map){
		
		Customer customer = customerService.get(customerId);
		
		Page<CustomerActivity> page = customerActivityService.getPageList(pageNoStr,customerId);
		
		map.put("customer", customer);
		map.put("page", page);
		
		return "activity/list";
	}
	
}
