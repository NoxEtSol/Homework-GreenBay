package com.greenfoxacademy.greenbay.models.DTOs;

import com.greenfoxacademy.greenbay.models.entities.Bid;
import lombok.Data;

import java.util.List;

@Data
public class SpecificSellableDto implements ResponseDto {

  private String name;
  private String description;
  private String photoUrl;
  private List<Bid> bids;
  private int startingPrice;
  private int purchasePrice;
  private String seller;
}

