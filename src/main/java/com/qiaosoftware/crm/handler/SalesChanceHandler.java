package com.qiaosoftware.crm.handler;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qiaosoftware.crm.entity.SalesChance;
import com.qiaosoftware.crm.entity.User;
import com.qiaosoftware.crm.model.Page;
import com.qiaosoftware.crm.service.SalesChanceService;
import com.qiaosoftware.crm.service.UserService;

@RequestMapping("/chance")
@Controller
public class SalesChanceHandler {
	
	@Autowired
	private SalesChanceService salesChanceService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/stop/{id}")
	public String stop(@PathVariable("id") Long id, RedirectAttributes attributes){
		
		SalesChance chance = salesChanceService.get(id);
		
		salesChanceService.updateChanceFail(chance);
		
		attributes.addFlashAttribute("message", "操作成功！");
		
		return "redirect:/plan/chance/list";
	}
	
	@RequestMapping("/finish/{id}")
	public String finish(@PathVariable("id") Long id, RedirectAttributes attributes){
		
		SalesChance chance = salesChanceService.get(id);
		
		salesChanceService.updateStatus(chance);
		
		attributes.addFlashAttribute("message", "操作成功！");
		
		return "redirect:/plan/chance/list";
	}
	
	@RequestMapping(value="/dispatch/{id}",method=RequestMethod.PUT)
	public String dispatch(SalesChance chance, RedirectAttributes attributes){
		
		salesChanceService.updateChance(chance);
		
		attributes.addFlashAttribute("message", "操作成功！");
		
		return "redirect:/chance/list";
	}
	
	@RequestMapping(value="/dispatch/{id}",method=RequestMethod.GET)
	public String dispatch(@PathVariable("id") Long id,
							Map<String, Object> map){
		
		SalesChance chance = salesChanceService.get(id);
		chance.setDesigneeDate(new Date());
		map.put("chance", chance);
		
		List<User> users = userService.getAllUser();
		map.put("users", users);
		
		return "chance/dispatch";
	}
	
	@RequestMapping("/list")
	public String showConditionList(
				  @RequestParam(value="search_LIKE_custName",required=false,defaultValue="null") String custName,
				  @RequestParam(value="search_LIKE_title",required=false,defaultValue="null") String title,
				  @RequestParam(value="search_LIKE_contact",required=false,defaultValue="null") String contact,
				  @RequestParam(value="pageNoStr",required=false) String pageNoStr,
				  HttpSession session,
				  Map<String, Object> map){
		
		User createBy = (User) session.getAttribute("user");
		
		Page<SalesChance> page = salesChanceService.getConditionPage(pageNoStr, createBy, custName, title, contact);
		
		map.put("page", page);
		
		return "chance/list";
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public String update(SalesChance salesChance,RedirectAttributes attributes){
		
		salesChanceService.update(salesChance);
		
		attributes.addFlashAttribute("message", "操作成功！");
		
		return "redirect:/chance/list";
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String input(@PathVariable("id") Long id, Map<String, Object> map){
		
		SalesChance chance = salesChanceService.get(id);
		map.put("chance", chance);
		
		return "chance/input";
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id,
						RedirectAttributes attributes){
		
		salesChanceService.delete(id);
		attributes.addFlashAttribute("message", "操作成功！");
		
		return "redirect:/chance/list";
	}
	
	@RequestMapping(value="/",method=RequestMethod.POST)
	public String save(SalesChance chance,
						HttpSession session,
						RedirectAttributes attributes){
		
		User createBy = (User) session.getAttribute("user");
		chance.setCreateBy(createBy);
		
		salesChanceService.save(chance);
		
		attributes.addFlashAttribute("message", "操作成功！");
		
		return "redirect:/chance/list";
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String input(Map<String, Object> map){
		
		SalesChance chance = new SalesChance();
		chance.setCreateDate(new Date());
		
		map.put("chance", chance);
		
		return "chance/input";
	}
	
	/*
	@RequestMapping("/list")
	public String showList(
			@RequestParam(value="search_LIKE_custName",required=false,defaultValue="null") String custName,
			  @RequestParam(value="search_LIKE_title",required=false,defaultValue="null") String title,
			  @RequestParam(value="search_LIKE_contact",required=false,defaultValue="null") String contact,
			  
					@RequestParam(value="pageNoStr",required=false) String pageNoStr,
					HttpSession session,
					Map<String, Object> map){
		
		/*User createBy = (User) session.getAttribute("user");
		
		Page<SalesChance> page = salesChanceService.getPage(pageNoStr, createBy);
		
		map.put("page", page);
		
		return "chance/list";
		
		return showConditionList(custName, title, contact, pageNoStr, session, map);
	}
	 */
	
}
