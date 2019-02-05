package com.krisztinanmth.phonebook.exceptions;

public class AddressNotFoundException extends RuntimeException {

  public AddressNotFoundException(String message) {
    super(message);
  }
}
