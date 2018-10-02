package com.qiaosoftware.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qiaosoftware.crm.entity.CustomerActivity;

public interface CustomerActivityMapper {

	long getTotalRecordNo(Map<String, Object> params);

	List<CustomerActivity> getList(Map<String, Object> params);

	void save(CustomerActivity customerActivity);

	CustomerActivity get(@Param("id") Long id);

	void update(CustomerActivity customerActivity);

	void delete(@Param("id") Long id);

}
