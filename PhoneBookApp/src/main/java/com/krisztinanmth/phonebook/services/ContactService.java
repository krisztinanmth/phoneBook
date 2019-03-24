package com.krisztinanmth.phonebook.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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

//  void showAllContacts() throws ContactNotFoundException;
  List<Contact> getAllContacts();
  boolean isContactInList(UUID id);
  Contact findContactById(UUID id);
  boolean isContactValid(Contact contact) throws ContactNotProvidedException, ContactAlreadyExistsException;
  void createNewContact(Contact contact) throws ContactNotProvidedException, ContactAlreadyExistsException ;
  void bulkCreate(List<Contact> contacts) throws ContactNotProvidedException, ContactAlreadyExistsException ;
  void deleteContact(UUID id) throws ContactNotProvidedException, ContactNotFoundException;
  void bulkDelete(List<Contact> contactsToDelete) throws ContactNotProvidedException, ContactNotFoundException;
//  void updateContact(String id, Contact updatedContact) throws ContactNotProvidedException;
  void updateContact(UUID id, Map<Field, Object> updatedContactMap) throws ContactNotProvidedException;
  List<Contact> findByFirstName(String firstName) throws FirstNameNotFoundException;
  List<Contact> findByLastName(String lastName) throws LastNameNotFoundException;
  List<Contact> findListOfContactsByName(String name) throws NameNotFoundException;
  Contact findContactByName(String name) throws NameNotFoundException;
  List<Contact> findByDate(LocalDate dateFrom, LocalDate dateUntil) throws BirthdayNotFoundException;
  List<Contact> findByPhoneNumber(List<String> phoneNums) throws PhoneNumberNotFoundException;
  List<Contact> findByAddress(String ad) throws AddressNotFoundException;
}