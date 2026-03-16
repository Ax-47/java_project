package com.example.restservice.Images.usecases;

import java.io.InputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Images.domain.ImageResourceType;
import com.example.restservice.Images.domain.ImageStorageRepository;

@Service
public class GetImageUsecase {

  private final ImageStorageRepository storage;

  public GetImageUsecase(ImageStorageRepository storage) {
    this.storage = storage;
  }

  public InputStream execute(ImageResourceType resourceType, UUID resourceId, String imageName) {

    String key = resourceType.getPath() + "/" + resourceId + "/" + imageName;

    return storage.get(key);
  }
}
