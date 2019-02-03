package com.krisztinanmth.phonebook;

import com.krisztinanmth.phonebook.exceptions.AddressNotFoundException;
import com.krisztinanmth.phonebook.exceptions.PhoneNumberNotFoundException;
import com.krisztinanmth.phonebook.models.Address;
import com.krisztinanmth.phonebook.models.Contact;
import com.krisztinanmth.phonebook.services.ContactService;
import com.krisztinanmth.phonebook.services.ContactServiceImpl;
import com.krisztinanmth.phonebook.services.JSONService;
import com.krisztinanmth.phonebook.services.JSONServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class PhoneBookApplication {

  private static ContactService contactService;
  private static JSONService jsonService;

  @Autowired
  public PhoneBookApplication() {
    this.contactService = new ContactServiceImpl();
    this.jsonService = new JSONServiceImpl();
  }


  public static void main(String[] args) {

    SpringApplication.run(PhoneBookApplication.class, args);
    List<String> number = new ArrayList<>();
    number.add("555-98-7678");
    List<Address> address = new ArrayList<>();
    Address ad = new Address("Iowa", "1065", "Nagymezo", "Budapest");
    address.add(ad);



    Contact contact1 = new Contact("caca", "cisco", "221078", number, address);
    Contact contact2 = new Contact("coco", "cisco", "221078", number, address);
    Contact contact3 = new Contact("cucu", "cisco", "221078", number, address);

    List<Contact> contacts = new ArrayList<>();
    contacts.add(contact1);
    contacts.add(contact2);
    contacts.add(contact3);

//    jsonService.writeListOfContactsIntoJSON("src/main/resources/testContacts.json", contacts);
//    contactService.createNewContact(contact1);
//    contactService.createNewContact(contact2);

    contactService.createNewContactsInBulk(contacts);
    contactService.showAllContacts(contacts);
    System.out.println();
    System.out.println("=================================================");
    System.out.println();


//    jsonService.writeIntoJSON("src/main/resources/testContacts.json", contact);

    contactService.showAllContacts(jsonService.readFromJSON("src/main/resources/contacts.json"));

    contactService.updateContact("Gary Fleming", "1-202-555-0144", "555555555");
//
//    try {
//      System.out.println(contactService.findByFirstName("Gary"));
//    } catch (FirstNameNotFoundException e) {
//      e.printStackTrace();
//    }
//
//    try {
//      System.out.println(contactService.findByFirstName(""));
//    } catch (FirstNameNotFoundException e) {
//      e.printStackTrace();
//    }
//
//    try {
//      System.out.println(contactService.findByAddress("351 Red Bud Lane"));
//    } catch (AddressNotFoundException e) {
//      e.printStackTrace();
//    }
//
//    try {
//      System.out.println(contactService.findByAddress(""));
//    } catch (AddressNotFoundException e) {
//      e.printStackTrace();
//    }
//
    try {
      System.out.println(contactService.findByAddress("51331"));
    } catch (AddressNotFoundException e) {
      e.printStackTrace();
    }
//
//    try {
//      System.out.println(contactService.findByAddress(null));
//    } catch (AddressNotFoundException e) {
//      e.printStackTrace();
//    }

    List<String> phoneNums = new ArrayList<>();
    phoneNums.add("1-202-555-0144");

    List<String> nullNumbers = new ArrayList<>();

    List<String> wrongNumber = new ArrayList<>();
    wrongNumber.add("06-70-600-7479");

    try {
      System.out.println(contactService.findByPhoneNumber(phoneNums));
    } catch (PhoneNumberNotFoundException e) {
      e.printStackTrace();
    }

    try {
      System.out.println(contactService.findByPhoneNumber(nullNumbers));
    } catch (PhoneNumberNotFoundException e) {
      e.printStackTrace();
    }

    try {
      System.out.println(contactService.findByPhoneNumber(wrongNumber));
    } catch (PhoneNumberNotFoundException e) {
      e.printStackTrace();
    }

  }
}

