package com.qiaosoftware.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qiaosoftware.crm.entity.Customer;

public interface CustomerMapper {

	void save(Customer customer);

	Customer get(@Param("id") Long id);

	void update(Map<String, Object> params);

	void updateCustomer(Customer customer);

	long getConditionTotalRecordNo(Map<String, Object> params);

	List<Customer> getConditionList(Map<String, Object> params);

	void updateConfirm(Map<String, Object> params);

	List<Customer> getAllCustomers();

}
