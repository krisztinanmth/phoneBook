package com.krisztinanmth.phonebook.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

	private UUID id;
	private String firstName;
	private String lastName;
//	private LocalDate dateOfBirth;
	
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
		this.id = UUID.randomUUID();
	}
	
//	public Contact(String firstName, String lastName, LocalDate dateOfBirth, List<String> phoneNumber, List<Address> address) {
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.dateOfBirth = dateOfBirth;
//		this.phoneNumber = phoneNumber;
//		this.address = address;
//		this.id = UUID.randomUUID();
//	}
	
	public UUID getId() {
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

	// i need the name getter for the findByName method in contactService
	public String getName() {
		return firstName + " " + lastName;
	}
	
//	public void setName(String name) {
//		this.name = name;
//	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}
	
//	public LocalDate getDateOfBirth() {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-d");
//		String formattedDateOfBirthString = dateOfBirth.format(formatter);
//		LocalDate formattedDateOfBirth = LocalDate.parse(formattedDateOfBirthString);
//		return formattedDateOfBirth;
//	}
	
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
//	public void setDateOfBirth(LocalDate dateOfBirth) {
//		this.dateOfBirth = dateOfBirth;
//	}
	
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
