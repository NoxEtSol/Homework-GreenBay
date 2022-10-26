package com.greenfoxacademy.greenbay.models.DTOs;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewProductDto {

  @NotBlank
  @NotNull
  private String name;
  @NotBlank
  @NotNull
  private String description;
  @NotBlank
  @NotNull
  private String photoUrl;

  @NotNull
  @Min(1)
  private Integer startingPrice;

  @NotNull
  @Min(1)
  private Integer purchasePrice;
}