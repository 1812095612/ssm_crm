package com.qiaosoftware.crm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qiaosoftware.crm.entity.CustomerServ;
import com.qiaosoftware.crm.mapper.CustomerServMapper;
import com.qiaosoftware.crm.model.Page;
import com.qiaosoftware.crm.model.PropertyFilter;

@Service
public class CustomerServiceService {
	
	@Autowired
	private CustomerServMapper customerServMapper;

	@Transactional
	public void save(CustomerServ customerServ) {
		customerServMapper.save(customerServ);
	}

	@Transactional(readOnly=true)
	public Page<CustomerServ> getConditionPage(String pageNoStr, Map<String, Object> requestParams){
		
		Map<String, Object> params = parseRequestParams2MybatisParams(requestParams);
		
		params.put("serviceState", "新创建");
		
		int totalRecordNo = (int) customerServMapper.getTotalRecordNo(params);
		
		Page<CustomerServ> page = new Page<>(pageNoStr, Page.PAGE_SIZE, totalRecordNo);
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		
		int fromIndex = (pageNo - 1) * pageSize + 1;
		int endIndex = fromIndex + pageSize;
		
		params.put("fromIndex", fromIndex);
		params.put("endIndex", endIndex);
		
		List<CustomerServ> list = customerServMapper.getConditionList(params);
		
		page.setList(list);
		
		return page;
	}

	/**
	 * 辅助方法
	 * @param requestParams
	 * @return
	 */
	private Map<String, Object> parseRequestParams2MybatisParams(
			Map<String, Object> requestParams) {
		List<PropertyFilter> filters = PropertyFilter.parseRequestParamsToPropertyFilters(requestParams);
		
		Map<String, Object> params = new HashMap<>();
		
		for(PropertyFilter filter: filters){
			params.put(filter.getPropertyName(), filter.getPropertyVal());
		}
		
		return params;
	}

	@Transactional
	public void allotTo(Long id, Long allotId) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		params.put("allotId", allotId);
		params.put("allotDate", new Date());
		params.put("serviceState", "已分配");
		
		customerServMapper.allotTo(params);
	}

	@Transactional
	public void delete(Long id) {
		customerServMapper.delete(id);
	}

	@Transactional(readOnly=true)
	public Page<CustomerServ> getConditionDealPage(String pageNoStr,
			Map<String, Object> requestParams) {
		
		Map<String, Object> params = parseRequestParams2MybatisParams(requestParams);
		
		params.put("serviceState", "已分配");

		int totalRecordNo = (int) customerServMapper.getTotalRecordNo(params);
		
		Page<CustomerServ> page = new Page<>(pageNoStr, Page.PAGE_SIZE, totalRecordNo);
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		
		int fromIndex = (pageNo - 1) * pageSize + 1;
		int endIndex = fromIndex + pageSize;
		
		params.put("fromIndex", fromIndex);
		params.put("endIndex", endIndex);
		
		List<CustomerServ> list = customerServMapper.getConditionList(params);
		
		page.setList(list);
		
		return page;
	}

	@Transactional(readOnly=true)
	public CustomerServ getServById(Long id) {
		return customerServMapper.getServById(id);
	}

	@Transactional
	public void dealService(CustomerServ customerServ) {
		
		customerServ.setServiceState("已处理");
		
		customerServMapper.dealService(customerServ);
	}

	@Transactional(readOnly=true)
	public Page<CustomerServ> getConditionFeedbackPage(String pageNoStr,
			Map<String, Object> requestParams) {
		
		Map<String, Object> params = parseRequestParams2MybatisParams(requestParams);
		
		params.put("serviceState", "已处理");

		int totalRecordNo = (int) customerServMapper.getTotalRecordNo(params);
		
		Page<CustomerServ> page = new Page<>(pageNoStr, Page.PAGE_SIZE, totalRecordNo);
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		
		int fromIndex = (pageNo - 1) * pageSize + 1;
		int endIndex = fromIndex + pageSize;
		
		params.put("fromIndex", fromIndex);
		params.put("endIndex", endIndex);
		
		List<CustomerServ> list = customerServMapper.getConditionList(params);
		
		page.setList(list);
		
		return page;
	}

	@Transactional
	public void feedbackService(CustomerServ customerServ) {
		
		customerServ.setServiceState("已归档");
		
		customerServMapper.feedbackService(customerServ);
	}

	@Transactional(readOnly=true)
	public Page<CustomerServ> getConditionSrchivePage(String pageNoStr,
			Map<String, Object> requestParams) {
		
		Map<String, Object> params = parseRequestParams2MybatisParams(requestParams);
		
		params.put("serviceState", "已归档");

		int totalRecordNo = (int) customerServMapper.getTotalRecordNo(params);
		
		Page<CustomerServ> page = new Page<>(pageNoStr, Page.PAGE_SIZE, totalRecordNo);
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		
		int fromIndex = (pageNo - 1) * pageSize + 1;
		int endIndex = fromIndex + pageSize;
		
		params.put("fromIndex", fromIndex);
		params.put("endIndex", endIndex);
		
		List<CustomerServ> list = customerServMapper.getConditionList(params);
		
		page.setList(list);
		
		return page;
	}
	
}
