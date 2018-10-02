package com.qiaosoftware.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qiaosoftware.crm.entity.SalesChance;
import com.qiaosoftware.crm.entity.SalesPlan;

public interface SalesPlanMapper {

	long getConditionTotalRecordNo(Map<String, Object> params);

	List<SalesChance> getConditionList(Map<String, Object> params);

	SalesChance getChanceForPlan(@Param("id") Long id);

	List<SalesPlan> getList(@Param("id") Long id);

	void save(Map<String, Object> params);

	void update(@Param("id") Long id, @Param("result") String result);

	void updateToDo(@Param("id") Long id, @Param("todo") String todo);

	void remove(@Param("id") Long id);

	void saveEntity(SalesPlan plan);

}
