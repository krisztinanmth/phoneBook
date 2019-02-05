package com.krisztinanmth.phonebook;

import com.krisztinanmth.phonebook.exceptions.ContactNotFoundException;
import com.krisztinanmth.phonebook.exceptions.ContactNotProvidedException;
import com.krisztinanmth.phonebook.models.Address;
import com.krisztinanmth.phonebook.models.Contact;
import com.krisztinanmth.phonebook.services.ContactService;
import com.krisztinanmth.phonebook.services.ContactServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class PhoneBookApplication {

  private static ContactService contactService;

  @Autowired
  public PhoneBookApplication() {
    contactService = new ContactServiceImpl();
  }


  public static void main(String[] args) {

    SpringApplication.run(PhoneBookApplication.class, args);


    System.out.println();
    System.out.println("ORIGINAL CONTACTS");
    System.out.println();
    try {
      contactService.showAllContacts();
    } catch (ContactNotFoundException e) {
      e.printStackTrace();
    }
    System.out.println("===================================");
    System.out.println();


    List<String> phoneNum = new ArrayList<>();
    phoneNum.add("1-202-555-0144");
    Address ad = new Address("Connecticut", "06103", "Hartford", "351 Red Bud Lane");
    List<Address> address = new ArrayList<>();
    address.add(ad);

    Contact testContact1 = new Contact("Dave", "Roberts", "19870101", phoneNum, address);
    Contact testContact2 = new Contact("Gary", "Fleming","19620620", phoneNum, address);
    Contact testContact3 = new Contact("Karen", "free", "19900508", phoneNum, address);
    Contact tesContact4 = new Contact("Tammy", "Miller", "19820609", phoneNum, address);

    List<Contact> contacts = new ArrayList<>();
    contacts.add(testContact1);
    contacts.add(testContact2);
    contacts.add(testContact3);
    contacts.add(tesContact4);

// for trying createNewContact:

//    try {
//      contactService.createNewContact(testContact1);
//    } catch (ContactNotProvidedException e) {
//      System.err.println(e.getMessage());
//    } catch (ContactAlreadyExistsException e) {
//      System.err.println(e.getMessage());
//    }


// for trying bulkCreate:

//    try {
//      contactService.bulkCreate(contacts);
//    } catch (ContactAlreadyExistsException e) {
//      System.err.println(e.getMessage());
//    } catch (ContactNotProvidedException e) {
//      System.err.println(e.getMessage());
//    }
//
//    System.out.println();
//    System.out.println("CONTACTS after BULK CREATE");
//    System.out.println();
//    try {
//      contactService.showAllContacts();
//    } catch (ContactNotFoundException e) {
//      e.printStackTrace();
//    }
//    System.out.println("===================================***************===============****");

// for trying deleteContact:

//    try {
//      contactService.deleteContact("");
//    } catch (ContactNotProvidedException e) {
//      System.err.println(e.getMessage());
//    } catch (ContactNotFoundException e) {
//      System.err.println(e.getMessage());
//    }


// for trying bulkDelete:

//    try {
//      contactService.bulkDelete(contacts);
//    } catch (ContactNotProvidedException e) {
//      System.err.println(e.getMessage());
//    }

    try {
      contactService.updateContact("Karen free", null);
    } catch (ContactNotProvidedException e) {
      System.err.println(e.getMessage());
    }


    System.out.println();
    System.out.println("CONTACTS after UPDATE");
    System.out.println();
    try {
      contactService.showAllContacts();
    } catch (ContactNotFoundException e) {
      e.printStackTrace();
    }
    System.out.println("===================================***************===============");



  }
}

