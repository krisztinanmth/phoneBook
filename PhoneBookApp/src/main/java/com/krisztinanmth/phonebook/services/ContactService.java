package com.krisztinanmth.phonebook.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.krisztinanmth.phonebook.models.Contact;
import com.krisztinanmth.phonebook.models.Contact.Field;
import com.krisztinanmth.phonebook.models.ContactUpdate;

public interface ContactService {

  List<Contact> getAllContacts();
  boolean isContactInList(String id);
  Optional<Contact> findContactById(String id);
  void createNewContact(Contact contact);
  void bulkCreate(List<Contact> contacts);
  void deleteContact(String id);
  void bulkDelete(List<Contact> contactsToDelete);
  void updateContact(String id, ContactUpdate contactUpdate);
//  void updateContact(String id, Map<Field, Object> updatedContactMap);
  List<Contact> findByFirstName(String firstName);
  List<Contact> findByLastName(String lastName);
  List<Contact> findListOfContactsByName(String name) ;
  Contact findContactByName(String name);
  List<Contact> findByDate(String dateFrom, String dateUntil);
  List<Contact> findByPhoneNumber(List<String> phoneNums);
  List<Contact> findByAddress(String ad);
}