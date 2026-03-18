package com.example.restservice.Images.dto;

import java.util.UUID;

import com.example.restservice.Images.domain.Image;
import com.example.restservice.Images.domain.ImageSize;

public record ImagesResponseDTO(
    UUID id, String thumbnail, String medium, String large, int sortOrder) {

  public static ImagesResponseDTO from(Image image) {
    var resource = image.getResource();
    var imageId = image.getId();
    String thumbnail = "/images" + resource.genFilename(imageId, ImageSize.THUMBNAIL);
    String medium = "/images" + resource.genFilename(imageId, ImageSize.MEDIUM);
    String large = "/images" + resource.genFilename(imageId, ImageSize.LARGE);

    return new ImagesResponseDTO(imageId, thumbnail, medium, large, image.getSortOrder());
  }
}
