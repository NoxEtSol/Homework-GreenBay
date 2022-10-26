package com.greenfoxacademy.greenbay.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageDto implements ResponseDto {

  private String message;
}
