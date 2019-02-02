package com.krisztinanmth.phonebook.services;

import com.krisztinanmth.phonebook.exceptions.AddressNotFoundException;
import com.krisztinanmth.phonebook.exceptions.FirstNameNotFoundException;
import com.krisztinanmth.phonebook.models.Contact;

import java.util.List;

public interface ContactService {

  void showAllContacts();
  List<Contact> findByFirstName(String firstName) throws FirstNameNotFoundException;
  List<Contact> findByAddress(String ad) throws AddressNotFoundException;
}
