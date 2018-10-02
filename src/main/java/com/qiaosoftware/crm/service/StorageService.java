package com.qiaosoftware.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qiaosoftware.crm.entity.Storage;
import com.qiaosoftware.crm.mapper.StorageMapper;
import com.qiaosoftware.crm.model.Page;

@Service
public class StorageService {

	@Autowired
	private StorageMapper storageMapper;

	@Transactional(readOnly=true)
	public Page<Storage> getConditionPage(String pageNoStr, String productName, String wareHouse) {
		
		if (productName.equals("null")) {
			productName = null;
		}else {
			productName = "%" + productName + "%";
		}
		
		if (wareHouse.equals("null")) {
			wareHouse = null;
		}else {
			wareHouse = "%" + wareHouse + "%";
		}
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("productName", productName);
		params.put("wareHouse", wareHouse);
		
		int totalRecordNo = (int) storageMapper.getTotalRecordNo(params);
		
		Page<Storage> page = new Page<>(pageNoStr, Page.PAGE_SIZE, totalRecordNo);
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		
		int fromIndex = (pageNo - 1) * pageSize + 1;
		int endIndex = fromIndex + pageSize;
		
		params.put("fromIndex", fromIndex);
		params.put("endIndex", endIndex);
		
		List<Storage> list = storageMapper.getStorageList(params);
		
		page.setList(list);
		
		return page;
	}

	@Transactional
	public void save(Storage storage) {
		storageMapper.save(storage);
	}

	@Transactional(readOnly=true)
	public Storage getStorage(Long id) {
		return storageMapper.getStorage(id);
	}

	@Transactional
	public void update(Long id, Integer incrementCount) {
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("id", id);
		params.put("incrementCount", incrementCount);
		
		storageMapper.update(params);
	}

}
