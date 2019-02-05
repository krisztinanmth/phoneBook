package com.krisztinanmth.phonebook.services;

import com.google.gson.Gson;
import com.krisztinanmth.phonebook.models.Address;
import com.krisztinanmth.phonebook.models.Contact;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

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

  @Override
  public List<Contact> readFromJSON(String path) {
    Object object;
    try {
      object = parser.parse(new FileReader(path));
      JSONArray jsonArray = (JSONArray) object;

      for (Object o : jsonArray) {
        JSONObject jo = (JSONObject) o;
        contacts.add(createContactFromJSONObject(jo));
      }
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return contacts;
  }

  @Override
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
    String json = new Gson().toJson(newContacts);
    try (FileWriter file = new FileWriter(path)) {
      file.write(json);
      file.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
