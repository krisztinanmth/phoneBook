package com.krisztinanmth.phonebook.services;

import com.krisztinanmth.phonebook.models.Contact;
import org.json.simple.JSONObject;

import java.util.List;

public interface JSONService {

  List<Contact> readFromJSON(String path);
  Contact createContactFromJSONObject(JSONObject jo);
//  void writeIntoJSON(String path, Contact newContact);
  void writeListOfContactsIntoJSON(String path, List<Contact> newContacts);
}
