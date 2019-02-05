package com.krisztinanmth.phonebook.services;

import com.krisztinanmth.phonebook.exceptions.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.naming.NameNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.fail;

public class ContactServiceImplTest {

  private static ContactService contactService;

  @Autowired
  public ContactServiceImplTest() {
    contactService = new ContactServiceImpl();
  }

  @Test
  public void findByFirstName_withExistingName() {
    try {
      assertEquals(1, contactService.findByFirstName("Gary").size());
    } catch (FirstNameNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void findByFirstName_withNonExistingName() {
    try {
      contactService.findByFirstName("Kriszta");
      fail();
    } catch (FirstNameNotFoundException e) {
      assertThat(FirstNameNotFoundException.class);
    }
  }

  @Test
  public void findByFirstName_withoutName() {
    try {
      contactService.findByFirstName("");
      fail();
    } catch (FirstNameNotFoundException e) {
      assertThat(FirstNameNotFoundException.class);
    }
  }

  @Test
  public void findByFirstName_withNull() {
    try {
      contactService.findByFirstName(null);
      fail();
    } catch (FirstNameNotFoundException e) {
      assertThat(FirstNameNotFoundException.class);
    }
  }

  @Test
  public void findByLastName_withExistingName() {
    try {
      assertEquals(1, contactService.findByLastName("Cunningham").size());
    } catch (LastNameNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void findByLastName_withNonExistingName() {
    try {
      contactService.findByLastName("Kriszta");
      fail();
    } catch (LastNameNotFoundException e) {
      assertThat(LastNameNotFoundException.class);
    }
  }

  @Test
  public void findByLastName_withoutName() {
    try {
      contactService.findByLastName("");
      fail();
    } catch (LastNameNotFoundException e) {
      assertThat(LastNameNotFoundException.class);
    }
  }

  @Test
  public void findByLastName_withNull() {
    try {
      contactService.findByLastName(null);
      fail();
    } catch (LastNameNotFoundException e) {
      assertThat(LastNameNotFoundException.class);
    }
  }

  @Test
  public void findByName_withExistingName() {
    try {
      assertEquals(1, contactService.findByName("Wilkinson").size());
    } catch (NameNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void findByName_withNonExistingName() {
    try {
      contactService.findByName("Kriszta");
      fail();
    } catch (NameNotFoundException e) {
      assertThat(NameNotFoundException.class);
    }
  }

  @Test
  public void findByName_withoutName() {
    try {
      contactService.findByName("");
      fail();
    } catch (NameNotFoundException e) {
      assertThat(NameNotFoundException.class);
    }
  }

  @Test
  public void findByName_withNull() {
    try {
      contactService.findByName(null);
      fail();
    } catch (NameNotFoundException e) {
      assertThat(NameNotFoundException.class);
    }
  }

  @Test
  public void findByDate_withExistingDate() {
    try {
      contactService.findByDate(19870101, 19890101);
      fail();
    } catch (BirthdayNotFoundException e) {
      assertThat(BirthdayNotFoundException.class);
    }
  }

  @Test
  public void findByDate_withNonExistingDate() {
    try {
      contactService.findByDate(20200101, 20400101);
      fail();
    } catch (BirthdayNotFoundException e) {
      assertThat(BirthdayNotFoundException.class);
    }
  }

  @Test
  public void findByDate_withNull() {
    try {
      contactService.findByDate(null, null);
      fail();
    } catch (BirthdayNotFoundException e) {
      assertThat(BirthdayNotFoundException.class);
    }
  }

  @Test
  public void findByPhoneNumber_withExistingNumber() {
    List<String> phoneNums = new ArrayList<>();
    phoneNums.add("1-202-555-0164");

    try {
      assertEquals(1, contactService.findByPhoneNumber(phoneNums).size());
    } catch (PhoneNumberNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void findByPhoneNumber_withNonExistingNumber() {
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
  public void findByPhoneNumber_withEmptyList() {
    List<String> emptyList = new ArrayList<>();

    try {
      contactService.findByPhoneNumber(emptyList);
      fail();
    } catch (PhoneNumberNotFoundException e) {
      assertThat(PhoneNumberNotFoundException.class);
    }
  }

  @Test
  public void findByAddress_withExistingAddress() {
    try {
      assertEquals(3, contactService.findByAddress("California").size());
    } catch (AddressNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void findByAddress_withNonExistingAddress() {
    try {
      contactService.findByAddress("Hungary");
      fail();
    } catch (AddressNotFoundException e) {
      assertThat(AddressNotFoundException.class);
    }
  }

  @Test
  public void findByAddress_withoutParameter() {
    try {
      contactService.findByAddress("");
      fail();
    } catch (AddressNotFoundException e) {
      assertThat(AddressNotFoundException.class);
    }
  }

  @Test
  public void findByAddress_withNull() {
    try {
      contactService.findByAddress(null);
      fail();
    } catch (AddressNotFoundException e) {
      assertThat(AddressNotFoundException.class);
    }
  }
}