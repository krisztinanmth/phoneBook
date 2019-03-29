package com.krisztinanmth.phonebook.models;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Contact {

	public enum Field {
		FIRST_NAME("FIRST NAME: "),
		LAST_NAME("LAST NAME: "),
		DATE_OF_BIRTH("DATE OF BIRTH: "),
		PHONE_NUMBER("PHONE NUMBER: "),
		ADDRESS("ADDRESS: ");
		
		private final String fieldName;
		
		private Field(String fieldName) {
			this.fieldName = fieldName;
		}
		
		public String getFieldName() {
			return fieldName;
		}
	}

	private String id;
	private String firstName;
	private String lastName;
	private String dateOfBirth;
	private List<String> phoneNumber;
	private List<Address> address;

	public Contact() {
		this("", "", "", Collections.emptyList(), Collections.emptyList());
	}

	public Contact(String firstName, String lastName, String dateOfBirth, List<String> phoneNumber, List<Address> address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.id = UUID.randomUUID().toString();
	}
	
	public String getId() {
		return id;
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

	public String getName() {
		return firstName + " " + lastName;
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

	@Override
	public String toString() {
		return firstName + " " + lastName + " " + dateOfBirth + " " + phoneNumber + " " + address.toString();
	}

}
