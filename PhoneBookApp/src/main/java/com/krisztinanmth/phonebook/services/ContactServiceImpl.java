package com.krisztinanmth.phonebook.services;

import com.krisztinanmth.phonebook.exceptions.FirstNameNotFoundException;
import com.krisztinanmth.phonebook.models.Address;
import com.krisztinanmth.phonebook.models.Contact;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {
  private JSONParser parser;
  private List<Contact> contacts;

  public ContactServiceImpl() {
    contacts = new ArrayList<>();
  }

  public ContactServiceImpl(String path) {
    this();                            /////// !!!!!!!!!!!!!!!
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
        contacts.add(createContactFromJSONObject(jo));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  private Contact createContactFromJSONObject(JSONObject jo) {
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

  public void showAllFirstNames() {
    for (Contact contact : contacts) {
      System.out.println(contact.toString());
    }
  }

  @Override
  public List<Contact> findByFirstName(String firstName) throws FirstNameNotFoundException {
    if (firstName == null || "".equals(firstName)) {
      throw new FirstNameNotFoundException("First name was not found by given parameters.");
    }
    return contacts.stream()
      .filter(contact -> contact.getFirstName().equals(firstName))
      .collect(Collectors.toList());
  }

  @Override
  public List<Contact> findByAddress(String ad) {
    return contacts.stream()
      .filter(contact -> contact.getAddress()
        .stream()
        .allMatch(address -> address.getCountry().equals(ad) ||address.getZipCode().equals(ad) || address.getCity().equals(ad) || address.getStreet().equals(ad)))
      .collect(Collectors.toList());
  }

}
