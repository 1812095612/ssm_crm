package com.qiaosoftware.crm.handler;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qiaosoftware.crm.entity.Contact;
import com.qiaosoftware.crm.entity.Customer;
import com.qiaosoftware.crm.model.Page;
import com.qiaosoftware.crm.service.ContactService;
import com.qiaosoftware.crm.service.CustomerService;
import com.qiaosoftware.crm.service.DictService;

@RequestMapping("/customer")
@Controller
public class CustomerHandler {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private DictService dictService;
	
	@Autowired
	private ContactService contactService;
	
	@ResponseBody
	@RequestMapping("/delete")
	public String delete(@RequestParam("id") Long id){
		
		customerService.update(id);
		
		return "1";
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public String update(Customer customer, RedirectAttributes attributes){
		
		customerService.updateCustomer(customer);
		
		attributes.addFlashAttribute("message", "修改成功！");
		
		return "redirect:/customer/list";
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String edit(@PathVariable("id") Long id,
						Map<String, Object> map){
		
		List<String> regions = dictService.getRegions();
		map.put("regions", regions);
		
		List<String> levels = dictService.getLevels();
		map.put("levels", levels);
		
		List<String> satisfies = dictService.getSatisfies();
		map.put("satisfies", satisfies);
		
		List<String> credits = dictService.getCredits();
		map.put("credits", credits);
		
		Customer customer = customerService.get(id);
		map.put("customer", customer);
		
		List<Contact> managers = contactService.getManagers(id);
		map.put("managers", managers);
		
		return "customer/input";
	}
	
	@RequestMapping("/list")
	public String list(Map<String, Object> map,
		@RequestParam(value="pageNoStr",required=false) String pageNoStr,
		@RequestParam(value="search_LIKE_name",required=false,defaultValue="null") String name,
		@RequestParam(value="search_EQ_region",required=false,defaultValue="null") String region,
		@RequestParam(value="search_LIKE_managerName",required=false,defaultValue="null") String managerName,
		@RequestParam(value="search_EQ_level",required=false,defaultValue="null") String level,
		@RequestParam(value="search_EQ_state",required=false,defaultValue="null") String state){
		
		List<String> regions = dictService.getRegions();
		map.put("regions", regions);
		
		List<String> levels = dictService.getLevels();
		map.put("levels", levels);
		
		Page<Customer> page = customerService.getConditionPage(pageNoStr,name,region,managerName,level,state);
		map.put("page", page);
		
		return "customer/list";
	}
	
}
