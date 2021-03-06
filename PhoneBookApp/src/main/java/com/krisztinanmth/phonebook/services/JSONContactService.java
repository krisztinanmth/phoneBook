package com.krisztinanmth.phonebook.services;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.naming.NameNotFoundException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.krisztinanmth.phonebook.exceptions.AddressNotFoundException;
import com.krisztinanmth.phonebook.exceptions.BirthdayNotFoundException;
import com.krisztinanmth.phonebook.exceptions.ContactNotProvidedException;
import com.krisztinanmth.phonebook.exceptions.FirstNameNotFoundException;
import com.krisztinanmth.phonebook.exceptions.LastNameNotFoundException;
import com.krisztinanmth.phonebook.exceptions.PhoneNumberNotFoundException;
import com.krisztinanmth.phonebook.models.Address;
import com.krisztinanmth.phonebook.models.Contact;
import com.krisztinanmth.phonebook.models.ContactUpdate;

@Service
public class JSONContactService implements ContactService {

	private JSONParser parser;
	private String jsonPath;
	private Set<Contact> contacts = new HashSet<Contact>();

	@Autowired
	public JSONContactService(String jsonPath) {
		parser = new JSONParser();
		this.jsonPath = jsonPath;
		this.contacts = readFromJSON();
	}
	
	public Set<Contact> readFromJSON() {
		Object object;
		try {
			object = parser.parse(new FileReader(jsonPath));
			JSONArray jsonArray = (JSONArray) object;

			for (Object o : jsonArray) {
				JSONObject jo = (JSONObject) o;
				contacts.add(createContactFromJSONObject(jo));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contacts;
	}
	
	public Contact createContactFromJSONObject(JSONObject jo) {
		String firstName = (String) jo.get("firstName");
		String lastName = (String) jo.get("lastName");
		String dateOfBirth = (String) jo.get("dateOfBirth");
		@SuppressWarnings("unchecked")
		List<String> phoneNumber = (List<String>) jo.get("phoneNumber");
		List<Address> addresses = new ArrayList<>();

		JSONArray addressList = (JSONArray) jo.get("address");
		for (Object ad : addressList) {
			Address address = new Address();
			address.setCountry((String) ((JSONObject) ad).get("country"));
			address.setZipCode((String) ((JSONObject) ad).get("zipCode"));
			address.setCity((String) ((JSONObject) ad).get("city"));
			address.setStreet((String) ((JSONObject) ad).get("street"));
			addresses.add(address);
		}
		return new Contact(firstName, lastName, dateOfBirth, phoneNumber, addresses);
	}
	
	public void writeSetOfContactsIntoJSON(Set<Contact> newContacts) {
		String json = new Gson().toJson(newContacts);
		try (FileWriter file = new FileWriter(jsonPath)) {
			file.write(json);
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isContactInList(String id) {
		for (Contact contact : this.contacts) {
			if (contact.getId().equals(id))
				return true;
		}
		return false;
	}

	@Override
	public Optional<Contact> findById(String id) {
		return this.contacts.stream().filter(c -> c.getId().equals(id)).findFirst();
	}

	@Override
	public void createNewContact(Contact contact) {
		if (contact != null) {
			this.contacts.add(contact);
			writeSetOfContactsIntoJSON(this.contacts);
		}
	}

	@Override
	public void bulkCreate(List<Contact> newContacts) {
		if (newContacts.size() == 0)
			throw new ContactNotProvidedException("Please provide a list of contacts to proceed.");
		for (Contact contact : newContacts) {
			if (contact != null) {
				this.contacts.add(contact);
			}
		}
		writeSetOfContactsIntoJSON(this.contacts);
	}

	@Override
	public void deleteContact(String id) {
		if (id != null) {
			Optional<Contact> findContactById = findById(id);
			if (findContactById.isPresent()) {
				this.contacts.removeIf(c -> c.getId().equals(id));
			}
		}
		writeSetOfContactsIntoJSON(this.contacts);
	}
	
	@Override
	public void bulkDelete(List<Contact> contactsToDelete) {
		if (contactsToDelete.size() == 0)
			throw new ContactNotProvidedException("Please provide a list of the contacts you would like to delete.");

		for (int i = 0; i < contactsToDelete.size(); i++) {
			if (this.contacts.contains(contactsToDelete.get(i))) {
				this.contacts.remove(contactsToDelete.get(i));
			} 
		}
		writeSetOfContactsIntoJSON(this.contacts);
	}
	
	@Override
	public void updateContact(String id, ContactUpdate contactUpdate) {
		Optional<Contact> optContact = findById(id);
		if (optContact.isPresent()) {
			Contact contact = optContact.get();
			Contact updatedContact = contactUpdate.getUpdatedContact(contact, contactUpdate);
			this.contacts.removeIf(c -> c.getId().equals(id));
			this.contacts.add(updatedContact);
			writeSetOfContactsIntoJSON(this.contacts);
		} else {
			System.err.println("No contact was found with given id");
		}
	}
			
	@Override
	public List<Contact> findByFirstName(String firstName) {
		if (firstName == null || firstName.length() == 0)
			throw new FirstNameNotFoundException("First name was not provided.");

		List<Contact> contacts = this.contacts.stream()
				.filter(contact -> contact.getFirstName().equals(firstName))
				.collect(Collectors.toList());

		if (contacts.size() == 0) {
			System.err.println("The list is empty, as no contact was found with given first name.");
		}
		return contacts;
	}

	@Override
	public List<Contact> findByLastName(String lastName) {
		if (lastName == null || lastName.length() == 0)
			throw new LastNameNotFoundException("Last name was not provided");

		List<Contact> contacts = this.contacts.stream()
				.filter(contact -> contact.getLastName().equals(lastName))
				.collect(Collectors.toList());

		if (contacts.size() == 0) {
			System.err.println("The list is empty, as no contact was found with given last name.");
		}

		return contacts;
	}
	
	@Override
	public Contact findByName(String name) {
		if (name == null || name.length() == 0)
			try {
				throw new NameNotFoundException("No name was provided.");
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
		
		Contact foundContact = Contact.initialContact();
		for (Contact contact : this.contacts) {
			if (contact.getName().equals(name)) {
				foundContact = contact;
			}
		}
		return foundContact;
	}

	@Override
	public List<Contact> findListOfContactsByName(String name) {
		if (name == null || name.length() == 0)
			try {
				throw new NameNotFoundException("No name was provided.");
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}

		List<Contact> contacts = this.contacts.stream()
				.filter(contact -> contact.getFirstName().equals(name) || contact.getLastName().equals(name) || contact.getName().equals(name))
				.collect(Collectors.toList());

		if (contacts.size() == 0) {
			System.err.println("The list is empty, as no contact was found with given name.");
		}
		return contacts;
	}

	@Override
	public List<Contact> findByDate(String fromDate, String toDate) {
		if (fromDate == null || toDate == null)
			throw new BirthdayNotFoundException("Please provide a date range.");

		List<Contact> contacts = this.contacts.stream()
				.filter(c -> Integer.parseInt(c.getDateOfBirth()) >= Integer.parseInt(fromDate) && Integer.parseInt(c.getDateOfBirth()) <= Integer.parseInt(toDate))
				.collect(Collectors.toList());

		if (contacts.size() == 0) {
			System.err.println("The list is empty, as no contact was found with date of birth in the provided range.");
		}
		return contacts;
	}

	@Override
	public List<Contact> findByPhoneNumber(List<String> phoneNums) {
		if (phoneNums.size() == 0)
			throw new PhoneNumberNotFoundException("No phone-number was provided");

		List<Contact> contacts = this.contacts.stream()
				.filter(contact -> contact.getPhoneNumber().equals(phoneNums))
				.collect(Collectors.toList());

		if (contacts.size() == 0) {
			System.err.println("The list is empty, as no contact was found with given phone numbers.");
		}
		return contacts;
	}

	@Override
	public List<Contact> findByAddress(String ad) {
		if (ad == null || ad.length() == 0)
			throw new AddressNotFoundException("Address was not provided.");

		List<Contact> contacts = this.contacts.stream()
				.filter(contact -> contact.getAddress().stream()
						.allMatch(address -> address.getCountry().equals(ad) || address.getZipCode().equals(ad) || address.getCity().equals(ad) || address.getStreet().equals(ad)))
				.collect(Collectors.toList());

		if (contacts.size() == 0) {
			System.err.println("The list is empty, as no contact was found with given address.");
		}
		return contacts;
	}

	@Override
	public Set<Contact> getAllContacts() {
		return this.contacts;
	}

}
