package com.krisztinanmth.phonebook.services;

import com.krisztinanmth.phonebook.exceptions.AddressNotFoundException;
import com.krisztinanmth.phonebook.exceptions.FirstNameNotFoundException;
import com.krisztinanmth.phonebook.exceptions.LastNameNotFoundException;
import com.krisztinanmth.phonebook.exceptions.PhoneNumberNotFoundException;
import com.krisztinanmth.phonebook.models.Address;
import com.krisztinanmth.phonebook.models.Contact;

import javax.naming.NameNotFoundException;
import java.util.List;

public interface ContactService {

  void showAllContacts(List<Contact> contacts);
  void createNewContact(Contact contact);
  void bulkCreate(List<Contact> contacts);
  void deleteContact(Contact contactToDelete);
  void bulkDelete(List<Contact> contactsToDelete);
  void updateContact(String id, String dataToUpdate, String updatedData);
  void updateContactsPhoneNumber(String id, List<String> newPhoneNums);
  void updateContactsAddress(String id, List<Address> newAddress);
  List<Contact> findByFirstName(String firstName) throws FirstNameNotFoundException;
  List<Contact> findByLastName(String lastName) throws LastNameNotFoundException;
  List<Contact> findByName(String name) throws NameNotFoundException;
  List<Contact> findByAddress(String ad) throws AddressNotFoundException;
  List<Contact> findByPhoneNumber(List<String> phoneNums) throws PhoneNumberNotFoundException;

}
