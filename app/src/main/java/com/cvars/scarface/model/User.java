package com.cvars.scarface.model;

import java.util.List;

public abstract class User {

	private String username;
	private List<Order> orders;

	public User(String username){
		this.username = username;
		populateOrders();
	}

	public void populateOrders(){
		// Send a GET request to REST API server to get all orders for this user and populate self.orders
	}

}