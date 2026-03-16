package com.example.restservice.Images.usecases;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Images.domain.DatabaseImageRepository;
import com.example.restservice.Images.domain.Image;
import com.example.restservice.Images.domain.ImageResource;
import com.example.restservice.Images.domain.ImageResourceType;
import com.example.restservice.Images.domain.ImageSize;
import com.example.restservice.Images.dto.UploadImageResponseDTO;

@Service
public class FindImageUsecase {

  private final DatabaseImageRepository databaseImageRepository;

  public FindImageUsecase(DatabaseImageRepository databaseImageRepository) {
    this.databaseImageRepository = databaseImageRepository;
  }

  public List<UploadImageResponseDTO> execute(UUID resourceId, ImageResourceType resourceType) {
    ImageResource resource = ImageResource.of(resourceId, resourceType);
    List<Image> images = databaseImageRepository.findByResource(resource);

    return images.stream()
        .map(
            image ->
                new UploadImageResponseDTO(
                    image.getId().toString(),
                    toFileName(resource, image.getId(), ImageSize.THUMBNAIL),
                    toFileName(resource, image.getId(), ImageSize.LARGE),
                    toFileName(resource, image.getId(), ImageSize.MEDIUM)))
        .toList();
  }

  public String toFileName(ImageResource resource, UUID imageId, ImageSize imageSize) {
    return resource.genFilename(imageId, imageSize);
  }
}
