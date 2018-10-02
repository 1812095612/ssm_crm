package com.qiaosoftware.crm.mapper;

import java.util.List;
import java.util.Map;

public interface ReportMapper {

	List<Map<String, Object>> getCustomerTotalMoneyContent(Map<String, Object> params);
	
	long getCustomerTotalMoneyCount(Map<String, Object> params);

	long getConsistCount(Map<String, Object> params);

	List<Map<String, Object>> getConsistContent(Map<String, Object> params);

	long getServiceCount(Map<String, Object> params);

	List<Map<String, Object>> getServiceContent(Map<String, Object> params);

	List<Map<String, Object>> getDataForChart();

}
