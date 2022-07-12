package com.client.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity指明该类是一个实体bean
//@Table指定了Entity所要映射的数据库表
@Table(name="client")
@Entity
public class Client {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	//@Column注解将属性映射到列
	private int id;
	private String cname;
	private String csex;
	private int cage;
	private String caddress;
	private String cphone;
	private Date create_time;
	private Date update_time;
	private Date delete_time;

	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "cname")
	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Column(name = "csex")
	public String getCsex() {
		return csex;
	}

	public void setCsex(String csex) {
		this.csex = csex;
	}

	@Column(name = "cage")
	public int getCage() {
		return cage;
	}

	public void setCage(int cage) {
		this.cage = cage;
	}

	@Column(name = "caddress")
	public String getCaddress() {
		return caddress;
	}

	public void setCaddress(String caddress) {
		this.caddress = caddress;
	}

	@Column(name = "cphone")
	public String getCphone() {
		return cphone;
	}

	public void setCphone(String cphone) {
		this.cphone = cphone;
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
