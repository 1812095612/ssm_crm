package com.qiaosoftware.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qiaosoftware.crm.entity.Product;
import com.qiaosoftware.crm.mapper.ProductMapper;
import com.qiaosoftware.crm.model.Page;

@Service
public class ProductService {

	@Autowired
	private ProductMapper productMapper;

	@Transactional(readOnly=true)
	public Page<Product> getConditionPage(String pageNoStr, String name, String type, String batch) {
		
		if (name.equals("null")) {
			name = null;
		}else {
			name = "%" + name + "%";
		}
		
		if (type.equals("null")) {
			type = null;
		}else {
			type = "%" + type + "%";
		}
		
		if (batch.equals("null")) {
			batch = null;
		}else {
			batch = "%" + batch + "%";
		}
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("name", name);
		params.put("type", type);
		params.put("batch", batch);
		
		int totalRecordNo = (int) productMapper.getTotalRecordNo(params);
		
		Page<Product> page = new Page<>(pageNoStr, Page.PAGE_SIZE, totalRecordNo);
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		
		int fromIndex = (pageNo - 1) * pageSize + 1;
		int endIndex = fromIndex + pageSize;
		
		params.put("fromIndex", fromIndex);
		params.put("endIndex", endIndex);
		
		List<Product> list = productMapper.getProductList(params);
		
		page.setList(list);
		
		return page;
	}

	@Transactional
	public void save(Product product) {
		productMapper.save(product);
	}

	@Transactional(readOnly=true)
	public Product getProduct(Long id) {
		return productMapper.getProduct(id);
	}

	@Transactional
	public void update(Product product) {
		productMapper.update(product);
	}

	@Transactional(readOnly=true)
	public List<Product> getProducts() {
		return productMapper.getProducts();
	}

	@Transactional(readOnly=true)
	public boolean checkProductExists(Product product) {
		
		Integer count = productMapper.checkProductExists(product);
		
		return count != 0;
	}
	
}
