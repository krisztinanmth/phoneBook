package com.krisztinanmth.phonebook;

import com.krisztinanmth.phonebook.exceptions.AddressNotFoundException;
import com.krisztinanmth.phonebook.exceptions.FirstNameNotFoundException;
import com.krisztinanmth.phonebook.services.ContactService;
import com.krisztinanmth.phonebook.services.ContactServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PhoneBookApplication {

  private static ContactService contactService;

  @Autowired
  public PhoneBookApplication() {
    this.contactService = new ContactServiceImpl("src/main/resources/contacts.json");
  }

  public static void main(String[] args) {
    SpringApplication.run(PhoneBookApplication.class, args);
    contactService.showAllFirstNames();
    try {
      System.out.println(contactService.findByFirstName("Gary"));
    } catch (FirstNameNotFoundException e) {
      e.printStackTrace();
    }

    try {
      System.out.println(contactService.findByFirstName(""));
    } catch (FirstNameNotFoundException e) {
      e.printStackTrace();
    }
    try {
      System.out.println(contactService.findByAddress("351 Red Bud Lane"));
    } catch (AddressNotFoundException e) {
      e.printStackTrace();
    }
    try {
      System.out.println(contactService.findByAddress(""));
    } catch (AddressNotFoundException e) {
      e.printStackTrace();
    }
    try {
      System.out.println(contactService.findByAddress("3 Andrassy"));
    } catch (AddressNotFoundException e) {
      e.printStackTrace();
    }
    try {
      System.out.println(contactService.findByAddress(null));
    } catch (AddressNotFoundException e) {
      e.printStackTrace();
    }
  }

}

