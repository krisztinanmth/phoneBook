package com.krisztinanmth.phonebook.exceptions;

public class BirthdayNotFoundException extends RuntimeException {

  public BirthdayNotFoundException(String message) {
    super(message);
  }
}
