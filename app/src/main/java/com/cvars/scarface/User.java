package com.cvars.scarface;

import java.util.List;

public abstract class User {

	static enum userTypes {
		DRIVER,
		SMALL_BUSINESS_OWNER,
		SUPPLIER
	}

	private String userId;
	private userTypes type;
	private String password;
	private List<Order> orders;

	public User(String userId, userTypes type, String password){
		this.userId = userId;
		this.type = type;
		this.password = password;
		populateOrders();
	}

	public void populateOrders(){
		// Send a GET request to REST API server to get all orders for this user and populate self.orders
	}

}