package com.auth.service;

import java.util.Date;
import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.dao.RoleDAO;
import com.auth.entity.Role;

@Service("roleService")
public class RoleService {
	@Autowired
	private RoleDAO roleDAO;

	/**
	 * 角色添加
	 * 
	 * @param role
	 * @return
	 */
	public Boolean addRole(Role role) {
		return roleDAO.addRole(role);
	}

	/**
	 * 角色查询（单条件）
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Role query(Object key, Object value) {
		return roleDAO.query(key, value);
	}

	/**
	 * 角色查询（多条件）
	 * 
	 * @param condition
	 * @return
	 */
	public Role queryRole(Map<String, Object> condition) {
		return roleDAO.queryRole(condition);
	}

	/**
	 * 角色列表
	 * 
	 * @param condition
	 * @return
	 */
	public List<Role> queryRoleList(Map<String, Object> condition) {
		return roleDAO.queryRoleList(condition);
	}

	/**
	 * 角色更新
	 * 
	 * @param model
	 */
	public Integer updateRole(Integer id, String rname, String mids, String description, Date update_time) {
		return roleDAO.updateRole(id, rname, mids, description, update_time);
	}

	/**
	 * 角色删除
	 * 
	 * @param id
	 */
	public Integer deleteRole(Integer id, Date delete_time) {
		return roleDAO.deleteRole(id, delete_time);
	}
}
