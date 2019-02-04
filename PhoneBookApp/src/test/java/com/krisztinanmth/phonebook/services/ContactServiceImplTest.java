package com.krisztinanmth.phonebook.services;

import com.krisztinanmth.phonebook.exceptions.BirthdayNotFoundException;
import com.krisztinanmth.phonebook.exceptions.FirstNameNotFoundException;
import com.krisztinanmth.phonebook.exceptions.LastNameNotFoundException;
import com.krisztinanmth.phonebook.exceptions.PhoneNumberNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.naming.NameNotFoundException;

import java.util.ArrayList;
import java.util.List;

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

  @Test
  void findByName_withExistingName() {
    try {
      assertEquals(1, contactService.findByName("Wilkinson").size());
    } catch (NameNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  void findByName_withNonExistingName() {
    try {
      contactService.findByName("Kriszta");
      fail();
    } catch (NameNotFoundException e) {
      assertThat(NameNotFoundException.class);
    }
  }

  @Test
  void findByName_withoutName() {
    try {
      contactService.findByName("");
      fail();
    } catch (NameNotFoundException e) {
      assertThat(NameNotFoundException.class);
    }
  }

  @Test
  void findByName_withNull() {
    try {
      contactService.findByName(null);
      fail();
    } catch (NameNotFoundException e) {
      assertThat(NameNotFoundException.class);
    }
  }

  @Test
  void findByDate_withExistingDate() {
    try {
      contactService.findByDate(19870101, 19890101);
      fail();
    } catch (BirthdayNotFoundException e) {
      assertThat(BirthdayNotFoundException.class);
    }
  }

  @Test
  void findByDate_withNonExistingDate() {
    try {
      contactService.findByDate(20200101, 20400101);
      fail();
    } catch (BirthdayNotFoundException e) {
      assertThat(BirthdayNotFoundException.class);
    }
  }

  @Test
  void findByDate_withNull() {
    try {
      contactService.findByDate(null, null);
      fail();
    } catch (BirthdayNotFoundException e) {
      assertThat(BirthdayNotFoundException.class);
    }
  }

  @Test
  void findByPhoneNumber_withExistingNumber() {
    List<String> phoneNums = new ArrayList<>();
    phoneNums.add("1-202-555-0164");

    try {
      assertEquals(1, contactService.findByPhoneNumber(phoneNums).size());
    } catch (PhoneNumberNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  void findByPhoneNumber_withNonExistingNumber() {
    List<String> wrongNums = new ArrayList<>();
    wrongNums.add("06-70-600-7479");

    try {
      contactService.findByPhoneNumber(wrongNums);
      fail();
    } catch (PhoneNumberNotFoundException e) {
      assertThat(PhoneNumberNotFoundException.class);
    }
  }

  @Test
  void findByPhoneNumber_withEmptyList() {
    List<String> emptyList = new ArrayList<>();

    try {
      contactService.findByPhoneNumber(emptyList);
      fail();
    } catch (PhoneNumberNotFoundException e) {
      assertThat(PhoneNumberNotFoundException.class);
    }
  }
}