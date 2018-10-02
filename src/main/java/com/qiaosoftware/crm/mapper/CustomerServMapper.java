package com.qiaosoftware.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qiaosoftware.crm.entity.CustomerServ;

public interface CustomerServMapper {

	void save(CustomerServ customerServ);

	long getTotalRecordNo(Map<String, Object> params);

	List<CustomerServ> getConditionList(Map<String, Object> params);

	void allotTo(Map<String, Object> params);

	void delete(@Param("id") Long id);

	CustomerServ getServById(@Param("id") Long id);

	void dealService(CustomerServ customerServ);

	void feedbackService(CustomerServ customerServ);

}
