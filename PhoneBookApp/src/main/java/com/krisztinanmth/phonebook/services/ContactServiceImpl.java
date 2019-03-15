package com.krisztinanmth.phonebook.services;

import com.krisztinanmth.phonebook.exceptions.*;
import com.krisztinanmth.phonebook.models.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

	private static JSONService jsonService;
	private List<Contact> contacts;
	private String jsonPath;

	@Autowired
	public ContactServiceImpl(String jsonPath) {
		jsonService = new JSONServiceImpl();
		this.contacts = jsonService.readFromJSON(jsonPath);
	}

	@Override
	public void showAllContacts() throws ContactNotFoundException {
		for (Contact contact : this.contacts)
			System.out.println(contact.toString());

		if (this.contacts.size() == 0)
			throw new ContactNotFoundException("There are no contacts in the list.");
	}

	@Override
	public boolean isContactInList(String id) {
		for (Contact contact : this.contacts) {
			if (contact.getName().equals(id))
				return true;
		}
		return false;
	}

	@Override
	public Contact findContactById(String id) {
		Contact contactToReturn = new Contact();
		for (Contact contact : this.contacts) {
			if (contact.getName().equals(id)) {
				contactToReturn = contact;
			}
		}
		return contactToReturn;
	}

	@Override
	public boolean isContactValid(Contact contact) throws ContactNotProvidedException, ContactAlreadyExistsException {
		if (contact == null) {
			throw new ContactNotProvidedException("Please provide a contact with all fields to proceed.");
		} else if (isContactInList(contact.getName())) {
			throw new ContactAlreadyExistsException(contact.getName() + " is already in the list.");
		}
		return true;
	}

	@Override
	public void createNewContact(Contact contact)
			throws ContactNotProvidedException, ContactAlreadyExistsException {
		if (isContactValid(contact)) {
			this.contacts.add(contact);
			jsonService.writeListOfContactsIntoJSON(jsonPath, this.contacts);
		}
	}

	@Override
	public void bulkCreate(List<Contact> newContacts)
			throws ContactNotProvidedException, ContactAlreadyExistsException {
		if (newContacts.size() == 0)
			throw new ContactNotProvidedException("Please provide a list of contacts to proceed.");

		for (Contact contact : newContacts) {
			if (isContactValid(contact)) {
				this.contacts.add(contact);
			}
		}
		jsonService.writeListOfContactsIntoJSON(jsonPath, this.contacts);
	}

	@Override
	public void deleteContact(String id) throws ContactNotProvidedException, ContactNotFoundException {
		if (id == null || "".equals(id)) {
			throw new ContactNotProvidedException(
					"Please provide a contact id (first name and last name together) to proceed.");
		} else if (!isContactInList(id)) {
			throw new ContactNotFoundException("No contact was found with given id.");
		} else {
			this.contacts.remove(findContactById(id));
			jsonService.writeListOfContactsIntoJSON(jsonPath, this.contacts);
		}
	}

	@Override
	public void bulkDelete(List<Contact> contactsToDelete)
			throws ContactNotProvidedException, ContactNotFoundException {
		if (contactsToDelete.size() == 0)
			throw new ContactNotProvidedException("Please provide a list of the contacts you would like to delete.");

		for (int i = 0; i < contactsToDelete.size(); i++) {
			if (this.contacts.contains(contactsToDelete.get(i))) {
				this.contacts.remove(contactsToDelete.get(i));
			} else {
				throw new ContactNotFoundException("Contact was not found in the list");
			}
		}
		jsonService.writeListOfContactsIntoJSON(jsonPath, this.contacts);
	}

	@Override
	public void updateContact(String id, Contact updatedContact) throws ContactNotProvidedException {
		if (id == null || "".equals(id))
			throw new ContactNotProvidedException("Please provide an id (first name and last name) to proceed.");
		if (updatedContact == null)
			throw new ContactNotProvidedException("Please provide all the fields of the updated contact.");

		for (Contact contact : this.contacts) {
			if (contact.getName().equals(id)) {
				contact.setFirstName(updatedContact.getFirstName());
				contact.setLastName(updatedContact.getLastName());
				contact.setDateOfBirth(updatedContact.getDateOfBirth());
				contact.setPhoneNumber(updatedContact.getPhoneNumber());
				contact.setAddress(updatedContact.getAddress());
			}
		}
		jsonService.writeListOfContactsIntoJSON(jsonPath, this.contacts);
	}
	
//	@Override
//	public void updateContact(String id, Map<String, String> updatedContactMap) throws ContactNotProvidedException {
//		if (id == null || "".equals(id))
//			throw new ContactNotProvidedException("Please provide an id (first name and last name) to proceed.");
//		if (updatedContactMap == null)
//			throw new ContactNotProvidedException("Please provide all the fields of the updated contact.");
//
//		for (Contact contact : this.contacts) {
//			if (id.equals(contact.getName())) {
//				contact.setFirstName(updatedContactMap.get("firstName"));
//				contact.setLastName(updatedContactMap.get("lastName"));
//				contact.setDateOfBirth(updatedContactMap.get("dateOfBirth"));
//				contact.setPhoneNumber(updatedContactMap.get("phoneNumber"));
//				contact.setAddress(updatedContactMap.get("address"));
//			}
//		}
//		jsonService.writeListOfContactsIntoJSON(jsonPath, this.contacts);
//	}

	@Override
	public List<Contact> findByFirstName(String firstName) throws FirstNameNotFoundException {
		if (firstName == null || "".equals(firstName))
			throw new FirstNameNotFoundException("First name was not provided.");

		List<Contact> contacts = this.contacts.stream().filter(contact -> contact.getFirstName().equals(firstName))
				.collect(Collectors.toList());

		if (contacts.size() == 0)
			throw new FirstNameNotFoundException("First name was not found with given parameters.");

		return contacts;
	}

	@Override
	public List<Contact> findByLastName(String lastName) throws LastNameNotFoundException {
		if (lastName == null || "".equals(lastName))
			throw new LastNameNotFoundException("Last name was not provided");

		List<Contact> contacts = this.contacts.stream().filter(contact -> contact.getLastName().equals(lastName))
				.collect(Collectors.toList());

		if (contacts.size() == 0)
			throw new LastNameNotFoundException("Last name was not found with given parameters");

		return contacts;
	}

	@Override
	public List<Contact> findByName(String name) throws NameNotFoundException {
		if (name == null || "".equals(name))
			throw new NameNotFoundException("No name was provided.");

		List<Contact> contacts = this.contacts.stream().filter(contact -> contact.getFirstName().equals(name)
				|| contact.getLastName().equals(name) || contact.getName().equals(name)).collect(Collectors.toList());

		if (contacts.size() == 0)
			throw new NameNotFoundException("No contact was found with given name parameters.");

		return contacts;
	}

	@Override
	public List<Contact> findByDate(Integer fromDate, Integer toDate) throws BirthdayNotFoundException {
		if (fromDate == null || toDate == null)
			throw new BirthdayNotFoundException("Please provide a date range.");

		List<Contact> contacts = this.contacts.stream()
				.filter(contact -> Integer.parseInt(contact.getDateOfBirth()) > fromDate
						&& Integer.parseInt(contact.getDateOfBirth()) < toDate)
				.collect(Collectors.toList());

		if (contacts.size() == 0)
			throw new BirthdayNotFoundException("No contact was found with date of birth in the provided range.");

		return contacts;
	}

	@Override
	public List<Contact> findByPhoneNumber(List<String> phoneNums) throws PhoneNumberNotFoundException {
		if (phoneNums.size() == 0)
			throw new PhoneNumberNotFoundException("No phone-number was provided");

		List<Contact> contacts = this.contacts.stream().filter(contact -> contact.getPhoneNumber().equals(phoneNums))
				.collect(Collectors.toList());

		if (contacts.size() == 0)
			throw new PhoneNumberNotFoundException("No contact was found with given phone-number parameters.");

		return contacts;
	}

	@Override
	public List<Contact> findByAddress(String ad) throws AddressNotFoundException {
		if (ad == null || "".equals(ad))
			throw new AddressNotFoundException("Address was not provided.");

		List<Contact> contacts = this.contacts.stream()
				.filter(contact -> contact.getAddress().stream()
						.allMatch(address -> address.getCountry().equals(ad) || address.getZipCode().equals(ad)
								|| address.getCity().equals(ad) || address.getStreet().equals(ad)))
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