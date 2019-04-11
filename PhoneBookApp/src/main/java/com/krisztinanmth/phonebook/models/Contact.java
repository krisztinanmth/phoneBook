package com.krisztinanmth.phonebook.models;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Contact is an immutable class
 */
public final class Contact {

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
	
	private final String id;
	private final String firstName;
	private final String lastName;
	private final String dateOfBirth;
	private final List<String> phoneNumber;
	private final List<Address> address;
	
	public static Contact initialContact() {
		return new Contact("", "", "", Collections.emptyList(), Collections.emptyList());
	}

	public Contact(String firstName, String lastName, String dateOfBirth, List<String> phoneNumber, List<Address> address) {
		this(UUID.randomUUID().toString(), firstName, lastName, dateOfBirth, phoneNumber, address);
	}

	private Contact(String id, String firstName, String lastName, String dateOfBirth, List<String> phoneNumber, List<Address> address) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = Collections.unmodifiableList(phoneNumber);
		this.address = Collections.unmodifiableList(address);
	}

	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public Contact withFirstName(String firstname) {
		Contact updatedContact = new Contact(this.id, firstname, this.lastName, this.dateOfBirth, this.phoneNumber, this.address);
		return updatedContact;
	}

	public String getLastName() {
		return lastName;
	}

	public Contact withLastName(String lastName) {
		Contact updatedContact = new Contact(this.id, this.firstName, lastName, this.dateOfBirth, this.phoneNumber, this.address);
		return updatedContact;
	}

	public String getName() {
		return firstName + " " + lastName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public Contact withDateOfBirth(String dateOfBirth) {
		Contact updatedContact = new Contact(this.id, this.firstName, this.lastName, dateOfBirth, this.phoneNumber, this.address);
		return updatedContact;
	}

	public List<String> getPhoneNumber() {
		return phoneNumber;
	}

	public Contact withPhoneNumber(List<String> phoneNumber) {
		Contact updatedContact = new Contact(this.id, this.firstName, this.lastName, this.dateOfBirth, phoneNumber, this.address);
		return updatedContact;
	}

	public List<Address> getAddress() {
		return address;
	}

	public Contact withAddress(List<Address> address) {
		Contact updatedContact = new Contact(this.id, this.firstName, this.lastName, this.dateOfBirth, this.phoneNumber, address);
		return updatedContact;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName + " " + dateOfBirth + " " + phoneNumber + " " + address.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
