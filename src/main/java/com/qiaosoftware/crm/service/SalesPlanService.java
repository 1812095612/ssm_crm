package com.qiaosoftware.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qiaosoftware.crm.entity.SalesChance;
import com.qiaosoftware.crm.entity.SalesPlan;
import com.qiaosoftware.crm.entity.User;
import com.qiaosoftware.crm.mapper.SalesPlanMapper;
import com.qiaosoftware.crm.model.Page;

@Service
public class SalesPlanService {

	@Autowired
	private SalesPlanMapper salesPlanMapper;
	
	@Transactional(readOnly=true)
	public Page<SalesChance> getConditionPage(String pageNoStr, User loginUser,
			String custName, String title, String contact) {
		Map<String, Object> params = new HashMap<>();
		
		params.put("status", 1);
		params.put("loginUser", loginUser);
		
		if (custName.equals("null")) {
			custName = null;
		}else {
			custName = "%" + custName + "%";
		}
		
		if (title.equals("null")) {
			title = null;
		}else {
			title = "%" + title + "%";
		}
		
		if (contact.equals("null")) {
			contact = null;
		}else {
			contact = "%" + contact + "%";
		}
		
		params.put("custName", custName);
		params.put("title", title);
		params.put("contact", contact);
		
		int totalRecordNo = (int)salesPlanMapper.getConditionTotalRecordNo(params);
		
		Page<SalesChance> page = new Page<>(pageNoStr, Page.PAGE_SIZE, totalRecordNo);
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		
		int fromIndex = (pageNo - 1) * pageSize + 1;
		int endIndex = fromIndex + pageSize;
		
		params.put("fromIndex", fromIndex);
		params.put("endIndex", endIndex);
		
		List<SalesChance> list = salesPlanMapper.getConditionList(params);
		
		page.setList(list);
		
		return page;
	}

	@Transactional(readOnly=true)
	public SalesChance getChanceForPlan(Long id) {
		return salesPlanMapper.getChanceForPlan(id);
	}

	@Transactional(readOnly=true)
	public List<SalesPlan> getList(Long id) {
		return salesPlanMapper.getList(id);
	}

	@Transactional
	public Integer saveEntity(SalesPlan plan) {
		
		salesPlanMapper.saveEntity(plan);
		
		return plan.getId();
	}

	@Transactional
	public void update(Long id, String result) {
		salesPlanMapper.update(id, result);
	}

	@Transactional
	public void updateToDo(Long id, String todo) {
		salesPlanMapper.updateToDo(id, todo);
	}

	@Transactional
	public void remove(Long id) {
		salesPlanMapper.remove(id);
	}
	
}
