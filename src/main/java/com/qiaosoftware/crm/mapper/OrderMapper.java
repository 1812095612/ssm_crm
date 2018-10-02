package com.qiaosoftware.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qiaosoftware.crm.entity.Order;

public interface OrderMapper {

	long getTotalRecordNo(Map<String, Object> params);

	List<Order> getList(Map<String, Object> params);

	Order get(@Param("id") Long id);

}
