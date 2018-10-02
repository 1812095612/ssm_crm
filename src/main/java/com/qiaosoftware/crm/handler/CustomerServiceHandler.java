package com.qiaosoftware.crm.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.qiaosoftware.crm.entity.Customer;
import com.qiaosoftware.crm.entity.CustomerServ;
import com.qiaosoftware.crm.entity.User;
import com.qiaosoftware.crm.model.Page;
import com.qiaosoftware.crm.service.CustomerService;
import com.qiaosoftware.crm.service.CustomerServiceService;
import com.qiaosoftware.crm.service.UserService;

@RequestMapping("/service")
@Controller
public class CustomerServiceHandler {

	@Autowired
	private CustomerServiceService customerServiceService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value="/archive/{id}",method=RequestMethod.GET)
	public String toArchive(@PathVariable("id") Long id,
			Map<String, Object> map){
		
		CustomerServ service = customerServiceService.getServById(id);
		map.put("service", service);
		
		return "service/archive/archive";
	}
	
	@RequestMapping("/archive/list")
	public String archiveList(
			Map<String, Object> map,
			@RequestParam(value="pageNoStr",required=false) String pageNoStr,
			HttpSession session,
			HttpServletRequest request){
	
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		
		Page<CustomerServ> page = customerServiceService.getConditionSrchivePage(pageNoStr, params);
		map.put("page", page);
		
		return "service/archive/list";
	}
	
	@RequestMapping(value="/feedback/{id}",method=RequestMethod.PUT)
	public String feedback(CustomerServ customerServ, RedirectAttributes attributes){
		
		customerServiceService.feedbackService(customerServ);
		
		attributes.addFlashAttribute("message", "保存成功！");
			
		return "redirect:/service/feedback/list";
	}
	
	@RequestMapping(value="/feedback/{id}",method=RequestMethod.GET)
	public String toFeedback(
			@PathVariable("id") Long id,
			Map<String, Object> map){
		
		CustomerServ service = customerServiceService.getServById(id);
		map.put("service", service);
		
		return "service/feedback/feedback";
	}
	
	@RequestMapping("/feedback/list")
	public String feedbackList(
			Map<String, Object> map,
			@RequestParam(value="pageNoStr",required=false) String pageNoStr,
			HttpSession session,
			HttpServletRequest request){
	
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		
		Page<CustomerServ> page = customerServiceService.getConditionFeedbackPage(pageNoStr, params);
		map.put("page", page);
		
		return "service/feedback/list";
	}
	
	@RequestMapping(value="/deal/{id}",method=RequestMethod.PUT)
	public String deal(CustomerServ customerServ, RedirectAttributes attributes){
		
		customerServiceService.dealService(customerServ);
		
		attributes.addFlashAttribute("message", "保存成功！");
		
		return "redirect:/service/deal/list";
	}
	
	@RequestMapping(value="/deal/{id}",method=RequestMethod.GET)
	public String toDeal(
				@PathVariable("id") Long id,
				Map<String, Object> map){
		
		CustomerServ service = customerServiceService.getServById(id);
		service.setDealDate(new Date());
		map.put("service", service);
		
		return "service/deal/deal";
	}
	
	@RequestMapping("/deal/list")
	public String dealList(
				Map<String, Object> map,
				@RequestParam(value="pageNoStr",required=false) String pageNoStr,
				HttpSession session,
				HttpServletRequest request){
		
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		
		Page<CustomerServ> page = customerServiceService.getConditionDealPage(pageNoStr, params);
		map.put("page", page);
		
		return "service/deal/list";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id,
						RedirectAttributes attributes){
		
		customerServiceService.delete(id);
		
		attributes.addFlashAttribute("message", "删除成功！");
		
		return "redirect:/service/allot/list";
	}
	
	@ResponseBody
	@RequestMapping("/allot/allTo")
	public String allotTo(
				@RequestParam("id") Long id,
				@RequestParam("allotId") Long allotId
				){
		
		customerServiceService.allotTo(id, allotId);
		
		return "1";
	}
	
	@RequestMapping("/allot/list")
	public String allotList(
			Map<String, Object> map,
			@RequestParam(value="pageNoStr",required=false) String pageNoStr,
			HttpSession session,
			HttpServletRequest request){
		
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		
		Page<CustomerServ> page = customerServiceService.getConditionPage(pageNoStr, params);
		map.put("page", page);
		
		List<User> users = userService.getAllUser();
		map.put("users", users);
		
		return "service/allot/list";
	}
	
	@RequestMapping("/save")
	public String save(CustomerServ customerServ, HttpSession session, RedirectAttributes attributes){
		
		User createdby = (User) session.getAttribute("user");
		customerServ.setCreatedby(createdby);
		
		customerServiceService.save(customerServ);
		
		attributes.addFlashAttribute("message", "保存成功！");
		
		return "redirect:/service/allot/list";
	}
	
	@RequestMapping("/create")
	public String create(Map<String, Object> map){
		
		List<String> serviceTypes = new ArrayList<>();
		serviceTypes.add("咨询");
		serviceTypes.add("投诉");
		serviceTypes.add("建议");
		map.put("serviceTypes", serviceTypes);
		
		List<Customer> customers = customerService.getAllCustomers();
		map.put("customers", customers);
		
		CustomerServ customerServ = new CustomerServ();
		customerServ.setCreateDate(new Date());
		map.put("customerService", customerServ);
		
		return "service/input";
	}
	
}
