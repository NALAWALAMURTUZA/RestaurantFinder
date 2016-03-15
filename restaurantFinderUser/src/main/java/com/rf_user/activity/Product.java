package com.rf_user.activity;

public class Product {
	String name;
	  public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getService_id() {
		return service_id;
	}


	public void setService_id(int service_id) {
		this.service_id = service_id;
	}


	public boolean isBox() {
		return box;
	}


	public void setBox(boolean box) {
		this.box = box;
	}


	int service_id;
	  boolean box;
	public Product(String name, int service_id, boolean box) {
		super();
		this.name = name;
		this.service_id = service_id;
		this.box = box;
	}

	}
