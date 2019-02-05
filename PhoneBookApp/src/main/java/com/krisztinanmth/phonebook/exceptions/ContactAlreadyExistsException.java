package com.krisztinanmth.phonebook.exceptions;

public class ContactAlreadyExistsException extends Exception {
  public ContactAlreadyExistsException(String message) {
    super(message);
  }
}
