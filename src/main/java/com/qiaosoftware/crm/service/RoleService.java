package com.qiaosoftware.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qiaosoftware.crm.entity.Authority;
import com.qiaosoftware.crm.entity.Role;
import com.qiaosoftware.crm.mapper.RoleMapper;
import com.qiaosoftware.crm.model.Page;

@Service
public class RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Transactional(readOnly=true)
	public List<Authority> getParentAuthorities(){
		return roleMapper.getParentAuthorities();
	}
	
	@Transactional(readOnly=true)
	public Role getById(Long id){
		return roleMapper.getById(id);
	}

	@Transactional(readOnly=true)
	public List<Role> getRoles() {
		return roleMapper.getRoles();
	}

	@Transactional(readOnly=true)
	public Page<Role> getConditionPage(String pageNoStr) {
		
		Map<String, Object> params = new HashMap<>();
		
		int totalRecordNo = (int) roleMapper.getTotalRecordNo();
		
		Page<Role> page = new Page<>(pageNoStr, Page.PAGE_SIZE, totalRecordNo);
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		
		int fromIndex = (pageNo - 1) * pageSize + 1;
		int endIndex = fromIndex + pageSize;
		
		params.put("fromIndex", fromIndex);
		params.put("endIndex", endIndex);
		
		List<Role> list = roleMapper.getRoleList(params);
		
		page.setList(list);
		
		return page;
	}

	@Transactional(readOnly=true)
	public boolean checkName(String name) {
		
		Integer count = roleMapper.checkName(name);
		
		return count != 0;
	}

	@Transactional
	public void save(Role role) {
		roleMapper.save(role);
	}

	@Transactional
	public void delete(Long id) {
		roleMapper.delete(id);
	}

	@Transactional(readOnly=true)
	public Role get(Long id) {
		return roleMapper.get(id);
	}
	
}
