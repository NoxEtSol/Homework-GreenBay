package com.greenfoxacademy.greenbay.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Bid {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JsonIgnore
  private Product product;

  @OneToOne
  private Customer customer;

  private int amount;

  public Bid(Product product, Customer customer, int amount) {
    this.product = product;
    this.customer = customer;
    this.amount = amount;
  }

  public Bid() {

  }
}
