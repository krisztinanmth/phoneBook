package com.krisztinanmth.phonebook.services;

import com.krisztinanmth.phonebook.models.Address;
import com.krisztinanmth.phonebook.models.Contact;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JSONServiceImpl implements JSONService {

  private JSONParser parser;
  private List<Contact> contacts;

  public JSONServiceImpl() {
    contacts = new ArrayList<>();
    parser = new JSONParser();
  }

  public List<Contact> readFromJSON(String path) {
    Object object;
    try {
      object = parser.parse(new FileReader(path));
      JSONArray jsonArray = (JSONArray) object;

      for (Object o : jsonArray) {
        JSONObject jo = (JSONObject) o;
        contacts.add(createContactFromJSONObject(jo));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return contacts;
  }

  public Contact createContactFromJSONObject(JSONObject jo) {
    String firstName = (String) jo.get("firstName");
    String lastName = (String) jo.get("lastName");
    String dateOfBirth = (String) jo.get("dateOfBirth");
    List<String> phoneNumber = (List<String>) jo.get("phoneNumber");
    List<Address> addresses = new ArrayList<>();

    JSONArray addressList = (JSONArray) jo.get("address");
    for (Object ad : addressList) {
      Address address = new Address();
      address.setCountry((String) ((JSONObject) ad).get("country"));
      address.setZipCode((String) ((JSONObject) ad).get("zipCode"));
      address.setCity((String) ((JSONObject) ad).get("city"));
      address.setStreet((String) ((JSONObject) ad).get("street"));
      addresses.add(address);
    }
    return new Contact(firstName, lastName, dateOfBirth, phoneNumber, addresses);
  }

  @Override
  public void writeListOfContactsIntoJSON(String path, List<Contact> newContacts) {

    JSONObject jo = new JSONObject();
    JSONArray newContactsJSON = new JSONArray();

    for (Contact contact : newContacts)
      newContactsJSON.add(contact.toString());

    jo.put("Contacts", newContactsJSON);

    try (FileWriter file = new FileWriter(path)) {
      file.write(jo.toString());
      file.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

//  @Override
//  public void writeIntoJSON(String path, Contact newContact) {
//
//    JSONObject jo = new JSONObject();
//
//    jo.put("firstName", newContact.getFirstName());
//    jo.put("lastName", newContact.getLastName());
//    jo.put("dateOfBirth", newContact.getDateOfBirth());
//
//    JSONArray phoneNums = new JSONArray();
//    phoneNums.add(newContact.getPhoneNumber());
//    jo.put("phoneNumber", phoneNums);
//
//    JSONArray address = new JSONArray();
//    address.add(newContact.getAddress().toString());
//    jo.put("address", address);
//
//    try (FileWriter file = new FileWriter(path)) {
//      file.write(jo.toString());
//      file.flush();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }

}
