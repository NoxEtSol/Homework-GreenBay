package com.greenfoxacademy.greenbay.exceptions;

public class ProductAlreadyBoughtException extends RuntimeException {
  public ProductAlreadyBoughtException(String message) {
    super(message);
  }
}
