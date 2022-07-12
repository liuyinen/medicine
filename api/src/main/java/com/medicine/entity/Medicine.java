package com.medicine.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "medicine")
@Entity
public class Medicine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int id;
	private String mno;
	private String mname;
	private int stock;
	private String mmode;
	private BigDecimal money;
	private String mefficacy;
	private Timestamp create_time;
	private Timestamp update_time;
	private Timestamp delete_time;
	
	public Medicine() {}

	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "mno")
	public String getMno() {
		return mno;
	}

	public void setMno(String mno) {
		this.mno = mno;
	}
	
	@Column(name = "mname")
	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}
	
	@Column(name = "stock")
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@Column(name = "mmode")
	public String getMmode() {
		return mmode;
	}

	public void setMmode(String mmode) {
		this.mmode = mmode;
	}

	@Column(name = "mefficacy")
	public String getMefficacy() {
		return mefficacy;
	}
	
	public void setMefficacy(String mefficacy) {
		this.mefficacy = mefficacy;
	}
	
	@Column(name = "money")
	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	@Column(name = "create_time")
	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	@Column(name = "update_time")
	public Timestamp getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}

	@Column(name = "delete_time")
	public Timestamp getDelete_time() {
		return delete_time;
	}

	public void setDelete_time(Timestamp delete_time) {
		this.delete_time = delete_time;
	}

}
