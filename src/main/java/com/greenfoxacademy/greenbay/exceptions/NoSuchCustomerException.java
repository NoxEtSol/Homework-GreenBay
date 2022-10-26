package com.greenfoxacademy.greenbay.exceptions;

public class NoSuchCustomerException extends RuntimeException {
  public NoSuchCustomerException(String message) {
    super(message);
  }
}
