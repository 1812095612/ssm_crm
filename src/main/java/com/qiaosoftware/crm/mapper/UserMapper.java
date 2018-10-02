package com.qiaosoftware.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qiaosoftware.crm.entity.User;

public interface UserMapper {

	User getByName(@Param("name") String name);

	List<User> getAllUser();

	long getTotalRecordNo(Map<String, Object> params);

	List<User> getUserList(Map<String, Object> params);

	void save(User user);

	User get(@Param("id") Long id);

	void update(User user);

	void delete(@Param("id") Long id);

	Integer checkName(@Param("name") String name);
	
}
