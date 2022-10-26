package com.greenfoxacademy.greenbay.exceptions;

public class CustomerAlreadyExistsException extends RuntimeException {
  public CustomerAlreadyExistsException(String message) {
    super(message);
  }
}
