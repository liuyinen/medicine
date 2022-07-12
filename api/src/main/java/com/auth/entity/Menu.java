package com.auth.entity;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "menu")
@Entity
public class Menu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int id;
	private String title;
	private String name;
	private Boolean alwaysShow;
	private Boolean affix;
	private String icon;
	private int status;
	private int cache;
	private int pid;
	private String path;
	private String component;
	private String redirect;
	private int sort;
	private Date create_time;
	private Date update_time;
	private Date delete_time;

	private HashMap<String, Object> meta;

	public Menu() {
		// TODO Auto-generated constructor stub
	}

	public Menu(Integer id, String title, String name, Boolean alwaysShow, Boolean affix, String icon, Integer cache,
			Integer pid, String path, String component, String redirect, Integer sort, Date create_time) {
		this.id = id;
		this.name = name;
		this.alwaysShow = alwaysShow;
		this.affix = affix;
		this.cache = cache;
		this.pid = pid;
		this.path = path;
		this.component = component;
		this.redirect = redirect;
		this.sort = sort;
		this.create_time = create_time;

		this.meta = new HashMap<String, Object>();
		this.meta.put("title", title);
		this.meta.put("icon", icon);
	}

	public HashMap<String, Object> getMeta() {
		return meta;
	}

	public void setMeta(HashMap<String, Object> meta) {
		this.meta = meta;
	}

	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "alwaysShow")
	public Boolean getAlwaysShow() {
		return alwaysShow;
	}

	public void setAlwaysShow(Boolean alwaysShow) {
		this.alwaysShow = alwaysShow;
	}

	@Column(name = "affix")
	public Boolean getAffix() {
		return affix;
	}

	public void setAffix(Boolean affix) {
		this.affix = affix;
	}

	@Column(name = "icon")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "cache")
	public int getCache() {
		return cache;
	}

	public void setCache(int cache) {
		this.cache = cache;
	}

	@Column(name = "pid")
	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	@Column(name = "path")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "component")
	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	@Column(name = "redirect")
	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	@Column(name = "sort")
	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	@Column(name = "create_time")
	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	@Column(name = "update_time")
	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	@Column(name = "delete_time")
	public Date getDelete_time() {
		return delete_time;
	}

	public void setDelete_time(Date delete_time) {
		this.delete_time = delete_time;
	}

}
