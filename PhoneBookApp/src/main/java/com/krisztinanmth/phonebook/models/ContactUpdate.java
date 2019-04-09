package com.krisztinanmth.phonebook.models;

import java.util.List;

public class ContactUpdate {
	
	private String id;
	private String firstName;
	private String lastName;
	private String dateOfBirth;
	private List<String> phoneNumber;
	private List<Address> address;
	
	public ContactUpdate(Contact contact) {
		this.id = contact.getId();
		this.firstName = contact.getFirstName();
		this.lastName = contact.getLastName();
		this.dateOfBirth = contact.getDateOfBirth();
		this.phoneNumber = contact.getPhoneNumber();
		this.address = contact.getAddress();
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
	
	public Contact getUpdatedContact(Contact contact, ContactUpdate contactUpdate) {
		if (!contact.getFirstName().equals(contactUpdate.getFirstName())) {
			contact = contact.with_FirstName(contactUpdate.getFirstName());
		}
		if (!contact.getLastName().equals(contactUpdate.getLastName())) {
			contact = contact.with_LastName(contactUpdate.getLastName());
		}
		if (!contact.getDateOfBirth().equals(contactUpdate.getDateOfBirth())) {
			contact = contact.with_DateOfBirth(contactUpdate.getDateOfBirth());
		}
		if (!contact.getPhoneNumber().equals(contactUpdate.getPhoneNumber())) {
			contact = contact.with_PhoneNumber(contactUpdate.getPhoneNumber());
		}
		if (!contact.getAddress().equals(contactUpdate.getAddress())) {
			contact = contact.with_Address(contactUpdate.getAddress());
		}
		return contact;
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
		ContactUpdate other = (ContactUpdate) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
