package com.greenfoxacademy.greenbay.models.DTOs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Data;

@Data
public class ErrorResponseDto {

  private final String message;
  private final String path;
  private String date;

  public ErrorResponseDto(String message, String path) {
    this.message = message;
    this.path = path;
    LocalDateTime timeNow = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    this.date = timeNow.format(formatter);
  }
}
