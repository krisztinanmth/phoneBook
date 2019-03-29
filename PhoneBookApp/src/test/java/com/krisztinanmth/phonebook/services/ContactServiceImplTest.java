package com.krisztinanmth.phonebook.services;

import static junit.framework.TestCase.assertEquals;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NameNotFoundException;

import org.hamcrest.collection.IsEmptyCollection;
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
import com.krisztinanmth.phonebook.models.Contact.Field;

public class ContactServiceImplTest {

	private static ContactService contactService;

	@Autowired
	public ContactServiceImplTest() {
		contactService = new ContactServiceImpl("src/main/resources/contacts.json");
	}

	@Test
	public void createNewContact_withNonExistingContact() {
		List<String> phoneNums = new ArrayList<>();
		phoneNums.add("06-70-600-7479");
		Address ad = new Address("USA", "1087", "New York", "Red Bull Road");
		List<Address> address = new ArrayList<>();
		address.add(ad);
//    LocalDate datOfBirth = LocalDate.of(1989, 10, 02);
		Contact contact = new Contact("Suzie", "Doe", "1988-12-11", phoneNums, address);
		List<Contact> contactList = contactService.getAllContacts();
		int sizeOfList = contactList.size();
		contactService.createNewContact(contact);
		contactList = contactService.getAllContacts();
		assertThat(contactList, hasSize(sizeOfList + 1));
	}
	
	@Test
	public void deleteContact_with_ExistingContact() {
		int numberOfContactsInList = contactService.getAllContacts().size();
		contactService.deleteContact(contactService.findContactByName("Gerhard Fleming").getId());
		assertThat(contactService.getAllContacts(), hasSize(numberOfContactsInList -1 ));
	}

	@Test(expected = ContactNotProvidedException.class)
	public void bulkCreate_withEmptyList() {
		contactService.bulkCreate(new ArrayList<>());
	}

	@Test(expected = ContactNotProvidedException.class)
	public void bulkDelete_withEmptyList() {
		contactService.bulkDelete(new ArrayList<>());
	}

	@Test(expected = ContactNotProvidedException.class)
	public void updateContact_withNull() throws ContactNotProvidedException {
		Contact johnnyContact;
		johnnyContact = contactService.findListOfContactsByName("John Doe").get(0);
		contactService.updateContact(johnnyContact.getId(), null);
	}

	@Test(expected = ContactNotProvidedException.class)
	public void updateContact_withNoID() {
		contactService.updateContact(null, null);
	}

	@Test
	public void findByFirstName_withExistingName() {
		assertThat(new ArrayList<>(), IsEmptyCollection.empty());
	}

	@Test
	public void findByFirstName_withNonExistingName() {
		assertThat(new ArrayList<>(), IsEmptyCollection.empty());
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
		assertThat(new ArrayList<>(), IsEmptyCollection.empty());
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
		assertEquals(1, contactService.findListOfContactsByName("Wilkinson").size());
	}

//	@Test
//	public void findByDate_withExistingDate() {
//		try {
////			LocalDate fromDate = LocalDate.of(1987-01-01);
////			LocalDate toDate = LocalDate.of(1989, 01, 01);
//			contactService.findByDate("1987-01-01", "1989-01-01");
//			fail();
//		} catch (BirthdayNotFoundException e) {
//			assertThat(BirthdayNotFoundException.class);
//		}
//	}

//	@Test
//	public void findByDate_withNonExistingDate() {
//		try {
////			LocalDate fromDate = LocalDate.of(2060, 01, 01);
////			LocalDate toDate = LocalDate.of(2080, 01, 01);
//			contactService.findByDate("2060-01-01", "2080-01-01");
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
		phoneNums.add("1-202-555-0144");

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
		assertThat(new ArrayList<>(), IsEmptyCollection.empty());
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
	public void findByAddress_withNonExistingAddress() {
		assertThat(new ArrayList<>(), IsEmptyCollection.empty());
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
	
//	@Test
//	public void updateContact_with_idNull() {
//		Map<Field, Object> updatedContactMap = new HashMap<Field, Object>();
//		updatedContactMap.put(Contact.Field.FIRST_NAME, "lulu");
//		
//		try {
//			contactService.updateContact(null, updatedContactMap);
//			fail();
//		} catch (ContactNotProvidedException e) {
//			assertThat(ContactNotProvidedException.class);
//		}
//	}
}














