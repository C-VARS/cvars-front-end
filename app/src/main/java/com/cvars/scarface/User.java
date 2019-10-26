package com.cvars.scarface;

import java.util.List;

public abstract class User {

	private String userId;
	private List<Order> orders;

	public User(String userId){
		this.userId = userId;
		populateOrders();
	}

	public void populateOrders(){
		// Send a GET request to REST API server to get all orders for this user and populate self.orders
	}

}