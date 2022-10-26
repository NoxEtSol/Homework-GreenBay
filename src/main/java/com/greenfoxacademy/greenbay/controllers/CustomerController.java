package com.greenfoxacademy.greenbay.controllers;

import static org.springframework.http.ResponseEntity.ok;

import com.greenfoxacademy.greenbay.exceptions.CustomerAlreadyExistsException;
import com.greenfoxacademy.greenbay.exceptions.IncorrectPasswordException;
import com.greenfoxacademy.greenbay.exceptions.NoSuchCustomerException;
import com.greenfoxacademy.greenbay.models.DTOs.BearerTokenDto;
import com.greenfoxacademy.greenbay.models.DTOs.ResponseDto;
import com.greenfoxacademy.greenbay.models.DTOs.UsernamePasswordDto;
import com.greenfoxacademy.greenbay.models.entities.Customer;
import com.greenfoxacademy.greenbay.services.CustomerService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

  private final CustomerService customerService;

  @PostMapping("/sign-up")
  public ResponseEntity<ResponseDto> signUp(@RequestBody @Valid UsernamePasswordDto dto) {
    if (customerService.customerExists(dto)) {
      throw new CustomerAlreadyExistsException("/sign-up");
    }
    Customer customer = customerService.saveNewCustomer(dto);
    return ok(customerService.convertToDto(customer));
  }

  @PostMapping("/login")
  public ResponseEntity<ResponseDto> attemptLogin(@RequestBody @Valid UsernamePasswordDto dto) {
    if (!customerService.customerExists(dto)) {
      throw new NoSuchCustomerException("/login");
    }
    if (!customerService.passwordIsCorrect(dto)) {
      throw new IncorrectPasswordException("/login");
    }
    User user = (User) customerService.getUserDetailsByUsername(dto.getUsername());
    String token = customerService.getToken(user);
    return ok(new BearerTokenDto(token));
  }
}