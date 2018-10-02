package com.qiaosoftware.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.qiaosoftware.crm.entity.CustomerDrain;

public interface CustomerDrainMapper {
	
	@Update("{call check_drain}")
	public void callCustomerDrainCheckProcecure();

	long getTotalRecordNo(Map<String, Object> params);

	List<CustomerDrain> getDrainList(Map<String, Object> params);

	public CustomerDrain get(@Param("id") Long id);

	public void updateDelay(@Param("id") Long id, @Param("delay") String delay);

	public void updateConfirm(Map<String, Object> params);

}
