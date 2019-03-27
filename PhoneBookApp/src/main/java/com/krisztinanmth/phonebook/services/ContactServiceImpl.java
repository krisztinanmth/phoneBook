package com.krisztinanmth.phonebook.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krisztinanmth.phonebook.exceptions.AddressNotFoundException;
import com.krisztinanmth.phonebook.exceptions.BirthdayNotFoundException;
import com.krisztinanmth.phonebook.exceptions.ContactNotFoundException;
import com.krisztinanmth.phonebook.exceptions.ContactNotProvidedException;
import com.krisztinanmth.phonebook.exceptions.FirstNameNotFoundException;
import com.krisztinanmth.phonebook.exceptions.LastNameNotFoundException;
import com.krisztinanmth.phonebook.exceptions.PhoneNumberNotFoundException;
import com.krisztinanmth.phonebook.models.Address;
import com.krisztinanmth.phonebook.models.Contact;
import com.krisztinanmth.phonebook.models.Contact.Field;

@Service
public class ContactServiceImpl implements ContactService {

	private static JSONService jsonService;
	private List<Contact> contacts;

	@Autowired
	public ContactServiceImpl(String jsonPath) {
		jsonService = new JSONServiceImpl(jsonPath);
		this.contacts = jsonService.readFromJSON();
	}
	
	public ContactServiceImpl() {
	}

	@Override
	public boolean isContactInList(UUID id) {
		for (Contact contact : this.contacts) {
			if (contact.getId().equals(id))
				return true;
		}
		return false;
	}

	@Override
	public Contact findContactById(UUID id) {
		Contact contactToReturn = new Contact();
		for (Contact contact : this.contacts) {
			if (contact.getId().equals(id)) {
				contactToReturn = contact;
			}
		}
		return contactToReturn;
	}

	@Override
	public boolean isContactValid(Contact contact) throws ContactNotProvidedException {
		if (contact == null) {
			throw new ContactNotProvidedException("Please provide first name, last name and phone number to proceed.");
		} 
		return true;
	}

//	@Override
//	public void createNewContact(Contact contact)
//			throws ContactNotProvidedException, ContactAlreadyExistsException {
//		if (isContactValid(contact)) {
//			this.contacts.add(contact);
//			jsonService.writeListOfContactsIntoJSON(this.contacts);
//		}
//	}
	
	@Override
	public void createNewContact(Contact contact) throws ContactNotProvidedException {
		if (isContactValid(contact)) {
			this.contacts.add(contact);
			jsonService.writeListOfContactsIntoJSON(this.contacts);
		}
	}

	@Override
	public void bulkCreate(List<Contact> newContacts) throws ContactNotProvidedException {
		if (newContacts.size() == 0)
			throw new ContactNotProvidedException("Please provide a list of contacts to proceed.");
		for (Contact contact : newContacts) {
			if (isContactValid(contact)) {
				this.contacts.add(contact);
			}
		}
		jsonService.writeListOfContactsIntoJSON(this.contacts);
	}

	@Override
	public void deleteContact(UUID id) throws ContactNotProvidedException, ContactNotFoundException {
		if (id == null) {
			throw new ContactNotProvidedException("Please provide a contact id (first name and last name together) to proceed.");
		} else if (!isContactInList(id)) {
			throw new ContactNotFoundException("No contact was found with given id.");
		} else {
			this.contacts.remove(findContactById(id));
			jsonService.writeListOfContactsIntoJSON(this.contacts);
		}
	}
	
	@Override
	public void bulkDelete(List<Contact> contactsToDelete) throws ContactNotProvidedException, ContactNotFoundException {
		if (contactsToDelete.size() == 0)
			throw new ContactNotProvidedException("Please provide a list of the contacts you would like to delete.");

		for (int i = 0; i < contactsToDelete.size(); i++) {
			if (this.contacts.contains(contactsToDelete.get(i))) {
				this.contacts.remove(contactsToDelete.get(i));
			} else {
				throw new ContactNotFoundException("Contact was not found in the list");
			}
		}
		jsonService.writeListOfContactsIntoJSON(this.contacts);
	}

	@Override
	public void updateContact(UUID id, Map<Field, Object> updatedContactMap) throws ContactNotProvidedException {
		
		if (id == null)
			throw new ContactNotProvidedException("Please provide an id (first name and last name) to proceed.");
		if (updatedContactMap == null)
			throw new ContactNotProvidedException("Please provide all the fields of the updated contact.");

		for (Contact contact : this.contacts) {
			if (contact.getId().equals(id)) {
				if (updatedContactMap.containsKey(Contact.Field.FIRST_NAME)) {
					String updatedFirstName = (String) updatedContactMap.get(Contact.Field.FIRST_NAME);
					contact.setFirstName(updatedFirstName);
				} 
				if (updatedContactMap.containsKey(Contact.Field.LAST_NAME)) {
					String updatedLastName = (String) updatedContactMap.get(Contact.Field.LAST_NAME);
					contact.setLastName(updatedLastName);
				}
				
				if (updatedContactMap.containsKey(Contact.Field.DATE_OF_BIRTH)) {
					String updatedDateOfBirth = (String) updatedContactMap.get(Contact.Field.DATE_OF_BIRTH);
					contact.setDateOfBirth(updatedDateOfBirth);
				} 
				if (updatedContactMap.containsKey(Contact.Field.PHONE_NUMBER)) {
					List<String> updatedPhoneNums = (List<String>) updatedContactMap.get(Contact.Field.PHONE_NUMBER);
					contact.setPhoneNumber(updatedPhoneNums);
				} 
				if (updatedContactMap.containsKey(Contact.Field.ADDRESS)) {
					List<Address> updatedAddresses = (List<Address>) updatedContactMap.get(Contact.Field.ADDRESS);
					contact.setAddress(updatedAddresses);
				}
			}
		}
		jsonService.writeListOfContactsIntoJSON(this.contacts);
	}

	@Override
	public List<Contact> findByFirstName(String firstName) throws FirstNameNotFoundException {
		if (firstName == null || firstName.length() == 0)
			throw new FirstNameNotFoundException("First name was not provided.");

		List<Contact> contacts = this.contacts.stream()
				.filter(contact -> contact.getFirstName().equals(firstName))
				.collect(Collectors.toList());

		if (contacts.size() == 0)
			throw new FirstNameNotFoundException("First name was not found with given parameters.");

		return contacts;
	}

	@Override
	public List<Contact> findByLastName(String lastName) throws LastNameNotFoundException {
		if (lastName == null || lastName.length() == 0)
			throw new LastNameNotFoundException("Last name was not provided");

		List<Contact> contacts = this.contacts.stream()
				.filter(contact -> contact.getLastName().equals(lastName))
				.collect(Collectors.toList());

		if (contacts.size() == 0)
			throw new LastNameNotFoundException("Last name was not found with given parameters");

		return contacts;
	}
	
	@Override
	public Contact findContactByName(String name) throws NameNotFoundException {
		if (name == null || name.length() == 0)
			throw new NameNotFoundException("No name was provided.");
		
		Contact foundContact = new Contact();
		for (Contact contact : this.contacts) {
			if (contact.getName().equals(name)) {
				foundContact = contact;
			}
		}
		return foundContact;
	}

	@Override
	public List<Contact> findListOfContactsByName(String name) throws NameNotFoundException {
		if (name == null || name.length() == 0)
			throw new NameNotFoundException("No name was provided.");

		List<Contact> contacts = this.contacts.stream()
//				.filter(contact -> contact.getFirstName().equals(name) || contact.getLastName().equals(name) || contact.getName().equals(name)).collect(Collectors.toList());
				.filter(contact -> contact.getName().equals(name))
				.collect(Collectors.toList());

		if (contacts.size() == 0)
			throw new NameNotFoundException("No contact was found with given name parameters.");

		return contacts;
	}

	@Override
	public List<Contact> findByDate(String fromDate, String toDate) throws BirthdayNotFoundException {
		if (fromDate == null || toDate == null)
			throw new BirthdayNotFoundException("Please provide a date range.");

		List<Contact> contacts = this.contacts.stream()
				.filter(c -> Integer.parseInt(c.getDateOfBirth()) >= Integer.parseInt(fromDate) && Integer.parseInt(c.getDateOfBirth()) <= Integer.parseInt(toDate))
				.collect(Collectors.toList());
		
		
//		List<Contact> contacts = new ArrayList<Contact>();
//		for (Contact contact : this.contacts) {
//			if (contact.getDateOfBirth().compareTo(fromDate) >= 0 && contact.getDateOfBirth().compareTo(toDate) <= 0) {
//				contacts.add(contact);
//			}
//		}
		
//		List<Contact> contacts = this.contacts.stream()
//				.filter(contact -> contact.getDateOfBirth().compareTo(fromDate) >= 0 && contact.getDateOfBirth().compareTo(toDate) <= 0)
//				.collect(Collectors.toList());

		if (contacts.size() == 0)
			throw new BirthdayNotFoundException("No contact was found with date of birth in the provided range.");

		return contacts;
	}

	@Override
	public List<Contact> findByPhoneNumber(List<String> phoneNums) throws PhoneNumberNotFoundException {
		if (phoneNums.size() == 0)
			throw new PhoneNumberNotFoundException("No phone-number was provided");

		List<Contact> contacts = this.contacts.stream()
				.filter(contact -> contact.getPhoneNumber().equals(phoneNums))
				.collect(Collectors.toList());

		if (contacts.size() == 0)
			throw new PhoneNumberNotFoundException("No contact was found with given phone-number parameters.");

		return contacts;
	}

	@Override
	public List<Contact> findByAddress(String ad) throws AddressNotFoundException {
		if (ad == null || ad.length() == 0)
			throw new AddressNotFoundException("Address was not provided.");

		List<Contact> contacts = this.contacts.stream()
				.filter(contact -> contact.getAddress().stream()
						.allMatch(address -> address.getCountry().equals(ad) || address.getZipCode().equals(ad) || address.getCity().equals(ad) || address.getStreet().equals(ad)))
				.collect(Collectors.toList());

		if (contacts.size() == 0)
			throw new AddressNotFoundException("No contact was found with given address parameters.");

		return contacts;
	}

	@Override
	public List<Contact> getAllContacts() {
		return this.contacts;
	}

}