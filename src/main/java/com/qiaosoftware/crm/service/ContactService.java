package com.qiaosoftware.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qiaosoftware.crm.entity.Contact;
import com.qiaosoftware.crm.mapper.ContactMapper;
import com.qiaosoftware.crm.model.Page;

@Service
public class ContactService {

	@Autowired
	private ContactMapper contactMapper;
	
	@Transactional(readOnly=true)
	public Page<Contact> getPage(String pageNoStr, Long customerId) {
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("customerId", customerId);
		
		int totalRecordNo = (int) contactMapper.getTotalRecordNo(params);
		
		Page<Contact> page = new Page<>(pageNoStr, Page.PAGE_SIZE, totalRecordNo);
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		
		int fromIndex = (pageNo - 1) * pageSize + 1;
		int endIndex = fromIndex + pageSize;
		
		params.put("fromIndex", fromIndex);
		params.put("endIndex", endIndex);
		
		List<Contact> list = contactMapper.getList(params);
		
		page.setList(list);
		
		return page;
	}

	@Transactional
	public void saveEntity(Contact contact) {
		contactMapper.saveEntity(contact);
	}

	@Transactional(readOnly=true)
	public Contact get(Long id) {
		return contactMapper.get(id);
	}
	
	@Transactional
	public void update(Contact contact) {
		contactMapper.update(contact);
	}

	@Transactional
	public void delete(Long contactId) {
		contactMapper.delete(contactId);
	}
	
	@Transactional(readOnly=true)
	public List<Contact> getManagers(Long id) {
		return contactMapper.getManagers(id);
	}
	
}
