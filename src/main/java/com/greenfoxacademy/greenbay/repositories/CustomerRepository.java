package com.greenfoxacademy.greenbay.repositories;

import com.greenfoxacademy.greenbay.models.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  boolean existsCustomerByUsername(String username);
  Customer getCustomerByUsername(String username);
}
