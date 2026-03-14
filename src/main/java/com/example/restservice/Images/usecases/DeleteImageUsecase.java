package com.example.restservice.Images.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Images.domain.DatabaseImageRepository;
import com.example.restservice.Images.domain.Image;

import jakarta.transaction.Transactional;

@Service
public class DeleteImageUsecase {

  private final DatabaseImageRepository databaseImageRepository;

  public DeleteImageUsecase(DatabaseImageRepository databaseImageRepository) {
    this.databaseImageRepository = databaseImageRepository;
  }

  @Transactional
  public void execute(UUID resourceId, UUID imageId) {

    Image image =
        databaseImageRepository
            .findById(imageId)
            .orElseThrow(() -> new RuntimeException("Image not found"));
    if (!image.getResource().getResourceId().equals(resourceId)) {
      throw new IllegalStateException("Image does not belong to this resource");
    }
    databaseImageRepository.delete(imageId);
  }
}
