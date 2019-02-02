package com.krisztinanmth.phonebook.services;

import com.krisztinanmth.phonebook.exceptions.FirstNameNotFoundException;
import com.krisztinanmth.phonebook.exceptions.LastNameNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ContactServiceImplTest {

  private static ContactService contactService;

  @Autowired
  public ContactServiceImplTest() {
    contactService = new ContactServiceImpl();
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
      contactService.findByFirstName("");
      fail();
    } catch (FirstNameNotFoundException e) {
      assertThat(FirstNameNotFoundException.class);
    }
  }

  @Test
  void findByFirstName_withNull() {
    try {
      contactService.findByFirstName(null);
      fail();
    } catch (FirstNameNotFoundException e) {
      assertThat(FirstNameNotFoundException.class);
    }
  }

  @Test
  void findByLastName_withExistingName() {
    try {
      assertEquals(1, contactService.findByLastName("Cunningham").size());
    } catch (LastNameNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  void findByLastName_withNonExistingName() {
    try {
      contactService.findByLastName("Kriszta");
      fail();
    } catch (LastNameNotFoundException e) {
      assertThat(LastNameNotFoundException.class);
    }
  }

  @Test
  void findByLastName_withoutName() {
    try {
      contactService.findByLastName("");
      fail();
    } catch (LastNameNotFoundException e) {
      assertThat(LastNameNotFoundException.class);
    }
  }

  @Test
  void findByLastName_withNull() {
    try {
      contactService.findByLastName(null);
      fail();
    } catch (LastNameNotFoundException e) {
      assertThat(LastNameNotFoundException.class);
    }
  }
}