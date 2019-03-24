package com.krisztinanmth.phonebook;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.krisztinanmth.phonebook.models.Address;
import com.krisztinanmth.phonebook.models.Contact;
import com.krisztinanmth.phonebook.services.ContactService;
import com.krisztinanmth.phonebook.services.ContactServiceImpl;


@SpringBootApplication
public class PhoneBookApplication {

  private static ContactService contactService;

  @Autowired
  public PhoneBookApplication() {
    contactService = new ContactServiceImpl("src/main/resources/contacts.json");
  }


  public static void main(String[] args) {


  }
}

