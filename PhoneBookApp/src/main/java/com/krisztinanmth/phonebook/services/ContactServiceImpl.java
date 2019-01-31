package com.krisztinanmth.phonebook.services;

import com.krisztinanmth.phonebook.models.Contact;

import java.util.List;

public class ContactServiceImpl implements ContactService {

  private List<Contact> contactList;

  public ContactServiceImpl() {
  }

  public ContactServiceImpl(List<Contact> contactList) {
    this.contactList = contactList;
  }

  @Override
  public void showAllFirstNames() {
    for (Contact contact : contactList) {
      System.out.println(contact.toString());
    }
  }
}
