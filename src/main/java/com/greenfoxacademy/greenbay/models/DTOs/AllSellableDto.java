package com.greenfoxacademy.greenbay.models.DTOs;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AllSellableDto implements ResponseDto {

  private List<SellableDto> sellableProducts;
}
