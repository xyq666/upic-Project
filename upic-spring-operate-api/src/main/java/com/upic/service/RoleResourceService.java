package com.upic.service;

import com.upic.condition.RoleResourceCondition;
import com.upic.dto.RoleResourceInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zhubuqing on 2017/9/7.
 */
public interface RoleResourceService {
	/**
	 * 添加角色资源关系
	 *
	 * @param roleResourceInfo
	 * @return
	 */
	RoleResourceInfo addRoleResource(RoleResourceInfo roleResourceInfo);

	/**
	 * 查询角色资源关系（条件）
	 *
	 * @param roleResourceCondition
	 * @param pageable
	 * @return
	 */
	Page<RoleResourceInfo> searchRoleResource(RoleResourceCondition roleResourceCondition, Pageable pageable);

	/**
	 * 删除角色资源关系
	 *
	 * @param roleResourceInfo
	 */
	void deleteRoleResource(RoleResourceInfo roleResourceInfo);

	/**
	 * 查询角色资源关系（条件）
	 *
	 * @param roleResourceCondition
	 * @return
	 */
	List<RoleResourceInfo> findAll(RoleResourceCondition roleResourceCondition);

	/**
	 * 添加角色资源关系
	 *
	 * @param roleResourceInfoList
	 * @return
	 */
	String addAll(List<RoleResourceInfo> roleResourceInfoList);

	/**
	 * 根据角色id获取对应的角色菜单关系
	 * 
	 * @param roleId
	 * @return
	 */
	List<RoleResourceInfo> getByRoleId(long roleId);

	String updateRoleResource(List<RoleResourceInfo> roleResourceInfoList,List<RoleResourceInfo> beforeRoleResourceInfoList);
}
