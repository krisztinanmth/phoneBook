package com.krisztinanmth.phonebook.services;

import com.google.gson.Gson;
import com.krisztinanmth.phonebook.models.Address;
import com.krisztinanmth.phonebook.models.Contact;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class JSONServiceImpl implements JSONService {

	private JSONParser parser;
	private List<Contact> contacts;
	private String jsonPath;

	public JSONServiceImpl(String jsonPath) {
		contacts = new ArrayList<>();
		parser = new JSONParser();
		this.jsonPath = jsonPath;
	}

	@Override
	public List<Contact> readFromJSON() {
		Object object;
		try {
			object = parser.parse(new FileReader(jsonPath));
			JSONArray jsonArray = (JSONArray) object;

			for (Object o : jsonArray) {
				JSONObject jo = (JSONObject) o;
				contacts.add(createContactFromJSONObject(jo));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contacts;
	}

	@Override
	public Contact createContactFromJSONObject(JSONObject jo) {
		String firstName = (String) jo.get("firstName");
		String lastName = (String) jo.get("lastName");
		
		
		
		JSONObject dateOfBirthJsonObject = (JSONObject) jo.get("dateOfBirth");
		String jsonYear = String.valueOf(dateOfBirthJsonObject.get("year"));
		String jsonMonth = String.valueOf(dateOfBirthJsonObject.get("month"));
		String jsonDay = String.valueOf(dateOfBirthJsonObject.get("day"));
		String dateOfBirthString = String.format("%s-%s-%s", jsonYear, jsonMonth, jsonDay);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
		LocalDate dateOfBirth = LocalDate.parse(dateOfBirthString, formatter);
		
		List<String> phoneNumber = (List<String>) jo.get("phoneNumber");
		List<Address> addresses = new ArrayList<>();

		JSONArray addressList = (JSONArray) jo.get("address");
		for (Object ad : addressList) {
			Address address = new Address();
			address.setCountry((String) ((JSONObject) ad).get("country"));
			address.setZipCode((String) ((JSONObject) ad).get("zipCode"));
			address.setCity((String) ((JSONObject) ad).get("city"));
			address.setStreet((String) ((JSONObject) ad).get("street"));
			addresses.add(address);
		}
		return new Contact(firstName, lastName, dateOfBirth, phoneNumber, addresses);
	}

	@Override
	public void writeListOfContactsIntoJSON(List<Contact> newContacts) {
		String json = new Gson().toJson(newContacts);
		try (FileWriter file = new FileWriter(jsonPath)) {
			file.write(json);
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
