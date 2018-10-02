package com.qiaosoftware.crm.handler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.qiaosoftware.crm.entity.CustomerDrain;
import com.qiaosoftware.crm.model.Page;
import com.qiaosoftware.crm.service.CustomerDrainService;
import com.qiaosoftware.crm.service.ReportService;

@RequestMapping("/report")
@Controller
public class ReportHandler {

	@Autowired
	private ReportService reportService;
	
	@Autowired
	private CustomerDrainService customerDrainService;
	
	@RequestMapping("/testJfreeChart")
	public String testJfreeChart2(Map<String, Object> map){
		
		List<Map<String, Object>> data = reportService.getDataForChart();
		
		for (int i = 0; i < data.size(); i++) {
			Map<String, Object> dataMap = data.get(i);
			String customerName = (String) dataMap.get("customerName");
			BigDecimal totalMoney = (BigDecimal) dataMap.get("totalMoney");
			map.put(customerName, totalMoney.intValue());
		}
		
		return "helloworld";
	}
	
	@RequestMapping("/drain")
	public String drainList(
			@RequestParam(value="pageNoStr",required=false) String pageNoStr,
			@RequestParam(value="search_LIKE_customerName",required=false,defaultValue="null") String customerName,
			@RequestParam(value="search_LIKE_managerName",required=false,defaultValue="null") String managerName,
			Map<String, Object> map){
		
		Page<CustomerDrain> page = customerDrainService.getConditionPage(pageNoStr, customerName, managerName);
		map.put("page", page);
		
		return "report/drain";
	}
	
	@RequestMapping("/service")
	public String serviceList(
					@RequestParam(value="pageNoStr",required=false) String pageNoStr,
					Map<String, Object> map,
					HttpServletRequest request){
		
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		
		Page<Map<String, Object>> page = reportService.getServicePage(pageNoStr, params);
		map.put("page", page);
		
		return "report/service";
	}
	
	@RequestMapping("/consist")
	public String consistList(
					@RequestParam(value="pageNoStr",required=false) String pageNoStr,
					@RequestParam(value="search_type",required=false,defaultValue="customer_level") String type,
					Map<String, Object> map){
		
		Page<Map<String, Object>> page = reportService.getConsistPage(pageNoStr, type);
		
		String condition = null;
		
		switch (type) {
		
			case "customer_level":
				condition = "客户等级";
				break;
				
			case "credit":
				condition = "客户信用度";
				break;
				
			case "satify":
				condition = "客户满意度";
				break;
			}
		
		map.put("page", page);
		map.put("condition", condition);
		
		return "report/consist";
	}
	
	@RequestMapping("/pay")
	public String payList(
					@RequestParam(value="pageNoStr",required=false) String pageNoStr,
					Map<String, Object> map,
					HttpServletRequest request){
				
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
//		String queryString = parseRequestParams2QueryString(params);
//		map.put("queryString", queryString);
		
		Page<Map<String, Object>> page = reportService.getCustomerTotalOrderMoneyPage(pageNoStr, params);
		map.put("page", page);
		
		return "report/pay";
	}
	
	/*
	private String parseRequestParams2QueryString(Map<String, Object> params) {
		StringBuilder queryString = new StringBuilder();
		
		if(params == null || params.size() == 0){
			return "";
		}
		
		for(Map.Entry<String, Object> entry: params.entrySet()){
			if(entry.getValue() == null || entry.getValue().toString().trim().equals("")){
				continue;
			}
			
			queryString.append("search_")
			           .append(entry.getKey())
			           .append("=")
			           .append(entry.getValue())
			           .append("&");
		}
		
		if(queryString.length() > 0){
			queryString.replace(queryString.length() - 1, queryString.length(), "");
		}
		return queryString.toString();
	}
	*/
}
