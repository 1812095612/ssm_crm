package com.qiaosoftware.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qiaosoftware.crm.entity.Product;

public interface ProductMapper {

	long getTotalRecordNo(Map<String, Object> params);

	List<Product> getProductList(Map<String, Object> params);

	void save(Product product);

	Product getProduct(@Param("id") Long id);

	void update(Product product);

	List<Product> getProducts();

	Integer checkProductExists(Product product);

}
