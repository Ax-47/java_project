package com.example.restservice.Images.usecases;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Images.domain.DatabaseImageRepository;
import com.example.restservice.Images.domain.Image;
import com.example.restservice.Images.dto.ReorderImageRequestDTO;

import jakarta.transaction.Transactional;

@Service
public class ReorderImageUsecase {

  private final DatabaseImageRepository databaseImageRepository;

  public ReorderImageUsecase(DatabaseImageRepository databaseImageRepository) {
    this.databaseImageRepository = databaseImageRepository;
  }

  @Transactional
  public void execute(UUID resourceId, List<ReorderImageRequestDTO> images) {

    for (ReorderImageRequestDTO dto : images) {

      Image image =
          databaseImageRepository
              .findById(dto.imageId())
              .orElseThrow(() -> new IllegalStateException("Image not found"));
      if (!image.getResource().getResourceId().equals(resourceId)) {
        throw new IllegalStateException("Image not belong to resource");
      }
      image.changeSortOrder(dto.sortOrder());
      databaseImageRepository.save(image);
    }
  }
}
