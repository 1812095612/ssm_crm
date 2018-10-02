package com.qiaosoftware.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qiaosoftware.crm.entity.User;
import com.qiaosoftware.crm.mapper.UserMapper;
import com.qiaosoftware.crm.model.Page;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	@Transactional(readOnly=true)
	public User login(String name, String password) {
		
		User user = userMapper.getByName(name);
		
		if (user != null && user.getEnabled() == 1 && user.getPassword().equals(password)) {
			return user;
		}
		
		return null;
	}

	@Transactional(readOnly=true)
	public List<User> getAllUser() {
		return userMapper.getAllUser();
	}

	@Transactional(readOnly=true)
	public Page<User> getConditionPage(String pageNoStr, String name, String enabled) {
		
		if (name.equals("null")) {
			name = null;
		}else {
			name = "%" + name.trim() + "%";
		}
		
		if (enabled.equals("null")) {
			enabled = null;
		}else {
			enabled = "%" + enabled + "%";
		}
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("name", name);
		params.put("enabled", enabled);
		
		int totalRecordNo = (int) userMapper.getTotalRecordNo(params);
		
		Page<User> page = new Page<>(pageNoStr, Page.PAGE_SIZE, totalRecordNo);
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		
		int fromIndex = (pageNo - 1) * pageSize + 1;
		int endIndex = fromIndex + pageSize;
		
		params.put("fromIndex", fromIndex);
		params.put("endIndex", endIndex);
		
		List<User> list = userMapper.getUserList(params);
		
		page.setList(list);
		
		return page;
	}

	@Transactional
	public void save(User user) {
		userMapper.save(user);
	}

	@Transactional(readOnly=true)
	public User get(Long id) {
		return userMapper.get(id);
	}

	@Transactional
	public void update(User user) {
		userMapper.update(user);
	}

	@Transactional
	public void delete(Long id) {
		userMapper.delete(id);
	}

	@Transactional(readOnly=true)
	public boolean checkName(String name) {
		
		Integer count = userMapper.checkName(name);
		
		return count != 0;
	}
	
}
