package com.krisztinanmth.phonebook.exceptions;

public class ContactAlreadyExistsException extends RuntimeException {
  public ContactAlreadyExistsException(String message) {
    super(message);
  }
}
