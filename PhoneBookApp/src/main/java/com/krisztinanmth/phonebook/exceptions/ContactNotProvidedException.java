package com.krisztinanmth.phonebook.exceptions;

public class ContactNotProvidedException extends RuntimeException {

  public ContactNotProvidedException(String message) {
    super(message);
  }
}
