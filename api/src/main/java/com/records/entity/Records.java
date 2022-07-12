package com.records.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "records")
@Entity
public class Records {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int id;
	private int uid;
	private int cid;
	private String symptom;
	private String mno;
	private int number;
	private String remark;
	private Date create_time;
	private Date update_time;
	private Date delete_time;

	@Column(name = "id")
	public int getId() {
		return id;
	}

	@Column(name = "id")
	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "uid")
	public int getUid() {
		return uid;
	}

	@Column(name = "uid")
	public void setUid(int uid) {
		this.uid = uid;
	}

	@Column(name = "cid")
	public int getCid() {
		return cid;
	}

	@Column(name = "cid")
	public void setCid(int cid) {
		this.cid = cid;
	}

	@Column(name = "symptom")
	public String getSymptom() {
		return symptom;
	}

	@Column(name = "symptom")
	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}

	@Column(name = "mno")
	public String getMno() {
		return mno;
	}

	@Column(name = "mno")
	public void setMno(String mno) {
		this.mno = mno;
	}

	@Column(name = "number")
	public int getNumber() {
		return number;
	}

	@Column(name = "number")
	public void setNumber(int number) {
		this.number = number;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	@Column(name = "remark")
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "create_time")
	public Date getCreate_time() {
		return create_time;
	}

	@Column(name = "create_time")
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	@Column(name = "update_time")
	public Date getUpdate_time() {
		return update_time;
	}

	@Column(name = "update_time")
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	@Column(name = "delete_time")
	public Date getDelete_time() {
		return delete_time;
	}

	@Column(name = "delete_time")
	public void setDelete_time(Date delete_time) {
		this.delete_time = delete_time;
	}

}
