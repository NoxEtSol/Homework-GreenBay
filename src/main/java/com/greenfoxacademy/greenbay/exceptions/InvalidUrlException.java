package com.greenfoxacademy.greenbay.exceptions;

public class InvalidUrlException extends RuntimeException {
  public InvalidUrlException(String message) {
    super(message);
  }
}
