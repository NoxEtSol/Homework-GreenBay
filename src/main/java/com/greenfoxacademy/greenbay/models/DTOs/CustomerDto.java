package com.greenfoxacademy.greenbay.models.DTOs;

import com.greenfoxacademy.greenbay.models.entities.Product;
import java.util.List;
import lombok.Data;

@Data
public class CustomerDto implements ResponseDto {

  private String username;
  private List<Product> productsForSale;
  private List<Product> productsOwned;
  private int balance;
}
