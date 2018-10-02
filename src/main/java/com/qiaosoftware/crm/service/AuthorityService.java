package com.qiaosoftware.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qiaosoftware.crm.entity.Authority;
import com.qiaosoftware.crm.mapper.AuthorityMapper;

@Service
public class AuthorityService {

	@Autowired
	private AuthorityMapper authorityMapper;

	@Transactional(readOnly=true)
	public List<Authority> getAuthorityList() {
		
		List<Authority> authorityList = authorityMapper.getAuthorityList();
		
		for (Authority authority : authorityList) {
			
			List<Authority> subAuthorities = authorityMapper.getSubList(authority.getId());
			
			authority.setSubAuthorities(subAuthorities);
		}
		
		return authorityList;
	}

	@Transactional
	public void update(Long id, List<Long> authList) {
		
		authorityMapper.deleteOldRelationship(id);
		
		if (authList != null && authList.size() > 0) {
			
			for (int i = 0; i < authList.size(); i++) {
				authorityMapper.update(id, authList.get(i));
			}
			
		}
		
	}

	@Transactional(readOnly=true)
	public List<String> getRoleAuthorityStringList(Long id) {
		
		return authorityMapper.getRoleAuthorityStringList(id);
	}
	
}
