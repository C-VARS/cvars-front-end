package com.cvars.ScotiaTracker.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class User {
	/**
	 * POJO for user information
	 */

	@SerializedName("name")
	private String name;

	@SerializedName("contact")
	private String contact;

	@SerializedName("address")
	private String address;

	@SerializedName("bankInformation")
	private String bankInformation;

	@SerializedName("infoRequestStatus")
	private boolean infoRequestStatus;

	public String getName() {
		return name;
	}

	public String getContact() {
		return contact;
	}

	public String getAddress() {
		return address;
	}

	public String getBankInformation() {
		return bankInformation;
	}

	public boolean getInfoRequestStatus(){
		return infoRequestStatus;
	}
}