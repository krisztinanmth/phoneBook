package com.krisztinanmth.phonebook.models;

import java.util.Collections;
import java.util.List;

public class PersonUpdate {
	
	private String id; 
//	 -> az ID-ban meg nem vagyok biztos, h kell-e ennek a wrapperClassnak
//	... sztem kell, csak majd a Contact-e, tehat nem uj...so invoke findContactBtID
	
	private String firstName;
	private String lastName;
	private String dateOfBirth;
	private List<String> phoneNumber;
	private List<Address> address;
	
	public PersonUpdate() {
		this("", "", "", Collections.emptyList(), Collections.emptyList());
	}
	
	public PersonUpdate(String firstName, String lastName, String dateOfBirth, List<String> phoneNumber, List<Address> address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
		this.address = address;
//		this.id =
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<String> getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(List<String> phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}
}
