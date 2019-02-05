package com.krisztinanmth.phonebook.services;

import com.krisztinanmth.phonebook.exceptions.*;
import com.krisztinanmth.phonebook.models.Contact;

import javax.naming.NameNotFoundException;
import java.util.List;

public interface ContactService {

  void showAllContacts() throws ContactNotFoundException;
  boolean isContactInList(String id);
  Contact findContactById(String id);
  void createNewContact(Contact contact) throws ContactNotProvidedException ;
  void bulkCreate(List<Contact> contacts) throws ContactNotProvidedException;
  void deleteContact(String id) throws ContactNotProvidedException;
  void bulkDelete(List<Contact> contactsToDelete) throws ContactNotProvidedException;
  void updateContact(String id, Contact updatedContact) throws ContactNotProvidedException;
  List<Contact> findByFirstName(String firstName) throws FirstNameNotFoundException;
  List<Contact> findByLastName(String lastName) throws LastNameNotFoundException;
  List<Contact> findByName(String name) throws NameNotFoundException;
  List<Contact> findByDate(Integer dateFrom, Integer dateUntil) throws BirthdayNotFoundException;
  List<Contact> findByPhoneNumber(List<String> phoneNums) throws PhoneNumberNotFoundException;
  List<Contact> findByAddress(String ad) throws AddressNotFoundException;
}
