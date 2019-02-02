package com.krisztinanmth.phonebook.services;

import com.krisztinanmth.phonebook.exceptions.AddressNotFoundException;
import com.krisztinanmth.phonebook.exceptions.FirstNameNotFoundException;
import com.krisztinanmth.phonebook.exceptions.LastNameNotFoundException;
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

  @Autowired
  public ContactServiceImpl() {
    this.jsonService = new JSONServiceImpl();
    //contacts = new ArrayList<>();
    contacts = jsonService.readFromJSON("src/main/resources/contacts.json");
  }

  @Override
  public void showAllContacts() {
    for (Contact contact : contacts) {
      System.out.println(contact.toString());
    }
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
      throw new NameNotFoundException("No last name or first name was provided.");

    List<Contact> contacts = this.contacts.stream()
      .filter(contact -> contact.getFirstName().equals(name) || contact.getLastName().equals(name) || contact.getName().equals(name))
      .collect(Collectors.toList());

    if (contacts.size() == 0)
      throw new NameNotFoundException("Name was not found with given parameters.");

    return contacts;
  }

  /**
   * - Filter by phone number: Support searching contacts by their phone number
   */

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
      throw new AddressNotFoundException("Address was not found with given parameters.");

    return contacts;
  }

}
