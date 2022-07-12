package com.records.entity;

import java.sql.Timestamp;
import java.util.Date;

public class UserVoClientVoRecords {

	private int id;
	private int uid;
	private int cid;
	private String symptom;
	private String mno;
	private int number;
	private String remark;
	private String uname;
	private String cname;
	private Date create_time;


//	r.id,r.uid,r.cid,r.symptom,r.mno,r.number,r.remark,u.username,c.cname,r.create_time
	public UserVoClientVoRecords(int id, int uid, int cid, String symptom, String mno, int number, String remark,
			String uname, String cname,Date create_time) {
		this.id = id;
		this.uid = uid;
		this.cid = cid;
		this.symptom = symptom;
		this.mno = mno;
		this.number = number;
		this.remark = remark;
		this.uname = uname;
		this.cname = cname;		
		this.create_time = create_time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getSymptom() {
		return symptom;
	}

	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}

	public String getMno() {
		return mno;
	}

	public void setMno(String mno) {
		this.mno = mno;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

}
