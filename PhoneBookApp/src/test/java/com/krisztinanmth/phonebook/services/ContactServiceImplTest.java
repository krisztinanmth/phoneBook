package com.krisztinanmth.phonebook.services;

import static junit.framework.TestCase.assertEquals;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NameNotFoundException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.krisztinanmth.phonebook.exceptions.AddressNotFoundException;
import com.krisztinanmth.phonebook.exceptions.BirthdayNotFoundException;
import com.krisztinanmth.phonebook.exceptions.ContactAlreadyExistsException;
import com.krisztinanmth.phonebook.exceptions.ContactNotFoundException;
import com.krisztinanmth.phonebook.exceptions.ContactNotProvidedException;
import com.krisztinanmth.phonebook.exceptions.FirstNameNotFoundException;
import com.krisztinanmth.phonebook.exceptions.LastNameNotFoundException;
import com.krisztinanmth.phonebook.exceptions.PhoneNumberNotFoundException;
import com.krisztinanmth.phonebook.models.Address;
import com.krisztinanmth.phonebook.models.Contact;

public class ContactServiceImplTest {

	private static ContactService contactService;

	@Autowired
	public ContactServiceImplTest() {
		contactService = new ContactServiceImpl("src/main/resources/contacts.json");
	}

	@Test(expected = ContactNotProvidedException.class)
	public void createNewContact_WithNull() {
		contactService.createNewContact(null);
	}

//	LocalDate fromDate = LocalDate.of(1987, 01, 01);
//	@Test(expected = ContactAlreadyExistsException.class)
//	public void createNewContact_withExistingContact() {
//		List<String> phoneNums = new ArrayList<>();
//		phoneNums.add("1-202-555-0164");
//		Address ad = new Address("California", "92614", "Irvine", "950 Joy Lane");
//		List<Address> address = new ArrayList<>();
//		address.add(ad);
//		LocalDate dateOfBirth = LocalDate.of(1989, 10, 02);
//		Contact contact = new Contact("John", "Doe", dateOfBirth, phoneNums, address);
//		contactService.createNewContact(contact);
//	}

//  @Test
  public void createNewContact_withNonExistingContact() {
    List<String> phoneNums = new ArrayList<>();
    phoneNums.add( "06-70-600-7479" );
    Address ad = new Address("USA", "1087", "New York", "Red Bull Road");
    List<Address> address = new ArrayList<>();
    address.add(ad);
    LocalDate datOfBirth = LocalDate.of(1989, 10, 02);
    Contact contact = new Contact("Suzie", "Doe", datOfBirth, phoneNums, address);
    contactService.createNewContact(contact);
  }

	@Test(expected = ContactNotProvidedException.class)
	public void bulkCreate_withEmptyList() {
		contactService.bulkCreate(new ArrayList<>());
	}

	@Test(expected = ContactNotProvidedException.class)
	public void deleteContact_with_null() {
		contactService.deleteContact(null);
	}

//	@Test(expected = ContactNotFoundException.class)
//	public void deleteContact_withContactNotInList() {
//		contactService.deleteContact("kriszta");
//	}

	@Test(expected = ContactNotProvidedException.class)
	public void bulkDelete_withEmptyList() {
		contactService.bulkDelete(new ArrayList<>());
	}

	@Test(expected = ContactNotFoundException.class)
	public void bulkDelete_withNonExistingContact() {
		List<String> phoneNums = new ArrayList<>();
		phoneNums.add("06-70-600-7479");
		Address ad = new Address("USA", "1087", "New York", "Red Bull Road");
		List<Address> address = new ArrayList<>();
		address.add(ad);
		LocalDate birthDay = LocalDate.of(1989, 10, 02);
		Contact contact = new Contact("Suzie", "Doe", birthDay, phoneNums, address);
		List<Contact> contactsToDelete = new ArrayList<>();
		contactsToDelete.add(contact);
		contactService.bulkDelete(contactsToDelete);
	}

	@Test(expected = ContactNotProvidedException.class)
	public void updateContact_withNull() throws ContactNotProvidedException {
		Contact johnnyContact;
		try {
			johnnyContact = contactService.findListOfContactsByName("John Doe").get(0);
			contactService.updateContact(johnnyContact.getId(), null);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(expected = ContactNotProvidedException.class)
	public void updateContact_withNoID() {
		contactService.updateContact(null, null);
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
			assertEquals(1, contactService.findListOfContactsByName("Wilkinson").size());
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void findByName_withNonExistingName() {
		try {
			contactService.findListOfContactsByName("Kriszta");
			fail();
		} catch (NameNotFoundException e) {
			assertThat(NameNotFoundException.class);
		}
	}

	@Test
	public void findByName_withoutName() {
		try {
			contactService.findListOfContactsByName("");
			fail();
		} catch (NameNotFoundException e) {
			assertThat(NameNotFoundException.class);
		}
	}

	@Test
	public void findByName_withNull() {
		try {
			contactService.findListOfContactsByName(null);
			fail();
		} catch (NameNotFoundException e) {
			assertThat(NameNotFoundException.class);
		}
	}

//	@Test
//	public void findByDate_withExistingDate() {
//		try {
//			LocalDate fromDate = LocalDate.of(1987-01-01);
//			LocalDate toDate = LocalDate.of(1989, 01, 01);
//			contactService.findByDate(fromDate, toDate);
//			fail();
//		} catch (BirthdayNotFoundException e) {
//			assertThat(BirthdayNotFoundException.class);
//		}
//	}

//	@Test
//	public void findByDate_withNonExistingDate() {
//		try {
//			LocalDate fromDate = LocalDate.of(2060, 01, 01);
//			LocalDate toDate = LocalDate.of(2080, 01, 01);
//			contactService.findByDate(fromDate, toDate);
//			fail();
//		} catch (BirthdayNotFoundException e) {
//			assertThat(BirthdayNotFoundException.class);
//		}
//	}

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
		phoneNums.add("1-555-600-0164");

		try {
			assertEquals(1, contactService.findByPhoneNumber(phoneNums).size());
		} catch (PhoneNumberNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void findByPhoneNumber_withNonExistingNumber() {
		List<String> wrongNums = new ArrayList<>();
		wrongNums.add("06-70-600-7579");

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

//	@Test
//	public void findByAddress_withExistingAddress() {
//		try {
//			assertEquals(3, contactService.findByAddress("California").size());
//		} catch (AddressNotFoundException e) {
//			e.printStackTrace();
//		}
//	}

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