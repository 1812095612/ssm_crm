package com.qiaosoftware.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qiaosoftware.crm.entity.Authority;

public interface AuthorityMapper {

	List<Authority> getAuthorityList();

	List<Authority> getSubList(@Param("id") Long id);

	void update(@Param("id") Long id, @Param("authId") Long authId);

	List<String> getRoleAuthorityStringList(@Param("id") Long id);

	void deleteOldRelationship(@Param("id") Long id);

}
