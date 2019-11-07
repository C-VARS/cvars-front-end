package com.cvars.ScotiaTracker.model;

import java.util.Collections;
import java.util.List;

public abstract class User {

	private String username;
	private List<Invoice> invoices;

	public User(String username){
		this.username = username;
	}

	public Invoice searchInvoicesByID(int id) {
		// returns Invoice with id if exists, else, returns null

		for (Invoice inv : invoices) {
			if (inv.getInvoiceId() == id) {
				return inv;
			}
		}
		return null;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public List<Invoice> sortedInvoices() {
		Collections.sort(invoices);
		return getInvoices();
	}
}