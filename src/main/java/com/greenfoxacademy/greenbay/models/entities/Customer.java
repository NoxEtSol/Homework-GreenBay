package com.greenfoxacademy.greenbay.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  @JsonIgnore
  private String password;
  @JsonIgnore
  private int balance;

  @JsonIgnore
  @OneToMany
  private List<Product> productsForSale;
  @JsonIgnore
  @OneToMany
  private List<Product> ownedProducts;

  public Customer(String username, String password) {
    this.username = username;
    this.password = password;
    this.balance = 500;
    this.productsForSale = new ArrayList<>();
    this.ownedProducts = new ArrayList<>();
  }
}
