package com.greenfoxacademy.greenbay.models.DTOs;

import lombok.Data;

@Data
public class SellableDto implements ResponseDto {

  private String name;
  private String photoUrl;
  private int lastBid;
}