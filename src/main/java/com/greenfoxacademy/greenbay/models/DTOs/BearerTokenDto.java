package com.greenfoxacademy.greenbay.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BearerTokenDto implements ResponseDto {

  private String accessToken;
}
