package com.qiaosoftware.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qiaosoftware.crm.entity.Dict;
import com.qiaosoftware.crm.mapper.DictMapper;
import com.qiaosoftware.crm.model.Page;

@Service
public class DictService {

	@Autowired
	private DictMapper dictMapper;

	@Transactional(readOnly=true)
	public List<String> getRegions() {
		return dictMapper.getRegions();
	}

	@Transactional(readOnly=true)
	public List<String> getLevels() {
		return dictMapper.getLevels();
	}

	@Transactional(readOnly=true)
	public List<String> getSatisfies() {
		return dictMapper.getSatisfies();
	}

	@Transactional(readOnly=true)
	public List<String> getCredits() {
		return dictMapper.getCredits();
	}

	@Transactional(readOnly=true)
	public Page<Dict> getConditionPage(String pageNoStr, String type, String item, String value) {
		
		if (type.equals("null")) {
			type = null;
		}else {
			type = "%" + type + "%";
		}
		
		if (item.equals("null")) {
			item = null;
		}else {
			item = "%" + item + "%";
		}
		
		if (value.equals("null")) {
			value = null;
		}else {
			value = "%" + value + "%";
		}
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("type", type);
		params.put("item", item);
		params.put("value", value);
		
		int totalRecordNo = (int) dictMapper.getTotalRecordNo(params);
		
		Page<Dict> page = new Page<>(pageNoStr, Page.PAGE_SIZE, totalRecordNo);
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		
		int fromIndex = (pageNo - 1) * pageSize + 1;
		int endIndex = fromIndex + pageSize;
		
		params.put("fromIndex", fromIndex);
		params.put("endIndex", endIndex);
		
		List<Dict> list = dictMapper.getDictList(params);
		
		page.setList(list);
		
		return page;
	}

	@Transactional
	public void save(Dict dict) {
		dictMapper.save(dict);
	}
	
	@Transactional(readOnly=true)
	public Dict getDict(Long id) {
		return dictMapper.getDict(id);
	}

	@Transactional
	public void update(Dict dict) {
		dictMapper.update(dict);
	}

	@Transactional
	public void delete(Long id) {
		dictMapper.delete(id);
	}

	@Transactional(readOnly=true)
	public boolean checkDictExists(Dict dict) {
		Integer count = dictMapper.checkDictExists(dict);
		return count != 0;
	}
	
}
