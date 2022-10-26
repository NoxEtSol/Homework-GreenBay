package com.greenfoxacademy.greenbay.services;

import static com.greenfoxacademy.greenbay.security.SecurityConfiguration.TOKEN_EXPIRATION_TIME;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.greenfoxacademy.greenbay.exceptions.NoSuchCustomerException;
import com.greenfoxacademy.greenbay.models.DTOs.CustomerDto;
import com.greenfoxacademy.greenbay.models.DTOs.UsernamePasswordDto;
import com.greenfoxacademy.greenbay.models.entities.Customer;
import com.greenfoxacademy.greenbay.repositories.CustomerRepository;
import io.github.cdimascio.dotenv.Dotenv;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final ModelMapper modelMapper;

  @Override
  public Customer saveNewCustomer(UsernamePasswordDto dto) {
    return customerRepository.save(new Customer(dto.getUsername(), passwordEncoder.encode(
        dto.getPassword())));
  }

  @Override
  public CustomerDto convertToDto(Customer customer) {
    return modelMapper.map(customer, CustomerDto.class);
  }

  @Override
  public boolean customerExists(UsernamePasswordDto dto) {
    return customerRepository.existsCustomerByUsername(dto.getUsername());
  }

  @Override
  public Customer getCustomerByUsername(String username) {
    Customer customer = customerRepository.getCustomerByUsername(username);
    if (customer == null) {
      throw new NoSuchCustomerException("Customer service: getCustomerByUsername()");
    }
    return customer;
  }

  @Override
  public boolean passwordIsCorrect(UsernamePasswordDto dto) {
    Customer customer = getCustomerByUsername(dto.getUsername());
    return passwordEncoder.matches(dto.getPassword(), customer.getPassword());
  }

  @Override
  public UserDetails getUserDetailsByUsername(String username) throws UsernameNotFoundException {
    Customer customer = customerRepository.getCustomerByUsername(username);
    if (customer == null) {
      throw new NoSuchCustomerException("Customer service: getUserDetailsByUsername()");
    }
    return new User(customer.getUsername(), customer.getPassword(), new ArrayList<>());
  }

  @Override
  public String getToken(UserDetails userDetails) {
    Dotenv dotenv = Dotenv.load();
    Algorithm algorithm = Algorithm.HMAC512(
        Objects.requireNonNull(dotenv.get("JWT_SECRET_KEY")).getBytes(
            StandardCharsets.UTF_8));
    return JWT.create().withSubject(userDetails.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
        .withIssuer(
            ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/customers/login")
                .toString()).withClaim("roles", userDetails.getAuthorities().stream().map(
            GrantedAuthority::getAuthority).collect(Collectors.toList())).sign(algorithm);
  }

  @Override
  public Customer getCustomerFromAuthorizationHeader(String bearerToken) {
    Dotenv dotenv = Dotenv.load();
    String token = bearerToken.substring("bearer ".length());

    Algorithm algorithm = Algorithm.HMAC512(
        Objects.requireNonNull(dotenv.get("JWT_SECRET_KEY")).getBytes(
            StandardCharsets.UTF_8));

    JWTVerifier verifier = JWT.require(algorithm).build();
    DecodedJWT decodedJWT = verifier.verify(token);
    String username = decodedJWT.getSubject();
    Customer customer = getCustomerByUsername(username);
    if (customer == null) {
      throw new NoSuchCustomerException("Customer service: getCustomerFromAuthorizationHeader()");
    }
    return customer;
  }
}
