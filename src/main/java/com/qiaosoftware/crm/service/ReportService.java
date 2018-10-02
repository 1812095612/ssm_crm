package com.qiaosoftware.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qiaosoftware.crm.mapper.ReportMapper;
import com.qiaosoftware.crm.model.Page;
import com.qiaosoftware.crm.model.PropertyFilter;

@Service
public class ReportService {
	
	@Autowired
	private ReportMapper reportMapper;

	@Transactional(readOnly=true)
	public Page<Map<String, Object>> getCustomerTotalOrderMoneyPage(
			String pageNoStr, Map<String, Object> requestParams) {
		
		Map<String, Object> params = parseRequestParams2MybatisParams(requestParams);
		
		int totalRecordNo = (int) reportMapper.getCustomerTotalMoneyCount(params);
		
		Page<Map<String, Object>> page = new Page<>(pageNoStr, Page.PAGE_SIZE, totalRecordNo);
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		int fromIndex = (pageNo - 1) * pageSize + 1;
		int endIndex = fromIndex + pageSize;
		params.put("fromIndex", fromIndex);
		params.put("endIndex", endIndex);
		
		List<Map<String, Object>> list = reportMapper.getCustomerTotalMoneyContent(params);
		page.setList(list);
		
		return page;
	}
	
	/**
	 * 辅助方法
	 * @param requestParams
	 * @return
	 */
	private Map<String, Object> parseRequestParams2MybatisParams(
			Map<String, Object> requestParams) {
		List<PropertyFilter> filters = PropertyFilter.parseRequestParamsToPropertyFilters(requestParams);
		
		Map<String, Object> params = new HashMap<>();
		
		for(PropertyFilter filter: filters){
			params.put(filter.getPropertyName(), filter.getPropertyVal());
		}
		
		return params;
	}

	@Transactional(readOnly=true)
	public Page<Map<String, Object>> getConsistPage(String pageNoStr,
			String type) {
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("type", type);
		
		int totalRecordNo = (int) reportMapper.getConsistCount(params);
		
		Page<Map<String, Object>> page = new Page<>(pageNoStr, Page.PAGE_SIZE, totalRecordNo);
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		int fromIndex = (pageNo - 1) * pageSize + 1;
		int endIndex = fromIndex + pageSize;
		
		params.put("fromIndex", fromIndex);
		params.put("endIndex", endIndex);
		
		List<Map<String, Object>> list = reportMapper.getConsistContent(params);
		page.setList(list);
		
		return page;
	}

	@Transactional(readOnly=true)
	public Page<Map<String, Object>> getServicePage(String pageNoStr, Map<String, Object> requestParams) {
		
		Map<String, Object> params = parseRequestParams2MybatisParams(requestParams);
		
		int totalRecordNo = (int) reportMapper.getServiceCount(params);
		
		Page<Map<String, Object>> page = new Page<>(pageNoStr, Page.PAGE_SIZE, totalRecordNo);
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		int fromIndex = (pageNo - 1) * pageSize + 1;
		int endIndex = fromIndex + pageSize;
		
		params.put("fromIndex", fromIndex);
		params.put("endIndex", endIndex);
		
		List<Map<String, Object>> list = reportMapper.getServiceContent(params);
		page.setList(list);
		
		return page;
	}

	@Transactional(readOnly=true)
	public List<Map<String, Object>> getDataForChart() {
		return reportMapper.getDataForChart();
	}
	
}
