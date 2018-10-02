package com.qiaosoftware.crm.handler;

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

@RequestMapping("/contact")
@Controller
public class ContactHandler {
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private CustomerService customerService;
	
	@ResponseBody
	@RequestMapping("/delete/{contactId}/{customerId}")
	public String delete(@PathVariable("contactId") Long contactId,
						@PathVariable("customerId") Long customerId){
		
		contactService.delete(contactId);
		
		return "1";
	}
	
	@RequestMapping("/saveOrUpdate/{id}")
	public String update(Contact contact, RedirectAttributes attributes){
		
		contactService.update(contact);
		
		attributes.addFlashAttribute("message", "修改成功！");
		
		return "redirect:/contact/list/" + contact.getCustomer().getId();
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id,
						Map<String, Object> map){
		
		Contact contact = contactService.get(id);
		map.put("contact", contact);
		
		return "contact/input";
	}
	
	@RequestMapping("/saveOrUpdate/")
	public String save(Contact contact, RedirectAttributes attributes){
		
		contactService.saveEntity(contact);
		
		attributes.addFlashAttribute("message", "保存成功！");
		
		return "redirect:/contact/list/" + contact.getCustomer().getId();
	}
	
	@RequestMapping(value="/create/{customerId}",method=RequestMethod.GET)
	public String create(@PathVariable("customerId") Integer customerId,
						Map<String, Object> map){
		
		Contact contact = new Contact();
		Customer customer = new Customer();
		customer.setId(customerId);
		contact.setCustomer(customer);
		
		map.put("contact", contact);
		
		return "contact/input";
	}
	
	@RequestMapping(value="/list/{customerId}",method=RequestMethod.GET)
	public String list(@PathVariable("customerId") Long customerId,
				@RequestParam(value="pageNoStr",required=false) String pageNoStr,
				Map<String, Object> map){
		
		Customer customer = customerService.get(customerId);
		
		Page<Contact> page = contactService.getPage(pageNoStr, customerId);
		
		map.put("customer", customer);
		map.put("page", page);
		map.put("pageListSize", page.getList().size());
		
		return "contact/list";
	}
	
}
