package com.krisztinanmth.phonebook.services;

import com.krisztinanmth.phonebook.exceptions.*;
import com.krisztinanmth.phonebook.models.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.naming.NameNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

  private static JSONService jsonService;
  private List<Contact> contacts;

  private static final String TEST_JSON_PATH = "src/main/resources/testContacts.json";

  @Autowired
  public ContactServiceImpl() {
    jsonService = new JSONServiceImpl();
    contacts = jsonService.readFromJSON("src/main/resources/contacts.json");
  }


  @Override
  public void showAllContacts(List<Contact> contacts) throws ContactNotFoundException {
    for (Contact contact : contacts)
      System.out.println(contact.toString());

    if (contacts.size() == 0)
      throw new ContactNotFoundException("There are no contacts in the list.");
  }

  @Override
  public void createNewContact(Contact contact) throws ContactNotProvidedException {
    if (contact == null)
      throw new ContactNotProvidedException("Please provide a contact with all fields to proceed.");

    this.contacts.add(contact);
    jsonService.writeListOfContactsIntoJSON(TEST_JSON_PATH, this.contacts);
  }

  @Override
  public void bulkCreate(List<Contact> newContacts) throws ContactNotProvidedException {
    if (newContacts.size() == 0)
      throw new ContactNotProvidedException("Please provide a list of contacts to proceed.");

    newContacts.stream()
      .map(contact -> this.contacts.add(contact))
      .collect(Collectors.toList());

    jsonService.writeListOfContactsIntoJSON(TEST_JSON_PATH, this.contacts);
  }

  @Override
  public void deleteContact(Contact contactToDelete) throws ContactNotProvidedException {
    if (contactToDelete == null)
      throw new ContactNotProvidedException("Please provide a contact to proceed.");

    this.contacts.remove(contactToDelete);
    jsonService.writeListOfContactsIntoJSON(TEST_JSON_PATH, this.contacts);
  }

  @Override
  public void bulkDelete(List<Contact> contactsToDelete) throws ContactNotProvidedException {
    if (contactsToDelete.size() == 0)
      throw new ContactNotProvidedException("Please provide a list of the contacts you would like to delete.");
    this.contacts.removeIf(contactsToDelete::contains);
    jsonService.writeListOfContactsIntoJSON(TEST_JSON_PATH, this.contacts);
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
    jsonService.writeListOfContactsIntoJSON(TEST_JSON_PATH, this.contacts);
  }

  @Override
  public List<Contact> findByFirstName(String firstName) throws FirstNameNotFoundException {
    if (firstName == null || "".equals(firstName))
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
    if (lastName == null || "".equals(lastName))
      throw new LastNameNotFoundException("Last name was not provided");

    List<Contact> contacts = this.contacts.stream()
      .filter(contact -> contact.getLastName().equals(lastName))
      .collect(Collectors.toList());

    if (contacts.size() == 0)
      throw new LastNameNotFoundException("Last name was not found with given parameters");

    return contacts;
  }

  @Override
  public List<Contact> findByName(String name) throws NameNotFoundException {
    if (name == null || "".equals(name))
      throw new NameNotFoundException("No name was provided.");

    List<Contact> contacts = this.contacts.stream()
      .filter(contact -> contact.getFirstName().equals(name) || contact.getLastName().equals(name) || contact.getName().equals(name))
      .collect(Collectors.toList());

    if (contacts.size() == 0)
      throw new NameNotFoundException("No contact was found with given name parameters.");

    return contacts;
  }

  @Override
  public List<Contact> findByDate(Integer fromDate, Integer toDate) throws BirthdayNotFoundException {
    if (fromDate == null || toDate == null)
      throw new BirthdayNotFoundException("Please provide a date range.");

    List<Contact> contacts = this.contacts.stream()
      .filter(contact -> Integer.parseInt(contact.getDateOfBirth()) > fromDate && Integer.parseInt(contact.getDateOfBirth()) < toDate)
      .collect(Collectors.toList());

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
    if (ad == null || "".equals(ad))
      throw new AddressNotFoundException("Address was not provided.");

    List<Contact> contacts = this.contacts.stream()
      .filter(contact -> contact.getAddress()
        .stream()
        .allMatch(address -> address.getCountry().equals(ad) || address.getZipCode().equals(ad) || address.getCity().equals(ad) || address.getStreet().equals(ad)))
      .collect(Collectors.toList());

    if (contacts.size() == 0)
      throw new AddressNotFoundException("No contact was found with given address parameters.");

    return contacts;
  }

}
