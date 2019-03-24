package com.krisztinanmth.phonebook.services;

import com.krisztinanmth.phonebook.models.Contact;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;

public interface JSONService {

  List<Contact> readFromJSON();
	
//  void readFromJSON();
  
  Contact createContactFromJSONObject(JSONObject jo);
  void writeListOfContactsIntoJSON(List<Contact> newContacts);
}
