package com.qiaosoftware.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qiaosoftware.crm.entity.Customer;
import com.qiaosoftware.crm.mapper.ContactMapper;
import com.qiaosoftware.crm.mapper.CustomerMapper;
import com.qiaosoftware.crm.model.Page;

@Service
public class CustomerService {

	@Autowired
	private CustomerMapper customerMapper;
	
	@Autowired
	private ContactMapper contactMapper;

	@Transactional(readOnly=true)
	public Page<Customer> getConditionPage(String pageNoStr, String name, String region, String managerName, String level, String state) {
		
		List<Long> managerIdList = null;
		
		if (name.equals("null")) {
			name = null;
		}else {
			name = "%" + name + "%";
		}
		
		if (region.equals("null")) {
			region = null;
		}else {
			region = "%" + region + "%";
		}
		
		if (level.equals("null")) {
			level = null;
		}else {
			level = "%" + level + "%";
		}
		
		if (state.equals("null")) {
			state = null;
		}else {
			state = "%" + state + "%";
		}
		
		if (!managerName.equals("null")) {
			
			managerIdList = contactMapper.getManagerIdList(managerName);
		}
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("name", name);
		params.put("region", region);
		params.put("level", level);
		params.put("state", state);
		params.put("managerIdList", managerIdList);
		
		int totalRecordNo = (int)customerMapper.getConditionTotalRecordNo(params);
		
		Page<Customer> page = new Page<>(pageNoStr, Page.PAGE_SIZE, totalRecordNo);
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		
		int fromIndex = (pageNo - 1) * pageSize + 1;
		int endIndex = fromIndex + pageSize;
		
		params.put("fromIndex", fromIndex);
		params.put("endIndex", endIndex);
		
		List<Customer> list = customerMapper.getConditionList(params);
		if (list != null && list.size() > 0) {
			page.setList(list);
		}
		
		return page;
	}

	@Transactional(readOnly=true)
	public Customer get(Long id) {
		return customerMapper.get(id);
	}

	@Transactional
	public void update(Long id) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		params.put("state", "删除");
		
		customerMapper.update(params);
	}

	@Transactional
	public void updateCustomer(Customer customer) {
		customerMapper.updateCustomer(customer);
	}

	@Transactional(readOnly=true)
	public List<Customer> getAllCustomers() {
		return customerMapper.getAllCustomers();
	}
	
}
