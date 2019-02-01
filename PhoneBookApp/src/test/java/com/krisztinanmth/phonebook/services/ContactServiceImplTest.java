package com.krisztinanmth.phonebook.services;

import com.krisztinanmth.phonebook.exceptions.FirstNameNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ContactServiceImplTest {

  private static ContactService contactService;

  @Autowired
  public ContactServiceImplTest() {
    this.contactService = new ContactServiceImpl("src/main/resources/contacts.json");
  }

  @Test
  void findByFirstName_withExistingName() {
//    List<Contact> contactList = new ArrayList<>();
//    List<String> phoneNumbers = new ArrayList<>();
//    String phone = "1-202-555-0144";
//    phoneNumbers.add(phone);
//
//    contactList.add(new Contact("Gary", "Fleming", "19620620", phoneNumbers, Arrays.asList(new Address("Connecticut", "06103", "Hartford", "351 Red Bud Lane"))));
    try {
      assertEquals(1, contactService.findByFirstName("Gary").size());
    } catch (FirstNameNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  void findByFirstName_withNonExistingName() {
    try {
      assertEquals(0, contactService.findByFirstName("Kriszta").size());
    } catch (FirstNameNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  void findByFirstName_withoutName() {

    try {
      contactService.findByFirstName("").size();
      fail();
    } catch (FirstNameNotFoundException e) {
      assertThat(FirstNameNotFoundException.class);
    }
  }

  @Test
  void findByFirstName_withNull() {

    try {
      contactService.findByFirstName(null).size();
      fail();
    } catch (FirstNameNotFoundException e) {
      assertThat(FirstNameNotFoundException.class);
    }
  }
}