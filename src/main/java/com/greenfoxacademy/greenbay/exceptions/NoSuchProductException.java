package com.greenfoxacademy.greenbay.exceptions;

public class NoSuchProductException extends RuntimeException {
  public NoSuchProductException(String message) {
    super(message);
  }
}
