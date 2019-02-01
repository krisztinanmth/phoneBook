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
    try {
      assertEquals(1, contactService.findByFirstName("Gary").size());
    } catch (FirstNameNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  void findByFirstName_withNonExistingName() {
    try {
      contactService.findByFirstName("Kriszta");
      fail();
    } catch (FirstNameNotFoundException e) {
      assertThat(FirstNameNotFoundException.class);
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