package com.krisztinanmth.phonebook.models;

import java.util.List;

public class Contact {

  // create variable Title ... try using Enums... search after how ...
  private String firstName;
  private String lastName;
  private String dateOfBirth;
  private List<String> phoneNumber;
  private List<Address> address;


  public Contact() {
  }

  public Contact(String firstName, String lastName, String dateOfBirth, List<String> phoneNumber, List<Address> address) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.phoneNumber = phoneNumber;
    this.address = address;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public List<String> getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(List<String> phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public List<Address> getAddress() {
    return address;
  }

  public void setAddress(List<Address> address) {
    this.address = address;
  }

  @Override
  public String toString() {
    String formatted = String.format("%s %s birth-date: %s phone: %s address: %s", firstName, lastName, dateOfBirth, phoneNumber, address.toString());
    return formatted;
  }
}
