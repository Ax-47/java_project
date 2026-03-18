package com.example.restservice.Images.dto;

public record UploadImageResponseDTO(String id, String thumbnail, String medium, String large) {

  public UploadImageResponseDTO {

    if (thumbnail != null) {
      thumbnail = "/images" + thumbnail;
    }

    if (medium != null) {
      medium = "/images" + medium;
    }

    if (large != null) {
      large = "/images" + large;
    }
  }
}
