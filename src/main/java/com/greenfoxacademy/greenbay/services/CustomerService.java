package com.greenfoxacademy.greenbay.services;

import com.greenfoxacademy.greenbay.models.DTOs.CustomerDto;
import com.greenfoxacademy.greenbay.models.DTOs.UsernamePasswordDto;
import com.greenfoxacademy.greenbay.models.entities.Customer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomerService {

  Customer saveNewCustomer(UsernamePasswordDto dto);

  CustomerDto convertToDto(Customer customer);

  boolean customerExists(UsernamePasswordDto dto);

  Customer getCustomerByUsername(String username);

  boolean passwordIsCorrect(UsernamePasswordDto dto);

  UserDetails getUserDetailsByUsername(String username) throws UsernameNotFoundException;

  String getToken(UserDetails userDetails);

  Customer getCustomerFromAuthorizationHeader(String bearerToken);
}
