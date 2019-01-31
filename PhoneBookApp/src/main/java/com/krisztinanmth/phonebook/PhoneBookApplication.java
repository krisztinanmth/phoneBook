package com.krisztinanmth.phonebook;

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
  }

}

