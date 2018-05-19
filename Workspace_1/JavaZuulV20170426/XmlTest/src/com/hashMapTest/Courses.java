package com.hashMapTest;

public class Courses {
	
	public String cid;
	public String cname;
	
	public Courses(String id,String name) {
		this.cid=id;
		this.cname=name;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

}
