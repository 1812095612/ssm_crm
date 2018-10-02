package com.qiaosoftware.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qiaosoftware.crm.entity.Dict;

public interface DictMapper {

	List<String> getRegions();

	List<String> getLevels();

	List<String> getSatisfies();

	List<String> getCredits();

	long getTotalRecordNo(Map<String, Object> params);

	List<Dict> getDictList(Map<String, Object> params);

	void save(Dict dict);

	Dict getDict(@Param("id") Long id);

	void update(Dict dict);

	void delete(@Param("id") Long id);

	Integer checkDictExists(Dict dict);

}
