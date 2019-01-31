package com.krisztinanmth.phonebook.services;

import com.krisztinanmth.phonebook.models.Address;
import com.krisztinanmth.phonebook.models.Contact;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.json.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContactServiceImpl implements ContactService {

  private List<Contact> contactList;
  private JSONParser parser;

  public ContactServiceImpl() {
    contactList = new ArrayList<>();
  }

  public ContactServiceImpl(String path) {
    this();
    parser = new JSONParser();
    readFromJSON(path);
  }

  private void readFromJSON(String path) {
    Object object;
    try {
      object = parser.parse(new FileReader(path));
      JSONArray jsonArray = (JSONArray) object;

      for (Object o : jsonArray) {
        JSONObject jo = (JSONObject) o;
        contactList.add(createContactFromJSONOBject(jo));
      }
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Contact createContactFromJSONOBject(JSONObject jo) {
    String firstName = (String) jo.get("firstName");
    String lastName = (String) jo.get("lastName");
    String dateOfBirth = (String) jo.get("dateOfBirth");
    List<String> phoneNumber = (List<String>) jo.get("phoneNumber");
    List<String> addresses = new ArrayList<>();

    JSONArray addressList = (JSONArray) jo.get("address");
    for (Object ad : addressList) {
      Address address = new Address();
      address.setCountry((String) ((JSONObject) ad).get("country"));
      address.setZipCode((String) ((JSONObject) ad).get("zipCode"));
      address.setCity((String) ((JSONObject) ad).get("city"));
      address.setStreet((String) ((JSONObject) ad).get("street"));
      addresses.add(String.valueOf(address));
    }
    return new Contact(firstName, lastName, dateOfBirth, phoneNumber, addressList);
  }

  @Override
  public void showAllFirstNames() {
    for (Contact contact : contactList) {
      System.out.println(contact.toString());
    }
  }
}
