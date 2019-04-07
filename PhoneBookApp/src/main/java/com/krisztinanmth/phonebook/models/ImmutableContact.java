package com.krisztinanmth.phonebook.models;

import java.util.List;
import java.util.UUID;

public final class ImmutableContact {
	
	private final String id;
	private final String firstName;
	private final String lastName;
	private final String dateOfBirth;
	private final List<String> phoneNumList;
	private List<Address> addressList;
	
	public ImmutableContact(String firstName, String lastName, String dateOfBirth, List<String> phoneNumList, List<Address> addressList) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumList = phoneNumList;
		this.addressList = addressList;
		this.id = UUID.randomUUID().toString();
	}

	protected String getId() {
		return id;
	}

	protected String getFirstName() {
		return firstName;
	}

	protected String getLastName() {
		return lastName;
	}

	protected String getDateOfBirth() {
		return dateOfBirth;
	}

	protected List<String> getPhoneNumList() {
		return phoneNumList;
	}

	protected List<Address> getAddressList() {
		return addressList;
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
		ImmutableContact other = (ImmutableContact) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
