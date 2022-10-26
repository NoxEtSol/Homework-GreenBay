package com.greenfoxacademy.greenbay.advice;

import static com.greenfoxacademy.greenbay.config.ExceptionMessageConfiguration.ACCESS_DENIED_EXCEPTION_MESSAGE;
import static com.greenfoxacademy.greenbay.config.ExceptionMessageConfiguration.BID_TOO_LOW_EXCEPTION_MESSAGE;
import static com.greenfoxacademy.greenbay.config.ExceptionMessageConfiguration.CUSTOMER_ALREADY_EXISTS_EXCEPTION_MESSAGE;
import static com.greenfoxacademy.greenbay.config.ExceptionMessageConfiguration.INCORRECT_PASSWORD_EXCEPTION_MESSAGE;
import static com.greenfoxacademy.greenbay.config.ExceptionMessageConfiguration.INSUFFICIENT_FUNDS_EXCEPTION_MESSAGE;
import static com.greenfoxacademy.greenbay.config.ExceptionMessageConfiguration.INVALID_INPUT_EXCEPTION_MESSAGE;
import static com.greenfoxacademy.greenbay.config.ExceptionMessageConfiguration.INVALID_URL_EXCEPTION_MESSAGE;
import static com.greenfoxacademy.greenbay.config.ExceptionMessageConfiguration.ITEM_ALREADY_BOUGHT_EXCEPTION_MESSAGE;
import static com.greenfoxacademy.greenbay.config.ExceptionMessageConfiguration.METHOD_ARGUMENT_NOT_VALID_EXCEPTION_MESSAGE;
import static com.greenfoxacademy.greenbay.config.ExceptionMessageConfiguration.NO_SUCH_CUSTOMER_EXCEPTION_MESSAGE;
import static com.greenfoxacademy.greenbay.config.ExceptionMessageConfiguration.NO_SUCH_ITEM_EXCEPTION_MESSAGE;
import static com.greenfoxacademy.greenbay.config.ExceptionMessageConfiguration.REQUIRED_BODY_MISSING_EXCEPTION_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.greenfoxacademy.greenbay.exceptions.AccessDeniedException;
import com.greenfoxacademy.greenbay.exceptions.BidTooLowException;
import com.greenfoxacademy.greenbay.exceptions.CustomerAlreadyExistsException;
import com.greenfoxacademy.greenbay.exceptions.IncorrectPasswordException;
import com.greenfoxacademy.greenbay.exceptions.InsufficientFundsException;
import com.greenfoxacademy.greenbay.exceptions.InvalidInputException;
import com.greenfoxacademy.greenbay.exceptions.InvalidUrlException;
import com.greenfoxacademy.greenbay.exceptions.NoSuchCustomerException;
import com.greenfoxacademy.greenbay.exceptions.NoSuchProductException;
import com.greenfoxacademy.greenbay.exceptions.ProductAlreadyBoughtException;
import com.greenfoxacademy.greenbay.models.DTOs.ErrorResponseDto;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(CustomerAlreadyExistsException.class)
  @ResponseStatus(value = CONFLICT)
  public ErrorResponseDto handleCustomerAlreadyExistsException(CustomerAlreadyExistsException e) {
    return new ErrorResponseDto(CUSTOMER_ALREADY_EXISTS_EXCEPTION_MESSAGE, e.getMessage());
  }

  @ExceptionHandler(IncorrectPasswordException.class)
  @ResponseStatus(value = UNAUTHORIZED)
  public ErrorResponseDto handleIncorrectPasswordException(IncorrectPasswordException e) {
    return new ErrorResponseDto(INCORRECT_PASSWORD_EXCEPTION_MESSAGE, e.getMessage());
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(value = FORBIDDEN)
  public ErrorResponseDto handleAccessDeniedException(AccessDeniedException e) {
    return new ErrorResponseDto(ACCESS_DENIED_EXCEPTION_MESSAGE, e.getMessage());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(value = NOT_ACCEPTABLE)
  public ErrorResponseDto handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    return new ErrorResponseDto(REQUIRED_BODY_MISSING_EXCEPTION_MESSAGE, e.getMessage());
  }

  @ExceptionHandler(NoSuchCustomerException.class)
  @ResponseStatus(value = NOT_FOUND)
  public ErrorResponseDto handleNoSuchCustomerException(NoSuchCustomerException e) {
    return new ErrorResponseDto(NO_SUCH_CUSTOMER_EXCEPTION_MESSAGE, e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(value = NOT_ACCEPTABLE)
  public ErrorResponseDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    return new ErrorResponseDto(METHOD_ARGUMENT_NOT_VALID_EXCEPTION_MESSAGE, e.getMessage());
  }

  @ExceptionHandler(InvalidUrlException.class)
  @ResponseStatus(value = NOT_ACCEPTABLE)
  public ErrorResponseDto handleInvalidUrlException(InvalidUrlException e) {
    return new ErrorResponseDto(INVALID_URL_EXCEPTION_MESSAGE, e.getMessage());
  }

  @ExceptionHandler(NoSuchProductException.class)
  @ResponseStatus(value = NOT_FOUND)
  public ErrorResponseDto handleNoSuchItemException(NoSuchProductException e) {
    return new ErrorResponseDto(NO_SUCH_ITEM_EXCEPTION_MESSAGE, e.getMessage());
  }

  @ExceptionHandler(InvalidInputException.class)
  @ResponseStatus(value = NOT_ACCEPTABLE)
  public ErrorResponseDto handleInvalidInputException(InvalidInputException e) {
    return new ErrorResponseDto(INVALID_INPUT_EXCEPTION_MESSAGE, e.getMessage());
  }

  @ExceptionHandler(NumberFormatException.class)
  @ResponseStatus(value = NOT_ACCEPTABLE)
  public ErrorResponseDto handleNumberFormatException(NumberFormatException e) {
    return new ErrorResponseDto(INVALID_INPUT_EXCEPTION_MESSAGE, e.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(value = NOT_ACCEPTABLE)
  public ErrorResponseDto handleIllegalArgumentException(IllegalArgumentException e) {
    return new ErrorResponseDto(INVALID_INPUT_EXCEPTION_MESSAGE, e.getMessage());
  }

  @ExceptionHandler(ProductAlreadyBoughtException.class)
  @ResponseStatus(value = CONFLICT)
  public ErrorResponseDto handleItemAlreadyBoughtException(ProductAlreadyBoughtException e) {
    return new ErrorResponseDto(ITEM_ALREADY_BOUGHT_EXCEPTION_MESSAGE, e.getMessage());
  }

  @ExceptionHandler(BidTooLowException.class)
  @ResponseStatus(value = NOT_ACCEPTABLE)
  public ErrorResponseDto handleBidTooLowException(BidTooLowException e) {
    return new ErrorResponseDto(BID_TOO_LOW_EXCEPTION_MESSAGE, e.getMessage());
  }

  @ExceptionHandler(InsufficientFundsException.class)
  @ResponseStatus(value = BAD_REQUEST)
  public ErrorResponseDto handleInsufficientFundsException(InsufficientFundsException e) {
    return new ErrorResponseDto(INSUFFICIENT_FUNDS_EXCEPTION_MESSAGE, e.getMessage());
  }
}