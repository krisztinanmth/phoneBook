package com.krisztinanmth.phonebook.services;

import com.krisztinanmth.phonebook.exceptions.*;
import com.krisztinanmth.phonebook.models.Contact;

import javax.naming.NameNotFoundException;
import java.util.List;

public interface ContactService {

  void showAllContacts(List<Contact> contacts) throws ContactNotFoundException;
  void createNewContact(Contact contact);
  void bulkCreate(List<Contact> contacts);
  void deleteContact(Contact contactToDelete);
  void bulkDelete(List<Contact> contactsToDelete);
  void updateContact(String id, Contact updatedContact);
  List<Contact> findByFirstName(String firstName) throws FirstNameNotFoundException;
  List<Contact> findByLastName(String lastName) throws LastNameNotFoundException;
  List<Contact> findByName(String name) throws NameNotFoundException;
  List<Contact> findByDate(Integer dateFrom, Integer dateUntil) throws BirthdayNotFoundException;
  List<Contact> findByPhoneNumber(List<String> phoneNums) throws PhoneNumberNotFoundException;
  List<Contact> findByAddress(String ad) throws AddressNotFoundException;
}
