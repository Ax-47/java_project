package com.example.restservice.Images.controllers;

import org.springframework.web.bind.annotation.*;

import com.example.restservice.Images.usecases.FindImageUsecase;
import com.example.restservice.Images.usecases.ReorderImageUsecase;
import com.example.restservice.Images.usecases.UploadImageUsecase;

@RestController
@RequestMapping("/api/images")
public class ImageController {

  private final UploadImageUsecase uploadImageUsecase;
  private final FindImageUsecase findImageUsecase;
  private final ReorderImageUsecase reorderImageUsecase;

  public ImageController(
      UploadImageUsecase uploadImageUsecase,
      FindImageUsecase findImageUsecase,
      ReorderImageUsecase reorderImageUsecase) {
    this.uploadImageUsecase = uploadImageUsecase;
    this.findImageUsecase = findImageUsecase;
    this.reorderImageUsecase = reorderImageUsecase;
  }
}
