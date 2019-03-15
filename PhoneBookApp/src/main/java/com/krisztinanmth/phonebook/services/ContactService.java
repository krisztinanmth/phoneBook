package com.krisztinanmth.phonebook.services;

import com.krisztinanmth.phonebook.exceptions.*;
import com.krisztinanmth.phonebook.models.Contact;

import javax.naming.NameNotFoundException;
import java.util.List;
import java.util.Map;

public interface ContactService {

  void showAllContacts() throws ContactNotFoundException;
  List<Contact> getAllContacts();
  boolean isContactInList(String id);
  Contact findContactById(String id);
  boolean isContactValid(Contact contact) throws ContactNotProvidedException, ContactAlreadyExistsException;
  void createNewContact(Contact contact) throws ContactNotProvidedException, ContactAlreadyExistsException ;
  void bulkCreate(List<Contact> contacts) throws ContactNotProvidedException, ContactAlreadyExistsException ;
  void deleteContact(String id) throws ContactNotProvidedException, ContactNotFoundException;
  void bulkDelete(List<Contact> contactsToDelete) throws ContactNotProvidedException, ContactNotFoundException;
  void updateContact(String id, Contact updatedContact) throws ContactNotProvidedException;
//  void updateContact(String id, Map<String, String> updatedContactMap) throws ContactNotProvidedException;
  List<Contact> findByFirstName(String firstName) throws FirstNameNotFoundException;
  List<Contact> findByLastName(String lastName) throws LastNameNotFoundException;
  List<Contact> findByName(String name) throws NameNotFoundException;
  List<Contact> findByDate(Integer dateFrom, Integer dateUntil) throws BirthdayNotFoundException;
  List<Contact> findByPhoneNumber(List<String> phoneNums) throws PhoneNumberNotFoundException;
  List<Contact> findByAddress(String ad) throws AddressNotFoundException;
}
