package com.example.restservice.Images.controllers;

import java.io.InputStream;
import java.util.UUID;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.restservice.Images.domain.ImageResourceType;
import com.example.restservice.Images.usecases.GetImageUsecase;

@RestController
@RequestMapping("/images")
public class ImageController {

  private final GetImageUsecase getImageUseCase;

  public ImageController(GetImageUsecase getImageUseCase) {
    this.getImageUseCase = getImageUseCase;
  }

  @GetMapping("/{type}/{resourceId}/{imageName}")
  public ResponseEntity<InputStreamResource> getImage(
      @PathVariable String type, @PathVariable UUID resourceId, @PathVariable String imageName) {
    ImageResourceType resourceType = ImageResourceType.fromPath(type);
    InputStream stream = getImageUseCase.execute(resourceType, resourceId, imageName);

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType("image/webp"))
        .body(new InputStreamResource(stream));
  }
}
