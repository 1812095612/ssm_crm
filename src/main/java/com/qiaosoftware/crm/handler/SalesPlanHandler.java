package com.qiaosoftware.crm.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qiaosoftware.crm.entity.SalesChance;
import com.qiaosoftware.crm.entity.SalesPlan;
import com.qiaosoftware.crm.entity.User;
import com.qiaosoftware.crm.model.Page;
import com.qiaosoftware.crm.service.SalesPlanService;

@RequestMapping("/plan")
@Controller
public class SalesPlanHandler {
	
	@Autowired
	private SalesPlanService salesPlanService;
	
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable("id") Long id, Map<String, Object> map){
		
		SalesChance chance = salesPlanService.getChanceForPlan(id);
		map.put("chance", chance);
		
		List<SalesPlan> salesPlans = salesPlanService.getList(id);
		map.put("salesPlans", salesPlans);
		
		return "plan/detail";
	}
	
	@ResponseBody
	@RequestMapping("/execute")
	public int saveExecution(@RequestParam("id") Long id,
							@RequestParam("result") String result){
		
		salesPlanService.update(id, result);
		
		return 1;
	}
	
	@RequestMapping(value="/execution/{id}",method=RequestMethod.GET)
	public String execu(@PathVariable("id") Long id, Map<String, Object> map){
		
		SalesChance chance = salesPlanService.getChanceForPlan(id);
		map.put("chance", chance);
		
		List<SalesPlan> salesPlans = salesPlanService.getList(id);
		map.put("salesPlans", salesPlans);
		
		return "plan/exec";
	}
	
	@ResponseBody
	@RequestMapping("/remove")
	public String remove(@RequestParam("id") Long id){
		
		salesPlanService.remove(id);
		
		return "1";
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public String update(
			@RequestParam("id") Long id, 
			@RequestParam("todo") String todo){
		
		salesPlanService.updateToDo(id, todo);
		
		return "1";
	}
	
	@ResponseBody
	@RequestMapping("/create")
	public int create(SalesPlan plan){
		
		Integer planId = salesPlanService.saveEntity(plan);
		
		return planId;
	}
	
	@RequestMapping(value="/make/{id}",method=RequestMethod.GET)
	public String make(@PathVariable("id") Long id,
						Map<String, Object> map){
		
		SalesChance chance = salesPlanService.getChanceForPlan(id);
		List<SalesPlan> salesPlans = salesPlanService.getList(id);
		
		map.put("salesPlans", salesPlans);
		
		map.put("chance", chance);
		
		return "plan/make";
	}
	
	@RequestMapping("/chance/list")
	public String showList(
			@RequestParam(value="search_LIKE_custName",required=false,defaultValue="null") String custName,
			  @RequestParam(value="search_LIKE_title",required=false,defaultValue="null") String title,
			  @RequestParam(value="search_LIKE_contact",required=false,defaultValue="null") String contact,
			  @RequestParam(value="pageNoStr",required=false) String pageNoStr,
			  HttpSession session,
			  Map<String, Object> map){
		
		User loginUser = (User) session.getAttribute("user");
		
		Page<SalesChance> page = salesPlanService.getConditionPage(pageNoStr, loginUser, custName, title, contact);
		
		map.put("page", page);
		
		return "plan/list";
	}
	
}
