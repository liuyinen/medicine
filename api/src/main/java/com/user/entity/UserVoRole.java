package com.user.entity;

import java.util.Date;

public class UserVoRole {

	private int id;
	private String role_name;
	private String username;
	private String password;
	private String sex;
	private String phone;
	private String avatar;
	private String introduction;
	private Date create_time;

	public UserVoRole(Integer id, String role_name, String username, String password, String sex, String phone, String avatar,
			String introduction, Date create_time) {
		this.id = id;
		this.role_name = role_name;
		this.username = username;
		this.password = password;
		this.sex = sex;
		this.phone = phone;
		this.avatar = avatar;
		this.introduction = introduction;
		this.create_time = create_time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

}
