package com.qiaosoftware.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qiaosoftware.crm.entity.Storage;

public interface StorageMapper {

	long getTotalRecordNo(Map<String, Object> params);

	List<Storage> getStorageList(Map<String, Object> params);

	void save(Storage storage);

	Storage getStorage(@Param("id") Long id);

	void update(Map<String, Object> params);

}
