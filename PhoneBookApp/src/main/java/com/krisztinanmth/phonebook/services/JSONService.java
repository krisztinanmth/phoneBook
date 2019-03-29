package com.krisztinanmth.phonebook.services;

import java.util.List;

import org.json.simple.JSONObject;

import com.krisztinanmth.phonebook.models.Contact;

public interface JSONService {

  List<Contact> readFromJSON();
  Contact createContactFromJSONObject(JSONObject jo);
  void writeListOfContactsIntoJSON(List<Contact> newContacts);
}
