package com.example.restservice.Images.usecases;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.restservice.Images.domain.DatabaseImageRepository;
import com.example.restservice.Images.domain.Image;
import com.example.restservice.Images.domain.ImageProcessingRepository;
import com.example.restservice.Images.domain.ImageResource;
import com.example.restservice.Images.domain.ImageResourceType;
import com.example.restservice.Images.domain.ImageSize;
import com.example.restservice.Images.domain.ImageStorageRepository;
import com.example.restservice.Images.dto.UploadImageResponseDTO;

import jakarta.transaction.Transactional;

@Service
public class UploadUserImageUsecase {

  private final ImageStorageRepository imageStorageRepository;
  private final ImageProcessingRepository imageProcessingRepository;
  private final DatabaseImageRepository databaseImageRepository;
  private final Executor imageExecutor;

  public UploadUserImageUsecase(
      ImageStorageRepository imageStorageRepository,
      ImageProcessingRepository imageProcessingRepository,
      DatabaseImageRepository databaseImageRepository,
      Executor imageExecutor) {

    this.imageStorageRepository = imageStorageRepository;
    this.databaseImageRepository = databaseImageRepository;
    this.imageProcessingRepository = imageProcessingRepository;
    this.imageExecutor = imageExecutor;
  }

  @Transactional
  public UploadImageResponseDTO execute(
      MultipartFile file, UUID userId, ImageResourceType resourceType) throws IOException {

    byte[] original = file.getBytes();

    ImageResource resource = ImageResource.of(userId, resourceType);
    Image image = Image.create(resource, 0);

    Map<ImageSize, byte[]> processedImages;

    if (resourceType == ImageResourceType.USER_PROFILE) {
      processedImages = imageProcessingRepository.process(original);
    } else if (resourceType == ImageResourceType.USER_BACKGROUND) {
      processedImages = imageProcessingRepository.processBackground(original);
    } else {
      throw new IllegalArgumentException("Unsupported image type");
    }

    Map<ImageSize, CompletableFuture<String>> uploads = new HashMap<>();

    for (ImageSize size : processedImages.keySet()) {

      uploads.put(size, uploadAsync(processedImages.get(size), image.getId(), size, resource));
    }

    CompletableFuture.allOf(uploads.values().toArray(new CompletableFuture[0])).join();

    databaseImageRepository.save(image);

    if (resourceType == ImageResourceType.USER_PROFILE) {

      return new UploadImageResponseDTO(
          image.getId().toString(),
          uploads.get(ImageSize.LARGE).join(),
          uploads.get(ImageSize.MEDIUM).join(),
          uploads.get(ImageSize.THUMBNAIL).join());
    }

    return new UploadImageResponseDTO(
        image.getId().toString(),
        uploads.get(ImageSize.BACKGROUND_LARGE).join(),
        uploads.get(ImageSize.BACKGROUND_MEDIUM).join(),
        uploads.get(ImageSize.BACKGROUND_SMALL).join());
  }

  private CompletableFuture<String> uploadAsync(
      byte[] bytes, UUID imageId, ImageSize size, ImageResource resource) {

    return CompletableFuture.supplyAsync(
        () -> {
          String objectKey = resource.genFilename(imageId, size);

          imageStorageRepository.upload(new ByteArrayInputStream(bytes), objectKey, bytes.length);

          return objectKey;
        },
        imageExecutor);
  }
}
