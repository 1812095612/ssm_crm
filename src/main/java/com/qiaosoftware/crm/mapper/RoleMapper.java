package com.qiaosoftware.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qiaosoftware.crm.entity.Authority;
import com.qiaosoftware.crm.entity.Role;

public interface RoleMapper {

	List<Role> getRoles();

	long getTotalRecordNo();

	List<Role> getRoleList(Map<String, Object> params);

	Integer checkName(@Param("name") String name);

	void save(Role role);

	void delete(@Param("id") Long id);

	Role get(@Param("id") Long id);

	List<Authority> getParentAuthorities();
	
	Role getById(@Param("id") Long id);
	
}
