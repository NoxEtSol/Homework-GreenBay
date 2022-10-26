package com.greenfoxacademy.greenbay.models.DTOs;

import com.greenfoxacademy.greenbay.models.entities.Bid;
import com.greenfoxacademy.greenbay.models.entities.Customer;
import lombok.Data;

import java.util.List;

@Data
public class SpecificNotSellableDto implements ResponseDto {

  private String name;
  private String description;
  private String photoUrl;
  private List<Bid> bids;
  private int purchasePrice;
  private String seller;
  private Customer buyer;
}