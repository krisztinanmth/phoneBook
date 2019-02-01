package com.krisztinanmth.phonebook.services;

import com.krisztinanmth.phonebook.exceptions.FirstNameNotFoundException;
import com.krisztinanmth.phonebook.models.Contact;

import java.util.List;

public interface ContactService {

  void showAllFirstNames();
  List<Contact> findByFirstName(String firstName) throws FirstNameNotFoundException;
  List<Contact> findByAddress(String ad);
}
