package com.qiaosoftware.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qiaosoftware.crm.entity.Contact;

public interface ContactMapper {

	void save(Contact contact);

	long getTotalRecordNo(Map<String, Object> params);

	List<Contact> getList(Map<String, Object> params);

	void saveEntity(Contact contact);

	Contact get(@Param("id") Long id);

	void update(Contact contact);

	void delete(@Param("contactId") Long contactId);

	List<Contact> getManagers(@Param("id") Long id);

	List<Long> getManagerIdList(String managerName);

}
