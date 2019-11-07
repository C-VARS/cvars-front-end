package com.cvars.ScotiaTracker.model;

import java.util.List;

public abstract class User {

	private String username;
	private List<Invoice> invoices;

	public User(String username){
		this.username = username;
		populateOrders();
	}

	public void populateOrders(){
		// Send a GET request to REST API server to get all invoices for this user and populate self.invoices
	}

	public Invoice searchInvoicesByID(int id) {
		// returns Invoice with id if exists, else, returns null

		for (Invoice inv : invoices) {
			if (inv.getInvoiceId() == id) {
				return inv;
			}
			return null;
		}
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}
}