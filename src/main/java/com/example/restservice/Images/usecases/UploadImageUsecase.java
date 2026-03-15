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
import com.example.restservice.Images.domain.ImageRepository;
import com.example.restservice.Images.domain.ImageResource;
import com.example.restservice.Images.domain.ImageResourceType;
import com.example.restservice.Images.domain.ImageSize;
import com.example.restservice.Images.dto.UploadImageResponseDTO;

import jakarta.transaction.Transactional;

@Service
public class UploadImageUsecase {

  private final ImageRepository imageRepository;
  private final ImageProcessingRepository imageProcessingRepository;
  private final DatabaseImageRepository databaseImageRepository;
  private final Executor imageExecutor;

  public UploadImageUsecase(
      ImageRepository imageRepository,
      ImageProcessingRepository imageProcessingRepository,
      DatabaseImageRepository databaseImageRepository,
      Executor imageExecutor) {

    this.imageRepository = imageRepository;
    this.imageProcessingRepository = imageProcessingRepository;
    this.databaseImageRepository = databaseImageRepository;
    this.imageExecutor = imageExecutor;
  }

  @Transactional
  public UploadImageResponseDTO execute(
      MultipartFile file, UUID resourseId, ImageResourceType resourceType, int sortOrder)
      throws IOException {

    byte[] original = file.getBytes();

    ImageResource resource = ImageResource.of(resourseId, resourceType);

    Image image = Image.create(resource, sortOrder);
    Map<ImageSize, byte[]> processedImages = imageProcessingRepository.process(original);
    Map<ImageSize, CompletableFuture<String>> uploads = new HashMap<>();
    for (ImageSize size : processedImages.keySet()) {
      uploads.put(size, uploadAsync(processedImages.get(size), image.getId(), size, resource));
    }
    CompletableFuture.allOf(uploads.values().toArray(new CompletableFuture[0])).join();
    databaseImageRepository.save(image);
    return new UploadImageResponseDTO(
        image.getId().toString(),
        uploads.get(ImageSize.THUMBNAIL).join(),
        uploads.get(ImageSize.MEDIUM).join(),
        uploads.get(ImageSize.LARGE).join());
  }

  private CompletableFuture<String> uploadAsync(
      byte[] bytes, UUID imageId, ImageSize size, ImageResource resource) {

    return CompletableFuture.supplyAsync(
        () -> {
          String objectKey = resource.genFilename(imageId, size);

          imageRepository.upload(new ByteArrayInputStream(bytes), objectKey, bytes.length);

          return objectKey;
        },
        imageExecutor);
  }
}
