package com.krisztinanmth.phonebook.services;

import static junit.framework.TestCase.assertEquals;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.krisztinanmth.phonebook.exceptions.AddressNotFoundException;
import com.krisztinanmth.phonebook.exceptions.BirthdayNotFoundException;
import com.krisztinanmth.phonebook.exceptions.ContactNotProvidedException;
import com.krisztinanmth.phonebook.exceptions.FirstNameNotFoundException;
import com.krisztinanmth.phonebook.exceptions.LastNameNotFoundException;
import com.krisztinanmth.phonebook.exceptions.PhoneNumberNotFoundException;
import com.krisztinanmth.phonebook.models.Address;
import com.krisztinanmth.phonebook.models.Contact;
import com.krisztinanmth.phonebook.models.Contact.Field;

public class JSONContactServiceTest {

	private static ContactService contactService;

	@Autowired
	public JSONContactServiceTest() {
		contactService = new JSONContactService("src/main/resources/contacts.json");
	}

	@Test
	public void createContact_with_Null() {
		int sizeOfContactList = contactService.getAllContacts().size();
		contactService.createNewContact(null);
		List<Contact> contactList = contactService.getAllContacts();
		assertThat(contactList, hasSize(sizeOfContactList));
	}
	
	@Test
	public void createNewContact_withNonExistingContact() {
		List<String> phoneNums = new ArrayList<>();
		phoneNums.add("06-70-600-7479");
		Address ad = new Address("USA", "1087", "New York", "Red Bull Road");
		List<Address> address = new ArrayList<>();
		address.add(ad);
		Contact contact = new Contact("Suzie", "Doe", "1988-12-11", phoneNums, address);
		List<Contact> contactList = contactService.getAllContacts();
		int sizeOfList = contactList.size();
		contactService.createNewContact(contact);
		contactList = contactService.getAllContacts();
		assertThat(contactList, hasSize(sizeOfList + 1));
	}

	@Test(expected = ContactNotProvidedException.class)
	public void bulkCreate_withEmptyList() {
		contactService.bulkCreate(new ArrayList<>());
	}
	
	@Test
	public void deleteContact_with_Null() {
		int cListSize = contactService.getAllContacts().size();
		contactService.deleteContact(null);
		List<Contact> contactList = contactService.getAllContacts();
		assertThat(contactList, hasSize(cListSize));
	}
	
	@Test
	public void deleteContact_with_EmptyStringId() {
		int cListSize = contactService.getAllContacts().size();
		contactService.deleteContact("");
		List<Contact> contactList = contactService.getAllContacts();
		assertThat(contactList, hasSize(cListSize));
	}
	
	@Test
	public void deleteContact_with_ExistingContact() {
		int numberOfContactsInList = contactService.getAllContacts().size();
		contactService.deleteContact(contactService.findContactByName("Gerhard Fleming").getId());
		assertThat(contactService.getAllContacts(), hasSize(numberOfContactsInList - 1));
	}

	@Test(expected = ContactNotProvidedException.class)
	public void bulkDelete_withEmptyList() {
		contactService.bulkDelete(new ArrayList<>());
	}

	@Test(expected = ContactNotProvidedException.class)
	public void updateContact_withNullMap() throws ContactNotProvidedException {
		Contact johnnyContact;
		johnnyContact = contactService.findListOfContactsByName("John Doe").get(0);
		contactService.updateContact(johnnyContact.getId(), null);
	}

	@Test
	public void updateContact_with_idNull() {
		Map<Field, Object> updatedContactMap = new HashMap<Field, Object>();
		updatedContactMap.put(Contact.Field.FIRST_NAME, "lulu");
		try {
			contactService.updateContact(null, updatedContactMap);
			fail();
		} catch (ContactNotProvidedException e) {
			assertThat(ContactNotProvidedException.class);
		}
	}
	
	@Test
	public void updateContact_with_ProvideMap_and_Id() {
		Map<Field, Object> updateContactMap = new HashMap<Contact.Field, Object>();
		List<String> phoneNumList = new ArrayList<String>();
		phoneNumList.add("555-555");
		List<Address> addressList = new ArrayList<Address>();
		Address address = new Address("country", "zipcode", "city", "street");
		addressList.add(address);
		updateContactMap.put(Contact.Field.FIRST_NAME, "Jose");
		updateContactMap.put(Contact.Field.LAST_NAME, "Jesus");
		updateContactMap.put(Contact.Field.DATE_OF_BIRTH, "1991-12-11");
		updateContactMap.put(Contact.Field.PHONE_NUMBER, phoneNumList);
		updateContactMap.put(Contact.Field.ADDRESS, addressList);
		Contact contactToUpdate = contactService.findByLastName("Cunningham").get(0);
		contactService.updateContact(contactToUpdate.getId(), updateContactMap);
		String resultFirst = contactToUpdate.getFirstName();
		assertEquals(resultFirst, "Jose");
		
		String resultLast = contactToUpdate.getLastName();
		assertEquals(resultLast, "Jesus");
		
		String resultDOB = contactToUpdate.getDateOfBirth();
		assertEquals(resultDOB, "1991-12-11");
		
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
			assertEquals(1, contactService.findByLastName("Jesus").size());
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
}
