package com.analysys.kong.model;

public class User extends BaseBean {
	private static final long serialVersionUID = 416098608950322959L;
	private String uid;
	private String uname;
	private String epwd;
	private String status;
	private String createtime;
	private String remarks;
	private String apicnt;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getEpwd() {
		return epwd;
	}
	public void setEpwd(String epwd) {
		this.epwd = epwd;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getApicnt() {
		return apicnt;
	}
	public void setApicnt(String apicnt) {
		this.apicnt = apicnt;
	}
}
