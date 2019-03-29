package com.krisztinanmth.phonebook.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.naming.NameNotFoundException;

import com.krisztinanmth.phonebook.exceptions.AddressNotFoundException;
import com.krisztinanmth.phonebook.exceptions.BirthdayNotFoundException;
import com.krisztinanmth.phonebook.exceptions.ContactAlreadyExistsException;
import com.krisztinanmth.phonebook.exceptions.ContactNotFoundException;
import com.krisztinanmth.phonebook.exceptions.ContactNotProvidedException;
import com.krisztinanmth.phonebook.exceptions.FirstNameNotFoundException;
import com.krisztinanmth.phonebook.exceptions.LastNameNotFoundException;
import com.krisztinanmth.phonebook.exceptions.PhoneNumberNotFoundException;
import com.krisztinanmth.phonebook.models.Contact;
import com.krisztinanmth.phonebook.models.Contact.Field;

public interface ContactService {

  List<Contact> getAllContacts();
  boolean isContactInList(String id);
  Optional<Contact> findContactById(String id);
  void createNewContact(Contact contact);
  void bulkCreate(List<Contact> contacts);
  void deleteContact(String id);
  void bulkDelete(List<Contact> contactsToDelete);
  void updateContact(String id, Map<Field, Object> updatedContactMap);
  List<Contact> findByFirstName(String firstName);
  List<Contact> findByLastName(String lastName);
  List<Contact> findListOfContactsByName(String name) ;
  Contact findContactByName(String name);
  List<Contact> findByDate(String dateFrom, String dateUntil);
  List<Contact> findByPhoneNumber(List<String> phoneNums);
  List<Contact> findByAddress(String ad);
}