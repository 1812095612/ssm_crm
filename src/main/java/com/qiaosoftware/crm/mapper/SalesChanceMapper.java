package com.qiaosoftware.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qiaosoftware.crm.entity.SalesChance;

public interface SalesChanceMapper {

	long getTotalRecordNo(Map<String, Object> params);

	List<SalesChance> getList(Map<String, Object> params);

	void save(SalesChance chance);

	void delete(@Param("id") long id);

	SalesChance get(@Param("id") long id);

	void update(SalesChance salesChance);

	long getConditionTotalRecordNo(Map<String, Object> params);

	List<SalesChance> getConditionList(Map<String, Object> params);

	void updateChance(SalesChance chance);

	void updateStatus(SalesChance chance);

}
