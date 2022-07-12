package com.user.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.dao.UserDAO;
import com.user.entity.User;
import com.user.entity.UserVoRole;

@Service("userService")
public class UserService {
	@Autowired
	private UserDAO userDAO;

	/**
	 * 用户添加
	 * 
	 * @param user
	 * @return
	 */
	public boolean addUser(User user) {
		return userDAO.addUser(user);
	}

	/**
	 * 用户查询（单条件）
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public User query(Object key, Object value) {
		return userDAO.query(key, value);
	}

	/**
	 * 用户查询（多条件）
	 * 
	 * @param condition
	 * @return
	 */
	public User queryUser(Map<String, Object> condition) {
		return userDAO.queryUser(condition);
	}

	/**
	 * 查询用户列表
	 * 
	 * @param condition
	 * @param page
	 * @param limit
	 * @return
	 */
	public Map<String, Object> queryUserList(Map<String, Object> condition, Integer page, Integer limit) {
		return userDAO.queryUserList(condition, page, limit);
	}

	/**
	 * 用户更新
	 * 
	 * @param id
	 * @param role_id
	 * @param username
	 * @param password
	 * @param sex
	 * @param phone
	 * @param introduction
	 * @param update_time
	 * @return
	 */
	public Integer updateUser(Integer id, String username, String password, String sex, String phone,
			String introduction, Timestamp update_time) {
		return userDAO.updateUser(id, username, password, sex, phone, introduction, update_time);
	}

	/**
	 * 用户图片上传
	 * 
	 * @param id
	 * @param fileName
	 * @param update_time
	 * @return
	 */
	public Integer updateUserImg(Integer id, String fileName, Timestamp update_time) {
		return userDAO.updateUserImg(id, fileName, update_time);
	}

	/**
	 * 用户删除
	 * 
	 * @param id
	 * @param delete_time
	 * @return
	 */
	public Integer deleteUser(Integer id, Timestamp delete_time) {
		return userDAO.deleteUser(id, delete_time);
	}

	public Integer total() {
		return userDAO.total();
	}

	public List dataCount() {
		return userDAO.dataCount();
	}
	
	public List<UserVoRole> getNewListse() {
		return userDAO.getNewLists();
	}
	
	public List getUserData() {
		return userDAO.getUserData();
	}
}
