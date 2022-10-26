package com.greenfoxacademy.greenbay.exceptions;

public class BidTooLowException extends RuntimeException {
  public BidTooLowException(String message) {
    super(message);
  }
}
