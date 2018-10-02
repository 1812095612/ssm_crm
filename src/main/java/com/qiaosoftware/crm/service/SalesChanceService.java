package com.qiaosoftware.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qiaosoftware.crm.entity.Contact;
import com.qiaosoftware.crm.entity.Customer;
import com.qiaosoftware.crm.entity.SalesChance;
import com.qiaosoftware.crm.entity.User;
import com.qiaosoftware.crm.mapper.ContactMapper;
import com.qiaosoftware.crm.mapper.CustomerMapper;
import com.qiaosoftware.crm.mapper.SalesChanceMapper;
import com.qiaosoftware.crm.model.Page;

@Service
public class SalesChanceService {
	
	@Autowired
	private SalesChanceMapper salesChanceMapper;
	
	@Autowired
	private CustomerMapper customerMapper;
	
	@Autowired
	private ContactMapper contactMapper;
	
	@Transactional
	public void save(SalesChance chance) {
		chance.setStatus(1);
		salesChanceMapper.save(chance);
	}
	
	@Transactional(readOnly=true)
	public Page<SalesChance> getPage(String pageNoStr, User createBy) {
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("status", 1);
		params.put("createBy", createBy);
		
		int totalRecordNo = (int) salesChanceMapper.getTotalRecordNo(params);
		
		Page<SalesChance> page = new Page<>(pageNoStr, Page.PAGE_SIZE, totalRecordNo);
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		
		int fromIndex = (pageNo - 1) * pageSize + 1;
		int endIndex = fromIndex + pageSize;
		
		params.put("fromIndex", fromIndex);
		params.put("endIndex", endIndex);
		
		List<SalesChance> list = salesChanceMapper.getList(params);
		
		page.setList(list);
		
		return page;
	}
	
	@Transactional
	public void delete(long id) {
		salesChanceMapper.delete(id);
	}
	
	@Transactional(readOnly=true)
	public SalesChance get(long id) {
		return salesChanceMapper.get(id);
	}
	
	@Transactional
	public void update(SalesChance salesChance) {
		salesChanceMapper.update(salesChance);
	}
	
	@Transactional(readOnly=true)
	public Page<SalesChance> getConditionPage(String pageNoStr, User createBy,
			String custName, String title, String contact) {
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("status", 1);
		params.put("createBy", createBy);
		
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
		
		int totalRecordNo = (int)salesChanceMapper.getConditionTotalRecordNo(params);
		
		Page<SalesChance> page = new Page<>(pageNoStr, Page.PAGE_SIZE, totalRecordNo);
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		
		int fromIndex = (pageNo - 1) * pageSize + 1;
		int endIndex = fromIndex + pageSize;
		
		params.put("fromIndex", fromIndex);
		params.put("endIndex", endIndex);
		
		List<SalesChance> list = salesChanceMapper.getConditionList(params);
		
		page.setList(list);
		
		return page;
	}
	
	@Transactional
	public void updateChance(SalesChance chance) {
		
		chance.setStatus(2);
		salesChanceMapper.updateChance(chance);
	}

	@Transactional
	public void updateStatus(SalesChance chance) {
		chance.setStatus(3);
		salesChanceMapper.updateStatus(chance);
		
		Customer customer = new Customer();
		customer.setName(chance.getCustName());
		customer.setNo(UUID.randomUUID().toString());
		customer.setState("正常");
		customerMapper.save(customer);
		
		Contact contact = new Contact();
		contact.setName(chance.getContact());
		contact.setTel(chance.getContactTel());
		contact.setCustomer(customer);
		
		contactMapper.save(contact);
	}
	
	@Transactional
	public void updateChanceFail(SalesChance chance) {
		chance.setStatus(4);
		salesChanceMapper.updateStatus(chance);
	}

}
