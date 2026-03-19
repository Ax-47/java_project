package com.example.restservice.Products.dto;

import java.util.List;

import com.example.restservice.Images.dto.UploadImageResponseDTO;
import com.example.restservice.Products.domain.Product;

public record ProductImagesViewResponseDTO(Product product, List<UploadImageResponseDTO> images) {

  public static ProductImagesViewResponseDTO from(
      Product product, List<UploadImageResponseDTO> images) {
    return new ProductImagesViewResponseDTO(product, images);
  }
}
