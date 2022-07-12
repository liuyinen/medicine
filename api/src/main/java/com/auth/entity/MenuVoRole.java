package com.auth.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MenuVoRole {

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

	private HashMap<String, Object> meta;

	public MenuVoRole(Integer id, String title, String name, Boolean alwaysShow, Boolean affix, String icon,
			Integer cache, Integer pid, String path, String component, String redirect, Integer sort,
			Date create_time) {
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getAlwaysShow() {
		return alwaysShow;
	}

	public void setAlwaysShow(Boolean alwaysShow) {
		this.alwaysShow = alwaysShow;
	}

	public Boolean getAffix() {
		return affix;
	}

	public void setAffix(Boolean affix) {
		this.affix = affix;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCache() {
		return cache;
	}

	public void setCache(int cache) {
		this.cache = cache;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public HashMap<String, Object> getMeta() {
		return meta;
	}

	public void setMeta(HashMap<String, Object> meta) {
		this.meta = meta;
	}

}
